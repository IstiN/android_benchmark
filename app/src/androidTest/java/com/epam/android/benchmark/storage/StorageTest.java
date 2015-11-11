package com.epam.android.benchmark.storage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import com.epam.android.benchmark.TestUtils;
import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.squareup.burst.BurstJUnit4;
import com.squareup.burst.annotation.Burst;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Egor Makovsky
 */
@RunWith(BurstJUnit4.class)
public class StorageTest {
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
    public void testSize() throws Exception {
        storageImpl.save(context, newTestList());

        Assert.assertEquals(5, storageImpl.getEntities(context).size());
    }

    @Test
    public void testGetFirst() throws Exception {
        storageImpl.save(context, newTestList());

        Assert.assertEquals("id1", storageImpl.getEntities(context).get(0).getId());
    }

    @Test
    public void testClear() throws Exception {
        storageImpl.save(context, newTestList());

        storageImpl.clear(context);

        Assert.assertTrue(storageImpl.getEntities(context).isEmpty());
    }

    @Test
    public void testFilterActive() throws Exception {
        storageImpl.save(context, newTestList());

        List<IEntity> entities = storageImpl.getEntities(context, false, "name3", 0, 10);

        Assert.assertEquals("id3", entities.get(0).getId());
        Assert.assertEquals(1, entities.size());
    }

    @Test
    public void testFilterIndex() throws Exception {
        storageImpl.save(context, newTestList());

        List<IEntity> entities = storageImpl.getEntities(context, false, "name3", 4, 10);

        Assert.assertTrue(entities.isEmpty());
    }

    @Test
    public void testFilterNullName() throws Exception {
        storageImpl.save(context, newTestList());

        List<IEntity> entities = storageImpl.getEntities(context, true, null, 1, 10);

        Assert.assertEquals(3, entities.size());
    }

    @Test
    public void testSaveTwice() throws Exception {
        if (storage != Storage.IN_MEMORY) {
            storageImpl.save(context, newTestList());

            storageImpl.save(context, newTestList());

            Assert.assertEquals(5, storageImpl.getEntities(context).size());
        } else {
            System.out.println("InMemory implementation doesnt support save and replace operation!");
        }
    }

    private List<IEntity> newTestList() {
        return TestUtils.newTestList();
    }
}
