package com.epam.android.benchmark;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;


/**
 * @author Egor Makovsky
 */
public class CustomRunner extends AndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        // RunnerArgs
        arguments.putString("listener", "com.epam.android.benchmark.CustomRunListenerImpl");

        super.onCreate(arguments);
    }
}
