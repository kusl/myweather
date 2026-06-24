# Caching & freshness

MyWeather caches at **two levels**: a transport-level HTTP cache that sits in
front of *every* NWS request, and the repository's domain cache that knows the
freshness policy for the specific calls a forecast lookup needs. The goal across
both: be responsive and offline-capable while making as few NWS requests as
possible — NWS is a free public service, so we aim to be a model citizen. This
mirrors the policy of the sibling .NET dashboard.

## Tier 0 — the transport cache (`HttpCacheInterceptor`)

`HttpCacheInterceptor` is an OkHttp **application interceptor**, installed
**outermost** (before the User-Agent interceptor and the debug logger). Because
it wraps the whole stack, it shields **every** endpoint automatically — not just
metadata and forecasts, but the supplementary calls too (`/alerts/active`,
`/forecast/hourly`, `/stations`, `/observations/latest`). It is backed by the
Room `http_cache` table, one row per request.

- **De-duplication key:** `"$METHOD $url"`, computed *after* Retrofit has built
  the final URL. Any two calls that resolve to the same endpoint collapse onto a
  single row. This is what makes near-identical inputs cheap end-to-end: two GPS
  fixes a fraction of a degree apart round to the same 4-dp coordinate, produce
  the same `/points` URL, and therefore hit the same cached row instead of
  re-requesting.
- **Everything is cached, including errors.** On a fresh hit the cached response
  is replayed with no network call. On a miss (or once stale) the request goes
  out; whatever comes back — success *or* error — is stored with a computed
  expiry:
  - **2xx / 304:** `Cache-Control: max-age`, clamped to **[15 s, 6 h]**; **30 min**
    if absent.
  - **404 / 410 (negative cache):** **24 h** — NWS returns `404` for off-grid
    cells and out-of-coverage points, and that answer is stable, so we stop
    asking.
  - **408 / 425 / 429 / 5xx (transient backoff):** honour `Retry-After` (clamped
    **[15 s, 10 min]**), else **60 s** — back off politely instead of hammering.
  - **other 4xx:** **1 h**.
- **Conditional revalidation.** If a stale row carries an `ETag` and the caller
  didn't set its own `If-None-Match`, the interceptor adds one. A `304` then
  renews the TTL and replays the stored body — the cheapest possible refresh. If
  the caller *did* send `If-None-Match` (the repository's forecast path does),
  the `304` is passed straight through so that layer's own logic handles it.
- **Stale-on-error.** If the network throws and a cached row exists, the stale
  body is served rather than failing.
- **Guards & housekeeping.** Bodies over **2 MB** are streamed through without
  caching; rows that expired **more than 7 days ago** are pruned opportunistically
  (roughly every 50 calls). Each served response carries an `X-MyWeather-Cache`
  header (`HIT` / `NETWORK` / `STALE` / `REVALIDATED`) for diagnostics.

**Intentional divergence from RFC 9111.** A standards-compliant HTTP cache would
refuse to store `no-store` responses or `404`s. We deliberately *do* cache them,
because the overriding goal is to minimise requests to a free public API. This is
safe here: every payload is public, re-fetchable, and short-lived, so a brief
negative cache can't cause harm — and a version bump drops the whole cache DB
anyway (see below).

**Out of scope:** true in-flight coalescing (collapsing genuinely simultaneous
identical requests into one socket). The stated motivating case — repeated/nearby
coordinates — is already handled by 4-dp rounding plus this TTL cache, and the
neighbourhood ring is additionally bounded by a `Semaphore` of 4 (below).

## Tier 1 — the repository's domain cache

NWS splits a forecast lookup into two calls that change at very different rates,
so `WeatherRepositoryImpl` caches them separately with policy it understands.
(This sits *above* Tier 0; the transport cache can still short-circuit the actual
socket traffic, but the repository remains the source of the freshness *policy*
and the offline-first behaviour the UI sees.)

### `coordinate → grid` metadata (long-lived)

`GET /points/{lat},{lon}` returns the forecast office and grid X/Y for a
coordinate. This mapping is effectively immutable.

- **Key:** the coordinate rounded to **4 decimal places**. Many physically-close
  lookups collapse onto one row, so we rarely hit `/points` at all.
- **TTL:** **30 days** (a fixed policy value, not derived from response headers).
- **404 handling:** coverage doesn't change, so a `404` trusts an existing stale
  row if present; otherwise it's reported as **no coverage**.

### Grid forecast (short-lived, revalidated)

`GET /gridpoints/{office}/{x},{y}/forecast` returns the multi-day forecast.

- **Key:** `"office/x,y"`.
- **TTL:** taken from the response's `Cache-Control: max-age`, then **clamped**:
  - default **30 minutes** if the header is missing or zero,
  - hard ceiling of **6 hours**.
- **Stored alongside the forecast:** the `ETag` and the computed expiry.

## The request flow

For a forecast cell, on each request:

1. **Fresh cache hit** (`now < expiresAt`) → return the cached forecast. No
   network call.
2. **Expired** → issue a **conditional GET** with `If-None-Match: <stored ETag>`:
   - **`304 Not Modified`** → the server confirms nothing changed; we just extend
     the TTL (and refresh the `ETag`). The cheapest refresh there is.
   - **`200 OK`** → store the new forecast, `ETag`, and a fresh TTL.
   - **`404` / `429` / `5xx` / network error** → **stale-on-error**: serve the
     last cached copy (flagged stale) if we have one; otherwise report
     *unavailable*. (For neighbor cells a `404` simply means "no such cell at the
     office edge", so that tile is quietly dropped.)

The same fresh/stale/conditional logic applies to metadata, except metadata uses
its fixed 30-day TTL instead of `Cache-Control`.

## Supplementary data (primary location only)

Alongside the forecast, the **primary** location also gathers, best-effort: the
active alerts, the hourly forecast (capped at 24 entries), the latest station
observation, and the set of source endpoints/zones. These run concurrently and
each is wrapped so a failure or absence never blocks the core forecast — they're
extras. The grid-recenter path (an ephemeral peek at a neighbouring cell) skips
them entirely. All of these calls still flow through Tier 0, so they're
de-duplicated, negatively cached, and served stale-on-error like everything else.

## Offline behaviour

When NWS is unreachable, the repository returns the freshest cached data it has
and sets `AreaWeather.fromCache = true`. The dashboard shows a banner so the user
knows the data may be stale, but still sees a forecast.

## The neighborhood matrix

For the surrounding cells (a Chebyshev ring of the configured radius — 1 ⇒ 3×3,
2 ⇒ 5×5, 3 ⇒ 7×7), each cell goes through the **same cache-aside path**. The
neighbor fetches run concurrently but are **bounded by a `Semaphore` of 4**, so
even a completely cold area issues at most four simultaneous requests. Tiles are
ordered by approximate distance from the user's cell.

## Bounding cache growth without losing the safety net

Merely-expired rows are deliberately **kept** — they're the offline fallback.
Only rows that expired **more than a week ago** are pruned (opportunistically, at
the start of an area request for the domain cache, and periodically inside the
transport cache), so the database stays small without sacrificing
stale-on-error.

## Schema note

Adding the transport cache introduced the `http_cache` table, taking the Room
database to **version 2**. It's a pure cache DB opened with
`fallbackToDestructiveMigration`, so a version bump simply drops and rebuilds it;
the dropped rows are transparently re-fetched from NWS. The exported schema JSON
is committed for review.

## What's tested

`CacheFreshnessTest` covers `max-age` parsing and TTL clamping; `WeatherRepositoryTest`
covers fresh-hit (no network), cold fetch + tile assembly, stale-on-error,
no-coverage, the conditional-GET header wiring, TTL defaulting/clamping, and the
supplementary alerts being fetched for the primary location but skipped on
recenter — all end-to-end with in-memory fakes. The transport interceptor's
TTL-selection helper (`ttlMsForStatus`) is pure and unit-testable in isolation.
