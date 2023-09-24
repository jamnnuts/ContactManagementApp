package com.example.contactmanagementapp;
import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {ContactEntry.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactEntryDAO contactEntryDAO();
}
