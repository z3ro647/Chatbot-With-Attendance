package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationScreen extends AppCompatActivity {

    private Button btnCreateNotification, btnSearchNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_screen);

        btnCreateNotification = findViewById(R.id.btnCreateNotification);
        btnSearchNotification = findViewById(R.id.btnSearchNotification);

        btnCreateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationScreen.this, NotificationCreate.class));
            }
        });

        btnSearchNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationScreen.this, NotificationSearch.class));
            }
        });
    }
}