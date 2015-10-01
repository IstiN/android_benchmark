package com.epam.benchmark;

import android.content.Context;

import java.util.List;

/**
 * @author Egor Makovsky
 */
public interface IStorage {

    void init();

    void save(Context context, List<IEntity> entities);

    List<IEntity> getEntities(Context context);

    List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex);

    void clear(Context context);

    void clearResources();
}
