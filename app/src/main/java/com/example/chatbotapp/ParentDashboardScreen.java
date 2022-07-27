package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParentDashboardScreen extends AppCompatActivity {

    private Button btnChatbotParent;
    private String customID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard_screen);

        customID = getIntent().getStringExtra("customID");
        String batch = getIntent().getStringExtra("batch");

//        btnChatbotParent = findViewById(R.id.btnChatbotParent);
//
//        btnChatbotParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ParentDashboardScreen.this, ChatbotScreen.class));
//            }
//        });
    }

    public void parentStudentAttendance(View view) {
        Intent intent = new Intent(ParentDashboardScreen.this, AttendanceStudent.class);
        intent.putExtra("customID", customID);
        startActivity(intent);
    }

    public void parentStudentMarks(View view) {
        Intent intent = new Intent(ParentDashboardScreen.this, MarksStudent.class);
        intent.putExtra("customID", customID);
        startActivity(intent);
    }

    public void parentChatbot(View view) {
        startActivity(new Intent(ParentDashboardScreen.this, ChatbotScreen.class));
    }
}