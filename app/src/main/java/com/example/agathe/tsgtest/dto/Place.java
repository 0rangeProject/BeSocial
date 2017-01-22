package com.example.agathe.tsgtest.dto;

import java.util.List;

/**
 * Created by agathe on 22/01/17.
 */

public class Place {
    private Double lon;
    private Double endTime;
    private Double lat;
    private Double startTime;
    private List<User> users;

    public Place(Double lon, Double endTime, Double lat, Double startTime, List<User> users) {
        this.lon = lon;
        this.endTime = endTime;
        this.lat = lat;
        this.startTime = startTime;
        this.users = users;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
