package com.example.contactmanagementapp;

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
    public ContactInterface cListener;

    public ContactVH(@NonNull View itemView, ContactInterface cListener) {
        super(itemView);
        this.cListener = cListener;
        contactName = itemView.findViewById(R.id.contactName);
        editContact = itemView.findViewById(R.id.editContactButton);
        viewContact = itemView.findViewById(R.id.viewContactButton);
        deleteContact = itemView.findViewById(R.id.deleteContactButton);

        editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cListener.editContact(contactName.getText().toString());
            }
        });
    }
}
