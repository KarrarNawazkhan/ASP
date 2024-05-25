package com.example.hw17sd;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private Handler handler = new Handler();
    private LineGraphSeries<DataPoint> series;
    private GraphView graph;
    private int lastX = 0;
    private TextView decibelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graph = findViewById(R.id.graph);
        series = new LineGraphSeries<>();
        graph.addSeries(series);
        decibelText = findViewById(R.id.decibel_text);

        configureGraph();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        } else {
            startRecording();
        }
    }
    private void configureGraph() {
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1000); //This adjusts maximum Y value

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(50);
    }
    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String outputPath = getExternalCacheDir().getAbsolutePath() + "/test.3gp";
        mediaRecorder.setOutputFile(outputPath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            updateGraph();
        } catch (IOException e) {
            Log.e("MainActivity", "IO Exception in mediaRecorder.prepare()", e);
            Toast.makeText(this, "MediaRecorder prepare failed", Toast.LENGTH_SHORT).show();
        } catch (RuntimeException e) {
            Log.e("MainActivity", "Runtime Exception in starting mediaRecorder", e);
            Toast.makeText(this, "MediaRecorder start failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void updateGraph() {
        if (!isRecording) return;

        int amplitude = mediaRecorder.getMaxAmplitude();
        series.appendData(new DataPoint(lastX++, amplitude), true, 100);
        updateDecibelText(amplitude);
        handler.postDelayed(this::updateGraph, 500);
    }

    private void updateDecibelText(int amplitude) {
        double amplitudeDb = 20 * Math.log10((double)Math.abs(amplitude));
        decibelText.setText(String.format("Amplitude (dB): %.2f", amplitudeDb));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRecording) {
            stopRecording();
        }
    }
}
