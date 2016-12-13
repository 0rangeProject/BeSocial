package com.example.agathe.tsgtest.carpooling;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.amazonaws.models.nosql.PathsDO;
import com.example.agathe.tsgtest.database.SaveObjectTask;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;


/**
 * Created by agathe on 12/12/16.
 */

public class GPSListener implements LocationListener {

    private static final double RAYON = 0.5;
    private static final String TAG = "GPSListener";
    private Activity activity;
    private Location initLoc = null;
    private String startTime = "";
    private String endTime = "";
    private String userID;

    public GPSListener(Activity activity, String userID) {
        this.activity = activity;
        this.userID = userID;
    }

    @Override
    public void onLocationChanged(Location loc) {

        Log.w("LAT", String.valueOf(loc.getLatitude()));
        Log.w("LONG", String.valueOf(loc.getLongitude()));

        String message;

        if (loc != null) {
             message = "Current location is:  Latitude = "
                    + loc.getLatitude() + ", Longitude = "
                    + loc.getLongitude();

            if (initLoc != null) {

                // Si l'utilisateur n'a pas bougé (ou presque pas)
                if (abs(loc.getLongitude() - initLoc.getLongitude()) < RAYON && abs(loc.getLatitude() - initLoc.getLatitude()) < RAYON) {
                    endTime = String.valueOf(loc.getTime());
                } else {
                    if (!endTime.equals("")) {
                        PathsDO path = new PathsDO();
                        path.setUserId(userID);
                        path.setPathId(userID + loc.getLatitude());
                        path.setStartTime(startTime);
                        path.setEndTime(endTime);
                        path.setLat(initLoc.getLatitude());
                        path.setLon(initLoc.getLongitude());
                        new SaveObjectTask().execute(path);
                    }
                    initLoc = loc; // on change d'endroit, donc de position initiale
                    startTime = String.valueOf(loc.getTime());
                    endTime = "";
                }
            } else {
                initLoc = loc;
                startTime = String.valueOf(loc.getTime());
            }
        } else
            message = "Location null";
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(activity, "Gps Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(activity, "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}