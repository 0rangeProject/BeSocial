package com.example.agathe.tsgtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by koudm on 30/10/2016.
 */

public class LittleServicesActivity extends AppCompatActivity {
    EditText little_service;
    Button send_message, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.little_services);
        little_service = (EditText) findViewById(R.id.little_service_text);
        send_message = (Button) findViewById(R.id.btn_send_message_little_services);
        call = (Button) findViewById(R.id.btn_call_little_services);
    }
}
