package com.example.chatbotapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.example.chatbotapp.models.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    public ArrayList<NotificationModel> data;
    public NotificationAdapter(ArrayList<NotificationModel> data, NotificationAdapter.ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    NotificationAdapter.ItemClickListener mItemListener;

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.NotificationViewHolder holder, int position) {
        int a = data.get(position).getNotificationID();
        //holder.dStudentID.setText(String.valueOf(a));
        holder.dNotificationTitle.setText(data.get(position).getTitle());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(NotificationModel a);
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView dNotificationTitle;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            dNotificationTitle = (TextView)itemView.findViewById(R.id.tvNotificationTitle);
        }
    }
}
