package com.epam.realmio.rmodel;

import io.realm.RealmObject;

/**
 * @author Egor Makovsky
 */
public class Employee extends RealmObject {
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
}
