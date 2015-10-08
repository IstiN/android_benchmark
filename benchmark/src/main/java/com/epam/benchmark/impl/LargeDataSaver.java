package com.epam.benchmark.impl;

import android.content.Context;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser.Listener;
import com.epam.benchmark.IStorage;
import com.epam.benchmark.util.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class LargeDataSaver implements Listener {
    private static final String TAG = "LargeDataSaver";

    private final Context context;
    private final IStorage storage;

    private List<IEntity> tempEntities;

    public LargeDataSaver(Context context, IStorage storage) {
        this.context = context;
        this.storage = storage;
        this.tempEntities = new LinkedList<>();
    }

    @Override
    public void onEntityRead(IEntity entity) {
        tempEntities.add(entity);

        //todo: how to calculate the number when performing saving?!
        if (tempEntities.size() >= 10000) {
            saveTempEntities();
        }
    }

    @Override
    public void onReadFinished() {
        saveTempEntities();
    }

    private void saveTempEntities() {
        Utils.logMemory();

        storage.save(context, tempEntities);

        tempEntities.clear();
    }
}
