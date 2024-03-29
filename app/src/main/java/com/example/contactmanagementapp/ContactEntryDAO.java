package com.example.contactmanagementapp;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactEntryDAO {
    @Insert
    void insert(ContactEntry... inContactEntry);
    @Update
    void update(ContactEntry... inContactEntry);
    @Delete
    void delete(ContactEntry... inContactEntry);
    @Query("SELECT * FROM contacts")
    List<ContactEntry> getAllContacts();
    @Query("SELECT count(*)!=0 FROM contacts WHERE name = :name")
    boolean containsPrimaryKey(String name);
    @Query("SELECT * FROM contacts WHERE name = :name")
    ContactEntry findContactEntry(String name);
    @Query("SELECT count(*) FROM contacts")
    int countNumEntry();

}
