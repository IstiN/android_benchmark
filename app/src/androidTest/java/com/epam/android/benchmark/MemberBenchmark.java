package com.epam.android.benchmark;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IMember;
import com.epam.benchmark.util.CloseableList;
import com.squareup.burst.RepeatableSuite;
import com.squareup.burst.annotation.Burst;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

/**
 * @author Egor Makovsky
 */
@RunWith(RepeatableSuite.class)
public class MemberBenchmark {
    @Burst Member member;

    private IMember memberImpl;
    private Context context;

    @Before
    public void init() {
        context = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");

        memberImpl = member.create();
        memberImpl.onApplicationCreate((Application) context.getApplicationContext());
        memberImpl.onActivityCreate(context);
    }

    @After
    public void tearDown() {
        memberImpl.delete(context);
    }

    @Test
    public void testProcess100() throws Exception {
        testProcess(100, true);
    }

    @Test
    public void testProcess1000() throws Exception {
        testProcess(1000, true);
    }

    @Test
    public void testProcess10000() throws Exception {
        testProcess(10000, true);
    }

    @Test
    public void testProcessLarge() throws Exception {
        testProcess(50000, false);
    }

    private void testProcess(int count, boolean isSmall) throws Exception {
        memberImpl.delete(context);

        InputStream inputStream = TestUtils.getJsonInputStream(context, count);

        if (isSmall) {
            memberImpl.processSmallData(context, inputStream);
        } else {
            memberImpl.processLargeData(context, inputStream);
        }
        CloseableList<IEntity> entities = memberImpl.getCachedEntities(context);
        Assert.assertEquals(count, entities.size());
        getAndClose(entities);

        entities = memberImpl.getCachedEntitiesWithFilter(context, true, null, 30, 40);
//        Assert.assertEquals(7, entities.size());
        getAndClose(entities);

        memberImpl.delete(context);

        entities = memberImpl.getCachedEntities(context);
        Assert.assertEquals(0, entities == null ? 0 : entities.size());
    }

    private void getAndClose(CloseableList<IEntity> entities) throws Exception {
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            entities.get(i);
        }
        entities.close();
    }
}
