package com.tony.inspirationboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {


    @Override
    public void onFragmentInteraction(Uri uri){

    }
    private String TAG = "MAIN_ACTIVITY";

    private NoteFragment noteFragment;
    private MainFragment mainFragment;

    private ImageButton addNote;
    private ImageButton addImageNote;

    public MainActivity(){
        //required empty constructer
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addImageNote = findViewById(R.id.addImageNote);
        addNote = findViewById(R.id.addNote);



        noteFragment = new NoteFragment();
        mainFragment = new MainFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, mainFragment);
        ft.commit();

        View mainView = findViewById(android.R.id.content);









    }


}
