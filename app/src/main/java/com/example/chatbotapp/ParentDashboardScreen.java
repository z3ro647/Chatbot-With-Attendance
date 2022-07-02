package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParentDashboardScreen extends AppCompatActivity {

    private Button btnChatbotParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard_screen);

        btnChatbotParent = findViewById(R.id.btnChatbotParent);

        btnChatbotParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParentDashboardScreen.this, ChatbotScreen.class));
            }
        });
    }
}