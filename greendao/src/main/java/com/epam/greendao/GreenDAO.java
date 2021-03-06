package com.epam.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.epam.benchmark.util.CloseableList;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.QueryBuilder;


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
        Model[] models = new Model[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            models[i] = new Model(entities.get(i));
        }

        try {
            dao.insertInTx(models);
        } catch (SQLiteConstraintException e) {
            Log.e(GREENDAO, "Cant save entities", e);
        }
    }

    @Override
    public CloseableList<IEntity> getEntities(Context context) {
        return convert(dao.queryBuilder().listLazyUncached());
    }

    private CloseableList<IEntity> convert(final LazyList<? extends IEntity> list) {
        return new CloseableList.Delegate<>((List<IEntity>) list, new Closeable() {
            @Override
            public void close() throws IOException {
                list.close();
            }
        });
    }

    @Override
    public CloseableList<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
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

        return convert(builder.listLazyUncached());
    }

    @Override
    public void clear(Context context) {
        dao.deleteAll();
    }

    @Override
    public void clearResources(Context context) {

    }
}
