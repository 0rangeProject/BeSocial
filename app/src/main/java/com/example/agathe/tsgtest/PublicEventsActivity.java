package com.example.agathe.tsgtest;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.ArrayList;

public class PublicEventsActivity extends AppCompatActivity {

    Context context;
    private ListView cardsList;
    //TextView view_test, view_test_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_events);
        //set toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_pe);
        setSupportActionBar(myToolbar);

        cardsList = (ListView) findViewById(R.id.cards_list);
        setupList();

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        context = this;
        /*view_test = (TextView) findViewById( R.id.pe_view_test);
        view_test_1 = (TextView) findViewById( R.id.pe_view_test_1);
        String test_output = "Logged as: " + SMPLibrary.LoggedUserName();
        test_output += "Is logged in: " + SMPLibrary.IsLoggedIn();
        SMPLibrary.GetFrequentContacts(context, 10, new DataResponseCallback(){
            @Override
            //  OnResponse callback is called when request is finished processing
            //  returns response code and JSON in form of string.
            public void OnResponse( int response_code, String data_response ){
                Log.i("PublicEvents:Response", "GetFrequentContacts response code " + response_code );
                Log.i("PublicEvents:Respwonse", "GetFrequentContacts - " + data_response);
                ShowMessage("Frequent Contacts\n" + data_response);
            }
        });
        view_test.setText(test_output);*/

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

    /*  function displaying message in TextView box.
    void ShowMessage(String message){
        final String to_show = message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view_test_1.setText( to_show );
            }
        });
    }
    */
    private void refresh() {
        finish();
        Intent intent = new Intent(PublicEventsActivity.this, PublicEventsActivity.class);
        startActivity(intent);
    }

    /************* list card ****************/
    private void setupList() {
        cardsList.setAdapter(createAdapter());
        cardsList.setOnItemClickListener(new ListItemClickListener());
    }

    private CardsAdapter createAdapter() {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            items.add(i, "Text for List Item " + i);
        }

        return new CardsAdapter(this, items, new ListItemButtonClickListener());
    }
    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = cardsList.getFirstVisiblePosition(); i <= cardsList.getLastVisiblePosition(); i++) {
                if (v == cardsList.getChildAt(i - cardsList.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_1)) {
                    // PERFORM AN ACTION WITH THE ITEM AT POSITION i
                    Toast.makeText(context, "Clicked on Left Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
                } else if (v == cardsList.getChildAt(i - cardsList.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_2)) {
                    // PERFORM ANOTHER ACTION WITH THE ITEM AT POSITION i
                    Toast.makeText(context, "Clicked on Right Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private final class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(context, "Clicked on List Item " + position, Toast.LENGTH_SHORT).show();
        }
    }

}
