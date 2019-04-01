package com.abhar.android.multiscreenapp.api;

import com.abhar.android.multiscreenapp.model.User;
import com.abhar.android.multiscreenapp.model.UserPost;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL="http://jsonplaceholder.typicode.com";

    @GET("/users")
    Call<ArrayList<User>> getUsers();

    @GET("/posts")
    Call<ArrayList<UserPost>> getUserPost(@Query("userId") int Id);
}
