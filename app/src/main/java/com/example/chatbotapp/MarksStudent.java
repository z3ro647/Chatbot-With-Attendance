package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceStudentAdapter;
import com.example.chatbotapp.adapters.MarksStudentAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.MarksModel;

import java.util.ArrayList;

public class MarksStudent extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    ArrayList<MarksModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_student);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);
        String customID = getIntent().getStringExtra("customID");

        RecyclerView StudentMarkList = (RecyclerView) findViewById(R.id.studentMarks);
        StudentMarkList.setLayoutManager(new LinearLayoutManager(MarksStudent.this));

        Cursor cursor = new ChatAppDatabaseHelper(MarksStudent.this).viewAllMarksOfStudentBy(customID);
        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(MarksStudent.this, "No Marks to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                MarksModel obj = new MarksModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                data.add(obj);
            }
        }

        MarksStudentAdapter adapter = new MarksStudentAdapter(data, new MarksStudentAdapter.ItemClickListener() {
            @Override
            public void onItemClick(MarksModel j) {
//                Intent intent;
//                intent = new Intent(JournalActivity.this, UpdateJournal.class);
//                String a = String.valueOf(j.getId());
//                intent.putExtra("idKey", a);
//                intent.putExtra("userIDKey", userID);
//                startActivity(intent);
            }
        });

        StudentMarkList.setAdapter(adapter);
    }
}