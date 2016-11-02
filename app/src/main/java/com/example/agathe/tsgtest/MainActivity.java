package com.example.agathe.tsgtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

public class MainActivity extends AppCompatActivity {

    Button login_button, logout_button, carpooling_button;
    TextView login_field;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.activity_main);
        context = this;
        login_button = (Button) findViewById(R.id.login_but) ;
        logout_button = (Button) findViewById(R.id.logout_but);
        carpooling_button = (Button) findViewById(R.id.carpooling_but) ;
        login_field = (TextView) findViewById( R.id.login);
        //  Library initialisation is required to be done once before any library function is called.
        //  You use your clientId and secret obtained from SMP website at developer tab.
        SMPLibrary.Initialise(this, "0000", "0000");

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

        //  setup listener for login button
        carpooling_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ServerMainActivity.class);
                startActivity(intent);
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
