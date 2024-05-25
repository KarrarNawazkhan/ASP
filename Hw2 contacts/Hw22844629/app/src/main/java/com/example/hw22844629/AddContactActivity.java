package com.example.hw22844629;

import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        EditText editTextName = findViewById(R.id.edittext_name);
        EditText editTextPhone = findViewById(R.id.edittext_Phone);
        EditText editTextEmail = findViewById(R.id.edittext_email);

        Button buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(view -> {

            String name = editTextName.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();

            Contacts newContact = new Contacts(name, phone, email);
            // Create an Intent to send the result back to MainActivity
            Intent resultIntent = new Intent();

            resultIntent.putExtra("CONTACT_NAME", newContact.getName());
            resultIntent.putExtra("CONTACT_PHONE", newContact.getPhoneNumber());
            resultIntent.putExtra("CONTACT_EMAIL", newContact.getEmail());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
