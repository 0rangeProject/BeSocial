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

@DynamoDBTable(tableName = "orangesocialservices-mobilehub-546780328-Travels")

public class TravelsDO {
    private String _travelId;
    private Double _endTime;
    private Double _lat;
    private Double _lon;
    private Double _startTime;
    private String _userId;

    @DynamoDBHashKey(attributeName = "travelId")
    @DynamoDBAttribute(attributeName = "travelId")
    public String getTravelId() {
        return _travelId;
    }

    public void setTravelId(final String _travelId) {
        this._travelId = _travelId;
    }
    @DynamoDBIndexRangeKey(attributeName = "endTime", globalSecondaryIndexName = "endTimeIndex")
    public Double getEndTime() {
        return _endTime;
    }

    public void setEndTime(final Double _endTime) {
        this._endTime = _endTime;
    }
    @DynamoDBIndexRangeKey(attributeName = "lat", globalSecondaryIndexName = "latIndex")
    public Double getLat() {
        return _lat;
    }

    public void setLat(final Double _lat) {
        this._lat = _lat;
    }
    @DynamoDBIndexRangeKey(attributeName = "lon", globalSecondaryIndexName = "lonIndex")
    public Double getLon() {
        return _lon;
    }

    public void setLon(final Double _lon) {
        this._lon = _lon;
    }
    @DynamoDBIndexRangeKey(attributeName = "startTime", globalSecondaryIndexName = "startTimeIndex")
    public Double getStartTime() {
        return _startTime;
    }

    public void setStartTime(final Double _startTime) {
        this._startTime = _startTime;
    }
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexNames = {"startTimeIndex","latIndex","lonIndex","endTimeIndex",})
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
