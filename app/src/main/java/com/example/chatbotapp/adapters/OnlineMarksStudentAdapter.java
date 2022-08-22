package com.example.chatbotapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.parse.ParseObject;

import java.util.List;

public class OnlineMarksStudentAdapter extends RecyclerView.Adapter<AllMarksStudentHolder> {
    private List<ParseObject> list;
    private Context context;
    public MutableLiveData<ParseObject> onEditListener = new MutableLiveData<>();

    public OnlineMarksStudentAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AllMarksStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_student, parent,false);
        return new AllMarksStudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMarksStudentHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.dStuSem.setText(object.getString("sem"));
        holder.dStuMarks.setText(object.getString("marks"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class AllMarksStudentHolder extends RecyclerView.ViewHolder{

    TextView dStuSem, dStuMarks;

    public AllMarksStudentHolder(@NonNull View itemView) {
        super(itemView);
        dStuSem = (TextView)itemView.findViewById(R.id.tvStuSem);
        dStuMarks = (TextView)itemView.findViewById(R.id.tvStuMarks);
    }
}