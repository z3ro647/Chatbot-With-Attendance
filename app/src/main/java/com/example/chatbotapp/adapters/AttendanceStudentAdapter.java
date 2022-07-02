package com.example.chatbotapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.example.chatbotapp.models.AttendanceModel;

import java.util.ArrayList;

public class AttendanceStudentAdapter  extends RecyclerView.Adapter<AttendanceStudentAdapter.AttendanceStudentViewHolder>{
    public ArrayList<AttendanceModel> data;
    public AttendanceStudentAdapter(ArrayList<AttendanceModel> data, AttendanceStudentAdapter.ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    AttendanceStudentAdapter.ItemClickListener mItemListener;

    @NonNull
    @Override
    public AttendanceStudentAdapter.AttendanceStudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_student,parent,false);
        return new AttendanceStudentAdapter.AttendanceStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceStudentAdapter.AttendanceStudentViewHolder holder, int position) {
        holder.dCustomDate.setText(data.get(position).getSessionID());
        if (data.get(position).getRemark().equals("")) {
            holder.dStudentRemarks.setText("Attendance Not Taken");
        } else {
            holder.dStudentRemarks.setText(data.get(position).getRemark());
        }
        //holder.dStudentRemarks.setText(data.get(position).getRemark());

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

    public class AttendanceStudentViewHolder extends RecyclerView.ViewHolder {
        TextView dCustomDate, dStudentRemarks;
        public AttendanceStudentViewHolder(View itemView) {
            super(itemView);
            dCustomDate = (TextView)itemView.findViewById(R.id.tvDate);
            dStudentRemarks = (TextView)itemView.findViewById(R.id.tvStudentRemarks);
        }

    }
}
