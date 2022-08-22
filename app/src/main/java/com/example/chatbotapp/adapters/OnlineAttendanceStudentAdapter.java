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

public class OnlineAttendanceStudentAdapter extends RecyclerView.Adapter<AllAttendanceStudentHolder> {
    private List<ParseObject> list;
    private Context context;
    public MutableLiveData<ParseObject> onEditListener = new MutableLiveData<>();

    public OnlineAttendanceStudentAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AllAttendanceStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_student, parent,false);
        return new AllAttendanceStudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAttendanceStudentHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.dCustomDate.setText(object.getString("customDate"));
//        if (list.get(position).getString("remark").isEmpty()) {
//            holder.dStudentRemarks.setText("Attendance Not Taken");
//        } else {
//            holder.dStudentRemarks.setText(list.get(position).getString("remark"));
//        }
        holder.dStudentRemarks.setText(list.get(position).getString("remark"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class AllAttendanceStudentHolder extends RecyclerView.ViewHolder{

    TextView dCustomDate, dStudentRemarks;

    public AllAttendanceStudentHolder(@NonNull View itemView) {
        super(itemView);
        dCustomDate = (TextView)itemView.findViewById(R.id.tvDate);
        dStudentRemarks = (TextView)itemView.findViewById(R.id.tvStudentRemarks);
    }
}