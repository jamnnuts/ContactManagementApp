package com.example.contactmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class AddContactFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ImageView photo;

    ActivityResultLauncher<Intent> photoCaptureLauncher =
            registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    if (image != null) {
                        photo.setImageBitmap(image);
                    }
                }
            });

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

        EditText name = rootView.findViewById(R.id.nameText);
        EditText phoneNo = rootView.findViewById(R.id.phoneNoText);
        EditText email = rootView.findViewById(R.id.emailText);
        photo = rootView.findViewById(R.id.photoImage);
        Button returnButton = rootView.findViewById(R.id.returnButton);
        Button saveButton = rootView.findViewById(R.id.saveButton);
        Button photoCapture = rootView.findViewById(R.id.takePhotoButton);

        ContactEntryDAO contactEntryDAO = ContactDBInstance.getDatabase(getContext()).contactEntryDAO();



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if((name.getText().toString() == "") || (phoneNo.getText().toString() == "") || (email.getText().toString() == "")) {
                Toast.makeText(getActivity(), "All boxes require Input", Toast.LENGTH_SHORT).show();
            }
            else if (contactEntryDAO.containsPrimaryKey(name.getText().toString())) {
                Toast.makeText(getActivity(), "There is already a contact with the same first name and last name.", Toast.LENGTH_SHORT).show();
            }
            else {
                String inName = name.getText().toString();
                String inPhoneNo = phoneNo.getText().toString();
                String inEmail = email.getText().toString();

                ContactEntry contactEntry = new ContactEntry();
                contactEntry.setName(inName);
                contactEntry.setPhoneNo(inPhoneNo);
                contactEntry.setEmail(inEmail);

                contactEntryDAO.insert(contactEntry);

                Toast.makeText(getActivity(), "Contact Created. ", Toast.LENGTH_SHORT).show();

                // Previous fault of same primary key crashing should be solved with the new query
            }
        }
      });

        photoCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                photoCaptureLauncher.launch(intent);
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {sessionData.setClickedFragment(1);}
        });

        return rootView;
    }
}