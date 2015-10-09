Android application for benchmarking
==============

Notes
----
- to be able to save custom reports to sdcard the following Runner should be used:
com.epam.android.benchmark.runner.RunnerWithListener
It's already specified in gradle build. Just ensure in your IDE run configuration the specific runner is empty or equals to RunnerWithListener

- the report will be created here: sdcard/bencmark/report.txt

- to run the same test several times just modify RepeatableSuite#EACH_TEST_REPEAT_COUNT
the results will be aggregated by custom reporter (BenchmarkReporter)

- to increase test launch speed please use 'noAssetsDebug' build type.
Assets folder will be cleared. Large JSONs are expected to be found at sdcard/benchmark folder

- here are the links to large JSONs files (some of them are stored at assets folder):
https://www.dropbox.com/s/hyosugo1kutg3vy/Test_50_000.json?dl=0
https://www.dropbox.com/s/jdhn24h9fj5nuoz/Test_70_000.json?dl=0
https://www.dropbox.com/s/vvaw9tvxqckygwh/Test_100_000.json?dl=0