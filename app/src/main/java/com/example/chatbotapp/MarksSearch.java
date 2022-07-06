package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MarksSearch extends AppCompatActivity {

    private EditText etFaculty, etSem, etBatch;
    private Button btnMarksSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_search);

        etFaculty = findViewById(R.id.etSearchFaculty);
        etSem = findViewById(R.id.etSearchSem);
        etBatch = findViewById(R.id.etBatchSearch);

        btnMarksSearch = findViewById(R.id.btnMarksSearch);

        btnMarksSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etFaculty.getText().toString().isEmpty() || etSem.getText().toString().isEmpty() || etBatch.getText().toString().isEmpty()) {
                    Toast.makeText(MarksSearch.this, "Field empty.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MarksSearch.this, MarksResult.class);
                    intent.putExtra("faculty", etFaculty.getText().toString());
                    intent.putExtra("sem", etSem.getText().toString());
                    intent.putExtra("batch", etBatch.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}