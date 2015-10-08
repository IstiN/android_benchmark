package com.epam.android.benchmark.parser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.android.benchmark.TestUtils;
import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser;
import com.epam.benchmark.impl.EmptyParserListenerImpl;
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
    public void testParserListener() throws Exception {
        final ListenerDataHolder listenerDataHolder = new ListenerDataHolder();

        parserImpl.parse(TestUtils.getJsonInputStream(context, 100000), new IParser.Listener() {
            @Override
            public void onEntityRead(IEntity entity) {
                listenerDataHolder.count++;
            }

            @Override
            public void onReadFinished() {
                listenerDataHolder.finished = true;
            }
        });

        Assert.assertEquals(100000, listenerDataHolder.count);
        Assert.assertTrue(listenerDataHolder.finished);
    }

    @Test
    public void testCount100() throws Exception {
        List<IEntity> entities = parserImpl.parse(TestUtils.getJsonInputStream(context, 100));

        Assert.assertEquals(100, entities.size());
    }

    @Test
    public void testCount1000() throws Exception {
        List<IEntity> entities = parserImpl.parse(TestUtils.getJsonInputStream(context, 1000));
        Assert.assertEquals(1000, entities.size());
    }

    @Test
    public void testCount10000() throws Exception {
        List<IEntity> entities = parserImpl.parse(TestUtils.getJsonInputStream(context, 10000));
        Assert.assertEquals(10000, entities.size());
    }

    @Test
    public void testCount50000() throws Exception {
        parserImpl.parse(TestUtils.getJsonInputStream(context, 50000), new EmptyParserListenerImpl());
    }

    @Test
    public void testIndexesCorrect() throws Exception {
        List<IEntity> entities = parserImpl.parse(TestUtils.getJsonInputStream(context, 100));

        for (int i = 0; i < entities.size(); i++) {
            Assert.assertEquals(i, entities.get(i).getIndex().intValue());
        }
    }

    private class ListenerDataHolder {
        int count;
        boolean finished;
    }
}
