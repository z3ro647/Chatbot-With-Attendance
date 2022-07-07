package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.UsersModel;

import java.util.ArrayList;

public class CreateAttendance extends AppCompatActivity {

    private TextView dateView;

    private EditText etFaculty, etSem, etBatch;

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;

    private Button btnCreateNewAttendance;

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attendance);

        dateView = (TextView) findViewById(R.id.tvShowDate);

        btnCreateNewAttendance = findViewById(R.id.btnCreateNewAttendance);

        etFaculty = findViewById(R.id.etFaculty);
        etSem = findViewById(R.id.etSem);
        etBatch = findViewById(R.id.etBatch);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        btnCreateNewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateView.getText().toString();
                if(etFaculty.getText().toString().isEmpty() || etSem.getText().toString().isEmpty() || etBatch.getText().toString().isEmpty() || dateView.getText().toString().equals("Date")) {
                    Toast.makeText(CreateAttendance.this, "Please fill the form correctly.", Toast.LENGTH_SHORT).show();
                } else {
                    //chatAppDatabaseHelper.viewAllStudents(etFaculty.getText().toString(), etSem.getText().toString());
                    Cursor cursor = new ChatAppDatabaseHelper(CreateAttendance.this).viewAllStudentsByBatch(etFaculty.getText().toString(), etSem.getText().toString(), etBatch.getText().toString());

                    if (cursor.getCount() == 0) {
                        Toast.makeText(CreateAttendance.this, "No Users to add.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int count = 0;
                        while(cursor.moveToNext())
                        {
                            UsersModel obj = new UsersModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                            //data.add(obj);
                            obj.getRole();
                            if (obj.getRole().equals("Student")) {
                                Boolean attendanceCreate = chatAppDatabaseHelper.createAttendance("", dateView.getText().toString(),etFaculty.getText().toString(), etSem.getText().toString(), obj.getPhone(), obj.getEmail(), obj.getName(), obj.getCustomID(), "", etBatch.getText().toString());
                                if(attendanceCreate == true) {
                                    Toast.makeText(CreateAttendance.this, "Attendence Created Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(CreateAttendance.this, "Attendence creation failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
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