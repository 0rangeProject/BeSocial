package com.example.agathe.tsgtest.database;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.models.nosql.ManualTripsDO;
import com.amazonaws.models.nosql.PathsDO;

/**
 * Created by agathe on 30/11/16.
 */

public class LoadObjectTask extends AsyncTask<String, Void, String> {

    private static final String LOG_TAG = "LoadObjectTask";
    private static final String type = "";

    // Fetch the default configured DynamoDB ObjectMapper
    final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

    @Override
    protected String doInBackground(String... strings) {
        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:
        // AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID()
        if (type == "path") {
            PathsDO path = mapper.load(PathsDO.class, "demo-noteId-500000");
            try {
                mapper.save(path);
            } catch (final AmazonClientException ex) {
                Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            }
        }
        if (type == "manualTrip") {
            ManualTripsDO trip = mapper.load(ManualTripsDO.class, "demo-noteId-500000");
            try {
                mapper.save(trip);
            } catch (final AmazonClientException ex) {
                Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            }
        }

        return "Executed";
    }

    public LoadObjectTask(String type) {
        type = type;
    }
}
