package com.example.contactmanagementapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactsViewModel extends ViewModel {
    public MutableLiveData<Integer> clickedFragment;

    public ContactsViewModel() {
        clickedFragment = new MutableLiveData<Integer>();

        clickedFragment.setValue(0);
    }

    public int getClickedFragment() { return clickedFragment.getValue();}
    public void setClickedFragment(int value) { clickedFragment.setValue(value);}

}
