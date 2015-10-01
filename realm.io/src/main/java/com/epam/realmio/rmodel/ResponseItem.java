package com.epam.realmio.rmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Egor Makovsky
 */
public class ResponseItem extends RealmObject {

    private String id;
    @PrimaryKey
    private int index;
    private boolean isActive;
    private String picture;
    private Employee employee = new Employee();

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getPicture() {
        return picture;
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
}
