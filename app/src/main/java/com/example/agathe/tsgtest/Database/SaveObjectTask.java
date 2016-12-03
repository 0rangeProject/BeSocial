package com.example.agathe.tsgtest.Database;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.models.nosql.PathsDO;

/**
 * Created by agathe on 30/11/16.
 */

public class SaveObjectTask extends AsyncTask<PathsDO, Void, String> {

    private static final String LOG_TAG = "SaveObjectTask";

    // Fetch the default configured DynamoDB ObjectMapper
    final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

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

