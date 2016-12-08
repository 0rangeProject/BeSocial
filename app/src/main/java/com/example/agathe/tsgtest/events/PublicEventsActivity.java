package com.example.agathe.tsgtest.events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.ArrayList;

public class PublicEventsActivity extends AppCompatActivity {

    Context context;
    RecyclerView mylist;
    CheckBox filter_button1, filter_button2, filter_button3, filter_button4, filter_button5;
    Button filter_button_reset, filter_button_confirm;
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

        //filter
        filter_button1 = (CheckBox) findViewById(R.id.filter_button1);
        filter_button2 = (CheckBox) findViewById(R.id.filter_button2);
        filter_button3 = (CheckBox) findViewById(R.id.filter_button3);
        filter_button4 = (CheckBox) findViewById(R.id.filter_button4);
        filter_button5 = (CheckBox) findViewById(R.id.filter_button5);
        filter_button_reset = (Button) findViewById(R.id.filter_button_reset);
        filter_button_confirm = (Button) findViewById(R.id.filter_button_confirm);
        filter_button_reset.getBackground().setAlpha(255);
        filter_button_confirm.getBackground().setAlpha(255);
        //list
        mylist = (RecyclerView) findViewById(R.id.my_list);
        mylist.setHasFixedSize(true);
        mylist.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        mylist.setAdapter(createAdapter());
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
    private EventListAdapter createAdapter() {
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        for (int i = 0; i < 10; i++) {
            items.add(i, new ListItem("New event title "+i,"Place: Urbawood","Time: 12/12/2016","event_example",false));
        }
        return new EventListAdapter(this, items);
    }
    /*private void setupList() {
        mylist.setAdapter(createAdapter());
        mylist.setOnItemClickListener(new ListItemClickListener());
    }



    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = mylist.getFirstVisiblePosition(); i <= mylist.getLastVisiblePosition(); i++) {
                if (v == mylist.getChildAt(i - mylist.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_1)) {
                    // PERFORM AN ACTION WITH THE ITEM AT POSITION i
                    Toast.makeText(context, "Clicked on Left Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
                } else if (v == mylist.getChildAt(i - mylist.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button_2)) {
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
    }*/

}
