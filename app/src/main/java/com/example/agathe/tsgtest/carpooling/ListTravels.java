package com.example.agathe.tsgtest.carpooling;

import com.example.agathe.tsgtest.dto.CommonTravel;
import com.example.agathe.tsgtest.dto.ManualTrip;

import java.util.List;

/**
 * Created by agathe on 19/12/16.
 */

public class ListTravels {

    public List<ManualTrip> travels;

    public List<ManualTrip> getTravels() {
        return travels;
    }

    public void setTravels(List<ManualTrip> travels) {
        this.travels = travels;
    }
}
