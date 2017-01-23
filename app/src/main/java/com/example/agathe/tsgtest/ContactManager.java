package com.example.agathe.tsgtest;

import android.content.Context;
import android.util.Log;

import com.example.agathe.tsgtest.dto.Contact;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.List;

/**
 * Created by agathe on 23/01/17.
 */

public class ContactManager {

    private Context context;

    public ContactManager(Context context) {
        this.context = context;
    }

    public List<Contact> getFrequentContacts(int numberOfContacts) {
        List<Contact> contacts = null;

        SMPLibrary.GetFrequentContacts(context, numberOfContacts, new DataResponseCallback(){
            @Override
            public void OnResponse(int response_code, String data_response){
                Log.i("MainActivity:Response", "GetFrequentContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetFrequentContacts - " + data_response);
            }
        });

        return contacts;
    }

    public List<Contact> getBusinessContacts(int numberOfContacts) {
        List<Contact> contacts = null;

        SMPLibrary.GetBusinessContacts(context, numberOfContacts, new DataResponseCallback(){
            @Override
            public void OnResponse( int response_code, String data_response ){
                Log.i("MainActivity:Response", "GetBusinessContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetBusinessContacts - " + data_response);
            }
        });

        return contacts;
    }

    public List<Contact> getPrivateContacts(int numberOfContacts) {
        List<Contact> contacts = null;

        SMPLibrary.GetPrivateContacts(context, 10, new DataResponseCallback(){
            @Override
            public void OnResponse( int response_code, String data_response ){
                Log.i("MainActivity:Response", "GetPrivateContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetPrivateContacts - " + data_response);
            }
        });

        return contacts;
    }
}
