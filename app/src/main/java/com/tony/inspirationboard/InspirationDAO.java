package com.tony.inspirationboard;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InspirationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteRecord... nr);

    @Update
    void update(NoteRecord... nr);

    @Query("SELECT * FROM NoteRecord WHERE title = :title LIMIT 1")
    LiveData<NoteRecord> getNoteForTitle(String title);

    @Query("SELECT * FROM NoteRecord")
    LiveData<List<NoteRecord>> getAllNotes();


}
