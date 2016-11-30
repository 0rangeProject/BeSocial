package com.example.agathe.tsgtest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.push.PushManager;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

public class MainActivity extends AppCompatActivity {

    //*************AWS push notification part start************
    /** Class name for log messages. */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // if you want to take action when a google services result is received.
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1363;
    private PushManager pushManager;
    private CheckBox enablePushCheckBox;

    //*************AWS push notification part end**************
    Button login_button, logout_button, goto_PublicEvents;
    TextView login_field, contents;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //*************AWS push notification part start************
        enablePushCheckBox = (CheckBox) findViewById(R.id.enable_push_checkbox);
        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the mobile client. It is created in the Application class.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        pushManager = AWSMobileClient.defaultMobileClient().getPushManager();

        enablePushCheckBox.setChecked(pushManager.isPushEnabled());
        enablePushCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotification(enablePushCheckBox.isChecked());
            }
        });
        //*************AWS push notification part end**************

        // go to public events activity
        goto_PublicEvents = (Button)findViewById(R.id.pevents_button);
        goto_PublicEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (view.getId()) {
                    case R.id.pevents_button:
                        intent = new Intent(MainActivity.this, PublicEventsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });


        context = this;
        login_button = (Button) findViewById( R.id.login_but) ;
        logout_button = (Button) findViewById( R.id.logout_but);
        login_field = (TextView) findViewById( R.id.login);
        //  Library initialisation is required to be done once before any library function is called.
        //  You use your clientId and secret obtained from SMP website at developer tab.
        SMPLibrary.Initialise(this, "testid", "testpass");

        //  setup listener for login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  when clicked show dialog where user can login.
                SMPLibrary.ShowLoginDialog(MainActivity.this, new LoginResponseCallback() {
                    @Override
                    //  OnResponse callback is called when login request is finished processing
                    //  and returns response code.
                    public void OnResponse(int response) {
                        if (response == 200) {
                            //  response 200 is returned when login was successful.
                            FunctionCalledWhenLoginIsSuccessful();
                        } else {
                            //  log response when logging was not successful
                            Log.i("MainActivity:LoginResp", "Login response code - " + response);
                        }
                    }
                });
            }
        });

        //  setup logout button.
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMPLibrary.Logout();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        login_field.setText("Not Logged In");
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        //*************AWS push notification part start************
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushListenerService.ACTION_SNS_NOTIFICATION));

        //*************AWS push notification part end**************
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
                enablePushCheckBox.setChecked(pushManager.isPushEnabled());
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


    //  function displaying message in TextView box.
    void ShowMessage(String message){
        final String to_show = message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contents.setText( to_show );
            }
        });
    }
    void FunctionCalledWhenLoginIsSuccessful() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                login_field.setText("Logged as: " + SMPLibrary.LoggedUserName());
            }
        });
    }

    private AlertDialog showErrorMessage(final int resId, final Object... args) {
        return new AlertDialog.Builder(this).setMessage(getString(resId, (Object[]) args))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
