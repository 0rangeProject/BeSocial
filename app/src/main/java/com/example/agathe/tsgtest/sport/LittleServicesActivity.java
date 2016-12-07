package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.agathe.tsgtest.ContactLS;
import com.example.agathe.tsgtest.R;

/**
 * Created by koudm on 30/10/2016.
 */

public class LittleServicesActivity extends AppCompatActivity {
    EditText little_service;
    ImageButton btn_send_message, btn_call;
    Context context;
    ListView contacts_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.little_services);
        context = this;
        little_service = (EditText) findViewById(R.id.little_service_text);
        btn_send_message = (ImageButton) findViewById(R.id.img_btn_sms);
        btn_call = (ImageButton) findViewById(R.id.img_btn_call);
        contacts_list = (ListView) findViewById(R.id.ls_contact_list);

       /** btn_send_message.setImageResource(R.drawable.envelop);
        btn_call.setImageResource(R.drawable.call_icon);
        **/
        List<ContactLS> contacts = genererContact();

        ContactLSAdapter adapter = new ContactLSAdapter(LittleServicesActivity.this, contacts);
        contacts_list.setAdapter(adapter);
    }

    private List<ContactLS>  genererContact(){
        List<ContactLS> contacts = new ArrayList<ContactLS>();
        contacts.add(new ContactLS("Sarah Liousse", "Very closed (around 10km)"));
        contacts.add(new ContactLS("Arthur Dubois", "Known (around 50km)"));
        contacts.add(new ContactLS("Martin Delarre", "Known (around 300km)"));
        contacts.add(new ContactLS("Lili Rose", "New known (around 200km)"));

        return contacts;
    }
}
