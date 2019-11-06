package com.tony.inspirationboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final static String TAG = "NOTE_LIST_ADAPTER";

    interface ListEventListener {
        void onNoteContentChanged(int position, String newContent);
        void onDeleteNote(int position);
    }

    private ListEventListener mListener;

    private List<NoteRecord> mNotes;

    NoteListAdapter(Context context, ListEventListener eventListener) {

        this.mListener = eventListener;
    }

    void setNotes(List<NoteRecord> notes) {
        this.mNotes = notes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(mNotes != null) {
            NoteRecord note = mNotes.get(position);
            holder.bind(note);
        }else{
            holder.bind(null);
        }
    }

    @Override
    public int getItemCount(){
        if (mNotes == null) {
            return 0;
        }
        return mNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteTitle;
        private final TextView noteContent;
        private final ImageButton deleteButton;
        private final ImageView imageView;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.title_tag);
            noteContent = itemView.findViewById(R.id.content_tag);
            deleteButton = itemView.findViewById(R.id.delete_button);
            imageView = itemView.findViewById(R.id.imageView);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteNote(getAdapterPosition());
                }
            });


        }

        void bind(NoteRecord note) {

            //helper method to update data shown

            Log.d(TAG, "binding note " + note);

            if (note == null) {
                noteTitle.setText("");
                noteContent.setText("");
            } else {
                noteTitle.setText(note.getTitle());
                noteContent.setText(note.getContent());
                if (note.getFilePath() != "nothing") {
                    String path = note.getFilePath();
                    Picasso.get()
                            .load(new File(path))
                            .error(android.R.drawable.stat_notify_error) // built in error icon
                            .fit()
                            .centerCrop()
                            .into(imageView);
                }
            }
        }
    }
}
