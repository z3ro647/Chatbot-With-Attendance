package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentDashboardScreen extends AppCompatActivity {

    private Button btnChatbotStudent, btnStudentAttendance, btnStudentMarksResult;
    private String customID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard_screen);

//        btnChatbotStudent = findViewById(R.id.btnChatbotStudent);
//        btnStudentAttendance = findViewById(R.id.btnStudentAttendance);
//        btnStudentMarksResult = findViewById(R.id.btnStudentMarksResult);
//
        customID = getIntent().getStringExtra("customID");
        String batch = getIntent().getStringExtra("batch");
//
//        btnChatbotStudent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(StudentDashboardScreen.this, ChatbotScreen.class));
//            }
//        });
//
//        btnStudentAttendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(StudentDashboardScreen.this, AttendanceStudent.class);
//                intent.putExtra("customID", customID);
//                startActivity(intent);
//            }
//        });
//
//        btnStudentMarksResult.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(StudentDashboardScreen.this, MarksStudent.class);
//                intent.putExtra("customID", customID);
//                startActivity(intent);
//            }
//        });
    }

    public void studentAttendance(View view) {
        Intent intent = new Intent(StudentDashboardScreen.this, AttendanceStudent.class);
        intent.putExtra("customID", customID);
        startActivity(intent);
    }

    public void studentMarks(View view) {
        Intent intent = new Intent(StudentDashboardScreen.this, MarksStudent.class);
        intent.putExtra("customID", customID);
        startActivity(intent);
    }

    public void studentChatbot(View view) {
        startActivity(new Intent(StudentDashboardScreen.this, ChatbotScreen.class));
    }
}