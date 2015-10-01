package com.epam.benchmark;

import android.content.Context;

import java.util.List;

/**
 * @author Egor Makovsky
 */
public interface IStorage {

    void init(Context context);

    void save(Context context, List<IEntity> entities);

    List<IEntity> getEntities(Context context);

    /**
     * If param is null, param need to be ignored during query, else params need to be joined with AND operator
     * @param context context to make request to db, cache or something else
     * @param isActive filter param null/true/false
     * @param employeeName filter param null/some name
     * @param startIndex filter param null/startIndex
     * @param endIndex filter param null/endIndex
     * @return list of entities
     */
    List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex);

    /**
     * Remove data from cache
     * @param context context to make request to db, cache or something else
     */
    void clear(Context context);

    /**
     * Clear resources if needed
     */
    void clearResources();
}
