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
import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.adapter.UserAdapter;
import com.abhar.android.multiscreenapp.api.ApiClient;
import com.abhar.android.multiscreenapp.api.ApiInterface;
import com.abhar.android.multiscreenapp.model.User;
import com.abhar.android.multiscreenapp.util.Constant;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * UserActivity class get the list of users and displays it. Further we can get details of
 * particular User and fetch his/her posts.
 */
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
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initialize();
        if(savedInstanceState != null)
        {
            mIdTv.setText(savedInstanceState.getString(Constant.idKey));
            mNameTv.setText(savedInstanceState.getString(Constant.idName));
            imageView.setBackgroundResource(R.drawable.student_img);
        }
        retrofitDataFetch();
        onItemClickRecyclerView();
        onFetchButtonClick();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.idName,mNameTv.getText().toString());
        outState.putString(Constant.idKey,mIdTv.getText().toString());
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
     * Method to display details of clicked User
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

    /**
     * Method to perform action on clicking Button. It sends User Name and Id to next Activity
     * through Intent.
     */
    private void onFetchButtonClick()
        {
            mFetchButton = findViewById(R.id.fetch_button);
            mFetchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentUserId = new Intent(UserActivity.this,UserPostActivity.class);
                    intentUserId.putExtra(Constant.idKey,Integer.parseInt(mIdTv.getText().toString()));
                    intentUserId.putExtra(Constant.idName,mNameTv.getText().toString());
                    startActivity(intentUserId);
                }
            });
        }

    /**
     * Method to initialize all data members used in the class
     */
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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    /**
     * Method to fetch data through API
     */
    private void retrofitDataFetch () {

        Call<ArrayList<User>> call = apiInterface.getUsers();
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
                Log.i(getString(R.string.noResultsFetched), getString(R.string.onFailure)+t.getMessage());
            }
        });
    }
}