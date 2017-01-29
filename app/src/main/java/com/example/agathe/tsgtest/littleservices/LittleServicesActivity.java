package com.example.agathe.tsgtest.littleservices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.SettingsActivity;
import com.example.agathe.tsgtest.SettingsFragment;

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
        setContentView(R.layout.activity_contacts_little_services);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_little_services);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

       /** btn_send_message.setImageResource(R.drawable.envelop);
        btn_call.setImageResource(R.drawable.call_icon);
        **/

        if (savedInstanceState == null) {
            ContactLSFragment clsf = new ContactLSFragment();
            getFragmentManager().beginTransaction().add(R.id.container, clsf).commit();
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
            case R.id.action_about:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                builder.setPositiveButton(R.string.dialog_ok, null);
                builder.setIcon(R.mipmap.ic_launcher);

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_main_settings:
                Intent intent = new Intent(LittleServicesActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
