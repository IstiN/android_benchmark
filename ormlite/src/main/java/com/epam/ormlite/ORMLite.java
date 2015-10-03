package com.epam.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class ORMLite implements IStorage {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "orm_lite";
    private static final String TAG = "ORMLite";

    private Dao<Model, String> dao;

    @Override
    public void init(Context context) {
        OrmLiteHelper ormLiteHelper = new OrmLiteHelper(context);
        try {
            dao = ormLiteHelper.getDao(Model.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void save(Context context, final List<IEntity> entities) {
        try {
            dao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (IEntity entity : entities) {
                        try {
                            dao.create(new Model(entity));
                        } catch (SQLException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        try {
            List<Model> models = dao.queryForAll();
            return convert(models);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private List<IEntity> convert(List<Model> models) {
        List temp = models;
        List<IEntity> iEntities = temp;
        return iEntities;
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        Where<Model, String> where = dao.queryBuilder().where();
        try {
            if (isActive != null) {
                where.eq("isActive", isActive).and();
            }

            if (employeeName != null) {
                where.eq("name", employeeName).and();
            }

            if (startIndex != null && endIndex != null) {
                where.between("index", startIndex, endIndex).and();
            }

            return convert(where.query());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public void clear(Context context) {
        try {
            dao.deleteBuilder().delete();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void clearResources() {

    }

    private class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

        public OrmLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, Model.class);
            } catch (Exception e) {
                Log.e(TAG, "Can't create database", e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        }
    }
}
