package com.example.contactmanagementapp;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactVH extends RecyclerView.ViewHolder{

    public TextView contactName;
    public Button editContact;
    public Button viewContact;
    public Button deleteContact;



    public ContactVH(@NonNull View itemView) {
        super(itemView);
        contactName = itemView.findViewById(R.id.contactName);
        editContact = itemView.findViewById(R.id.editContactButton);
        viewContact = itemView.findViewById(R.id.viewContactButton);
        deleteContact = itemView.findViewById(R.id.deleteContactButton);
    }
}
