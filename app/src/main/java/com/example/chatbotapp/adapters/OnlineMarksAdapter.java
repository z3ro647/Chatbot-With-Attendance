package com.example.chatbotapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.parse.ParseObject;

import java.util.List;

public class OnlineMarksAdapter extends RecyclerView.Adapter<AllMarksHolder> {
    private List<ParseObject> list;
    private Context context;
    public MutableLiveData<ParseObject> onEditListener = new MutableLiveData<>();

    public OnlineMarksAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AllMarksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_list, parent,false);
        return new AllMarksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMarksHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.dSem.setText(object.getString("sem"));
        holder.dStudentEmail.setText(object.getString("email"));
        holder.dStudentMarks.setText(object.getString("marks"));

        holder.dEditMarks.setOnClickListener(v -> {
            onEditListener.postValue(object);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AllMarksHolder extends RecyclerView.ViewHolder{

    TextView dSem, dStudentEmail, dStudentMarks;
    ImageView dEditMarks;

    public AllMarksHolder(@NonNull View itemView) {
        super(itemView);
        dSem = (TextView)itemView.findViewById(R.id.tvSemMarks);
        dStudentEmail = (TextView)itemView.findViewById(R.id.tvStuEmailMarks);
        dStudentMarks = (TextView)itemView.findViewById(R.id.tvMarks);
        dEditMarks = (ImageView)itemView.findViewById(R.id.editMarks) ;
    }
}