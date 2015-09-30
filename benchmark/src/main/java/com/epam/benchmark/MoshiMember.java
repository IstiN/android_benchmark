package com.epam.benchmark;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.epam.benchmark.model.FullObject;
import com.squareup.moshi.Moshi;

import java.io.InputStream;
import java.util.List;

import okio.Okio;

/**
 * @author Egor Makovsky
 */
public class MoshiMember implements IMember {
    private Moshi moshi;

    private FullObject fullObject;

    @Override
    public String getName() {
        return "Moshi parser";
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
        moshi = new Moshi.Builder().build();
    }

    @Override
    public void onActivityDestroy(Activity activity) {

    }


    @Override
    public void process(Context context, InputStream inputStream) throws Exception {
        fullObject = moshi.adapter(FullObject.class).fromJson(Okio.buffer(Okio.source(inputStream)));
    }

    @Override
    public List<IEntity> getCachedEntities(Context context) {
        return fullObject.getResponseItems();
    }

    @Override
    public List<IEntity> getCachedEntitiesWithFilter(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        return fullObject.getResponseItems();
    }

    @Override
    public void delete(Context context) {

    }

    @Override
    public void finishWorkWithCachedEntities(List<IEntity> cachedEntities) {

    }
}
