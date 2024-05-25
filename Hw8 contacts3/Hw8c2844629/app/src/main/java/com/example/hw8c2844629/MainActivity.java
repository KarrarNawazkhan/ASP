package com.example.hw8c2844629;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewContacts;
    CustomAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        listViewContacts = findViewById(R.id.listview_contacts);
        ArrayList<Contacts> contacts = databaseHelper.getAllContacts();
        adapter = new CustomAdapter(this, contacts);
        listViewContacts.setAdapter(adapter);

        listViewContacts.setOnItemClickListener((parent, view, position, id) -> {
            Contacts selectedContact = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ContactDetailsActivity.class);
            intent.putExtra("CONTACT", selectedContact);
            startActivity(intent);
        });
        //add button
        Button buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddContactsActivity.class);
            startActivityForResult(intent, 1);
        });
        //Delete Button
        Button buttonDeleteAll = findViewById(R.id.button_delete_all);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteAllContacts();
                updateContactList();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Contacts newContact = (Contacts) data.getSerializableExtra("NEW_CONTACT");
            databaseHelper.addContact(newContact);
            updateContactList();
        }
    }

    private void updateContactList() {
        ArrayList<Contacts> contacts = databaseHelper.getAllContacts();
        adapter.clear();
        adapter.addAll(contacts);
        adapter.notifyDataSetChanged();
    }
}
