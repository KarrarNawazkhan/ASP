package com.example.hw8_2844629;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private ListView versesListView;
    private ArrayAdapter<String> adapter;
    private List<String> versesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);
        versesListView = findViewById(R.id.versesListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, versesList);
        versesListView.setAdapter(adapter);

        new DownloadTextTask().execute("https://jsonplaceholder.typicode.com/posts");
        new DownloadImageTask().execute("https://upload.wikimedia.org/wikipedia/commons/0/04/New_York_Empire_Apples.jpg");
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadText(urls[0]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Downloadtext task", "Result"+result);
            if (result != null) {
                parseAndDisplayVerses(result);
            }
        }

        private String downloadText(String urlString) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(urlString);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                }
                httpConn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.toString();
        }

        private void parseAndDisplayVerses(String jsonString) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                versesList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    versesList.add(title);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                return downloadImage(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                img.setImageBitmap(result);
            } else {
                Log.e("Download Image task", "failded to doenload image ");
            }
            }
        }

        private Bitmap downloadImage(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            try (InputStream input = connection.getInputStream()) {
                return BitmapFactory.decodeStream(input);
            } finally {
                connection.disconnect();
            }
        }
    }

