package com.tony.inspirationboard;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(NoteRecord... nr);

    @Query("SELECT * FROM NOTERECORD WhERE title like :search or content like :search")
    LiveData<List<NoteRecord>> getMatchingNotes(String search);

    @Query("SELECT * FROM NoteRecord WHERE title = :title LIMIT 1")
    LiveData<List<NoteRecord>> getNoteForTitle(String title);

    @Query("SELECT * FROM NoteRecord")
    LiveData<List<NoteRecord>> getAllNotes();


}
