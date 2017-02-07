package com.example.agathe.tsgtest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
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
    private String[] tokens;

    List<List<Contact>> contacts = null;

    public ContactsTask(Context context, String tokens[]) {
        this.context = context;
        this.tokens = tokens;
    }

    public void getContactsList(final VolleyCallback callback) {
        final List<List<Contact>> contacts = new ArrayList<>();

        RequestQueue queue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();

        // Request a string response from the provided URL.
        final List<String> types = new ArrayList<>();
        types.add("frequent_contacts");
        types.add("business_contacts");
        types.add("private_contacts");

        for (final String s : types) {
            final List<Contact> subContacts = new ArrayList<>();
            String url = "http://217.96.70.94/dsn-smp/" + s + "/8";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Contact type = new Contact();
                            type.setName(s);
                            subContacts.add(type);
                            Log.i(LOG_TAG, "Response is: " + response);
                            String[] parts = response.split("phoneNumbers");
                            for (String contact : parts) {
                                if (contact.contains("+")) {
                                    boolean dup = false;
                                    // to avoid dublons
                                    for (Contact c : subContacts) {
                                        if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                            dup = true;
                                        }
                                    }
                                    if (dup == false) {
                                        Contact c = new Contact();
                                        c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                                        c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                                        c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                                        subContacts.add(c);
                                    }
                                }
                            }
                            contacts.add(subContacts);
                            if (contacts.size() == 3) {
                                callback.onSuccess(contacts);
                            }
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
            queue.add(stringRequest);
        }
    }

    public interface VolleyCallback{
        void onSuccess(List<List<Contact>> contacts);
    }
}
