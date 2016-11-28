package com.example.agathe.tsgtest.Dto;

/**
 * Created by agathe on 08/11/16.
 */

public class Path {

    private int userId = 0;

    private int pathId = 0;

    private String startTime = "";

    private String endTime = "";

    private double lon = 0f;

    private double lat = 0f;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    @Override
    public String toString() {
        return "Path{" +
                "userId='" + userId + '\'' +
                ", pathId='" + pathId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    // Constructor
    public Path(int userId, int pathId, String startTime, String endTime, double lon, double lat) {
        this.userId = userId;
        this.pathId = pathId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lon = lon;
        this.lat = lat;
    }

    // Empty constructor
    public Path() {
    }
}