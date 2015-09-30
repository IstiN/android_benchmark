package by.istin.android.xcore.benchmark.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import by.istin.android.xcore.annotations.Config;
import by.istin.android.xcore.annotations.db;
import by.istin.android.xcore.annotations.dbBoolean;
import by.istin.android.xcore.annotations.dbDouble;
import by.istin.android.xcore.annotations.dbEntity;
import by.istin.android.xcore.annotations.dbInteger;
import by.istin.android.xcore.annotations.dbString;
import by.istin.android.xcore.benchmark.transformer.DateTransformer;
import by.istin.android.xcore.benchmark.transformer.TagsTransformer;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.entity.IGenerateID;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.source.DataSourceRequest;
import by.istin.android.xcore.utils.HashUtils;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class SourceEntity implements BaseColumns, IGenerateID {

    @dbString
    public static final String ID = _ID;

    @dbString
    @SerializedName("picture")
    public static final String PICTURE = "p";

    @dbBoolean
    @SerializedName("isActive")
    public static final String IS_ACTIVE = "is_a";

    @dbString
    @SerializedName("id")
    public static final String ID_AS_STRING = "id";

    @dbInteger
    @SerializedName("index")
    public static final String INDEX = "i";

    @dbString
    @SerializedName("employee:email")
    public static final String EMAIL = "e";

    @dbString
    @SerializedName("employee:registered")
    @db(@Config(dbType = Config.DBType.STRING, key = "employee:registered", transformer = DateTransformer.class))
    public static final String REGISTERED = "r";

    @dbString
    @SerializedName("employee:company")
    public static final String COMPANY = "c";

    @dbString
    @SerializedName("employee:name")
    public static final String NAME = "n";

    @dbString
    @SerializedName("employee:about")
    public static final String ABOUT = "a";

    @dbDouble
    @SerializedName("employee:longitude")
    public static final String LONGITUDE = "lo";

    @dbDouble
    @SerializedName("employee:latitude")
    public static final String LATITUDE = "la";

    @db(@Config(dbType = Config.DBType.STRING, key = "tags", transformer = TagsTransformer.class))
    public static final String TAGS = "ta";

    @Override
    public long generateId(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        return HashUtils.generateId(contentValues, ID_AS_STRING);
    }
}
