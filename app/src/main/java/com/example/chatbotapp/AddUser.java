package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;

public class AddUser extends AppCompatActivity {

    private Button btnNewSubmit;
    private EditText etNewPhone, etNewEmail, etNewName, etNewPassword, etNewRole, etNewFaculty, etNewSem, etNewCustomID, etBatch;

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        btnNewSubmit = findViewById(R.id.btnNewSubmit);
        etNewPhone = findViewById(R.id.etNewPhone);
        etNewEmail = findViewById(R.id.etNewEmail);
        etNewName = findViewById(R.id.etNewName);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewRole = findViewById(R.id.etNewRole);
        etNewFaculty = findViewById(R.id.etNewFaculty);
        etNewSem = findViewById(R.id.etNewSem);
        etNewCustomID = findViewById(R.id.etNewCustomID);
        etBatch = findViewById(R.id.etBatch);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(AddUser.this);

        btnNewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNewPhone.getText().toString().isEmpty() || etNewEmail.getText().toString().isEmpty() || etNewName.getText().toString().isEmpty() ||  etNewPassword.getText().toString().isEmpty() || etNewRole.getText().toString().isEmpty() || etNewFaculty.getText().toString().isEmpty() || etNewSem.getText().toString().isEmpty() || etNewCustomID.getText().toString().isEmpty() || etBatch.getText().toString().isEmpty()) {
                    Toast.makeText(AddUser.this, "Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean insertUser = chatAppDatabaseHelper.insertUser(Long.parseLong(etNewPhone.getText().toString()), etNewEmail.getText().toString(), etNewName.getText().toString(), etNewPassword.getText().toString(), etNewRole.getText().toString(), etNewFaculty.getText().toString(), etNewSem.getText().toString(), etNewCustomID.getText().toString(), etBatch.getText().toString());
                    if(insertUser == true) {
                        Toast.makeText(AddUser.this, "New User Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddUser.this, "Failed to Add User", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}