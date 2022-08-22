package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NotificationCreate extends AppCompatActivity {

    private TextView dateView;

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;

    private Button btnCreateNewNotification;
    private EditText etNotificationTitle, etNotificationDescription;

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_create);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);
        progressDialog = new ProgressDialog(NotificationCreate.this);

        dateView = (TextView) findViewById(R.id.tvShowNotificationDate);

        btnCreateNewNotification = findViewById(R.id.btnCreateNewNotification);

        etNotificationTitle = findViewById(R.id.etNotificationTitle);
        etNotificationDescription = findViewById(R.id.etNotificationDescription);

        btnCreateNewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNotificationTitle.getText().toString().isEmpty() || etNotificationDescription.getText().toString().isEmpty()) {
                    Toast.makeText(NotificationCreate.this, "Field is empty.", Toast.LENGTH_SHORT).show();
                } else if (dateView.getText().toString().equals("Date")) {
                    Toast.makeText(NotificationCreate.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                } else {
//                    Boolean notificationCreate = chatAppDatabaseHelper.createNotification(dateView.getText().toString(), etNotificationTitle.getText().toString(), etNotificationDescription.getText().toString());
//                    if(notificationCreate == true) {
//                        Toast.makeText(NotificationCreate.this, "Notification Created Successfuly", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        Toast.makeText(NotificationCreate.this, "Notification Creation failed", Toast.LENGTH_SHORT).show();
//                    }

                    ParseObject notificationTable = new ParseObject("NotificationTable");
                    notificationTable.put("notificationTitle", etNotificationTitle.getText().toString());
                    notificationTable.put("notificationDescription", etNotificationDescription.getText().toString());
                    notificationTable.put("customDate", dateView.getText().toString());
                    notificationTable.saveInBackground();
                    finish();
                }
            }
        });
    }


    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                        Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}