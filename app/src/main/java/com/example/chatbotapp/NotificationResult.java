package com.example.chatbotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.adapters.NotificationAdapter;
import com.example.chatbotapp.adapters.OnlineAttendanceAdapter;
import com.example.chatbotapp.adapters.OnlineNotificationAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.NotificationModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class NotificationResult extends AppCompatActivity {

    ChatAppDatabaseHelper chatAppDatabaseHelper;
    ArrayList<NotificationModel> data;
    String customDate;
    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private View popupInputDialogView;
    private Button btnPopUpSaveNotification, btnPopUpCancleNotification;
    private EditText etNotificationTitle, etNotificationDescription;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);
        progressDialog = new ProgressDialog(NotificationResult.this);
        recyclerView = findViewById(R.id.notifications);

        customDate = getIntent().getStringExtra("customDate");

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        RecyclerView NotificationList = (RecyclerView) findViewById(R.id.notifications);
        NotificationList.setLayoutManager(new LinearLayoutManager(NotificationResult.this));

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllNotificationByDate(customDate);

        getNotificationList();

//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(NotificationResult.this, "No Notification to show.", Toast.LENGTH_SHORT).show();
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
//                intent = new Intent(NotificationResult.this, NotificationUpdate.class);
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

    private void getNotificationList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("NotificationTable");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.whereContains("customDate", customDate);
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

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NotificationResult.this);
            alertDialogBuilder.setTitle("Edit Notification");
            alertDialogBuilder.setCancelable(true);
            //We are initializing PopUp Views with title and description parameters of Parse Object
            initPopupViewControls(parseObject.getString("customDate"), parseObject.getString("notificationTitle"), parseObject.getString("notificationDescription"));
            alertDialogBuilder.setView(popupInputDialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            btnPopUpSaveNotification.setOnClickListener(saveTodoButtonView -> {
                alertDialog.cancel();
                progressDialog.show();
                parseObject.put("notificationTitle", etNotificationTitle.getText().toString());
                parseObject.put("notificationDescription", etNotificationDescription.getText().toString());
                parseObject.put("customDate", dateView.getText().toString());
                parseObject.saveInBackground(e1 -> {
                    progressDialog.dismiss();
                    if (e1 == null) {
                        Toast.makeText(NotificationResult.this, "Notification Updated", Toast.LENGTH_SHORT).show();
                        getNotificationList();
                    } else {
                        showAlert("Error", e1.getMessage());
                    }
                });
            });
            btnPopUpCancleNotification.setOnClickListener(cancelButtonView -> alertDialog.cancel());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(onlineNotificationAdapter);
    }

    private void initPopupViewControls(String customDate, String notificationTitle, String notificationDescription) {
        LayoutInflater layoutInflater = LayoutInflater.from(NotificationResult.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.custom_edit_notification_dialog, null);
        dateView = popupInputDialogView.findViewById(R.id.tvShowPopUpNotificationDate);
        etNotificationTitle = popupInputDialogView.findViewById(R.id.etPopUpNotificationTitle);
        etNotificationDescription = popupInputDialogView.findViewById(R.id.etPopUpNotificationDescription);
        btnPopUpSaveNotification = popupInputDialogView.findViewById(R.id.btnPopUpSaveNotification);
        btnPopUpCancleNotification = popupInputDialogView.findViewById(R.id.btnPopUpCancleNotification);

        dateView.setText(customDate);
        etNotificationTitle.setText(notificationTitle);
        etNotificationDescription.setText(notificationDescription);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationResult.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(NotificationResult.this, NotificationResult.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
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