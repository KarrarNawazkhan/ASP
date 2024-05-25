package com.example.hw10_myservice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This will initialize the ProgressBar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
    }
    @Override
    public void onResume() {
        super.onResume();
        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");
        intentFilter.addAction("FILE_DOWNLOAD_PROGRESS");
        int FlAG_RECEIVER_NOT_EXPORTED =0X00000002;

        @SuppressLint("WrongConstant") Intent intent = registerReceiver(intentReceiver, intentFilter, FlAG_RECEIVER_NOT_EXPORTED);
        // context.registerReceiver(broadcastReceiver, intentFilter, RECEIVER_EXPORTED);

    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
    }
    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("FILE_DOWNLOAD_PROGRESS".equals(intent.getAction())) {
                int progress = intent.getIntExtra("progress", 0);
                progressBar.setProgress(progress);
            } else if ("FILE_DOWNLOADED_ACTION".equals(intent.getAction())) {
                Toast.makeText(getBaseContext(), "File downloaded!", Toast.LENGTH_LONG).show();
                Log.i("IntentService", "received broadcast");
            }
        }
    };
}
