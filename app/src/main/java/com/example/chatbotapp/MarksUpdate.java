package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;

public class MarksUpdate extends AppCompatActivity {

    private TextView tvMarksID, tvMarksEmail,tvMarksFaculty, etStudentMarksmark;
    private Button btnSubmitMarks;

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_update);

        tvMarksID = findViewById(R.id.tvMarksID);
        tvMarksEmail = findViewById(R.id.tvMarksEmail);
        tvMarksFaculty = findViewById(R.id.tvMarksFaculty);
        etStudentMarksmark = findViewById(R.id.etStudentMarksmark);

        btnSubmitMarks = findViewById(R.id.btnSubmitMarks);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        String marksID = getIntent().getStringExtra("marksID");
        String faculty = getIntent().getStringExtra("faculty");
        String email = getIntent().getStringExtra("email");
        String marks = getIntent().getStringExtra("marks");
        if(marks.isEmpty() || marks.equals("")) {
            etStudentMarksmark.setText("0");
        } else {
            etStudentMarksmark.setText(marks);
        }

        tvMarksID.setText(marksID);
        tvMarksEmail.setText(email);
        tvMarksFaculty.setText(faculty);

        btnSubmitMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etStudentMarksmark.getText().toString().isEmpty()) {
                    Toast.makeText(MarksUpdate.this, "Marks Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUpdateMarks = chatAppDatabaseHelper.updateMarks(Integer.parseInt(marksID), etStudentMarksmark.getText().toString());
                    if (checkUpdateMarks==true){
                        Toast.makeText(MarksUpdate.this, "Marks Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MarksUpdate.this, "Marks not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}