package com.epam.android.benchmark.parser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

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
        parserImpl.parse(context.getAssets().open("source_100.json"));
    }

    @Test
    public void testParse1000() throws Exception {
        parserImpl.parse(context.getAssets().open("source_1000.json"));
    }

    @Test
    public void testParseMany() throws Exception {
        parserImpl.parse(context.getAssets().open("source.json"));
    }
}