package com.example.chatbotapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.example.chatbotapp.models.UsersModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    public ArrayList<UsersModel> data;
    public UsersAdapter(ArrayList<UsersModel> data, ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    ItemClickListener mItemListener;

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder( UsersViewHolder holder, int position) {
        int a = data.get(position).getUserID();
        holder.dId.setText(String.valueOf(a));
        holder.dCustomID.setText(data.get(position).getCustomID());
        holder.dEmail.setText(data.get(position).getEmail());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(UsersModel u);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView dId, dCustomID, dEmail;
        Button btnEdit, btnDelete;
        public UsersViewHolder(View itemView) {
            super(itemView);
            //dId = (TextView)itemView.findViewById(R.id.txtUserID);
            dCustomID = (TextView)itemView.findViewById(R.id.txtCustomID);
            dEmail = (TextView)itemView.findViewById(R.id.txtEmail);
        }

    }
}
