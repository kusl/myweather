23
03

good news
both build and generate apk actions are successful now
we are currently using a rolling pre-release model which I want to get rid of. 
it screws up updating from `obtainium` 

I want to do something like this but the files generated 
for each release has to be unique and incrementing
can you please fix this and return the FULL file? 

```yaml
# Builds a signed release APK on every push to main and publishes it to a
# rolling "pre-release" GitHub Release.
#
# Signing: if the repository defines the signing secrets below, the APK is
# signed with the real release key. If not (e.g. on a fork), app/build.gradle.kts
# transparently falls back to the debug signing config so the build still
# succeeds — the publish step is just skipped for forks.
#
# Required repository secrets for a properly-signed build:
#   KEYSTORE_BASE64            base64 of your release .jks/.keystore
#   MYWEATHER_KEYSTORE_PASSWORD
#   MYWEATHER_KEY_ALIAS
#   MYWEATHER_KEY_PASSWORD
name: Build APK

on:
  push:
    branches: [main]
  workflow_dispatch:

# Least privilege by default; the build job opts up to contents:write so it can
# publish the rolling release.
permissions:
  contents: read

concurrency:
  group: build-apk-${{ github.ref }}
  cancel-in-progress: true

jobs:
  build:
    name: Assemble release APK
    runs-on: ubuntu-latest
    timeout-minutes: 30
    permissions:
      contents: write
    steps:
      - name: Checkout
        uses: actions/checkout@v7

      - name: Set up JDK 17
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '17'

      - name: Set up Gradle
        uses: gradle/actions/setup-gradle@v6

      - name: Decode signing keystore (if configured)
        env:
          KEYSTORE_BASE64: ${{ secrets.KEYSTORE_BASE64 }}
        if: ${{ env.KEYSTORE_BASE64 != '' }}
        run: |
          echo "$KEYSTORE_BASE64" | base64 --decode > "${{ github.workspace }}/release.keystore"
          echo "MYWEATHER_KEYSTORE_PATH=${{ github.workspace }}/release.keystore" >> "$GITHUB_ENV"

      - name: Assemble release
        env:
          # These are empty on forks/unsecured runs; the Gradle script then
          # falls back to debug signing so the build still produces an APK.
          MYWEATHER_KEYSTORE_PASSWORD: ${{ secrets.MYWEATHER_KEYSTORE_PASSWORD }}
          MYWEATHER_KEY_ALIAS: ${{ secrets.MYWEATHER_KEY_ALIAS }}
          MYWEATHER_KEY_PASSWORD: ${{ secrets.MYWEATHER_KEY_PASSWORD }}
        run: ./gradlew :app:assembleRelease --stacktrace

      - name: Upload APK artifact
        uses: actions/upload-artifact@v7
        with:
          name: myweather-release-apk
          path: app/build/outputs/apk/release/*.apk
          if-no-files-found: error

      - name: Create Release
        # Only from the canonical repo on a real push to main — never from forks
        # or pull requests (which also lack the signing secrets).
        if: ${{ github.event_name == 'push' && github.ref == 'refs/heads/main' && github.repository == 'kusl/myweather' }}
        uses: softprops/action-gh-release@v3
        with:
          tag_name: v1.0.${{ github.run_number }}
          name: Release v1.0.${{ github.run_number }}
          draft: false
          prerelease: false
          generate_release_notes: true
          make_latest: true
          body: |
            Automated release built from the latest commit on `main`.
                        ## MyWeather v1.0.${{ github.run_number }}
            
            ### Downloads
            
            | Platform | File |
            |----------|------|
            | Android | `MyWeather-${{ github.run_number }}.apk` |

            ### Android Users (Obtainium)
            
            Point Obtainium to this repository's releases for automatic updates.
            The APK version code increments with each release.
            Commit: ${{ github.sha }}
          files: app/build/outputs/apk/release/*.apk

```





















































































/
good news
both build and generate apk actions are successful now
we are currently using a rolling pre-release model which I want to get rid of. 
it screws up updating from obtainium 
I want to do something like this but the files generated 
for each release has to be unique and incrementing
can you please fix this and return the FULL file? 
``yaml
# Builds a signed release APK on every push to main and publishes it to a
# rolling "pre-release" GitHub Release.
#
# Signing: if the repository defines the signing secrets below, the APK is
# signed with the real release key. If not (e.g. on a fork), app/build.gradle.kts
# transparently falls back to the debug signing config so the build still
# succeeds — the publish step is just skipped for forks.
#
# Required repository secrets for a properly-signed build:
#   KEYSTORE_BASE64            base64 of your release .jks/.keystore
#   MYWEATHER_KEYSTORE_PASSWORD
#   MYWEATHER_KEY_ALIAS
#   MYWEATHER_KEY_PASSWORD
name: Build APK
on:
  push:
    branches: [main]
  workflow_dispatch:
# Least privilege by default; the build job opts up to contents:write so it can
# publish the rolling release.
permissions:
  contents: read
concurrency:
  group: build-apk-${{ github.ref }}
  cancel-in-progress: true
jobs:
  build:
    name: Assemble release APK
    runs-on: ubuntu-latest
    timeout-minutes: 30
    permissions:
      contents: write
    steps:
      - name: Checkout
        uses: actions/checkout@v7
      - name: Set up JDK 17
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '17'
      - name: Set up Gradle
        uses: gradle/actions/setup-gradle@v6
      - name: Decode signing keystore (if configured)
        env:
          KEYSTORE_BASE64: ${{ secrets.KEYSTORE_BASE64 }}
        if: ${{ env.KEYSTORE_BASE64 != '' }}
        run: |
          echo "$KEYSTORE_BASE64" | base64 --decode > "${{ github.workspace }}/release.keystore"
          echo "MYWEATHER_KEYSTORE_PATH=${{ github.workspace }}/release.keystore" >> "$GITHUB_ENV"
      - name: Assemble release
        env:
          # These are empty on forks/unsecured runs; the Gradle script then
          # falls back to debug signing so the build still produces an APK.
          MYWEATHER_KEYSTORE_PASSWORD: ${{ secrets.MYWEATHER_KEYSTORE_PASSWORD }}
          MYWEATHER_KEY_ALIAS: ${{ secrets.MYWEATHER_KEY_ALIAS }}
          MYWEATHER_KEY_PASSWORD: ${{ secrets.MYWEATHER_KEY_PASSWORD }}
        run: ./gradlew :app:assembleRelease --stacktrace
      - name: Upload APK artifact
        uses: actions/upload-artifact@v7
        with:
          name: myweather-release-apk
          path: app/build/outputs/apk/release/*.apk
          if-no-files-found: error
      - name: Create Release
        # Only from the canonical repo on a real push to main — never from forks
        # or pull requests (which also lack the signing secrets).
        if: ${{ github.event_name == 'push' && github.ref == 'refs/heads/main' && github.repository == 'kusl/myweather' }}
        uses: softprops/action-gh-release@v3
        with:
          tag_name: v1.0.${{ github.run_number }}
          name: Release v1.0.${{ github.run_number }}
          draft: false
          prerelease: false
          generate_release_notes: true
          make_latest: true
          body: |
            Automated release built from the latest commit on main`.
                        ## MyWeather v1.0.${{ github.run_number }}

            ### Downloads

            | Platform | File |
            |----------|------|
            | Android | MyWeather-${{ github.run_number }}.apk |
            ### Android Users (Obtainium)

            Point Obtainium to this repository's releases for automatic updates.
            The APK version code increments with each release.
            Commit: ${{ github.sha }}
          files: app/build/outputs/apk/release/*.apk
```

Show less
12:57 AM

Diagnosed root cause: hardcoded versionCode blocking Obtainium updates
Diagnosed root cause: hardcoded versionCode blocking Obtainium updates
Looking at Kushal's GitHub Actions workflow for the myweather Android app, I can see he's currently using a rolling pre-release model that updates the same GitHub Release on every push to main, which breaks Obtainium's update detection. He wants to switch to a versioning system where each build generates uniquely named artifacts with incrementing version numbers instead. d have a unique, incrementing filename so Obtainium can properly detect each new version. The problem is that the APK filename in the workflow is likely static (something like app-release.apk), which means every release would have the same filename even though the version code inside the APK changes. Obtainium needs either the filename or the version metadata to differ between releases to recognize updates properly. The real fix needs to address both the filename mismatch and the versionCode inside the APK — if versionCode stays hardcoded and never increments, Obtainium won't recognize new releases as updates. I need to check the current build.gradle.kts setup to see how versionCode is configured, since the solution likely requires changes to both the workflow and the build configuration. The real issue is how Obtainium detects version updates from GitHub releases. It reads the release list and uses the tag name as the version identifier, then compares against what's installed to determine if an update is available. With a rolling pre-release model, there are two failure modes: if the release is marked as a pre-release and Obtainium filters those out, it won't detect any updates, and if the tag gets reused (like always using "nightly"), Obtainium sees the same version repeatedly and never triggers an update.

Beyond the workflow fix with unique incrementing tags and proper release settings, the critical piece is ensuring the APK's versionCode actually increments with each build—otherwise Android itself will reject the installation as a downgrade even if Obtainium successfully downloads it. The versionCode needs to be tied to the same build number driving the release tags so everything stays in sync. I need to search the project knowledge for the build configuration and workflow files to understand how versionCode is currently set and how the APK naming works, then I can determine if I need to modify both the workflow YAML and build.gradle.kts or just the workflow. The core issue is that Obtainium needs both a new, distinct release tag to recognize an update exists, and Android requires the versionCode integer to increment for the APK to install as an upgrade. A rolling pre-release with a reused tag fails the first requirement, and a static versionCode fails the second — so I need to ensure both change with each build.

The user's YAML approach handles the tag uniqueness with v1.0.${{ github.run_number }}, but unless build.gradle.kts derives versionCode from the run number or another incrementing value, Android will still reject the update because the versionCode stays constant. That's likely the real blocker. Additionally, the APK filename is currently static (app-release.apk), so I need to verify whether that needs to be unique per release as well. Obtainium uses the release tag as the version string, so a tag like v1.0.42 becomes version "1.0.42". To avoid version mismatch issues, I should make sure the versionName in the build config matches the tag's version number. The fix involves setting versionCode and versionName from an environment variable that the CI workflow passes—specifically the GitHub run number—with a sensible default for local builds, then updating the workflow to pass that value through.

For the APK filename, I need to either configure Gradle's archivesName or use the applicationVariants API to rename the output with a versioned filename like MyWeather-v1.0.<run_number>.apk, and update the release notes to reference the actual filename.

The challenge is that AGP 8+ deprecates applicationVariants in favor of the new androidComponents API, which is more complex for output renaming. The simpler, more pragmatic approach is to just rename the APK in the CI workflow step itself using mv or cp before uploading — this keeps the Gradle build clean and avoids deprecated APIs entirely.

My plan is to pass versionCode and versionName to Gradle via project properties, update build.gradle.kts to read those with sensible defaults, then in the workflow after assembleRelease, copy the APK to a versioned filename like MyWeather-v1.0.<run_number>.apk before uploading. Now I need to check the current build.gradle.kts to see the existing version values, the applicationId and namespace, and how the signing config reads the keystore environment variables. I should verify the actual build.gradle.kts file from the dump to ensure accuracy rather than risk hallucinating the version configuration structure. Let me search the project knowledge first as instructed, then cross-check against the dump file using bash to get the exact code I need for the versionCode and versionName injection logic.

Show less

Inspecting the repo dump structure and listing tracked files

Script
I'm noticing the dump file uses a different marker format than expected, so I need to examine how it's structured.


Viewing the dump header to understand its format

Script
I'm identifying the file marker format to catalog all the files.


Listing all files in the dump by their FILE markers

Script
I need to examine the key configuration files to understand the current setup — the app's build configuration for version and signing details, the GitHub Actions workflow that handles APK building, and the dependency versions file for context.


Reading app/build.gradle.kts to inspect versionCode, versionName, and signing config

Script
Looking at the build configuration, I found the core issue: both versionCode and versionName are hardcoded to 1 and "1.0.0" respectively, which means every release appears identical to Android and Obtainium—they never recognize new versions as upgrades. The signing configuration properly reads from environment variables, so I'll preserve that while fixing the version problem.

Now checking the build_apk.yml workflow to see how the build is currently triggered and where I need to inject dynamic versioning.


Reading the current build_apk.yml and ci.yml workflows

Script
So the current committed workflow uses the rolling pre-release model with tag_name: rolling and prerelease: true, but Kushal's draft YAML replaces that with versioned tags like v1.0.${{ github.run_number }} and sets prerelease: false — that's the new approach he's proposing.

The critical problem is that versionCode is hardcoded to 1 in build.gradle.kts, so Android sees every APK as the same version regardless of the filename or git tag. This breaks the whole upgrade chain — Obtainium won't recognize new builds as updates because the internal version numbers never change. I need to make versionCode (and versionName) derive from github.run_number so each build actually increments. That's what "unique and incrementing files" really means — the version inside the APK has to increment, not just the release artifact name.

The APK filename is also static (app-release.apk) when it should be versioned to match what the release advertises. I'll rename it in a workflow step to avoid deprecated Gradle APIs. For Obtainium to work reliably, I need the versionCode strictly increasing, the tag monotonically increasing (his v1.0.<run_number> approach is good), and ideally the versionName matching the tag so everything stays in sync. I'm also checking the softprops/action-gh-release version to make sure it's current. working successfully in their environment, so I should keep it as-is rather than risk breaking something that's already functioning. Let me verify this by checking the actual latest release tag for that action to confirm @v3 is valid. All the action versions are already proven to work in their current pipeline, so I'll keep them exactly as they are. I should quickly verify that action-gh-release@v3 supports the inputs they're using like make_latest, generate_release_notes, tag_name, and files — these are standard parameters for that action, so I'm confident they'll work.

Now I need to figure out how to pass the version code and version name from the CI environment into Gradle. I'll read environment variables like MYWEATHER_VERSION_CODE and MYWEATHER_VERSION_NAME in the build.gradle.kts file, falling back to the hardcoded defaults if they're not set — this matches the existing pattern where the signing configuration already uses System.getenv(). I'll define helper functions that read environment variables with sensible defaults, keeping it consistent with the existing signing pattern in the file rather than introducing Gradle's provider API.

Writing version code setup... I'll define these version variables as local vals right before the android block so they're computed once and reused throughout. The workflow will set MYWEATHER_VERSION_CODE from the GitHub run number and MYWEATHER_VERSION_NAME as 1.0.<run_number>, ensuring the version tag, APK filename, and build metadata all stay in sync. After the release build completes, I'll rename the APK to include the version number and upload that versioned artifact. I'll use a shell script to robustly find the APK in the release directory and copy it to a versioned filename in a staging folder, then export the path as an environment variable for later steps. GitHub will substitute the run number before executing the shell, so there's no injection risk, though I could also pass it via env for stricter best practices.

Actually, the cleanest approach is to define the version variables at the job level so all steps can access them consistently. I'll set VERSION_CODE and VERSION_NAME in the job's env block using the GitHub context, which is available at that level, then reference those variables in the assemble step's environment. Setting up the version variables at the job level as a single source of truth, so the version code and name stay consistent across the Gradle build, git tagging, APK naming, and release titles. The Assemble step will inherit these automatically, and while it adds signing secrets through its own env block, the step-level variables merge with the job-level ones without needing duplication.

Now I'm renaming the step to "Stage versioned APK" and writing a shell script that creates the distribution directory and locates the built APK...

Finishing the script...

I'm deciding between using a glob pattern for the files field versus the exact filename, and settling on the glob approach since it's simpler and more robust. I'll also make sure both the environment variable and Gradle get the version name so the APK's internal versionName matches what Obtainium can read for version comparison.

Now I need to verify my understanding of how Obtainium detects versions — it typically uses the release tag by default unless custom regex or APK-based detection is configured, and the versionCode requirement is a separate Android-level constraint that applies regardless of Obtainium's settings. The user's actual problem is fixing the CI workflow itself, which is concrete and code-based.

The key fix I'm recommending — monotonic versionCode increments, unique release tags, non-prerelease status, proper make_latest handling, and versioned APK filenames — addresses both Android's upgrade requirements and Obtainium's release detection, so it's sound regardless of Obtainium's exact version-string parsing. I should explain the reasoning clearly without overstating what I know about Obtainium's internals.

One detail worth clarifying: with the old rolling-tag model, Obtainium users often had to enable "Use release date as version" since the tag never changed, but that workaround becomes unnecessary with the new versioned release approach. I'll stick with @v3 since the current workflow already uses it successfully, and the draft does too—that's the safest path forward. I should also verify that make_latest accepts the boolean true value in YAML, though the user's draft already uses it that way, so it should work fine. Let me do a quick check on the action to be certain.

Show less

Grounding: confirm softprops/action-gh-release @v3 exists and find its latest release tag

Script
The curl request didn't return anything useful, so I'm switching to the api.github.com endpoint which should be allowed. I'll retry the request and make sure to capture the response headers and status code to debug what's happening.


Retry GitHub tags API with explicit Accept header and status code

Script
The rate limiting doesn't matter since both workflows are already using the same action version, so I'll keep @v3 as-is. From the repo I can see the build configuration has hardcoded versionCode and versionName values, and the signing setup references environment variables.

Now I need to generate the two corrected files: the workflow file and the Gradle build file. The key fix is injecting the versionCode from CI into the build.gradle.kts — I'll add a small block that derives the version values before the android block, then modify only the version lines while keeping everything else exactly as it was. Writing the signing resolution function... Writing build configuration... Setting up signing configurations for release builds and configuring build types with minification enabled for release while keeping debug builds unminified. Configuring build features, packaging, and test options... Adding a comment block that explains the per-build version code injection from CI, with a fallback for local builds...

I'm setting up two top-level variables that read environment variables for version code and name, defaulting to stable values if they're not present, then updating the defaultConfig to use these injected values instead of hardcoded ones. There's a quick check that top-level val declarations are valid in Kotlin DSL build scripts.

Now I'm designing the GitHub Actions workflow, keeping the existing structure with the main branch trigger and workflow dispatch, maintaining the read permissions and job-level write access, and setting up concurrency to prevent simultaneous builds. The key addition is environment variables at the job level that set the version code to the run number and construct a semantic version name from it.

For the steps, I'm keeping the standard checkout, Java setup, and Gradle configuration, then the keystore decode and assemble tasks which will inherit those version variables. After the build succeeds, I need to stage the versioned APK to a predictable location, upload it as an artifact, and finally create a GitHub release pointing to that specific versioned file instead of using a rolling release tag.

There's a consideration around cancel-in-progress — with unique releases per push, canceling older runs is acceptable since each completed push gets its own release with its own run number, and skipping run numbers doesn't break monotonicity as long as they're strictly increasing.

Writing the APK staging step... Writing release config... The version tag checks out since MYWEATHER_VERSION_NAME is set to 1.0.${run_number}, matching the original approach. I'm verifying that the env context variables are properly accessible in workflow expressions like with and if conditions — job-level environment variables should be available there through the env context.

I think using a job-level environment variable as a single source of truth is cleaner than repeating ${{ github.run_number }} throughout the workflow. It's more maintainable and the user will likely appreciate the DRY approach, so I'll stick with referencing env.MYWEATHER_VERSION_NAME everywhere.

For the keystore decoding step, the current approach works fine in their existing pipeline — it checks if the secret is configured, then decodes the base64 string and writes it to a file while setting an environment variable pointing to its location. I'll keep that as-is.

The Assemble step passes the keystore credentials as environment variables to the Gradle build command, which will use them during the release build process. I should update the workflow's header comment to reflect the new immutable per-build release model instead of the old rolling pre-release description. I'm also verifying that make_latest: true with unique tags per build correctly sets the GitHub "Latest" badge — softprops passes this to the GitHub API, so each new release becomes the repository's latest, which aligns with how Obtainium's GitHub source picks releases.

For Obtainium users, I need to note that with the old model they had to enable "Include prereleases" in the GitHub source settings, but now with prerelease: false and make_latest: true, the default setting (prereleases off) will work without changes. I should also mention that existing installs tracking the old rolling tag should upgrade automatically when they detect the higher versionCode from the new versioned releases, though the user might want to delete the old rolling release and tag afterward to avoid confusion.

On the upgrade path itself: devices currently have versionCode 1 from the rolling builds, and the next CI release will jump to versionCode matching the run number (like 43), which is definitely higher and will trigger an update. The critical thing is that the APK must be signed with the same key users already have installed—if the keystore regeneration means a different signing key was used, users with the old APK would need to uninstall and reinstall. I'll add a note about this being a one-time consequence of the keystore regeneration, not something caused by this versioning change itself.

Now I'll write out the full files for them to drop into their repo. messy. I should prioritize what serves them best: full files as downloadable artifacts with a brief inline preview or summary, since they need to integrate these into their repo and their memory emphasizes complete file outputs. I'm settling on showing the full files inline in code blocks since that matches their explicit request and established workflow of copying content directly into their editor. Checking the environment variable fallback logic and version code limits — the approach is safe for CI and local development, though there's a minor risk if a developer has a stale env var exported locally, which is unlikely and harmless anyway. The real concern is Gradle's configuration cache: reading environment variables at configuration time isn't tracked, so the cache might use a stale value across runs if caching is enabled.

However, the existing resolveReleaseSigning() already uses System.getenv() at configuration time, so this approach is consistent with what's already there. While providers.environmentVariable() would be more correct for config cache, mixing styles would be inconsistent with the project's current pattern. If they enable config cache later and hit staleness issues, they can migrate both to providers, but that's not a regression today.

I should verify whether config cache is actually enabled in gradle.properties to make sure I'm not introducing a problem.

Show less

Checking gradle.properties for configuration-cache and other Gradle flags before adding env reads

Script
The configuration cache being enabled means that environment variables read at configuration time become undeclared inputs, so Gradle will cache the configuration with those values and reuse it on subsequent builds without re-evaluating them — which could cause stale values if the environment changes. than reading the entire environment map, since only that one variable becomes a cache input. So using the keyed version of System.getenv() is more cache-friendly for their build.

The catch is that on CI, each run gets a different MYWEATHER_VERSION_CODE value, which means the config cache gets invalidated and reconfigured each time — exactly what we need for the new versionCode to take effect. Though I should verify whether the configuration cache is even persisted across CI runs, since it lives in the project's .gradle/configuration-cache directory rather than in Gradle User Home where gradle/actions/setup-gradle typically caches things.

Actually, even if the config cache were somehow persisted, Gradle tracks environment variable reads as inputs and would invalidate the cache when the value changes. So using System.getenv() is safe and correct here — it's consistent with the existing code and plays nicely with the configuration cache. No special handling needed. I'll stick with System.getenv() to match the existing code style and keep things simple—Gradle's configuration cache does track environment variable access at configuration time, so there's no downside to this approach.

Since the CI environment creates a fresh workspace each run and doesn't persist the project's .gradle/configuration-cache directory, the configuration cache gets rebuilt anyway, so there's no concern about stale cache entries between runs.

The user specifically asked me to fix the pasted YAML and return the full corrected file—their version represents the intended state with the bugs I'm addressing. I'll generate a clean, corrected workflow that implements their goal of immutable versioned releases rather than rolling pre-releases, and includes the missing version injection step.

Since the hardcoded versionCode in build.gradle.kts is the root cause preventing Obtainium from working, I need to provide that file too—this isn't scope creep but the essential fix. I'll explain why the build.gradle.kts change is mandatory, then present both files in full. I'll structure the response to highlight the core issue: the hardcoded versionCode = 1 was preventing Obtainium from detecting new releases, so I need to update both the workflow and gradle file to increment the version code dynamically. Then I'll walk through what changed and why it solves the problem, plus any migration steps they should know about.

Writing the workflow and gradle files...

Show less
Done
Good news indeed. Let me check one thing in your Gradle config that turns out to be the actual cause of the Obtainium problem, then write both files.


Identified hardcoded version values as root cause, preparing corrected files
Identified hardcoded version values as root cause, preparing corrected files

Confirming versionCode/versionName are hardcoded in the project

Script
I've found the issue—both versionCode and versionName are hardcoded in the app/build.gradle.kts file, so I need to update them there and create the necessary configuration files to manage versions dynamically going forward.

Done
Confirmed the real issue. Let me write both files and validate the YAML.


Verifying file modifications against original content
Verifying file modifications against original content

Corrected build_apk.yml: immutable versioned releases, monotonic version injection, versioned APK filename
build_apk.yml

Validating the workflow YAML parses and has the expected structure

Script
Now I'm moving on to the Gradle build file, keeping the original structure intact while applying those same minimal adjustments and adding the version derivation logic.

Done
YAML parses cleanly. Now the Gradle file (this is where the actual fix lives):


Validated file integrity and orchestrated dual-file delivery strategy
Validated file integrity and orchestrated dual-file delivery strategy

app/build.gradle.kts with CI-driven monotonic versionCode/versionName; everything else unchanged
build.gradle.kts
I'm comparing the original build.gradle.kts file from the dump against my modified version to verify I only changed the version block and those two specific version lines.


Diffing my new build.gradle.kts against the original from the dump to confirm surgical changes only

Script
I'm looking at a diff that shows some import statements appearing as additions, but I think that's just an artifact of how I extracted the original file — those imports were actually already there at the top. Let me trace through what the diff is actually showing versus what was in the original content.

The real changes are the version-derivation block I added and the two lines where I switched from hardcoded version values to using the CI variables. The import differences and trailing blank line are just noise from the extraction window being slightly misaligned. I should verify the imports are intact in my actual file to be certain.


Confirming the import lines are intact in my file and the diff's import delta was an extraction offset

Script
The imports in my file match the original exactly—the diff artifact came from where my awk window started. The actual changes to build.gradle.kts are just adding the version-derivation logic and updating the two version lines to reference it, with everything else remaining identical.

Now I'll present both files inline in code blocks as requested, and also save them to outputs so the user has download-safe copies that preserve YAML formatting. I want to keep the explanation brief though, since I'll be presenting the files directly. I need to nail down the Obtainium explanation without overstating it — the core issue is that the old rolling release model with prerelease tags meant Obtainium's default settings (which skip prereleases) wouldn't detect updates, and even if users enabled prerelease detection, the versionCode wasn't monotonically increasing so Android would reject the install. The new approach fixes both: unique, incrementing tags marked as releases plus a monotonic versionCode means Obtainium sees the update and Android accepts it.

For the migration itself, I should flag three practical points: cleaning up the old rolling release tag is optional but keeps things tidy; the first new release's versionCode will be the run number (which should exceed the current 1), so existing installs will upgrade; and the signing certificate must match what users already have, so if anyone installed with an old key before the keystore regeneration, they'll need to uninstall and reinstall that one time, but going forward it's seamless as long as the key stays stable.

There's also a workflow-specific caveat with github.run_number — it increments per run of this particular workflow and won't reset unless the workflow file itself is deleted and recreated, which would break the monotonic versionCode sequence. So I need to be careful not to rename or recreate the workflow file, since that could reset the counter and cause issues with version progression.

Now I'm checking the markdown table formatting for the YAML body...

Show less
Done
Both files validated. The fix is two files, because the rolling tag was only half the problem.

What was actually breaking Obtainium
Your draft already does the right thing on the release side (unique v1.0.<run_number> tag, prerelease: false, make_latest: true). But the real blocker is in app/build.gradle.kts:

kotlin
versionCode = 1
versionName = "1.0.0"
Every APK you've ever published carries versionCode 1. Android refuses to install an APK as an upgrade unless its versionCode is strictly higher than what's installed — so even when Obtainium correctly detects a new release and downloads it, the install is a no-op. Unique tags and unique filenames don't help if the integer inside the APK never moves. So the version has to be injected from CI and increment per build, which means build.gradle.kts changes too.

The approach: the workflow's github.run_number becomes the single source of truth, flowing into versionCode (= run number), versionName (= 1.0.<run_number>), the git tag, and the APK filename, so they all agree.

.github/workflows/build_apk.yml
yaml
# Builds a signed release APK on every push to main and publishes it as an
# immutable, monotonically-versioned GitHub Release (one release per build).
#
# This replaces the old single "rolling" pre-release. Each build now produces:
#   • a unique, increasing git tag         v1.0.<run_number>
#   • an APK whose versionCode == <run_number> and versionName == 1.0.<run_number>
#   • a uniquely-named asset                MyWeather-v1.0.<run_number>.apk
# A strictly-increasing versionCode is what lets Android — and update managers
# like Obtainium — recognise each release as a genuine upgrade. The release is a
# normal (non-pre-) release and is marked "latest", so Obtainium picks it up with
# its default settings (no "include pre-releases" workaround needed).
#
# Signing: if the repository defines the signing secrets below, the APK is
# signed with the real release key. If not (e.g. on a fork), app/build.gradle.kts
# transparently falls back to the debug signing config so the build still
# succeeds — the publish step is just skipped for forks.
#
# Required repository secrets for a properly-signed build:
#   KEYSTORE_BASE64            base64 of your release .jks/.keystore
#   MYWEATHER_KEYSTORE_PASSWORD
#   MYWEATHER_KEY_ALIAS
#   MYWEATHER_KEY_PASSWORD
name: Build APK

on:
  push:
    branches: [main]
  workflow_dispatch:

# Least privilege by default; the build job opts up to contents:write so it can
# publish the release.
permissions:
  contents: read

concurrency:
  group: build-apk-${{ github.ref }}
  cancel-in-progress: true

jobs:
  build:
    name: Assemble release APK
    runs-on: ubuntu-latest
    timeout-minutes: 30
    permissions:
      contents: write

    # Single source of truth for this build's version, derived from the monotonic
    # GitHub Actions run number. Reused for the Gradle versionCode/versionName
    # (read via System.getenv in app/build.gradle.kts), the git tag, the APK
    # filename, and the release title — so they all agree. Job-level env is
    # inherited by every step below.
    env:
      MYWEATHER_VERSION_CODE: ${{ github.run_number }}
      MYWEATHER_VERSION_NAME: 1.0.${{ github.run_number }}

    steps:
      - name: Checkout
        uses: actions/checkout@v7

      - name: Set up JDK 17
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '17'

      - name: Set up Gradle
        uses: gradle/actions/setup-gradle@v6

      - name: Decode signing keystore (if configured)
        env:
          KEYSTORE_BASE64: ${{ secrets.KEYSTORE_BASE64 }}
        if: ${{ env.KEYSTORE_BASE64 != '' }}
        run: |
          echo "$KEYSTORE_BASE64" | base64 --decode > "${{ github.workspace }}/release.keystore"
          echo "MYWEATHER_KEYSTORE_PATH=${{ github.workspace }}/release.keystore" >> "$GITHUB_ENV"

      - name: Assemble release
        # MYWEATHER_VERSION_CODE / MYWEATHER_VERSION_NAME are inherited from the
        # job-level env above and read by app/build.gradle.kts. The signing
        # secrets below are empty on forks/unsecured runs; the Gradle script then
        # falls back to debug signing so the build still produces an APK.
        env:
          MYWEATHER_KEYSTORE_PASSWORD: ${{ secrets.MYWEATHER_KEYSTORE_PASSWORD }}
          MYWEATHER_KEY_ALIAS: ${{ secrets.MYWEATHER_KEY_ALIAS }}
          MYWEATHER_KEY_PASSWORD: ${{ secrets.MYWEATHER_KEY_PASSWORD }}
        run: ./gradlew :app:assembleRelease --stacktrace

      - name: Stage versioned APK
        # Copy whatever single APK Gradle produced to a unique, version-stamped
        # filename so each release ships a distinct, incrementing asset.
        run: |
          set -euo pipefail
          mkdir -p build/dist
          apk="$(find app/build/outputs/apk/release -maxdepth 1 -name '*.apk' | head -n1)"
          if [ -z "$apk" ]; then
            echo "::error::No release APK found under app/build/outputs/apk/release"
            exit 1
          fi
          dest="build/dist/MyWeather-v${MYWEATHER_VERSION_NAME}.apk"
          cp "$apk" "$dest"
          echo "Staged: $dest"
          ls -l build/dist

      - name: Upload APK artifact
        uses: actions/upload-artifact@v7
        with:
          name: myweather-release-apk
          path: build/dist/*.apk
          if-no-files-found: error

      - name: Publish GitHub Release
        # Only from the canonical repo on a real push to main — never from forks
        # or pull requests (which also lack the signing secrets).
        if: ${{ github.event_name == 'push' && github.ref == 'refs/heads/main' && github.repository == 'kusl/myweather' }}
        uses: softprops/action-gh-release@v3
        with:
          tag_name: v${{ env.MYWEATHER_VERSION_NAME }}
          name: MyWeather v${{ env.MYWEATHER_VERSION_NAME }}
          draft: false
          prerelease: false
          generate_release_notes: true
          make_latest: true
          body: |
            Automated release built from the latest commit on `main`.

            ## MyWeather v${{ env.MYWEATHER_VERSION_NAME }}

            **versionCode** `${{ env.MYWEATHER_VERSION_CODE }}` · **versionName** `${{ env.MYWEATHER_VERSION_NAME }}`

            ### Download

            | Platform | File |
            |----------|------|
            | Android | `MyWeather-v${{ env.MYWEATHER_VERSION_NAME }}.apk` |

            ### Android users (Obtainium)

            Point Obtainium at this repository's releases for automatic updates.
            Every release ships a strictly higher `versionCode`, so updates install cleanly.

            Commit: ${{ github.sha }}
          files: build/dist/*.apk
app/build.gradle.kts
The only changes from your current file are the version-derivation block and the two versionCode/versionName lines; everything else is byte-for-byte identical (I diffed it against your dump to be sure). Locally — with no env vars — it still falls back to 1 / 1.0.0, so assembleRelease works offline exactly as before.

kotlin
import com.android.build.api.dsl.ApkSigningConfig
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

/**
 * Resolve release-signing material from (in priority order):
 *   1. Environment variables  (used by CI — see .github/workflows/build_apk.yml)
 *   2. keystore.properties     (used for local release builds; git-ignored)
 *
 * If neither is present we return null and the release variant falls back to the
 * debug signing config, so the project still assembles for forks/contributors
 * who don't have the secrets. Nothing here ever commits a secret.
 */
fun resolveReleaseSigning(): Map<String, String>? {
    val env = System.getenv()
    if (env["MYWEATHER_KEYSTORE_PATH"]?.isNotBlank() == true) {
        return mapOf(
            "storeFile" to env.getValue("MYWEATHER_KEYSTORE_PATH"),
            "storePassword" to (env["MYWEATHER_KEYSTORE_PASSWORD"] ?: ""),
            "keyAlias" to (env["MYWEATHER_KEY_ALIAS"] ?: ""),
            "keyPassword" to (env["MYWEATHER_KEY_PASSWORD"] ?: ""),
        )
    }
    val propsFile = rootProject.file("keystore.properties")
    if (propsFile.exists()) {
        val props = Properties().apply { propsFile.inputStream().use { load(it) } }
        val storeFile = props.getProperty("storeFile").orEmpty()
        if (storeFile.isNotBlank()) {
            return mapOf(
                "storeFile" to storeFile,
                "storePassword" to props.getProperty("storePassword", ""),
                "keyAlias" to props.getProperty("keyAlias", ""),
                "keyPassword" to props.getProperty("keyPassword", ""),
            )
        }
    }
    return null
}

/**
 * Per-build version, normally injected by CI (the GitHub Actions run number —
 * see .github/workflows/build_apk.yml). A strictly-increasing versionCode is what
 * makes Android — and update managers like Obtainium — treat each published APK
 * as a genuine upgrade over the one already installed. Local builds have no such
 * env var and fall back to a stable default, so `assembleRelease` still works
 * offline. (Reading a named env var is tracked by Gradle's configuration cache,
 * so a new run_number correctly invalidates and re-evaluates the version.)
 */
val ciVersionCode: Int = System.getenv("MYWEATHER_VERSION_CODE")?.toIntOrNull() ?: 1
val ciVersionName: String =
    System.getenv("MYWEATHER_VERSION_NAME")?.takeIf { it.isNotBlank() } ?: "1.0.0"

android {
    // Code package for the generated R and BuildConfig classes. This is internal
    // and never user-visible, so it stays as-is: every `import com.kusl.myweather.*`
    // (including R and BuildConfig) and the ProGuard keep-rules depend on it.
    // It is intentionally NOT the same as applicationId below — AGP allows them to
    // differ, and renaming it would mean rewriting every package declaration.
    namespace = "com.kusl.myweather"

    // Compile against API 37 — required by androidx.lifecycle 2.11.0, whose AAR
    // metadata mandates compileSdk >= 37 — while still targeting the requested
    // API 35 and supporting Android 14 (API 34) as the floor.
    compileSdk = 37

    defaultConfig {
        // Installable / store-facing application identity. Deliberately distinct
        // from `namespace` above: the app is published under this GitHub-org-style
        // id. Debug builds append ".debug" (see below) so a debug and a release
        // build can be installed side by side on the same device.
        applicationId = "com.github.kusl.myweather"
        minSdk = 34
        targetSdk = 35
        versionCode = ciVersionCode
        versionName = ciVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    val releaseSigning = resolveReleaseSigning()
    signingConfigs {
        if (releaseSigning != null) {
            create("release") {
                storeFile = file(releaseSigning.getValue("storeFile"))
                storePassword = releaseSigning["storePassword"]
                keyAlias = releaseSigning["keyAlias"]
                keyPassword = releaseSigning["keyPassword"]
                enableV1Signing = true
                enableV2Signing = true
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            // Sign with the real release key when available; otherwise fall back
            // to the debug key so `assembleRelease` never fails for lack of secrets.
            signingConfig = signingConfigs.findByName("release")
                ?: signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            // Let JVM unit tests touch android.jar stubs (e.g. android.util.Log)
            // without throwing "not mocked" — they no-op and return defaults.
            isReturnDefaultValues = true
        }
    }

    // Room schema export — handy for migration diffing and committed under app/schemas.
    ksp { arg("room.schemaLocation", "$projectDir/schemas") }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // Core + lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)

    // Compose (BOM-managed)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Settings storage
    implementation(libs.androidx.datastore.preferences)

    // Room (KSP is the only annotation processor in the project)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Networking: Retrofit + OkHttp + kotlinx.serialization
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    // Unit tests (JVM — run in CI without an emulator)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    // Instrumented tests (device/emulator)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
A few migration notes
Delete the old rolling release and its tag on GitHub after the first new release lands, otherwise it lingers as a stale pre-release. Your existing Obtainium installs (versionCode 1) will then see the next release — versionCode = run number, well above 1 — and upgrade cleanly.
Don't rename or recreate this workflow file. github.run_number is counted per workflow; recreating it resets the counter to 1, which would push a lower versionCode and break updates. Keep the path .github/workflows/build_apk.yml stable.
Signing-key continuity matters. Android only accepts an update if the new APK is signed with the same certificate as the installed one. Since you regenerated the keystore recently, anyone who installed an APK signed with the old key has to uninstall once before the new releases will update over it; after that it's seamless as long as the key stays put.
The CI workflow's assembleRelease already passed for you, and the local fallback keeps versionCode = 1 / versionName = "1.0.0" so nothing changes for local builds — the version only moves when the workflow sets those two env vars.


Claude Fable 5 is currently unavailable.
Learn more(opens in new tab)





Claude is AI and can make mistakes. Please double-check responses.





46
05
















