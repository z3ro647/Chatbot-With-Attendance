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

public class OnlineNotificationAdapter extends RecyclerView.Adapter<AllNotificationHolder> {
    private List<ParseObject> list;
    private Context context;
    public MutableLiveData<ParseObject> onEditListener = new MutableLiveData<>();

    public OnlineNotificationAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AllNotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list, parent,false);
        return new AllNotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNotificationHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.dNotificationTitle.setText(object.getString("notificationTitle"));

        holder.dEditNotification.setOnClickListener(v -> {
            onEditListener.postValue(object);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class AllNotificationHolder extends RecyclerView.ViewHolder{

    TextView dNotificationTitle;
    ImageView dEditNotification;

    public AllNotificationHolder(@NonNull View itemView) {
        super(itemView);
        dNotificationTitle = (TextView)itemView.findViewById(R.id.tvNotificationTitle);
        dEditNotification = (ImageView)itemView.findViewById(R.id.editNotification) ;
    }
}