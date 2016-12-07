package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.SMPLibrary;

/**
 * Created by koudm on 30/10/2016.
 */

public class ChallengeActivity extends AppCompatActivity {
    Button btn_challenge;
    TextView challenge_texte;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.sport_challenge);
        context = this;
       // challenge_texte = (TextView) findViewById( R.id.login);
        //btn_challenge = (Button) findViewById( R.id.logout_but) ;
        //  Library initialisation is required to be done once before any library function is called.
        //  You use your clientId and secret obtained from SMP website at developer tab.
        SMPLibrary.Initialise(this, "0000", "0000");


        //  setup sport button.
        btn_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /** Intent intent = new Intent(MainActivity.this,
                        SportActivity.class);
                startActivity(intent);
                **/
            }
        });
    }


}

