package com.example.android.keytotechapp.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Comparable<Comment>, Serializable {

    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mName;
    }

    public void setTitle(String name) {
        mName = mName;
    }

    @Override
    public int compareTo(Comment o) {
        return mId - o.getId();
    }
}
