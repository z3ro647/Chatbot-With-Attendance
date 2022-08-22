package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.UsersModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MarksCreate extends AppCompatActivity {

    private EditText etCreateMarksFaculty, etCreateMarksSem, etCreateMarksBatch;
    private Button btnCreateMarks;

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_create);

        etCreateMarksFaculty = findViewById(R.id.etCreateMarksFaculty);
        etCreateMarksSem = findViewById(R.id.etCreateMarksSem);
        etCreateMarksBatch = findViewById(R.id.etCreateMarksBatch);

        btnCreateMarks = findViewById(R.id.btnCreateMarks);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        btnCreateMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCreateMarksFaculty.getText().toString().isEmpty() || etCreateMarksSem.getText().toString().isEmpty() || etCreateMarksBatch.getText().toString().isEmpty()) {
                    Toast.makeText(MarksCreate.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = new ChatAppDatabaseHelper(MarksCreate.this).viewAllStudentsByBatch(etCreateMarksFaculty.getText().toString(), etCreateMarksSem.getText().toString(), etCreateMarksBatch.getText().toString());

                    ParseQuery<ParseObject> allUsers = ParseQuery.getQuery("AppUser");
                    ParseQuery<ParseObject> studentMarks = ParseQuery.getQuery("StudentMarks");

                    // Use this code to get all the students
                    allUsers.orderByDescending("createdAt");
                    allUsers.whereContains("faculty", etCreateMarksFaculty.getText().toString());
                    allUsers.whereContains("sem", etCreateMarksSem.getText().toString());
                    allUsers.whereContains("batch", etCreateMarksBatch.getText().toString());
                    allUsers.whereContains("role", "Student");
                    allUsers.findInBackground((objects, e) -> {
                        //progressDialog.dismiss();
                        if (e == null) {
                            //Toast.makeText(CreateAttendance.this, "Object Len: " + objects.size(), Toast.LENGTH_SHORT).show();
                            addStudentMarks(objects);
                        } else {
                            Toast.makeText(MarksCreate.this, "No user Found", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    if (cursor.getCount() == 0) {
//                        Toast.makeText(MarksCreate.this, "No Users to add.", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        int count = 0;
//                        while(cursor.moveToNext())
//                        {
//                            UsersModel obj = new UsersModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
//                            //data.add(obj);
//                            //obj.getRole();
//                            if (obj.getRole().equals("Student")) {
//                                chatAppDatabaseHelper.createMarks(etCreateMarksFaculty.getText().toString(), etCreateMarksSem.getText().toString(), obj.getPhone(), obj.getEmail(), obj.getName(), obj.getCustomID(), "", etCreateMarksBatch.getText().toString());
//                            }
//                        }
//                        //Toast.makeText(MarksCreate.this, "Marks Created", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
                }
            }
        });
    }

    private void addStudentMarks(List<ParseObject> list) {
        ParseObject studentAttendance = new ParseObject("StudentMarks");
        for (int i = 0; i< list.size(); i++) {
            studentAttendance.put("faculty", etCreateMarksFaculty.getText().toString());
            studentAttendance.put("sem", etCreateMarksSem.getText().toString());
            studentAttendance.put("phone", list.get(i).getString("phone"));
            studentAttendance.put("email", list.get(i).getString("email"));
            studentAttendance.put("name", list.get(i).getString("name"));
            studentAttendance.put("customID", list.get(i).getString("customID"));
            studentAttendance.put("marks", "");
            studentAttendance.put("batch", etCreateMarksBatch.getText().toString());
            studentAttendance.saveInBackground();
        }
    }
}