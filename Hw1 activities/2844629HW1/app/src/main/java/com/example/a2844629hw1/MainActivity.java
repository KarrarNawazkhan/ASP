package com.example.a2844629hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.TextView;

import android.content.Intent;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

        private int iterationCount = 0;
        private TextView iterationTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            iterationTextView = findViewById(R.id.iteration_count);
            updateIterationCount();
        }

        @Override
        protected void onResume() {
            super.onResume();
            iterationCount++;
            updateIterationCount();
        }

        private void updateIterationCount() {
            iterationTextView.setText("Iteration count: " + iterationCount);
        }

        public void launchSecondActivity(View view) {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }
