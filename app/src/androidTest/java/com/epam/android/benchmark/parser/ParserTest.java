package com.epam.android.benchmark.parser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser;
import com.squareup.burst.BurstJUnit4;
import com.squareup.burst.annotation.Burst;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Egor Makovsky
 */
@RunWith(BurstJUnit4.class)
public class ParserTest {
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
    public void testCount100() throws Exception {
        List<IEntity> entities = parserImpl.parse(context.getAssets().open("source_100.json"));

        Assert.assertEquals(100, entities.size());
    }

    @Test
    public void testCount1000() throws Exception {
        List<IEntity> entities = parserImpl.parse(context.getAssets().open("source_1000.json"));

        Assert.assertEquals(1000, entities.size());
    }

    @Test
    public void testIndexesCorrect() throws Exception {
        List<IEntity> entities = parserImpl.parse(context.getAssets().open("source_100.json"));

        for (int i = 0; i < entities.size(); i++) {
            Assert.assertEquals(i, entities.get(i).getIndex().intValue());
        }
    }
}
