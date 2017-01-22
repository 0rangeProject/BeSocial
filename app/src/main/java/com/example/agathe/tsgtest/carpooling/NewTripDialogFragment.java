package com.example.agathe.tsgtest.carpooling;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TimePicker;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.ManualTripsDO;
import com.example.agathe.tsgtest.ComplexPreferences;
import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.database.SaveObjectTaskManualTrip;
import com.example.agathe.tsgtest.dto.ManualTrip;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by agathe on 19/12/16.
 */

public class NewTripDialogFragment extends DialogFragment {

    private static final String TAG = "NewTripDialogFragment";
    private Button departureHourButton, destinationHourButton;
    private AutoCompleteTextView autoCompView, autoCompView2;
    private int startTime, endTime;
    private String userID;

    private SharedPreferences settings = null;
    private DynamoDBMapper mapper = null;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyB6eDuGk--vAvurvSEPQjebejhywHFUNE4";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.dialog_layout, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_trip);

        mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

        settings = getContext().getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        userID = settings.getString("userID", "");

        departureHourButton = (Button) rootView.findViewById(R.id.btn_departure_hour);
        departureHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(rootView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime = selectedHour;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time for Departure");
                mTimePicker.show();
            }
        });

        destinationHourButton = (Button) rootView.findViewById(R.id.btn_destination_hour);
        destinationHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(rootView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime = selectedHour;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time for Destination");
                mTimePicker.show();
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        autoCompView = (AutoCompleteTextView) rootView.findViewById(R.id.departure_place);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(getContext(), R.layout.list_item));
        autoCompView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        autoCompView2 = (AutoCompleteTextView) rootView.findViewById(R.id.destination_place);
        autoCompView2.setAdapter(new GooglePlacesAutocompleteAdapter(getContext(), R.layout.list_item));
        autoCompView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_ak, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // envoyer toutes les infos si c'est rempli dans la BDD et dans les préférences
            if (startTime != 0 && endTime != 0 && autoCompView != null && autoCompView2 != null) {
                ManualTripsDO trip = new ManualTripsDO();
                trip.setUserId(userID);
                trip.setPathId(userID + autoCompView);
                trip.setStartTime(startTime);
                trip.setEndTime(endTime);
                trip.setDeparture(autoCompView.getText().toString());
                trip.setDestination(autoCompView2.getText().toString());
                new SaveObjectTaskManualTrip(mapper).execute(trip);

                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getContext(), "PREFERENCES_FILE", MODE_PRIVATE);
                ListTravels complexObject = complexPreferences.getObject("list", ListTravels.class);

                ListTravels list = new ListTravels();
                List<ManualTrip> manualTrips = new ArrayList<>();

                if (complexObject != null) {
                    manualTrips = complexObject.getTravels();
                }

                LatLng responseDeparture = null;
                LatLng responsesDestination = null;
                try {
                    responseDeparture = new GeocoderAsyncTask(autoCompView.getText().toString()).execute().get();
                    responsesDestination = new GeocoderAsyncTask(autoCompView2.getText().toString()).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (responseDeparture.latitude != 0 && responsesDestination.latitude != 0) {
                    manualTrips.add(new ManualTrip(autoCompView.getText().toString(), autoCompView2.getText().toString(), responseDeparture, responsesDestination));
                } else manualTrips.add(new ManualTrip(autoCompView.getText().toString(), autoCompView2.getText().toString()));

                list.setTravels(manualTrips);
                complexPreferences.putObject("list", list);
                complexPreferences.commit();
            }

            dismiss();
            return true;
        } else if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:fr");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList<String> resultList ;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected Filter.FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
}