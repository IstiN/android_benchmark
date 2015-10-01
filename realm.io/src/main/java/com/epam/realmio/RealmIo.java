package com.epam.realmio;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.epam.realmio.rmodel.Employee;
import com.epam.realmio.rmodel.FullObject;
import com.epam.realmio.rmodel.ResponseItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class RealmIo implements IStorage {

    @Override
    public void init(Context context) {

    }

    @Override
    public void save(Context context, final List<IEntity> entities) {
        Realm realm = checkRealm(context);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<ResponseItem> items = convert(entities);
                realm.copyToRealmOrUpdate(items);
            }
        });
    }

    @NonNull
    private List<ResponseItem> convert(List<IEntity> entities) {
        List<ResponseItem> items = new ArrayList<>(entities.size());
        for (IEntity entity : entities) {
            ResponseItem item = new ResponseItem();
            item.setId(entity.getId());
            item.setIndex(entity.getIndex());
            item.setIsActive(entity.isActive());
            item.setPicture(entity.getPicture());
            {
                Employee employee = new Employee();
                employee.setAbout(entity.getEmployeeAbout());
                employee.setCompany(entity.getEmployeeCompany());
                employee.setEmail(entity.getEmployeeEmail());
                employee.setLatitude(entity.getLatitude());
                employee.setLongitude(entity.getLongitude());
                employee.setName(entity.getEmployeeName());
                employee.setRegistered(entity.getEmployeeRegisteredFormatted());
                item.setEmployee(employee);
            }
            items.add(item);
        }
        return items;
    }

    @Override
    public List<IEntity> getEntities(Context context) {
        checkRealm(context);
        RealmResults<ResponseItem> all = checkRealm(context).where(ResponseItem.class).findAll();
        return convert(all);
    }

    private List<IEntity> convert(RealmResults<ResponseItem> items) {
        List<IEntity> list = new ArrayList<>(items.size());
        for (ResponseItem item : items) {
            com.epam.realmio.model.ResponseItem responseItem = new com.epam.realmio.model.ResponseItem();
            responseItem.setPicture(item.getPicture());
            responseItem.setIsActive(item.isActive());
            responseItem.setIndex(item.getIndex());
            responseItem.setId(item.getId());

            {
                com.epam.realmio.model.Employee employee = new com.epam.realmio.model.Employee();
                employee.setRegistered(item.getEmployee().getRegistered());
                employee.setName(item.getEmployee().getName());
                employee.setLongitude(item.getEmployee().getLongitude());
                employee.setLatitude(item.getEmployee().getLatitude());
                employee.setAbout(item.getEmployee().getAbout());
                employee.setCompany(item.getEmployee().getCompany());
                employee.setEmail(item.getEmployee().getEmail());

                responseItem.setEmployee(employee);
            }

            list.add(responseItem);
        }

        return list;
    }

    @Override
    public List<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        RealmResults<ResponseItem> all = checkRealm(context)
                .where(ResponseItem.class)
                .equalTo("isActive", isActive)
                .equalTo("employee.name", employeeName)
                .between("index", startIndex, endIndex)
                .findAll();
        return convert(all);
    }

    @Override
    public void clear(Context context) {
        checkRealm(context).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.clear(FullObject.class);
                realm.clear(ResponseItem.class);
                realm.clear(Employee.class);
            }
        });
    }

    private Realm checkRealm(Context context) {
        return Realm.getInstance(context);
    }

    @Override
    public void clearResources() {

    }
}
