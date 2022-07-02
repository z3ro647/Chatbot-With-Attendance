package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.UsersModel;

import java.util.ArrayList;

public class AttendanceResult extends AppCompatActivity {

    ArrayList<AttendanceModel> data;
    ChatAppDatabaseHelper chatAppDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_result);

        String customDate = getIntent().getStringExtra("customDate");
        String faculty = getIntent().getStringExtra("faculty");
        String sem = getIntent().getStringExtra("sem");

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        RecyclerView AttendanceList = (RecyclerView) findViewById(R.id.attendances);
        AttendanceList.setLayoutManager(new LinearLayoutManager(AttendanceResult.this));

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllAttendanceBy(customDate, faculty, sem);

        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(AttendanceResult.this, "No Attendance to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                AttendanceModel obj = new AttendanceModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                data.add(obj);
            }
        }

        AttendanceAdapter adapter = new AttendanceAdapter(data, new AttendanceAdapter.ItemClickListener() {
            @Override
            public void onItemClick(AttendanceModel attendanceModel) {
                Intent intent;
                intent = new Intent(AttendanceResult.this, AttendanceUpdate.class);
                String a = String.valueOf(attendanceModel.getAttendanceID());
                intent.putExtra("attendanceID", a);
                intent.putExtra("email", attendanceModel.getEmail());
                startActivity(intent);
            }
        });

        AttendanceList.setAdapter(adapter);
    }
}