package com.example.agathe.tsgtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.regions.Regions;
import com.example.agathe.tsgtest.AWS.AWSMobileClient;
import com.example.agathe.tsgtest.AWS.user.IdentityManager;
import com.example.agathe.tsgtest.AWS.user.IdentityProvider;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IdentityManager.SignInStateChangeListener {

    private Button loginButton;
    private Button logoutButton;
    private Button carpoolingButton;
    private TextView loginField;
    private TextView userName;
    private ImageView userImage;

    /** The identity manager used to keep track of the current user account. */
    private IdentityManager identityManager;

    private Button logoutButtonGoogle;

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    /**
     * Initializes the sign-in and sign-out buttons.
     */
    private void setupButtons() {
        logoutButtonGoogle = (Button) findViewById(R.id.button_signout);
        logoutButtonGoogle.setOnClickListener(this);

        carpoolingButton = (Button) findViewById(R.id.carpooling_but) ;
        carpoolingButton.setOnClickListener(this);

        loginButton = (Button) findViewById(R.id.login_but) ;
        loginButton.setOnClickListener(this);

        logoutButton = (Button) findViewById(R.id.logout_but);
        logoutButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:db5f1441-bab5-4dcc-9082-d5d744e2033e", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        // Initialize the Cognito Sync client
        CognitoSyncManager syncClient = new CognitoSyncManager(
                getApplicationContext(),
                Regions.US_WEST_2, // Region
                credentialsProvider);

        // Recover or create user ID
        settings = getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE);
        editor = settings.edit();
        String userID = settings.getString("userID", "");;
        if (userID.isEmpty()) {
            userID = UUID.randomUUID().toString();
            editor.putString("userID", userID).commit();
        }

        //  Setup layout and its elements
        setContentView(R.layout.activity_main);

        loginField = (TextView) findViewById( R.id.login);
        userName = (TextView) findViewById(R.id.userName);
        userImage = (ImageView) findViewById(R.id.userImage);

        //  Library initialisation is required to be done once before any library function is called.
        //  You use your clientId and secret obtained from SMP website at developer tab.
        SMPLibrary.Initialise(this, "0000", "0000");

        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the mobile client. It is created in the Application class.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();

        setContentView(R.layout.activity_main);
    }

    private void FunctionCalledWhenLoginIsSuccessful() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginField.setText("Logged as: " + SMPLibrary.LoggedUserName() );
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AWSMobileClient.defaultMobileClient().getIdentityManager().isUserSignedIn()) {
            // In the case that the activity is restarted by the OS after the application
            // is killed we must redirect to the splash activity to handle the sign-in flow.
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        setupButtons();

        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        identityManager = awsMobileClient.getIdentityManager();
        identityManager.addSignInStateChangeListener(this);
        fetchUserIdentity();
    }

    @Override
    public void onClick(final View view) {
        if (view == logoutButtonGoogle) {
            // The user is currently signed in with a provider. Sign out of that provider.
            identityManager.signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        if (view == carpoolingButton) {
            Intent intent = new Intent(MainActivity.this, PurposeActivity.class);
            startActivity(intent);
        }

        if (view == loginButton) {
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

        if (view == logoutButton) {
            SMPLibrary.Logout();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loginField.setText("Not Logged In");
                }
            });
        }
    }

    /**
     * Fetches the user identity safely on the background thread.  It may make a network call.
     */
    private void fetchUserIdentity() {
        // Pre-fetched to avoid race condition where fragment is no longer active.
        Log.i("TAG", "fetchUserIdentity");
        final String unknownUserIdentityText =
                getString(R.string.identity_demo_unknown_identity_text);

        AWSMobileClient.defaultMobileClient()
                .getIdentityManager()
                .getUserID(new IdentityManager.IdentityHandler() {

                    @Override
                    public void handleIdentityID(String identityId) {

                        clearUserInfo();

                        if (identityManager.isUserSignedIn()) {

                            userName.setText(identityManager.getUserName());

                            if (identityManager.getUserImage() != null) {
                                userImage.setImageBitmap(identityManager.getUserImage());
                            }
                        }
                    }

                    @Override
                    public void handleError(Exception exception) {

                        clearUserInfo();

                        final Context context = MainActivity.this;

                        if (context != null) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(R.string.identity_demo_error_dialog_title)
                                    .setMessage(getString(R.string.identity_demo_error_message_failed_get_identity)
                                            + exception.getMessage())
                                    .setNegativeButton(R.string.identity_demo_dialog_dismiss_text, null)
                                    .create()
                                    .show();
                        }
                    }
                });
    }

    private void clearUserInfo() {

        clearUserImage();

        try {
            userName.setText(getString(R.string.unknown_user));
        } catch (final IllegalStateException e) {
        }
    }

    private void clearUserImage() {

        try {
            userImage.setImageResource(R.mipmap.user);
        } catch (final IllegalStateException e) {
        }
    }

    @Override
    public void onUserSignedIn() {
        // Update the user identity to account for the user signing in.
        fetchUserIdentity();
        Log.i("TAG", "userSignIn");
    }

    @Override
    public void onUserSignedOut() {
        // Update the user identity to account for the user signing out.
        fetchUserIdentity();
        Log.i("TAG", "userSignOut");
    }
}
