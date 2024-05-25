package Hw12.ps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private TextView proximityValue;
    private RelativeLayout layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make sure the IDs here match those in your activity_main.xml
        proximityValue = findViewById(R.id.proximityValue);
        layout = findViewById(R.id.layout); // Corrected ID reference

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            proximityValue.setText("Proximity Sensor Not Available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (proximitySensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            updateBackgroundColor(distance);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    private void updateBackgroundColor(float distance) {
        int backgroundColor = Color.GREEN;

        if (distance <= 0.9) {
            backgroundColor = getResources().getColor(R.color.brown);
        }
        if (distance <= 0.7) {
            backgroundColor = getResources().getColor(R.color.red);
        }
        if (distance <= 0.5) {
            backgroundColor = getResources().getColor(R.color.pink);
        }
        if (distance <= 0.3) {
            backgroundColor = getResources().getColor(R.color.yellow);
        }
        if (distance <= 0.1) {
            backgroundColor = getResources().getColor(R.color.blue);
        }

        layout.setBackgroundColor(backgroundColor);
        proximityValue.setText("Proximity Value: " + distance);
    }
}
