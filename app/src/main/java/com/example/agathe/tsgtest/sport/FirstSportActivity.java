package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.SMPLibrary;

import static android.hardware.Sensor.TYPE_STEP_COUNTER;
import static android.hardware.Sensor.TYPE_STEP_DETECTOR;


/**
 * Created by koudm on 29/11/2016.
 */

public class FirstSportActivity extends AppCompatActivity implements SensorEventListener {
    private Context context;

    private Chronometer chrono;
    private Button start_chrono_btn, stop_chrono_btn, save_performance_btn;
    private TextView steps;
    private ImageView img_feet;
    private ImageButton challenge_btn;

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layout elements initialization
        setContentView(R.layout.first_sport);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_first_sport);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        context = this;
        chrono = (Chronometer) findViewById(R.id.chrono);
        steps= (TextView) findViewById(R.id.stepscounts);
        start_chrono_btn = (Button) findViewById(R.id.btn_start_chrono);
        stop_chrono_btn = (Button) findViewById(R.id.btn_stop_chrono);
        img_feet = (ImageView) findViewById(R.id.img_feet);
        save_performance_btn = (Button) findViewById(R.id.btn_save_perf);
        challenge_btn = (ImageButton) findViewById(R.id.img_btn_challenge);

        //sensors initialization for steps counting
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(TYPE_STEP_DETECTOR);

        //  setup chronometer start button.
        chrono.setFormat("  %s");
        start_chrono_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.start();
            }
        });

        //  setup chronometer stop and restart button.
        stop_chrono_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.stop();

            }
        });
        //  setup save day,steps and chronometer time button.
       save_performance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save
                //and reset the chronometer
                chrono.setBase(SystemClock.elapsedRealtime());
            }
        });
        img_feet.setImageResource(R.drawable.sport_steps);
        challenge_btn.setImageResource(R.drawable.sport_challenge);

        //  setup challenge image button.
        challenge_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstSportActivity.this,
                        ChallengeActivity.class);
                startActivity(intent);
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
            case R.id.action_disconnect:
                SMPLibrary.Logout();
                return true;

            case R.id.action_about:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                builder.setPositiveButton(R.string.dialog_ok, null);
                builder.setIcon(R.mipmap.ic_launcher);

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_main_settings:
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
        mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }
        while (chrono.isActivated()) {
            if (sensor.getType() == TYPE_STEP_COUNTER) {
                steps.setText("You did " + value + "steps !");
            } /**else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                // For test only. Only allowed value is 1.0 i.e. for step taken
                steps.setText("Step Detector Detected : " + value);
            }**/
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onStop() {
        super.onStop();
      /**  mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
        **/
    }
}