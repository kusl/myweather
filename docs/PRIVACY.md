# Privacy

MyWeather is built to be private by construction, not by policy.

## No tracking, no third parties

- **No analytics or telemetry** of any kind.
- **No Google Play Services, Firebase, or Google Sign-In.** Location uses the
  Android framework `LocationManager` directly — not the fused/Play-Services
  provider.
- **No advertising, no crash reporting, no remote config.**
- The **only** network endpoint contacted is `https://api.weather.gov`.

You can confirm this from the dependency list in `gradle/libs.versions.toml` and
the single `INTERNET` use in `AndroidManifest.xml`.

## Permissions

| Permission | Why | Notes |
| --- | --- | --- |
| `INTERNET` | Fetch forecasts from NWS | The only network use. |
| `ACCESS_NETWORK_STATE` | Tell "offline" apart from a server error | |
| `ACCESS_COARSE_LOCATION` | Optional: load the forecast for where you are | **Coarse only.** No fine location, no background location. The app is fully usable if you deny it. |

## Your data stays on device

- Saved locations and settings live in a local Room database and DataStore.
- The forecast cache is local SQLite.
- The cache is **excluded** from Android cloud backup and from device-to-device
  transfer (see `res/xml/backup_rules.xml` and `data_extraction_rules.xml`) —
  it's just re-fetchable public data.

## The User-Agent you send

NWS asks every client to send a descriptive `User-Agent` identifying the app and
a contact, so they can reach you before rate-limiting rather than just blocking
you. MyWeather ships a generic default and lets you set your own in **Settings**.
This string is sent only to `api.weather.gov`.

## What NWS receives

When you request a forecast, NWS necessarily receives the coordinate (or grid)
you asked about and your `User-Agent`, like any HTTP request to their public API.
MyWeather adds nothing beyond that. Review the
[NWS API documentation](https://www.weather.gov/documentation/services-web-api)
for their terms.
