package com.example.contactmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class EditContactFragment extends Fragment {
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

    public EditContactFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_edit_contact,container,false);
        ContactsViewModel sessionData = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);

        EditText name = rootView.findViewById(R.id.nameText);
        EditText phoneNo = rootView.findViewById(R.id.phoneNoText);
        EditText email = rootView.findViewById(R.id.emailText);
        photo = rootView.findViewById(R.id.photoImage);
        Button returnButton = rootView.findViewById(R.id.returnButton);
        Button updateButton = rootView.findViewById(R.id.updateButton);
        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        Button photoCapture = rootView.findViewById(R.id.takePhotoButton);

        ContactEntryDAO contactEntryDAO = ContactDBInstance.getDatabase(getContext()).contactEntryDAO();
        ContactEntry editContact = contactEntryDAO.findContactEntry(sessionData.clickedContactName.getValue().toString());

        name.setText(editContact.getName());
        phoneNo.setText(editContact.getPhoneNo());
        email.setText(editContact.getEmail());
        photo.setImageBitmap(editContact.getPhoto());
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((name.getText().toString() == "") || (phoneNo.getText().toString() == "") || (email.getText().toString() == "")) {
                    Toast.makeText(getActivity(), "All boxes require Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    String inName = name.getText().toString();
                    String inPhoneNo = phoneNo.getText().toString();
                    String inEmail = email.getText().toString();

                    ContactEntry contactEntry = new ContactEntry();
                    contactEntry.setName(inName);
                    contactEntry.setPhoneNo(inPhoneNo);
                    contactEntry.setEmail(inEmail);

                    if (hasImage(photo)) {
                        contactEntry.setPhoto(((BitmapDrawable)photo.getDrawable()).getBitmap());
                        Log.d("Success","it worked."); // Check for Photo entry into database
                    }
                    contactEntryDAO.update(contactEntry);

                    Toast.makeText(getActivity(), "Contact updated. ", Toast.LENGTH_SHORT).show();

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

        deleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                 Toast.makeText(getActivity(), "Contact Deleted: " + editContact.getName().toString(), Toast.LENGTH_SHORT).show();
                contactEntryDAO.delete(editContact);
                sessionData.setClickedFragment(1);
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {sessionData.setClickedFragment(1);}
        });

        return rootView;
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;

    }
}