package com.example.chatbotapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.UsersModel;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>{
    public ArrayList<AttendanceModel> data;
    public AttendanceAdapter(ArrayList<AttendanceModel> data, AttendanceAdapter.ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    AttendanceAdapter.ItemClickListener mItemListener;

    @NonNull
    @Override
    public AttendanceAdapter.AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendances_list,parent,false);
        return new AttendanceAdapter.AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceAdapter.AttendanceViewHolder holder, int position) {
        int a = data.get(position).getAttendanceID();
        holder.dStudentID.setText(String.valueOf(a));
        holder.dStudentEmail.setText(data.get(position).getEmail());
        holder.dStudentRemarks.setText(data.get(position).getRemark());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(AttendanceModel a);
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView dStudentID, dStudentEmail, dStudentRemarks;
        public AttendanceViewHolder(View itemView) {
            super(itemView);
            dStudentID = (TextView)itemView.findViewById(R.id.tvStudentID);
            dStudentEmail = (TextView)itemView.findViewById(R.id.tvStudentEmail);
            dStudentRemarks = (TextView)itemView.findViewById(R.id.tvRemarks);
        }

    }
}
