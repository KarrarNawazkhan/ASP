package com.example.contacts2;

//28446289 karrar nawaz khan

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ListView listViewContacts;
    CustomAdapter adapter;  // This is our custom adapter
    ArrayList<Contacts> contacts = new ArrayList<Contacts>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewContacts = findViewById(R.id.listview_contacts);
        adapter = new CustomAdapter(this, contacts);  //  This will Initialize our custom adapter
        listViewContacts.setAdapter(adapter);

        listViewContacts.setOnItemClickListener((parent, view, position, id) -> {
            Contacts selectedContact = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ContactDetailsActivity.class);
            intent.putExtra("CONTACT", selectedContact);  //
            startActivity(intent);
        });

        Button buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddContactsActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Contacts newContact = (Contacts) data.getSerializableExtra("NEW_CONTACT");
            contacts.add(newContact);
            adapter.notifyDataSetChanged();
        }
    }
}
