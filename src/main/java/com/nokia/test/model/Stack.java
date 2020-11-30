package com.nokia.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "stack")
@Entity
public class Stack implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private String data;

    public Stack() {
    }

    public Stack(Long id, String data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Stack{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
