package com.example.agathe.tsgtest.database;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.models.nosql.PathsDO;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by agathe on 30/11/16.
 */



public class GetAllItemsTask extends AsyncTask<Void, Void, ArrayList<PathsDO>> {

    private static final String LOG_TAG = "GetAllItemstTask";
    private ArrayList<PathsDO> tmpList = null;
    private ArrayList<PathsDO> paths = null;

    // Fetch the default configured DynamoDB ObjectMapper
    final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

    @Override
    protected ArrayList<PathsDO> doInBackground(Void... params) {
        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:
        // AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID()
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<PathsDO> result = mapper.scan(PathsDO.class, scanExpression);

        tmpList = new ArrayList<>(result.size());
        Iterator<PathsDO> iterator = result.iterator();
        while (iterator.hasNext()) {
            PathsDO path = iterator.next();
            tmpList.add(path);
            Log.i(LOG_TAG, Integer.toString(tmpList.size()));
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PathsDO> paths) {
        super.onPostExecute(paths);
        Log.i(LOG_TAG, "passage onPostExecute");
        paths = tmpList;
    }

    public ArrayList<PathsDO> getPaths(){
        Log.i(LOG_TAG, "passage getPaths");
        return paths;
    }
}