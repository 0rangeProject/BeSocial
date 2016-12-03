package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;


/**
 * Created by koudm on 25/10/2016.
 */

public class SportActivity  extends AppCompatActivity {

    ImageButton challenge_button;
    TextView duration_field, steps_field, challenge_field;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.sport_home);
        context = this;
        duration_field = (TextView) findViewById(R.id.durationText);
        steps_field = (TextView) findViewById(R.id.stepsText);
        challenge_field = (TextView) findViewById(R.id.challengeText);
        challenge_button = (ImageButton) findViewById(R.id.challengeImg);

        //  setup challenge image button.
        challenge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportActivity.this,
                        ChallengeActivity.class);
                startActivity(intent);
            }
        });
    }
}
