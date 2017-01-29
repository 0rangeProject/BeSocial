package com.example.agathe.tsgtest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.push.PushManager;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.AmazonClientException;

import com.example.agathe.tsgtest.carpooling.HomeCarpoolingActivity;
import com.example.agathe.tsgtest.carpooling.PurposeActivity;
import com.example.agathe.tsgtest.events.PublicEventsActivity;
import com.example.agathe.tsgtest.sport.SportActivity;
import com.example.agathe.tsgtest.littleservices.LittleServicesActivity;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //*************AWS push notification part start************
    /** Class name for log messages. */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private PushManager pushManager;
    private CheckBox enablePushCheckBox;

    private ImageButton carpoolingButton, publicEventsButton, sportButton, littleServicesButton;
    Context context;

    private Button contactsButton;
    private TextView contacts;

    /** The identity manager used to keep track of the current user account. */
    private IdentityManager identityManager;

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    protected DrawerLayout drawer;

    boolean userConnected = false;

    /**
     * Initializes the sign-in and sign-out buttons.
     */
    private void setupButtons() {
        carpoolingButton = (ImageButton) findViewById(R.id.carpooling_but) ;
        carpoolingButton.setOnClickListener(this);

        publicEventsButton = (ImageButton)findViewById(R.id.pevents_button);
        publicEventsButton.setOnClickListener(this);

        sportButton = (ImageButton) findViewById(R.id.sport_btn);
        sportButton.setOnClickListener(this);

        littleServicesButton = (ImageButton) findViewById(R.id.lServices_btn);
        littleServicesButton.setOnClickListener(this);

        contactsButton = (Button) findViewById(R.id.contacts_btn);
        contactsButton.setOnClickListener(this);
    }

    //*************AWS push notification part end**************

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:af846312-d9d1-4007-8825-869fdfc2a3ae", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        // Initialize the Cognito Sync client
        CognitoSyncManager syncClient = new CognitoSyncManager(
                getApplicationContext(),
                Regions.US_EAST_1, // Region
                credentialsProvider);

        */

        // Recover or create user ID
        settings = getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        editor = settings.edit();
        String userID = settings.getString("userID", "");
        if (userID.isEmpty()) {
            userID = UUID.randomUUID().toString();
            editor.putString("userID", userID).commit();
        }

        editor.putBoolean("userConnected", true).commit();
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

        pushManager = AWSMobileClient.defaultMobileClient().getPushManager();

        /* enablePushCheckBox.setChecked(pushManager.isPushEnabled());
        enablePushCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotification(enablePushCheckBox.isChecked());
            }
        });
        //*************AWS push notification part end**************
        */
        toggleNotification(true);
        context = this;

        //  Library initialisation is required to be done once before any library function is called.
        //  You use your clientId and secret obtained from SMP website at developer tab.
        SMPLibrary.Initialise(this, "0000", "0000");

        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();
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
                        FunctionCalledWhenLoginIsSuccessful();
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

    void FunctionCalledWhenLoginIsSuccessful() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //login_field.setText("Logged as: " + SMPLibrary.LoggedUserName() );
            }
        });
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

        if (view == contactsButton) {
            ContactManager cm = new ContactManager(context);
            cm.getFrequentContacts(10);
            cm.getBusinessContacts(10);
            cm.getPrivateContacts(10);
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

    private void toggleNotification(final boolean enabled) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {
                // register device first to ensure we have a push endpoint.
                pushManager.registerDevice();

                // if registration succeeded.
                if (pushManager.isRegistered()) {
                    try {
                        pushManager.setPushEnabled(enabled);
                        // Automatically subscribe to the default SNS topic
                        if (enabled) {
                            pushManager.subscribeToTopic(pushManager.getDefaultTopic());
                        }
                        return null;
                    } catch (final AmazonClientException ace) {
                        Log.e(LOG_TAG, "Failed to change push notification status", ace);
                        return ace.getMessage();
                    }
                }
                return "Failed to register for push notifications.";
            }

            @Override
            protected void onPostExecute(final String errorMessage) {
                //enablePushCheckBox.setChecked(pushManager.isPushEnabled());
                System.out.println((errorMessage == null) ? "Register succeed......" : "Fail to register......" + errorMessage);
                if (errorMessage != null) {
                    showErrorMessage(R.string.error_message_update_notification, errorMessage);
                }
            }
        }.execute();
    }

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
            Intent intent = new Intent(MainActivity.this, HomeCarpoolingActivity.class);
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