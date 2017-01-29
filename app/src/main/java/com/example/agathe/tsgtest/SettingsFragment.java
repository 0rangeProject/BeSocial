package com.example.agathe.tsgtest;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.push.PushManager;
import com.example.agathe.tsgtest.carpooling.EntriesVisualisationActivity;
import com.example.agathe.tsgtest.carpooling.GeolocationService;

/**
 * Created by agathe on 29/01/17.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static final String KEY_PREF_PUSH_NOTIFICATIONS = "pref_push_notifications";
    public static final String KEY_PREF_DETECTION_ENABLED = "pref_detection_enabled";
    public static final String KEY_PREF_MANUAL_ENTRIES = "pref_manual_entries";

    private PushManager pushManager;
    private static final String LOG_TAG = "SettingsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        pushManager = AWSMobileClient.defaultMobileClient().getPushManager();

        Preference manualEntries = (Preference) findPreference(KEY_PREF_MANUAL_ENTRIES);

        manualEntries.setOnPreferenceClickListener(this);

        CheckBoxPreference detectionCB = (CheckBoxPreference)getPreferenceManager().findPreference(KEY_PREF_DETECTION_ENABLED);
        detectionCB.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object newValue) {

                boolean myValue = (Boolean) newValue;

                if (myValue) {
                    getActivity().startService(new Intent(getActivity(), GeolocationService.class));
                }
                else {
                    getActivity().stopService(new Intent(getActivity(), GeolocationService.class));
                }
                return true;
            }
        });

        CheckBoxPreference notificationsCB = (CheckBoxPreference)getPreferenceManager().findPreference(KEY_PREF_PUSH_NOTIFICATIONS);
        notificationsCB.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object newValue) {

                boolean myValue = (Boolean) newValue;
                toggleNotification(myValue);
                return true;
            }
        });
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(KEY_PREF_MANUAL_ENTRIES)) {
            Intent intent = new Intent(getActivity(), EntriesVisualisationActivity.class);
            startActivity(intent);
            Log.i("App", "Manual OK");
        }
        return false;
    }

    private void toggleNotification(final boolean enabled) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {
                // register device first to ensure we have a push endpoint.
                pushManager.registerDevice();

                // if registration succeeded.
                if (pushManager.isRegistered()) {
                    try {
                        pushManager.setPushEnabled(enabled);
                        // Automatically subscribe to the default SNS topic
                        if (enabled) {
                            pushManager.subscribeToTopic(pushManager.getDefaultTopic());
                        }
                        return null;
                    } catch (final AmazonClientException ace) {
                        Log.e(LOG_TAG, "Failed to change push notification status", ace);
                        return ace.getMessage();
                    }
                }
                return "Failed to register for push notifications.";
            }

            @Override
            protected void onPostExecute(final String errorMessage) {
                //enablePushCheckBox.setChecked(pushManager.isPushEnabled());
                System.out.println((errorMessage == null) ? "Register succeed......" : "Fail to register......" + errorMessage);
                if (errorMessage != null) {
                    showErrorMessage(R.string.error_message_update_notification, errorMessage);
                }
            }
        }.execute();
    }

    private AlertDialog showErrorMessage(final int resId, final Object... args) {
        return new AlertDialog.Builder(getActivity()).setMessage(getString(resId, (Object[]) args))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
