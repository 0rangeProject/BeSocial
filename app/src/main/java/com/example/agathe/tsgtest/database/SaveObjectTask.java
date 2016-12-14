package com.example.agathe.tsgtest.database;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.PathsDO;

/**
 * Created by agathe on 30/11/16.
 */

public class SaveObjectTask extends AsyncTask<PathsDO, Void, String> {

    private static final String LOG_TAG = "SaveObjectTask";
    private final DynamoDBMapper mapper;

    public SaveObjectTask(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected String doInBackground(PathsDO... pathsDOs) {
        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:
        // AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID()
        PathsDO path = pathsDOs[0];

        try {
            mapper.save(path);
        } catch (final AmazonClientException ex) {
            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
        }

        return "Executed";
    }
}

