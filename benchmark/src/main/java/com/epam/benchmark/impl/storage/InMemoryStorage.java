package com.epam.benchmark.impl.storage;

import android.content.Context;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * // TODO: 10/1/2015 move to separate library
 *
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
        List<IEntity> entities = new LinkedList<>();

        for (IEntity entity : this.entities) {
            if ((isActive == null || isActive == entity.isActive())
                && (employeeName == null || employeeName.equals(entity.getEmployeeName()))
                && (startIndex != null || startIndex >= entity.getIndex())
                && (endIndex != null || endIndex <= entity.getIndex())) {

                entities.add(entity);
            }
        }

        return entities;
    }

    @Override
    public void clear(Context context) {
        entities.clear();
    }

    @Override
    public void clearResources() {

    }
}
