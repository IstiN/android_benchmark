package com.epam.benchmark.jackson.model;

import com.epam.benchmark.IEntity;

import java.util.Arrays;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class FullObject {
    private int status;
    private ResponseItem[] response = new ResponseItem[0];

    public List<IEntity> getResponseItems() {
        return (List<IEntity>) (List<?>) Arrays.asList(response);
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
