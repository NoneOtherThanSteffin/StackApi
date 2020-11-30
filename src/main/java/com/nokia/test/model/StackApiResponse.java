package com.nokia.test.model;

public class StackApiResponse {

    String data;

    public StackApiResponse() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StackApiResponse{" +
                "data='" + data + '\'' +
                '}';
    }
}
