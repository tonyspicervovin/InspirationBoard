package com.tony.inspirationboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class NoteListFragment extends Fragment implements NoteListAdapter.ListEventListener {


    private ImageButton addNote;
    private ImageButton addPicture;
    private EditText searchNotes;

    private InspirationViewModel mInspirationModel;

    private static final String TAG = "NOTE_LIST_FRAGMENT";



    private List<NoteRecord> mNotes;
    private List<NoteRecord> mChangeNotes;
    private NoteListAdapter noteListAdapter;


    private NoteListFragmentListener mListener;


    interface NoteListFragmentListener {
        void requestMakeNewNote();
    }
    public NoteListFragment() {
        // Required empty public constructor
    }


    public static NoteListFragment newInstance() {
        NoteListFragment fragment = new NoteListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final InspirationViewModel inspirationViewModel = ViewModelProviders.of(this).get(InspirationViewModel.class);

        final Observer<List<NoteRecord>> noteListObserver = new Observer<List<NoteRecord>>() {
            @Override
            public void onChanged(List<NoteRecord> noteRecords) {
                Log.d(TAG, "Notes changed " + noteRecords);
                NoteListFragment.this.mNotes = noteRecords;
                NoteListFragment.this.noteListAdapter.setNotes(noteRecords);
                NoteListFragment.this.noteListAdapter.notifyDataSetChanged();
            }
        };
        inspirationViewModel.getAllRecords().observe(this, noteListObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.note_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        noteListAdapter = new NoteListAdapter(this.getContext(), this);
        noteListAdapter.setNotes(mNotes);
        recyclerView.setAdapter(noteListAdapter);

        addPicture = view.findViewById(R.id.addImageNote);
        searchNotes = view.findViewById(R.id.searchNotes);

        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddImageFragment addImageFragment = AddImageFragment.newInstance();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, addImageFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        searchNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final InspirationViewModel inspirationViewModel = ViewModelProviders.of(getActivity()).get(InspirationViewModel.class);

                String search = editable.toString();
                Log.d(TAG, "Editable is " + search);

                inspirationViewModel.getMatchingNotes(search).observe(getActivity(), new Observer<List<NoteRecord>>() {
                    @Override
                    public void onChanged(List<NoteRecord> noteRecords) {
                        noteListAdapter.setNotes(noteRecords);
                        noteListAdapter.notifyDataSetChanged();

                    }
                });

                if(search.equals("")){
                    Log.d(TAG, "It's empty");
                    inspirationViewModel.getAllRecords().observe(getActivity(), new Observer<List<NoteRecord>>() {
                        @Override
                        public void onChanged(List<NoteRecord> noteRecords) {
                            noteListAdapter.setNotes(noteRecords);
                            noteListAdapter.notifyDataSetChanged();
                        }
                    });
                }






            }
        });



        addNote = view.findViewById(R.id.addNote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment addNoteFragment = AddNoteFragment.newInstance();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, addNoteFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NoteListFragmentListener) {
            mListener = (NoteListFragmentListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mInspirationModel = ViewModelProviders.of(this).get(InspirationViewModel.class);

    }


    @Override
    public void onNoteContentChanged(int position, String newContent) {
        NoteRecord noteRecord = mNotes.get(position);
        noteRecord.setContent(newContent);
        mInspirationModel.update(noteRecord);

    }

    @Override
    public void onDeleteNote(int position) {
        NoteRecord noteRecord = mNotes.get(position);
        mInspirationModel.delete(noteRecord);

    }



}
