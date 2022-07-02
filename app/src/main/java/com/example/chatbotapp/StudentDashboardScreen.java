package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentDashboardScreen extends AppCompatActivity {

    private Button btnChatbotStudent, btnStudentAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard_screen);

        btnChatbotStudent = findViewById(R.id.btnChatbotStudent);
        btnStudentAttendance = findViewById(R.id.btnStudentAttendance);

        String customID = getIntent().getStringExtra("customID");

        btnChatbotStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardScreen.this, ChatbotScreen.class));
            }
        });

        btnStudentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDashboardScreen.this, AttendanceStudent.class);
                intent.putExtra("customID", customID);
                startActivity(intent);
            }
        });
    }
}