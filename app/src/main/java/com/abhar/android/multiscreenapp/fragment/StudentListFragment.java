package com.abhar.android.multiscreenapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.util.Log;
import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.activity.UserActivity;
import com.abhar.android.multiscreenapp.adapter.UserAdapter;
import com.abhar.android.multiscreenapp.api.Api;
import com.abhar.android.multiscreenapp.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentListFragment extends Fragment {
    private ArrayList<User> userList = new ArrayList<>();
    private RecyclerView mUserRecyclerView;
    private UserAdapter mUserAdapter;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_list,container,false);
       // mUserRecyclerView = rootView.findViewById(R.id.student_recycler_view);

       // retrofitDataFetch();
        //userList.add(new User(1,"Abhar"));
        //mUserAdapter.notifyDataSetChanged();
        return rootView;
    }
   /* private void retrofitDataFetch () {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = mRetrofit.create(Api.class);

        Call<ArrayList<User>> call = api.getUsers();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userList.addAll(response.body());
                mUserAdapter.notifyDataSetChanged();
                Log.i("asdfghjk","zdxcfgvbhjnkm");
                Log.i("check",userList.get(0).getName());

                Toast.makeText(mContext, "Thank you", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(mContext, "Data Not Retrieved ", Toast.LENGTH_LONG).show();
            }
        });
    }*/
}
