package com.epam.realmio;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.util.Utils;

/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class ModelWrapper implements IEntity {
    private Model item;

    public ModelWrapper(Model item) {
        this.item = item;
    }

    @Override
    public String getId() {
        return item.getId();
    }

    @Override
    public Integer getIndex() {
        return item.getIndex();
    }

    @Override
    public Boolean isActive() {
        return item.isActive();
    }

    @Override
    public String getPicture() {
        return item.getPicture();
    }

    @Override
    public String getEmployeeName() {
        return item.getName();
    }

    @Override
    public String getEmployeeCompany() {
        return item.getCompany();
    }

    @Override
    public String getEmployeeEmail() {
        return item.getEmail();
    }

    @Override
    public String getEmployeeAbout() {
        return item.getAbout();
    }

    @Override
    public String getEmployeeRegisteredFormatted() {
        return item.getRegistered();
    }

    @Override
    public Double getLatitude() {
        return item.getLatitude();
    }

    @Override
    public Double getLongitude() {
        return item.getLongitude();
    }

    @Override
    public String getTags() {
        return null;
    }

    @Override
    public void print() {
        Utils.print("Realm.io", this);
    }
}
