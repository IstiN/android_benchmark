package by.istin.android.xcore.benchmark.transformer;

import android.content.ContentValues;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import by.istin.android.xcore.annotations.Config;
import by.istin.android.xcore.annotations.converter.IConverter;
import by.istin.android.xcore.annotations.converter.gson.GsonConverter;
import by.istin.android.xcore.utils.StringUtil;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class TagsTransformer extends Config.AbstractTransformer<GsonConverter.Meta> {

    public static final IConverter<GsonConverter.Meta> CONVERTER = new IConverter<GsonConverter.Meta>() {
        @Override
        public void convert(ContentValues contentValues, String fieldValue, Object parent, GsonConverter.Meta meta) {
            JsonElement jsonElement = meta.getJsonElement();
            if (jsonElement.isJsonNull()) {
                return;
            }
            StringBuilder tagsBuilder = new StringBuilder();
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            int size = jsonArray.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    JsonElement item = jsonArray.get(i);
                    tagsBuilder.append(item.getAsString());
                    if (i != size - 1) {
                        tagsBuilder.append(", ");
                    }
                }
                String result = tagsBuilder.toString();
                contentValues.put(fieldValue, result);
            }
        }
    };

    @Override
    public IConverter<GsonConverter.Meta> converter() {
        return CONVERTER;
    }


}
