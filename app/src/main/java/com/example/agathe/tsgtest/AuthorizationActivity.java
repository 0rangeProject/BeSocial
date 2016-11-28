package com.example.agathe.tsgtest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agathe.tsgtest.Dto.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 02/11/16.
 */

public class AuthorizationActivity extends Activity {

    private static final String CLIENT_ID = "H63B_cPr8kVvSm8NFiQ8p6NwdGD6NYo1";

    private static final String CLIENT_SECRET = "7Is1qx1ty23ylRegAT3n1Adm3WcJGi1n2iFse87fNV3sbb6RH5Uefdn1Lwm5UOCG";

    private static final String REDIRECT_URI = "https://moves-api-demo.herokuapp.com/auth/moves/callback";

    private static final int REQUEST_AUTHORIZE = 1;

    private String access_token = "";
    private String refresh_token = "";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        settings = getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        editor = settings.edit();

        findViewById(R.id.authorizeInApp).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doRequestAuthInApp();
            }
        });

        // On récupère le jeton et on vérifie sa validité
        access_token =  settings.getString("access_token", "");
        refresh_token =  settings.getString("refresh_token", "");
    }

    private void doRequestAuthInApp() {
        Uri uri = createAuthUri("moves", "app", "/authorize").build();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivityForResult(intent, REQUEST_AUTHORIZE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Moves app not installed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_AUTHORIZE:
                getTokens(new VolleyCallback() {
                    @Override
                    public void onSuccess(List<String> result) {
                        Log.i("TAG", result.get(0));
                        Log.i("TAG", result.get(1));
                        finish();
                    }
                }, data);
        }
    }

    /**
     * Helper method for building a valid Moves authorize uri.
     */
    private Uri.Builder createAuthUri(String scheme, String authority, String path) {
        return new Uri.Builder()
                .scheme(scheme)
                .authority(authority)
                .path(path)
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("redirect_uri", REDIRECT_URI)
                .appendQueryParameter("scope", ("location" + " activity").trim())
                .appendQueryParameter("state", String.valueOf(SystemClock.uptimeMillis()));
    }

    private void getTokens(final VolleyCallback callback, Intent data) {
        String code = "";
        final List<String> tokens = new ArrayList<>();

        Uri resultUri = data.getData();
        code = resultUri.toString().split("code=")[1];
        code = code.split("&")[0];
        editor.putString("code", code).commit();
        RequestQueue queue = Volley.newRequestQueue(AuthorizationActivity.this);
        String url = "https://api.moves-app.com/oauth/v1/access_token?grant_type=authorization_code&code=" + code + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + REDIRECT_URI;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String access_token = response.getString("access_token");
                            String refresh_token = response.getString("refresh_token");
                            editor.putString("access_token", access_token).commit();
                            editor.putString("refresh_token", refresh_token).commit();
                            tokens.add(access_token);
                            tokens.add(refresh_token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess(tokens);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AuthorizationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsObjRequest);
    }

    public interface VolleyCallback {
        void onSuccess(List<String> result);
    }
}
