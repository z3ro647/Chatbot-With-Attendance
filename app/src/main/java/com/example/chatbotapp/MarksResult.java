package com.example.chatbotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AttendanceAdapter;
import com.example.chatbotapp.adapters.MarksAdapter;
import com.example.chatbotapp.adapters.OnlineAttendanceAdapter;
import com.example.chatbotapp.adapters.OnlineMarksAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.AttendanceModel;
import com.example.chatbotapp.models.MarksModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MarksResult extends AppCompatActivity {

    ArrayList<MarksModel> data;
    ChatAppDatabaseHelper chatAppDatabaseHelper;

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private View popupInputDialogView;

    private String faculty, sem, batch;
    private TextView tvPopUpSem, tvPopUpMarksEmail, tvPopUpMarksFaculty;
    private EditText etPopUpStudentMarksmark;
    private Button btnPopUpSaveMarks, btnPopUpCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_result);

        faculty = getIntent().getStringExtra("faculty");
        sem = getIntent().getStringExtra("sem");
        batch = getIntent().getStringExtra("batch");

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        RecyclerView MarksList = (RecyclerView) findViewById(R.id.marks);
        MarksList.setLayoutManager(new LinearLayoutManager(MarksResult.this));

        progressDialog = new ProgressDialog(MarksResult.this);
        recyclerView = findViewById(R.id.marks);
        getStudentMarksList();

        Cursor cursor = new ChatAppDatabaseHelper(this).viewAllMarksBy(faculty, sem, batch);

//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(MarksResult.this, "No Marks to show.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            int count = 0;
//            while(cursor.moveToNext())
//            {
//                MarksModel obj = new MarksModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
//                data.add(obj);
//            }
//        }
//
//        MarksAdapter adapter = new MarksAdapter(data, new MarksAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(MarksModel marksModel) {
//                Intent intent;
//                intent = new Intent(MarksResult.this, MarksUpdate.class);
//                String a = String.valueOf(marksModel.getMarksID());
//                intent.putExtra("marksID", a);
//                intent.putExtra("faculty", marksModel.getFaculty());
//                intent.putExtra("email", marksModel.getEmail());
//                intent.putExtra("marks", marksModel.getMarks());
//                startActivity(intent);
//            }
//        });
//
//        MarksList.setAdapter(adapter);
//    }
    }

    private void getStudentMarksList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("StudentMarks");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.whereContains("faculty", faculty);
        query.whereContains("sem", sem);
        query.whereContains("batch", batch);
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            if (e == null) {
                //We are initializing
                initStudentMarksList(objects);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initStudentMarksList(List<ParseObject> list) {

        OnlineMarksAdapter onlineMarksAdapter = new OnlineMarksAdapter(list, this);

        onlineMarksAdapter.onEditListener.observe(this, parseObject -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MarksResult.this);
            alertDialogBuilder.setTitle("Edit marks");
            alertDialogBuilder.setCancelable(true);
            //We are initializing PopUp Views with title and description parameters of Parse Object
            initPopupViewControls(parseObject.getString("sem"), parseObject.getString("email"), parseObject.getString("faculty"), parseObject.getString("marks"));
            alertDialogBuilder.setView(popupInputDialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            btnPopUpSaveMarks.setOnClickListener(saveTodoButtonView -> {
                alertDialog.cancel();
                progressDialog.show();
                parseObject.put("marks", etPopUpStudentMarksmark.getText().toString());
                parseObject.saveInBackground(e1 -> {
                    progressDialog.dismiss();
                    if (e1 == null) {
                        Toast.makeText(MarksResult.this, "Marks Updated", Toast.LENGTH_SHORT).show();
                        getStudentMarksList();
                    } else {
                        showAlert("Error", e1.getMessage());
                    }
                });
            });
            btnPopUpCancel.setOnClickListener(cancelButtonView -> alertDialog.cancel());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(onlineMarksAdapter);
    }

    private void initPopupViewControls(String sem, String email, String faculty, String marks) {
        LayoutInflater layoutInflater = LayoutInflater.from(MarksResult.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.custom_edit_marks_dialog, null);
        tvPopUpSem = popupInputDialogView.findViewById(R.id.tvPopUpSem);
        tvPopUpMarksEmail = popupInputDialogView.findViewById(R.id.tvPopUpMarksEmail);
        tvPopUpMarksFaculty = popupInputDialogView.findViewById(R.id.tvPopUpMarksFaculty);
        etPopUpStudentMarksmark = popupInputDialogView.findViewById(R.id.etPopUpStudentMarksmark);
        btnPopUpSaveMarks = popupInputDialogView.findViewById(R.id.btnPopUpSaveMarks);
        btnPopUpCancel = popupInputDialogView.findViewById(R.id.btnPopUpCancel);
        tvPopUpSem.setText(sem);
        tvPopUpMarksEmail.setText(email);
        tvPopUpMarksFaculty.setText(faculty);
        etPopUpStudentMarksmark.setText(marks);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MarksResult.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(MarksResult.this, MarksResult.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}