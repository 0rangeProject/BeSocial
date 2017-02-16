package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "orangesocialservices-mobilehub-546780328-EventUsers")

public class EventUsersDO {
    private String _userId;
    private List<String> _eventsCreated;
    private List<String> _eventsInterested;
    private List<String> _eventsJoined;
    private String _userName;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "eventsCreated")
    public List<String> getEventsCreated() {
        return _eventsCreated;
    }

    public void setEventsCreated(final List<String> _eventsCreated) {
        this._eventsCreated = _eventsCreated;
    }
    @DynamoDBAttribute(attributeName = "eventsInterested")
    public List<String> getEventsInterested() {
        return _eventsInterested;
    }

    public void setEventsInterested(final List<String> _eventsInterested) {
        this._eventsInterested = _eventsInterested;
    }
    @DynamoDBAttribute(attributeName = "eventsJoined")
    public List<String> getEventsJoined() {
        return _eventsJoined;
    }

    public void setEventsJoined(final List<String> _eventsJoined) {
        this._eventsJoined = _eventsJoined;
    }
    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return _userName;
    }

    public void setUserName(final String _userName) {
        this._userName = _userName;
    }

}
