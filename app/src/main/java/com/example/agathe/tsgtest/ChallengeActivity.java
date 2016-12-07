package com.example.agathe.tsgtest;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import static android.R.attr.phoneNumber;

/**
 * Created by koudm on 30/10/2016.
 */

public class ChallengeActivity extends AppCompatActivity {
    ImageView img_challenge;
    TextView challenge_texte;
    ImageButton btn_send_sms, btn_call;
    Context context;
    ListView contacts_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.sport_challenge);
        context = this;
        challenge_texte = (TextView) findViewById( R.id.see_challenge);
        img_challenge = (ImageView) findViewById(R.id.challengeImg);
       contacts_list = (ListView) findViewById(R.id.challenge_contact_list);
        btn_send_sms = (ImageButton) findViewById(R.id.img_btn_sms);
        btn_call = (ImageButton) findViewById(R.id.img_btn_call);
        List<ContactChallenge> contacts = genererContact();

        ContactAdapter adapter = new ContactAdapter(ChallengeActivity.this, contacts);
        contacts_list.setAdapter(adapter);

        //send sms to challenge a friend
        /**btn_send_sms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.putExtra("address", "0625252525");
                smsIntent.putExtra("sms_body","Hello, Do you want to do a running session with me? ");
                smsIntent.setData(Uri.parse("smsto:" + "0625252525"));
                startActivity(smsIntent);
            }
        });
        **/
        }

    private List<ContactChallenge>  genererContact(){
        List<ContactChallenge> contacts = new ArrayList<ContactChallenge>();
        contacts.add(new ContactChallenge("Anna Hattaway", "Best Time:10'54", "Average:6km"));
        contacts.add(new ContactChallenge("Haly Berry", "Best Time:12'28", "Average:4km"));
        contacts.add(new ContactChallenge("Will Smith", "Best Time:40'22", "Average:8km"));
        contacts.add(new ContactChallenge("Ed Shee", "Best Time:9'42", "Average:6km"));

        return contacts;
    }
}

