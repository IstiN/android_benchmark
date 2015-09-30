package com.epam.benchmark.jackson.model;

import com.epam.benchmark.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class FullObject {
    private int status;
    private ResponseItem[] response = new ResponseItem[0];

    public List<IEntity> getResponseItems() {
        ArrayList<IEntity> iEntities = new ArrayList<>();
        for (ResponseItem item : response) {
            iEntities.add(item);
        }
        return iEntities;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseItem[] getResponse() {
        return response;
    }

    public void setResponse(ResponseItem[] response) {
        this.response = response;
    }
}
