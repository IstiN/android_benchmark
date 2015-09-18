package by.istin.android.xcore.benchmark.processor;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.processor.IProcessor;
import by.istin.android.xcore.provider.IDBContentProviderSupport;
import by.istin.android.xcore.source.DataSourceRequest;
import by.istin.android.xcore.source.IDataSource;
import by.istin.android.xcore.utils.IOUtils;
import by.istin.android.xcore.utils.Log;
import by.istin.android.xcore.utils.StringUtil;

/**
 * Created by uladzimir_klyshevich on 8/7/15.
 */
public class Source2Processor implements IProcessor<Void, InputStream> {

    public static final String APP_SERVICE_KEY = "source:processor";
    private final String path;

    private Class<?> clazz;

    private IDBContentProviderSupport contentProviderSupport;

    public Source2Processor(IDBContentProviderSupport contentProviderSupport) {
        super();
        this.clazz = SourceEntity.class;
        this.contentProviderSupport = contentProviderSupport;
        this.path = "response";
    }

    @Override
    public Void execute(DataSourceRequest dataSourceRequest, IDataSource<InputStream> dataSource, InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StringUtil.DEFAULT_ENCODING);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 1024);
        JsonReader jsonReader = new JsonReader(bufferedReader);
        try {
            JsonToken peek = jsonReader.peek();
            while (peek != JsonToken.END_DOCUMENT) {
                if (peek == JsonToken.BEGIN_OBJECT) {
                    jsonReader.beginObject();
                    peek = jsonReader.peek();
                    if (peek == JsonToken.NAME) {
                        String name = jsonReader.nextName();
                        if (name.equals(path)) {
                            peek = jsonReader.peek();
                            if (peek == JsonToken.BEGIN_ARRAY) {
                                jsonReader.beginArray();
                            } else {
                                //jsonReader.skipValue();
                            }
                        }
                    }
                } else if (peek == JsonToken.BEGIN_ARRAY) {
                    jsonReader.beginArray();
                    //jsonReader.skipValue();
                } else {

                    //jsonReader.skipValue();
                }
                peek = jsonReader.peek();
            }
        } finally {
            IOUtils.close(inputStream);
            IOUtils.close(inputStreamReader);
            IOUtils.close(bufferedReader);
            IOUtils.close(jsonReader);
        }
        return null;
    }

    @Override
    public void cache(Context context, DataSourceRequest dataSourceRequest, Void result) throws Exception {

    }

    @Override
    public String getAppServiceKey() {
        return APP_SERVICE_KEY;
    }
}
