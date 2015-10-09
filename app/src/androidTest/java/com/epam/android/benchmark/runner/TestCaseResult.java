package com.epam.android.benchmark.runner;

import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class TestCaseResult {
    private String name;

    private List<Long> times = new LinkedList<>();

    private List<Long> minMemories = new LinkedList<>();
    private List<Long> maxMemories = new LinkedList<>();
    private List<Long> midMemories = new LinkedList<>();

    public TestCaseResult(String name) {
        this.name = name;
    }

    public TestCaseResult(String name, long time, long minMemory, long maxMemory, long midMemory) {
        this.name = name;
        addTime(time);
        addMemory(minMemory, maxMemory, midMemory);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTime(long time) {
        times.add(time);
    }

    public void addMemory(long minMemory, long maxMemory, long midMemory) {
        minMemories.add(minMemory);
        maxMemories.add(maxMemory);
        midMemories.add(midMemory);
    }

    public String formatResult() {
        return String.format("%s: times=%s, median time=%s, min memory=%.2f, max memory=%.2f, mid memory=%.2f", name,
            Arrays.toString(times.toArray()), getMedian(times), toMb(getMedian(minMemories)), toMb(getMedian(maxMemories)), toMb(getMedian(midMemories)));
    }

    private double toMb(double bytes) {
        return bytes / 1024 / 1024;
    }

    private double getMedian(List<Long> values) {
        double[] doubles = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            doubles[i] = values.get(i);
        }
        return new Median().evaluate(doubles);
    }
}
