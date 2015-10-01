package com.epam.benchmark.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IMember;
import com.epam.benchmark.IParser;
import com.epam.benchmark.IStorage;

import java.io.InputStream;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class ParserStorageMember implements IMember {

    private IParser parser;
    private IStorage storage;

    public ParserStorageMember(IParser parser, IStorage storage) {
        this.parser = parser;
        this.storage = storage;
    }

    @Override
    public String getName() {
        return String.format("Parser: %s, Storage: %s", parser.getClass().getSimpleName(), storage.getClass().getSimpleName());
    }

    @Override
    public void onApplicationCreate(Application application) {

    }

    @Override
    public Object getSystemService(String name) {
        return null;
    }

    @Override
    public void onActivityCreate(Activity activity) {
        parser.init();
        storage.init(activity);
    }

    @Override
    public void onActivityDestroy(Activity activity) {

    }

    @Override
    public void process(Context context, InputStream inputStream) throws Exception {
        List<IEntity> entities = parser.parse(inputStream);

        storage.save(context, entities);
    }

    @Override
    public List<IEntity> getCachedEntities(Context context) {
        return storage.getEntities(context);
    }

    @Override
    public List<IEntity> getCachedEntitiesWithFilter(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        return storage.getEntities(context, isActive, employeeName, startIndex, endIndex);
    }

    @Override
    public void delete(Context context) {
        storage.clear(context);
    }

    @Override
    public void finishWorkWithCachedEntities(List<IEntity> cachedEntities) {
        storage.clearResources();
    }
}
