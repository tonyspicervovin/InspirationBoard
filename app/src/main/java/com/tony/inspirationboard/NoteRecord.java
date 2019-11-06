package com.tony.inspirationboard;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;


@Entity
public class NoteRecord {
    @PrimaryKey
    @NonNull
    private String title;

    private String content;

    private String filePath;


    @Override
    public String toString() {
        return "NoteRecord{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public NoteRecord(@NonNull String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;



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

    public String getFilePath() { return filePath;}

    public void setFilePath(String filePath) {this.filePath = filePath; }


}
