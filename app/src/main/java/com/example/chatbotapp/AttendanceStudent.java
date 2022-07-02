package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceStudentAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;

import java.util.ArrayList;

public class AttendanceStudent extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    ArrayList<AttendanceModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);

        String customID = getIntent().getStringExtra("customID");
        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        RecyclerView StudentAttendanceList = (RecyclerView) findViewById(R.id.studentAttendance);
        StudentAttendanceList.setLayoutManager(new LinearLayoutManager(AttendanceStudent.this));

        Cursor cursor = new ChatAppDatabaseHelper(AttendanceStudent.this).viewAllAttendanceOfStudentBy(customID);
        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(AttendanceStudent.this, "No Attendance to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                AttendanceModel obj = new AttendanceModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                data.add(obj);
            }
        }

        AttendanceStudentAdapter adapter = new AttendanceStudentAdapter(data, new AttendanceStudentAdapter.ItemClickListener() {
            @Override
            public void onItemClick(AttendanceModel j) {
//                Intent intent;
//                intent = new Intent(JournalActivity.this, UpdateJournal.class);
//                String a = String.valueOf(j.getId());
//                intent.putExtra("idKey", a);
//                intent.putExtra("userIDKey", userID);
//                startActivity(intent);
            }
        });

        StudentAttendanceList.setAdapter(adapter);
    }
}