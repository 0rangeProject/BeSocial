package com.example.agathe.tsgtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by agathe on 25/10/16.
 */

public class CarpoolingPurpose1 extends AppCompatActivity {
    TextView title, introduction, departure, destination;
    Button seeCarpoolers_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carpooling_purpose1);
    }
}
