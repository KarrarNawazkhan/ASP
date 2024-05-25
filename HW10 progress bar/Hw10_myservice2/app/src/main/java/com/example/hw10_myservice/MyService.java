package com.example.hw10_myservice;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends IntentService {
    public MyService() {
        super("MyServiceName");
    }
     @Override
    protected void onHandleIntent(Intent intent) {
        try {
            int result = DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            Log.d("IntentService", "Downloaded " + result + " bytes");

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("FILE_DOWNLOADED_ACTION");
            getBaseContext().sendBroadcast(broadcastIntent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private int DownloadFile(URL url) {
        try {
            for (int i = 0; i <= 100; i += 10) {
                Thread.sleep(1000);

                //This will send progress update broadcast
                Intent progressIntent = new Intent();
                progressIntent.setAction("FILE_DOWNLOAD_PROGRESS");
                progressIntent.putExtra("progress", i);
                getBaseContext().sendBroadcast(progressIntent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100; // This will Return the total bytes downloaded
    }
}
