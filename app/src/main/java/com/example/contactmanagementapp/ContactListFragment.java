package com.example.contactmanagementapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/** This Fragment displays all contact list information. It acts as the main fragment that other functions are utilised through. */
public class ContactListFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener{

    Button addContactButton;
    Button importContactButton;
    ArrayList<ContactEntry> contacts;
    ContactsViewModel sessionData;
    ContactEntry importedEntry = new ContactEntry();
    int contactId;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;
    ContactEntryDAO contactEntryDAO;

    ActivityResultLauncher<Intent> importContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    processImportContactResult(data);
                }
            });

    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment newInstance(String param1, String param2) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sessionData = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        addContactButton = rootView.findViewById(R.id.addContact);
        importContactButton = rootView.findViewById(R.id.importContacts);

        contactEntryDAO = ContactDBInstance.getDatabase(getContext()).contactEntryDAO();
        contacts = (ArrayList<ContactEntry>) contactEntryDAO.getAllContacts();

        RecyclerView rv = rootView.findViewById(R.id.contactListRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(contacts,this);
        rv.setAdapter(adapter);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionData.setClickedFragment(2);
            }
        });

        importContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                importContactLauncher.launch(intent);
            }
        });

        return rootView;
    }

    @Override
    public void OnItemClick(ContactEntry editContact) {
        sessionData.setClickedContact(editContact.getName());
        sessionData.setClickedFragment(3);
    }

    private void processImportContactResult(Intent data) {
        Uri contactUri = data.getData();
        String[] queryFields = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor c = getContext().getContentResolver().query(
                contactUri, queryFields, null, null, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                this.contactId = c.getInt(0); // ID first
                importedEntry.setName( c.getString(1)); // Name second
            }
        }
        finally {
            c.close();
        }
        if(ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACT_PERMISSION);
        }
        else {
            getEmail();
        }
    }

    private void getEmail() {
        String result="";
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.CommonDataKinds.Email.ADDRESS
        };

        String whereClause = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(this.contactId)
        };
        Cursor c = getActivity().getContentResolver().query(
                emailUri, queryFields, whereClause,whereValues, null);
        try{
            c.moveToFirst();
            do{
                String emailAddress = c.getString(0);
                result = result+emailAddress+" ";
            }
            while (c.moveToNext());

        }
        finally {
            c.close();
        }
        importedEntry.setEmail(result);

        getPhoneNumber();
    }

    private void getPhoneNumber() {
        String result="";
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(this.contactId)
        };
        Cursor c = getActivity().getContentResolver().query(
                phoneUri, queryFields, whereClause,whereValues, null);
        try{
            c.moveToFirst();
            do{
                String phoneNumber = c.getString(0);
                result = result+phoneNumber+" ";
            }
            while (c.moveToNext());

        }
        finally {
            c.close();
        }
        importedEntry.setPhoneNo(result);

        contactEntryDAO.insert(importedEntry);
    }
}