package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "orangesocialservices-mobilehub-546780328-ManualTrips")

public class ManualTripsDO {
    private String _pathId;
    private int _endTime;
    private String _departure;
    private String _destination;
    private int _startTime;
    private String _userId;

    @DynamoDBHashKey(attributeName = "pathId")
    @DynamoDBAttribute(attributeName = "pathId")
    public String getPathId() {
        return _pathId;
    }

    public void setPathId(final String _pathId) {
        this._pathId = _pathId;
    }
    @DynamoDBAttribute(attributeName = "endTime")
    public int getEndTime() {
        return _endTime;
    }

    public void setEndTime(final int _endTime) {
        this._endTime = _endTime;
    }
    @DynamoDBAttribute(attributeName = "departure")
    public String getDeparture() {
        return _departure;
    }

    public void setDeparture(final String _departure) {
        this._departure = _departure;
    }
    @DynamoDBAttribute(attributeName = "destination")
    public String getDestination() {
        return _destination;
    }

    public void setDestination(final String _destination) {
        this._destination = _destination;
    }
    @DynamoDBAttribute(attributeName = "startTime")
    public int getStartTime() {
        return _startTime;
    }

    public void setStartTime(final int _startTime) {
        this._startTime = _startTime;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
