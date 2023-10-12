package com.example.contactmanagementapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity(tableName = "contacts")
public class ContactEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    private String phoneNo;
    private String email;
    private String photoFile;

    public int getId(){return id;}
    public void setId(int id) {this.id = id;}

    public String getPhotoFile() {return photoFile;}

    public void setPhotoFile(String photoFile) {this.photoFile = photoFile;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhoneNo() {return phoneNo;}

    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}
