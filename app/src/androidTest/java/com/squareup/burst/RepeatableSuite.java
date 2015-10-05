package com.squareup.burst;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class RepeatableSuite extends Suite {
    private static final int EACH_TEST_REPEAT_COUNT = 1;
//    private static final int EACH_TEST_REPEAT_COUNT = 5;
//    private static final int EACH_TEST_REPEAT_COUNT = 10;

    public RepeatableSuite(Class<?> cls) throws InitializationError {
        super(cls, explode(cls));
    }

    static List<Runner> explode(Class<?> cls) throws InitializationError {
        List<Runner> explode = BurstJUnit4.explode(cls);

        ArrayList<Runner> runners = new ArrayList<>();

        for (Runner runner : explode) {
            for (int i = 0; i < EACH_TEST_REPEAT_COUNT; i++) {
                runners.add(runner);
            }
        }

        return runners;
    }
}
