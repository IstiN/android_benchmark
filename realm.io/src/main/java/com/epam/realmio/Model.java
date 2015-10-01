package com.epam.realmio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Egor Makovsky
 */
public class Model extends RealmObject {

    @PrimaryKey
    private String id;
    private int index;
    private boolean isActive;
    private String picture;
    private String name;
    private String company;
    private String email;
    private String about;
    private String registered;
    private double latitude;
    private double longitude;

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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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

}
