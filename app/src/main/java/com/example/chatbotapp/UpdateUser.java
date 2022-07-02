package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;

public class UpdateUser extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    private EditText etUpdatePhone, etUpdateEmail, etUpdateName, etUpdatePassword, etUpdateRole, etUpdateFaculty, etUpdateSem, etUpdateCustomID;

    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        etUpdatePhone = findViewById(R.id.etUpdatePhone);
        etUpdateEmail = findViewById(R.id.etUpdateEmail);
        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdatePassword = findViewById(R.id.etUpdatePassword);
        etUpdateRole = findViewById(R.id.etUpdateRole);
        etUpdateFaculty = findViewById(R.id.etUpdateFaculty);
        etUpdateSem = findViewById(R.id.etUpdateSem);
        etUpdateCustomID = findViewById(R.id.etUpdateCustomID);

        btnUpdate = findViewById(R.id.btnUpdate);

        int userID = getIntent().getIntExtra("userID", 0);
        //Toast.makeText(UpdateUser.this, "UserID is.: "+userID, Toast.LENGTH_SHORT).show();

        chatAppDatabaseHelper.viewOneUsers(userID);
        searchOneUser(userID);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUpdatePhone.getText().toString().isEmpty() || etUpdateEmail.getText().toString().isEmpty() || etUpdateName.getText().toString().isEmpty() || etUpdatePassword.getText().toString().isEmpty() || etUpdateRole.getText().toString().isEmpty() || etUpdateFaculty.getText().toString().isEmpty() || etUpdateSem.getText().toString().isEmpty() || etUpdateCustomID.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateUser.this, "Fields can not be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    chatAppDatabaseHelper.updateUser(userID, Long.parseLong(etUpdatePhone.getText().toString()), etUpdateEmail.getText().toString(), etUpdateName.getText().toString(), etUpdatePassword.getText().toString(), etUpdateRole.getText().toString(), etUpdateFaculty.getText().toString(), etUpdateSem.getText().toString(), etUpdateCustomID.getText().toString());
                    Toast.makeText(UpdateUser.this, "User Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void searchOneUser(int id) {
        long phone;
        String email = null, name = null, password = null, role = null, faculty = null, sem = null, customID = null;
        Cursor cursor = chatAppDatabaseHelper.viewOneUsers(id);
        while (cursor.moveToNext()) {

            phone = cursor.getLong(1);
            email = cursor.getString(2);
            name = cursor.getString(3);
            password = cursor.getString(4);
            role = cursor.getString(5);
            faculty = cursor.getString(6);
            sem = cursor.getString(7);
            customID = cursor.getString(8);

            String sPhone = String.valueOf(phone);

            etUpdatePhone.setText(sPhone);
            etUpdateEmail.setText(email);
            etUpdateName.setText(name);
            etUpdatePassword.setText(password);
            etUpdateRole.setText(role);
            etUpdateFaculty.setText(faculty);
            etUpdateSem.setText(sem);
            etUpdateCustomID.setText(customID);
        }
    }
}