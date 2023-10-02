package com.example.contactmanagementapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(primaryKeys = {"name"},tableName = "contacts")
public class ContactEntry {
    @NonNull
    private String name;
    private String phoneNo;
    private String email;
    @Ignore
    private Bitmap photo;

    public Bitmap getPhoto() {return photo;}

    public void setPhoto(Bitmap photo) {this.photo = photo;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhoneNo() {return phoneNo;}

    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}
