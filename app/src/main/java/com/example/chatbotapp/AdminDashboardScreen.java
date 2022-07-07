package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceAdapter;
import com.example.chatbotapp.adapters.NotificationAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.NotificationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdminDashboardScreen extends AppCompatActivity {

    private Button btnInsertPhone;

    private Button btnViewAllUsers;
    private Button btnAttendance;
    private Button btnMarks;

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    ArrayList<NotificationModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard_screen);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

//        btnViewAllUsers = findViewById(R.id.btnViewAllUsers);
//        btnAttendance = findViewById(R.id.btnAttendance);
//        btnMarks = findViewById(R.id.btnMarks);
//
//        btnViewAllUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AdminDashboardScreen.this, UsersScreen.class));
//            }
//        });
//
//        btnAttendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AdminDashboardScreen.this, AttendanceScreen.class));
//            }
//        });
//
//        btnMarks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AdminDashboardScreen.this, MarksScreen.class));
//            }
//        });

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        String currentDateandTime = sdf.format(new Date());

        RecyclerView NotificationList = (RecyclerView) findViewById(R.id.notificationsAdmin);
        NotificationList.setLayoutManager(new LinearLayoutManager(AdminDashboardScreen.this));

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllNotificationByDate(currentDateandTime);

        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(AdminDashboardScreen.this, "No Notification to show.", Toast.LENGTH_SHORT).show();
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
                intent = new Intent(AdminDashboardScreen.this, NotificationUpdate.class);
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

    public void takeAttendance(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, AttendanceScreen.class));
    }

    public void takeMarks(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, MarksScreen.class));
    }

    public void takeUsers(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, UsersScreen.class));
    }

    public void takeNotification(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, NotificationScreen.class));
    }
}