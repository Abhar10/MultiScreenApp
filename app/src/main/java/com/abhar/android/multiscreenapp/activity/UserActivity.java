package com.abhar.android.multiscreenapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.adapter.UserAdapter;
import com.abhar.android.multiscreenapp.api.Api;
import com.abhar.android.multiscreenapp.fragment.StudentDetailFragment;
import com.abhar.android.multiscreenapp.model.User;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    private ArrayList<User> userList = new ArrayList<>();
    private RecyclerView mUserRecyclerView;
    private UserAdapter mUserAdapter;
    private static TextView mNameTv;
    private static TextView mIdTv;
    private ImageView imageView;
    private Intent intentUserId;
    private Button mFetchButton;
    private int Id;
    private String name;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initialize();
        if(savedInstanceState != null)
        {
            mIdTv.setText(savedInstanceState.getString("Id"));
            mNameTv.setText(savedInstanceState.getString("Name"));
            imageView.setBackgroundResource(R.drawable.student_img);
        }
        retrofitDataFetch();
        onItemClickRecyclerView();
        onFetchButtonClick();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Name",mNameTv.getText().toString());
        outState.putString("Id",mIdTv.getText().toString());
    }

    private void retrofitDataFetch () {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = mRetrofit.create(Api.class);


        Call<ArrayList<User>> call = api.getUsers();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response)
            {
                ArrayList<User> user=response.body();
                for(int i=0;i<user.size();i++){
                    userList.add(new User(user.get(i).getName(),user.get(i).getRollNo()));
                }
                mUserAdapter.notifyDataSetChanged();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.i("TESTING", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    /**
     * Method to perform various action such as view, edit or delete on selecting an
     * item of RecyclerView
     */
    private void onItemClickRecyclerView()
    {
        mUserAdapter.setOnClickListener(new UserAdapter.RecyclerViewClickListener() {

            @Override
            public void onClick(final int position) {
                name = userList.get(position).getName();
                Id = userList.get(position).getRollNo();
                mNameTv.setText(name);
                mIdTv.setText(String.valueOf(Id));
                imageView.setBackgroundResource(R.drawable.student_img);
            }
        });}
        private void onFetchButtonClick()
        {
            mFetchButton = findViewById(R.id.fetch_button);
            mFetchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentUserId = new Intent(UserActivity.this,UserPostActivity.class);
                    intentUserId.putExtra("Id",Integer.parseInt(mIdTv.getText().toString()));
                    intentUserId.putExtra("Name",mNameTv.getText().toString());
                    startActivity(intentUserId);
                }
            });
        }

    private void initialize()
    {
        mIdTv = findViewById(R.id.id_textview);
        mNameTv = findViewById(R.id.name_textview);
        imageView = findViewById(R.id.image_view);
        mUserRecyclerView = findViewById(R.id.student_recycler_view);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
        mUserAdapter = new UserAdapter(userList);
        mUserRecyclerView.setAdapter(mUserAdapter);

    }
}