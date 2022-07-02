package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AttendanceScreen extends AppCompatActivity {

    private Button btnCreateAttendance, btnSearchAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_screen);

        btnCreateAttendance = findViewById(R.id.btnCreateAttendance);
        btnSearchAttendance = findViewById(R.id.btnSearchAttendance);

        btnCreateAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceScreen.this, CreateAttendance.class));
            }
        });

        btnSearchAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceScreen.this, SearchAttendance.class));
            }
        });
    }
}