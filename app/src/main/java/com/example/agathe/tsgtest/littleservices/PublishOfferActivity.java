package com.example.agathe.tsgtest.littleservices;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.SMPLibrary;


/**
 * Created by koudm on 26/01/2017.
 */

public class PublishOfferActivity extends AppCompatActivity {
    EditText offer_title, offer_type, offer_description;
    Button btn_publish_offer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_offer_littleservices);
        offer_title = (EditText) findViewById(R.id.post_offer_title) ;
        offer_type = (EditText) findViewById(R.id.post_offer_type) ;
        offer_description = (EditText) findViewById(R.id.post_offer_description) ;
        btn_publish_offer = (Button) findViewById(R.id.btn_publish) ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_little_services);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Publish an offer");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  super.onCreateOptionsMenu(menu);
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
