package com.tony.inspirationboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InspirationViewModel extends AndroidViewModel {

    private NoteRepository repository;

    private LiveData<List<NoteRecord>> allRecords;
    //caching copy of results

    public InspirationViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allRecords = repository.getAllRecords();

    }

    public LiveData<List<NoteRecord>> getAllRecords() {return allRecords;}

    public LiveData<NoteRecord> getRecordForTitle(String title) {
        return repository.getRecordForTitle(title);
    }

    public void insert(NoteRecord record) {repository.insert(record);}

    public void update(NoteRecord record) {repository.update(record);}

    public void delete(NoteRecord record) {repository.delete(record);}

}
