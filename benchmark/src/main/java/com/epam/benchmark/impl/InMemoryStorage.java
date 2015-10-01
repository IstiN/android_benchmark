package com.epam.benchmark.impl;

import android.content.Context;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;

import java.util.Collections;
import java.util.List;

/**
 * // TODO: 10/1/2015 move to separate library
 * @author Egor Makovsky
 */
public class InMemoryStorage implements IStorage {

    private List<IEntity> entities;

    @Override
    public void init(Context context) {
        entities = Collections.emptyList();
    }

    @Override
    public void save(Context context, List<IEntity> entities) {
        this.entities = entities;
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        return entities;
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        return entities;// TODO: 10/1/2015  filter in memory implementation
    }

    @Override
    public void clear(Context context) {
        entities.clear();
    }

    @Override
    public void clearResources() {

    }
}
