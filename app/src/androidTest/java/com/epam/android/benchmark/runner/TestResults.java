package com.epam.android.benchmark.runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class TestResults {
    private List<TestSuiteResult> testSuiteResults;

    public TestResults() {
        testSuiteResults = new LinkedList<>();
    }

    public void addTestCase(String className, String name, long time, long minMemory, long maxMemory, long midMemory) {
        TestSuiteResult testSuiteResult = findByClassName(className);

        if (testSuiteResult != null) {
            testSuiteResult.addTestCase(name, time, minMemory, maxMemory, midMemory);
        } else {
            throw new IllegalArgumentException("Test suite not found for class name: " + className);
        }
    }

    public void addTestSuiteResultIfNeeded(String className) {
        TestSuiteResult testSuiteResult = findByClassName(className);
        if (testSuiteResult == null) {
            testSuiteResult = new TestSuiteResult(className);
            testSuiteResults.add(testSuiteResult);
        }
    }

    public void saveToFile(File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        for (TestSuiteResult testSuiteResult : testSuiteResults) {
            printWriter.println();
            printWriter.println("Test suite: " + testSuiteResult.getClassName());
            printWriter.println("---------");

            for (TestCaseResult testCaseResult : testSuiteResult) {
                printWriter.println(testCaseResult.formatResult());
            }
            printWriter.println("---------");
        }
        printWriter.flush();
        printWriter.close();
    }

    private TestSuiteResult findByClassName(String className) {
        for (TestSuiteResult testSuiteResult : testSuiteResults) {
            if (testSuiteResult.getClassName().equals(className)) {
                return testSuiteResult;
            }
        }
        return null;
    }
}
