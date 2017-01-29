package com.example.agathe.tsgtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.example.agathe.tsgtest.carpooling.EntriesVisualisationActivity;
import com.example.agathe.tsgtest.carpooling.GeolocationService;
import com.example.agathe.tsgtest.carpooling.HomeCarpoolingActivity;

/**
 * Created by agathe on 29/01/17.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static final String KEY_PREF_DETECTION_ENABLED = "pref_detection_enabled";
    public static final String KEY_PREF_MANUAL_ENTRIES = "pref_manual_entries";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference manualEntries = (Preference) findPreference(KEY_PREF_MANUAL_ENTRIES);

        manualEntries.setOnPreferenceClickListener(this);

        CheckBoxPreference checkboxPref = (CheckBoxPreference)getPreferenceManager().findPreference(KEY_PREF_DETECTION_ENABLED);
        checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object newValue) {

                boolean myValue = (Boolean) newValue;

                if (myValue) {
                    getActivity().startService(new Intent(getActivity(), GeolocationService.class));
                    Log.i("App", "Service ok");
                }
                else {
                    getActivity().stopService(new Intent(getActivity(), GeolocationService.class));
                    Log.i("App", "Service down");
                }
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

}
