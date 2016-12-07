package com.amazonaws.mobile.user.signin;
//
// Copyright 2016 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.11
//

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.view.View;

import com.amazonaws.mobile.AWSConfiguration;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobile.util.ThreadUtils;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;

/**
 * Sign in Provider for Google.
 */
public class GoogleSignInProvider implements SignInProvider {
    /** Log tag. */
    private static final String LOG_TAG = GoogleSignInProvider.class.getSimpleName();

    /** The Cognito login key for Google+ to be used in the Cognito login Map. */
    public static final String COGNITO_LOGIN_KEY_GOOGLE = "accounts.google.com";

    // Arbitrary activity request ID. You can handle this in the main activity,
    // if you want to take action when a google services result is received.
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1363;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 900913;

    /** Client used to interact with Google APIs. */
    private final GoogleApiClient mGoogleApiClient;

    /** Android context. */
    private Context context;

    /** Flag indicating Google is handling an intent to connect (sign-in). */
    private boolean mIntentInProgress = false;

    /** The sign-in results adapter from the SignInManager. */
    private IdentityManager.SignInResultsHandler resultsHandler;

    /** The sign-in activity. */
    private Activity signInActivity = null;

    /** The auth token retrieved when signed-in.  It is good for 6-months from the last service
     * call. */
    private volatile String authToken = null;

    /** User's name. */
    private String userName;

    /** User's image Url. */
    private String userImageUrl;

    /**
     * Constuctor. Builds the Google Api Client.
     * @param context context.
     */
    public GoogleSignInProvider(final Context context) {
        this.context = context;

        clearUserInfo();

        Log.d(LOG_TAG, "Initializing Google SDK...");

        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
        mGoogleApiClient.connect();
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return "Google";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isUserSignedIn() {
        final ConnectionResult result = mGoogleApiClient.blockingConnect();
        if (result.isSuccess()) {
            try {
                authToken = getGoogleAuthToken();
                return true;
            } catch (Exception e) {
                Log.w(LOG_TAG, "Failed to update Google token", e);
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String getCognitoLoginKey() {
        return COGNITO_LOGIN_KEY_GOOGLE;
    }

    /** {@inheritDoc} */
    @Override
    public String getToken() {
        return authToken;
    }

    @Override
    public String refreshToken() {
        Log.d(LOG_TAG, "Google provider refreshing token...");

        try {
            authToken = getGoogleAuthToken();
        } catch (Exception e) {
            Log.w(LOG_TAG, "Failed to update Google token", e);
            authToken = null;
        }
        return authToken;
    }

    /**
     * Initiate sign-in with Google.
     */
    private void signIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ConnectionResult result = mGoogleApiClient.blockingConnect();
                if (!result.isSuccess()) {
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onConnectionFailed(result);
                        }
                    });
                    return;
                }

                try {
                    authToken = getGoogleAuthToken();
                    Log.d(LOG_TAG, "Google provider sign-in succeeded!");

                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultsHandler.onSuccess(GoogleSignInProvider.this);
                        }
                    });
                } catch (final Exception e) {
                    Log.e(LOG_TAG, "Error retrieving ID token.", e);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultsHandler.onError(GoogleSignInProvider.this, e);
                        }
                    });
                }
            }
        }).start();
    }

    private String getGoogleAuthToken() throws GoogleAuthException, IOException {
        Log.d(LOG_TAG, "Google provider getting token...");

        final String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
        final Account googleAccount = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        final String scopes = "audience:server:client_id:" + AWSConfiguration.GOOGLE_CLIENT_ID;
        final String token = GoogleAuthUtil.getToken(context, googleAccount, scopes);

        if (token != null) {
            Log.d(LOG_TAG, "Google Token is OK. Token hashcode = " + token.hashCode());
        } else {
            Log.d(LOG_TAG, "Google Token is NULL.");
        }

        return token;
    }

    /** {@inheritDoc} */
    @Override
    public void signOut() {
        Log.d(LOG_TAG, "Google provider signing out...");

        clearUserInfo();

        authToken = null;
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isRequestCodeOurs(final int requestCode) {
        return (requestCode == RC_SIGN_IN);
    }

    /** {@inheritDoc} */
    @Override
    public void handleActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            // if the user canceled
            if (resultCode == 0) {
                resultsHandler.onCancel(GoogleSignInProvider.this);
                clearUserInfo();
                return;
            }
            signIn();
        }
    }

    /** {@inheritDoc} */
    @Override
    public View.OnClickListener initializeSignInButton(final Activity signInActivity, final View buttonView,
                                                       final IdentityManager.SignInResultsHandler resultsHandler) {
        this.signInActivity = signInActivity;
        this.resultsHandler = resultsHandler;
        final GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        final int code = api.isGooglePlayServicesAvailable(context.getApplicationContext());

        if (ConnectionResult.SUCCESS != code) {
            if(api.isUserResolvableError(code)) {
                Log.w(LOG_TAG, "Google Play services recoverable error.");
                api.showErrorDialogFragment(signInActivity, code, REQUEST_GOOGLE_PLAY_SERVICES);
            } else {
                final boolean isDebugBuild =
                        (0 != (signInActivity
                                .getApplicationContext()
                                .getApplicationInfo()
                                .flags & ApplicationInfo.FLAG_DEBUGGABLE));

                if (!isDebugBuild) {
                    buttonView.setVisibility(View.GONE);
                } else {
                    Log.w(LOG_TAG, "Google Play Services are not available, but we are showing the Google Sign-in Button, anyway, because this is a debug build.");
                }
            }
            return null;
        }

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        };
        buttonView.setOnClickListener(listener);
        return listener;
    }

    /**
     * Handler method for connection to Google failed.
     * @param result Contains all possible error codes for when a client fails to connect to
     *               Google Play services.
     */
    public void onConnectionFailed(final ConnectionResult result) {
        if (!mIntentInProgress) {
            if (result.hasResolution()) {
                try {
                    mIntentInProgress = true;
                    result.startResolutionForResult(signInActivity, RC_SIGN_IN);
                } catch (IntentSender.SendIntentException ex) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            } else {
                resultsHandler.onError(GoogleSignInProvider.this,
                        new IllegalStateException(result.toString()));
            }
        } else {
            Log.w(LOG_TAG, "onConnectionFailed while Google sign-in intent is already in progress.");
        }
    }

    private void clearUserInfo() {
        userName = null;
        userImageUrl = null;
    }

    /** {@inheritDoc} */
    @Override
    public String getUserName() {
        return userName;
    }

    /** {@inheritDoc} */
    @Override
    public String getUserImageUrl() {
        return userImageUrl;
    }

    /** {@inheritDoc} */
    public void reloadUserInfo() {
        mGoogleApiClient.blockingConnect();
        Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (person != null) {
            userName = person.getDisplayName();
            userImageUrl = person.getImage().getUrl();
        }
    }
}