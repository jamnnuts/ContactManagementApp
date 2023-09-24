package com.example.contactmanagementapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
//Todo: firstName and lastName primary key? Or is the phone number?
@Entity(primaryKeys = {"firstName","lastName"},tableName = "contacts")
public class ContactEntry {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private int phoneNo;
    private String email;
    @Ignore
    private Bitmap photo;

    public Bitmap getPhoto() {return photo;}

    public void setPhoto(Bitmap photo) {this.photo = photo;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public int getPhoneNo() {return phoneNo;}

    public void setPhoneNo(int phoneNo) {this.phoneNo = phoneNo;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}
}
