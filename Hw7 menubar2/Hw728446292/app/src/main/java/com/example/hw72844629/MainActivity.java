package com.example.hw72844629;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText urlEditText;
    private ArrayList<String> bookmarks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        urlEditText = findViewById(R.id.urlEditText);

        webView.setWebViewClient(new WebViewClient());

        findViewById(R.id.submit_button).setOnClickListener(view -> webView.loadUrl(urlEditText.getText().toString()));

        findViewById(R.id.bookmark_button).setOnClickListener(view -> {
            String currentUrl = webView.getUrl();
            if (currentUrl != null && !currentUrl.isEmpty()) {
                bookmarks.add(currentUrl);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bookmarks) {
            showBookmarksMenu(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showBookmarksMenu(MenuItem item) {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.action_bookmarks));
        for (int i = 0; i < bookmarks.size(); i++) {
            popupMenu.getMenu().add(Menu.NONE, Menu.FIRST + i, Menu.NONE, bookmarks.get(i));
        }
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            webView.loadUrl(bookmarks.get(menuItem.getItemId() - Menu.FIRST));
            return true;
        });
        popupMenu.show();
    }
}
