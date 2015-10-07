package com.epam.android.benchmark.parser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.android.benchmark.TestUtils;
import com.epam.benchmark.IParser;
import com.squareup.burst.RepeatableSuite;
import com.squareup.burst.annotation.Burst;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Egor Makovsky
 */
@RunWith(RepeatableSuite.class)
public class ParserBenchmark {
    @Burst Parser parser;

    private IParser parserImpl;
    private Context context;

    @Before
    public void init() {
        context = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");

        parserImpl = parser.create();
        parserImpl.init();
    }

    @Test
    public void testInit() {

    }

    @Test
    public void testParse100() throws Exception {
        parserImpl.parse(TestUtils.getJsonInputStream(context, 100));
    }

    @Test
    public void testParse1000() throws Exception {
        parserImpl.parse(TestUtils.getJsonInputStream(context, 1000));
    }

    @Test
    public void testParse10000() throws Exception {
        parserImpl.parse(TestUtils.getJsonInputStream(context, 10000));
    }

    @Test
    public void testParse50000() throws Exception {
        parserImpl.parse(TestUtils.getJsonInputStream(context, 50000));
    }

    @Test
    public void testParse100000() throws Exception {
        parserImpl.parse(TestUtils.getJsonInputStream(context, 100000));
    }
}