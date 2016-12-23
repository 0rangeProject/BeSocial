package com.example.agathe.tsgtest.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.CommonTravel;
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

            Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_potential_carpoolers);
            setSupportActionBar(toolbar);

            Bundle extras = getIntent().getExtras();

            // Get a support ActionBar corresponding to this toolbar
            ActionBar ab = getSupportActionBar();

            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_disconnect:
                SMPLibrary.Logout();
                return true;

            case R.id.action_about:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                builder.setPositiveButton(R.string.dialog_ok, null);
                builder.setIcon(R.mipmap.ic_launcher);

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_main_settings:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    }
