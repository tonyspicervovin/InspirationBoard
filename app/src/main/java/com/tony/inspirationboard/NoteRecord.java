package com.tony.inspirationboard;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity
public class NoteRecord {
    @PrimaryKey
    @NonNull
    private String title;

    private String content;




    public NoteRecord(@NonNull String title, String content) {
        this.title = title;
        this.content = content;



    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // public String getImagePath() { return imagePath;}

    // public void setImagePath(String imagePath) {this.imagePath = imagePath; }



    @Override
    public String toString() {
        return "NoteRecord{" +
                "title='" + title + '\'' +
                ", content='" + content + '\''
               ;
    }
}
