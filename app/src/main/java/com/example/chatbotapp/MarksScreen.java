package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MarksScreen extends AppCompatActivity {

    private Button btnCreateMarksScreen, btnSearchMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_screen);

        btnCreateMarksScreen = findViewById(R.id.btnCreateMarksScreen);
        btnSearchMarks = findViewById(R.id.btnSearchMarks);

        btnCreateMarksScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarksScreen.this, MarksCreate.class));
            }
        });

        btnSearchMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarksScreen.this, MarksSearch.class));
            }
        });
    }
}