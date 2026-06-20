#!/usr/bin/env bash
#
# fix-myweather.sh — applies the mechanical fixes for kusl/myweather:
#   * all Dependabot Gradle version bumps   (gradle/libs.versions.toml)
#   * all Dependabot GitHub Actions bumps    (.github/workflows/*.yml)
#   * the lint MissingPermission fix         (LocationProvider.kt)
#   * removes the stray duplicate files left over from the folder move
#   * hardens .gitignore so the base64 keystore can never be committed
#
# It does NOT touch your signing secrets — set those manually (see chat notes).
# Run from the repo root, review `git diff`, then commit & push.
#
set -euo pipefail

# --- safety: must be at the repo root --------------------------------------
if [[ ! -f settings.gradle.kts || ! -f gradle/libs.versions.toml ]]; then
  echo "ERROR: run this from the myweather repo root (where settings.gradle.kts is)." >&2
  exit 1
fi

echo "==> Gradle version bumps  (gradle/libs.versions.toml)"
# Anchored on  key = "..."  so only the [versions] lines change, never the
# [libraries] block (note there is also an  okhttp = { ... }  library entry).
sed -i \
  -e 's/^lifecycle = "[^"]*"/lifecycle = "2.11.0"/' \
  -e 's/^activityCompose = "[^"]*"/activityCompose = "1.13.0"/' \
  -e 's/^navigationCompose = "[^"]*"/navigationCompose = "2.9.8"/' \
  -e 's/^coroutines = "[^"]*"/coroutines = "1.11.0"/' \
  -e 's/^okhttp = "[^"]*"/okhttp = "5.4.0"/' \
  -e 's/^androidxJunit = "[^"]*"/androidxJunit = "1.3.0"/' \
  -e 's/^espresso = "[^"]*"/espresso = "3.7.0"/' \
  gradle/libs.versions.toml
# (agp is already 9.2.1 — Dependabot PR #2 needs no change.)

echo "==> GitHub Actions bumps  (.github/workflows/*.yml)"
sed -i \
  -e 's#actions/checkout@v5#actions/checkout@v7#g' \
  -e 's#actions/setup-java@v4#actions/setup-java@v5#g' \
  -e 's#gradle/actions/setup-gradle@v4#gradle/actions/setup-gradle@v6#g' \
  -e 's#actions/upload-artifact@v4#actions/upload-artifact@v7#g' \
  -e 's#softprops/action-gh-release@v2#softprops/action-gh-release@v3#g' \
  .github/workflows/*.yml
# (reactivecircus/android-emulator-runner stays at @v2 — Dependabot has no PR for it.)

echo "==> Lint fix  (LocationProvider.kt)"
LP="app/src/main/java/com/kusl/myweather/data/location/LocationProvider.kt"
MARK='@Suppress("MissingPermission") // hasPermission() is checked at the top.'
if ! grep -qF "$MARK" "$LP"; then
  sed -i 's#^    override suspend fun currentLocation(): LocationResult {#    @Suppress("MissingPermission") // hasPermission() is checked at the top.\n    override suspend fun currentLocation(): LocationResult {#' "$LP"
  echo "    added @Suppress to currentLocation()"
else
  echo "    already fixed"
fi

echo "==> Removing stray duplicates left over from the folder move"
for stray in app-build.gradle.kts libs.versions.toml WeatherRepositoryImpl.kt; do
  if [[ -e "$stray" ]]; then
    git rm -q -- "$stray" 2>/dev/null || rm -f -- "$stray"
    echo "    removed ./$stray"
  fi
done

echo "==> Hardening .gitignore"
if ! grep -qxF 'release.keystore.base64' .gitignore 2>/dev/null; then
  printf '\n# base64-encoded keystore — never commit\nrelease.keystore.base64\n*.base64\n' >> .gitignore
  echo "    added base64 keystore ignore rule"
fi

echo
echo "Done. Now:"
echo "  git diff                       # review the changes"
echo "  git add -A"
echo "  git commit -m 'deps: apply Dependabot bumps; fix lint MissingPermission; drop stray files'"
echo "  git push"
