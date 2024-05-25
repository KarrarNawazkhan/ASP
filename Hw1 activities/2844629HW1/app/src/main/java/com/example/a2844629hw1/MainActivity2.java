package com.example.a2844629hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;



import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void closeActivity(View view) {
        finish();
    }
}