package com.epam.benchmark;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.epam.benchmark.model.FullObject;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class JacksonMember implements IMember {
    private ObjectMapper mapper;

    private FullObject fullObject;

    @Override
    public String getName() {
        return "Jackson parser";
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
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void onActivityDestroy(Activity activity) {

    }

    @Override
    public void process(Context context, InputStream inputStream) throws Exception {
        fullObject = mapper.readValue(inputStream, FullObject.class);
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
