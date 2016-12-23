package com.example.agathe.tsgtest.dto;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by agathe on 23/12/16.
 */

public class ManualTrip {
    public String departure;
    public String destination;
    public LatLng departurePosition;
    public LatLng destinationPosition;

    public ManualTrip(String departure, String destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public ManualTrip(String departure, String destination, LatLng departurePosition, LatLng destinationPosition) {
        this.departure = departure;
        this.destination = destination;
        this.departurePosition = departurePosition;
        this.destinationPosition = destinationPosition;
    }
}
