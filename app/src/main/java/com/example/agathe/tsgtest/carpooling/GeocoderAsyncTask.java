package com.example.agathe.tsgtest.carpooling;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by agathe on 23/12/16.
 */

public class GeocoderAsyncTask extends AsyncTask<String, Void, LatLng> {

    public String address = "";

    public GeocoderAsyncTask(String address) {
        this.address = address;
    }

    @Override
    protected LatLng doInBackground(String... params) {
        String response;
        try {
            String encodedAddress = URLEncoder.encode(address, "utf-8");
            response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address=" + encodedAddress + "&sensor=false");
            Log.d("response", "" + response);

            JSONObject jsonObject = new JSONObject(response);

            double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            LatLng myLatLng = new LatLng (lat, lng);

            return myLatLng;
        } catch (Exception e) {
            return new LatLng(0,0);
        }
    }

    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}