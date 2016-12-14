package com.example.agathe.tsgtest.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.SMPLibrary;

/**
 * Created by agathe on 14/12/16.
 */

public class HomeCarpoolingActivity extends AppCompatActivity {
    private ToggleButton enableButton;
    private Button tripsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_carpooling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_carpooling_home);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.enable_button);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(HomeCarpoolingActivity.this, GeolocationService.class);
                if (isChecked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });

        tripsButton = (Button) findViewById(R.id.trips_button);
        tripsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeCarpoolingActivity.this, PurposeActivity.class);
                startActivity(intent);
            }
        });
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
