package com.example.agathe.tsgtest.carpooling;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.PathsDO;
import com.example.agathe.tsgtest.database.SaveObjectTask;

import static java.lang.Math.abs;

/**
 * Created by agathe on 14/12/16.
 */

public class GeolocationService extends Service {
    private static final double RAYON = 0.1;
    private static final String TAG = "GeolocationService";
    private Location initLoc = null;
    private String startTime = "";
    private String endTime = "";
    private String userID;

    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 30 * 60 * 1000;
    private static final float LOCATION_DISTANCE = 1;
    final DynamoDBMapper mapper = null;

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;
        DynamoDBMapper mapper;

        public LocationListener(String provider, DynamoDBMapper mapper) {
            Log.i(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            this.mapper = mapper;
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.w("LAT", String.valueOf(location.getLatitude()));
            Log.w("LONG", String.valueOf(location.getLongitude()));

            mLastLocation.set(location);
            if (initLoc != null) {
                // Si l'utilisateur n'a pas bougé (ou presque pas)
                if (abs(location.getLongitude() - initLoc.getLongitude()) < RAYON && abs(location.getLatitude() - initLoc.getLatitude()) < RAYON) {
                    endTime = String.valueOf(location.getTime());
                } else {
                    if (!endTime.equals("")) {
                        PathsDO path = new PathsDO();
                        path.setUserId(userID);
                        path.setPathId(userID + location.getLatitude());
                        path.setStartTime(startTime);
                        path.setEndTime(endTime);
                        path.setLat(initLoc.getLatitude());
                        path.setLon(initLoc.getLongitude());
                        new SaveObjectTask(mapper).execute(path);
                    }
                    initLoc = location; // on change d'endroit, donc de position initiale
                    startTime = String.valueOf(location.getTime());
                    endTime = "";
                }
            } else {
                initLoc = location;
                startTime = String.valueOf(location.getTime());
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{};

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userID = intent.getStringExtra("userID");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        initializeLocationManager();
        AWSMobileClient.initializeMobileClientIfNecessary(this);
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

        mLocationListeners = new LocationListener[]{
                new LocationListener(LocationManager.GPS_PROVIDER, mapper),
                new LocationListener(LocationManager.NETWORK_PROVIDER, mapper)
        };

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
            }
        }
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);

    }

    private void initializeLocationManager() {
        Log.i(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}