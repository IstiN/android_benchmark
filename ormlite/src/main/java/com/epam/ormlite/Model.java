package com.epam.ormlite;


import com.epam.benchmark.IEntity;
import com.epam.benchmark.util.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Egor Makovsky
 */
@DatabaseTable
public class Model implements IEntity {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private int index;
    @DatabaseField
    private boolean isActive;
    @DatabaseField
    private String picture;
    @DatabaseField
    private String name;
    @DatabaseField
    private String company;
    @DatabaseField
    private String email;
    @DatabaseField
    private String about;
    @DatabaseField
    private String registered;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;

    public Model() {
    }

    public Model(IEntity entity) {
        id = entity.getId();
        index = entity.getIndex();
        isActive = entity.isActive();
        picture = entity.getPicture();
        name = entity.getEmployeeName();
        company = entity.getEmployeeCompany();
        email = entity.getEmployeeEmail();
        about = entity.getEmployeeAbout();
        registered = entity.getEmployeeRegisteredFormatted();
        latitude = entity.getLatitude();
        longitude = entity.getLongitude();
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getAbout() {
        return about;
    }

    public String getRegistered() {
        return registered;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String getTags() {
        return null;
    }

    @Override
    public void print() {
        Utils.print("ORMLite", this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

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


    public String getPicture() {
        return picture;
    }

    @Override
    public String getEmployeeName() {
        return name;
    }

    @Override
    public String getEmployeeCompany() {
        return company;
    }

    @Override
    public String getEmployeeEmail() {
        return email;
    }

    @Override
    public String getEmployeeAbout() {
        return about;
    }

    @Override
    public String getEmployeeRegisteredFormatted() {
        return registered;
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

}
