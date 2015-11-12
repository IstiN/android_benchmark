package by.istin.android.xcore.benchmark;

import android.content.ContentValues;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import by.istin.android.xcore.benchmark.assist.ResultContentValuesWrapper;
import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.benchmark.gson.Response;
import by.istin.android.xcore.gson.AbstractValuesAdapter;
import by.istin.android.xcore.gson.external.ArrayAdapterFactory;
import by.istin.android.xcore.utils.ReflectUtils;
import by.istin.android.xcore.utils.StringUtil;

/**
 * Created by uladzimir_klyshevich on 11/11/15.
 */
public class XcoreParser implements IParser {

    private Gson mLittleGson;

    private Gson mBigGson;

    private Listener listener;

    private AbstractValuesAdapter jsonContentValuesAdapter = new AbstractValuesAdapter(SourceEntity.class) {

        @Override
        protected void proceedSubEntities(Type type, JsonDeserializationContext jsonDeserializationContext, ContentValues contentValues, ReflectUtils.XField field, JsonArray jsonArray) {

        }

        @Override
        protected void proceedSubEntity(Type type, JsonDeserializationContext jsonDeserializationContext, ContentValues contentValues, ReflectUtils.XField field, Class<?> clazz, JsonObject subEntityJsonObject) {

        }

        @Override
        protected ContentValues proceed(ContentValues parent, int position, ContentValues contentValues, JsonElement jsonElement) {
            if (listener != null) {
                final ResultContentValuesWrapper.ContentValuesEntity entity = new ResultContentValuesWrapper.ContentValuesEntity();
                entity.mContentValues = contentValues;
                listener.onEntityRead(entity);
            }
            return contentValues;
        }
    };

    @Override
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(ContentValues.class, jsonContentValuesAdapter);
        gsonBuilder.registerTypeAdapterFactory(new ArrayAdapterFactory(-1, jsonContentValuesAdapter));
        mLittleGson = gsonBuilder.create();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(ContentValues.class, jsonContentValuesAdapter);
        gsonBuilder.registerTypeAdapterFactory(new ArrayAdapterFactory(0, jsonContentValuesAdapter));
        mBigGson = gsonBuilder.create();
    }

    @Override
    public List<IEntity> parse(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StringUtil.DEFAULT_ENCODING);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
        final Response response = mLittleGson.fromJson(bufferedReader, Response.class);
        return new ResultContentValuesWrapper(response.getResponse());
    }

    @Override
    public void parse(InputStream inputStream, Listener listener) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StringUtil.DEFAULT_ENCODING);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
        this.listener = listener;
        mBigGson.fromJson(bufferedReader, Response.class);
        this.listener.onReadFinished();
        this.listener = null;
    }

}
