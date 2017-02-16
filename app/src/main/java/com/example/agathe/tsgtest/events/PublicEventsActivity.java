package com.example.agathe.tsgtest.events;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.agathe.tsgtest.MainActivity;
import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.SettingsActivity;
import com.example.agathe.tsgtest.SettingsFragment;

import java.util.ArrayList;

public class PublicEventsActivity extends AppCompatActivity {

    Context context;
    RecyclerView mylist;
    CheckBox filter_button1, filter_button2, filter_button3, filter_button4, filter_button5;
    Button filter_button_reset, filter_button_confirm;
    FloatingActionButton add_event_action;
    //TextView view_test, view_test_1;



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
        context = this;
        //filter
        filter_button1 = (CheckBox) findViewById(R.id.filter_button1);
        filter_button2 = (CheckBox) findViewById(R.id.filter_button2);
        filter_button3 = (CheckBox) findViewById(R.id.filter_button3);
        //list
        mylist = (RecyclerView) findViewById(R.id.my_list);
        mylist.setHasFixedSize(true);
        mylist.setLayoutManager(new LinearLayoutManager(this));
        mylist.setAdapter(createAdapter());
        //add event action
        add_event_action = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        add_event_action.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(PublicEventsActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_settings:
                Intent intent = new Intent(PublicEventsActivity.this, SettingsActivity.class);
                startActivity(intent);
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
        Intent intent = new Intent(PublicEventsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /************* list card ****************/
    private EventListAdapter createAdapter() {
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        for (int i = 0; i < 3; i++) {
            items.add(i, new ListItem("New event title "+i,"Place: Urbawood","Time: 12/12/2016","event_example","BLA BLA BLA BLA ...",false));
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
