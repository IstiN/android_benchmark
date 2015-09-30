package com.epam.benchmark.model;

import android.text.TextUtils;
import android.util.Log;

import com.epam.benchmark.IEntity;

/**
 * @author Egor Makovsky
 */
public class ResponseItem implements IEntity {
    private String id;
    private int index;
    private boolean isActive;
    private String picture;
    private Employee employee = new Employee();
    private String[] tags = new String[0];

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
        return employee.getName();
    }

    @Override
    public String getEmployeeCompany() {
        return employee.getCompany();
    }

    @Override
    public String getEmployeeEmail() {
        return employee.getEmail();
    }

    @Override
    public String getEmployeeAbout() {
        return employee.getAbout();
    }

    @Override
    public String getEmployeeRegisteredFormatted() {
        return employee.getRegistered();
    }

    @Override
    public Double getLatitude() {
        return employee.getLatitude();
    }

    @Override
    public Double getLongitude() {
        return employee.getLongitude();
    }

    @Override
    public String getTags() {
        return TextUtils.join(", ", tags);
    }

    @Override
    public void print() {
        Log.d("test", "id: " + getId() + ", name: " + getEmployeeName());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
