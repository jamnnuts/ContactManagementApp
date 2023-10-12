package com.example.contactmanagementapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.contactVH> {
    ArrayList<ContactEntry> contacts;
    ItemClickListener clickListener;
    private contactVH holder;
    private int position;

    public RecyclerViewAdapter(ArrayList<ContactEntry> contacts, ItemClickListener clickListener) {
        this.contacts = contacts;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.contactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_list_item_layout, parent, false);

        return new contactVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.contactVH holder,int position) {
        Bitmap photo = BitmapFactory.decodeFile(contacts.get(position).getPhotoFile());

        holder.contactName.setText(contacts.get(position).getName());
        holder.contactPhoto.setImageBitmap(photo);
        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.OnItemClick(contacts.get(position));
                Log.d("Edit button", "Click pos is:" + position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class contactVH extends RecyclerView.ViewHolder{
        ImageView contactPhoto;
        TextView contactName;
        Button editContact;
        public contactVH(@NonNull View view) {
            super(view);
            contactPhoto = view.findViewById(R.id.contactPicture);
            contactName = view.findViewById(R.id.contactName);
            editContact = view.findViewById(R.id.editContactButton);
        }
    }

    public interface ItemClickListener {
        void OnItemClick(ContactEntry editContact);
    }
}
