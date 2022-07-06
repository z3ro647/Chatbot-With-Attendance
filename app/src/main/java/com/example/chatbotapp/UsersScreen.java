package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatbotapp.adapters.UsersAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.UsersModel;

import java.util.ArrayList;

public class UsersScreen extends AppCompatActivity {

    ArrayList<UsersModel> data;

    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_screen);

        btnAddUser = findViewById(R.id.btnAddUser);

        RecyclerView UsersList = (RecyclerView) findViewById(R.id.users);
        UsersList.setLayoutManager(new LinearLayoutManager(UsersScreen.this));

        Cursor cursor = new ChatAppDatabaseHelper(UsersScreen.this).viewAllUsers();
        data = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(UsersScreen.this, "No Users to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                UsersModel obj = new UsersModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                data.add(obj);
            }
        }

        UsersAdapter adapter = new UsersAdapter(data, new UsersAdapter.ItemClickListener() {
            @Override
            public void onItemClick(UsersModel j) {
                int a = j.getUserID();
                Intent intent;
                intent = new Intent(UsersScreen.this, UpdateUser.class);
                //Toast.makeText(UsersScreen.this, ""+a, Toast.LENGTH_SHORT).show();
                intent.putExtra("userID", a);
                //intent.putExtra("userIDKey", userID);
                startActivity(intent);
            }
        });

        UsersList.setAdapter(adapter);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersScreen.this, AddUser.class));
            }
        });
    }
}