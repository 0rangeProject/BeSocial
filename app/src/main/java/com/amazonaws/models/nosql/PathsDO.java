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
    private String _userId;
    private Double _lon;
    private Double _endTime;
    private Double _lat;
    private Double _startTime;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexNames = {"end-index","start-index","lat-index",})
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "lon")
    @DynamoDBAttribute(attributeName = "lon")
    public Double getLon() {
        return _lon;
    }

    public void setLon(final Double _lon) {
        this._lon = _lon;
    }
    @DynamoDBIndexRangeKey(attributeName = "endTime", globalSecondaryIndexName = "end-index")
    public Double getEndTime() {
        return _endTime;
    }

    public void setEndTime(final Double _endTime) {
        this._endTime = _endTime;
    }
    @DynamoDBIndexRangeKey(attributeName = "lat", globalSecondaryIndexName = "lat-index")
    public Double getLat() {
        return _lat;
    }

    public void setLat(final Double _lat) {
        this._lat = _lat;
    }
    @DynamoDBIndexRangeKey(attributeName = "startTime", globalSecondaryIndexName = "start-index")
    public Double getStartTime() {
        return _startTime;
    }

    public void setStartTime(final Double _startTime) {
        this._startTime = _startTime;
    }

}