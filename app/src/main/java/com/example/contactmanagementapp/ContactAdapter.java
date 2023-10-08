package com.example.contactmanagementapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactVH> {

    List<ContactEntry> contacts;

    public ContactAdapter(ArrayList<ContactEntry> contacts) { this.contacts = contacts;}
    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_list_item_layout,parent,false);
        ContactVH contactVH = new ContactVH(view);
        return contactVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position) {
        ContactEntry singleContact = contacts.get(position);
        holder.contactName.setText(singleContact.getName());
    }

    @Override
    public int getItemCount() {return contacts.size();}
}
