package com.example.agathe.tsgtest;

/**
 * Created by agathe on 22/11/16.
 */

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.example.agathe.tsgtest.AWS.AWSMobileClient;

/**
 * Application class responsible for initializing singletons and other common components.
 */
public class AWSApplication extends MultiDexApplication {

    private final static String LOG_TAG = Application.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        initializeApplication();
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");
    }


    private void initializeApplication() {

        // Initialize the AWS Mobile Client
        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());

        // ... Put any application-specific initialization logic here ...
    }
}

