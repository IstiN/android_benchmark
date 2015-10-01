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

/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class ORMLite implements IStorage {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "orm_lite";

    private Dao<Model, String> dao;

    @Override
    public void init(Context context) {
        OrmLiteHelper ormLiteHelper = new OrmLiteHelper(context);
        try {
            dao = ormLiteHelper.getDao(Model.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Context context, List<IEntity> entities) {
        for (IEntity entity : entities) {
            try {
                dao.createOrUpdate(new Model(entity));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        try {
            List<Model> models = dao.queryForAll();
            return convert(models);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<IEntity> convert(List<Model> models) {
        List temp = models;
        List<IEntity> iEntities = temp;
        return iEntities;
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        Where<Model, String> where = dao.queryBuilder().where();

        if (isActive != null) {
            try {
                where.eq("isActive", isActive).and();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (employeeName != null) {
            try {
                where.eq("name", employeeName).and();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (startIndex != null && endIndex != null) {
            try {
                where.between("index", startIndex, endIndex).and();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            where.isNotNull("index").and();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            return convert(where.query());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void clear(Context context) {
        try {
            dao.delete(dao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
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
                Log.e("ORMLite", "Can't create database", e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        }
    }
}
