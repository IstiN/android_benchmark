package com.epam.benchmark.impl.storage;

import android.content.Context;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;

import java.util.Collections;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class StubStorage implements IStorage {
    @Override
    public void init(Context context) {

    }

    @Override
    public void save(Context context, List<IEntity> entities) {

    }

    @Override
    public List<IEntity> getEntities(Context context) {
        return Collections.emptyList();
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        return Collections.emptyList();
    }

    @Override
    public void clear(Context context) {

    }

    @Override
    public void clearResources() {

    }
}
