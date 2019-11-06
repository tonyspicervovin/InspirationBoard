package com.tony.inspirationboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNoteFragment extends Fragment {




    private static final String TAG = "NOTE_FRAGMENT";

    private OnNoteAddedListener newNoteListener;

    private InspirationViewModel inspirationViewModel;

    private NoteRecord noteRecord;

    private String filepath;

    private EditText noteTitle;
    private EditText noteContent;
    private Button addButton;

    public interface OnNoteAddedListener {
        void onNoteAdded(NoteRecord noteRecord);

        void requestMakeNewNote();
    }



    public AddNoteFragment() {
        // Required empty public constructor
    }

    public static AddNoteFragment newInstance() {
        AddNoteFragment fragment = new AddNoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        noteContent = view.findViewById(R.id.noteContent);
        noteTitle = view.findViewById(R.id.noteTitle);
        addButton = view.findViewById(R.id.add_button);

        inspirationViewModel = ViewModelProviders.of(getActivity()).get(InspirationViewModel.class);





        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = noteTitle.getText().toString();
                //what title did user enter
                String content = noteContent.getText().toString();
                //what note content was entered
                Log.d(TAG, title+" "+content);
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddNoteFragment.this.getContext(), "Enter a title and note", Toast.LENGTH_LONG).show();
                    //if either is empty ask user for input
                }
                filepath = "nothing";
                NoteRecord aNote = new NoteRecord(title,content, filepath);
                inspirationViewModel.insert(aNote);
                newNoteListener.onNoteAdded(noteRecord); // notifies activity to fragments can be swapped
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteAddedListener) {
            newNoteListener = (OnNoteAddedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNoteChanged");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        inspirationViewModel = ViewModelProviders.of(this).get(InspirationViewModel.class);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        newNoteListener = null;
    }

}
