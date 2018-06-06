package com.smad.bsnassignment.module.feedback;

/**
 * Created by RohitSingh on 6/5/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smad.bsnassignment.R;

import java.util.List;


public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<Feedback> listUsers;

    public UsersRecyclerAdapter(List<Feedback> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.feedback.setText(listUsers.get(position).getFeedback());
        holder.user_id.setText(listUsers.get(position).getUser_id());
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(), "" + listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView user_id, feedback;

        public UserViewHolder(View view) {
            super(view);
            user_id = view.findViewById(R.id.user_id);
            feedback = view.findViewById(R.id.feedback);
        }
    }


}