package com.epam.benchmark.impl.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;

import java.util.ArrayList;
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
        try {
            db.beginTransaction();
            for (IEntity entity : entities) {
                ContentValues values = new ContentValues();
                values.put("id", entity.getId());
                values.put("index_", entity.getIndex());
                values.put("isActive", entity.isActive());
                values.put("picture", entity.getPicture());
                values.put("employeeName", entity.getEmployeeName());
                values.put("employeeCompany", entity.getEmployeeCompany());
                values.put("employeeEmail", entity.getEmployeeEmail());
                values.put("employeeAbout", entity.getEmployeeAbout());
                values.put("employeeRegisteredFormatted", entity.getEmployeeRegisteredFormatted());
                values.put("latitude", entity.getLatitude());
                values.put("longitude", entity.getLongitude());
                values.put("tags", entity.getTags());

                db.insert("Entities", null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        return cursorToEntities(sqLiteHelper.getReadableDatabase().rawQuery("select * from Entities", null));
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
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

    private List<IEntity> cursorToEntities(Cursor cursor) {
        List<IEntity> entities = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                entities.add(new EntityImpl(
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
                ));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return entities;
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
