package com.example.agathe.tsgtest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.agathe.tsgtest.dto.Contact;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 23/01/17.
 */

public class ContactManager {

    private Context context;
    private int deviceID;

    public ContactManager(Context context, int deviceID) {
        this.context = context;
        this.deviceID = deviceID;
    }

    public void getFrequentContacts(int numberOfContacts, final VolleyCallback callback) {

        SMPLibrary.GetFrequentContactsByDeviceID(context, numberOfContacts, deviceID, new DataResponseCallback(){
            @Override
            public void OnResponse(int response_code, String data_response) {
                List<Contact> contacts = new ArrayList<>();
                Log.i("MainActivity:Response", "GetFrequentContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetFrequentContacts - " + data_response);
                String[] parts = data_response.split("phoneNumbers");
                for (String contact : parts) {
                    if (contact.contains("+")) {
                        boolean dup = false;
                        // to avoid dublons
                        for (Contact c : contacts) {
                            if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                dup = true;
                            }
                        }
                        if (dup == false) {
                            Contact c = new Contact();
                            c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                            c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                            c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                            contacts.add(c);
                        }
                    }
                }
                callback.onSuccess(contacts);
            }
        });
    }

    public void getBusinessContacts(int numberOfContacts, final VolleyCallback callback) {
        SMPLibrary.GetBusinessContactsByDeviceID(context, numberOfContacts, deviceID, new DataResponseCallback(){
            @Override
            public void OnResponse( int response_code, String data_response ){
                List<Contact> contacts = new ArrayList<>();
                Log.i("MainActivity:Response", "GetBusinessContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetBusinessContacts - " + data_response);
                String[] parts = data_response.split("phoneNumbers");
                for (String contact : parts) {
                    if (contact.contains("+")) {
                        boolean dup = false;
                        // to avoid dublons
                        for (Contact c : contacts) {
                            if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                dup = true;
                            }
                        }
                        if (dup == false) {
                            Contact c = new Contact();
                            c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                            c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                            c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                            contacts.add(c);
                        }
                    }
                }
                callback.onSuccess(contacts);
            }
        });
    }

    public void getPrivateContacts(int numberOfContacts, final VolleyCallback callback) {
        SMPLibrary.GetPrivateContactsByDeviceID(context, numberOfContacts, deviceID, new DataResponseCallback(){
            @Override
            public void OnResponse( int response_code, String data_response ){
                List<Contact> contacts = null;
                Log.i("MainActivity:Response", "GetPrivateContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetPrivateContacts - " + data_response);
                String[] parts = data_response.split("phoneNumbers");
                for (String contact : parts) {
                    if (contact.contains("+")) {
                        boolean dup = false;
                        // to avoid dublons
                        for (Contact c : contacts) {
                            if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                dup = true;
                            }
                        }
                        if (dup == false) {
                            Contact c = new Contact();
                            c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                            c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                            c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                            contacts.add(c);
                        }
                    }
                }
                callback.onSuccess(contacts);
            }
        });
    }

    public void getContacts(int numberOfContacts, final VolleyCallbackGlobal callback) {
        final List<List<Contact>> contacts = new ArrayList<>();

        SMPLibrary.GetFrequentContactsByDeviceID(context, numberOfContacts, deviceID, new DataResponseCallback(){
            @Override
            public void OnResponse(int response_code, String data_response ){
                List<Contact> frequentContacts = new ArrayList<>();
                Log.i("MainActivity:Response", "GetFrequentContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetFrequentContacts - " + data_response);
                Contact type = new Contact();
                type.setName("frequent_contacts");
                frequentContacts.add(type);
                String[] parts = data_response.split("phoneNumbers");
                for (String contact : parts) {
                    if (contact.contains("+")) {
                        boolean dup = false;
                        // to avoid dublons
                        for (Contact c : frequentContacts) {
                            if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                dup = true;
                                break;
                            }
                        }
                        if (dup == false) {
                            Contact c = new Contact();
                            c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                            c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                            c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                            frequentContacts.add(c);
                        }
                    }
                }
                contacts.add(frequentContacts);
            }
        });

        SMPLibrary.GetBusinessContactsByDeviceID(context, numberOfContacts, deviceID, new DataResponseCallback(){
            @Override
            public void OnResponse(int response_code, String data_response ){
                List<Contact> businessContacts = new ArrayList<>();
                Log.i("MainActivity:Response", "GetBusinessContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetBusinessContacts - " + data_response);
                Contact type = new Contact();
                type.setName("business_contacts");
                businessContacts.add(type);
                String[] parts = data_response.split("phoneNumbers");
                for (String contact : parts) {
                    if (contact.contains("+")) {
                        boolean dup = false;
                        // to avoid dublons
                        for (Contact c : businessContacts) {
                            if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                dup = true;
                            }
                        }
                        if (dup == false) {
                            Contact c = new Contact();
                            c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                            c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                            c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                            businessContacts.add(c);
                        }
                    }
                }
                contacts.add(businessContacts);
            }
        });

        SMPLibrary.GetPrivateContactsByDeviceID(context, numberOfContacts, deviceID, new DataResponseCallback(){
            @Override
            public void OnResponse(int response_code, String data_response ){
                List<Contact> privateContacts = new ArrayList<>();
                Log.i("MainActivity:Response", "GetPrivateContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetPrivateContacts - " + data_response);
                Contact type = new Contact();
                type.setName("private_contacts");
                privateContacts.add(type);
                String[] parts = data_response.split("phoneNumbers");
                for (String contact : parts) {
                    if (contact.contains("+")) {
                        boolean dup = false;
                        // to avoid dublons
                        for (Contact c : privateContacts) {
                            if (c.getPhoneNumber().equals("+" + contact.split("\\+")[1].split("\"")[0])) {
                                dup = true;
                            }
                        }
                        if (dup == false) {
                            Contact c = new Contact();
                            c.setName(contact.split("name\":\"")[1].split("\"")[0]);
                            c.setPhoneNumber("+" + contact.split("\\+")[1].split("\"")[0]);
                            c.setRelationStrength(contact.split("relationStrength\":")[1].split("\\}")[0]);
                            privateContacts.add(c);
                        }
                    }
                }
                contacts.add(privateContacts);
                callback.onSuccess(contacts);
            }
        });
    }

    public interface VolleyCallback {
        void onSuccess(List<Contact> contacts);
    }

    public interface VolleyCallbackGlobal {
        void onSuccess(List<List<Contact>> contacts);
    }
}
