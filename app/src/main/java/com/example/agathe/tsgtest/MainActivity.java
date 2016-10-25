package com.example.agathe.tsgtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.olab.smplibrary.DataResponseCallback;
import com.olab.smplibrary.LoginResponseCallback;
import com.olab.smplibrary.SMPLibrary;

public class MainActivity extends AppCompatActivity {


    Button login_button, logout_button, contacts_button, business_button, private_button;
    TextView login_field, contents;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.activity_main);
        context = this;
        login_button = (Button) findViewById( R.id.login_but) ;
        logout_button = (Button) findViewById( R.id.logout_but);
        contacts_button = (Button) findViewById( R.id.getcontcts);
        business_button = (Button) findViewById( R.id.getbuisness);
        private_button = (Button) findViewById( R.id.getprivate);
        login_field = (TextView) findViewById( R.id.login);
        contents = (TextView) findViewById( R.id.contents);
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

        //  setup business contacts button
        business_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  create new request for business contacts.
                SMPLibrary.GetBusinessContacts(context, 10, new DataResponseCallback(){
                    @Override
                    //  OnResponse callback is called when request is finished processing
                    //  returns response code and JSON in form of string.
                    public void OnResponse( int response_code, String data_response ){
                        Log.i("MainActivity:Response", "GetBusinessContacts response code " + response_code );
                        Log.i("MainActivity:Response", "GetBusinessContacts - " + data_response);
                        ShowMessage("Business Contacts\n" + data_response);
                    }
                });
            }
        });

        //  //  setup private contacts button
        private_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  create new request for private contacts.
                SMPLibrary.GetPrivateContacts(context, 10, new DataResponseCallback(){
                    @Override
                    //  OnResponse callback is called when request is finished processing
                    //  returns response code and JSON in form of string.
                    public void OnResponse( int response_code, String data_response ){
                        Log.i("MainActivity:Response", "GetPrivateContacts response code" + response_code );
                        Log.i("MainActivity:Response", "GetPrivateContacts - " + data_response);
                        ShowMessage("Private Contacts\n" + data_response);
                    }
                });
            }
        });

        //  setup frequent contacts button
        contacts_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  create new request for frequent contacts.
                SMPLibrary.GetFrequentContacts(context, 10, new DataResponseCallback(){
                    @Override
                    //  OnResponse callback is called when request is finished processing
                    //  returns response code and JSON in form of string.
                    public void OnResponse( int response_code, String data_response ){
                        Log.i("MainActivity:Response", "GetFrequentContacts response code " + response_code );
                        Log.i("MainActivity:Response", "GetFrequentContacts - " + data_response);
                        ShowMessage("Frequent Contacts\n" + data_response);
                    }
                });
            }
        });
    }

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
                login_field.setText("Logged as: " + SMPLibrary.LoggedUserName() );
            }
        });
    }




}
