package com.example.agathe.tsgtest.sport;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_STEP_COUNTER;
import static android.hardware.Sensor.TYPE_STEP_DETECTOR;

/**
 * Created by koudm on 31/01/2017.
 */

public class RunningFragment extends Fragment implements SensorEventListener {
    private Chronometer chrono;
    private Button start_chrono_btn, stop_chrono_btn, save_performance_btn;
    private TextView steps;
    private ImageView img_feet;

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    public RunningFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //sensors initialization for steps counting
        mSensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(TYPE_STEP_DETECTOR);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_running, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chrono = (Chronometer) view.findViewById(R.id.chrono);
        steps= (TextView) view.findViewById(R.id.stepscounts);
        start_chrono_btn = (Button) view.findViewById(R.id.btn_start_chrono);
        stop_chrono_btn = (Button) view.findViewById(R.id.btn_stop_chrono);
        img_feet = (ImageView) view.findViewById(R.id.img_feet);
        save_performance_btn = (Button) view.findViewById(R.id.btn_save_perf);

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

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

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

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onStop() {
        super.onStop();
        /**  mSensorManager.unregisterListener(this, mStepCounterSensor);
         mSensorManager.unregisterListener(this, mStepDetectorSensor);
         **/
    }
}
