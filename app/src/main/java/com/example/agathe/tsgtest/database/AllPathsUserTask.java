
package com.example.agathe.tsgtest.database;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList;
import com.amazonaws.models.nosql.PathsDO;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.example.agathe.tsgtest.dto.CommonTravel;
import com.example.agathe.tsgtest.dto.Place;
import com.example.agathe.tsgtest.dto.User;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by agathe on 30/11/16.
 */

public class AllPathsUserTask extends AsyncTask<String, Void, ArrayList<CommonTravel>> {

    private static String LOG_TAG = "LoadObjectTask";
    private String type = "";
    private String clientId = "";
    private Context context = null;

    // Fetch the default configured DynamoDB ObjectMapper
    final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

    @Override
    protected ArrayList<CommonTravel> doInBackground(String... strings) {
        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:

        PaginatedQueryList<PathsDO> resultFinalLon = null, resultFinalLat = null, resultFinalStart = null, resultFinalEnd = null;
        List<PathsDO> resultFinal = new ArrayList<>();

        PathsDO path = new PathsDO();
        path.setUserId(clientId);

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(path)
                .withConsistentRead(false);

        PaginatedQueryList<PathsDO> resultsForCurrentUser = mapper.query(PathsDO.class, queryExpression);

        List<Place> places = null;

        // liste de mes contacts
        List<User> contacts = new ArrayList<>();

        ArrayList<CommonTravel> travels = new ArrayList<>();

        // Pour chaque trajet, on regarde ceux qui ont une longitude / latitude / startTime / endTime assez proche, ici pour le même utilisateur, ce qui n'a pas de sens
        if (resultsForCurrentUser.size() != 0) {
            for (PathsDO p : resultsForCurrentUser) {
                List<User> users = new ArrayList<>();

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

                // pour chacun de ses contacts
                if (contacts != null) {
                    for (User u : contacts) {
                        PathsDO pathOtherUser = new PathsDO();
                        pathOtherUser.setUserId(u.name);

                        DynamoDBQueryExpression queryExpressionLat = new DynamoDBQueryExpression()
                                .withHashKeyValues(pathOtherUser)
                                .withRangeKeyCondition("lat", latCondition)
                                .withConsistentRead(false);

                        DynamoDBQueryExpression queryExpressionLon = new DynamoDBQueryExpression()
                                .withHashKeyValues(pathOtherUser)
                                .withRangeKeyCondition("lon", lonCondition)
                                .withConsistentRead(false);

                        DynamoDBQueryExpression queryExpressionStart = new DynamoDBQueryExpression()
                                .withHashKeyValues(pathOtherUser)
                                .withRangeKeyCondition("startTime", startTimeCondition)
                                .withConsistentRead(false);

                        DynamoDBQueryExpression queryExpressionEnd = new DynamoDBQueryExpression()
                                .withHashKeyValues(pathOtherUser)
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
                                            // ajouter l'utilisateur dans une liste qu'on ajoutera à la fin au trajet
                                            users.add(u);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (users.size() != 0) {
                        Place place = new Place(path.getLon(), path.getEndTime(), path.getLat(), path.getStartTime(), users);
                        places.add(place);
                    }
                }
            }

            if (contacts != null) {
                // A la fin, on a une liste de lieux en commun. Il faut distinguer des trajets ("CommonTravel")
                for (User u : contacts) {
                    Place firstPlace = null;
                    // trouver les places en commun
                    for (Place p : places) {
                        if (p.getUsers().contains(u) && firstPlace.equals(null)) {
                            firstPlace = p;
                        }
                        if (p.getUsers().contains(u) && !firstPlace.equals(null)) {
                            List<User> users = new ArrayList<>();
                            users.add(u);

                            Geocoder geocoder;
                            List<Address> addresses1 = null, addresses2 = null;
                            geocoder = new Geocoder(context, Locale.getDefault());

                            try {
                                addresses1 = geocoder.getFromLocation(firstPlace.getLat(), firstPlace.getLon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                addresses2 = geocoder.getFromLocation(firstPlace.getLat(), firstPlace.getLon(), 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String address1 = addresses1.get(0).getAddressLine(0);
                            String city1 = addresses1.get(0).getLocality();
                            String postalCode1 = addresses1.get(0).getPostalCode();

                            String address2 = addresses2.get(0).getAddressLine(0);
                            String city2 = addresses2.get(0).getLocality();
                            String postalCode2 = addresses2.get(0).getPostalCode();

                            CommonTravel ct = new CommonTravel(address1 + city1 + postalCode1, address2 + city2 + postalCode2, new LatLng(firstPlace.getLat(), firstPlace.getLon()), new LatLng(p.getLat(), p.getLon()), users);
                            travels.add(ct);
                        }
                    }
                }
            }

        }
        return travels;
    }

    public static double delta(double d1, double d2) {
        return Math.abs(d1- d2) / Math.max(Math.abs(d1), Math.abs(d2));
    }

    public AllPathsUserTask(String type2, String clientId2, Context ct) {
        type = type2;
        clientId = clientId2;
        context = ct;
    }
}