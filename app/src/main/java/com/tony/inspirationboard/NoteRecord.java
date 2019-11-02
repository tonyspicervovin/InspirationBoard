package com.tony.inspirationboard;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class NoteRecord {
    @PrimaryKey
    @NonNull
    private String title;

    private String content;

    private Date date;


    public NoteRecord(@NonNull String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NoteRecord{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
