package by.istin.android.xcore.benchmark.transformer;

import android.content.ContentValues;

import com.google.gson.JsonElement;

import org.apache.commons.lang3.time.internal.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

import by.istin.android.xcore.annotations.Config;
import by.istin.android.xcore.annotations.converter.IConverter;
import by.istin.android.xcore.annotations.converter.gson.GsonConverter;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class DateTransformer extends Config.AbstractTransformer<GsonConverter.Meta> {

    private static final FastDateFormat SOURCE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss .SSS");
    public static final FastDateFormat RESULT_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");

    public static final IConverter<GsonConverter.Meta> CONVERTER = new IConverter<GsonConverter.Meta>() {
        @Override
        public void convert(ContentValues contentValues, String fieldValue, Object parent, GsonConverter.Meta meta) {
            JsonElement jsonElement = meta.getJsonElement();
            if (jsonElement.isJsonNull()) {
                return;
            }
            String dateValue = jsonElement.getAsString();
            //"2014-03-11T10:49:09 -03:00"

            Date parse = null;
            try {
                parse = SOURCE_FORMATTER.parse(dateValue);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            dateValue = RESULT_FORMATTER.format(parse);
            contentValues.put(fieldValue, dateValue);
        }
    };

    @Override
    public IConverter<GsonConverter.Meta> converter() {
        return CONVERTER;
    }


}
