package com.example.agathe.tsgtest.carpooling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.CommonTravel;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.ArrayList;

/**
 * Created by agathe on 05/12/16.
 */

public class PotentialCarpoolersActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CarpoolersActivity";
        public int pageNumber;
        public ArrayList<CommonTravel> travels = new ArrayList<CommonTravel>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_carpoolers);

            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                pageNumber = extras.getInt("pageNumber");
                travels = extras.getParcelableArrayList("travels");
                Log.i(LOG_TAG, "pageNumber = " + pageNumber);
            }

            // Set toolbar
            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_carpoolers);
            setSupportActionBar(myToolbar);

            if (savedInstanceState == null) {
                Bundle args = new Bundle();
                args.putInt("pageNumber", pageNumber);
                args.putParcelableArrayList("travels", travels);
                PotentialCarpoolersFragment pcf = new PotentialCarpoolersFragment();
                pcf.setArguments(args);
                getFragmentManager().beginTransaction().add(R.id.container, pcf).commit();
            }
        }

        private void refresh() {
            finish();
            Intent intent = new Intent(PotentialCarpoolersActivity.this, PotentialCarpoolersActivity.class);
            startActivity(intent);
        }
    }
