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
