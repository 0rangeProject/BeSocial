package com.example.agathe.tsgtest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agathe.tsgtest.dto.Contact;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agathe on 23/01/17.
 */

public class ContactsTask {

    private static final String LOG_TAG = "ContactsTask";
    private Context context;
    private String type;
    private String[] tokens;

    List<Contact> contacts = null;

    public ContactsTask(Context context, String type, String tokens[]) {
        this.context = context;
        this.type = type;
        this.tokens = tokens;
    }

    public void getContactsList(final VolleyCallback callback) {
        String url = "http://217.96.70.94/dsn-smp/frequent_contacts/8";
        final List<Contact> contacts = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(LOG_TAG, "Response is: " + response);
                        String[] parts = response.split("phoneNumbers");
                        for (String contact : parts) {
                            if (contact.contains("+")) {
                                Contact c = new Contact();
                                c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                                c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                                c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                                contacts.add(c);
                            }
                        }
                        callback.onSuccess(contacts);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(LOG_TAG, "That didn't work!");
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + tokens[0]);

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public interface VolleyCallback{
        void onSuccess(List<Contact> contacts);
    }
}
