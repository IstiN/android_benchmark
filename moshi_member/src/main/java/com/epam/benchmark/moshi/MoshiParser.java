package com.epam.benchmark.moshi;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser;
import com.epam.benchmark.moshi.model.FullObject;
import com.epam.benchmark.moshi.model.ResponseItem;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;

import java.io.InputStream;
import java.util.List;

import okio.Okio;

/**
 * @author Egor Makovsky
 */
public class MoshiParser implements IParser {
    private Moshi moshi;

    @Override
    public void init() {
        moshi = new Moshi.Builder().build();
    }

    @Override
    public List<IEntity> parse(InputStream inputStream) throws Exception {
        return moshi.adapter(FullObject.class).fromJson(Okio.buffer(Okio.source(inputStream))).getResponseItems();
    }

    @Override
    public void parse(InputStream inputStream, Listener listener) throws Exception {
        JsonReader jsonReader = JsonReader.of(Okio.buffer(Okio.source(inputStream)));

        jsonReader.beginObject();

        jsonReader.skipValue(); // status name
        jsonReader.skipValue(); // status value
        jsonReader.skipValue(); // response name

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            listener.onEntityRead(moshi.adapter(ResponseItem.class).fromJson(jsonReader));
        }

        jsonReader.endArray();
        jsonReader.endObject();

        listener.onReadFinished();
    }
}
