package com.epam.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;

import java.io.IOException;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class GreenDAO implements IStorage {

    public static final String GREENDAO = "greendao";
    private ModelDao dao;

    @Override
    public void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, GREENDAO, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        dao = daoSession.getModelDao();
    }

    @Override
    public void save(Context context, List<IEntity> entities) {
        SQLiteDatabase db = dao.getDatabase();
        db.beginTransaction();

        try {
            for (IEntity entity : entities) {
                dao.insertOrReplace(new Model(entity));
            }
            db.setTransactionSuccessful();
        } catch (Exception ignored) {
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        return convert(dao.queryBuilder().list());
    }

    private List<IEntity> convert(List<Model> list) {
        List temp = list;
        List<IEntity> result = temp;
        return result;
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        QueryBuilder<Model> builder = dao.queryBuilder();
        if (isActive != null) {
            builder.where(ModelDao.Properties.IsActive.eq(isActive));
        }
        if (employeeName != null) {
            builder.where(ModelDao.Properties.Name.eq(employeeName));
        }

        if (startIndex != null && endIndex != null) {
            builder.where(ModelDao.Properties.Index.between(startIndex, endIndex));
        }

        return convert(builder.list());
    }

    @Override
    public void clear(Context context) {
        dao.deleteAll();
    }

    @Override
    public void clearResources() {

    }
}
