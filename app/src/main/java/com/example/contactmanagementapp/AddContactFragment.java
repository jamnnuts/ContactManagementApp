package com.example.contactmanagementapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddContactFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddContactFragment() {
        // Required empty public constructor
    }

    public static AddContactFragment newInstance(String param1, String param2) {
        AddContactFragment fragment = new AddContactFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_contact,container,false);
        ContactsViewModel sessionData = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);

        EditText firstName = rootView.findViewById(R.id.firstNameText);
        EditText lastName = rootView.findViewById(R.id.lastNameText);
        EditText phoneNo = rootView.findViewById(R.id.phoneNoText);
        EditText email = rootView.findViewById(R.id.emailText);
        ImageView photo = rootView.findViewById(R.id.photoImage);
        Button returnButton = rootView.findViewById(R.id.returnButton);
        Button saveButton = rootView.findViewById(R.id.saveButton);

        ContactEntryDAO contactEntryDAO = ContactDBInstance.getDatabase(getContext()).contactEntryDAO();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if((firstName.getText().toString() == "") || (lastName.getText().toString()=="")) {
                Toast.makeText(getActivity(), "First name and Last name are required", Toast.LENGTH_SHORT).show();
            }
            else {
                String inFirstName = firstName.getText().toString();
                String inLastName = lastName.getText().toString();
                long inPhoneNo = Long.parseLong(phoneNo.getText().toString());
                String inEmail = email.getText().toString();

                ContactEntry contactEntry = new ContactEntry();
                contactEntry.setFirstName(inFirstName);
                contactEntry.setLastName(inLastName);
                contactEntry.setPhoneNo(inPhoneNo);
                contactEntry.setEmail(inEmail);

                contactEntryDAO.insert(contactEntry);

                //Todo App crashes if you try put in a name already in the DB (Primary key conflict)
                // Need to add some kind of check wrapper around this

            }
        }
      });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {sessionData.setClickedFragment(1);}
        });

        return rootView;
    }
}