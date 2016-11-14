package com.example.agathe.tsgtest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class PublicEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_events);
        //set toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_pe);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PublicEventsFragment()).commit();
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
}
