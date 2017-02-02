package com.example.agathe.tsgtest;

/**
 * Created by vincent on 19/01/17.
 */

import android.content.Context;
import android.util.Log;

import com.olab.smplibrary.SMPLibrary;
import com.olab.smplibrary.DataResponseCallback;

import java.util.List;

public class GetContact {
    Context context;
    public GetContact(Context context) {
        this.context = context;
    }

    public List<GetContact> getFrequentContacts(int number) {
        List<GetContact> FrequentContacts = null;
        SMPLibrary.GetFrequentContacts(context, number, new DataResponseCallback(){
            @Override
            public void OnResponse(int response_code, String data_response){
                Log.i("MainActivity:Response", "GetFrequentContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetFrequentContacts - " + data_response);
               // ShowMessage("Frequent Contacts\n" + data_response); //test

            }
        });
        return FrequentContacts;
    }

    public List<GetContact> getBusinessContacts(int number) {
        List<GetContact> businessContacts = null;
        SMPLibrary.GetBusinessContacts(context, number, new DataResponseCallback(){
            @Override
            public void OnResponse( int response_code, String data_response ){
                Log.i("MainActivity:Response", "GetBusinessContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetBusinessContacts - " + data_response);
               // ShowMessage("Business Contacts\n" + data_response); //test

            }
        });
        return businessContacts;
    }

    public List<GetContact> getPrivateContacts(int number) {
        List<GetContact> PrivateContacts = null;
        SMPLibrary.GetPrivateContacts(context, number, new DataResponseCallback(){
            @Override
            public void OnResponse( int response_code, String data_response ){
                Log.i("MainActivity:Response", "GetPrivateContacts response code " + response_code );
                Log.i("MainActivity:Response", "GetPrivateContacts - " + data_response);
                // ShowMessage("Private Contacts\n" + data_response); //test
            }
        });
        return PrivateContacts;
    }
}
