package com.example.hw11sensors;


import static com.example.hw11sensors.R.id.sensorData;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView sensorDataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorDataTextView = findViewById(sensorData);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        int[] sensorTypes = {Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LIGHT};

        StringBuilder sensorInfo = new StringBuilder();
        for (int sensorType : sensorTypes) {
            Sensor sensor = sensorManager.getDefaultSensor(sensorType);
            if (sensor != null) {
                sensorInfo.append("Sensor Name: ").append(sensor.getName()).append("\n");
                sensorInfo.append("Sensor Type: ").append(sensor.getStringType()).append("\n");
                sensorInfo.append("Vendor: ").append(sensor.getVendor()).append("\n");
                sensorInfo.append("Version: ").append(sensor.getVersion()).append("\n");
                sensorInfo.append("Power: ").append(sensor.getPower()).append(" mA\n");
                sensorInfo.append("Resolution: ").append(sensor.getResolution()).append("\n");
                sensorInfo.append("Maximum Range: ").append(sensor.getMaximumRange()).append("\n\n");
            } else {
                sensorInfo.append("Sensor Type ").append(sensorType).append(" not found.\n\n");
            }
        }

        sensorDataTextView.setText(sensorInfo.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
