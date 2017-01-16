package com.example.agathe.tsgtest;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.signin.SignInManager;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobile.user.IdentityProvider;
import com.amazonaws.mobile.user.signin.GoogleSignInProvider;

public class SignInSMP extends Activity {

    Button loginSMP_but;
    Context context;
    TextView login_field ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.activity_signinsmp);
        context = this;
        loginSMP_but = (Button) findViewById( R.id.loginSMP_but ) ;
        login_field = (TextView) findViewById( R.id.loginSMP_but ) ;

        SMPLibrary.Initialise(this, "vincent.decomble@isen.yncrea.fr", "ISENproject59@");
        startActivity(new Intent(SignInSMP.this, MainActivity.class)) ;
        loginSMP_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  when clicked show dialog where user can login.
                SMPLibrary.ShowLoginDialog(SignInSMP.this, new LoginResponseCallback() {
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
    }

        void FunctionCalledWhenLoginIsSuccessful() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    login_field.setText("Logged as: " + SMPLibrary.LoggedUserName() );
                }
            });
        }
}
