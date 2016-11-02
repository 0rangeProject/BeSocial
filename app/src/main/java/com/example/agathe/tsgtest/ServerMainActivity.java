package com.example.agathe.tsgtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by agathe on 02/11/16.
 */
public class ServerMainActivity extends Activity {

    private static final String CLIENT_ID = "H63B_cPr8kVvSm8NFiQ8p6NwdGD6NYo1";

    private static final String CLIENT_SECRET = "7Is1qx1ty23ylRegAT3n1Adm3WcJGi1n2iFse87fNV3sbb6RH5Uefdn1Lwm5UOCG";

    private static final int REQUEST_AUTHORIZE = 1;

    SharedPreferences settings = null;
    SharedPreferences.Editor editor = null;

    String access_token = "";
    String refresh_token = "";

    TextView text_access_token = null;
    TextView text_refresh_token = null;

    Button get_summary_daily = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_main);

        settings = getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        editor = settings.edit();

        String code = settings.getString("code", "");

        // On demande l'autorisation si on n'a pas encore de code
        if (code.equals("")) {
            Intent authorizationIntent = new Intent(this, AuthorizationActivity.class);
            startActivityForResult(authorizationIntent, REQUEST_AUTHORIZE);
        } else recoverAndCheckTokens();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_AUTHORIZE:
                recoverAndCheckTokens();
        }
    }

    private void refreshTokens(final String refresh_token_to_pass) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.moves-app.com/oauth/v1/access_token?grant_type=refresh_token&refresh_token=" + refresh_token_to_pass + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            editor.putString("access_token", response.getString("access_token")).commit();
                            text_access_token.setText("access token: " + access_token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            editor.putString("refresh_token", response.getString("refresh_token")).commit();
                            text_refresh_token.setText("refresh_token: " + refresh_token_to_pass);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsObjRequest);
    }

    private void recoverAndCheckTokens() {
        text_access_token = (TextView) findViewById(R.id.access_token);
        text_refresh_token = (TextView) findViewById(R.id.refresh_token);

        // On récupère le jeton et on vérifie sa validité
        access_token = settings.getString("access_token", "");
        refresh_token = settings.getString("refresh_token", "");

        text_access_token.setText("access token: " + access_token);
        text_refresh_token.setText("refresh_token: " + refresh_token);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.moves-app.com/oauth/v1/tokeninfo?access_token=" + access_token;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ServerMainActivity.this, "Access token valid", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        refreshTokens(refresh_token);
                        access_token = settings.getString("access_token", "");
                        text_access_token.setText("access token: " + access_token);
                        refresh_token = settings.getString("refresh_token", "");
                        text_refresh_token.setText("refresh_token: " + refresh_token);
                    }
                });

        queue.add(jsObjRequest);

        get_summary_daily = (Button) findViewById(R.id.get_summary_daily);
        get_summary_daily.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(ServerMainActivity.this);
                String url = "https://api.moves-app.com/api/1.1/user/summary/daily/20161101?access_token=" + access_token;
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(ServerMainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ServerMainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsObjRequest);
            }
        });
    }
}
