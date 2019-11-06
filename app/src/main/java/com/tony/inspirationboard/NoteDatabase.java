package com.tony.inspirationboard;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities =  {NoteRecord.class}, version = 2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {





    private static volatile NoteDatabase INSTANCE;

    public abstract InspirationDAO inspirationDAO(); //abstract method



    static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) { //only one thread can run code at once
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "Notes").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}

//this class creates the actual database