package com.example.hw8c2844629;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ContactDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactdetailsactivity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // This will Retrieve the contact object passed from MainActivity
        Contacts contact = (Contacts) getIntent().getSerializableExtra("CONTACT");
        // This will Find the TextViews in our layout
        TextView tvName = findViewById(R.id.textview_name);
        TextView tvPhone = findViewById(R.id.textview_phone);
        TextView tvEmail = findViewById(R.id.textview_email);
        // This Sets the text of the TextViews to the contact information
        if (contact != null) {
            tvName.setText("Name: " + contact.getName());
            tvPhone.setText("Phone: " + contact.getPhoneNumber());
            tvEmail.setText("Email: " + contact.getEmail());
        }
        // Back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //  this will Close this activity and return to the previous one
            }
        });
    }
}
