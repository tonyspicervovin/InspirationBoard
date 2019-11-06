package com.tony.inspirationboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  AddNoteFragment.OnNoteAddedListener, AddImageFragment.OnNoteAddedListener {



    private String TAG = "MAIN_ACTIVITY";
    private static final String TAG_MAIN_FRAGMENT = "NoteListFragment";
    //tags for logs


    private NoteListFragment mainFragment;
    private InspirationViewModel inspirationViewModel;



    public MainActivity(){
        //required empty constructer
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteListFragment mainFragment = NoteListFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, mainFragment);
        ft.commit();
        //displaying the main fragment initially


        InspirationViewModel ivm = new InspirationViewModel(getApplication());
        LiveData<List<NoteRecord>> noteList = ivm.getAllRecords();
        noteList.observe(this, new Observer<List<NoteRecord>>() {
            @Override
            public void onChanged(List<NoteRecord> noteRecords) {
                Log.d(TAG, "Note records are: "+noteRecords);
                //logging notes for debugging purposes
            }
        });
    }

    @Override
    public void onNoteAdded(NoteRecord noteRecord) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        NoteListFragment mainFragment = (NoteListFragment) fm.findFragmentByTag(TAG_MAIN_FRAGMENT);
        if (mainFragment != null) {
            ft.replace(R.id.fragment_container, mainFragment);
            ft.commit();
        }else {
            Log.w(TAG, "Note list fragment not found");
        }
    }

    @Override
    public void requestMakeNewNote() {

    }


}
