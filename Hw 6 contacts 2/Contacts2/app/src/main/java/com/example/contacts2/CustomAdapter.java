package com.example.contacts2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
public class CustomAdapter extends ArrayAdapter<Contacts> {
    public CustomAdapter(Context context, ArrayList<Contacts> contacts) {
        super(context, 0, contacts);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contacts contact = getItem(position);
        // This will Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
        }
        TextView tvContactName = convertView.findViewById(R.id.tvContactName);
        if (tvContactName !=null){

        tvContactName.setText(contact.getName());}
        else

        {//Log.e("CustomAdapter", "textview not found in contact item layout ");
           }

        TextView tvContactPhone = convertView.findViewById(R.id.tvContactPhone);
         tvContactPhone.setText(contact.getPhoneNumber());
         TextView tvContactEmail = convertView.findViewById(R.id.tvContactEmail);
         tvContactEmail.setText(contact.getEmail());

        // Return the completed view to render on screen
        return convertView;
    }
}
