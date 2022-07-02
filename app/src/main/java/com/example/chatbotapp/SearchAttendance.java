package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchAttendance extends AppCompatActivity {

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;

    private TextView dateView;
    private Button btnSearchAttendanceWithDate;
    private EditText etSearchFaculty, etSearchSem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_attendance);

        dateView = findViewById(R.id.tvDisplayDate);

        etSearchFaculty = findViewById(R.id.etSearchFaculty);
        etSearchSem = findViewById(R.id.etSearchSem);
        btnSearchAttendanceWithDate = findViewById(R.id.btnSearchAttendanceWithDate);

        btnSearchAttendanceWithDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateView.getText().toString().equals("Date")) {
                    Toast.makeText(SearchAttendance.this, "Please select Date.", Toast.LENGTH_SHORT).show();
                } else if(etSearchFaculty.getText().toString().isEmpty() || etSearchSem.getText().toString().isEmpty()) {
                    Toast.makeText(SearchAttendance.this, "Please Fill the Faculty and Sem", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchAttendance.this, AttendanceResult.class);
                    intent.putExtra("customDate", dateView.getText().toString());
                    intent.putExtra("faculty", etSearchFaculty.getText().toString());
                    intent.putExtra("sem", etSearchSem.getText().toString());
                    startActivity(intent);
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