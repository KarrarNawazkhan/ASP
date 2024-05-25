package com.example.hw22844629;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.widget.Button;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    // Defines ListView and ArrayAdapter to manage contact display
    ListView listViewContacts;
    ArrayAdapter<String> adapter;
    ArrayList<String> contacts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewContacts = findViewById(R.id.listview_contacts);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listViewContacts.setAdapter(adapter);

        Button buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, 1);
        });
    }
    // Handle the result from AddContactActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String contactName = data.getStringExtra("CONTACT_NAME");
            String contactPhone = data.getStringExtra("CONTACT_PHONE");
            String contactEmail = data.getStringExtra("CONTACT_EMAIL");

            if (contactName != null && contactPhone != null && contactEmail != null) {
                contacts.add(contactName + " - " + contactPhone + " - " + contactEmail);
                adapter.notifyDataSetChanged();
            }
        }
    }

}

