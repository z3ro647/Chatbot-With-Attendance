package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceStudentAdapter;
import com.example.chatbotapp.adapters.MarksStudentAdapter;
import com.example.chatbotapp.adapters.OnlineAttendanceStudentAdapter;
import com.example.chatbotapp.adapters.OnlineMarksAdapter;
import com.example.chatbotapp.adapters.OnlineMarksStudentAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.MarksModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MarksStudent extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    ArrayList<MarksModel> data;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private String customID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_student);
        progressDialog = new ProgressDialog(MarksStudent.this);
        recyclerView = findViewById(R.id.studentMarks);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);
        customID = getIntent().getStringExtra("customID");

        getStudentMarksList();

//        RecyclerView StudentMarkList = (RecyclerView) findViewById(R.id.studentMarks);
//        StudentMarkList.setLayoutManager(new LinearLayoutManager(MarksStudent.this));
//
//        Cursor cursor = new ChatAppDatabaseHelper(MarksStudent.this).viewAllMarksOfStudentBy(customID);
//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(MarksStudent.this, "No Marks to show.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            int count = 0;
//            while(cursor.moveToNext())
//            {
//                MarksModel obj = new MarksModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
//                data.add(obj);
//            }
//        }
//
//        MarksStudentAdapter adapter = new MarksStudentAdapter(data, new MarksStudentAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(MarksModel j) {
////                Intent intent;
////                intent = new Intent(JournalActivity.this, UpdateJournal.class);
////                String a = String.valueOf(j.getId());
////                intent.putExtra("idKey", a);
////                intent.putExtra("userIDKey", userID);
////                startActivity(intent);
//            }
//        });
//
//        StudentMarkList.setAdapter(adapter);
    }

    private void getStudentMarksList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("StudentMarks");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.whereContains("customID", customID);
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            if (e == null) {
                //We are initializing
                initStudentMarksList(objects);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initStudentMarksList(List<ParseObject> list) {

        OnlineMarksStudentAdapter onlineMarksStudentAdapter = new OnlineMarksStudentAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(onlineMarksStudentAdapter);
    }
}