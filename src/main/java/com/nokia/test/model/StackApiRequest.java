package com.nokia.test.model;

public class StackApiRequest {

    private String data;

    public StackApiRequest() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StackApiRequest{" +
                "data='" + data + '\'' +
                '}';
    }
}
