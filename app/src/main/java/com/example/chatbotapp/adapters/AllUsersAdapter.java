package com.example.chatbotapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.parse.ParseObject;

import java.util.List;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersHolder>{

    private List<ParseObject> list;
    private Context context;
    public MutableLiveData<ParseObject> onEditListener = new MutableLiveData<>();
    public MutableLiveData<ParseObject> onDeleteListener = new MutableLiveData<>();

    public AllUsersAdapter(List<ParseObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AllUsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list,parent,false);
        return new AllUsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUsersHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.dCustomID.setText(object.getString("customID"));
        holder.dEmail.setText(object.getString("email"));

        holder.dEdit.setOnClickListener(v -> {
            onEditListener.postValue(object);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class AllUsersHolder extends RecyclerView.ViewHolder{

    TextView dCustomID, dEmail;
    ImageView dEdit;

    public AllUsersHolder(@NonNull View itemView) {
        super(itemView);
        dCustomID = itemView.findViewById(R.id.txtCustomID);
        dEmail = itemView.findViewById(R.id.txtEmail);
        dEdit = itemView.findViewById(R.id.edit);
    }
}