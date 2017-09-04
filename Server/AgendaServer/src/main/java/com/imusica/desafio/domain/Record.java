package com.imusica.desafio.domain;

/**
 * Created by gustavoamg on 30/05/17.
 *
 * Record model
 */
public class Record {

    private int id;
    private String name;
    private String phone;

    public Record(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
