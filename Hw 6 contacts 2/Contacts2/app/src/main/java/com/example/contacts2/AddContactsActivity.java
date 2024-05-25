package com.example.contacts2;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class AddContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontactsactivity);

        EditText editTextName = findViewById(R.id.edittext_name);
        EditText editTextPhone = findViewById(R.id.edittext_Phone);
        EditText editTextEmail = findViewById(R.id.edittext_email);

        Button buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(view -> {
            String name = editTextName.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            // To Create a new Contacts object
            Contacts newContact = new Contacts(name, phone, email);
            // We are using this create an Intent to send the result back to MainActivity
            Intent resultIntent = new Intent();
           // Contacts newContact = new Contacts(name, phone, email);
            resultIntent.putExtra("NEW_CONTACT", newContact);
            setResult(RESULT_OK, resultIntent);
            finish(); // This will Close this activity
        });

    }
}
