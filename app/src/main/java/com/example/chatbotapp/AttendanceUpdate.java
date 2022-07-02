package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;

public class AttendanceUpdate extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    private TextView tvAttendanceID, tvAttendanceEmail;
    private Button btnSubmitAttendance;
    private EditText etStudentAttendanceRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_update);

        tvAttendanceID = findViewById(R.id.tvAttendanceID);
        tvAttendanceEmail = findViewById(R.id.tvAttendanceEmail);
        btnSubmitAttendance = findViewById(R.id.btnSubmitAttendance);
        etStudentAttendanceRemarks = findViewById(R.id.etStudentAttendanceRemarks);

        String attendanceID = getIntent().getStringExtra("attendanceID");
        String attendanceEmail = getIntent().getStringExtra("email");

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        tvAttendanceID.setText(attendanceID);
        tvAttendanceEmail.setText(attendanceEmail);

        btnSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etStudentAttendanceRemarks.getText().toString().isEmpty()) {
                    Toast.makeText(AttendanceUpdate.this, "Remarks can not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUpdateJournal = chatAppDatabaseHelper.updateAttendance(Integer.parseInt(tvAttendanceID.getText().toString()), etStudentAttendanceRemarks.getText().toString());
                    if (checkUpdateJournal==true){
                        Toast.makeText(AttendanceUpdate.this, "Attendance Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AttendanceUpdate.this, "Attendance not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}