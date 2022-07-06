package com.example.chatbotapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.MarksModel;

import java.util.ArrayList;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {
    public ArrayList<MarksModel> data;
    public MarksAdapter(ArrayList<MarksModel> data, MarksAdapter.ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    MarksAdapter.ItemClickListener mItemListener;

    @NonNull
    @Override
    public MarksAdapter.MarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_list,parent,false);
        return new MarksAdapter.MarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarksAdapter.MarksViewHolder holder, int position) {
        holder.dFaculty.setText(data.get(position).getFaculty());
        holder.dStudentEmail.setText(data.get(position).getEmail());
        holder.dStudentMarks.setText(data.get(position).getMarks());

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

    public class MarksViewHolder extends RecyclerView.ViewHolder {
        TextView dFaculty, dStudentEmail, dStudentMarks;
        public MarksViewHolder(View itemView) {
            super(itemView);
            dFaculty = (TextView)itemView.findViewById(R.id.tvFacultyMarks);
            dStudentEmail = (TextView)itemView.findViewById(R.id.tvStuEmailMarks);
            dStudentMarks = (TextView)itemView.findViewById(R.id.tvMarks);
        }

    }
}
