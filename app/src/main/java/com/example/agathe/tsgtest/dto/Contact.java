package com.example.agathe.tsgtest.dto;

import java.util.List;

/**
 * Created by agathe on 23/01/17.
 */

public class Contact {

    private String name = "";
    private String phoneNumber = "";
    private String relationStrength = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRelationStrength() {
        return relationStrength;
    }

    public void setRelationStrength(String relationStrength) {
        this.relationStrength = relationStrength;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", relationStrength='" + relationStrength + '\'' +
                '}';
    }
}
