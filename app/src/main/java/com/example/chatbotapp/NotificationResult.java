package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatbotapp.adapters.NotificationAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.NotificationModel;

import java.util.ArrayList;

public class NotificationResult extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    ArrayList<NotificationModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);

        String customDate = getIntent().getStringExtra("customDate");

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        RecyclerView NotificationList = (RecyclerView) findViewById(R.id.notifications);
        NotificationList.setLayoutManager(new LinearLayoutManager(NotificationResult.this));

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllNotificationByDate(customDate);

        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(NotificationResult.this, "No Notification to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                NotificationModel obj = new NotificationModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                data.add(obj);
            }
        }

        NotificationAdapter adapter = new NotificationAdapter(data, new NotificationAdapter.ItemClickListener() {
            @Override
            public void onItemClick(NotificationModel notificationModel) {
                Intent intent;
                intent = new Intent(NotificationResult.this, NotificationUpdate.class);
                String a = String.valueOf(notificationModel.getNotificationID());
                intent.putExtra("attendanceID", a);
                intent.putExtra("customDate", notificationModel.getCustomDate());
                intent.putExtra("notificationTitle", notificationModel.getTitle());
                intent.putExtra("notificationDescription", notificationModel.getDescription());
                startActivity(intent);
            }
        });

        NotificationList.setAdapter(adapter);
    }
}