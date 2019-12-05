package com.tony.inspirationboard;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InspirationViewModel extends AndroidViewModel {

    private static final String TAG = "Inspiration_View_Model" ;
    private NoteRepository repository;

    private LiveData<List<NoteRecord>> allRecords;
    private LiveData<List<NoteRecord>> matchingRecords;
    //caching copy of results

    public InspirationViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allRecords = repository.getAllRecords();

    }

    public LiveData<List<NoteRecord>> getAllRecords() {
        allRecords = repository.getAllRecords();
        return allRecords;}



    public void insert(NoteRecord record) {repository.insert(record);}

    public void update(NoteRecord record) {repository.update(record);}

    public void delete(NoteRecord record) {repository.delete(record);}

    public LiveData<List<NoteRecord>> getMatchingNotes(String search) {
        matchingRecords = repository.getMatchingNotes(search);
        return matchingRecords;

    }
}
