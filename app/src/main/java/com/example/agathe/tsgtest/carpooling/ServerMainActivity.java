package com.example.agathe.tsgtest.carpooling;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.agathe.tsgtest.R;
import com.amazonaws.models.nosql.PathsDO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by agathe on 02/11/16.
 */
public class ServerMainActivity extends Activity {

    private static final String CLIENT_ID = "H63B_cPr8kVvSm8NFiQ8p6NwdGD6NYo1";

    private static final String CLIENT_SECRET = "7Is1qx1ty23ylRegAT3n1Adm3WcJGi1n2iFse87fNV3sbb6RH5Uefdn1Lwm5UOCG";

    private static final int REQUEST_AUTHORIZE = 2;
    private static final String LOG_TAG = "ServerMainActivity";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    private String access_token = "";
    private String refresh_token = "";

    private TextView text_access_token = null;
    private TextView text_refresh_token = null;
    private TextView text_paths = null;

    private Button get_paths = null;

    private String userID = "";

    // DatabaseHandler db = new DatabaseHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_main);

        settings = getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        editor = settings.edit();

        String code = settings.getString("code", "");
        userID = settings.getString("userID", "");

        // On demande l'autorisation si on n'a pas encore de code
        if (code.equals("")) {
            Intent authorizationIntent = new Intent(this, AuthorizationActivity.class);
            startActivityForResult(authorizationIntent, REQUEST_AUTHORIZE);
        } else {
            recoverAndCheckTokens();
        }

        // En cas de succès de la requête (récupérer les paths et enregistrer en base), on affiche tous les résultats
        get_paths = (Button) findViewById(R.id.get_paths);
        get_paths.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                getPaths(new VolleyCallback() {
                    @Override
                    public void onSuccess(List<PathsDO> result) {
                        Log.i(LOG_TAG, String.valueOf(result.size()));

                        /*
                        GetAllItemsTask gait = new GetAllItemsTask();
                        gait.execute();
                        ArrayList<PathsDO> list = gait.getPaths();
                        ù/
                        // Log.i(LOG_TAG, String.valueOf(list.size()));

                        /*
                        List<Path> paths = db.getAllPaths();

                        for (Path p : paths) {
                            String log = p.toString();
                            Log.d("Name", log);
                        }
                        */
                    }
                });
            }
        });
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

    private void recoverAndCheckTokens() {;
        // On récupère le jeton et on vérifie sa validité
        access_token = settings.getString("access_token", "");
        refresh_token = settings.getString("refresh_token", "");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.moves-app.com/oauth/v1/tokeninfo?access_token=" + access_token;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Toast.makeText(ServerMainActivity.this, "Access token valid", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        refreshTokens(refresh_token);
                        access_token = settings.getString("access_token", "");
                        refresh_token = settings.getString("refresh_token", "");
                    }
                });

        queue.add(jsObjRequest);
    }

    // Recover all paths from a user on Moves, and save them in database
    private void getPaths(final VolleyCallback callback) {
        text_paths = (TextView) findViewById(R.id.paths);
        final List<PathsDO> paths = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(ServerMainActivity.this);
        final String url = "https://api.moves-app.com/api/1.1/user/places/daily?pastDays=31&access_token=" + access_token;
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        text_paths.setText(response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject element = null;
                            try {
                                element = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                JSONArray segments = element.getJSONArray("segments");
                                for (int j = 0; j < segments.length(); j++) {
                                    JSONObject segment = segments.getJSONObject(j);

                                    PathsDO path = new PathsDO();

                                    try {
                                        path.setUserId(userID);
                                        path.setPathId(userID + segment.getString("startTime"));
                                        path.setStartTime(segment.getString("startTime"));
                                        path.setEndTime(segment.getString("endTime"));
                                        path.setLat(segment.getJSONObject("place").getJSONObject("location").getDouble("lat"));
                                        path.setLon(segment.getJSONObject("place").getJSONObject("location").getDouble("lon"));
                                        paths.add(path);
                                        // new SaveObjectTask().execute(path);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    // paths.add(path);
                                    // db.addPath(path);
                                }
                            } catch (JSONException e) { }
                        }
                        callback.onSuccess(paths);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        text_paths.setText(error.toString());
                    }
                });
        queue.add(jsArrRequest);
    }

    public interface VolleyCallback {
        void onSuccess(List<PathsDO> result);
    }
}