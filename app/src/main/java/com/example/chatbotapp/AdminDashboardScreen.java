package com.example.chatbotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AllUsersAdapter;
import com.example.chatbotapp.adapters.AttendanceAdapter;
import com.example.chatbotapp.adapters.NotificationAdapter;
import com.example.chatbotapp.adapters.OnlineNotificationAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.NotificationModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminDashboardScreen extends AppCompatActivity {

    private Button btnInsertPhone;

    private Button btnViewAllUsers;
    private Button btnAttendance;
    private Button btnMarks;

    ChatAppDatabaseHelper chatAppDatabaseHelper;

    ArrayList<NotificationModel> data;

    private TextView dateView;

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;

    private ProgressDialog progressDialog;
    private View popupInputDialogView;
    private EditText etPopUpNotificationTitle, etPopUpNotificationDescription;
    private Button btnPopUpSaveNotification, btnPopUpCancelNotification;
//    private Button btnPopUpSaveNotification, btnPopUpCancleNotification;
//    private EditText etNotificationTitle, etNotificationDescription;
    private RecyclerView recyclerView;
    private String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard_screen);

        dateView = (TextView) findViewById(R.id.tvShowPopUpNotificationDate);

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        currentDateandTime = sdf.format(new Date());

//        Toast.makeText(this, "Current Date: " + currentDateandTime, Toast.LENGTH_SHORT).show();

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        progressDialog = new ProgressDialog(AdminDashboardScreen.this);
        recyclerView = findViewById(R.id.notificationsAdmin);

        getAllNotificationList();

//        btnViewAllUsers = findViewById(R.id.btnViewAllUsers);
//        btnAttendance = findViewById(R.id.btnAttendance);
//        btnMarks = findViewById(R.id.btnMarks);
//
//        btnViewAllUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AdminDashboardScreen.this, UsersScreen.class));
//            }
//        });
//
//        btnAttendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AdminDashboardScreen.this, AttendanceScreen.class));
//            }
//        });
//
//        btnMarks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AdminDashboardScreen.this, MarksScreen.class));
//            }
//        });

        RecyclerView NotificationList = (RecyclerView) findViewById(R.id.notificationsAdmin);
        NotificationList.setLayoutManager(new LinearLayoutManager(AdminDashboardScreen.this));

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllNotificationByDate(currentDateandTime);

//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(AdminDashboardScreen.this, "No Notification to show.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            int count = 0;
//            while(cursor.moveToNext())
//            {
//                NotificationModel obj = new NotificationModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//                data.add(obj);
//            }
//        }
//
//        NotificationAdapter adapter = new NotificationAdapter(data, new NotificationAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(NotificationModel notificationModel) {
//                Intent intent;
//                intent = new Intent(AdminDashboardScreen.this, NotificationUpdate.class);
//                String a = String.valueOf(notificationModel.getNotificationID());
//                intent.putExtra("attendanceID", a);
//                intent.putExtra("customDate", notificationModel.getCustomDate());
//                intent.putExtra("notificationTitle", notificationModel.getTitle());
//                intent.putExtra("notificationDescription", notificationModel.getDescription());
//                startActivity(intent);
//            }
//        });
//
//        NotificationList.setAdapter(adapter);
    }

    private void getAllNotificationList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("NotificationTable");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.whereContains("customDate", currentDateandTime);
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            if (e == null) {
                //We are initializing
                initNotificationList(objects);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initNotificationList(List<ParseObject> list) {

        OnlineNotificationAdapter onlineNotificationAdapter = new OnlineNotificationAdapter(list, this);

        onlineNotificationAdapter.onEditListener.observe(this, parseObject -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminDashboardScreen.this);
            alertDialogBuilder.setTitle("Update a Notification");
            alertDialogBuilder.setCancelable(true);
            //We are initializing PopUp Views with title and description parameters of Parse Object
            initPopupViewControls(parseObject.getString("notificationTitle"), parseObject.getString("notificationDescription"), parseObject.getString("customDate"));
            alertDialogBuilder.setView(popupInputDialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            btnPopUpSaveNotification.setOnClickListener(saveTodoButtonView -> {
                alertDialog.cancel();
                progressDialog.show();
                parseObject.put("notificationTitle", etPopUpNotificationTitle.getText().toString());
                parseObject.put("notificationDescription", etPopUpNotificationDescription.getText().toString());
                parseObject.put("customDate", dateView.getText().toString());
                parseObject.saveInBackground(e1 -> {
                    progressDialog.dismiss();
                    if (e1 == null) {
                        Toast.makeText(AdminDashboardScreen.this, "Notification Updated", Toast.LENGTH_SHORT).show();
                        getAllNotificationList();
                    } else {
                        showAlert("Error", e1.getMessage());
                    }
                });
            });
            btnPopUpCancelNotification.setOnClickListener(cancelButtonView -> alertDialog.cancel());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(onlineNotificationAdapter);
    }

    private void initPopupViewControls(String notificationTitle, String notificationDescription, String customDate) {
        LayoutInflater layoutInflater = LayoutInflater.from(AdminDashboardScreen.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.custom_edit_notification_dialog, null);
        etPopUpNotificationTitle = popupInputDialogView.findViewById(R.id.etPopUpNotificationTitle);
        etPopUpNotificationDescription = popupInputDialogView.findViewById(R.id.etPopUpNotificationDescription);
        btnPopUpSaveNotification = popupInputDialogView.findViewById(R.id.btnPopUpSaveNotification);
        btnPopUpCancelNotification = popupInputDialogView.findViewById(R.id.btnPopUpCancleNotification);
        dateView = popupInputDialogView.findViewById(R.id.tvShowPopUpNotificationDate);

        etPopUpNotificationTitle.setText(notificationTitle);
        etPopUpNotificationDescription.setText(notificationDescription);
        dateView.setText(customDate);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboardScreen.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(AdminDashboardScreen.this, AdminDashboardScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                        Toast.LENGTH_SHORT)
//                .show();
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

    public void takeAttendance(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, AttendanceScreen.class));
    }

    public void takeMarks(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, MarksScreen.class));
    }

    public void takeUsers(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, UsersScreen.class));
    }

    public void takeNotification(View view) {
        startActivity(new Intent(AdminDashboardScreen.this, NotificationScreen.class));
    }
}