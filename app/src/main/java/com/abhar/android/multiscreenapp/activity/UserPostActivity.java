package com.abhar.android.multiscreenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.adapter.UserAdapter;
import com.abhar.android.multiscreenapp.adapter.UserPostAdapter;
import com.abhar.android.multiscreenapp.api.Api;
import com.abhar.android.multiscreenapp.model.User;
import com.abhar.android.multiscreenapp.model.UserPost;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPostActivity extends AppCompatActivity {

    private int id;
    private ArrayList<UserPost> userPostList = new ArrayList<>();
    private RecyclerView mUserPostRecyclerView;
    private UserPostAdapter mUserPostAdapter;
    private ShimmerFrameLayout mShimmerViewContainerPost;
    private TextView id_tv;
    private TextView name_tv;
    private ImageView img_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);
        id_tv = findViewById(R.id.id_post_textview);
        name_tv = findViewById(R.id.name_post_textview);
        img_iv = findViewById(R.id.image_view_post);
        Intent intentId = getIntent();
        id = intentId.getIntExtra("Id",0);
        id_tv.setText(String.valueOf(id));
        img_iv.setBackgroundResource(R.drawable.student_img);
        name_tv.setText(getIntent().getStringExtra("Name"));
        mUserPostRecyclerView = findViewById(R.id.user_post_recyclerview);
        mUserPostRecyclerView.setLayoutManager(new LinearLayoutManager(UserPostActivity.this));
        mUserPostAdapter = new UserPostAdapter(userPostList);
        mUserPostRecyclerView.setAdapter(mUserPostAdapter);
        mShimmerViewContainerPost = findViewById(R.id.shimmer_view_container_post);
        retrofitDataFetch();


    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainerPost.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainerPost.stopShimmerAnimation();
        super.onPause();
    }

    private void retrofitDataFetch () {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = mRetrofit.create(Api.class);


        Call<ArrayList<UserPost>> call = api.getUserPost(id);
        call.enqueue(new Callback<ArrayList<UserPost>>() {
            @Override
            public void onResponse(Call<ArrayList<UserPost>> call, Response<ArrayList<UserPost>> response) {

                ArrayList<UserPost> userPost=response.body();
                for(int i=0;i<userPost.size();i++){
                    userPostList.add(new UserPost(userPost.get(i).getUserId(),
                            userPost.get(i).getId(),userPost.get(i).getTitle(),
                            userPost.get(i).getBody()));
                }
                mUserPostAdapter.notifyDataSetChanged();
                mShimmerViewContainerPost.stopShimmerAnimation();
                mShimmerViewContainerPost.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<UserPost>> call, Throwable t) {

            }


        });
    }

    }


