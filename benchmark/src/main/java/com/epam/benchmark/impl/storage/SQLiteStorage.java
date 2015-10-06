package com.epam.benchmark.impl.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.epam.benchmark.util.CloseableList;
import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.epam.benchmark.util.CursorList;

import java.util.LinkedList;
import java.util.List;

/**
 * // TODO: 10/1/2015 move to separate library
 *
 * @author Egor Makovsky
 */
public class SQLiteStorage implements IStorage {

    private MySQLiteHelper sqLiteHelper;

    @Override
    public void init(Context context) {
        sqLiteHelper = new MySQLiteHelper(context);
    }

    @Override
    public void save(Context context, List<IEntity> entities) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("INSERT INTO Entities VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
        try {
            db.beginTransaction();
            for (IEntity entity : entities) {
                statement.clearBindings();

                statement.bindString(1, entity.getId());
                statement.bindLong(2, entity.getIndex());
                statement.bindLong(3, entity.isActive() ? 1 : 0);
                statement.bindString(4, entity.getPicture() == null ? "" : entity.getPicture());
                statement.bindString(5, entity.getEmployeeName() == null ? "" : entity.getEmployeeName());
                statement.bindString(6, entity.getEmployeeCompany() == null ? "" : entity.getEmployeeCompany());
                statement.bindString(7, entity.getEmployeeEmail() == null ? "" : entity.getEmployeeEmail());
                statement.bindString(8, entity.getEmployeeAbout() == null ? "" : entity.getEmployeeAbout());
                statement.bindString(9, entity.getEmployeeRegisteredFormatted() == null ? "" : entity.getEmployeeRegisteredFormatted());
                statement.bindDouble(10, entity.getLatitude());
                statement.bindDouble(11, entity.getLongitude());
                statement.bindString(12, entity.getTags() == null ? "" : entity.getTags());

                statement.execute();
            }
            db.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            Log.e("SQLiteStorage", "Cannot insert entities", e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public CloseableList<IEntity> getEntities(Context context) {
        return cursorToEntities(sqLiteHelper.getReadableDatabase().rawQuery("select * from Entities", null));
    }

    @Override
    public CloseableList<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        List<String> whereArgs = new LinkedList<>();
        if (isActive != null) {
            whereArgs.add("isActive=" + (isActive ? 1 : 0));
        }
        if (employeeName != null) {
            whereArgs.add("employeeName=\"" + employeeName + "\"");
        }
        if (startIndex != null) {
            whereArgs.add("index_>=" + startIndex);
        }
        if (endIndex != null) {
            whereArgs.add("index_<=" + endIndex);
        }

        String where = whereArgs.isEmpty() ? "" : " where " + TextUtils.join(" AND ", whereArgs);

        return cursorToEntities(sqLiteHelper.getReadableDatabase().rawQuery("select * from Entities" + where, null));
    }

    @Override
    public void clear(Context context) {
        sqLiteHelper.getWritableDatabase().execSQL("delete from Entities");
    }

    @Override
    public void clearResources(Context context) {
        sqLiteHelper.close();
    }

    private CloseableList<IEntity> cursorToEntities(Cursor cursor) {
        return new CursorList<>(new CursorToEntityConverterImpl(), cursor, false);
    }

    private class CursorToEntityConverterImpl implements CursorList.CursorToEntityConverter<IEntity> {
        @Override
        public IEntity convert(Cursor cursor) {
            return new EntityImpl(
                cursor.getString(0),
                cursor.getInt(1),
                cursor.getInt(2) == 1,
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getDouble(9),
                cursor.getDouble(10),
                cursor.getString(11)
            );
        }
    }

    private class MySQLiteHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 2;
        private static final String DATABASE_NAME = "SimpleSQLiteStorage";

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_BOOK_TABLE = "CREATE TABLE Entities ( " +
                "id TEXT PRIMARY KEY NOT NULL, " +
                "index_ INTEGER, " +
                "isActive INTEGER," +
                "picture TEXT," +
                "employeeName TEXT," +
                "employeeCompany TEXT," +
                "employeeEmail TEXT," +
                "employeeAbout TEXT," +
                "employeeRegisteredFormatted TEXT," +
                "latitude REAL," +
                "longitude REAL," +
                "tags TEXT" +
                ")";

            db.execSQL(CREATE_BOOK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Entities");
            this.onCreate(db);
        }
    }

    private class EntityImpl implements IEntity {
        private String id;
        private Integer index;
        private boolean isActive;
        private String picture;
        private String employeeName;
        private String employeeCompany;
        private String employeeEmail;
        private String employeeAbout;
        private String employeeRegisteredFormatted;
        private Double latitude;
        private Double longitude;
        private String tags;

        public EntityImpl(String id, Integer index, boolean isActive, String picture, String employeeName, String employeeCompany, String employeeEmail, String employeeAbout, String employeeRegisteredFormatted, Double latitude, Double longitude, String tags) {
            this.id = id;
            this.index = index;
            this.isActive = isActive;
            this.picture = picture;
            this.employeeName = employeeName;
            this.employeeCompany = employeeCompany;
            this.employeeEmail = employeeEmail;
            this.employeeAbout = employeeAbout;
            this.employeeRegisteredFormatted = employeeRegisteredFormatted;
            this.latitude = latitude;
            this.longitude = longitude;
            this.tags = tags;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public Integer getIndex() {
            return index;
        }

        @Override
        public Boolean isActive() {
            return isActive;
        }

        @Override
        public String getPicture() {
            return picture;
        }

        @Override
        public String getEmployeeName() {
            return employeeName;
        }

        @Override
        public String getEmployeeCompany() {
            return employeeCompany;
        }

        @Override
        public String getEmployeeEmail() {
            return employeeEmail;
        }

        @Override
        public String getEmployeeAbout() {
            return employeeAbout;
        }

        @Override
        public String getEmployeeRegisteredFormatted() {
            return employeeRegisteredFormatted;
        }

        @Override
        public Double getLatitude() {
            return latitude;
        }

        @Override
        public Double getLongitude() {
            return longitude;
        }

        @Override
        public String getTags() {
            return tags;
        }

        @Override
        public void print() {
            Log.d("SimpleSqlite", TextUtils.join(
                "|", new Object[]{
                    getId(),
                    getIndex(),
                    isActive(),
                    getPicture(),
                    getEmployeeName(),
                    getEmployeeCompany(),
                    getEmployeeEmail(),
                    getEmployeeAbout(),
                    getEmployeeRegisteredFormatted(),
                    getLatitude(),
                    getLongitude(),
                    getTags()}));
        }
    }
}
