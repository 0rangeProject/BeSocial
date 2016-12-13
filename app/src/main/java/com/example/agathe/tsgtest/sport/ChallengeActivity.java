package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

/**
 * Created by koudm on 30/10/2016.
 */

public class ChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.fragment_contacts_challenge);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_challenge);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

       /** context = this;
        challenge_texte = (TextView) findViewById(R.id.see_challenge);
        img_challenge = (ImageView) findViewById(R.id.challengeImg);
        contacts_list = (ListView) findViewById(R.id.challenge_contact_list);
        btn_send_sms = (ImageButton) findViewById(R.id.img_btn_sms);
        btn_call = (ImageButton) findViewById(R.id.img_btn_call);
        **/

        if (savedInstanceState == null) {
            ContactChallengeFragment ccf = new ContactChallengeFragment();
            getFragmentManager().beginTransaction().add(R.id.container, ccf).commit();
        }
    }
}
