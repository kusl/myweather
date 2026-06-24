# Architecture

MyWeather follows a pragmatic, layered, unidirectional-data-flow design.

```
Compose UI  ──state──>  ViewModel  ──suspend calls──>  Repository (interface)
     ^                      |                                   |
     └────── StateFlow ─────┘                                   v
                                                  WeatherRepositoryImpl
                                                  ├─ NwsApi  (Retrofit + OkHttp)
                                                  ├─ Room    (SQLite cache)
                                                  └─ mappers (DTO → domain)
```

## Layers

### `core` — pure Kotlin
No Android dependencies, so it's trivially unit-testable.

- **`GeoPoint`** — a validated lat/lon. Rounds to 4 decimal places for cache keys
  (NWS itself rounds to 4 dp, ~11 m), formats an invariant `"lat,lon"` API
  string, and computes haversine distance.
- **`GridPoint`** — an NWS grid cell (`office` + `x` + `y`).
- **`GridMath`** — the neighborhood geometry: `surrounding(origin, radius)`
  (Chebyshev ring, origin excluded, negative indices dropped) and
  `blockWithOrigin(...)` (north-up matrix with nulls for off-grid cells).
- **`TimeSource`** — a one-method clock seam so cache-freshness logic is
  deterministic under test.

### `domain` — models + contracts
Plain data classes (`Forecast`, `ForecastPeriod`, `PointMetadata`, `WeatherTile`,
`AreaWeather`, `SavedLocation`) and the `WeatherRepository` interface, whose
result type `AreaWeatherResult` is `Success | NoCoverage | Unavailable` so the UI
can react cleanly to every outcome. `AreaWeather` also carries the primary
location's supplementary data: `WeatherAlert`s (active watches/warnings, with the
human-authored text kept verbatim), an hourly forecast, the latest
`CurrentObservation` (measured conditions, normalised to US-customary units), and
`LocationSources` (the official NWS endpoints/zones behind the data).

### `data` — the real work
- **`data.remote`** — `NwsApi` returns Retrofit `Response<T>` (not bare bodies)
  so the repository can read `ETag`/`Cache-Control` and handle `304`. It covers
  the points and forecast endpoints plus the supplementary ones (`/alerts/active`,
  `/forecast/hourly`, `/stations`, `/observations/latest`). Two interceptors run
  on every call: `HttpCacheInterceptor` (outermost — a Room-backed transport
  cache that de-duplicates by resolved URL, negatively caches `404`s, backs off
  on `429`/`5xx`, and serves stale-on-error; see [CACHING.md](CACHING.md)), then
  `NwsHeaderInterceptor`, which reads the current User-Agent from an
  `AtomicReference` (`UserAgentProvider`) and sets `Accept: application/geo+json`.
- **`data.local`** — Room stores forecasts as a serialised JSON blob plus the
  columns that drive freshness (`expiresAtEpochMs`, `etag`), and backs the
  transport cache via the `http_cache` table (database version 2).
- **`WeatherRepositoryImpl`** — the cache-aside orchestrator (see
  [CACHING.md](CACHING.md)). It resolves metadata, fetches the primary forecast,
  warms the surrounding ring with **bounded concurrency** (a `Semaphore` capped
  at 4) so a cold area can't stampede NWS, and gathers the primary location's
  best-effort supplementary data concurrently (each call isolated so it never
  blocks the core forecast).

### `di` — manual injection
`AppContainer` builds the object graph once, owns an application
`CoroutineScope`, keeps the live User-Agent in sync with the saved setting, and
exposes a single `ViewModelProvider.Factory` that knows how to build all three
ViewModels. The Room database is built first so its `http_cache` DAO can be handed
to the OkHttp client. No DI framework is used — the graph is small, explicit, and
keeps the dependency footprint minimal.

### `ui` — Compose
A `Scaffold` with a bottom navigation bar hosts a `NavHost` with three
destinations. Each screen owns its `TopAppBar`. Icons are hand-built
`ImageVector`s (from SVG path data) to avoid depending on the deprecated
`material-icons` artifacts. State is exposed as `StateFlow` and collected with
`collectAsStateWithLifecycle`. On the dashboard, active alerts render in a
collapsible banner pinned above everything else, and tapping the headline card or
any forecast period opens a detail sheet.

## Why these choices

- **Manual DI over Hilt** — fewer moving parts, less generated code to trust, a
  smaller APK, and nothing extra to keep version-aligned.
- **Interface seams** (`WeatherRepository`, `SettingsRepository`,
  `LocationSource`) — let ViewModels and the repository be tested with simple
  fakes and no Android runtime.
- **Single module** — the codebase is small; package structure is enough.
- **KSP only for Room** — Room's codegen is its most battle-tested path; nothing
  else needs an annotation processor.
  