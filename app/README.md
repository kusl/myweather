# `:app` module

The single application module for MyWeather. It contains everything: the Compose
UI, ViewModels, the domain contracts, and the data layer (NWS networking + Room
cache + framework location).

There is intentionally only one Gradle module — the project is small enough that
splitting it would add ceremony without benefit. Internal boundaries are enforced
by package structure and interface seams rather than by module boundaries.

## Layout

| Package | Responsibility |
| --- | --- |
| `core` | Pure Kotlin value types and geometry (no Android imports): `GeoPoint`, `GridPoint`, `GridMath`, `TimeSource`. |
| `domain` | Domain models and the `WeatherRepository` contract + `AreaWeatherResult`. |
| `data.remote` | Retrofit `NwsApi`, GeoJSON DTOs, the dynamic `UserAgentProvider`/interceptor, and `NetworkModule`. |
| `data.local` | Room entities, DAOs, and `WeatherDatabase`. |
| `data.mapper` | Pure DTO → domain mapping. |
| `data.location` | `LocationSource` seam and the framework-backed `LocationProvider`. |
| `data.settings` | `SettingsRepository` seam and its DataStore implementation. |
| `data` | `WeatherRepositoryImpl` (cache-aside orchestrator) and `SavedLocationRepository`. |
| `di` | `AppContainer` — manual DI — and the shared `ViewModelProvider.Factory`. |
| `ui` | Theme, dependency-free icons, navigation, the three screens, and reusable components. |

## Build specifics

- `compileSdk 36`, `minSdk 34`, `targetSdk 35`, JDK 17.
- Application id `com.github.kusl.myweather` (`…debug` for debug builds). The
  Kotlin namespace is `com.kusl.myweather` and is intentionally separate from the
  application id — they are allowed to differ and serve different purposes.
- Compose via the BOM; KSP only for Room.
- `assembleRelease` reads signing material from env vars or `keystore.properties`,
  falling back to debug signing when neither is present (see the root README).
- `testOptions.unitTests.isReturnDefaultValues = true` so JVM unit tests can
  touch `android.util.Log` stubs without throwing.
- The XML window theme (`res/values/themes.xml` + `res/values-night/themes.xml`)
  is a thin framework (`android:Theme.Material*`) base — there is no
  AppCompat/Material-Components library on the classpath — and only sets the
  pre-Compose window background and system-bar colors. All real theming is the
  Compose `MaterialTheme`.
  