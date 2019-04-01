package com.abhar.android.multiscreenapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhar.android.multiscreenapp.R;
import com.abhar.android.multiscreenapp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the abstract class Adapter. This class helps in converting a listview
 * to a recycler view.
 */
public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<User> userList;
    private RecyclerViewClickListener mListener;

    /**
     * Interface to implement Click Listener on Recycler View
     */
    public interface RecyclerViewClickListener
    {
        void onClick(int position);
    }

    /**
     * Method to initialize listener
     * @param listener RecyclerViewClickListener
     */
    public void setOnClickListener(RecyclerViewClickListener listener){
        mListener=listener;
    }

    /**
     * Constructor to initialize Student List
     * @param userList The list of students created by the user
     */
    public UserAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }

    /**
     * Inner class of RecyclerView for view holder
     */
    public class userViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvId;

        /**
         * Constructor to initialize view of view holder
         * @param view instance of View
         */
        public userViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.name_textview_recycler);
            tvId = view.findViewById(R.id.id_textview_recycler);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(mListener!=null){
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public userViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recyclerview_layout, parent, false);

        return new userViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        User user=userList.get(i);
        userViewHolder holder=(userViewHolder) viewHolder;

        holder.tvName.setText(user.getName());
        holder.tvId.setText(String.valueOf(user.getRollNo()));
    }


    @Override
    public int getItemCount() {
        int size = userList.size();
        return size;
    }


}


