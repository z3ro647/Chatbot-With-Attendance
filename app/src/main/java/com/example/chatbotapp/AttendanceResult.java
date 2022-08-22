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

import com.example.chatbotapp.adapters.AllUsersAdapter;
import com.example.chatbotapp.adapters.AttendanceAdapter;
import com.example.chatbotapp.adapters.OnlineAttendanceAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.UsersModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AttendanceResult extends AppCompatActivity {

    ArrayList<AttendanceModel> data;
    ChatAppDatabaseHelper chatAppDatabaseHelper;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private View popupInputDialogView;
    private String customDate, faculty, sem, batch;
    private Button saveAttendanceButton, cancleAttendanceButton;
    private EditText etPopUpRemark;
    private TextView tvPopUpName, tvPopUpEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_result);
        progressDialog = new ProgressDialog(AttendanceResult.this);
        recyclerView = findViewById(R.id.attendances);

        customDate = getIntent().getStringExtra("customDate");
        faculty = getIntent().getStringExtra("faculty");
        sem = getIntent().getStringExtra("sem");
        batch = getIntent().getStringExtra("batch");

        getStudentAttendanceList();

//        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);
//
//        RecyclerView AttendanceList = (RecyclerView) findViewById(R.id.attendances);
//        AttendanceList.setLayoutManager(new LinearLayoutManager(AttendanceResult.this));
//
//        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllAttendanceBy(customDate, faculty, sem, batch);
//
//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(AttendanceResult.this, "No Attendance to show.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            int count = 0;
//            while(cursor.moveToNext())
//            {
//                AttendanceModel obj = new AttendanceModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10));
//                data.add(obj);
//            }
//        }
//
//        AttendanceAdapter adapter = new AttendanceAdapter(data, new AttendanceAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(AttendanceModel attendanceModel) {
//                Intent intent;
//                intent = new Intent(AttendanceResult.this, AttendanceUpdate.class);
//                String a = String.valueOf(attendanceModel.getAttendanceID());
//                intent.putExtra("attendanceID", a);
//                intent.putExtra("email", attendanceModel.getEmail());
//                startActivity(intent);
//            }
//        });
//
//        AttendanceList.setAdapter(adapter);
    }

    private void getStudentAttendanceList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("StudentAttendance");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.whereContains("customDate", customDate);
        query.whereContains("faculty", faculty);
        query.whereContains("sem", sem);
        query.whereContains("batch", batch);
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            if (e == null) {
                //We are initializing
                initStudentAttendanceList(objects);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initStudentAttendanceList(List<ParseObject> list) {

        OnlineAttendanceAdapter onlineAttendanceAdapter = new OnlineAttendanceAdapter(list, this);

        onlineAttendanceAdapter.onEditListener.observe(this, parseObject -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AttendanceResult.this);
            alertDialogBuilder.setTitle("Edit attendance");
            alertDialogBuilder.setCancelable(true);
            //We are initializing PopUp Views with title and description parameters of Parse Object
            initPopupViewControls(parseObject.getString("name"), parseObject.getString("email"), parseObject.getString("remark"));
            alertDialogBuilder.setView(popupInputDialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            saveAttendanceButton.setOnClickListener(saveTodoButtonView -> {
                alertDialog.cancel();
                progressDialog.show();
                parseObject.put("remark", etPopUpRemark.getText().toString());
                parseObject.saveInBackground(e1 -> {
                    progressDialog.dismiss();
                    if (e1 == null) {
                        Toast.makeText(AttendanceResult.this, "Attendence Updated", Toast.LENGTH_SHORT).show();
                        getStudentAttendanceList();
                    } else {
                        showAlert("Error", e1.getMessage());
                    }
                });
            });
            cancleAttendanceButton.setOnClickListener(cancelButtonView -> alertDialog.cancel());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(onlineAttendanceAdapter);
    }

    private void initPopupViewControls(String name, String email, String remark) {
        LayoutInflater layoutInflater = LayoutInflater.from(AttendanceResult.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.custom_edit_attendance_dialog, null);
        tvPopUpName = popupInputDialogView.findViewById(R.id.tvAttendancePopUpShowName);
        tvPopUpEmail = popupInputDialogView.findViewById(R.id.tvAttendancePopUpShowEmail);
        etPopUpRemark = popupInputDialogView.findViewById(R.id.etAttendancePopUpRemarks);
        saveAttendanceButton = popupInputDialogView.findViewById(R.id.btnAttendancePopUpEditSave);
        cancleAttendanceButton = popupInputDialogView.findViewById(R.id.btnAttendancePopUpEditCancle);

        tvPopUpName.setText(name);
        tvPopUpEmail.setText(email);
        etPopUpRemark.setText(remark);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceResult.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(AttendanceResult.this, AttendanceResult.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}