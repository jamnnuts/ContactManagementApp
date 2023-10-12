package com.example.contactmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
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

import java.io.File;
import java.util.List;

public class EditContactFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String photoString;
    File photoFile;
    ImageView photoView;
    EditText name;
    EditText phoneNo;
    EditText email;
    ContactEntryDAO contactEntryDAO;

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
    public void onResume() {
        super.onResume();

        ContactsViewModel sessionData = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);

        contactEntryDAO = ContactDBInstance.getDatabase(getContext()).contactEntryDAO();
        ContactEntry editContact = contactEntryDAO.findContactEntry(sessionData.getClickedContact());

        Bitmap photoBitMap = BitmapFactory.decodeFile(editContact.getPhotoFile());

        name.setText(editContact.getName());
        phoneNo.setText(editContact.getPhoneNo());
        email.setText(editContact.getEmail());
        photoView.setImageBitmap(photoBitMap);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_contact,container,false);
        ContactsViewModel sessionData = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);

        ActivityResultLauncher<Intent> photoCaptureLauncher =
                registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        processPhotoResults(data);
                    }
                });

        name = rootView.findViewById(R.id.nameText);
        phoneNo = rootView.findViewById(R.id.phoneNoText);
        email = rootView.findViewById(R.id.emailText);
        photoView = rootView.findViewById(R.id.photoImage);

        Button returnButton = rootView.findViewById(R.id.returnButton);
        Button updateButton = rootView.findViewById(R.id.updateButton);
        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        Button photoCapture = rootView.findViewById(R.id.takePhotoButton);

        contactEntryDAO = ContactDBInstance.getDatabase(getContext()).contactEntryDAO();
        ContactEntry editContact = contactEntryDAO.findContactEntry(sessionData.getClickedContact());
        photoString = editContact.getPhotoFile();
        Bitmap photoBitMap = BitmapFactory.decodeFile(photoString);

        name.setText(editContact.getName());
        phoneNo.setText(editContact.getPhoneNo());
        email.setText(editContact.getEmail());
        photoView.setImageBitmap(photoBitMap);

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
                    contactEntry.setId(editContact.getId());
                    contactEntry.setName(inName);
                    contactEntry.setPhoneNo(inPhoneNo);
                    contactEntry.setEmail(inEmail);

                    if (hasImage(photoView)) {
                        contactEntry.setPhotoFile(photoString);
                        Log.d("Success","it worked."); // Check for Photo entry into database
                    }

                    contactEntryDAO.update(contactEntry);
                    Toast.makeText(getActivity(), "Contact updated. " + contactEntry.getName(), Toast.LENGTH_SHORT).show();


                }
            }
        });

        photoCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = new File(getActivity().getFilesDir(), "photo" + (editContact.getId() - 1) + ".jpg");
                Log.d("Check photoFile string", photoFile.toString());

                Intent intent = new Intent();
                Uri cameraUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName() + ".fileprovider", photoFile);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);

                PackageManager pm = getActivity().getPackageManager();
                for (ResolveInfo a : pm.queryIntentActivities(
                        intent, PackageManager.MATCH_DEFAULT_ONLY)) {

                    getActivity().grantUriPermission(a.activityInfo.packageName, cameraUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                photoCaptureLauncher.launch(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                 Toast.makeText(getActivity(), "Contact Deleted: " + editContact.getName(), Toast.LENGTH_SHORT).show();
                contactEntryDAO.delete(editContact);
                sessionData.setClickedFragment(1);
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionData.setClickedFragment(1);
            }
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

    protected void processPhotoResults(Intent data) {
        Bitmap photo = BitmapFactory.decodeFile(photoFile.toString());
        photoString = photoFile.toString();
        photoView.setImageBitmap(photo);
    }
}