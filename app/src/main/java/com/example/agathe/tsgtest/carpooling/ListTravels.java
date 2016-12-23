package com.example.agathe.tsgtest.carpooling;

import com.example.agathe.tsgtest.dto.CommonTravel;

import java.util.List;

/**
 * Created by agathe on 19/12/16.
 */

public class ListTravels {

    public List<CommonTravel> travels;

    public List<CommonTravel> getUsers() {
        return travels;
    }

    public void setUsers(List<CommonTravel> users) {
        this.travels = users;
    }
}
