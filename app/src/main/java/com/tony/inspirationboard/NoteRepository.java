package com.tony.inspirationboard;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private InspirationDAO inspirationDAO;

    public NoteRepository (Application application) {
        NoteDatabase nb = NoteDatabase.getDatabase(application);
        inspirationDAO = nb.inspirationDAO();

    }

    public void insert(NoteRecord record) {

        new InsertNoteAsync(inspirationDAO).execute(record);
    }

    //insert and update records asynchronously

    static class InsertNoteAsync extends AsyncTask<NoteRecord, Void, Void> {

        private InspirationDAO inspirationDAO;
        InsertNoteAsync(InspirationDAO inspirationDAO) {this.inspirationDAO = inspirationDAO; }

        @Override
        protected Void doInBackground(NoteRecord... noteRecords) {
            inspirationDAO.insert(noteRecords);
            return null;
        }
    }
    public void update(NoteRecord record) {
        new UpdateNoteAsync(inspirationDAO).execute(record);
    }

    static class UpdateNoteAsync extends AsyncTask<NoteRecord, Void, Void> {

        private InspirationDAO inspirationDAO;

        UpdateNoteAsync(InspirationDAO inspirationDAO) { this.inspirationDAO = inspirationDAO; }

        @Override
        protected Void doInBackground(NoteRecord... noteRecords) {
            inspirationDAO.update(noteRecords);
            return null;
        }
    }
    public LiveData<List<NoteRecord>> getAllRecords() {
        return inspirationDAO.getAllNotes();
    }
    public LiveData<NoteRecord> getRecordForTitle(String title) {
        return inspirationDAO.getNoteForTitle(title);
    }
}
