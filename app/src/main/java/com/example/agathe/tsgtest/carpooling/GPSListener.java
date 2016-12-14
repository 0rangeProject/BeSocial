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


/**
 * Created by agathe on 12/12/16.
 */

public class GPSListener implements LocationListener {

    private static final double RAYON = 0.5;
    private static final String TAG = "GPSListener";
    private Activity activity;
    private Location previousLoc;
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

            if (previousLoc != null) {
                Log.i(TAG, "previousloc lat : " + String.valueOf(previousLoc.getLatitude()));
                Log.i(TAG, "previousloc lon : " + String.valueOf(previousLoc.getLongitude()));
                Log.i(TAG, "loc lat : " + String.valueOf(loc.getLatitude()));
                Log.i(TAG, "loc lon : " + String.valueOf(loc.getLongitude()));

                if (previousLoc.getLatitude() - loc.getLatitude() < RAYON || previousLoc.getLongitude() - loc.getLongitude() < RAYON) {
                    Log.i(TAG, "Match");
                    PathsDO path = new PathsDO();
                    path.setUserId(userID);
                    path.setPathId(userID + loc.getLatitude());
                    path.setStartTime(String.valueOf(previousLoc.getTime()));
                    path.setEndTime(String.valueOf(loc.getTime()));
                    path.setLat(loc.getLatitude());
                    path.setLon(loc.getLongitude());
                    new SaveObjectTask().execute(path);
                }
            }
        } else
            message = "Location null";

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        
        previousLoc = loc;
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