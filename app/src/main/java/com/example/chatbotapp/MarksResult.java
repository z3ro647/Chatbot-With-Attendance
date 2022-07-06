package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceAdapter;
import com.example.chatbotapp.adapters.MarksAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.MarksModel;

import java.util.ArrayList;

public class MarksResult extends AppCompatActivity {

    ArrayList<MarksModel> data;
    ChatAppDatabaseHelper chatAppDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_result);

        String faculty = getIntent().getStringExtra("faculty");
        String sem = getIntent().getStringExtra("sem");
        String batch = getIntent().getStringExtra("batch");

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        RecyclerView MarksList = (RecyclerView) findViewById(R.id.marks);
        MarksList.setLayoutManager(new LinearLayoutManager(MarksResult.this));

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllMarksBy(faculty, sem, batch);

        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(MarksResult.this, "No Marks to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                MarksModel obj = new MarksModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                data.add(obj);
            }
        }

        MarksAdapter adapter = new MarksAdapter(data, new MarksAdapter.ItemClickListener() {
            @Override
            public void onItemClick(MarksModel marksModel) {
                Intent intent;
                intent = new Intent(MarksResult.this, MarksUpdate.class);
                String a = String.valueOf(marksModel.getMarksID());
                intent.putExtra("marksID", a);
                intent.putExtra("faculty", marksModel.getFaculty());
                intent.putExtra("email", marksModel.getEmail());
                intent.putExtra("marks", marksModel.getMarks());
                startActivity(intent);
            }
        });

        MarksList.setAdapter(adapter);

    }
}