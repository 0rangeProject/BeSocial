package com.example.agathe.tsgtest;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by agathe on 30/01/17.
 */

public class TokensTask extends AsyncTask<Void,Void,String[]> {

    private static final String LOG_TAG = "log";
    private Context context2;
    private String code2;

    public TokensTask(Context context, String code) {
        context2 = context;
        code2 = code;
    }

    protected void onPreExecute() {

    }

    protected String[] doInBackground(Void... params) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context2);
        String url ="orange2:orange@217.96.70.94/dsn-smp/oauth/token -d grant_type=authorization_code -d client_id=orange2 -d redirect_uri=http://orange -d code=" + code2;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(LOG_TAG, "Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(LOG_TAG, "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return null;
    }

    protected void onPostExecute(Void result) {
        // dismiss progress dialog and update ui
    }
}