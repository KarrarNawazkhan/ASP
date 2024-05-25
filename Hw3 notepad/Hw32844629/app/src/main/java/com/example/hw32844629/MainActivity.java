package com.example.hw32844629;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements BottomFragment.OnKeyInteractionListener {
    private TopFragment topFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This initializes the fragments
        topFragment = new TopFragment();
        BottomFragment bottomFragment = new BottomFragment();
        // This one begins the fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // This replaces the placeholders with the actual fragments
        transaction.replace(R.id.topFragmentContainer, topFragment);
        transaction.replace(R.id.bottomFragmentContainer, bottomFragment);
        transaction.commit();
    }
    // To implement the interface method from BottomFragment
    @Override
    public void onKeyEntered(String key) {
        Log.d("MainActivity", "Key entered: " + key);
        if (topFragment != null) {
            topFragment.appendText(key);
        }
    }
}
