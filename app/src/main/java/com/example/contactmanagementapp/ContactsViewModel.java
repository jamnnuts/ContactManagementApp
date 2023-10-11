package com.example.contactmanagementapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactsViewModel extends ViewModel {
    public MutableLiveData<Integer> clickedFragment;
    public MutableLiveData<String> clickedContactName;

    public ContactsViewModel() {
        clickedFragment = new MutableLiveData<Integer>();
        clickedContactName = new MutableLiveData<String>();

        clickedFragment.setValue(0);
    }
    public String getClickedContact() {return clickedContactName.getValue();}
    public void setClickedContact(String inContactName) {clickedContactName.setValue(inContactName);}
    public int getClickedFragment() { return clickedFragment.getValue();}
    public void setClickedFragment(int value) { clickedFragment.setValue(value);}

}
