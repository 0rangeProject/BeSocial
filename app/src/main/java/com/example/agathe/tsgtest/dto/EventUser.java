package com.example.agathe.tsgtest.dto;

import java.util.ArrayList;

/**
 * Created by DanchaoGU on 2017/2/15.
 */

public class EventUser {
    private String userId = "";
    private String userName = "";
    private ArrayList<String> eventsCreated;
    private ArrayList<String> eventsInterested;
    private ArrayList<String> eventsJoined;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(ArrayList<String> eventsCreated) {
        this.eventsCreated = eventsCreated;
    }

    public ArrayList<String> getEventsInterested() {
        return eventsInterested;
    }

    public void setEventsInterested(ArrayList<String> eventsInterested) {
        this.eventsInterested = eventsInterested;
    }

    public ArrayList<String> getEventsJoined() {
        return eventsJoined;
    }

    public void setEventsJoined(ArrayList<String> eventsJoined) {
        this.eventsJoined = eventsJoined;
    }
}
