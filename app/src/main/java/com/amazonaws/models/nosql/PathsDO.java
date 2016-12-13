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

@DynamoDBTable(tableName = "orangesocialservices-mobilehub-546780328-Paths")

public class PathsDO {
    private String _pathId;
    private String _endTime;
    private Double _lat;
    private Double _lon;
    private String _startTime;
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
    public String getEndTime() {
        return _endTime;
    }

    public void setEndTime(final String _endTime) {
        this._endTime = _endTime;
    }
    @DynamoDBAttribute(attributeName = "lat")
    public Double getLat() {
        return _lat;
    }

    public void setLat(final Double _lat) {
        this._lat = _lat;
    }
    @DynamoDBAttribute(attributeName = "lon")
    public Double getLon() {
        return _lon;
    }

    public void setLon(final Double _lon) {
        this._lon = _lon;
    }
    @DynamoDBAttribute(attributeName = "startTime")
    public String getStartTime() {
        return _startTime;
    }

    public void setStartTime(final String _startTime) {
        this._startTime = _startTime;
    }
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "User")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
