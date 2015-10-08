package com.epam.android.benchmark.runner;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;


/**
 * We need to add a custom RunListener somehow.
 *
 * The recommended method via testInstrumentationRunnerArguments in gradle file doesnt work.
 *
 * So need to remove this class as soon as that will be fixed
 *
 * @author Egor Makovsky
 */
public class RunnerWithListener extends AndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        // see RunnerArgs
        arguments.putString("listener", "com.epam.android.benchmark.runner.BenchmarkReporter");

        super.onCreate(arguments);
    }
}
