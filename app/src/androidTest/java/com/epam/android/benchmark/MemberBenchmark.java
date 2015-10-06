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
    public void testProcess() throws Exception {
        memberImpl.delete(context);

        InputStream inputStream = context.getAssets().open("source_10000.json");
        memberImpl.process(context, inputStream);

        CloseableList<IEntity> entities = memberImpl.getCachedEntities(context);
        Assert.assertEquals(10000, entities.size());
        printAndClose(entities);

        entities = memberImpl.getCachedEntitiesWithFilter(context, true, null, 30, 40);
        Assert.assertEquals(7, entities.size());
        printAndClose(entities);

        memberImpl.delete(context);

        entities = memberImpl.getCachedEntities(context);
        Assert.assertEquals(0, entities == null ? 0 : entities.size());
    }

    private void printAndClose(CloseableList<IEntity> pCachedEntities) throws Exception {
        int size = pCachedEntities.size();
        for (int i = 0; i < size; i++) {
            pCachedEntities.get(i).print();
        }
        pCachedEntities.close();
    }
}
