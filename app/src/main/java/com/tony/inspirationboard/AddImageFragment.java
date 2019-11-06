package com.tony.inspirationboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class AddImageFragment extends Fragment {


    private static final String TAG = "IMAGE_FRAGMENT";



    private String mCurrentImagePath = "nothing";

    private NoteRecord noteRecord;

    private InspirationViewModel inspirationViewModel;
    private OnNoteAddedListener newNoteListener;

    private ImageButton captureImage;
    private EditText imageTitle;
    private EditText imageThumbnail;
    private Button addImageButton;

    private static final String BUNDLE_KEY_MOST_RECENT_FILE_PATH = "bundle key most recent path";

    public interface OnNoteAddedListener {
        void onNoteAdded(NoteRecord noteRecord);

        void requestMakeNewNote();
    }

    public AddImageFragment() {
        // Required empty public constructor
    }

    public static AddImageFragment newInstance() {
        AddImageFragment fragment = new AddImageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_add_image, container, false);

        addImageButton = view.findViewById(R.id.add_image_button);
        imageThumbnail = view.findViewById(R.id.image_thumbnail);
        imageTitle = view.findViewById(R.id.image_title);
        captureImage = view.findViewById(R.id.capture_image);





        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                int requestCodeButtonIndex = 0;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) { //if there is a camera
                    File imagefile = null;
                    try {
                        imagefile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (imagefile != null) {
                        Uri imageUri = FileProvider.getUriForFile(context, "com.tony.inspirationboard.fileprovider", imagefile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePictureIntent, requestCodeButtonIndex);

                    } else {
                        Log.e(TAG, "Image file is null");
                    }
                }

            }

        });
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inspirationViewModel = ViewModelProviders.of(getActivity()).get(InspirationViewModel.class);

                String title = imageTitle.getText().toString();
                String thumbnail = imageThumbnail.getText().toString();
                String filepath = mCurrentImagePath;
                if (title.isEmpty() || thumbnail.isEmpty()) {
                    Toast.makeText(AddImageFragment.this.getContext(), "Enter a thumbnail and title, and make sure to take a picture", Toast.LENGTH_LONG).show();
                    //if either is empty ask user for input
                }
                NoteRecord aNote = new NoteRecord(title, thumbnail, filepath);
                inspirationViewModel.insert(aNote);
                newNoteListener.onNoteAdded(aNote);
                newNoteListener.onNoteAdded(noteRecord);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();


            }
        });

        return view;
    }

    private File createImageFile() throws IOException{

        String imageFileName = "NOTE_IMAGE_" + new Date().getTime();
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentImagePath = imageFile.getAbsolutePath();
        return imageFile;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) { // result ok
            Log.d(TAG, "onActivityResult for request code " + requestCode +
                    " and current path " + mCurrentImagePath
            );
            loadImage();
        }
    }
    private void loadImage() {



        if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
            String path = mCurrentImagePath;
            Log.d(TAG, "trying to load image");
            Picasso.get()
                    .load(new File(path))
                    .error(android.R.drawable.stat_notify_error) // built in error icon
                    .fit()
                    .centerCrop()
                    .into(captureImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d(TAG, e.toString());
                        }
                    });

        }

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
