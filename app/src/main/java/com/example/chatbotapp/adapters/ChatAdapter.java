package com.example.chatbotapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.R;
import com.example.chatbotapp.models.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<Message> messageList;
    private Activity activity;

    public ChatAdapter(List<Message> messageList, Activity activity) {
        this.messageList = messageList;
        this.activity = activity;
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.adapter_message_one, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String message = messageList.get(position).getMessage();
        boolean isReceived = messageList.get(position).getIsReceived();

//        if(isReceived) {
//            boolean downloadAndroidPDF = message.startsWith("Download Android");
//            if(downloadAndroidPDF) {
//                Log.d("androidPDF" ,"Download Android PDF");
//            }
//            boolean downloadFlutterPDF = message.startsWith("Download Flutter");
//            if(downloadFlutterPDF) {
//                Log.d("flutterPDF", "Download Flutter PDF");
//            }
//        }

        if(isReceived){
            holder.messageReceive.setVisibility(View.VISIBLE);
            holder.messageSend.setVisibility(View.GONE);
            holder.messageReceive.setText(message);
            holder.btnDownloadPDF.setVisibility(View.GONE);
            boolean downloadAndroidPDF = message.startsWith("Download Android");
            boolean downloadFlutterPDF = message.startsWith("Download Flutter");
            if(downloadAndroidPDF) {
                Log.d("androidPDF" ,"Download Android PDF");
                holder.btnDownloadPDF.setText("Download Android PDF");
                holder.btnDownloadPDF.setVisibility(View.VISIBLE);
            } else if (downloadFlutterPDF) {
                Log.d("flutterPDF", "Download Flutter PDF");
                holder.btnDownloadPDF.setText("Download Flutter PDF");
                holder.btnDownloadPDF.setVisibility(View.VISIBLE);
            } else {
                holder.btnDownloadPDF.setVisibility(View.GONE);
            }
        } else {
            holder.messageSend.setVisibility(View.VISIBLE);
            holder.messageReceive.setVisibility(View.GONE);
            holder.messageSend.setText(message);
            holder.btnDownloadPDF.setVisibility(View.GONE);
//            boolean downloadAndroidPDF = message.startsWith("Download Android");
//            if(downloadAndroidPDF) {
//                Log.d("androidPDF" ,"Download Android PDF");
//                holder.btnDownloadPDF.setVisibility(View.VISIBLE);
//                holder.btnDownloadPDF.setText("Download Android PDF");
//            } else {
//                holder.btnDownloadPDF.setVisibility(View.GONE);
//            }
//            boolean downloadFlutterPDF = message.startsWith("Download Flutter");
//            if(downloadFlutterPDF) {
//                Log.d("flutterPDF", "Download Flutter PDF");
//                holder.btnDownloadPDF.setVisibility(View.VISIBLE);
//                holder.btnDownloadPDF.setText("Download Flutter PDF");
//            } else {
//                holder.btnDownloadPDF.setVisibility(View.VISIBLE);
//            }
        }
        holder.btnDownloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(activity, "Message From Chat Adapter", Toast.LENGTH_SHORT).show();
                boolean downloadAndroidPDF = message.startsWith("Download Android");
                boolean downloadFlutterPDF = message.startsWith("Download Flutter");
                if(downloadAndroidPDF) {
                    Log.d("downloadingAndroidPDF" ,"Downloading Android PDF");
                    Uri uri = Uri.parse("https://www.tutorialspoint.com/android/android_tutorial.pdf"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    activity.startActivity(intent);
                } else if (downloadFlutterPDF) {
                    Log.d("downloadingFlutterPDF", "Downloading Flutter PDF");
                    Uri uri = Uri.parse("https://www.tutorialspoint.com/flutter/flutter_tutorial.pdf"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    activity.startActivity(intent);
                } else {
                    holder.btnDownloadPDF.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override public int getItemCount() {
        return messageList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView messageSend;
        TextView messageReceive;
        Button btnDownloadPDF;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageSend = itemView.findViewById(R.id.message_send);
            messageReceive = itemView.findViewById(R.id.message_receive);
            btnDownloadPDF = itemView.findViewById(R.id.btnDownloadPDF);
        }
    }
}

