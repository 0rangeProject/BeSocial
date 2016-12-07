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
    Context context;
        TextView view_test, view_test_1;
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

            // Get a support ActionBar corresponding to this toolbar
            ActionBar ab = getSupportActionBar();

            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);

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
            getMenuInflater().inflate(R.menu.card_layout, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_disconnect:
                    SMPLibrary.Logout();
                    refresh();
                    return true;

                case R.id.action_about:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                    builder.setPositiveButton(R.string.dialog_ok, null);
                    builder.setIcon(R.mipmap.ic_launcher);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;

                default:
                    // If we got here, the user's action was not recognized.
                    // Invoke the superclass to handle it.
                    return super.onOptionsItemSelected(item);

            }
        }

        private void refresh() {
            finish();
            Intent intent = new Intent(PotentialCarpoolersActivity.this, PotentialCarpoolersActivity.class);
            startActivity(intent);
        }
    }
