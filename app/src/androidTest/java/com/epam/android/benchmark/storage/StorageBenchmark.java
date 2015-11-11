package com.epam.android.benchmark.storage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.android.benchmark.TestUtils;
import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.squareup.burst.RepeatableSuite;
import com.squareup.burst.annotation.Burst;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Egor Makovsky
 */
@RunWith(RepeatableSuite.class)
public class StorageBenchmark {
    @Burst Storage storage;

    private IStorage storageImpl;
    private Context context;

    @Before
    public void init() {
        context = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");

        storageImpl = storage.create();
        storageImpl.init(context.getApplicationContext());
    }

    @After
    public void tearDown() {
        storageImpl.clear(context);
        storageImpl.clearResources(context);
    }

    @Test
    public void testInit() {

    }

    @Test
    public void testSave() {
        storageImpl.save(context, newTestList());
    }

    @Test
    public void testGet() {
        storageImpl.save(context, newTestList());

        storageImpl.getEntities(context);
    }

    @Test
    public void testGetFiltered() {
        storageImpl.save(context, newTestList());

        storageImpl.getEntities(context, true, null, 100, 1000);
    }

    private List<IEntity> newTestList() {
        return TestUtils.newTestList(1000);
    }
}
