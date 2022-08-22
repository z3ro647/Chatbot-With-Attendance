package com.example.chatbotapp.back4app;

import android.widget.Toast;

import com.parse.ParseObject;

public class OnLineDatabaseHelper {

    public void insertDemoUser(String phone, String email, String name, String password, String role, String faculty, String sem, String customID, String batch) {
        ParseObject user = new ParseObject("AppUser");
            user.put("phone", phone);
            user.put("email", email);
            user.put("name", name);
            user.put("password", password);
            user.put("role", role);
            user.put("faculty", faculty);
            user.put("sem", sem);
            user.put("customID", customID);
            user.put("batch", batch);
            user.saveInBackground(e -> {
                if (e == null) {
                } else {
                }
            });
    }
}
