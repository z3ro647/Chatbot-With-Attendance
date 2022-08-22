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
import com.example.chatbotapp.models.AttendanceModel;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class OnlineAttendanceAdapter extends RecyclerView.Adapter<AllAttendanceHolder> {

    private List<ParseObject> list;
    private Context context;
    public MutableLiveData<ParseObject> onEditListener = new MutableLiveData<>();

    public OnlineAttendanceAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AllAttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendances_list, parent,false);
        return new AllAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAttendanceHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.dStudentID.setText(object.getString("customID"));
        holder.dStudentEmail.setText(object.getString("email"));
        holder.dStudentRemarks.setText(object.getString("remark"));

        holder.dEditAttendance.setOnClickListener(v -> {
            onEditListener.postValue(object);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


class AllAttendanceHolder extends RecyclerView.ViewHolder{

    TextView dStudentID, dStudentEmail, dStudentRemarks;
    ImageView dEditAttendance;

    public AllAttendanceHolder(@NonNull View itemView) {
        super(itemView);
        dStudentID = (TextView)itemView.findViewById(R.id.tvStudentID);
        dStudentEmail = (TextView)itemView.findViewById(R.id.tvStudentEmail);
        dStudentRemarks = (TextView)itemView.findViewById(R.id.tvRemarks);
        dEditAttendance = (ImageView)itemView.findViewById(R.id.editAttendance) ;
    }
}