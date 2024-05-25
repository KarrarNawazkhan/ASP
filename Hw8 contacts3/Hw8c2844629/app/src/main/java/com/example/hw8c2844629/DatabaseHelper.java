package com.example.hw8c2844629;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactsDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns
    private static final String KEY_CONTACT_ID = "id";
    private static final String KEY_CONTACT_NAME = "name";
    private static final String KEY_CONTACT_PHONE = "phone";
    private static final String KEY_CONTACT_EMAIL = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY," + //This defines a primary key
                KEY_CONTACT_NAME + " TEXT," +
                KEY_CONTACT_PHONE + " TEXT," +
                KEY_CONTACT_EMAIL + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }
    //This to insert a contact into the database
    public void addContact(Contacts contact) {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_NAME, contact.getName());
        values.put(KEY_CONTACT_PHONE, contact.getPhoneNumber());
        values.put(KEY_CONTACT_EMAIL, contact.getEmail());

        database.insert(TABLE_CONTACTS, null, values);
        database.close();
    }
    // This is to Fetch all contacts from the database
    @SuppressLint("Range")
    public ArrayList<Contacts> getAllContacts() {
        ArrayList<Contacts> contactsArrayList = new ArrayList<>();
         SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_CONTACTS, new String[]{KEY_CONTACT_ID, KEY_CONTACT_NAME, KEY_CONTACT_PHONE, KEY_CONTACT_EMAIL}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_PHONE));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_EMAIL));

                Contacts contact = new Contacts(name, phone, email);
                contact.setId(cursor.getInt(cursor.getColumnIndex(KEY_CONTACT_ID)));
                contactsArrayList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return contactsArrayList;
    }
    public void deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }
}
