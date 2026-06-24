29
67

Assemble release APK
failed 10 minutes ago in 1m 15s 
Build, unit-test, lint
failed 9 minutes ago in 56s 

2026-06-24T23:00:07.9160421Z Current runner version: '2.335.1'
2026-06-24T23:00:07.9186531Z ##[group]Runner Image Provisioner
2026-06-24T23:00:07.9187445Z Hosted Compute Agent
2026-06-24T23:00:07.9188127Z Version: 20260611.554
2026-06-24T23:00:07.9188809Z Commit: 5e0782fdc9014723d3be820dd114dd31555c2bd1
2026-06-24T23:00:07.9189580Z Build Date: 2026-06-11T21:40:46Z
2026-06-24T23:00:07.9190356Z Worker ID: {ace7084e-bb6d-4c3f-b51d-1c0feb24a376}
2026-06-24T23:00:07.9191132Z Azure Region: westus
2026-06-24T23:00:07.9191796Z ##[endgroup]
2026-06-24T23:00:07.9193262Z ##[group]Operating System
2026-06-24T23:00:07.9193901Z Ubuntu
2026-06-24T23:00:07.9194530Z 24.04.4
2026-06-24T23:00:07.9195490Z LTS
2026-06-24T23:00:07.9196250Z ##[endgroup]
2026-06-24T23:00:07.9196844Z ##[group]Runner Image
2026-06-24T23:00:07.9197465Z Image: ubuntu-24.04
2026-06-24T23:00:07.9198192Z Version: 20260622.220.1
2026-06-24T23:00:07.9199590Z Included Software: https://github.com/actions/runner-images/blob/ubuntu24/20260622.220/images/ubuntu/Ubuntu2404-Readme.md
2026-06-24T23:00:07.9201225Z Image Release: https://github.com/actions/runner-images/releases/tag/ubuntu24%2F20260622.220
2026-06-24T23:00:07.9202321Z ##[endgroup]
2026-06-24T23:00:07.9203511Z ##[group]GITHUB_TOKEN Permissions
2026-06-24T23:00:07.9205814Z Contents: write
2026-06-24T23:00:07.9206462Z Metadata: read
2026-06-24T23:00:07.9207013Z ##[endgroup]
2026-06-24T23:00:07.9209315Z Secret source: Actions
2026-06-24T23:00:07.9210084Z Prepare workflow directory
2026-06-24T23:00:07.9774262Z Prepare all required actions
2026-06-24T23:00:07.9812541Z Getting action download info
2026-06-24T23:00:08.3381475Z Download action repository 'actions/checkout@v7' (SHA:9c091bb21b7c1c1d1991bb908d89e4e9dddfe3e0)
2026-06-24T23:00:08.4567021Z Download action repository 'actions/setup-java@v5' (SHA:ad2b38190b15e4d6bdf0c97fb4fca8412226d287)
2026-06-24T23:00:09.0231398Z Download action repository 'gradle/actions@v6' (SHA:3f131e8634966bd73d06cc69884922b02e6faf92)
2026-06-24T23:00:10.0728375Z Download action repository 'actions/upload-artifact@v7' (SHA:043fb46d1a93c77aae656e7c1c64a875d1fc6a0a)
2026-06-24T23:00:10.1949696Z Download action repository 'softprops/action-gh-release@v3' (SHA:718ea10b132b3b2eba29c1007bb80653f286566b)
2026-06-24T23:00:10.5769962Z Complete job name: Assemble release APK
2026-06-24T23:00:10.6480156Z ##[group]Run actions/checkout@v7
2026-06-24T23:00:10.6480766Z with:
2026-06-24T23:00:10.6481082Z   repository: kusl/***
2026-06-24T23:00:10.6484205Z   token: ***
2026-06-24T23:00:10.6484434Z   ssh-strict: true
2026-06-24T23:00:10.6484644Z   ssh-user: git
2026-06-24T23:00:10.6484865Z   persist-credentials: true
2026-06-24T23:00:10.6485364Z   clean: true
2026-06-24T23:00:10.6485607Z   sparse-checkout-cone-mode: true
2026-06-24T23:00:10.6485889Z   fetch-depth: 1
2026-06-24T23:00:10.6486104Z   fetch-tags: false
2026-06-24T23:00:10.6486334Z   show-progress: true
2026-06-24T23:00:10.6486558Z   lfs: false
2026-06-24T23:00:10.6486755Z   submodules: false
2026-06-24T23:00:10.6486975Z   set-safe-directory: true
2026-06-24T23:00:10.6487243Z   allow-unsafe-pr-checkout: false
2026-06-24T23:00:10.6487751Z env:
2026-06-24T23:00:10.6487996Z   MYWEATHER_VERSION_CODE: 21
2026-06-24T23:00:10.6488249Z   MYWEATHER_VERSION_NAME: 1.0.21
2026-06-24T23:00:10.6488494Z ##[endgroup]
2026-06-24T23:00:10.7478313Z Syncing repository: kusl/***
2026-06-24T23:00:10.7479769Z ##[group]Getting Git version info
2026-06-24T23:00:10.7480258Z Working directory is '/home/runner/work/***/***'
2026-06-24T23:00:10.7480795Z [command]/usr/bin/git version
2026-06-24T23:00:10.7575588Z git version 2.54.0
2026-06-24T23:00:10.7597857Z ##[endgroup]
2026-06-24T23:00:10.7609819Z Temporarily overriding HOME='/home/runner/work/_temp/d4458fea-39ef-4eac-90d1-993c8f6dab1a' before making global git config changes
2026-06-24T23:00:10.7610871Z Adding repository directory to the temporary git global config as a safe directory
2026-06-24T23:00:10.7616063Z [command]/usr/bin/git config --global --add safe.directory /home/runner/work/***/***
2026-06-24T23:00:10.7669617Z Deleting the contents of '/home/runner/work/***/***'
2026-06-24T23:00:10.7673021Z ##[group]Determining repository object format
2026-06-24T23:00:10.7674449Z ##[endgroup]
2026-06-24T23:00:10.7675531Z ##[group]Initializing the repository
2026-06-24T23:00:10.7679428Z [command]/usr/bin/git init /home/runner/work/***/***
2026-06-24T23:00:10.7819438Z hint: Using 'master' as the name for the initial branch. This default branch name
2026-06-24T23:00:10.7820478Z hint: will change to "main" in Git 3.0. To configure the initial branch name
2026-06-24T23:00:10.7821452Z hint: to use in all of your new repositories, which will suppress this warning,
2026-06-24T23:00:10.7822192Z hint: call:
2026-06-24T23:00:10.7822541Z hint:
2026-06-24T23:00:10.7822894Z hint: 	git config --global init.defaultBranch <name>
2026-06-24T23:00:10.7823290Z hint:
2026-06-24T23:00:10.7823658Z hint: Names commonly chosen instead of 'master' are 'main', 'trunk' and
2026-06-24T23:00:10.7824308Z hint: 'development'. The just-created branch can be renamed via this command:
2026-06-24T23:00:10.7824809Z hint:
2026-06-24T23:00:10.7825048Z hint: 	git branch -m <name>
2026-06-24T23:00:10.7825490Z hint:
2026-06-24T23:00:10.7825892Z hint: Disable this message with "git config set advice.defaultBranchName false"
2026-06-24T23:00:10.7828443Z Initialized empty Git repository in /home/runner/work/***/***/.git/
2026-06-24T23:00:10.7839905Z [command]/usr/bin/git remote add origin https://github.com/kusl/***
2026-06-24T23:00:10.7885349Z ##[endgroup]
2026-06-24T23:00:10.7886078Z ##[group]Disabling automatic garbage collection
2026-06-24T23:00:10.7890128Z [command]/usr/bin/git config --local gc.auto 0
2026-06-24T23:00:10.7921052Z ##[endgroup]
2026-06-24T23:00:10.7921710Z ##[group]Setting up auth
2026-06-24T23:00:10.7922190Z Removing SSH command configuration
2026-06-24T23:00:10.7930904Z [command]/usr/bin/git config --local --name-only --get-regexp core\.sshCommand
2026-06-24T23:00:10.7983365Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'core\.sshCommand' && git config --local --unset-all 'core.sshCommand' || :"
2026-06-24T23:00:10.8360476Z Removing HTTP extra header
2026-06-24T23:00:10.8367979Z [command]/usr/bin/git config --local --name-only --get-regexp http\.https\:\/\/github\.com\/\.extraheader
2026-06-24T23:00:10.8399238Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'http\.https\:\/\/github\.com\/\.extraheader' && git config --local --unset-all 'http.https://github.com/.extraheader' || :"
2026-06-24T23:00:10.8613104Z Removing includeIf entries pointing to credentials config files
2026-06-24T23:00:10.8619054Z [command]/usr/bin/git config --local --name-only --get-regexp ^includeIf\.gitdir:
2026-06-24T23:00:10.8650667Z [command]/usr/bin/git submodule foreach --recursive git config --local --show-origin --name-only --get-regexp remote.origin.url
2026-06-24T23:00:10.8879378Z [command]/usr/bin/git config --file /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config http.https://github.com/.extraheader AUTHORIZATION: basic ***
2026-06-24T23:00:10.8923917Z [command]/usr/bin/git config --local includeIf.gitdir:/home/runner/work/***/***/.git.path /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:00:10.8958413Z [command]/usr/bin/git config --local includeIf.gitdir:/home/runner/work/***/***/.git/worktrees/*.path /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:00:10.8995687Z [command]/usr/bin/git config --local includeIf.gitdir:/github/workspace/.git.path /github/runner_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:00:10.9027774Z [command]/usr/bin/git config --local includeIf.gitdir:/github/workspace/.git/worktrees/*.path /github/runner_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:00:10.9053915Z ##[endgroup]
2026-06-24T23:00:10.9054625Z ##[group]Fetching the repository
2026-06-24T23:00:10.9061939Z [command]/usr/bin/git -c protocol.version=2 fetch --no-tags --prune --no-recurse-submodules --depth=1 origin +9c64e7a569517b1fbc1e4054a0cd7191a289b6e9:refs/remotes/origin/main
2026-06-24T23:00:11.4264650Z From https://github.com/kusl/***
2026-06-24T23:00:11.4265952Z  * [new ref]         9c64e7a569517b1fbc1e4054a0cd7191a289b6e9 -> origin/main
2026-06-24T23:00:11.4311850Z [command]/usr/bin/git branch --list --remote origin/main
2026-06-24T23:00:11.4384744Z   origin/main
2026-06-24T23:00:11.4393485Z [command]/usr/bin/git rev-parse refs/remotes/origin/main
2026-06-24T23:00:11.4415910Z 9c64e7a569517b1fbc1e4054a0cd7191a289b6e9
2026-06-24T23:00:11.4420164Z ##[endgroup]
2026-06-24T23:00:11.4420829Z ##[group]Determining the checkout info
2026-06-24T23:00:11.4421598Z ##[endgroup]
2026-06-24T23:00:11.4425614Z [command]/usr/bin/git sparse-checkout disable
2026-06-24T23:00:11.4472006Z [command]/usr/bin/git config --local --unset-all extensions.worktreeConfig
2026-06-24T23:00:11.4498964Z ##[group]Checking out the ref
2026-06-24T23:00:11.4502654Z [command]/usr/bin/git checkout --progress --force -B main refs/remotes/origin/main
2026-06-24T23:00:11.4649058Z Switched to a new branch 'main'
2026-06-24T23:00:11.4651451Z branch 'main' set up to track 'origin/main'.
2026-06-24T23:00:11.4658273Z ##[endgroup]
2026-06-24T23:00:11.4701792Z [command]/usr/bin/git log -1 --format=%H
2026-06-24T23:00:11.4725301Z 9c64e7a569517b1fbc1e4054a0cd7191a289b6e9
2026-06-24T23:00:11.4985366Z ##[group]Run actions/setup-java@v5
2026-06-24T23:00:11.4985704Z with:
2026-06-24T23:00:11.4985926Z   distribution: temurin
2026-06-24T23:00:11.4986164Z   java-version: 17
2026-06-24T23:00:11.4986381Z   java-package: jdk
2026-06-24T23:00:11.4986646Z   check-latest: false
2026-06-24T23:00:11.4986865Z   server-id: github
2026-06-24T23:00:11.4987088Z   server-username: GITHUB_ACTOR
2026-06-24T23:00:11.4987350Z   server-password: GITHUB_TOKEN
2026-06-24T23:00:11.4987613Z   overwrite-settings: true
2026-06-24T23:00:11.4987851Z   job-status: success
2026-06-24T23:00:11.4990957Z   token: ***
2026-06-24T23:00:11.4991181Z env:
2026-06-24T23:00:11.4991384Z   MYWEATHER_VERSION_CODE: 21
2026-06-24T23:00:11.4991650Z   MYWEATHER_VERSION_NAME: 1.0.21
2026-06-24T23:00:11.4991907Z ##[endgroup]
2026-06-24T23:00:11.6476091Z ##[group]Installed distributions
2026-06-24T23:00:11.6660869Z Resolved Java 17.0.19+10 from tool-cache
2026-06-24T23:00:11.6661586Z Setting Java 17.0.19+10 as the default
2026-06-24T23:00:11.6679163Z Creating toolchains.xml for JDK version 17 from temurin
2026-06-24T23:00:11.6793420Z Writing to /home/runner/.m2/toolchains.xml
2026-06-24T23:00:11.6794143Z 
2026-06-24T23:00:11.6794651Z Java configuration:
2026-06-24T23:00:11.6795477Z   Distribution: temurin
2026-06-24T23:00:11.6796106Z   Version: 17.0.19+10
2026-06-24T23:00:11.6796834Z   Path: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:11.6797503Z 
2026-06-24T23:00:11.6798620Z ##[endgroup]
2026-06-24T23:00:11.6812306Z Creating settings.xml with server-id: github
2026-06-24T23:00:11.6814543Z Writing to /home/runner/.m2/settings.xml
2026-06-24T23:00:11.7052269Z ##[group]Run gradle/actions/setup-gradle@v6
2026-06-24T23:00:11.7052612Z with:
2026-06-24T23:00:11.7052837Z   cache-provider: enhanced
2026-06-24T23:00:11.7053098Z   cache-disabled: false
2026-06-24T23:00:11.7053342Z   cache-read-only: false
2026-06-24T23:00:11.7053588Z   cache-write-only: false
2026-06-24T23:00:11.7053845Z   cache-overwrite-existing: false
2026-06-24T23:00:11.7054118Z   cache-cleanup: on-success
2026-06-24T23:00:11.7054417Z   gradle-home-cache-includes: caches
notifications

2026-06-24T23:00:11.7054754Z   add-job-summary: always
2026-06-24T23:00:11.7055017Z   add-job-summary-as-pr-comment: never
2026-06-24T23:00:11.7055498Z   dependency-graph: disabled
2026-06-24T23:00:11.7055812Z   dependency-graph-report-dir: dependency-graph-reports
2026-06-24T23:00:11.7056190Z   dependency-graph-continue-on-failure: true
2026-06-24T23:00:11.7056495Z   build-scan-publish: false
2026-06-24T23:00:11.7056742Z   validate-wrappers: true
2026-06-24T23:00:11.7057211Z   allow-snapshot-wrappers: false
2026-06-24T23:00:11.7057508Z   gradle-home-cache-strict-match: false
2026-06-24T23:00:11.7057806Z   workflow-job-context: null
2026-06-24T23:00:11.7060992Z   github-token: ***
2026-06-24T23:00:11.7061215Z env:
2026-06-24T23:00:11.7061429Z   MYWEATHER_VERSION_CODE: 21
2026-06-24T23:00:11.7061689Z   MYWEATHER_VERSION_NAME: 1.0.21
2026-06-24T23:00:11.7062060Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:11.7062584Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:11.7062990Z ##[endgroup]
2026-06-24T23:00:13.5096182Z Merged default JDK locations into /home/runner/.m2/toolchains.xml
2026-06-24T23:00:13.5100033Z Enhanced Caching: This build is using the proprietary 'gradle-actions-caching' provider for optimized caching support. See https://github.com/gradle/actions/blob/main/DISTRIBUTION.md for terms of use and opt-out instructions.
2026-06-24T23:00:13.5876521Z ##[group]Restore Gradle state from cache
2026-06-24T23:00:13.8344217Z Cache hit for restore-key: gradle-home-v1|Linux-X64|build[620c74083efa5b88ef904c2356f72d31]-c957757d32a9c8816b32cb30a57b3b4db41136cc
2026-06-24T23:00:15.1158609Z Received 406784 of 4601088 (8.8%), 0.4 MBs/sec
2026-06-24T23:00:15.4140274Z Received 4601088 of 4601088 (100.0%), 3.4 MBs/sec
2026-06-24T23:00:15.4141284Z Cache Size: ~4 MB (4601088 B)
2026-06-24T23:00:15.4176293Z [command]/usr/bin/tar -xf /home/runner/work/_temp/2ca85e45-1f8a-46bc-8ed3-6c058dcc2661/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:15.5475447Z Cache restored successfully
2026-06-24T23:00:15.5483422Z Restored cache entry with key gradle-home-v1|Linux-X64|build[620c74083efa5b88ef904c2356f72d31]-9c64e7a569517b1fbc1e4054a0cd7191a289b6e9 to /home/runner/.gradle/caches,/home/runner/.gradle/notifications,/home/runner/.gradle/.setup-gradle in 1960ms
2026-06-24T23:00:15.8207327Z Cache hit for: gradle-generated-gradle-jars-v1-17a19f2c81c308e6469d4947d2674735
2026-06-24T23:00:15.8253942Z Cache hit for: gradle-build-cache-v1-b5fd3521cfad19dfe893ae806afb8bfe
2026-06-24T23:00:15.8268138Z Cache hit for: gradle-wrapper-zips-v1-d66ca9ed62c02faccd154fac15917352
2026-06-24T23:00:15.8307246Z Cache hit for: gradle-groovy-dsl-v1-9942bfa88910b734547a8b6c7437a4bb
2026-06-24T23:00:15.8334829Z Cache hit for: gradle-kotlin-dsl-v1-8b45ce5939a7aba0bbc7b47c7c5f730d
2026-06-24T23:00:15.8341740Z Cache hit for: gradle-dependencies-v1-1fde6b635189d953f89ef0d8863c2992
2026-06-24T23:00:15.8348294Z Cache hit for: gradle-java-toolchains-v1-57802292e8354a989f6e3bf59419e75e
2026-06-24T23:00:15.8415803Z Cache hit for: gradle-instrumented-jars-v1-bb910fcacf708cb42c10c2b5b22b5d19
2026-06-24T23:00:15.8461117Z Cache hit for: gradle-transforms-v1-2be712ffac46d0325bbe2498d438361f
2026-06-24T23:00:16.3284085Z Received 60103 of 60103 (100.0%), 0.4 MBs/sec
2026-06-24T23:00:16.3284807Z Cache Size: ~0 MB (60103 B)
2026-06-24T23:00:16.3648217Z Received 89487 of 89487 (100.0%), 0.4 MBs/sec
2026-06-24T23:00:16.3649492Z Cache Size: ~0 MB (89487 B)
2026-06-24T23:00:16.3747386Z [command]/usr/bin/tar -xf /home/runner/work/_temp/9cadfef1-411c-42a6-94c5-a3fd677796ab/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:16.3783919Z [command]/usr/bin/tar -xf /home/runner/work/_temp/b0e48bd4-01b9-4857-8816-98c3003f97e7/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:16.4046324Z Cache restored successfully
2026-06-24T23:00:16.4254867Z Cache restored successfully
2026-06-24T23:00:16.4256895Z Restored cache entry with key gradle-instrumented-jars-v1-bb910fcacf708cb42c10c2b5b22b5d19 to /home/runner/.gradle/caches/jars-*/*/ in 875ms
2026-06-24T23:00:16.4353521Z Restored cache entry with key gradle-groovy-dsl-v1-9942bfa88910b734547a8b6c7437a4bb to /home/runner/.gradle/caches/*/groovy-dsl/*/ in 885ms
2026-06-24T23:00:16.4943040Z Received 102671 of 102671 (100.0%), 0.2 MBs/sec
2026-06-24T23:00:16.4943999Z Cache Size: ~0 MB (102671 B)
2026-06-24T23:00:16.4968768Z [command]/usr/bin/tar -xf /home/runner/work/_temp/33fb49bb-0271-441b-8158-09cdef798ab1/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:16.5351077Z Cache restored successfully
2026-06-24T23:00:16.5353390Z Restored cache entry with key gradle-kotlin-dsl-v1-8b45ce5939a7aba0bbc7b47c7c5f730d to /home/runner/.gradle/caches/*/kotlin-dsl/accessors/*/,/home/runner/.gradle/caches/*/kotlin-dsl/scripts/*/ in 985ms
2026-06-24T23:00:17.0968676Z Received 0 of 42165970 (0.0%), 0.0 MBs/sec
2026-06-24T23:00:17.1110868Z Received 0 of 139956631 (0.0%), 0.0 MBs/sec
2026-06-24T23:00:17.1221530Z Received 0 of 125007077 (0.0%), 0.0 MBs/sec
2026-06-24T23:00:17.1330140Z Received 703037 of 13285949 (5.3%), 0.7 MBs/sec
2026-06-24T23:00:17.1448736Z Received 0 of 423654704 (0.0%), 0.0 MBs/sec
2026-06-24T23:00:17.1573304Z Received 0 of 517451896 (0.0%), 0.0 MBs/sec
2026-06-24T23:00:17.6784233Z Received 13285949 of 13285949 (100.0%), 8.2 MBs/sec
2026-06-24T23:00:17.6785958Z Cache Size: ~13 MB (13285949 B)
2026-06-24T23:00:17.6914891Z [command]/usr/bin/tar -xf /home/runner/work/_temp/75e66ba3-5bb5-45a0-9bf5-baf9be92ffbe/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:17.7445727Z Received 42165970 of 42165970 (100.0%), 24.4 MBs/sec
2026-06-24T23:00:17.7447747Z Cache Size: ~40 MB (42165970 B)
2026-06-24T23:00:17.7716697Z Cache restored successfully
2026-06-24T23:00:17.7907275Z Restored cache entry with key gradle-build-cache-v1-b5fd3521cfad19dfe893ae806afb8bfe to /home/runner/.gradle/caches/build-cache-*/* in 2240ms
2026-06-24T23:00:17.8423506Z [command]/usr/bin/tar -xf /home/runner/work/_temp/1c35244f-8e66-42ef-b3c6-0a88ee33d7b3/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:18.1187313Z Received 71303168 of 139956631 (50.9%), 33.8 MBs/sec
2026-06-24T23:00:18.1447594Z Received 71303168 of 125007077 (57.0%), 33.7 MBs/sec
2026-06-24T23:00:18.1536350Z Received 58720256 of 423654704 (13.9%), 28.0 MBs/sec
2026-06-24T23:00:18.1601373Z Received 41943040 of 517451896 (8.1%), 20.0 MBs/sec
2026-06-24T23:00:18.2166079Z Cache restored successfully
2026-06-24T23:00:18.2227411Z Restored cache entry with key gradle-generated-gradle-jars-v1-17a19f2c81c308e6469d4947d2674735 to /home/runner/.gradle/caches/9.6.0/generated-gradle-jars/gradle-api-9.6.0.jar in 2672ms
2026-06-24T23:00:18.6051836Z Received 125007077 of 125007077 (100.0%), 48.0 MBs/sec
2026-06-24T23:00:18.6052807Z Cache Size: ~119 MB (125007077 B)
2026-06-24T23:00:18.6565311Z [command]/usr/bin/tar -xf /home/runner/work/_temp/955d183d-9bd4-4dc9-80bf-d4ac4668aed9/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:19.1216588Z Received 135762327 of 139956631 (97.0%), 43.0 MBs/sec
2026-06-24T23:00:19.1547258Z Received 134217728 of 423654704 (31.7%), 42.5 MBs/sec
2026-06-24T23:00:19.1616570Z Received 125829120 of 517451896 (24.3%), 39.9 MBs/sec
2026-06-24T23:00:19.2897566Z Received 139956631 of 139956631 (100.0%), 41.9 MBs/sec
2026-06-24T23:00:19.2911898Z Cache Size: ~133 MB (139956631 B)
2026-06-24T23:00:19.3240489Z [command]/usr/bin/tar -xf /home/runner/work/_temp/5c2413e3-c02e-46bd-8e6f-eeca9679b81e/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:19.7508116Z Cache restored successfully
2026-06-24T23:00:19.7707614Z Restored cache entry with key gradle-wrapper-zips-v1-d66ca9ed62c02faccd154fac15917352 to /home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s in 4221ms
2026-06-24T23:00:20.0799272Z Cache restored successfully
2026-06-24T23:00:20.0993563Z Restored cache entry with key gradle-transforms-v1-2be712ffac46d0325bbe2498d438361f to /home/runner/.gradle/caches/transforms-4/*/,/home/runner/.gradle/caches/*/transforms/*/ in 4548ms
2026-06-24T23:00:20.1556338Z Received 247463936 of 423654704 (58.4%), 58.9 MBs/sec
2026-06-24T23:00:20.1676371Z Received 201326592 of 517451896 (38.9%), 47.9 MBs/sec
2026-06-24T23:00:21.1896397Z Received 373293056 of 423654704 (88.1%), 70.7 MBs/sec
2026-06-24T23:00:21.1929969Z Received 268435456 of 517451896 (51.9%), 50.9 MBs/sec
2026-06-24T23:00:21.6182050Z Received 423654704 of 423654704 (100.0%), 73.8 MBs/sec
2026-06-24T23:00:21.6218502Z Cache Size: ~404 MB (423654704 B)
2026-06-24T23:00:21.6373610Z [command]/usr/bin/tar -xf /home/runner/work/_temp/b0133d65-5d5e-4de1-97e1-cbfdff88babd/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:22.1876505Z Received 377487360 of 517451896 (73.0%), 59.7 MBs/sec
2026-06-24T23:00:22.5488685Z Cache restored successfully
2026-06-24T23:00:22.5732461Z Restored cache entry with key gradle-dependencies-v1-1fde6b635189d953f89ef0d8863c2992 to /home/runner/.gradle/caches/modules-*/files-*/*/*/*/*/ in 7023ms
2026-06-24T23:00:23.1820792Z Received 490733568 of 517451896 (94.8%), 66.6 MBs/sec
2026-06-24T23:00:23.4254594Z Received 517451896 of 517451896 (100.0%), 67.9 MBs/sec
2026-06-24T23:00:23.4256209Z Cache Size: ~493 MB (517451896 B)
2026-06-24T23:00:23.4351277Z [command]/usr/bin/tar -xf /home/runner/work/_temp/d6491fc3-fd7a-4d84-9b28-c66fc05058e0/cache.tzst -P -C /home/runner/work/***/*** --use-compress-program unzstd
2026-06-24T23:00:24.7654886Z Cache restored successfully
2026-06-24T23:00:24.7878524Z Restored cache entry with key gradle-java-toolchains-v1-57802292e8354a989f6e3bf59419e75e to /home/runner/.gradle/jdks/jetbrains_s_r_o_-21-amd64-linux.2 in 9238ms
2026-06-24T23:00:24.7978525Z ##[endgroup]
2026-06-24T23:00:24.8097828Z ##[group]All Gradle Wrapper jars are valid
2026-06-24T23:00:24.8100016Z ✓ Found known Gradle Wrapper JAR files:
2026-06-24T23:00:24.8101102Z   497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7 gradle/wrapper/gradle-wrapper.jar
2026-06-24T23:00:24.8102488Z ##[endgroup]
2026-06-24T23:00:24.8397805Z ##[group]Run echo "$KEYSTORE_BASE64" | base64 --decode > "/home/runner/work/***/***/release.keystore"
2026-06-24T23:00:24.8398726Z [36;1mecho "$KEYSTORE_BASE64" | base64 --decode > "/home/runner/work/***/***/release.keystore"[0m
2026-06-24T23:00:24.8399569Z [36;1mecho "MYWEATHER_KEYSTORE_PATH=/home/runner/work/***/***/release.keystore" >> "$GITHUB_ENV"[0m
2026-06-24T23:00:25.1703267Z shell: /usr/bin/bash -e {0}
2026-06-24T23:00:25.1703709Z env:
2026-06-24T23:00:25.1704020Z   MYWEATHER_VERSION_CODE: 21
2026-06-24T23:00:25.1704421Z   MYWEATHER_VERSION_NAME: 1.0.21
2026-06-24T23:00:25.1704967Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:25.1706074Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:25.1706709Z   GRADLE_ACTION_ID: gradle/actions/setup-gradle
2026-06-24T23:00:25.1707182Z   GRADLE_USER_HOME: /home/runner/.gradle
2026-06-24T23:00:25.1707692Z   GRADLE_BUILD_ACTION_SETUP_COMPLETED: true
2026-06-24T23:00:25.1708353Z   DEVELOCITY_INJECTION_INIT_SCRIPT_NAME: gradle-actions.inject-develocity.init.gradle
2026-06-24T23:00:25.1709043Z   DEVELOCITY_INJECTION_CUSTOM_VALUE: gradle-actions
2026-06-24T23:00:25.1709549Z   GITHUB_DEPENDENCY_GRAPH_ENABLED: false
2026-06-24T23:00:25.1740193Z   KEYSTORE_BASE64: ***
2026-06-24T23:00:25.1740500Z ##[endgroup]
2026-06-24T23:00:25.1881020Z ##[group]Run ./gradlew :app:assembleRelease --stacktrace
2026-06-24T23:00:25.1881557Z [36;1m./gradlew :app:assembleRelease --stacktrace[0m
2026-06-24T23:00:25.1912158Z shell: /usr/bin/bash -e {0}
2026-06-24T23:00:25.1912488Z env:
2026-06-24T23:00:25.1912762Z   MYWEATHER_VERSION_CODE: 21
2026-06-24T23:00:25.1913085Z   MYWEATHER_VERSION_NAME: 1.0.21
2026-06-24T23:00:25.1913532Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:25.1914112Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:25.1914621Z   GRADLE_ACTION_ID: gradle/actions/setup-gradle
2026-06-24T23:00:25.1915000Z   GRADLE_USER_HOME: /home/runner/.gradle
2026-06-24T23:00:25.1915650Z   GRADLE_BUILD_ACTION_SETUP_COMPLETED: true
2026-06-24T23:00:25.1916190Z   DEVELOCITY_INJECTION_INIT_SCRIPT_NAME: gradle-actions.inject-develocity.init.gradle
2026-06-24T23:00:25.1916962Z   DEVELOCITY_INJECTION_CUSTOM_VALUE: gradle-actions
2026-06-24T23:00:25.1917388Z   GITHUB_DEPENDENCY_GRAPH_ENABLED: false
2026-06-24T23:00:25.1917959Z   MYWEATHER_KEYSTORE_PATH: /home/runner/work/***/***/release.keystore
2026-06-24T23:00:25.1918628Z   MYWEATHER_KEYSTORE_PASSWORD: ***
2026-06-24T23:00:25.1918980Z   MYWEATHER_KEY_ALIAS: ***
2026-06-24T23:00:25.1919491Z   MYWEATHER_KEY_PASSWORD: ***
2026-06-24T23:00:25.1919802Z ##[endgroup]
2026-06-24T23:00:26.8943296Z Starting a Gradle Daemon (subsequent builds will be faster)
2026-06-24T23:00:33.9947775Z Calculating task graph as no cached configuration is available for tasks: :app:assembleRelease
2026-06-24T23:00:39.7937047Z 
2026-06-24T23:00:39.7946877Z > Configure project :app
2026-06-24T23:00:39.7957000Z WARNING: The option setting 'android.usesSdkInManifest.disallowed=false' is deprecated.
2026-06-24T23:00:39.7978056Z The current default is 'true'.
2026-06-24T23:00:39.7979058Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.7980443Z WARNING: The option setting 'android.sdk.defaultTargetSdkToCompileSdkIfUnset=false' is deprecated.
2026-06-24T23:00:39.7982200Z The current default is 'true'.
2026-06-24T23:00:39.7983108Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.7984705Z WARNING: The option setting 'android.enableAppCompileTimeRClass=false' is deprecated.
2026-06-24T23:00:39.7986176Z The current default is 'true'.
2026-06-24T23:00:39.7987015Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.7989676Z WARNING: The option setting 'android.builtInKotlin=false' is deprecated.
2026-06-24T23:00:39.7990745Z The current default is 'true'.
2026-06-24T23:00:39.7991644Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.8008751Z WARNING: The option setting 'android.newDsl=false' is deprecated.
2026-06-24T23:00:39.8010143Z The current default is 'true'.
2026-06-24T23:00:39.8011119Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.8012570Z WARNING: The option setting 'android.r8.optimizedResourceShrinking=false' is deprecated.
2026-06-24T23:00:39.8013819Z The current default is 'true'.
2026-06-24T23:00:39.8014732Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.8016331Z WARNING: The option setting 'android.defaults.buildfeatures.resvalues=true' is deprecated.
2026-06-24T23:00:39.8017585Z The current default is 'false'.
2026-06-24T23:00:39.8018486Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.8965882Z WARNING: API 'applicationVariants' is obsolete and has been replaced with 'AndroidComponentsExtension'.
2026-06-24T23:00:39.8978376Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.8980549Z The legacy variant API is disabled by default in AGP 9.0, but can be re-enabled by adding 
2026-06-24T23:00:39.8981929Z     android.newDsl=false
2026-06-24T23:00:39.8982827Z to this project's gradle.properties file.
2026-06-24T23:00:39.8984530Z For more information, see http://developer.android.com/build/r/new-dsl.
2026-06-24T23:00:39.8985765Z 
2026-06-24T23:00:39.8986914Z To determine what is calling applicationVariants, use -Pandroid.debug.obsoleteApi=true on the command line to display more information.
2026-06-24T23:00:39.8989032Z WARNING: API 'testVariants' is obsolete and has been replaced with 'AndroidComponentsExtension'.
2026-06-24T23:00:39.9004196Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.9005841Z The legacy variant API is disabled by default in AGP 9.0, but can be re-enabled by adding 
2026-06-24T23:00:39.9007196Z     android.newDsl=false
2026-06-24T23:00:39.9008678Z to this project's gradle.properties file.
2026-06-24T23:00:39.9016554Z For more information, see http://developer.android.com/build/r/new-dsl.
2026-06-24T23:00:39.9017449Z 
2026-06-24T23:00:39.9018477Z To determine what is calling testVariants, use -Pandroid.debug.obsoleteApi=true on the command line to display more information.
2026-06-24T23:00:39.9020792Z WARNING: API 'unitTestVariants' is obsolete and has been replaced with 'AndroidComponentsExtension'.
2026-06-24T23:00:39.9022192Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:39.9023521Z The legacy variant API is disabled by default in AGP 9.0, but can be re-enabled by adding 
2026-06-24T23:00:39.9024638Z     android.newDsl=false
2026-06-24T23:00:39.9025792Z to this project's gradle.properties file.
2026-06-24T23:00:39.9028606Z For more information, see http://developer.android.com/build/r/new-dsl.
2026-06-24T23:00:39.9029480Z 
2026-06-24T23:00:39.9030538Z To determine what is calling unitTestVariants, use -Pandroid.debug.obsoleteApi=true on the command line to display more information.
2026-06-24T23:00:40.2942198Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:40.2945020Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:40.2947936Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:40.2950647Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:40.3938907Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:40.3949318Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:40.3952011Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:40.3954505Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:41.2942515Z w: ⚠️ Deprecated 'org.jetbrains.kotlin.android' plugin usage
2026-06-24T23:00:41.2964704Z The 'org.jetbrains.kotlin.android' plugin in project ':app' is no longer required for Kotlin support since AGP 9.0.
2026-06-24T23:00:41.2966783Z Solution: Remove both `android.builtInKotlin=true` and `android.newDsl=false` from `gradle.properties`, then migrate to built-in Kotlin.
2026-06-24T23:00:41.2968202Z See https://kotl.in/gradle/agp-built-in-kotlin for more details.
2026-06-24T23:00:41.2968785Z 
2026-06-24T23:00:44.5015998Z 
2026-06-24T23:00:44.5017104Z > Task :app:generateReleaseAssets UP-TO-DATE
2026-06-24T23:00:44.5046014Z > Task :app:preBuild UP-TO-DATE
2026-06-24T23:00:44.5076240Z > Task :app:checkKotlinGradlePluginConfigurationErrors SKIPPED
2026-06-24T23:00:44.5105955Z > Task :app:preReleaseBuild UP-TO-DATE
2026-06-24T23:00:44.6966922Z > Task :app:buildKotlinToolingMetadata
2026-06-24T23:00:44.7937998Z > Task :app:generateReleaseBuildConfig
2026-06-24T23:00:44.7970194Z > Task :app:mergeReleaseJniLibFolders
2026-06-24T23:00:44.7972928Z > Task :app:generateReleaseResValues FROM-CACHE
2026-06-24T23:00:44.7973832Z > Task :app:mapReleaseSourceSetPaths
2026-06-24T23:00:44.9938020Z > Task :app:checkReleaseDuplicateClasses
2026-06-24T23:00:45.0937277Z > Task :app:generateReleaseResources FROM-CACHE
2026-06-24T23:00:45.1981841Z > Task :app:processReleaseNavigationResources FROM-CACHE
2026-06-24T23:00:45.1988008Z > Task :app:compileReleaseNavigationResources FROM-CACHE
2026-06-24T23:00:45.1998871Z > Task :app:packageReleaseResources FROM-CACHE
2026-06-24T23:00:45.2005865Z > Task :app:checkReleaseAarMetadata
2026-06-24T23:00:45.2937469Z > Task :app:createReleaseCompatibleScreenManifests
2026-06-24T23:00:45.2968877Z > Task :app:mergeReleaseNativeLibs
2026-06-24T23:00:45.3997430Z > Task :app:mergeReleaseResources FROM-CACHE
2026-06-24T23:00:45.4046103Z > Task :app:javaPreCompileRelease FROM-CACHE
2026-06-24T23:00:46.4937354Z > Task :app:stripReleaseDebugSymbols
2026-06-24T23:00:46.4938711Z > Task :app:extractReleaseNativeSymbolTables FROM-CACHE
2026-06-24T23:00:46.4942970Z > Task :app:mergeReleaseNativeDebugMetadata
2026-06-24T23:00:46.4943977Z > Task :app:extractProguardFiles
2026-06-24T23:00:46.5938052Z > Task :app:mergeReleaseStartupProfile
2026-06-24T23:00:49.4966312Z > Task :app:extractDeepLinksRelease FROM-CACHE
2026-06-24T23:00:49.6942722Z > Task :app:mergeReleaseArtProfile
2026-06-24T23:00:49.6966283Z > Task :app:mergeReleaseAssets
2026-06-24T23:00:49.7011530Z > Task :app:compressReleaseAssets FROM-CACHE
2026-06-24T23:00:49.8966573Z > Task :app:extractReleaseVersionControlInfo
2026-06-24T23:00:49.9006344Z > Task :app:validateSigningRelease
2026-06-24T23:00:49.9035898Z > Task :app:writeReleaseAppMetadata
2026-06-24T23:00:49.9065881Z > Task :app:collectReleaseDependencies
2026-06-24T23:00:49.9950524Z > Task :app:writeReleaseSigningConfigVersions
2026-06-24T23:00:50.0964019Z > Task :app:parseReleaseLocalResources FROM-CACHE
2026-06-24T23:00:50.1941129Z > Task :app:sdkReleaseDependencyData
2026-06-24T23:00:50.1943408Z > Task :app:processReleaseMainManifest
2026-06-24T23:00:50.2961679Z > Task :app:processReleaseManifest
2026-06-24T23:00:50.2986314Z > Task :app:processReleaseManifestForPackage
2026-06-24T23:00:50.7937545Z > Task :app:processReleaseResources
2026-06-24T23:00:57.5951059Z > Task :app:kspReleaseKotlin
2026-06-24T23:01:09.2950166Z 
2026-06-24T23:01:09.2987435Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/data/remote/NetworkModule.kt:35:29 Unresolved reference 'HttpCacheInterceptor'.
2026-06-24T23:01:09.2988822Z > Task :app:compileReleaseKotlin FAILED
2026-06-24T23:01:09.2990833Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/data/remote/NetworkModule.kt:36:14 Unresolved reference 'addInterceptor'.
2026-06-24T23:01:09.3016816Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:42:41 Unresolved reference 'AlertsBanner'.
2026-06-24T23:01:09.3018996Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:44:41 Unresolved reference 'HourlyStrip'.
2026-06-24T23:01:09.3021260Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:45:41 Unresolved reference 'LocationSourcesCard'.
2026-06-24T23:01:09.3023514Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:46:41 Unresolved reference 'ObservationCard'.
2026-06-24T23:01:09.3025983Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:47:41 Unresolved reference 'PeriodDetailSheet'.
2026-06-24T23:01:09.3028285Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:105:40 Unresolved reference 'AlertsBanner'.
2026-06-24T23:01:09.3030854Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:185:28 Unresolved reference 'ObservationCard'.
2026-06-24T23:01:09.3033016Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:201:25 Unresolved reference 'HourlyStrip'.
2026-06-24T23:01:09.3035385Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:203:67 Unresolved reference 'it'.
2026-06-24T23:01:09.3037548Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:208:28 Unresolved reference 'LocationSourcesCard'.
2026-06-24T23:01:09.3039798Z e: file:///home/runner/work/***/***/app/src/main/java/com/kusl/***/ui/dashboard/DashboardScreen.kt:229:13 Unresolved reference 'PeriodDetailSheet'.
2026-06-24T23:01:09.3957213Z gradle/actions: Writing build results to /home/runner/work/_temp/.gradle-actions/build-results/__run_2-1782342034851.json
2026-06-24T23:01:09.3972547Z 
2026-06-24T23:01:09.3973978Z [Incubating] Problems report is available at: file:///home/runner/work/***/***/build/reports/problems/problems-report.html
2026-06-24T23:01:09.3991397Z 
2026-06-24T23:01:09.4003469Z FAILURE: Build failed with an exception.
2026-06-24T23:01:09.4035934Z 
2026-06-24T23:01:09.4036222Z * What went wrong:
2026-06-24T23:01:09.4037163Z Execution failed for task ':app:compileReleaseKotlin' (registered by plugin 'org.jetbrains.kotlin.android').
2026-06-24T23:01:09.4038742Z > A failure occurred while executing org.jetbrains.kotlin.compilerRunner.btapi.BuildToolsApiCompilationWork
2026-06-24T23:01:09.4039938Z    > Compilation error. See log for more details
2026-06-24T23:01:09.4040354Z 
2026-06-24T23:01:09.4040534Z * Try:
2026-06-24T23:01:09.4041065Z > Run with --info or --debug option to get more log output.
2026-06-24T23:01:09.4041955Z > Run with --scan to get full insights from a Build Scan (powered by Develocity).
2026-06-24T23:01:09.4042871Z > Get more help at https://help.gradle.org.
2026-06-24T23:01:09.4043342Z 
2026-06-24T23:01:09.4043535Z * Exception is:
2026-06-24T23:01:09.4044828Z org.gradle.api.tasks.TaskExecutionException: Execution failed for task ':app:compileReleaseKotlin' (registered by plugin 'org.jetbrains.kotlin.android').
2026-06-24T23:01:09.4047485Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.lambda$executeIfValid$1(ExecuteActionsTaskExecuter.java:135)
2026-06-24T23:01:09.4049157Z 	at org.gradle.internal.Try$Failure.ifSuccessfulOrElse(Try.java:288)
2026-06-24T23:01:09.4050763Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:133)
2026-06-24T23:01:09.4052850Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.execute(ExecuteActionsTaskExecuter.java:121)
2026-06-24T23:01:09.4055055Z 	at org.gradle.api.internal.tasks.execution.ProblemsTaskPathTrackingTaskExecuter.execute(ProblemsTaskPathTrackingTaskExecuter.java:41)
2026-06-24T23:01:09.4057742Z 	at org.gradle.api.internal.tasks.execution.ResolveTaskExecutionModeExecuter.execute(ResolveTaskExecutionModeExecuter.java:51)
2026-06-24T23:01:09.4059961Z 	at org.gradle.api.internal.tasks.execution.FinalizePropertiesTaskExecuter.execute(FinalizePropertiesTaskExecuter.java:46)
2026-06-24T23:01:09.4062090Z 	at org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter.execute(SkipTaskWithNoActionsExecuter.java:57)
2026-06-24T23:01:09.4064018Z 	at org.gradle.api.internal.tasks.execution.SkipOnlyIfTaskExecuter.execute(SkipOnlyIfTaskExecuter.java:74)
2026-06-24T23:01:09.4066030Z 	at org.gradle.api.internal.tasks.execution.CatchExceptionTaskExecuter.execute(CatchExceptionTaskExecuter.java:36)
2026-06-24T23:01:09.4067822Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.executeTask(EventFiringTaskExecuter.java:77)
2026-06-24T23:01:09.4069359Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:55)
2026-06-24T23:01:09.4070817Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:52)
2026-06-24T23:01:09.4073202Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:01:09.4075671Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:01:09.4077713Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:01:09.4079466Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4081225Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:01:09.4083036Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4084057Z 
2026-06-24T23:01:09.4085490Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:01:09.4087260Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter.execute(EventFiringTaskExecuter.java:52)
2026-06-24T23:01:09.4088984Z 	at org.gradle.execution.plan.DefaultNodeExecutor.executeLocalTaskNode(DefaultNodeExecutor.java:55)
2026-06-24T23:01:09.4090448Z 	at org.gradle.execution.plan.DefaultNodeExecutor.execute(DefaultNodeExecutor.java:34)
2026-06-24T23:01:09.4092166Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:355)
2026-06-24T23:01:09.4094300Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:343)
2026-06-24T23:01:09.4096448Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.lambda$execute$0(DefaultTaskExecutionGraph.java:339)
2026-06-24T23:01:09.4098122Z 	at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:84)
2026-06-24T23:01:09.4099712Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:339)
2026-06-24T23:01:09.4101583Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:328)
2026-06-24T23:01:09.4103182Z 	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.execute(DefaultPlanExecutor.java:459)
2026-06-24T23:01:09.4104445Z 	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.run(DefaultPlanExecutor.java:376)
2026-06-24T23:01:09.4105876Z 	at org.gradle.execution.plan.DefaultPlanExecutor.process(DefaultPlanExecutor.java:111)
2026-06-24T23:01:09.4107239Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph.executeWithServices(DefaultTaskExecutionGraph.java:146)
2026-06-24T23:01:09.4108732Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph.execute(DefaultTaskExecutionGraph.java:131)
2026-06-24T23:01:09.4110065Z 	at org.gradle.execution.SelectedTaskExecutionAction.execute(SelectedTaskExecutionAction.java:35)
2026-06-24T23:01:09.4111572Z 	at org.gradle.execution.BuildOperationFiringBuildWorkerExecutor$ExecuteTasks.call(BuildOperationFiringBuildWorkerExecutor.java:54)
2026-06-24T23:01:09.4113310Z 	at org.gradle.execution.BuildOperationFiringBuildWorkerExecutor$ExecuteTasks.call(BuildOperationFiringBuildWorkerExecutor.java:43)
2026-06-24T23:01:09.4115258Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:01:09.4117204Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:01:09.4118850Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:01:09.4120560Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4122003Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:01:09.4123454Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4124865Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:01:09.4126622Z 	at org.gradle.execution.BuildOperationFiringBuildWorkerExecutor.execute(BuildOperationFiringBuildWorkerExecutor.java:40)
2026-06-24T23:01:09.4128310Z 	at org.gradle.internal.build.DefaultBuildLifecycleController.lambda$executeTasks$11(DefaultBuildLifecycleController.java:323)
2026-06-24T23:01:09.4129411Z 	at org.gradle.internal.model.StateTransitionController.doTransition(StateTransitionController.java:304)
2026-06-24T23:01:09.4130444Z 	at org.gradle.internal.model.StateTransitionController.lambda$tryTransition$9(StateTransitionController.java:215)
2026-06-24T23:01:09.4132110Z 	at org.gradle.internal.work.DefaultSynchronizer.withLock(DefaultSynchronizer.java:45)
2026-06-24T23:01:09.4133628Z 	at org.gradle.internal.model.StateTransitionController.tryTransition(StateTransitionController.java:215)
2026-06-24T23:01:09.4135598Z 	at org.gradle.internal.build.DefaultBuildLifecycleController.executeTasks(DefaultBuildLifecycleController.java:314)
2026-06-24T23:01:09.4137478Z 	at org.gradle.internal.build.DefaultBuildWorkGraphController$DefaultBuildWorkGraph.runWork(DefaultBuildWorkGraphController.java:220)
2026-06-24T23:01:09.4139493Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$runAndReleaseLocks$0(DefaultWorkerLeaseService.java:300)
2026-06-24T23:01:09.4141110Z 	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
2026-06-24T23:01:09.4142687Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAndReleaseLocks(DefaultWorkerLeaseService.java:298)
2026-06-24T23:01:09.4144487Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocksAcquired(DefaultWorkerLeaseService.java:294)
2026-06-24T23:01:09.4146459Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocks(DefaultWorkerLeaseService.java:286)
2026-06-24T23:01:09.4148215Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:130)
2026-06-24T23:01:09.4149918Z 	at org.gradle.composite.internal.DefaultBuildController.doRun(DefaultBuildController.java:182)
2026-06-24T23:01:09.4151610Z 	at org.gradle.composite.internal.DefaultBuildController$BuildOpRunnable.lambda$run$0(DefaultBuildController.java:199)
2026-06-24T23:01:09.4153323Z 	at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:84)
2026-06-24T23:01:09.4155013Z 	at org.gradle.composite.internal.DefaultBuildController$BuildOpRunnable.run(DefaultBuildController.java:199)
2026-06-24T23:01:09.4157070Z 	at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
2026-06-24T23:01:09.4158745Z 	at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:47)
2026-06-24T23:01:09.4161099Z Caused by: org.gradle.workers.internal.DefaultWorkerExecutor$WorkExecutionException: A failure occurred while executing org.jetbrains.kotlin.compilerRunner.btapi.BuildToolsApiCompilationWork
2026-06-24T23:01:09.4163675Z 	at org.gradle.workers.internal.DefaultWorkerExecutor$WorkItemExecution.waitForCompletion(DefaultWorkerExecutor.java:278)
2026-06-24T23:01:09.4166009Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.lambda$waitForItemsAndGatherFailures$2(DefaultAsyncWorkTracker.java:132)
2026-06-24T23:01:09.4167546Z 	at org.gradle.internal.Factories$1.create(Factories.java:30)
2026-06-24T23:01:09.4169012Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$withoutLocksBlocking$3(DefaultWorkerLeaseService.java:410)
2026-06-24T23:01:09.4170675Z 	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
2026-06-24T23:01:09.4172669Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withoutLocksBlocking(DefaultWorkerLeaseService.java:405)
2026-06-24T23:01:09.4174500Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.blocking(DefaultWorkerLeaseService.java:255)
2026-06-24T23:01:09.4176486Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.blocking(DefaultWorkerLeaseService.java:237)
2026-06-24T23:01:09.4178206Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.lambda$waitForItemsAndGatherFailures$3(DefaultAsyncWorkTracker.java:128)
2026-06-24T23:01:09.4179763Z 	at org.gradle.internal.Factories$1.create(Factories.java:30)
2026-06-24T23:01:09.4181391Z 	at org.gradle.internal.resources.AbstractResourceLockRegistry.whileDisallowingLockChanges(AbstractResourceLockRegistry.java:50)
2026-06-24T23:01:09.4183697Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.whileDisallowingProjectLockChanges(DefaultWorkerLeaseService.java:260)
2026-06-24T23:01:09.4186451Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForItemsAndGatherFailures(DefaultAsyncWorkTracker.java:127)
2026-06-24T23:01:09.4188546Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForItemsAndGatherFailures(DefaultAsyncWorkTracker.java:93)
2026-06-24T23:01:09.4190390Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForAll(DefaultAsyncWorkTracker.java:79)
2026-06-24T23:01:09.4192101Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForCompletion(DefaultAsyncWorkTracker.java:67)
2026-06-24T23:01:09.4193788Z 	at org.gradle.api.internal.tasks.execution.TaskExecution$3.run(TaskExecution.java:267)
2026-06-24T23:01:09.4195691Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(DefaultBuildOperationRunner.java:30)
2026-06-24T23:01:09.4197469Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(DefaultBuildOperationRunner.java:27)
2026-06-24T23:01:09.4199455Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:01:09.4201309Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4203196Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:01:09.4205072Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4207177Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.run(DefaultBuildOperationRunner.java:48)
2026-06-24T23:01:09.4208880Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.executeAction(TaskExecution.java:244)
2026-06-24T23:01:09.4210471Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.executeActions(TaskExecution.java:227)
2026-06-24T23:01:09.4212296Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.executeWithPreviousOutputFiles(TaskExecution.java:210)
2026-06-24T23:01:09.4214034Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.execute(TaskExecution.java:176)
2026-06-24T23:01:09.4215808Z 	at org.gradle.internal.execution.steps.ExecuteStep.executeInternal(ExecuteStep.java:167)
2026-06-24T23:01:09.4217216Z 	at org.gradle.internal.execution.steps.ExecuteStep.access$000(ExecuteStep.java:47)
2026-06-24T23:01:09.4218547Z 	at org.gradle.internal.execution.steps.ExecuteStep$1.call(ExecuteStep.java:137)
2026-06-24T23:01:09.4219831Z 	at org.gradle.internal.execution.steps.ExecuteStep$1.call(ExecuteStep.java:134)
2026-06-24T23:01:09.4221570Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:01:09.4223871Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:01:09.4226310Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:01:09.4228564Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4230479Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:01:09.4232365Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4234195Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:01:09.4236086Z 	at org.gradle.internal.execution.steps.ExecuteStep.execute(ExecuteStep.java:134)
2026-06-24T23:01:09.4237479Z 	at org.gradle.internal.execution.steps.ExecuteStep$Mutable.execute(ExecuteStep.java:80)
2026-06-24T23:01:09.4239008Z 	at org.gradle.internal.execution.steps.CancelExecutionStep.execute(CancelExecutionStep.java:42)
2026-06-24T23:01:09.4240602Z 	at org.gradle.internal.execution.steps.TimeoutStep.executeWithoutTimeout(TimeoutStep.java:75)
2026-06-24T23:01:09.4242362Z 	at org.gradle.internal.execution.steps.TimeoutStep.execute(TimeoutStep.java:55)
2026-06-24T23:01:09.4243983Z 	at org.gradle.internal.execution.steps.PreCreateOutputParentsStep.execute(PreCreateOutputParentsStep.java:51)
2026-06-24T23:01:09.4246099Z 	at org.gradle.internal.execution.steps.PreCreateOutputParentsStep.execute(PreCreateOutputParentsStep.java:29)
2026-06-24T23:01:09.4248000Z 	at org.gradle.internal.execution.steps.RemovePreviousOutputsStep.executeMutable(RemovePreviousOutputsStep.java:67)
2026-06-24T23:01:09.4249788Z 	at org.gradle.internal.execution.steps.RemovePreviousOutputsStep.executeMutable(RemovePreviousOutputsStep.java:39)
2026-06-24T23:01:09.4251363Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4252853Z 	at org.gradle.internal.execution.steps.BroadcastChangingOutputsStep.execute(BroadcastChangingOutputsStep.java:42)
2026-06-24T23:01:09.4254645Z 	at org.gradle.internal.execution.steps.BroadcastChangingOutputsStep.execute(BroadcastChangingOutputsStep.java:24)
2026-06-24T23:01:09.4256856Z 	at org.gradle.internal.execution.steps.CaptureOutputsAfterExecutionStep.execute(CaptureOutputsAfterExecutionStep.java:69)
2026-06-24T23:01:09.4258792Z 	at org.gradle.internal.execution.steps.CaptureOutputsAfterExecutionStep.execute(CaptureOutputsAfterExecutionStep.java:46)
2026-06-24T23:01:09.4260632Z 	at org.gradle.internal.execution.steps.ResolveInputChangesStep.executeMutable(ResolveInputChangesStep.java:39)
2026-06-24T23:01:09.4262043Z 	at org.gradle.internal.execution.steps.ResolveInputChangesStep.executeMutable(ResolveInputChangesStep.java:28)
2026-06-24T23:01:09.4262898Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4263682Z 	at org.gradle.internal.execution.steps.BuildCacheStep.executeWithoutCache(BuildCacheStep.java:189)
2026-06-24T23:01:09.4264557Z 	at org.gradle.internal.execution.steps.BuildCacheStep.executeAndStoreInCache(BuildCacheStep.java:145)
2026-06-24T23:01:09.4265991Z 	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$executeWithCache$4(BuildCacheStep.java:104)
2026-06-24T23:01:09.4267584Z 	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$executeWithCache$5(BuildCacheStep.java:104)
2026-06-24T23:01:09.4268823Z 	at org.gradle.internal.Try$Success.map(Try.java:170)
2026-06-24T23:01:09.4270018Z 	at org.gradle.internal.execution.steps.BuildCacheStep.executeWithCache(BuildCacheStep.java:88)
2026-06-24T23:01:09.4273227Z 	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$execute$0(BuildCacheStep.java:75)
2026-06-24T23:01:09.4274501Z 	at org.gradle.internal.Either$Left.fold(Either.java:116)
2026-06-24T23:01:09.4275878Z 	at org.gradle.internal.execution.caching.CachingState.fold(CachingState.java:62)
2026-06-24T23:01:09.4277408Z 	at org.gradle.internal.execution.steps.BuildCacheStep.execute(BuildCacheStep.java:74)
2026-06-24T23:01:09.4278593Z 	at org.gradle.internal.execution.steps.BuildCacheStep.execute(BuildCacheStep.java:49)
2026-06-24T23:01:09.4280260Z 	at org.gradle.internal.execution.steps.StoreExecutionStateStep.executeMutable(StoreExecutionStateStep.java:46)
2026-06-24T23:01:09.4281908Z 	at org.gradle.internal.execution.steps.StoreExecutionStateStep.executeMutable(StoreExecutionStateStep.java:35)
2026-06-24T23:01:09.4283494Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4284867Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.executeBecause(SkipUpToDateStep.java:75)
2026-06-24T23:01:09.4286629Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.lambda$execute$2(SkipUpToDateStep.java:53)
2026-06-24T23:01:09.4288121Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.execute(SkipUpToDateStep.java:53)
2026-06-24T23:01:09.4289506Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.execute(SkipUpToDateStep.java:35)
2026-06-24T23:01:09.4291317Z 	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsFinishedStep.execute(MarkSnapshottingInputsFinishedStep.java:37)
2026-06-24T23:01:09.4294084Z 	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsFinishedStep.execute(MarkSnapshottingInputsFinishedStep.java:27)
2026-06-24T23:01:09.4297221Z 	at org.gradle.internal.execution.steps.ResolveMutableCachingStateStep.executeDelegate(ResolveMutableCachingStateStep.java:70)
2026-06-24T23:01:09.4299336Z 	at org.gradle.internal.execution.steps.ResolveMutableCachingStateStep.executeDelegate(ResolveMutableCachingStateStep.java:32)
2026-06-24T23:01:09.4301284Z 	at org.gradle.internal.execution.steps.AbstractResolveCachingStateStep.execute(AbstractResolveCachingStateStep.java:69)
2026-06-24T23:01:09.4303238Z 	at org.gradle.internal.execution.steps.AbstractResolveCachingStateStep.execute(AbstractResolveCachingStateStep.java:37)
2026-06-24T23:01:09.4305367Z 	at org.gradle.internal.execution.steps.ResolveChangesStep.executeMutable(ResolveChangesStep.java:63)
2026-06-24T23:01:09.4307051Z 	at org.gradle.internal.execution.steps.ResolveChangesStep.executeMutable(ResolveChangesStep.java:34)
2026-06-24T23:01:09.4308529Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4309816Z 	at org.gradle.internal.execution.steps.ValidateStep$Mutable.executeDelegate(ValidateStep.java:79)
2026-06-24T23:01:09.4311106Z 	at org.gradle.internal.execution.steps.ValidateStep$Mutable.executeDelegate(ValidateStep.java:65)
2026-06-24T23:01:09.4312317Z 	at org.gradle.internal.execution.steps.ValidateStep.execute(ValidateStep.java:105)
2026-06-24T23:01:09.4313528Z 	at org.gradle.internal.execution.steps.ValidateStep$Mutable.execute(ValidateStep.java:65)
2026-06-24T23:01:09.4315766Z 	at org.gradle.internal.execution.steps.CaptureMutableStateBeforeExecutionStep.executeMutable(CaptureMutableStateBeforeExecutionStep.java:86)
2026-06-24T23:01:09.4318611Z 	at org.gradle.internal.execution.steps.CaptureMutableStateBeforeExecutionStep.execute(CaptureMutableStateBeforeExecutionStep.java:65)
2026-06-24T23:01:09.4320861Z 	at org.gradle.internal.execution.steps.CaptureMutableStateBeforeExecutionStep.execute(CaptureMutableStateBeforeExecutionStep.java:45)
2026-06-24T23:01:09.4323179Z 	at org.gradle.internal.execution.steps.SkipEmptyMutableWorkStep.executeWithNonEmptySources(SkipEmptyMutableWorkStep.java:210)
2026-06-24T23:01:09.4325437Z 	at org.gradle.internal.execution.steps.SkipEmptyMutableWorkStep.executeMutable(SkipEmptyMutableWorkStep.java:90)
2026-06-24T23:01:09.4327458Z 	at org.gradle.internal.execution.steps.SkipEmptyMutableWorkStep.executeMutable(SkipEmptyMutableWorkStep.java:53)
2026-06-24T23:01:09.4328898Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4330614Z 	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsStartedStep.execute(MarkSnapshottingInputsStartedStep.java:38)
2026-06-24T23:01:09.4332834Z 	at org.gradle.internal.execution.steps.LoadPreviousExecutionStateStep.executeMutable(LoadPreviousExecutionStateStep.java:36)
2026-06-24T23:01:09.4335008Z 	at org.gradle.internal.execution.steps.LoadPreviousExecutionStateStep.executeMutable(LoadPreviousExecutionStateStep.java:23)
2026-06-24T23:01:09.4336924Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4338477Z 	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.executeMutable(HandleStaleOutputsStep.java:77)
2026-06-24T23:01:09.4340227Z 	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.executeMutable(HandleStaleOutputsStep.java:43)
2026-06-24T23:01:09.4341599Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4343221Z 	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.lambda$executeMutable$0(AssignMutableWorkspaceStep.java:34)
2026-06-24T23:01:09.4344989Z 	at org.gradle.api.internal.tasks.execution.TaskExecution$4.withWorkspace(TaskExecution.java:305)
2026-06-24T23:01:09.4346884Z 	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.executeMutable(AssignMutableWorkspaceStep.java:30)
2026-06-24T23:01:09.4349294Z 	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.executeMutable(AssignMutableWorkspaceStep.java:21)
2026-06-24T23:01:09.4350868Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:01:09.4352200Z 	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:40)
2026-06-24T23:01:09.4353727Z 	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:23)
2026-06-24T23:01:09.4355962Z 	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.lambda$execute$2(ExecuteWorkBuildOperationFiringStep.java:67)
2026-06-24T23:01:09.4358199Z 	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:67)
2026-06-24T23:01:09.4360393Z 	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:39)
2026-06-24T23:01:09.4362289Z 	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:46)
2026-06-24T23:01:09.4363796Z 	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:34)
2026-06-24T23:01:09.4365722Z 	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:56)
2026-06-24T23:01:09.4366962Z 	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:38)
2026-06-24T23:01:09.4368296Z 	at org.gradle.internal.execution.impl.DefaultExecutionEngine$1.execute(DefaultExecutionEngine.java:68)
2026-06-24T23:01:09.4370156Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:132)
2026-06-24T23:01:09.4371468Z 	... 61 more
2026-06-24T23:01:09.4372500Z Caused by: org.jetbrains.kotlin.gradle.tasks.CompilationErrorException: Compilation error. See log for more details
2026-06-24T23:01:09.4374107Z 	at org.jetbrains.kotlin.gradle.tasks.TasksUtilsKt.throwExceptionIfCompilationFailed(tasksUtils.kt:21)
2026-06-24T23:01:09.4376207Z 	at org.jetbrains.kotlin.compilerRunner.btapi.BuildToolsApiCompilationWork.execute(BuildToolsApiCompilationWork.kt:320)
2026-06-24T23:01:09.4377921Z 	at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:68)
2026-06-24T23:01:09.4379410Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1$1.create(NoIsolationWorkerFactory.java:64)
2026-06-24T23:01:09.4380997Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1$1.create(NoIsolationWorkerFactory.java:61)
2026-06-24T23:01:09.4382547Z 	at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:102)
2026-06-24T23:01:09.4384158Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1.lambda$execute$0(NoIsolationWorkerFactory.java:61)
2026-06-24T23:01:09.4385797Z 	at org.gradle.workers.internal.AbstractWorker$1.call(AbstractWorker.java:44)
2026-06-24T23:01:09.4386950Z 	at org.gradle.workers.internal.AbstractWorker$1.call(AbstractWorker.java:41)
2026-06-24T23:01:09.4388851Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:01:09.4394541Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:01:09.4396015Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:01:09.4396959Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4397897Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:01:09.4398819Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:01:09.4399730Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:01:09.4400849Z 	at org.gradle.workers.internal.AbstractWorker.executeWrappedInBuildOperation(AbstractWorker.java:41)
2026-06-24T23:01:09.4401694Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1.execute(NoIsolationWorkerFactory.java:58)
2026-06-24T23:01:09.4402531Z 	at org.gradle.workers.internal.DefaultWorkerExecutor.lambda$submitWork$0(DefaultWorkerExecutor.java:174)
2026-06-24T23:01:09.4403509Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.runExecution(DefaultConditionalExecutionQueue.java:191)
2026-06-24T23:01:09.4404608Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.access$500(DefaultConditionalExecutionQueue.java:112)
2026-06-24T23:01:09.4405927Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner$1.run(DefaultConditionalExecutionQueue.java:168)
2026-06-24T23:01:09.4406728Z 	at org.gradle.internal.Factories$1.create(Factories.java:30)
2026-06-24T23:01:09.4407477Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$runAndReleaseLocks$0(DefaultWorkerLeaseService.java:300)
2026-06-24T23:01:09.4408345Z 	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
2026-06-24T23:01:09.4409191Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAndReleaseLocks(DefaultWorkerLeaseService.java:298)
2026-06-24T23:01:09.4410120Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocksAcquired(DefaultWorkerLeaseService.java:294)
2026-06-24T23:01:09.4411003Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocks(DefaultWorkerLeaseService.java:286)
2026-06-24T23:01:09.4411893Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:130)
2026-06-24T23:01:09.4412822Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:135)
2026-06-24T23:01:09.4413822Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.runBatch(DefaultConditionalExecutionQueue.java:163)
2026-06-24T23:01:09.4414884Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.run(DefaultConditionalExecutionQueue.java:125)
2026-06-24T23:01:09.4415789Z 	... 2 more
2026-06-24T23:01:09.4415943Z 
2026-06-24T23:01:09.4415948Z 
2026-06-24T23:01:09.4416149Z BUILD FAILED in 44s
2026-06-24T23:01:09.4417173Z Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.
2026-06-24T23:01:09.4417879Z 
2026-06-24T23:01:09.4418697Z You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.
2026-06-24T23:01:09.4419694Z 
2026-06-24T23:01:09.4420730Z For more on this, please refer to https://docs.gradle.org/9.6.0/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.
2026-06-24T23:01:09.4423775Z 37 actionable tasks: 26 executed, 11 from cache
2026-06-24T23:01:09.4424182Z Configuration cache entry stored.
2026-06-24T23:01:09.8817399Z ##[error]Process completed with exit code 1.
2026-06-24T23:01:09.9050328Z Post job cleanup.
2026-06-24T23:01:10.1653971Z In post-action step
2026-06-24T23:01:10.1663432Z Enhanced Caching: This build is using the proprietary 'gradle-actions-caching' provider for optimized caching support. See https://github.com/gradle/actions/blob/main/DISTRIBUTION.md for terms of use and opt-out instructions.
2026-06-24T23:01:10.2422205Z ##[group]Stopping Gradle daemons
2026-06-24T23:01:10.2424224Z Stopping Gradle daemons for /home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s/gradle-9.6.0
2026-06-24T23:01:10.2443395Z [command]/home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s/gradle-9.6.0/bin/gradle --stop
2026-06-24T23:01:10.9865733Z Stopping Daemon(s)
2026-06-24T23:01:10.9972928Z 1 Daemon stopped
2026-06-24T23:01:11.0070253Z ##[endgroup]
2026-06-24T23:01:11.0071013Z Not performing cache-cleanup due to build failure
2026-06-24T23:01:11.0072537Z ##[group]Caching Gradle state
2026-06-24T23:01:11.4690517Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/***/*** --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:01:11.5049847Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/***/*** --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:01:11.5157892Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/***/*** --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:01:11.7809490Z Failed to save cache entry with path '/home/runner/.gradle/caches/*/kotlin-dsl/accessors/*/,/home/runner/.gradle/caches/*/kotlin-dsl/scripts/*/' and key: gradle-kotlin-dsl-v1-196cb20de7c38df3f45a11c306400383: ReserveCacheError: Unable to reserve cache with key gradle-kotlin-dsl-v1-196cb20de7c38df3f45a11c306400383, another job may be creating this cache.
2026-06-24T23:01:11.7993902Z Failed to save cache entry with path '/home/runner/.gradle/caches/*/groovy-dsl/*/' and key: gradle-groovy-dsl-v1-f89e44f70092d67690f53f5725593bea: ReserveCacheError: Unable to reserve cache with key gradle-groovy-dsl-v1-f89e44f70092d67690f53f5725593bea, another job may be creating this cache.
2026-06-24T23:01:12.7073845Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/***/*** --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:01:12.8156230Z Sent 13536859 of 13536859 (100.0%), 12.9 MBs/sec
2026-06-24T23:01:13.1336662Z Saved cache entry with key gradle-build-cache-v1-fce9bcedf8b314dd8b0c22cc638bda0d from /home/runner/.gradle/caches/build-cache-*/* in 1694ms
2026-06-24T23:01:15.1189852Z Sent 48758784 of 124991639 (39.0%), 46.5 MBs/sec
2026-06-24T23:01:16.1191610Z Sent 108134400 of 124991639 (86.5%), 51.6 MBs/sec
2026-06-24T23:01:16.6506949Z Sent 124991639 of 124991639 (100.0%), 47.1 MBs/sec
2026-06-24T23:01:16.9265968Z Saved cache entry with key gradle-transforms-v1-0062b9e388c73c6cdabec33df67c643b from /home/runner/.gradle/caches/transforms-4/*/,/home/runner/.gradle/caches/*/transforms/*/ in 4892ms
2026-06-24T23:01:17.3973261Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/***/*** --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:01:18.7228148Z Sent 4615627 of 4615627 (100.0%), 4.8 MBs/sec
2026-06-24T23:01:18.9949691Z Saved cache entry with key gradle-home-v1|Linux-X64|build[620c74083efa5b88ef904c2356f72d31]-9c64e7a569517b1fbc1e4054a0cd7191a289b6e9 from /home/runner/.gradle/caches,/home/runner/.gradle/notifications,/home/runner/.gradle/.setup-gradle in 1612ms
2026-06-24T23:01:18.9951404Z ##[endgroup]
2026-06-24T23:01:18.9962935Z Generating Job Summary
2026-06-24T23:01:18.9977880Z Completed post-action step
2026-06-24T23:01:19.0210388Z Post job cleanup.
2026-06-24T23:01:19.1565755Z Post job cleanup.
2026-06-24T23:01:19.2389627Z [command]/usr/bin/git version
2026-06-24T23:01:19.2432212Z git version 2.54.0
2026-06-24T23:01:19.2470769Z Temporarily overriding HOME='/home/runner/work/_temp/cd0af29c-7ade-40ce-ad1c-c35522d6da87' before making global git config changes
2026-06-24T23:01:19.2472393Z Adding repository directory to the temporary git global config as a safe directory
2026-06-24T23:01:19.2477701Z [command]/usr/bin/git config --global --add safe.directory /home/runner/work/***/***
2026-06-24T23:01:19.2521135Z Removing SSH command configuration
2026-06-24T23:01:19.2529800Z [command]/usr/bin/git config --local --name-only --get-regexp core\.sshCommand
2026-06-24T23:01:19.2568933Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'core\.sshCommand' && git config --local --unset-all 'core.sshCommand' || :"
2026-06-24T23:01:19.2811318Z Removing HTTP extra header
2026-06-24T23:01:19.2816724Z [command]/usr/bin/git config --local --name-only --get-regexp http\.https\:\/\/github\.com\/\.extraheader
2026-06-24T23:01:19.2851950Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'http\.https\:\/\/github\.com\/\.extraheader' && git config --local --unset-all 'http.https://github.com/.extraheader' || :"
2026-06-24T23:01:19.3066310Z Removing includeIf entries pointing to credentials config files
2026-06-24T23:01:19.3072166Z [command]/usr/bin/git config --local --name-only --get-regexp ^includeIf\.gitdir:
2026-06-24T23:01:19.3100836Z includeif.gitdir:/home/runner/work/***/***/.git.path
2026-06-24T23:01:19.3101579Z includeif.gitdir:/home/runner/work/***/***/.git/worktrees/*.path
2026-06-24T23:01:19.3102139Z includeif.gitdir:/github/workspace/.git.path
2026-06-24T23:01:19.3102633Z includeif.gitdir:/github/workspace/.git/worktrees/*.path
2026-06-24T23:01:19.3110199Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/home/runner/work/***/***/.git.path
2026-06-24T23:01:19.3132439Z /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3141990Z [command]/usr/bin/git config --local --unset includeif.gitdir:/home/runner/work/***/***/.git.path /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3177490Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/home/runner/work/***/***/.git/worktrees/*.path
2026-06-24T23:01:19.3199023Z /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3207705Z [command]/usr/bin/git config --local --unset includeif.gitdir:/home/runner/work/***/***/.git/worktrees/*.path /home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3238925Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/github/workspace/.git.path
2026-06-24T23:01:19.3266902Z /github/runner_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3274130Z [command]/usr/bin/git config --local --unset includeif.gitdir:/github/workspace/.git.path /github/runner_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3304297Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/github/workspace/.git/worktrees/*.path
2026-06-24T23:01:19.3325389Z /github/runner_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3334405Z [command]/usr/bin/git config --local --unset includeif.gitdir:/github/workspace/.git/worktrees/*.path /github/runner_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config
2026-06-24T23:01:19.3366298Z [command]/usr/bin/git submodule foreach --recursive git config --local --show-origin --name-only --get-regexp remote.origin.url
2026-06-24T23:01:19.3581132Z Removing credentials config '/home/runner/work/_temp/git-credentials-a88d1934-eda8-4417-91d1-17e8583f66ab.config'
2026-06-24T23:01:19.3732704Z Cleaning up orphan processes

2026-06-24T23:00:05.9706964Z Current runner version: '2.335.1'
2026-06-24T23:00:05.9741430Z ##[group]Runner Image Provisioner
2026-06-24T23:00:05.9743091Z Hosted Compute Agent
2026-06-24T23:00:05.9743993Z Version: 20260611.554
2026-06-24T23:00:05.9745049Z Commit: 5e0782fdc9014723d3be820dd114dd31555c2bd1
2026-06-24T23:00:05.9746253Z Build Date: 2026-06-11T21:40:46Z
2026-06-24T23:00:05.9747338Z Worker ID: {a88410f6-f92a-436e-a038-683cccb8c8c5}
2026-06-24T23:00:05.9748991Z Azure Region: eastus
2026-06-24T23:00:05.9749944Z ##[endgroup]
2026-06-24T23:00:05.9752954Z ##[group]Operating System
2026-06-24T23:00:05.9753984Z Ubuntu
2026-06-24T23:00:05.9754752Z 24.04.4
2026-06-24T23:00:05.9755621Z LTS
2026-06-24T23:00:05.9756398Z ##[endgroup]
2026-06-24T23:00:05.9757785Z ##[group]Runner Image
2026-06-24T23:00:05.9758846Z Image: ubuntu-24.04
2026-06-24T23:00:05.9759691Z Version: 20260615.205.1
2026-06-24T23:00:05.9761644Z Included Software: https://github.com/actions/runner-images/blob/ubuntu24/20260615.205/images/ubuntu/Ubuntu2404-Readme.md
2026-06-24T23:00:05.9764568Z Image Release: https://github.com/actions/runner-images/releases/tag/ubuntu24%2F20260615.205
2026-06-24T23:00:05.9766201Z ##[endgroup]
2026-06-24T23:00:05.9767992Z ##[group]GITHUB_TOKEN Permissions
2026-06-24T23:00:05.9770849Z Contents: read
2026-06-24T23:00:05.9771813Z Metadata: read
2026-06-24T23:00:05.9772905Z ##[endgroup]
2026-06-24T23:00:05.9775723Z Secret source: Actions
2026-06-24T23:00:05.9776921Z Prepare workflow directory
2026-06-24T23:00:06.0220468Z Prepare all required actions
2026-06-24T23:00:06.0273181Z Getting action download info
2026-06-24T23:00:06.2597299Z Download action repository 'actions/checkout@v7' (SHA:9c091bb21b7c1c1d1991bb908d89e4e9dddfe3e0)
2026-06-24T23:00:06.4190125Z Download action repository 'actions/setup-java@v5' (SHA:ad2b38190b15e4d6bdf0c97fb4fca8412226d287)
2026-06-24T23:00:06.6378575Z Download action repository 'gradle/actions@v6' (SHA:3f131e8634966bd73d06cc69884922b02e6faf92)
2026-06-24T23:00:07.2602146Z Download action repository 'actions/upload-artifact@v7' (SHA:043fb46d1a93c77aae656e7c1c64a875d1fc6a0a)
2026-06-24T23:00:07.4782985Z Complete job name: Build, unit-test, lint
2026-06-24T23:00:07.5539981Z ##[group]Run actions/checkout@v7
2026-06-24T23:00:07.5541022Z with:
2026-06-24T23:00:07.5541762Z   repository: kusl/myweather
2026-06-24T23:00:07.5546658Z   token: ***
2026-06-24T23:00:07.5547347Z   ssh-strict: true
2026-06-24T23:00:07.5547974Z   ssh-user: git
2026-06-24T23:00:07.5548582Z   persist-credentials: true
2026-06-24T23:00:07.5549283Z   clean: true
2026-06-24T23:00:07.5549888Z   sparse-checkout-cone-mode: true
2026-06-24T23:00:07.5550592Z   fetch-depth: 1
2026-06-24T23:00:07.5551307Z   fetch-tags: false
2026-06-24T23:00:07.5551913Z   show-progress: true
2026-06-24T23:00:07.5552856Z   lfs: false
2026-06-24T23:00:07.5553526Z   submodules: false
2026-06-24T23:00:07.5554153Z   set-safe-directory: true
2026-06-24T23:00:07.5554896Z   allow-unsafe-pr-checkout: false
2026-06-24T23:00:07.5555953Z ##[endgroup]
2026-06-24T23:00:07.6555995Z Syncing repository: kusl/myweather
2026-06-24T23:00:07.6558113Z ##[group]Getting Git version info
2026-06-24T23:00:07.6559262Z Working directory is '/home/runner/work/myweather/myweather'
2026-06-24T23:00:07.6560608Z [command]/usr/bin/git version
2026-06-24T23:00:07.8286054Z git version 2.54.0
2026-06-24T23:00:07.8307711Z ##[endgroup]
2026-06-24T23:00:07.8318952Z Temporarily overriding HOME='/home/runner/work/_temp/abefebb4-6737-4541-bb8f-9baffc0dd577' before making global git config changes
2026-06-24T23:00:07.8322286Z Adding repository directory to the temporary git global config as a safe directory
2026-06-24T23:00:07.8333708Z [command]/usr/bin/git config --global --add safe.directory /home/runner/work/myweather/myweather
2026-06-24T23:00:07.8380390Z Deleting the contents of '/home/runner/work/myweather/myweather'
2026-06-24T23:00:07.8385689Z ##[group]Determining repository object format
2026-06-24T23:00:07.8388241Z ##[endgroup]
2026-06-24T23:00:07.8390589Z ##[group]Initializing the repository
2026-06-24T23:00:07.8392965Z [command]/usr/bin/git init /home/runner/work/myweather/myweather
2026-06-24T23:00:07.8478039Z hint: Using 'master' as the name for the initial branch. This default branch name
2026-06-24T23:00:07.8479935Z hint: will change to "main" in Git 3.0. To configure the initial branch name
2026-06-24T23:00:07.8482332Z hint: to use in all of your new repositories, which will suppress this warning,
2026-06-24T23:00:07.8483975Z hint: call:
2026-06-24T23:00:07.8484696Z hint:
2026-06-24T23:00:07.8485889Z hint: 	git config --global init.defaultBranch <name>
2026-06-24T23:00:07.8487374Z hint:
2026-06-24T23:00:07.8488957Z hint: Names commonly chosen instead of 'master' are 'main', 'trunk' and
2026-06-24T23:00:07.8491244Z hint: 'development'. The just-created branch can be renamed via this command:
2026-06-24T23:00:07.8493469Z hint:
2026-06-24T23:00:07.8494653Z hint: 	git branch -m <name>
2026-06-24T23:00:07.8495730Z hint:
2026-06-24T23:00:07.8497265Z hint: Disable this message with "git config set advice.defaultBranchName false"
2026-06-24T23:00:07.8505092Z Initialized empty Git repository in /home/runner/work/myweather/myweather/.git/
2026-06-24T23:00:07.8510515Z [command]/usr/bin/git remote add origin https://github.com/kusl/myweather
2026-06-24T23:00:07.8541056Z ##[endgroup]
2026-06-24T23:00:07.8543779Z ##[group]Disabling automatic garbage collection
2026-06-24T23:00:07.8546021Z [command]/usr/bin/git config --local gc.auto 0
2026-06-24T23:00:07.8577057Z ##[endgroup]
2026-06-24T23:00:07.8579345Z ##[group]Setting up auth
2026-06-24T23:00:07.8581001Z Removing SSH command configuration
2026-06-24T23:00:07.8583996Z [command]/usr/bin/git config --local --name-only --get-regexp core\.sshCommand
2026-06-24T23:00:07.8620622Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'core\.sshCommand' && git config --local --unset-all 'core.sshCommand' || :"
2026-06-24T23:00:07.8945324Z Removing HTTP extra header
2026-06-24T23:00:07.8951135Z [command]/usr/bin/git config --local --name-only --get-regexp http\.https\:\/\/github\.com\/\.extraheader
2026-06-24T23:00:07.8986974Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'http\.https\:\/\/github\.com\/\.extraheader' && git config --local --unset-all 'http.https://github.com/.extraheader' || :"
2026-06-24T23:00:07.9209987Z Removing includeIf entries pointing to credentials config files
2026-06-24T23:00:07.9214000Z [command]/usr/bin/git config --local --name-only --get-regexp ^includeIf\.gitdir:
2026-06-24T23:00:07.9245029Z [command]/usr/bin/git submodule foreach --recursive git config --local --show-origin --name-only --get-regexp remote.origin.url
2026-06-24T23:00:07.9490696Z [command]/usr/bin/git config --file /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config http.https://github.com/.extraheader AUTHORIZATION: basic ***
2026-06-24T23:00:07.9540232Z [command]/usr/bin/git config --local includeIf.gitdir:/home/runner/work/myweather/myweather/.git.path /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:07.9577854Z [command]/usr/bin/git config --local includeIf.gitdir:/home/runner/work/myweather/myweather/.git/worktrees/*.path /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:07.9621516Z [command]/usr/bin/git config --local includeIf.gitdir:/github/workspace/.git.path /github/runner_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:07.9658178Z [command]/usr/bin/git config --local includeIf.gitdir:/github/workspace/.git/worktrees/*.path /github/runner_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:07.9688174Z ##[endgroup]
2026-06-24T23:00:07.9689705Z ##[group]Fetching the repository
2026-06-24T23:00:07.9697975Z [command]/usr/bin/git -c protocol.version=2 fetch --no-tags --prune --no-recurse-submodules --depth=1 origin +9c64e7a569517b1fbc1e4054a0cd7191a289b6e9:refs/remotes/origin/main
2026-06-24T23:00:08.2090061Z From https://github.com/kusl/myweather
2026-06-24T23:00:08.2092407Z  * [new ref]         9c64e7a569517b1fbc1e4054a0cd7191a289b6e9 -> origin/main
2026-06-24T23:00:08.2129861Z [command]/usr/bin/git branch --list --remote origin/main
2026-06-24T23:00:08.2162850Z   origin/main
2026-06-24T23:00:08.2171693Z [command]/usr/bin/git rev-parse refs/remotes/origin/main
2026-06-24T23:00:08.2194742Z 9c64e7a569517b1fbc1e4054a0cd7191a289b6e9
2026-06-24T23:00:08.2200466Z ##[endgroup]
2026-06-24T23:00:08.2201989Z ##[group]Determining the checkout info
2026-06-24T23:00:08.2204160Z ##[endgroup]
2026-06-24T23:00:08.2205199Z [command]/usr/bin/git sparse-checkout disable
2026-06-24T23:00:08.2249685Z [command]/usr/bin/git config --local --unset-all extensions.worktreeConfig
2026-06-24T23:00:08.2279355Z ##[group]Checking out the ref
2026-06-24T23:00:08.2282020Z [command]/usr/bin/git checkout --progress --force -B main refs/remotes/origin/main
2026-06-24T23:00:08.2431288Z Switched to a new branch 'main'
2026-06-24T23:00:08.2433829Z branch 'main' set up to track 'origin/main'.
2026-06-24T23:00:08.2443971Z ##[endgroup]
2026-06-24T23:00:08.2490435Z [command]/usr/bin/git log -1 --format=%H
2026-06-24T23:00:08.2521311Z 9c64e7a569517b1fbc1e4054a0cd7191a289b6e9
2026-06-24T23:00:08.2896871Z ##[group]Run actions/setup-java@v5
2026-06-24T23:00:08.2897927Z with:
2026-06-24T23:00:08.2898704Z   distribution: temurin
2026-06-24T23:00:08.2899591Z   java-version: 17
2026-06-24T23:00:08.2900427Z   java-package: jdk
2026-06-24T23:00:08.2901272Z   check-latest: false
2026-06-24T23:00:08.2902133Z   server-id: github
2026-06-24T23:00:08.2903217Z   server-username: GITHUB_ACTOR
2026-06-24T23:00:08.2904245Z   server-password: GITHUB_TOKEN
2026-06-24T23:00:08.2905259Z   overwrite-settings: true
2026-06-24T23:00:08.2906186Z   job-status: success
2026-06-24T23:00:08.2915844Z   token: ***
2026-06-24T23:00:08.2916643Z ##[endgroup]
2026-06-24T23:00:08.4400862Z ##[group]Installed distributions
2026-06-24T23:00:08.4468769Z Resolved Java 17.0.19+10 from tool-cache
2026-06-24T23:00:08.4470519Z Setting Java 17.0.19+10 as the default
2026-06-24T23:00:08.4481117Z Creating toolchains.xml for JDK version 17 from temurin
2026-06-24T23:00:08.4554235Z Writing to /home/runner/.m2/toolchains.xml
2026-06-24T23:00:08.4555729Z 
2026-06-24T23:00:08.4556462Z Java configuration:
2026-06-24T23:00:08.4557837Z   Distribution: temurin
2026-06-24T23:00:08.4559219Z   Version: 17.0.19+10
2026-06-24T23:00:08.4561107Z   Path: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:08.4563189Z 
2026-06-24T23:00:08.4564608Z ##[endgroup]
2026-06-24T23:00:08.4577859Z Creating settings.xml with server-id: github
2026-06-24T23:00:08.4590117Z Writing to /home/runner/.m2/settings.xml
2026-06-24T23:00:08.4997606Z ##[group]Run gradle/actions/setup-gradle@v6
2026-06-24T23:00:08.4998663Z with:
2026-06-24T23:00:08.4999405Z   cache-provider: enhanced
2026-06-24T23:00:08.5000348Z   cache-disabled: false
2026-06-24T23:00:08.5001197Z   cache-read-only: false
2026-06-24T23:00:08.5002084Z   cache-write-only: false
2026-06-24T23:00:08.5003123Z   cache-overwrite-existing: false
2026-06-24T23:00:08.5004096Z   cache-cleanup: on-success
2026-06-24T23:00:08.5005126Z   gradle-home-cache-includes: caches
notifications

2026-06-24T23:00:08.5006287Z   add-job-summary: always
2026-06-24T23:00:08.5007201Z   add-job-summary-as-pr-comment: never
2026-06-24T23:00:08.5008218Z   dependency-graph: disabled
2026-06-24T23:00:08.5009328Z   dependency-graph-report-dir: dependency-graph-reports
2026-06-24T23:00:08.5010626Z   dependency-graph-continue-on-failure: true
2026-06-24T23:00:08.5011728Z   build-scan-publish: false
2026-06-24T23:00:08.5012721Z   validate-wrappers: true
2026-06-24T23:00:08.5013676Z   allow-snapshot-wrappers: false
2026-06-24T23:00:08.5014682Z   gradle-home-cache-strict-match: false
2026-06-24T23:00:08.5015717Z   workflow-job-context: null
2026-06-24T23:00:08.5024837Z   github-token: ***
2026-06-24T23:00:08.5025614Z env:
2026-06-24T23:00:08.5026672Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:08.5028668Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:08.5030077Z ##[endgroup]
2026-06-24T23:00:08.9077009Z Merged default JDK locations into /home/runner/.m2/toolchains.xml
2026-06-24T23:00:08.9083663Z Enhanced Caching: This build is using the proprietary 'gradle-actions-caching' provider for optimized caching support. See https://github.com/gradle/actions/blob/main/DISTRIBUTION.md for terms of use and opt-out instructions.
2026-06-24T23:00:08.9841858Z ##[group]Restore Gradle state from cache
2026-06-24T23:00:09.0920734Z Cache hit for restore-key: gradle-home-v1|Linux-X64|build-and-test[b8a35c2f917259958c9abe8f2ff1b5ff]-c957757d32a9c8816b32cb30a57b3b4db41136cc
2026-06-24T23:00:09.2705218Z Received 4741508 of 4741508 (100.0%), 31.8 MBs/sec
2026-06-24T23:00:09.2707680Z Cache Size: ~5 MB (4741508 B)
2026-06-24T23:00:09.2737956Z [command]/usr/bin/tar -xf /home/runner/work/_temp/075245a8-cc5d-41b9-938a-13b26c9aaca0/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:09.4150027Z Cache restored successfully
2026-06-24T23:00:09.4159497Z Restored cache entry with key gradle-home-v1|Linux-X64|build-and-test[b8a35c2f917259958c9abe8f2ff1b5ff]-9c64e7a569517b1fbc1e4054a0cd7191a289b6e9 to /home/runner/.gradle/caches,/home/runner/.gradle/notifications,/home/runner/.gradle/.setup-gradle in 431ms
2026-06-24T23:00:09.4881046Z Cache hit for: gradle-wrapper-zips-v1-d66ca9ed62c02faccd154fac15917352
2026-06-24T23:00:09.4922718Z Cache hit for: gradle-generated-gradle-jars-v1-17a19f2c81c308e6469d4947d2674735
2026-06-24T23:00:09.4938692Z Cache hit for: gradle-transforms-v1-60fb0be62c8533071dc0725593578e5e
2026-06-24T23:00:09.4950556Z Cache hit for: gradle-dependencies-v1-ae55342bdeed9789391bef94c13f8113
2026-06-24T23:00:09.4989624Z Cache hit for: gradle-kotlin-dsl-v1-8b45ce5939a7aba0bbc7b47c7c5f730d
2026-06-24T23:00:09.5012805Z Cache hit for: gradle-instrumented-jars-v1-bb910fcacf708cb42c10c2b5b22b5d19
2026-06-24T23:00:09.5021311Z Cache hit for: gradle-groovy-dsl-v1-9942bfa88910b734547a8b6c7437a4bb
2026-06-24T23:00:09.5091851Z Cache hit for: gradle-java-toolchains-v1-57802292e8354a989f6e3bf59419e75e
2026-06-24T23:00:09.5105930Z Cache hit for: gradle-build-cache-v1-db801ae783d21da3b79b28911090505a
2026-06-24T23:00:09.7628593Z Received 60103 of 60103 (100.0%), 0.5 MBs/sec
2026-06-24T23:00:09.7631127Z Cache Size: ~0 MB (60103 B)
2026-06-24T23:00:09.8403669Z Received 102671 of 102671 (100.0%), 0.4 MBs/sec
2026-06-24T23:00:09.8406276Z Cache Size: ~0 MB (102671 B)
2026-06-24T23:00:09.8902187Z Received 89487 of 89487 (100.0%), 0.4 MBs/sec
2026-06-24T23:00:09.8906367Z Cache Size: ~0 MB (89487 B)
2026-06-24T23:00:10.0460455Z [command]/usr/bin/tar -xf /home/runner/work/_temp/19ccb194-95d3-409a-bafd-e6a6c900c2be/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:10.0931194Z Cache restored successfully
2026-06-24T23:00:10.0960203Z Restored cache entry with key gradle-instrumented-jars-v1-bb910fcacf708cb42c10c2b5b22b5d19 to /home/runner/.gradle/caches/jars-*/*/ in 678ms
2026-06-24T23:00:10.1223176Z [command]/usr/bin/tar -xf /home/runner/work/_temp/748954da-eb26-4c60-8bd2-1ec06581aec8/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:10.1990585Z [command]/usr/bin/tar -xf /home/runner/work/_temp/b5d25b47-ac65-4a77-8b8b-433fbf475a9f/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:10.2294560Z Cache restored successfully
2026-06-24T23:00:10.2520210Z Cache restored successfully
2026-06-24T23:00:10.2548521Z Restored cache entry with key gradle-kotlin-dsl-v1-8b45ce5939a7aba0bbc7b47c7c5f730d to /home/runner/.gradle/caches/*/kotlin-dsl/accessors/*/,/home/runner/.gradle/caches/*/kotlin-dsl/scripts/*/ in 837ms
2026-06-24T23:00:10.2559566Z Restored cache entry with key gradle-groovy-dsl-v1-9942bfa88910b734547a8b6c7437a4bb to /home/runner/.gradle/caches/*/groovy-dsl/*/ in 836ms
2026-06-24T23:00:10.5311859Z Received 42165970 of 42165970 (100.0%), 40.9 MBs/sec
2026-06-24T23:00:10.5313020Z Cache Size: ~40 MB (42165970 B)
2026-06-24T23:00:10.5354826Z Received 54525952 of 139956631 (39.0%), 51.3 MBs/sec
2026-06-24T23:00:10.5884133Z Received 4194304 of 424882737 (1.0%), 3.9 MBs/sec
2026-06-24T23:00:10.6203015Z Received 41943040 of 517451896 (8.1%), 40.0 MBs/sec
2026-06-24T23:00:10.6923899Z Received 20971520 of 143463615 (14.6%), 19.8 MBs/sec
2026-06-24T23:00:10.7594571Z Received 9380627 of 13574931 (69.1%), 8.8 MBs/sec
2026-06-24T23:00:10.7597776Z [command]/usr/bin/tar -xf /home/runner/work/_temp/cda2f4a5-829d-4602-9197-d521799040d5/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:10.8593197Z Received 13574931 of 13574931 (100.0%), 11.7 MBs/sec
2026-06-24T23:00:10.8614523Z Cache Size: ~13 MB (13574931 B)
2026-06-24T23:00:11.1880127Z [command]/usr/bin/tar -xf /home/runner/work/_temp/83b62e71-a729-489f-ab63-fa75293c36d9/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:11.2483495Z Cache restored successfully
2026-06-24T23:00:11.2484132Z Cache restored successfully
2026-06-24T23:00:11.2519875Z Restored cache entry with key gradle-build-cache-v1-db801ae783d21da3b79b28911090505a to /home/runner/.gradle/caches/build-cache-*/* in 1834ms
2026-06-24T23:00:11.2521478Z Restored cache entry with key gradle-generated-gradle-jars-v1-17a19f2c81c308e6469d4947d2674735 to /home/runner/.gradle/caches/9.6.0/generated-gradle-jars/gradle-api-9.6.0.jar in 1835ms
2026-06-24T23:00:11.5455944Z Received 135762327 of 139956631 (97.0%), 64.0 MBs/sec
2026-06-24T23:00:11.5508270Z Received 139956631 of 139956631 (100.0%), 65.8 MBs/sec
2026-06-24T23:00:11.5510303Z Cache Size: ~133 MB (139956631 B)
2026-06-24T23:00:11.5832975Z Received 104857600 of 424882737 (24.7%), 49.6 MBs/sec
2026-06-24T23:00:11.6204560Z Received 113246208 of 517451896 (21.9%), 53.9 MBs/sec
2026-06-24T23:00:11.6597773Z [command]/usr/bin/tar -xf /home/runner/work/_temp/7a3defc4-3e6c-4d3a-9344-66406036a05c/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:11.6916257Z Received 130023424 of 143463615 (90.6%), 61.7 MBs/sec
2026-06-24T23:00:11.9527581Z Received 143463615 of 143463615 (100.0%), 60.2 MBs/sec
2026-06-24T23:00:11.9529580Z Cache Size: ~137 MB (143463615 B)
2026-06-24T23:00:11.9809749Z [command]/usr/bin/tar -xf /home/runner/work/_temp/656ada02-16d4-415c-b911-bb72d4c50aac/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:12.0055144Z Cache restored successfully
2026-06-24T23:00:12.0380660Z Restored cache entry with key gradle-wrapper-zips-v1-d66ca9ed62c02faccd154fac15917352 to /home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s in 2621ms
2026-06-24T23:00:12.6066335Z Received 159383552 of 424882737 (37.5%), 50.0 MBs/sec
2026-06-24T23:00:12.6358839Z Received 289406976 of 517451896 (55.9%), 91.5 MBs/sec
2026-06-24T23:00:13.6173103Z Received 268435456 of 424882737 (63.2%), 63.3 MBs/sec
2026-06-24T23:00:13.6464562Z Received 482344960 of 517451896 (93.2%), 114.4 MBs/sec
2026-06-24T23:00:13.7634922Z Received 517451896 of 517451896 (100.0%), 119.1 MBs/sec
2026-06-24T23:00:13.7665480Z Cache Size: ~493 MB (517451896 B)
2026-06-24T23:00:13.8274357Z [command]/usr/bin/tar -xf /home/runner/work/_temp/1952853c-f59b-4b1a-a996-da3a9277879d/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:14.0633756Z Cache restored successfully
2026-06-24T23:00:14.0905881Z Restored cache entry with key gradle-transforms-v1-60fb0be62c8533071dc0725593578e5e to /home/runner/.gradle/caches/transforms-4/*/,/home/runner/.gradle/caches/*/transforms/*/ in 4665ms
2026-06-24T23:00:14.6193889Z Received 415236096 of 424882737 (97.7%), 78.4 MBs/sec
2026-06-24T23:00:14.6424004Z Received 424882737 of 424882737 (100.0%), 79.8 MBs/sec
2026-06-24T23:00:14.6434527Z Cache Size: ~405 MB (424882737 B)
2026-06-24T23:00:14.6679418Z [command]/usr/bin/tar -xf /home/runner/work/_temp/d2891274-18e7-48b7-b009-b74790bd89f8/cache.tzst -P -C /home/runner/work/myweather/myweather --use-compress-program unzstd
2026-06-24T23:00:15.6460080Z Cache restored successfully
2026-06-24T23:00:15.7437396Z Cache restored successfully
2026-06-24T23:00:16.4627427Z Restored cache entry with key gradle-dependencies-v1-ae55342bdeed9789391bef94c13f8113 to /home/runner/.gradle/caches/modules-*/files-*/*/*/*/*/ in 7045ms
2026-06-24T23:00:16.5099417Z Restored cache entry with key gradle-java-toolchains-v1-57802292e8354a989f6e3bf59419e75e to /home/runner/.gradle/jdks/jetbrains_s_r_o_-21-amd64-linux.2 in 7092ms
2026-06-24T23:00:16.5108781Z ##[endgroup]
2026-06-24T23:00:16.5208745Z ##[group]All Gradle Wrapper jars are valid
2026-06-24T23:00:16.5210025Z ✓ Found known Gradle Wrapper JAR files:
2026-06-24T23:00:16.5211030Z   497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7 gradle/wrapper/gradle-wrapper.jar
2026-06-24T23:00:16.5212270Z ##[endgroup]
2026-06-24T23:00:16.5512368Z ##[group]Run ./gradlew assembleDebug testDebugUnitTest lintDebug --stacktrace
2026-06-24T23:00:16.5513343Z [36;1m./gradlew assembleDebug testDebugUnitTest lintDebug --stacktrace[0m
2026-06-24T23:00:16.9299940Z shell: /usr/bin/bash -e {0}
2026-06-24T23:00:16.9300320Z env:
2026-06-24T23:00:16.9300740Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:16.9301350Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:16.9301846Z   GRADLE_ACTION_ID: gradle/actions/setup-gradle
2026-06-24T23:00:16.9302226Z   GRADLE_USER_HOME: /home/runner/.gradle
2026-06-24T23:00:16.9302924Z   GRADLE_BUILD_ACTION_SETUP_COMPLETED: true
2026-06-24T23:00:16.9303513Z   DEVELOCITY_INJECTION_INIT_SCRIPT_NAME: gradle-actions.inject-develocity.init.gradle
2026-06-24T23:00:16.9304055Z   DEVELOCITY_INJECTION_CUSTOM_VALUE: gradle-actions
2026-06-24T23:00:16.9304447Z   GITHUB_DEPENDENCY_GRAPH_ENABLED: false
2026-06-24T23:00:16.9304918Z ##[endgroup]
2026-06-24T23:00:17.7904390Z Starting a Gradle Daemon (subsequent builds will be faster)
2026-06-24T23:00:20.7879090Z Calculating task graph as no cached configuration is available for tasks: assembleDebug testDebugUnitTest lintDebug
2026-06-24T23:00:26.5862370Z 
2026-06-24T23:00:26.5886407Z > Configure project :app
2026-06-24T23:00:26.5913726Z WARNING: The option setting 'android.usesSdkInManifest.disallowed=false' is deprecated.
2026-06-24T23:00:26.5980521Z The current default is 'true'.
2026-06-24T23:00:26.5993771Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6004134Z WARNING: The option setting 'android.sdk.defaultTargetSdkToCompileSdkIfUnset=false' is deprecated.
2026-06-24T23:00:26.6005268Z The current default is 'true'.
2026-06-24T23:00:26.6006235Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6007349Z WARNING: The option setting 'android.enableAppCompileTimeRClass=false' is deprecated.
2026-06-24T23:00:26.6008436Z The current default is 'true'.
2026-06-24T23:00:26.6009234Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6010328Z WARNING: The option setting 'android.builtInKotlin=false' is deprecated.
2026-06-24T23:00:26.6011228Z The current default is 'true'.
2026-06-24T23:00:26.6012019Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6013222Z WARNING: The option setting 'android.newDsl=false' is deprecated.
2026-06-24T23:00:26.6014073Z The current default is 'true'.
2026-06-24T23:00:26.6014884Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6016001Z WARNING: The option setting 'android.r8.optimizedResourceShrinking=false' is deprecated.
2026-06-24T23:00:26.6017043Z The current default is 'true'.
2026-06-24T23:00:26.6017853Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6019024Z WARNING: The option setting 'android.defaults.buildfeatures.resvalues=true' is deprecated.
2026-06-24T23:00:26.6020489Z The current default is 'false'.
2026-06-24T23:00:26.6021295Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6022677Z WARNING: API 'applicationVariants' is obsolete and has been replaced with 'AndroidComponentsExtension'.
2026-06-24T23:00:26.6024013Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6025126Z The legacy variant API is disabled by default in AGP 9.0, but can be re-enabled by adding 
2026-06-24T23:00:26.6026139Z     android.newDsl=false
2026-06-24T23:00:26.6026825Z to this project's gradle.properties file.
2026-06-24T23:00:26.6027806Z For more information, see http://developer.android.com/build/r/new-dsl.
2026-06-24T23:00:26.6028526Z 
2026-06-24T23:00:26.6029524Z To determine what is calling applicationVariants, use -Pandroid.debug.obsoleteApi=true on the command line to display more information.
2026-06-24T23:00:26.6031486Z WARNING: API 'testVariants' is obsolete and has been replaced with 'AndroidComponentsExtension'.
2026-06-24T23:00:26.6032880Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6034011Z The legacy variant API is disabled by default in AGP 9.0, but can be re-enabled by adding 
2026-06-24T23:00:26.6035083Z     android.newDsl=false
2026-06-24T23:00:26.6035793Z to this project's gradle.properties file.
2026-06-24T23:00:26.6036751Z For more information, see http://developer.android.com/build/r/new-dsl.
2026-06-24T23:00:26.6037472Z 
2026-06-24T23:00:26.6038366Z To determine what is calling testVariants, use -Pandroid.debug.obsoleteApi=true on the command line to display more information.
2026-06-24T23:00:26.6039943Z WARNING: API 'unitTestVariants' is obsolete and has been replaced with 'AndroidComponentsExtension'.
2026-06-24T23:00:26.6041185Z It will be removed in version 10.0 of the Android Gradle plugin.
2026-06-24T23:00:26.6042308Z The legacy variant API is disabled by default in AGP 9.0, but can be re-enabled by adding 
2026-06-24T23:00:26.6043467Z     android.newDsl=false
2026-06-24T23:00:26.6044147Z to this project's gradle.properties file.
2026-06-24T23:00:26.6045096Z For more information, see http://developer.android.com/build/r/new-dsl.
2026-06-24T23:00:26.6045808Z 
2026-06-24T23:00:26.6046875Z To determine what is calling unitTestVariants, use -Pandroid.debug.obsoleteApi=true on the command line to display more information.
2026-06-24T23:00:26.6870926Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:26.6873799Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:26.7864848Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:26.7893705Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:26.7895280Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:26.7896708Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:26.7898203Z WARNING: The property android.dependency.excludeLibraryComponentsFromConstraints improves project import performance for very large projects. It should be enabled to improve performance.
2026-06-24T23:00:26.7899522Z To suppress this warning, add android.generateSyncIssueWhenLibraryConstraintsAreEnabled=false to gradle.properties
2026-06-24T23:00:27.5876083Z w: ⚠️ Deprecated 'org.jetbrains.kotlin.android' plugin usage
2026-06-24T23:00:27.5877542Z The 'org.jetbrains.kotlin.android' plugin in project ':app' is no longer required for Kotlin support since AGP 9.0.
2026-06-24T23:00:27.5879680Z Solution: Remove both `android.builtInKotlin=true` and `android.newDsl=false` from `gradle.properties`, then migrate to built-in Kotlin.
2026-06-24T23:00:27.5881071Z See https://kotl.in/gradle/agp-built-in-kotlin for more details.
2026-06-24T23:00:27.5903019Z 
2026-06-24T23:00:30.8865694Z 
2026-06-24T23:00:30.8893561Z > Task :app:preBuild UP-TO-DATE
2026-06-24T23:00:30.8895131Z > Task :app:generateDebugAssets UP-TO-DATE
2026-06-24T23:00:30.8902370Z > Task :app:checkKotlinGradlePluginConfigurationErrors SKIPPED
2026-06-24T23:00:30.8903590Z > Task :app:preDebugUnitTestBuild UP-TO-DATE
2026-06-24T23:00:30.8904461Z > Task :app:preDebugBuild UP-TO-DATE
2026-06-24T23:00:30.8905268Z > Task :app:mergeDebugNativeDebugMetadata NO-SOURCE
2026-06-24T23:00:30.9866329Z > Task :app:generateDebugBuildConfig FROM-CACHE
2026-06-24T23:00:30.9896420Z > Task :app:generateDebugResValues FROM-CACHE
2026-06-24T23:00:31.0868844Z > Task :app:createDebugCompatibleScreenManifests
2026-06-24T23:00:31.0873583Z > Task :app:mapDebugSourceSetPaths
2026-06-24T23:00:31.0893896Z > Task :app:generateDebugResources FROM-CACHE
2026-06-24T23:00:31.0894773Z > Task :app:extractDeepLinksDebug FROM-CACHE
2026-06-24T23:00:31.0895661Z > Task :app:packageDebugResources FROM-CACHE
2026-06-24T23:00:31.3874032Z > Task :app:processDebugNavigationResources FROM-CACHE
2026-06-24T23:00:31.3902110Z > Task :app:compileDebugNavigationResources FROM-CACHE
2026-06-24T23:00:31.3913308Z > Task :app:checkDebugAarMetadata
2026-06-24T23:00:31.3935009Z > Task :app:javaPreCompileDebug FROM-CACHE
2026-06-24T23:00:31.4862044Z > Task :app:mergeDebugResources FROM-CACHE
2026-06-24T23:00:34.4866358Z > Task :app:generateDebugGlobalSynthetics FROM-CACHE
2026-06-24T23:00:34.5894176Z > Task :app:processDebugMainManifest FROM-CACHE
2026-06-24T23:00:34.5953253Z > Task :app:processDebugManifest FROM-CACHE
2026-06-24T23:00:34.5988979Z > Task :app:processDebugManifestForPackage FROM-CACHE
2026-06-24T23:00:34.6031195Z > Task :app:desugarDebugFileDependencies FROM-CACHE
2026-06-24T23:00:34.6080037Z > Task :app:mergeDebugJniLibFolders
2026-06-24T23:00:34.6110135Z > Task :app:mergeDebugAssets
2026-06-24T23:00:34.6864625Z > Task :app:compressDebugAssets FROM-CACHE
2026-06-24T23:00:34.9889721Z > Task :app:mergeDebugNativeLibs
2026-06-24T23:00:34.9913959Z > Task :app:checkDebugDuplicateClasses
2026-06-24T23:00:35.2902038Z > Task :app:parseDebugLocalResources FROM-CACHE
2026-06-24T23:00:35.5875569Z > Task :app:processDebugResources FROM-CACHE
2026-06-24T23:00:35.7874113Z > Task :app:validateSigningDebug
2026-06-24T23:00:35.7913214Z > Task :app:mergeLibDexDebug FROM-CACHE
2026-06-24T23:00:35.7943059Z > Task :app:writeDebugAppMetadata
2026-06-24T23:00:35.7973212Z > Task :app:writeDebugSigningConfigVersions
2026-06-24T23:00:35.8003150Z > Task :app:javaPreCompileDebugUnitTest FROM-CACHE
2026-06-24T23:00:35.8033224Z > Task :app:preDebugAndroidTestBuild SKIPPED
2026-06-24T23:00:35.8063185Z > Task :app:generateDebugAndroidTestResValues FROM-CACHE
2026-06-24T23:00:35.8093076Z > Task :app:extractProguardFiles
2026-06-24T23:00:35.9874317Z > Task :app:mergeExtDexDebug FROM-CACHE
2026-06-24T23:00:36.1862385Z > Task :app:stripDebugDebugSymbols
2026-06-24T23:00:41.8879387Z > Task :app:kspDebugKotlin
2026-06-24T23:00:52.1889258Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/data/remote/NetworkModule.kt:35:29 Unresolved reference 'HttpCacheInterceptor'.
2026-06-24T23:00:52.1890488Z 
2026-06-24T23:00:52.1891838Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/data/remote/NetworkModule.kt:36:14 Unresolved reference 'addInterceptor'.
2026-06-24T23:00:52.1893535Z > Task :app:compileDebugKotlin FAILED
2026-06-24T23:00:52.1895153Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:42:41 Unresolved reference 'AlertsBanner'.
2026-06-24T23:00:52.1897343Z gradle/actions: Writing build results to /home/runner/work/_temp/.gradle-actions/build-results/__run-1782342021667.json
2026-06-24T23:00:52.1899692Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:44:41 Unresolved reference 'HourlyStrip'.
2026-06-24T23:00:52.1901926Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:45:41 Unresolved reference 'LocationSourcesCard'.
2026-06-24T23:00:52.1904310Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:46:41 Unresolved reference 'ObservationCard'.
2026-06-24T23:00:52.1906594Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:47:41 Unresolved reference 'PeriodDetailSheet'.
2026-06-24T23:00:52.1908909Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:105:40 Unresolved reference 'AlertsBanner'.
2026-06-24T23:00:52.1911426Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:185:28 Unresolved reference 'ObservationCard'.
2026-06-24T23:00:52.1913854Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:201:25 Unresolved reference 'HourlyStrip'.
2026-06-24T23:00:52.1916064Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:203:67 Unresolved reference 'it'.
2026-06-24T23:00:52.1918327Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:208:28 Unresolved reference 'LocationSourcesCard'.
2026-06-24T23:00:52.1920672Z e: file:///home/runner/work/myweather/myweather/app/src/main/java/com/kusl/myweather/ui/dashboard/DashboardScreen.kt:229:13 Unresolved reference 'PeriodDetailSheet'.
2026-06-24T23:00:52.2913865Z 
2026-06-24T23:00:52.2913893Z 
2026-06-24T23:00:52.2941179Z FAILURE: Build failed with an exception.
2026-06-24T23:00:52.2942461Z [Incubating] Problems report is available at: file:///home/runner/work/myweather/myweather/build/reports/problems/problems-report.html
2026-06-24T23:00:52.2977791Z 
2026-06-24T23:00:52.2977847Z 
2026-06-24T23:00:52.2983624Z * What went wrong:
2026-06-24T23:00:52.2984490Z Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.
2026-06-24T23:00:52.2986307Z Execution failed for task ':app:compileDebugKotlin' (registered by plugin 'org.jetbrains.kotlin.android').
2026-06-24T23:00:52.2987095Z 
2026-06-24T23:00:52.2988047Z > A failure occurred while executing org.jetbrains.kotlin.compilerRunner.btapi.BuildToolsApiCompilationWork
2026-06-24T23:00:52.2989717Z You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.
2026-06-24T23:00:52.2991128Z    > Compilation error. See log for more details
2026-06-24T23:00:52.2991548Z 
2026-06-24T23:00:52.2991874Z 
2026-06-24T23:00:52.2993039Z For more on this, please refer to https://docs.gradle.org/9.6.0/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.
2026-06-24T23:00:52.2994571Z * Try:
2026-06-24T23:00:52.2995038Z 35 actionable tasks: 14 executed, 21 from cache
2026-06-24T23:00:52.2995981Z > Run with --info or --debug option to get more log output.
2026-06-24T23:00:52.2996645Z Configuration cache entry stored.
2026-06-24T23:00:52.2997656Z > Run with --scan to get full insights from a Build Scan (powered by Develocity).
2026-06-24T23:00:52.2998617Z > Get more help at https://help.gradle.org.
2026-06-24T23:00:52.2999175Z 
2026-06-24T23:00:52.2999510Z * Exception is:
2026-06-24T23:00:52.3000825Z org.gradle.api.tasks.TaskExecutionException: Execution failed for task ':app:compileDebugKotlin' (registered by plugin 'org.jetbrains.kotlin.android').
2026-06-24T23:00:52.3003255Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.lambda$executeIfValid$1(ExecuteActionsTaskExecuter.java:135)
2026-06-24T23:00:52.3005225Z 	at org.gradle.internal.Try$Failure.ifSuccessfulOrElse(Try.java:288)
2026-06-24T23:00:52.3006742Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:133)
2026-06-24T23:00:52.3008667Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.execute(ExecuteActionsTaskExecuter.java:121)
2026-06-24T23:00:52.3010688Z 	at org.gradle.api.internal.tasks.execution.ProblemsTaskPathTrackingTaskExecuter.execute(ProblemsTaskPathTrackingTaskExecuter.java:41)
2026-06-24T23:00:52.3012977Z 	at org.gradle.api.internal.tasks.execution.ResolveTaskExecutionModeExecuter.execute(ResolveTaskExecutionModeExecuter.java:51)
2026-06-24T23:00:52.3014965Z 	at org.gradle.api.internal.tasks.execution.FinalizePropertiesTaskExecuter.execute(FinalizePropertiesTaskExecuter.java:46)
2026-06-24T23:00:52.3016784Z 	at org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter.execute(SkipTaskWithNoActionsExecuter.java:57)
2026-06-24T23:00:52.3017860Z 	at org.gradle.api.internal.tasks.execution.SkipOnlyIfTaskExecuter.execute(SkipOnlyIfTaskExecuter.java:74)
2026-06-24T23:00:52.3018888Z 	at org.gradle.api.internal.tasks.execution.CatchExceptionTaskExecuter.execute(CatchExceptionTaskExecuter.java:36)
2026-06-24T23:00:52.3019922Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.executeTask(EventFiringTaskExecuter.java:77)
2026-06-24T23:00:52.3020907Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:55)
2026-06-24T23:00:52.3021873Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:52)
2026-06-24T23:00:52.3023653Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:00:52.3027224Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:00:52.3029028Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:00:52.3030535Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3032055Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:00:52.3033812Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3035323Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:00:52.3036856Z 	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter.execute(EventFiringTaskExecuter.java:52)
2026-06-24T23:00:52.3038340Z 	at org.gradle.execution.plan.DefaultNodeExecutor.executeLocalTaskNode(DefaultNodeExecutor.java:55)
2026-06-24T23:00:52.3039973Z 	at org.gradle.execution.plan.DefaultNodeExecutor.execute(DefaultNodeExecutor.java:34)
2026-06-24T23:00:52.3041572Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:355)
2026-06-24T23:00:52.3043619Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:343)
2026-06-24T23:00:52.3045606Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.lambda$execute$0(DefaultTaskExecutionGraph.java:339)
2026-06-24T23:00:52.3047375Z 	at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:84)
2026-06-24T23:00:52.3049102Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:339)
2026-06-24T23:00:52.3051134Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:328)
2026-06-24T23:00:52.3053283Z 	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.execute(DefaultPlanExecutor.java:459)
2026-06-24T23:00:52.3054687Z 	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.run(DefaultPlanExecutor.java:376)
2026-06-24T23:00:52.3055979Z 	at org.gradle.execution.plan.DefaultPlanExecutor.process(DefaultPlanExecutor.java:111)
2026-06-24T23:00:52.3057444Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph.executeWithServices(DefaultTaskExecutionGraph.java:146)
2026-06-24T23:00:52.3059150Z 	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph.execute(DefaultTaskExecutionGraph.java:131)
2026-06-24T23:00:52.3060624Z 	at org.gradle.execution.SelectedTaskExecutionAction.execute(SelectedTaskExecutionAction.java:35)
2026-06-24T23:00:52.3062292Z 	at org.gradle.execution.BuildOperationFiringBuildWorkerExecutor$ExecuteTasks.call(BuildOperationFiringBuildWorkerExecutor.java:54)
2026-06-24T23:00:52.3064629Z 	at org.gradle.execution.BuildOperationFiringBuildWorkerExecutor$ExecuteTasks.call(BuildOperationFiringBuildWorkerExecutor.java:43)
2026-06-24T23:00:52.3066609Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:00:52.3070798Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:00:52.3072919Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:00:52.3074481Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3076014Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:00:52.3077540Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3079056Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:00:52.3080724Z 	at org.gradle.execution.BuildOperationFiringBuildWorkerExecutor.execute(BuildOperationFiringBuildWorkerExecutor.java:40)
2026-06-24T23:00:52.3082457Z 	at org.gradle.internal.build.DefaultBuildLifecycleController.lambda$executeTasks$11(DefaultBuildLifecycleController.java:323)
2026-06-24T23:00:52.3084312Z 	at org.gradle.internal.model.StateTransitionController.doTransition(StateTransitionController.java:304)
2026-06-24T23:00:52.3085760Z 	at org.gradle.internal.model.StateTransitionController.lambda$tryTransition$9(StateTransitionController.java:215)
2026-06-24T23:00:52.3087206Z 	at org.gradle.internal.work.DefaultSynchronizer.withLock(DefaultSynchronizer.java:45)
2026-06-24T23:00:52.3088462Z 	at org.gradle.internal.model.StateTransitionController.tryTransition(StateTransitionController.java:215)
2026-06-24T23:00:52.3090091Z 	at org.gradle.internal.build.DefaultBuildLifecycleController.executeTasks(DefaultBuildLifecycleController.java:314)
2026-06-24T23:00:52.3091704Z 	at org.gradle.internal.build.DefaultBuildWorkGraphController$DefaultBuildWorkGraph.runWork(DefaultBuildWorkGraphController.java:220)
2026-06-24T23:00:52.3093526Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$runAndReleaseLocks$0(DefaultWorkerLeaseService.java:300)
2026-06-24T23:00:52.3094847Z 	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
2026-06-24T23:00:52.3096160Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAndReleaseLocks(DefaultWorkerLeaseService.java:298)
2026-06-24T23:00:52.3097585Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocksAcquired(DefaultWorkerLeaseService.java:294)
2026-06-24T23:00:52.3098945Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocks(DefaultWorkerLeaseService.java:286)
2026-06-24T23:00:52.3100329Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:130)
2026-06-24T23:00:52.3101946Z 	at org.gradle.composite.internal.DefaultBuildController.doRun(DefaultBuildController.java:182)
2026-06-24T23:00:52.3103592Z 	at org.gradle.composite.internal.DefaultBuildController$BuildOpRunnable.lambda$run$0(DefaultBuildController.java:199)
2026-06-24T23:00:52.3105002Z 	at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:84)
2026-06-24T23:00:52.3106432Z 	at org.gradle.composite.internal.DefaultBuildController$BuildOpRunnable.run(DefaultBuildController.java:199)
2026-06-24T23:00:52.3107898Z 	at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
2026-06-24T23:00:52.3109285Z 	at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:47)
2026-06-24T23:00:52.3111552Z Caused by: org.gradle.workers.internal.DefaultWorkerExecutor$WorkExecutionException: A failure occurred while executing org.jetbrains.kotlin.compilerRunner.btapi.BuildToolsApiCompilationWork
2026-06-24T23:00:52.3114093Z 	at org.gradle.workers.internal.DefaultWorkerExecutor$WorkItemExecution.waitForCompletion(DefaultWorkerExecutor.java:278)
2026-06-24T23:00:52.3115827Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.lambda$waitForItemsAndGatherFailures$2(DefaultAsyncWorkTracker.java:132)
2026-06-24T23:00:52.3117138Z 	at org.gradle.internal.Factories$1.create(Factories.java:30)
2026-06-24T23:00:52.3118354Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$withoutLocksBlocking$3(DefaultWorkerLeaseService.java:410)
2026-06-24T23:00:52.3119829Z 	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
2026-06-24T23:00:52.3121250Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withoutLocksBlocking(DefaultWorkerLeaseService.java:405)
2026-06-24T23:00:52.3122967Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.blocking(DefaultWorkerLeaseService.java:255)
2026-06-24T23:00:52.3124372Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.blocking(DefaultWorkerLeaseService.java:237)
2026-06-24T23:00:52.3125968Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.lambda$waitForItemsAndGatherFailures$3(DefaultAsyncWorkTracker.java:128)
2026-06-24T23:00:52.3127274Z 	at org.gradle.internal.Factories$1.create(Factories.java:30)
2026-06-24T23:00:52.3128660Z 	at org.gradle.internal.resources.AbstractResourceLockRegistry.whileDisallowingLockChanges(AbstractResourceLockRegistry.java:50)
2026-06-24T23:00:52.3130593Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.whileDisallowingProjectLockChanges(DefaultWorkerLeaseService.java:260)
2026-06-24T23:00:52.3132394Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForItemsAndGatherFailures(DefaultAsyncWorkTracker.java:127)
2026-06-24T23:00:52.3134179Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForItemsAndGatherFailures(DefaultAsyncWorkTracker.java:93)
2026-06-24T23:00:52.3135700Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForAll(DefaultAsyncWorkTracker.java:79)
2026-06-24T23:00:52.3137126Z 	at org.gradle.internal.work.DefaultAsyncWorkTracker.waitForCompletion(DefaultAsyncWorkTracker.java:67)
2026-06-24T23:00:52.3138419Z 	at org.gradle.api.internal.tasks.execution.TaskExecution$3.run(TaskExecution.java:267)
2026-06-24T23:00:52.3139725Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(DefaultBuildOperationRunner.java:30)
2026-06-24T23:00:52.3141227Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(DefaultBuildOperationRunner.java:27)
2026-06-24T23:00:52.3142951Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:00:52.3144483Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3146028Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:00:52.3147584Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3149369Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.run(DefaultBuildOperationRunner.java:48)
2026-06-24T23:00:52.3150762Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.executeAction(TaskExecution.java:244)
2026-06-24T23:00:52.3153017Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.executeActions(TaskExecution.java:227)
2026-06-24T23:00:52.3154843Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.executeWithPreviousOutputFiles(TaskExecution.java:210)
2026-06-24T23:00:52.3156555Z 	at org.gradle.api.internal.tasks.execution.TaskExecution.execute(TaskExecution.java:176)
2026-06-24T23:00:52.3158124Z 	at org.gradle.internal.execution.steps.ExecuteStep.executeInternal(ExecuteStep.java:167)
2026-06-24T23:00:52.3159593Z 	at org.gradle.internal.execution.steps.ExecuteStep.access$000(ExecuteStep.java:47)
2026-06-24T23:00:52.3161255Z 	at org.gradle.internal.execution.steps.ExecuteStep$1.call(ExecuteStep.java:137)
2026-06-24T23:00:52.3162973Z 	at org.gradle.internal.execution.steps.ExecuteStep$1.call(ExecuteStep.java:134)
2026-06-24T23:00:52.3164828Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:00:52.3167139Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:00:52.3169111Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:00:52.3186509Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3188159Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:00:52.3189833Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3191391Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:00:52.3192876Z 	at org.gradle.internal.execution.steps.ExecuteStep.execute(ExecuteStep.java:134)
2026-06-24T23:00:52.3194068Z 	at org.gradle.internal.execution.steps.ExecuteStep$Mutable.execute(ExecuteStep.java:80)
2026-06-24T23:00:52.3195337Z 	at org.gradle.internal.execution.steps.CancelExecutionStep.execute(CancelExecutionStep.java:42)
2026-06-24T23:00:52.3196710Z 	at org.gradle.internal.execution.steps.TimeoutStep.executeWithoutTimeout(TimeoutStep.java:75)
2026-06-24T23:00:52.3197943Z 	at org.gradle.internal.execution.steps.TimeoutStep.execute(TimeoutStep.java:55)
2026-06-24T23:00:52.3199322Z 	at org.gradle.internal.execution.steps.PreCreateOutputParentsStep.execute(PreCreateOutputParentsStep.java:51)
2026-06-24T23:00:52.3200927Z 	at org.gradle.internal.execution.steps.PreCreateOutputParentsStep.execute(PreCreateOutputParentsStep.java:29)
2026-06-24T23:00:52.3202886Z 	at org.gradle.internal.execution.steps.RemovePreviousOutputsStep.executeMutable(RemovePreviousOutputsStep.java:67)
2026-06-24T23:00:52.3204579Z 	at org.gradle.internal.execution.steps.RemovePreviousOutputsStep.executeMutable(RemovePreviousOutputsStep.java:39)
2026-06-24T23:00:52.3206009Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3207412Z 	at org.gradle.internal.execution.steps.BroadcastChangingOutputsStep.execute(BroadcastChangingOutputsStep.java:42)
2026-06-24T23:00:52.3209104Z 	at org.gradle.internal.execution.steps.BroadcastChangingOutputsStep.execute(BroadcastChangingOutputsStep.java:24)
2026-06-24T23:00:52.3210866Z 	at org.gradle.internal.execution.steps.CaptureOutputsAfterExecutionStep.execute(CaptureOutputsAfterExecutionStep.java:69)
2026-06-24T23:00:52.3212984Z 	at org.gradle.internal.execution.steps.CaptureOutputsAfterExecutionStep.execute(CaptureOutputsAfterExecutionStep.java:46)
2026-06-24T23:00:52.3214772Z 	at org.gradle.internal.execution.steps.ResolveInputChangesStep.executeMutable(ResolveInputChangesStep.java:39)
2026-06-24T23:00:52.3216695Z 	at org.gradle.internal.execution.steps.ResolveInputChangesStep.executeMutable(ResolveInputChangesStep.java:28)
2026-06-24T23:00:52.3218100Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3219400Z 	at org.gradle.internal.execution.steps.BuildCacheStep.executeWithoutCache(BuildCacheStep.java:189)
2026-06-24T23:00:52.3242265Z 	at org.gradle.internal.execution.steps.BuildCacheStep.executeAndStoreInCache(BuildCacheStep.java:145)
2026-06-24T23:00:52.3243978Z 	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$executeWithCache$4(BuildCacheStep.java:104)
2026-06-24T23:00:52.3245441Z 	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$executeWithCache$5(BuildCacheStep.java:104)
2026-06-24T23:00:52.3246542Z 	at org.gradle.internal.Try$Success.map(Try.java:170)
2026-06-24T23:00:52.3247831Z 	at org.gradle.internal.execution.steps.BuildCacheStep.executeWithCache(BuildCacheStep.java:88)
2026-06-24T23:00:52.3249188Z 	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$execute$0(BuildCacheStep.java:75)
2026-06-24T23:00:52.3250247Z 	at org.gradle.internal.Either$Left.fold(Either.java:116)
2026-06-24T23:00:52.3251214Z 	at org.gradle.internal.execution.caching.CachingState.fold(CachingState.java:62)
2026-06-24T23:00:52.3252399Z 	at org.gradle.internal.execution.steps.BuildCacheStep.execute(BuildCacheStep.java:74)
2026-06-24T23:00:52.3254002Z 	at org.gradle.internal.execution.steps.BuildCacheStep.execute(BuildCacheStep.java:49)
2026-06-24T23:00:52.3255434Z 	at org.gradle.internal.execution.steps.StoreExecutionStateStep.executeMutable(StoreExecutionStateStep.java:46)
2026-06-24T23:00:52.3257051Z 	at org.gradle.internal.execution.steps.StoreExecutionStateStep.executeMutable(StoreExecutionStateStep.java:35)
2026-06-24T23:00:52.3258426Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3259689Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.executeBecause(SkipUpToDateStep.java:75)
2026-06-24T23:00:52.3261090Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.lambda$execute$2(SkipUpToDateStep.java:53)
2026-06-24T23:00:52.3262426Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.execute(SkipUpToDateStep.java:53)
2026-06-24T23:00:52.3263869Z 	at org.gradle.internal.execution.steps.SkipUpToDateStep.execute(SkipUpToDateStep.java:35)
2026-06-24T23:00:52.3265510Z 	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsFinishedStep.execute(MarkSnapshottingInputsFinishedStep.java:37)
2026-06-24T23:00:52.3267492Z 	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsFinishedStep.execute(MarkSnapshottingInputsFinishedStep.java:27)
2026-06-24T23:00:52.3269423Z 	at org.gradle.internal.execution.steps.ResolveMutableCachingStateStep.executeDelegate(ResolveMutableCachingStateStep.java:70)
2026-06-24T23:00:52.3271517Z 	at org.gradle.internal.execution.steps.ResolveMutableCachingStateStep.executeDelegate(ResolveMutableCachingStateStep.java:32)
2026-06-24T23:00:52.3273525Z 	at org.gradle.internal.execution.steps.AbstractResolveCachingStateStep.execute(AbstractResolveCachingStateStep.java:69)
2026-06-24T23:00:52.3275289Z 	at org.gradle.internal.execution.steps.AbstractResolveCachingStateStep.execute(AbstractResolveCachingStateStep.java:37)
2026-06-24T23:00:52.3276888Z 	at org.gradle.internal.execution.steps.ResolveChangesStep.executeMutable(ResolveChangesStep.java:63)
2026-06-24T23:00:52.3278312Z 	at org.gradle.internal.execution.steps.ResolveChangesStep.executeMutable(ResolveChangesStep.java:34)
2026-06-24T23:00:52.3279563Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3280796Z 	at org.gradle.internal.execution.steps.ValidateStep$Mutable.executeDelegate(ValidateStep.java:79)
2026-06-24T23:00:52.3282096Z 	at org.gradle.internal.execution.steps.ValidateStep$Mutable.executeDelegate(ValidateStep.java:65)
2026-06-24T23:00:52.3285242Z 	at org.gradle.internal.execution.steps.ValidateStep.execute(ValidateStep.java:105)
2026-06-24T23:00:52.3286435Z 	at org.gradle.internal.execution.steps.ValidateStep$Mutable.execute(ValidateStep.java:65)
2026-06-24T23:00:52.3288000Z 	at org.gradle.internal.execution.steps.CaptureMutableStateBeforeExecutionStep.executeMutable(CaptureMutableStateBeforeExecutionStep.java:86)
2026-06-24T23:00:52.3289910Z 	at org.gradle.internal.execution.steps.CaptureMutableStateBeforeExecutionStep.execute(CaptureMutableStateBeforeExecutionStep.java:65)
2026-06-24T23:00:52.3291706Z 	at org.gradle.internal.execution.steps.CaptureMutableStateBeforeExecutionStep.execute(CaptureMutableStateBeforeExecutionStep.java:45)
2026-06-24T23:00:52.3293696Z 	at org.gradle.internal.execution.steps.SkipEmptyMutableWorkStep.executeWithNonEmptySources(SkipEmptyMutableWorkStep.java:210)
2026-06-24T23:00:52.3295465Z 	at org.gradle.internal.execution.steps.SkipEmptyMutableWorkStep.executeMutable(SkipEmptyMutableWorkStep.java:90)
2026-06-24T23:00:52.3297076Z 	at org.gradle.internal.execution.steps.SkipEmptyMutableWorkStep.executeMutable(SkipEmptyMutableWorkStep.java:53)
2026-06-24T23:00:52.3298427Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3299953Z 	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsStartedStep.execute(MarkSnapshottingInputsStartedStep.java:38)
2026-06-24T23:00:52.3301682Z 	at org.gradle.internal.execution.steps.LoadPreviousExecutionStateStep.executeMutable(LoadPreviousExecutionStateStep.java:36)
2026-06-24T23:00:52.3303706Z 	at org.gradle.internal.execution.steps.LoadPreviousExecutionStateStep.executeMutable(LoadPreviousExecutionStateStep.java:23)
2026-06-24T23:00:52.3305147Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3306476Z 	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.executeMutable(HandleStaleOutputsStep.java:77)
2026-06-24T23:00:52.3307894Z 	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.executeMutable(HandleStaleOutputsStep.java:43)
2026-06-24T23:00:52.3309227Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3310718Z 	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.lambda$executeMutable$0(AssignMutableWorkspaceStep.java:34)
2026-06-24T23:00:52.3312278Z 	at org.gradle.api.internal.tasks.execution.TaskExecution$4.withWorkspace(TaskExecution.java:305)
2026-06-24T23:00:52.3315980Z 	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.executeMutable(AssignMutableWorkspaceStep.java:30)
2026-06-24T23:00:52.3317667Z 	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.executeMutable(AssignMutableWorkspaceStep.java:21)
2026-06-24T23:00:52.3318984Z 	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
2026-06-24T23:00:52.3320161Z 	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:40)
2026-06-24T23:00:52.3321391Z 	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:23)
2026-06-24T23:00:52.3323201Z 	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.lambda$execute$2(ExecuteWorkBuildOperationFiringStep.java:67)
2026-06-24T23:00:52.3325003Z 	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:67)
2026-06-24T23:00:52.3326785Z 	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:39)
2026-06-24T23:00:52.3328258Z 	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:46)
2026-06-24T23:00:52.3329439Z 	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:34)
2026-06-24T23:00:52.3330617Z 	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:56)
2026-06-24T23:00:52.3334362Z 	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:38)
2026-06-24T23:00:52.3335818Z 	at org.gradle.internal.execution.impl.DefaultExecutionEngine$1.execute(DefaultExecutionEngine.java:68)
2026-06-24T23:00:52.3337275Z 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:132)
2026-06-24T23:00:52.3338337Z 	... 61 more
2026-06-24T23:00:52.3339243Z Caused by: org.jetbrains.kotlin.gradle.tasks.CompilationErrorException: Compilation error. See log for more details
2026-06-24T23:00:52.3355540Z 	at org.jetbrains.kotlin.gradle.tasks.TasksUtilsKt.throwExceptionIfCompilationFailed(tasksUtils.kt:21)
2026-06-24T23:00:52.3357233Z 	at org.jetbrains.kotlin.compilerRunner.btapi.BuildToolsApiCompilationWork.execute(BuildToolsApiCompilationWork.kt:320)
2026-06-24T23:00:52.3358766Z 	at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:68)
2026-06-24T23:00:52.3360384Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1$1.create(NoIsolationWorkerFactory.java:64)
2026-06-24T23:00:52.3361836Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1$1.create(NoIsolationWorkerFactory.java:61)
2026-06-24T23:00:52.3363533Z 	at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:102)
2026-06-24T23:00:52.3365023Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1.lambda$execute$0(NoIsolationWorkerFactory.java:61)
2026-06-24T23:00:52.3366303Z 	at org.gradle.workers.internal.AbstractWorker$1.call(AbstractWorker.java:44)
2026-06-24T23:00:52.3367349Z 	at org.gradle.workers.internal.AbstractWorker$1.call(AbstractWorker.java:41)
2026-06-24T23:00:52.3368884Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
2026-06-24T23:00:52.3370868Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
2026-06-24T23:00:52.3372757Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
2026-06-24T23:00:52.3374174Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3375635Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
2026-06-24T23:00:52.3377162Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
2026-06-24T23:00:52.3378608Z 	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
2026-06-24T23:00:52.3380046Z 	at org.gradle.workers.internal.AbstractWorker.executeWrappedInBuildOperation(AbstractWorker.java:41)
2026-06-24T23:00:52.3381440Z 	at org.gradle.workers.internal.NoIsolationWorkerFactory$1.execute(NoIsolationWorkerFactory.java:58)
2026-06-24T23:00:52.3386244Z 	at org.gradle.workers.internal.DefaultWorkerExecutor.lambda$submitWork$0(DefaultWorkerExecutor.java:174)
2026-06-24T23:00:52.3387947Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.runExecution(DefaultConditionalExecutionQueue.java:191)
2026-06-24T23:00:52.3389891Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.access$500(DefaultConditionalExecutionQueue.java:112)
2026-06-24T23:00:52.3391629Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner$1.run(DefaultConditionalExecutionQueue.java:168)
2026-06-24T23:00:52.3393233Z 	at org.gradle.internal.Factories$1.create(Factories.java:30)
2026-06-24T23:00:52.3394381Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$runAndReleaseLocks$0(DefaultWorkerLeaseService.java:300)
2026-06-24T23:00:52.3409352Z 	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
2026-06-24T23:00:52.3410811Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAndReleaseLocks(DefaultWorkerLeaseService.java:298)
2026-06-24T23:00:52.3412383Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocksAcquired(DefaultWorkerLeaseService.java:294)
2026-06-24T23:00:52.3414233Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocks(DefaultWorkerLeaseService.java:286)
2026-06-24T23:00:52.3415646Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:130)
2026-06-24T23:00:52.3417154Z 	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:135)
2026-06-24T23:00:52.3418820Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.runBatch(DefaultConditionalExecutionQueue.java:163)
2026-06-24T23:00:52.3420600Z 	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.run(DefaultConditionalExecutionQueue.java:125)
2026-06-24T23:00:52.3421727Z 	... 2 more
2026-06-24T23:00:52.3421954Z 
2026-06-24T23:00:52.3421961Z 
2026-06-24T23:00:52.3422146Z BUILD FAILED in 35s
2026-06-24T23:00:52.7182368Z ##[error]Process completed with exit code 1.
2026-06-24T23:00:52.7268420Z ##[group]Run actions/upload-artifact@v7
2026-06-24T23:00:52.7268792Z with:
2026-06-24T23:00:52.7269055Z   name: reports
2026-06-24T23:00:52.7269541Z   path: app/build/reports/tests/testDebugUnitTest
app/build/reports/lint-results-debug.html

2026-06-24T23:00:52.7270070Z   if-no-files-found: ignore
2026-06-24T23:00:52.7270383Z   compression-level: 6
2026-06-24T23:00:52.7270668Z   overwrite: false
2026-06-24T23:00:52.7270963Z   include-hidden-files: false
2026-06-24T23:00:52.7271278Z   archive: true
2026-06-24T23:00:52.7271537Z env:
2026-06-24T23:00:52.7271917Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:52.7272781Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.19-10/x64
2026-06-24T23:00:52.7273295Z   GRADLE_ACTION_ID: gradle/actions/setup-gradle
2026-06-24T23:00:52.7273677Z   GRADLE_USER_HOME: /home/runner/.gradle
2026-06-24T23:00:52.7274043Z   GRADLE_BUILD_ACTION_SETUP_COMPLETED: true
2026-06-24T23:00:52.7274616Z   DEVELOCITY_INJECTION_INIT_SCRIPT_NAME: gradle-actions.inject-develocity.init.gradle
2026-06-24T23:00:52.7275155Z   DEVELOCITY_INJECTION_CUSTOM_VALUE: gradle-actions
2026-06-24T23:00:52.7275546Z   GITHUB_DEPENDENCY_GRAPH_ENABLED: false
2026-06-24T23:00:52.7275889Z ##[endgroup]
2026-06-24T23:00:52.8649829Z Multiple search paths detected. Calculating the least common ancestor of all paths
2026-06-24T23:00:52.8652109Z The least common ancestor is /home/runner/work/myweather/myweather/app/build/reports. This will be the root directory of the artifact
2026-06-24T23:00:52.8653376Z No files were found with the provided path: app/build/reports/tests/testDebugUnitTest
2026-06-24T23:00:52.8654324Z app/build/reports/lint-results-debug.html. No artifacts will be uploaded.
2026-06-24T23:00:52.8924038Z Post job cleanup.
2026-06-24T23:00:53.1517569Z In post-action step
2026-06-24T23:00:53.1526852Z Enhanced Caching: This build is using the proprietary 'gradle-actions-caching' provider for optimized caching support. See https://github.com/gradle/actions/blob/main/DISTRIBUTION.md for terms of use and opt-out instructions.
2026-06-24T23:00:53.2261839Z ##[group]Stopping Gradle daemons
2026-06-24T23:00:53.2263497Z Stopping Gradle daemons for /home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s/gradle-9.6.0
2026-06-24T23:00:53.2282378Z [command]/home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s/gradle-9.6.0/bin/gradle --stop
2026-06-24T23:00:53.8360947Z Stopping Daemon(s)
2026-06-24T23:00:53.8361318Z 1 Daemon stopped
2026-06-24T23:00:53.8461679Z ##[endgroup]
2026-06-24T23:00:53.8462671Z Not performing cache-cleanup due to build failure
2026-06-24T23:00:53.8463876Z ##[group]Caching Gradle state
2026-06-24T23:00:54.2973081Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:00:54.3317286Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:00:54.3430852Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:00:54.5190596Z Sent 101675 of 101675 (100.0%), 2.6 MBs/sec
2026-06-24T23:00:54.5308020Z Sent 89470 of 89470 (100.0%), 1.3 MBs/sec
2026-06-24T23:00:54.6167208Z Saved cache entry with key gradle-groovy-dsl-v1-f89e44f70092d67690f53f5725593bea from /home/runner/.gradle/caches/*/groovy-dsl/*/ in 308ms
2026-06-24T23:00:54.6349754Z Saved cache entry with key gradle-kotlin-dsl-v1-196cb20de7c38df3f45a11c306400383 from /home/runner/.gradle/caches/*/kotlin-dsl/accessors/*/,/home/runner/.gradle/caches/*/kotlin-dsl/scripts/*/ in 342ms
2026-06-24T23:00:54.7437895Z Sent 13581417 of 13581417 (100.0%), 48.0 MBs/sec
2026-06-24T23:00:54.8837532Z Saved cache entry with key gradle-build-cache-v1-227d14f0f259b53108f0d09e747c90c9 from /home/runner/.gradle/caches/build-cache-*/* in 611ms
2026-06-24T23:00:55.4388637Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:00:57.7870950Z Sent 76353168 of 143462032 (53.2%), 72.8 MBs/sec
2026-06-24T23:00:58.0644262Z Sent 143462032 of 143462032 (100.0%), 107.1 MBs/sec
2026-06-24T23:00:58.1422910Z Saved cache entry with key gradle-transforms-v1-1cd2dd460698a1332a6260a2f37c813b from /home/runner/.gradle/caches/transforms-4/*/,/home/runner/.gradle/caches/*/transforms/*/ in 3288ms
2026-06-24T23:00:58.6577932Z [command]/usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
2026-06-24T23:00:58.9949678Z Sent 4773412 of 4773412 (100.0%), 27.9 MBs/sec
2026-06-24T23:00:59.0684750Z Saved cache entry with key gradle-home-v1|Linux-X64|build-and-test[b8a35c2f917259958c9abe8f2ff1b5ff]-9c64e7a569517b1fbc1e4054a0cd7191a289b6e9 from /home/runner/.gradle/caches,/home/runner/.gradle/notifications,/home/runner/.gradle/.setup-gradle in 422ms
2026-06-24T23:00:59.0686212Z ##[endgroup]
2026-06-24T23:00:59.0691117Z Generating Job Summary
2026-06-24T23:00:59.0705229Z Completed post-action step
2026-06-24T23:00:59.0926838Z Post job cleanup.
2026-06-24T23:00:59.2283286Z Post job cleanup.
2026-06-24T23:00:59.3115603Z [command]/usr/bin/git version
2026-06-24T23:00:59.3153578Z git version 2.54.0
2026-06-24T23:00:59.3198746Z Temporarily overriding HOME='/home/runner/work/_temp/7e9e929a-5754-4824-b099-8cc06ba47a44' before making global git config changes
2026-06-24T23:00:59.3200109Z Adding repository directory to the temporary git global config as a safe directory
2026-06-24T23:00:59.3205207Z [command]/usr/bin/git config --global --add safe.directory /home/runner/work/myweather/myweather
2026-06-24T23:00:59.3238240Z Removing SSH command configuration
2026-06-24T23:00:59.3244861Z [command]/usr/bin/git config --local --name-only --get-regexp core\.sshCommand
2026-06-24T23:00:59.3303269Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'core\.sshCommand' && git config --local --unset-all 'core.sshCommand' || :"
2026-06-24T23:00:59.3538374Z Removing HTTP extra header
2026-06-24T23:00:59.3543632Z [command]/usr/bin/git config --local --name-only --get-regexp http\.https\:\/\/github\.com\/\.extraheader
2026-06-24T23:00:59.3584244Z [command]/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'http\.https\:\/\/github\.com\/\.extraheader' && git config --local --unset-all 'http.https://github.com/.extraheader' || :"
2026-06-24T23:00:59.3813904Z Removing includeIf entries pointing to credentials config files
2026-06-24T23:00:59.3819655Z [command]/usr/bin/git config --local --name-only --get-regexp ^includeIf\.gitdir:
2026-06-24T23:00:59.3848677Z includeif.gitdir:/home/runner/work/myweather/myweather/.git.path
2026-06-24T23:00:59.3850240Z includeif.gitdir:/home/runner/work/myweather/myweather/.git/worktrees/*.path
2026-06-24T23:00:59.3851156Z includeif.gitdir:/github/workspace/.git.path
2026-06-24T23:00:59.3851912Z includeif.gitdir:/github/workspace/.git/worktrees/*.path
2026-06-24T23:00:59.3858451Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/home/runner/work/myweather/myweather/.git.path
2026-06-24T23:00:59.3881642Z /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.3891436Z [command]/usr/bin/git config --local --unset includeif.gitdir:/home/runner/work/myweather/myweather/.git.path /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.3926332Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/home/runner/work/myweather/myweather/.git/worktrees/*.path
2026-06-24T23:00:59.3948862Z /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.3957598Z [command]/usr/bin/git config --local --unset includeif.gitdir:/home/runner/work/myweather/myweather/.git/worktrees/*.path /home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.3996715Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/github/workspace/.git.path
2026-06-24T23:00:59.4018848Z /github/runner_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.4025689Z [command]/usr/bin/git config --local --unset includeif.gitdir:/github/workspace/.git.path /github/runner_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.4056795Z [command]/usr/bin/git config --local --get-all includeif.gitdir:/github/workspace/.git/worktrees/*.path
2026-06-24T23:00:59.4078340Z /github/runner_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.4087038Z [command]/usr/bin/git config --local --unset includeif.gitdir:/github/workspace/.git/worktrees/*.path /github/runner_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config
2026-06-24T23:00:59.4120321Z [command]/usr/bin/git submodule foreach --recursive git config --local --show-origin --name-only --get-regexp remote.origin.url
2026-06-24T23:00:59.4347985Z Removing credentials config '/home/runner/work/_temp/git-credentials-4bb46002-0770-41dc-a59b-4fcef0ca6ab0.config'
2026-06-24T23:00:59.4487979Z Cleaning up orphan processes
