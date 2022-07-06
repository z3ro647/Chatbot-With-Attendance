package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;

public class AdminDashboardScreen extends AppCompatActivity {

    private Button btnInsertPhone;

    private Button btnViewAllUsers;
    private Button btnAttendance;
    private Button btnMarks;

    ChatAppDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard_screen);

        db = new ChatAppDatabaseHelper(this);

        btnViewAllUsers = findViewById(R.id.btnViewAllUsers);
        btnAttendance = findViewById(R.id.btnAttendance);
        btnMarks = findViewById(R.id.btnMarks);

        btnViewAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardScreen.this, UsersScreen.class));
            }
        });

        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardScreen.this, AttendanceScreen.class));
            }
        });

        btnMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardScreen.this, MarksScreen.class));
            }
        });
    }
}