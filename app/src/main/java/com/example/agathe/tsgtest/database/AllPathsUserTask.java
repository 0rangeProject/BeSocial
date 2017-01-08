
package com.example.agathe.tsgtest.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList;
import com.amazonaws.models.nosql.ManualTripsDO;
import com.amazonaws.models.nosql.PathsDO;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.example.agathe.tsgtest.carpooling.PurposeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 30/11/16.
 */

public class AllPathsUserTask extends AsyncTask<String, Void, List<PaginatedQueryList<PathsDO>>> {

    private static String LOG_TAG = "LoadObjectTask";
    private String type = "";
    private String clientId = "";

    // Fetch the default configured DynamoDB ObjectMapper
    final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

    @Override
    protected List<PaginatedQueryList<PathsDO>> doInBackground(String... strings) {
        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:

        PaginatedQueryList<PathsDO> resultUsers = null;
        PaginatedQueryList<PathsDO> resultFinal = null;

        PathsDO path = new PathsDO();
        path.setUserId(clientId);

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(path)
                .withConsistentRead(false);

        resultUsers = mapper.query(PathsDO.class, queryExpression);

        List<PaginatedQueryList<PathsDO>> list = null;
        list.add(resultUsers);

        /*
        // Pour chaque trajet, on regarde ceux à une latitude / longitude dans un rayon de 1
        for (PathsDO p : resultUsers) {
            Condition rangeKeyCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BETWEEN)
                    .withAttributeValueList(new AttributeValue().withN(String.valueOf((p.getLat() - 0.5))), new AttributeValue().withN(String.valueOf((p.getLat() + 0.5))));

            queryExpression = new DynamoDBQueryExpression()
                    .withHashKeyValues(path)
                    //.withRangeKeyCondition("lat", rangeKeyCondition)
                    .withConsistentRead(false);

            resultFinal = mapper.query(PathsDO.class, queryExpression);
            list.add(resultFinal);
        }*/
        return list;
    }

    public AllPathsUserTask(String type2, String clientId2) {
        type = type2;
        clientId = clientId2;
    }
}