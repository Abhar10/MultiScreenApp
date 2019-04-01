package com.abhar.android.multiscreenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.adapter.UserPostAdapter;
import com.abhar.android.multiscreenapp.api.ApiClient;
import com.abhar.android.multiscreenapp.api.ApiInterface;
import com.abhar.android.multiscreenapp.model.UserPost;
import com.abhar.android.multiscreenapp.util.Constant;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPostActivity extends AppCompatActivity {

    private int mId;
    private ArrayList<UserPost> userPostList = new ArrayList<>();
    private RecyclerView mUserPostRecyclerView;
    private UserPostAdapter mUserPostAdapter;
    private ShimmerFrameLayout mShimmerViewContainerPost;
    private TextView id_tv;
    private TextView name_tv;
    private ImageView img_iv;
    private Intent intentId;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);
        initialize();
        setResources();
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

    /**
     * Method to fetch Selected User Posts
     */
    private void retrofitDataFetch () {

        Call<ArrayList<UserPost>> call = apiInterface.getUserPost(mId);
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

    /**
     * Method to initialize resources used in the Class
     */
    private void initialize()
    {
        id_tv = findViewById(R.id.id_post_textview);
        name_tv = findViewById(R.id.name_post_textview);
        img_iv = findViewById(R.id.image_view_post);
        mUserPostRecyclerView = findViewById(R.id.user_post_recyclerview);
        mUserPostRecyclerView.setLayoutManager(new LinearLayoutManager(UserPostActivity.this));
        mUserPostAdapter = new UserPostAdapter(userPostList);
        mUserPostRecyclerView.setAdapter(mUserPostAdapter);
        mShimmerViewContainerPost = findViewById(R.id.shimmer_view_container_post);
        intentId = getIntent();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    /**
     * Method to Set Resources used in the Class
     */
    private void setResources()
    {
        mId = intentId.getIntExtra(Constant.idKey,0);
        id_tv.setText(String.valueOf(mId));
        img_iv.setBackgroundResource(R.drawable.student_img);
        name_tv.setText(getIntent().getStringExtra(Constant.idName));
    }

    }


