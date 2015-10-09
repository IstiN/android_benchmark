package com.epam.android.benchmark.runner;

import android.os.Environment;
import android.os.SystemClock;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import java.io.File;

import by.istin.android.xcore.utils.Log;

/**
 * @author Egor Makovsky
 */
public class BenchmarkReporter extends RunListener {
    private static final String TAG = "BenchmarkReporter";

    private TestResults testResults;

    private long startTime;
    private long heapSize;
    private MemoryTracker memoryTracker;

    public BenchmarkReporter() {
        testResults = new TestResults();
    }

    @Override
    public void testStarted(Description description) throws Exception {
        testResults.addTestSuiteResultIfNeeded(description.getClassName());

        startTime = System.currentTimeMillis();
        heapSize = Runtime.getRuntime().totalMemory();

        memoryTracker = new MemoryTracker();
        memoryTracker.start();
    }

    @Override
    public void testFinished(Description description) throws Exception {
        memoryTracker.interrupt();

        testResults.addTestCase(description.getClassName(), description.getMethodName(), System.currentTimeMillis() - startTime,
            memoryTracker.getMinHeapSize(), memoryTracker.getMaxHeapSize(), memoryTracker.getMidHeapSize());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        File benchmarkDir = new File(Environment.getExternalStorageDirectory(), "benchmark");
        if (!benchmarkDir.mkdirs()) {
            Log.d(TAG, "Cannot create folder 'benchmark' at " + Environment.getExternalStorageDirectory().getAbsolutePath());
        }

        testResults.saveToFile(new File(benchmarkDir, "report.txt"));
    }

    private class MemoryTracker extends Thread {
        private long minHeapSize = Long.MAX_VALUE;
        private long maxHeapSize;
        private long midHeapSize;

        private int count = 1;

        @Override
        public void run() {
            while (!interrupted()) {
                long heapSize = Runtime.getRuntime().totalMemory();

                if (heapSize < minHeapSize) {
                    minHeapSize = heapSize;
                }

                if (heapSize > maxHeapSize) {
                    maxHeapSize = heapSize;
                }

                midHeapSize = ((count - 1) * midHeapSize + heapSize) / count;
                count++;

                SystemClock.sleep(800);
            }
        }

        public long getMinHeapSize() {
            return minHeapSize;
        }

        public long getMaxHeapSize() {
            return maxHeapSize;
        }

        public long getMidHeapSize() {
            return midHeapSize;
        }
    }
}
