package com.example.chatbotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AllUsersAdapter;
import com.example.chatbotapp.adapters.AttendanceStudentAdapter;
import com.example.chatbotapp.adapters.OnlineAttendanceStudentAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AttendanceStudent extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    ArrayList<AttendanceModel> data;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private String customID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);
        progressDialog = new ProgressDialog(AttendanceStudent.this);
        recyclerView = findViewById(R.id.studentAttendance);

        customID = getIntent().getStringExtra("customID");
        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        getStudentAttendanceList();

//        RecyclerView StudentAttendanceList = (RecyclerView) findViewById(R.id.studentAttendance);
//        StudentAttendanceList.setLayoutManager(new LinearLayoutManager(AttendanceStudent.this));
//
//        Cursor cursor = new ChatAppDatabaseHelper(AttendanceStudent.this).viewAllAttendanceOfStudentBy(customID);
//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(AttendanceStudent.this, "No Attendance to show.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            int count = 0;
//            while(cursor.moveToNext())
//            {
//                AttendanceModel obj = new AttendanceModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
//                data.add(obj);
//            }
//        }
//
//        AttendanceStudentAdapter adapter = new AttendanceStudentAdapter(data, new AttendanceStudentAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(AttendanceModel j) {
////                Intent intent;
////                intent = new Intent(JournalActivity.this, UpdateJournal.class);
////                String a = String.valueOf(j.getId());
////                intent.putExtra("idKey", a);
////                intent.putExtra("userIDKey", userID);
////                startActivity(intent);
//            }
//        });
//
//        StudentAttendanceList.setAdapter(adapter);
    }

    private void getStudentAttendanceList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("StudentAttendance");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.whereContains("customID", customID);
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            if (e == null) {
                //We are initializing
                initStudentAttendanceList(objects);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initStudentAttendanceList(List<ParseObject> list) {

        OnlineAttendanceStudentAdapter attendanceStudentAdapter = new OnlineAttendanceStudentAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(attendanceStudentAdapter);
    }
}