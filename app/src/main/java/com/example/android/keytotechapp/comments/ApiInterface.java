package com.example.android.keytotechapp.comments;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/comments/{id}")
    Call<Comment> getCommentWithID(@Path("id") int id);

    @GET("/comments")
    Call<List<Comment>> getComments();

}
