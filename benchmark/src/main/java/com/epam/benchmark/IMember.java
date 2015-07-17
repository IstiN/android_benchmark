package com.epam.benchmark;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public interface IMember {

    /**
     * Name of member that participate in benchmark
     * @return
     */
    String getName();

    /**
     * Delegate state of application
     * @param application
     */
    void onApplicationCreate(Application application);

    /**
     * Delegate state of application
     * @param name of service
     */
    Object getSystemService(String name);

    /**
     * Delegate state of activity
     * @param activity
     */
    void onActivityCreate(Activity activity);

    /**
     * Delegate state of activity
     * @param activity
     */
    void onActivityDestroy(Activity activity);

    /**
     * Parse json and cache
     * @param context context to make request to db, cache or something else
     * @param inputStream stream of source file
     */
    void process(Context context, InputStream inputStream) throws Exception;

    List<IEntity> getCachedEntities(Context context);

    /**
     * If param is null, param need to be ignored during query, else params need to be joined with AND operator
     * @param context context to make request to db, cache or something else
     * @param isActive filter param null/true/false
     * @param employeeName filter param null/some name
     * @param startIndex filter param null/startIndex
     * @param endIndex filter param null/endIndex
     * @return list of entities
     */
    List<IEntity> getCachedEntitiesWithFilter(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex);

    /**
     * Remove or data from cache
     * @param context context to make request to db, cache or something else
     */
    void delete(Context context);

    /**
     * Clear resources if needed
     * @param cachedEntities
     */
    void finishWorkWithCachedEntities(List<IEntity> cachedEntities);


    class Impl {
        /**
         * Sample member;
         * @return
         */
        public static IMember get() {
            return new IMember() {

                private static final String TAG = "sample_member";

                @Override
                public String getName() {
                    return "sample";
                }

                @Override
                public void onApplicationCreate(Application application) {
                    //create your singletones
                    Log.d(TAG, "member_created");
                }

                @Override
                public Object getSystemService(String name) {
                    return null;
                }

                @Override
                public void onActivityCreate(Activity activity) {
                    //connect to activity
                    Log.d(TAG, "member_activity_created");
                }

                @Override
                public void onActivityDestroy(Activity activity) {
                    //destroy your instances to save memory
                    Log.d(TAG, "member_activity_destroyed");
                }

                @Override
                public void process(Context context, InputStream inputStream) {
                    //download and cache data
                    Log.d(TAG, "member_process_data");
                    try {
                        //you need close stream after processing
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public List<IEntity> getCachedEntities(Context context) {
                    Log.d(TAG, "member_get_cached_entities");
                    return new ArrayList<>();
                }

                @Override
                public List<IEntity> getCachedEntitiesWithFilter(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
                    Log.d(TAG, "member_get_cached_entities_with_filter");
                    return new ArrayList<>();
                }

                @Override
                public void delete(Context context) {
                    Log.d(TAG, "member_delete");
                }

                @Override
                public void finishWorkWithCachedEntities(List<IEntity> cachedEntities) {
                    //cleared
                }
            };
        }
    }

}
