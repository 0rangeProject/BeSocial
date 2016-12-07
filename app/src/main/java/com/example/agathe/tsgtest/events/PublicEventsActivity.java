package com.example.agathe.tsgtest.events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.SMPLibrary;

public class PublicEventsActivity extends AppCompatActivity {

    Context context;
    TextView view_test, view_test_1;
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

        context = this;
        view_test = (TextView) findViewById( R.id.pe_view_test);
        view_test_1 = (TextView) findViewById( R.id.pe_view_test_1);
        String test_output = "Logged as: " + SMPLibrary.LoggedUserName();
        test_output += "Is logged in: " + SMPLibrary.IsLoggedIn();
        SMPLibrary.GetFrequentContacts(context, 10, new DataResponseCallback(){
            @Override
            //  OnResponse callback is called when request is finished processing
            //  returns response code and JSON in form of string.
            public void OnResponse( int response_code, String data_response ){
                Log.i("PublicEvents:Response", "GetFrequentContacts response code " + response_code );
                Log.i("PublicEvents:Response", "GetFrequentContacts - " + data_response);
                ShowMessage("Frequent Contacts\n" + data_response);
            }
        });
        view_test.setText(test_output);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //  function displaying message in TextView box.
    void ShowMessage(String message){
        final String to_show = message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view_test_1.setText( to_show );
            }
        });
    }

    private void refresh() {
        finish();
        Intent intent = new Intent(PublicEventsActivity.this, PublicEventsActivity.class);
        startActivity(intent);
    }
}
