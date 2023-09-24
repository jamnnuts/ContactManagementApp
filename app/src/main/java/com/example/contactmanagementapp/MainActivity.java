package com.example.contactmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ContactListFragment contactListFragment = new ContactListFragment();
    AddContactFragment addContactFragment = new AddContactFragment();

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
}