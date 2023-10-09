package com.example.contactmanagementapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactVH> {

    ArrayList<ContactEntry> contacts;

    ContactsViewModel sessionData;

    public ContactAdapter(ArrayList<ContactEntry> contacts, ContactsViewModel contactsViewModel) {
        this.contacts = contacts;
        sessionData = contactsViewModel;
    }

    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_list_item_layout, parent, false);
        ContactVH contactVH = new ContactVH(view);
        return contactVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position) {

        ContactEntry singleContact = contacts.get(position);
        holder.contactName.setText(singleContact.getName());
        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionData.setClickedContact(holder.contactName.getText().toString());
                sessionData.setClickedFragment(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
