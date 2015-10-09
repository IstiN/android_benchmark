package com.epam.android.benchmark.runner;

import java.util.LinkedList;

/**
 * @author Egor Makovsky
 */
public class TestSuiteResult extends LinkedList<TestCaseResult> {
    private String className;

    public TestSuiteResult(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public TestCaseResult findByName(String name) {
        for (TestCaseResult testCaseResult : this) {
            if (testCaseResult.getName().equals(name)) {
                return testCaseResult;
            }
        }
        return null;
    }

    public void addTestCase(String name, long time, long minMemory, long maxMemory, long midMemory) {
        TestCaseResult existingTestCaseResult = findByName(name);

        if (existingTestCaseResult != null) {
            existingTestCaseResult.addTime(time);
            existingTestCaseResult.addMemory(minMemory, maxMemory, midMemory);
        } else {

            super.add(new TestCaseResult(name, time, minMemory, maxMemory, midMemory));
        }
    }
}
