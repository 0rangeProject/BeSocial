package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "orangesocialservices-mobilehub-546780328-Paths")

public class PathsDO {
    private String _pathId;
    private int _endTime;
    private Double _lat;
    private Double _lon;
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
    @DynamoDBIndexRangeKey(attributeName = "endTime", globalSecondaryIndexName = "endTime-index")
    public int getEndTime() {
        return _endTime;
    }

    public void setEndTime(final int _endTime) {
        this._endTime = _endTime;
    }
    @DynamoDBIndexRangeKey(attributeName = "lat", localSecondaryIndexName = "lat-index")
    public Double getLat() {
        return _lat;
    }

    public void setLat(final Double _lat) {
        this._lat = _lat;
    }
    @DynamoDBIndexRangeKey(attributeName = "lon", localSecondaryIndexName = "lon-index")
    public Double getLon() {
        return _lon;
    }

    public void setLon(final Double _lon) {
        this._lon = _lon;
    }
    @DynamoDBIndexRangeKey(attributeName = "startTime", localSecondaryIndexName = "startTime-index")
    public int getStartTime() {
        return _startTime;
    }

    public void setStartTime(final int _startTime) {
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
