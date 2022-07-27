package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    private Button btnChatBot;
    private Button btnBtn;

    private Button btnPickDateTime;

    private TextView tvDateTime;

    private Button btnDateInsert;

    private Button btnAdminDashboard;

    private Button btnSignIn;

    private EditText etPhone, etPassword;

    private TextView signUp;

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;

    private TextView dateView;

    private Button btnCreate;

    private Button btnDemoInsert;
    private TextView textView38;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnChatBot = findViewById(R.id.btnChatBot);
        btnDemoInsert = findViewById(R.id.btnDemoInsert);

        btnSignIn = findViewById(R.id.btnSignIn);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        signUp = findViewById(R.id.signUp);

        textView38 = findViewById(R.id.textView38);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etPhone.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Phone or Password can not be empty.", Toast.LENGTH_SHORT).show();
                } else if(etPhone.getText().toString().equals("123") && etPassword.getText().toString().equals("admin")) {
                    //startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
                    Intent i = new Intent(MainActivity.this, AdminDashboardScreen.class);
                    finishAffinity();
                    startActivity(i);
                } else {
                    Cursor cursor = chatAppDatabaseHelper.searchOneUserPhoneAndPassword(etPhone.getText().toString(), etPassword.getText().toString());
                    if (cursor.getCount() == 0) {
                        Toast.makeText(MainActivity.this, "Phone and Password does not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        cursor.moveToFirst();
                        if(cursor.getString(5).equals("Student")) {
                            //Toast.makeText(MainActivity.this, "Student", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, StudentDashboardScreen.class));
                            Intent intent = new Intent(MainActivity.this, StudentDashboardScreen.class);
                            intent.putExtra("customID", cursor.getString(8));
                            intent.putExtra("batch", cursor.getString(9));
                            startActivity(intent);
                        } else if (cursor.getString(5).equals("Parent")) {
                            //Toast.makeText(MainActivity.this, "Parent", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, ParentDashboardScreen.class));
                            Intent intent = new Intent(MainActivity.this, ParentDashboardScreen.class);
                            intent.putExtra("customID", cursor.getString(8));
                            intent.putExtra("batch", cursor.getString(9));
                            startActivity(intent);
                        } else if (cursor.getString(5).equals("Teacher")) {
                            //Toast.makeText(MainActivity.this, "Teacher", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
                        } else {
                            //Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
                        }
                    }
                }
            }
        });

        textView38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.tutorialspoint.com/android/android_tutorial.pdf"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ChatbotScreen.class);
                startActivity(i);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpScreen.class));
            }
        });

        btnDemoInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatAppDatabaseHelper.insertUser(1234567890, "z3ro647@gmail.com", "Vivek", "123", "Student", "IT", "7", "1001", "1");
                chatAppDatabaseHelper.insertUser(1234567891, "prijal@gmail.com", "Prabesh", "123", "Teacher", "IT", "7", "2001", "");
                chatAppDatabaseHelper.insertUser(1234567892, "admin@gmail.com", "Admin", "123", "Admin", "Admin", "Admin", "3001", "");
                chatAppDatabaseHelper.insertUser(1234567893, "suman@gmail.com", "Suman", "123", "Parent", "IT", "7", "1001", "1");
                chatAppDatabaseHelper.insertUser(1234567894, "bishnu@gmail.com", "Bishnu", "123", "Student", "IT", "7", "1002", "1");
            }
        });

//        btnBtn = findViewById(R.id.button);
//        btnPickDateTime = findViewById(R.id.btnPick);
//        btnAdminDashboard = findViewById(R.id.btnAdmin);

//        tvDateTime = findViewById(R.id.tvDateTime);
//        dateView = (TextView) findViewById(R.id.textView3);
//
//        btnDateInsert = findViewById(R.id.btnDateInsert);
//
//        btnCreate = findViewById(R.id.create);

//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, CreateAttendance.class));
//            }
//        });



//        btnBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Boolean insertJournal = db.insertJournal(1234567890, "Demo Title", "Demo Description", 0.0, 0.0);
//                if(insertJournal == true) {
//                    Toast.makeText(MainActivity.this, "New Journal Added", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Failed to Add Journal", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        btnPickDateTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this,year, month,day);
//                datePickerDialog.show();
//            }
//        });
//
//        btnDateInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(MainActivity.this, "Insert", Toast.LENGTH_SHORT).show();
//                db.insertDate("06/04/2016");
//            }
//        });
//
//        btnAdminDashboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, AdminDashboardScreen.class);
//                startActivity(i);
//            }
//        });
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myDay = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        tvDateTime.setText("Year: " + myYear + "\n" +
                "Month: " + myMonth + "\n" +
                "Day: " + myDay + "\n" +
                "Hour: " + myHour + "\n" +
                "Minute: " + myMinute);
    }

    @SuppressWarnings("deprecation")
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