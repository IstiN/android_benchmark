package com.epam.realmio.benchmodel;

import android.text.TextUtils;
import android.util.Log;

import com.epam.benchmark.IEntity;
import com.epam.realmio.realmmodel.ResponseItem;

/**
 * Created by shulha_dmytro on 1.10.15.
 */
public class Model implements IEntity {
    private ResponseItem item;

    public Model(ResponseItem item) {
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
        Log.d("Moshi", TextUtils.join(
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
