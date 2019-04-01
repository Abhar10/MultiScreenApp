package com.abhar.android.multiscreenapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.activity.UserActivity;
import com.abhar.android.multiscreenapp.activity.UserPostActivity;

import com.abhar.android.multiscreenapp.model.User;
import com.abhar.android.multiscreenapp.model.UserPost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<UserPost> userPostList;

    public UserPostAdapter(ArrayList<UserPost> userPostList)
    {
        this.userPostList = userPostList;
    }

    public class userPostViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvId, tvTitle, tvBody;
        public userPostViewHolder(View view)
        {
            super(view);

            tvId = view.findViewById(R.id.user_id);
            tvBody = view.findViewById(R.id.user_body);
            tvTitle = view.findViewById(R.id.user_title);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_post_recyclerview, viewGroup, false);

        return new userPostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        UserPost userPost=userPostList.get(i);
        UserPostAdapter.userPostViewHolder holder=(UserPostAdapter.userPostViewHolder) viewHolder;

        holder.tvTitle.setText(userPost.getTitle());
        holder.tvId.setText(String.valueOf(userPost.getId()));
        holder.tvBody.setText(userPost.getBody());
    }

    @Override
    public int getItemCount() {
        return userPostList.size();
    }
}
