package com.epam.realmio;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dd.realmbrowser.RealmBrowser;
import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class RealmIo implements IStorage {

    static {
        RealmBrowser.getInstance().addRealmModel(Model.class);
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void save(Context context, final List<IEntity> entities) {
        Realm realm = getRealm(context);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<Model> items = convert(entities);
                realm.copyToRealmOrUpdate(items);
            }
        });
    }

    @NonNull
    private List<Model> convert(List<IEntity> entities) {
        List<Model> items = new ArrayList<>(entities.size());
        for (IEntity entity : entities) {
            Model item = new Model();
            item.setId(entity.getId());
            item.setIndex(entity.getIndex());
            item.setIsActive(entity.isActive());
            item.setPicture(entity.getPicture());
            item.setAbout(entity.getEmployeeAbout());
            item.setCompany(entity.getEmployeeCompany());
            item.setEmail(entity.getEmployeeEmail());
            item.setLatitude(entity.getLatitude());
            item.setLongitude(entity.getLongitude());
            item.setName(entity.getEmployeeName());
            item.setRegistered(entity.getEmployeeRegisteredFormatted());
            items.add(item);
        }
        return items;
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        RealmResults<Model> all = getRealm(context).where(Model.class).findAll();
        return convert(all);
    }

    private List<IEntity> convert(RealmResults<Model> items) {
        List<IEntity> list = new ArrayList<>(items.size());
        for (Model item : items) {
            list.add(new ModelWrapper(item));
        }

        return list;
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        RealmQuery<Model> query = getRealm(context).where(Model.class);

        if (isActive != null) {
            query.equalTo("isActive", isActive);
        }

        if (employeeName != null) {
            query.equalTo("name", employeeName);
        }

        if (startIndex != null && endIndex != null) {
            query.between("index", startIndex, endIndex);
        }

        return convert(query.findAll());
    }

    @Override
    public void clear(Context context) {
        getRealm(context).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.clear(Model.class);
            }
        });
    }

    private Realm getRealm(Context context) {
        return Realm.getInstance(context);
    }

    @Override
    public void clearResources() {

    }
}
