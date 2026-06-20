# Caching & freshness

All caching lives in `WeatherRepositoryImpl` and is invisible to the rest of the
app. The goal: be responsive and offline-capable while making as few NWS requests
as possible. This mirrors the policy of the sibling .NET dashboard.

## Two tiers, two lifetimes

NWS splits a forecast lookup into two calls, and they change at very different
rates — so they're cached separately.

### 1. `coordinate → grid` metadata (long-lived)

`GET /points/{lat},{lon}` returns the forecast office and grid X/Y for a
coordinate. This mapping is effectively immutable.

- **Key:** the coordinate rounded to **4 decimal places**. Many physically-close
  lookups collapse onto one row, so we rarely hit `/points` at all.
- **TTL:** **30 days** (a fixed policy value, not derived from response headers).
- **404 handling:** coverage doesn't change, so a `404` trusts an existing stale
  row if present; otherwise it's reported as **no coverage**.

### 2. Grid forecast (short-lived, revalidated)

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
the start of an area request), so the database stays small without sacrificing
stale-on-error.

## What's tested

`CacheFreshnessTest` covers `max-age` parsing and TTL clamping; `WeatherRepositoryTest`
covers fresh-hit (no network), cold fetch + tile assembly, stale-on-error,
no-coverage, the conditional-GET header wiring, and TTL defaulting/clamping
end-to-end with in-memory fakes.
