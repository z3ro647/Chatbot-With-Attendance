package com.example.chatbotapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.MarksStudent;
import com.example.chatbotapp.R;
import com.example.chatbotapp.models.MarksModel;

import java.util.ArrayList;

public class MarksStudentAdapter extends RecyclerView.Adapter<MarksStudentAdapter.MarksStudentViewHolder>  {
    public ArrayList<MarksModel> data;
    public MarksStudentAdapter(ArrayList<MarksModel> data, MarksStudentAdapter.ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    MarksStudentAdapter.ItemClickListener mItemListener;

    @NonNull
    @Override
    public MarksStudentAdapter.MarksStudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_student,parent,false);
        return new MarksStudentAdapter.MarksStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarksStudentAdapter.MarksStudentViewHolder holder, int position) {
        holder.dStuSem.setText(data.get(position).getSem());
        holder.dStuMarks.setText(data.get(position).getMarks());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(MarksModel a);
    }

    public class MarksStudentViewHolder extends RecyclerView.ViewHolder {
        TextView dStuSem, dStuMarks;
        public MarksStudentViewHolder(View itemView) {
            super(itemView);
            dStuSem = (TextView) itemView.findViewById(R.id.tvStuSem);
            dStuMarks = (TextView)itemView.findViewById(R.id.tvStuMarks);
        }

    }
}
