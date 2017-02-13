package com.example.agathe.tsgtest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.IdentityManager;
import com.example.agathe.tsgtest.carpooling.ListContacts;
import com.example.agathe.tsgtest.carpooling.PurposeActivity;
import com.example.agathe.tsgtest.carpooling.TestActivity;
import com.example.agathe.tsgtest.dto.Contact;
import com.example.agathe.tsgtest.events.PublicEventsActivity;
import com.example.agathe.tsgtest.sport.SportActivity;
import com.example.agathe.tsgtest.littleservices.LittleServicesActivity;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //*************AWS push notification part start************
    /** Class name for log messages. */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ImageButton carpoolingButton, publicEventsButton, sportButton, littleServicesButton;
    Context context;

    private IdentityManager identityManager;

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    protected DrawerLayout drawer;

    boolean userConnected = false;

    /**
     * Initializes the sign-in and sign-out buttons.
     */
    private void setupButtons() {
        carpoolingButton = (ImageButton) findViewById(R.id.carpooling_but);
        carpoolingButton.setOnClickListener(this);

        publicEventsButton = (ImageButton) findViewById(R.id.pevents_button);
        publicEventsButton.setOnClickListener(this);

        sportButton = (ImageButton) findViewById(R.id.sport_btn);
        sportButton.setOnClickListener(this);

        littleServicesButton = (ImageButton) findViewById(R.id.lServices_btn);
        littleServicesButton.setOnClickListener(this);
    }

    //*************AWS push notification part end**************

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recover or create user ID
        settings = getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        editor = settings.edit();
        String userID = settings.getString("userID", "");
        if (userID.isEmpty()) {
            userID = UUID.randomUUID().toString();
            editor.putString("userID", userID).commit();
        }

        userConnected = settings.getBoolean("userConnected", false);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //*************AWS push notification part start************
        // enablePushCheckBox = (CheckBox) findViewById(R.id.enable_push_checkbox);
        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the mobile client. It is created in the Application class.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();

        context = this;

        //  Library initialisation is required to be done once before any library function is called.
        //  You use your clientId and secret obtained from SMP website at developer tab.
        SMPLibrary.Initialise(this, "0000", "0000");

        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();

        final ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), "PREFERENCES_FILE", MODE_PRIVATE);

        ContactManager cm = new ContactManager(MainActivity.this, 8);
        cm.getContacts(1000, new ContactManager.VolleyCallbackGlobal() {
            @Override
            public void onSuccess(List<List<Contact>> contacts) {
                for (List<Contact> subContacts : contacts) {
                    ListContacts list = new ListContacts();
                    list.setContacts(subContacts);
                    complexPreferences.putObject(subContacts.get(0).getName(), list);
                }
                complexPreferences.commit();
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
            case R.id.action_about:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                builder.setPositiveButton(R.string.dialog_ok, null);
                builder.setIcon(R.mipmap.ic_launcher);

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_main_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        userConnected = settings.getBoolean("userConnected", false);
        if (userConnected == false) {
            SMPLibrary.ShowLoginDialog(MainActivity.this, new LoginResponseCallback() {
                @Override
                //  OnResponse callback is called when login request is finished processing
                //  and returns response code.
                public void OnResponse(int response) {
                    if (response == 200) {
                        //  response 200 is returned when login was successful.
                        editor.putBoolean("userConnected", true).commit();
                        Log.i("MainActivity:LoginResp", "It works - " + response);
                    } else {
                        //  log response when logging was not successful
                        Log.i("MainActivity:LoginResp", "Login response code - " + response);
                    }
                }
            });
        }

        setupButtons();

        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();
        identityManager = awsMobileClient.getIdentityManager();

        //*************AWS push notification part start************
        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushListenerService.ACTION_SNS_NOTIFICATION));

        //*************AWS push notification part end**************
    }

    @Override
    public void onClick(final View view) {
        if (view == publicEventsButton) {
            Intent intent = new Intent(MainActivity.this,
                    PublicEventsActivity.class);
            startActivity(intent);
        }

        if (view == sportButton) {
            Intent intent = new Intent(MainActivity.this,
                    SportActivity.class);
            startActivity(intent);
        }

        if (view == carpoolingButton) {
            Intent intent = new Intent(MainActivity.this,
                    PurposeActivity.class);
            startActivity(intent);
        }

        if (view == littleServicesButton) {
            Intent intent = new Intent(MainActivity.this,
                    LittleServicesActivity.class);
            startActivity(intent);
        }
    }


    //*************AWS push notification part start************
    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Received notification from local broadcast. Display it in a dialog.");

            Bundle data = intent.getBundleExtra(PushListenerService.INTENT_SNS_NOTIFICATION_DATA);
            String message = PushListenerService.getMessage(data);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.push_demo_title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        // unregister notification receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }
    //*************AWS push notification part end**************

    private AlertDialog showErrorMessage(final int resId, final Object... args) {
        return new AlertDialog.Builder(this).setMessage(getString(resId, (Object[]) args))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.carpooling_draw) {
            Intent intent = new Intent(MainActivity.this, PurposeActivity.class);
            startActivity(intent);
        } else if (id == R.id.ls_draw) {
            Intent intent = new Intent(MainActivity.this, LittleServicesActivity.class);
            startActivity(intent);
        } else if (id == R.id.pe_draw) {
            Intent intent = new Intent(MainActivity.this, PublicEventsActivity.class);
            startActivity(intent);
        } else if (id == R.id.sport_draw) {
            Intent intent = new Intent(MainActivity.this, SportActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}