Build is failing 
Please fix all defects. 
also remember that the application id should be com.github.kusl.myweather not a generic name 
the project repository is at 
https://github.com/kusl/myweather/
please update any documentation and readme as well as necessary 
also remember to return FULL files as well as the exact file location of the any and ALL files that need to change
also please tell me the exact steps I need to take on my fedora laptop 
step by step to generate and upload release keys to github actions 
I have android studio available here 

Build APK
run dump #3

    All jobs
    Run details

Assemble release APK
failed now in 1m 14s
1s
1s
0s
9s
0s
47s
	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.executeMutable(HandleStaleOutputsStep.java:43)
	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.lambda$executeMutable$0(AssignMutableWorkspaceStep.java:34)
	at org.gradle.api.internal.tasks.execution.TaskExecution$4.withWorkspace(TaskExecution.java:305)
	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.executeMutable(AssignMutableWorkspaceStep.java:30)
	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.executeMutable(AssignMutableWorkspaceStep.java:21)
	at org.gradle.internal.execution.steps.MutableStep.execute(MutableStep.java:26)
	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:40)
	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:23)
	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.lambda$execute$2(ExecuteWorkBuildOperationFiringStep.java:67)
	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:67)
	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:39)
	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:46)
	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:34)
	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:56)
	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:38)
	at org.gradle.internal.execution.impl.DefaultExecutionEngine$1.execute(DefaultExecutionEngine.java:68)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:132)
	... 61 more
Caused by: com.android.builder.internal.aapt.v2.Aapt2Exception: Android resource linking failed
ERROR: AAPT: error: resource android:style/Theme.Material.DayNight.NoActionBar not found.
error: failed linking references.
    
	at com.android.builder.internal.aapt.v2.Aapt2Exception$Companion.create(Aapt2Exception.kt:44)
	at com.android.builder.internal.aapt.v2.Aapt2Exception$Companion.create$default(Aapt2Exception.kt:33)
	at com.android.build.gradle.internal.res.Aapt2ErrorUtils.rewriteException(Aapt2ErrorUtils.kt:221)
	at com.android.build.gradle.internal.res.Aapt2ErrorUtils.rewriteLinkException(Aapt2ErrorUtils.kt:120)
	at com.android.build.gradle.internal.res.Aapt2ProcessResourcesRunnableKt.processResources(Aapt2ProcessResourcesRunnable.kt:75)
	at com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask$Companion.invokeAaptForSplit(LinkApplicationAndroidResourcesTask.kt:857)
	at com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask$Companion.access$invokeAaptForSplit(LinkApplicationAndroidResourcesTask.kt:695)
	at com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask$TaskAction.run(LinkApplicationAndroidResourcesTask.kt:396)
	at com.android.build.gradle.internal.profile.ProfileAwareWorkAction.execute(ProfileAwareWorkAction.kt:66)
	at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:68)
	at org.gradle.workers.internal.NoIsolationWorkerFactory$1$1.create(NoIsolationWorkerFactory.java:64)
	at org.gradle.workers.internal.NoIsolationWorkerFactory$1$1.create(NoIsolationWorkerFactory.java:61)
	at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:102)
	at org.gradle.workers.internal.NoIsolationWorkerFactory$1.lambda$execute$0(NoIsolationWorkerFactory.java:61)
	at org.gradle.workers.internal.AbstractWorker$1.call(AbstractWorker.java:44)
	at org.gradle.workers.internal.AbstractWorker$1.call(AbstractWorker.java:41)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:210)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:205)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:67)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:60)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:167)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:60)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:54)
	at org.gradle.workers.internal.AbstractWorker.executeWrappedInBuildOperation(AbstractWorker.java:41)
	at org.gradle.workers.internal.NoIsolationWorkerFactory$1.execute(NoIsolationWorkerFactory.java:58)
	at org.gradle.workers.internal.DefaultWorkerExecutor.lambda$submitWork$0(DefaultWorkerExecutor.java:174)
	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.runExecution(DefaultConditionalExecutionQueue.java:191)
	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.access$500(DefaultConditionalExecutionQueue.java:112)
	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner$1.run(DefaultConditionalExecutionQueue.java:168)
	at org.gradle.internal.Factories$1.create(Factories.java:30)
	at org.gradle.internal.work.DefaultWorkerLeaseService.lambda$runAndReleaseLocks$0(DefaultWorkerLeaseService.java:300)
	at org.gradle.internal.work.ResourceLockStatistics$1.measure(ResourceLockStatistics.java:43)
	at org.gradle.internal.work.DefaultWorkerLeaseService.runAndReleaseLocks(DefaultWorkerLeaseService.java:298)
	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocksAcquired(DefaultWorkerLeaseService.java:294)
	at org.gradle.internal.work.DefaultWorkerLeaseService.withLocks(DefaultWorkerLeaseService.java:286)
	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:130)
	at org.gradle.internal.work.DefaultWorkerLeaseService.runAsWorkerThread(DefaultWorkerLeaseService.java:135)
	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.runBatch(DefaultConditionalExecutionQueue.java:163)
	at org.gradle.internal.work.DefaultConditionalExecutionQueue$ExecutionRunner.run(DefaultConditionalExecutionQueue.java:125)
	... 2 more
BUILD FAILED in 46s
Configuration cache entry stored.
Error: Process completed with exit code 1.
0s
0s
14s
Node 20 is being deprecated. This workflow is running with Node 24 by default. If you need to temporarily use Node 20, you can set the ACTIONS_ALLOW_USE_UNSECURE_NODE_VERSION=true environment variable. For more information see: https://github.blog/changelog/2025-09-19-deprecation-of-node-20-on-github-actions-runners/
Post job cleanup.
In post-action step
Stopping Gradle daemons
  Stopping Gradle daemons for /home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s/gradle-9.6.0
  /home/runner/.gradle/wrapper/dists/gradle-9.6.0-bin/42k10rwplmzkhuboz9kdazi7s/gradle-9.6.0/bin/gradle --stop
  Stopping Daemon(s)
  1 Daemon stopped
Not performing cache-cleanup due to build failure
Caching Gradle state
  Not saving configuration-cache state, as no encryption key was provided
  /usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
  /usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
  Sent 290576 of 290576 (100.0%), 4.6 MBs/sec
  Saved cache entry with key gradle-kotlin-dsl-v1-547de548629a8a712de54f7f26e5248b from /home/runner/.gradle/caches/*/kotlin-dsl/accessors/*/,/home/runner/.gradle/caches/*/kotlin-dsl/scripts/*/ in 494ms
  /usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
  Sent 0 of 517451896 (0.0%), 0.0 MBs/sec
  Sent 47689848 of 517451896 (9.2%), 22.7 MBs/sec
  Sent 0 of 586558557 (0.0%), 0.0 MBs/sec
  Sent 517451896 of 517451896 (100.0%), 226.2 MBs/sec
  Saved cache entry with key gradle-java-toolchains-v1-57802292e8354a989f6e3bf59419e75e from /home/runner/.gradle/jdks/jetbrains_s_r_o_-21-amd64-linux.2 in 5540ms
  Sent 469762048 of 586558557 (80.1%), 224.0 MBs/sec
  Sent 586558557 of 586558557 (100.0%), 206.9 MBs/sec
  Saved cache entry with key gradle-dependencies-v1-5a632327f39ed631c370fc7a89673e12 from /home/runner/.gradle/caches/modules-*/files-*/*/*/*/* in 6522ms
  /usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
  Sent 50484 of 268485940 (0.0%), 0.0 MBs/sec
  Sent 268485940 of 268485940 (100.0%), 192.4 MBs/sec
  Saved cache entry with key gradle-transforms-v1-e6c2aafd957b80a56416eee33e6eac46 from /home/runner/.gradle/caches/transforms-4/*/,/home/runner/.gradle/caches/*/transforms/*/ in 8248ms
  /usr/bin/tar --posix -cf cache.tzst --exclude cache.tzst -P -C /home/runner/work/myweather/myweather --files-from manifest.txt --use-compress-program zstdmt
  Sent 6665708 of 6665708 (100.0%), 33.3 MBs/sec
0s
0s
Post job cleanup.
/usr/bin/git version
git version 2.54.0
Temporarily overriding HOME='/home/runner/work/_temp/60ecbdc3-94b6-4ad1-a337-88000936cf9d' before making global git config changes
Adding repository directory to the temporary git global config as a safe directory
/usr/bin/git config --global --add safe.directory /home/runner/work/myweather/myweather
/usr/bin/git config --local --name-only --get-regexp core\.sshCommand
/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'core\.sshCommand' && git config --local --unset-all 'core.sshCommand' || :"
/usr/bin/git config --local --name-only --get-regexp http\.https\:\/\/github\.com\/\.extraheader
http.https://github.com/.extraheader
/usr/bin/git config --local --unset-all http.https://github.com/.extraheader
/usr/bin/git submodule foreach --recursive sh -c "git config --local --name-only --get-regexp 'http\.https\:\/\/github\.com\/\.extraheader' && git config --local --unset-all 'http.https://github.com/.extraheader' || :"
/usr/bin/git config --local --name-only --get-regexp ^includeIf\.gitdir:
/usr/bin/git submodule foreach --recursive git config --local --show-origin --name-only --get-regexp remote.origin.url
0s
