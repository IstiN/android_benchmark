package com.epam.realmio.rmodel;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @author Egor Makovsky
 */
public class FullObject extends RealmObject {
    private int status;
    private RealmList<ResponseItem> response = new RealmList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RealmList<ResponseItem> getResponse() {
        return response;
    }

    public void setResponse(RealmList<ResponseItem> response) {
        this.response = response;
    }
}
