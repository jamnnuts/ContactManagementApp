package com.example.contactmanagementapp;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    //Todo Do we need parameter specific getters? Doesn't say anything about searching for contacts via name/number.
    // My understanding is just get every contact in the DB and display on the RC view.
}