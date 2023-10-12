package com.example.contactmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ContactListFragment contactListFragment = new ContactListFragment();
    AddContactFragment addContactFragment = new AddContactFragment();
    EditContactFragment editContactFragment = new EditContactFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactsViewModel contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        loadContactList();
        contactsViewModel.clickedFragment.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (contactsViewModel.getClickedFragment() == 1) {
                    loadContactList();
                }
                if (contactsViewModel.getClickedFragment() == 2) {
                    loadAddContact();
                }
                if (contactsViewModel.getClickedFragment() == 3) {
                    loadEditContact();
                }

            }
        });
    }

    public void loadContactList() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerMain);

        if (frag == null) {
            fm.beginTransaction().add(R.id.fragmentContainerMain, contactListFragment).commit();
        }
        else {
            fm.beginTransaction().replace(R.id.fragmentContainerMain, contactListFragment).commit();
        }
    }
    public void loadAddContact() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerMain);

        if (frag == null) {
            fm.beginTransaction().add(R.id.fragmentContainerMain, addContactFragment).commit();
        }
        else {
            fm.beginTransaction().replace(R.id.fragmentContainerMain, addContactFragment).commit();
        }
    }

    public void loadEditContact() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerMain);

        if (frag == null) {
            fm.beginTransaction().add(R.id.fragmentContainerMain, editContactFragment).commit();
        }
        else {
            fm.beginTransaction().replace(R.id.fragmentContainerMain, editContactFragment).commit();
        }
    }
}