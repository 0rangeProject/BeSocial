
package com.example.agathe.tsgtest.database;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList;
import com.amazonaws.models.nosql.PathsDO;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 30/11/16.
 */

public class AllPathsUserTask extends AsyncTask<String, Void, List<List<PathsDO>>> {

    private static String LOG_TAG = "LoadObjectTask";
    private String type = "";
    private String clientId = "";

    // Fetch the default configured DynamoDB ObjectMapper
    final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

    @Override
    protected List<List<PathsDO>> doInBackground(String... strings) {
        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:

        PaginatedQueryList<PathsDO> resultUsers = null;
        PaginatedQueryList<PathsDO> resultFinalLon = null;
        PaginatedQueryList<PathsDO> resultFinalLat = null;
        PaginatedQueryList<PathsDO> resultFinalStart = null;
        PaginatedQueryList<PathsDO> resultFinalEnd = null;
        List<PathsDO> resultFinal = new ArrayList<>();

        PathsDO path = new PathsDO();
        path.setUserId(clientId);

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(path)
                .withConsistentRead(false);

        resultUsers = mapper.query(PathsDO.class, queryExpression);

        List<List<PathsDO>> list = null;

        // Pour chaque trajet, on regarde ceux qui ont une longitude / latitude / startTime / endTime assez proche, ici pour le même utilisateur, ce qui n'a pas de sens
        for (PathsDO p : resultUsers) {

            Condition lonCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BETWEEN)
                    .withAttributeValueList(new AttributeValue().withN(String.valueOf((p.getLon() - 0.5))), new AttributeValue().withN(String.valueOf((p.getLon() + 0.5))));

            Condition latCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BETWEEN)
                    .withAttributeValueList(new AttributeValue().withN(String.valueOf((p.getLat() - 0.5))), new AttributeValue().withN(String.valueOf((p.getLat() + 0.5))));

            Condition startTimeCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BETWEEN)
                    .withAttributeValueList(new AttributeValue().withN(String.valueOf((p.getStartTime() - 0.5))), new AttributeValue().withN(String.valueOf((p.getEndTime() + 0.5))));

            Condition endTimeCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BETWEEN)
                    .withAttributeValueList(new AttributeValue().withN(String.valueOf((p.getStartTime() - 0.5))), new AttributeValue().withN(String.valueOf((p.getEndTime() + 0.5))));

            DynamoDBQueryExpression queryExpressionLat = new DynamoDBQueryExpression()
                    .withHashKeyValues(path)
                    .withRangeKeyCondition("lat", latCondition)
                    .withConsistentRead(false);

            DynamoDBQueryExpression queryExpressionLon = new DynamoDBQueryExpression()
                    .withHashKeyValues(path)
                    .withRangeKeyCondition("lon", lonCondition)
                    .withConsistentRead(false);

            DynamoDBQueryExpression queryExpressionStart = new DynamoDBQueryExpression()
                    .withHashKeyValues(path)
                    .withRangeKeyCondition("startTime", startTimeCondition)
                    .withConsistentRead(false);

            DynamoDBQueryExpression queryExpressionEnd = new DynamoDBQueryExpression()
                    .withHashKeyValues(path)
                    .withRangeKeyCondition("endTime", endTimeCondition)
                    .withConsistentRead(false);

            resultFinalLat = mapper.query(PathsDO.class, queryExpressionLat);
            resultFinalLon = mapper.query(PathsDO.class, queryExpressionLon);
            resultFinalStart = mapper.query(PathsDO.class, queryExpressionStart);
            resultFinalEnd = mapper.query(PathsDO.class, queryExpressionEnd);

            // Ne garde que les éléments communs aux différents paramètres
            for (PathsDO pathLat : resultFinalLat) {
                for (PathsDO pathLon : resultFinalLon) {
                    for (PathsDO pathStart : resultFinalStart) {
                        for (PathsDO pathEnd : resultFinalEnd) {
                            if (delta(pathLat.getLat(), pathLon.getLat()) == 0 && delta(pathLat.getLat(), pathStart.getLat()) == 0 && delta(pathLat.getLat(), pathEnd.getLat()) == 0) {
                                PathsDO newPath = new PathsDO();
                                path.setUserId(pathLat.getUserId());
                                path.setEndTime(pathLat.getEndTime());
                                path.setStartTime(pathLat.getStartTime());
                                path.setLat(pathLat.getLat());
                                path.setLon(pathLat.getLon());

                                resultFinal.add(newPath);
                                break;
                            }

                            if (pathLat.getLat() == pathLon.getLat() && pathLat.getLon() == pathLon.getLon()) {
                                resultFinal.add(pathLat);
                            }
                        }
                    }
                }
            }

            list.add(resultFinal);
        }
        return list;
    }

    public static double delta(double d1, double d2) {
        return Math.abs(d1- d2) / Math.max(Math.abs(d1), Math.abs(d2));
    }

    public AllPathsUserTask(String type2, String clientId2) {
        type = type2;
        clientId = clientId2;
    }
}