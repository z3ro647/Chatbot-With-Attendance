package com.example.chatbotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbotapp.adapters.AllUsersAdapter;
import com.example.chatbotapp.adapters.UsersAdapter;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.example.chatbotapp.models.UsersModel;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class UsersScreen extends AppCompatActivity {

    ArrayList<UsersModel> data;

    private ProgressDialog progressDialog;
    private View popupInputDialogView;
    private Button btnAddUser, saveUserButton, cancelUserButton;
    private RecyclerView recyclerView;
    private EditText etPopUpPhone, etPopUpEmail, etPopUpName,  etPopUpPassword, etPopUpRole, etPopUpFaculty, etPopUpSem, etPopUpCustomID, etPopUpBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_screen);
        progressDialog = new ProgressDialog(UsersScreen.this);
        recyclerView = findViewById(R.id.users);

        getAppUsersList();

        btnAddUser = findViewById(R.id.btnAddUser);

//        RecyclerView UsersList = (RecyclerView) findViewById(R.id.users);
//        UsersList.setLayoutManager(new LinearLayoutManager(UsersScreen.this));
//
//        Cursor cursor = new ChatAppDatabaseHelper(UsersScreen.this).viewAllUsers();
//        data = new ArrayList<>();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(UsersScreen.this, "No Users to show.", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            int count = 0;
//            while(cursor.moveToNext())
//            {
//                UsersModel obj = new UsersModel(cursor.getInt(0),cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
//                data.add(obj);
//            }
//        }
//
//
//        UsersAdapter adapter = new UsersAdapter(data, new UsersAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(UsersModel j) {
//                int a = j.getUserID();
//                Intent intent;
//                intent = new Intent(UsersScreen.this, UpdateUser.class);
//                //Toast.makeText(UsersScreen.this, ""+a, Toast.LENGTH_SHORT).show();
//                intent.putExtra("userID", a);
//                //intent.putExtra("userIDKey", userID);
//                startActivity(intent);
//            }
//        });
//
//        UsersList.setAdapter(adapter);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(UsersScreen.this, AddUser.class));
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UsersScreen.this);
                alertDialogBuilder.setTitle("Create a User");
                alertDialogBuilder.setCancelable(true);
                initPopupViewControls();
                //We are setting our custom popup view by AlertDialog.Builder
                alertDialogBuilder.setView(popupInputDialogView);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                saveUserButton.setOnClickListener(saveButtonView -> saveUserDetail(alertDialog));
                cancelUserButton.setOnClickListener(cancelButtonView -> alertDialog.cancel());
            }
        });
    }

    private void saveUserDetail(AlertDialog alertDialog) {
        ParseObject newUser = new ParseObject("AppUser");

        if(!etPopUpPhone.getText().toString().isEmpty() || !etPopUpEmail.getText().toString().isEmpty() || !etPopUpName.getText().toString().isEmpty() ||  !etPopUpPassword.getText().toString().isEmpty() || !etPopUpRole.getText().toString().isEmpty() || !etPopUpFaculty.getText().toString().isEmpty() || !etPopUpSem.getText().toString().isEmpty() || !etPopUpCustomID.getText().toString().isEmpty() || !etPopUpBatch.getText().toString().isEmpty()){
            alertDialog.cancel();
            progressDialog.show();
            newUser.put("phone", etPopUpPhone.getText().toString());
            newUser.put("email", etPopUpEmail.getText().toString());
            newUser.put("name", etPopUpName.getText().toString());
            newUser.put("password", etPopUpPassword.getText().toString());
            newUser.put("role", etPopUpRole.getText().toString());
            newUser.put("faculty", etPopUpFaculty.getText().toString());
            newUser.put("sem", etPopUpSem.getText().toString());
            newUser.put("customID", etPopUpCustomID.getText().toString());
            newUser.put("batch", etPopUpBatch.getText().toString());
            newUser.saveInBackground(e -> {
                progressDialog.dismiss();
                if (e == null) {
                    //We saved the object and fetching data again
                    getAppUsersList();
                } else {
                    //We have an error.We are showing error message here.
                    showAlert("Error", e.getMessage());
                }
            });
        } else {
            showAlert("Error", "Please enter all detail");
        }
    }

    private void getAppUsersList() {
        progressDialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AppUser");
        //We use this code to fetch data from newest to oldest.
        query.orderByDescending("createdAt");
        query.findInBackground((objects, e) -> {
            progressDialog.dismiss();
            if (e == null) {
                //We are initializing
                initAppUsersList(objects);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAppUsersList(List<ParseObject> list) {

        AllUsersAdapter allUsersAdapter = new AllUsersAdapter(list, this);

        allUsersAdapter.onDeleteListener.observe(this, parseObject -> {
            progressDialog.show();
            parseObject.deleteInBackground(e -> {
                progressDialog.dismiss();
                if (e == null) {
                    //We deleted the object and fetching data again.
                    getAppUsersList();
                } else {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        allUsersAdapter.onEditListener.observe(this, parseObject -> {

            //Toast.makeText(this, "Email: " + parseObject.getString("email"), Toast.LENGTH_SHORT).show();

//            Intent intent;
//            intent = new Intent(UsersScreen.this, UpdateUser.class);
//            intent.putExtra("etUpdatePhone", parseObject.getString("phone"));
//            intent.putExtra("etUpdateEmail", parseObject.getString("email"));
//            intent.putExtra("etUpdateName", parseObject.getString("name"));
//            intent.putExtra("etUpdatePassword", parseObject.getString("password"));
//            intent.putExtra("etUpdateRole", parseObject.getString("role"));
//            intent.putExtra("etUpdateFaculty", parseObject.getString("faculty"));
//            intent.putExtra("etUpdateSem", parseObject.getString("sem"));
//            intent.putExtra("etUpdateCustomID", parseObject.getString("customID"));
//            intent.putExtra("etUpdateBatch", parseObject.getString("batch"));
//            startActivity(intent);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UsersScreen.this);
            alertDialogBuilder.setTitle("Update a Student");
            alertDialogBuilder.setCancelable(true);
            //We are initializing PopUp Views with title and description parameters of Parse Object
            initPopupViewControls(parseObject.getString("phone"), parseObject.getString("email"), parseObject.getString("name"), parseObject.getString("password"), parseObject.getString("role"), parseObject.getString("faculty"), parseObject.getString("sem"), parseObject.getString("customID"), parseObject.getString("batch"));
            alertDialogBuilder.setView(popupInputDialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            saveUserButton.setOnClickListener(saveTodoButtonView -> {
//                if (titleInput.getText().toString().length() != 0 && descriptionInput.getText().toString().length() != 0) {
//                    alertDialog.cancel();
//                    progressDialog.show();
//                    parseObject.put("title", titleInput.getText().toString());
//                    parseObject.put("description", descriptionInput.getText().toString());
//                    parseObject.saveInBackground(e1 -> {
//                        progressDialog.dismiss();
//                        if (e1 == null) {
//                            getAppUsersList();
//                        } else {
//                            showAlert("Error", e1.getMessage());
//                        }
//                    });
//                } else {
//                    showAlert("Error", "Please enter a title and description");
//                }

                alertDialog.cancel();
                progressDialog.show();
                parseObject.put("phone", etPopUpPhone.getText().toString());
                parseObject.put("email", etPopUpEmail.getText().toString());
                parseObject.put("name", etPopUpName.getText().toString());
                parseObject.put("password", etPopUpPassword.getText().toString());
                parseObject.put("role", etPopUpRole.getText().toString());
                parseObject.put("faculty", etPopUpFaculty.getText().toString());
                parseObject.put("sem", etPopUpSem.getText().toString());
                parseObject.put("customID", etPopUpCustomID.getText().toString());
                parseObject.put("batch", etPopUpBatch.getText().toString());
                parseObject.saveInBackground(e1 -> {
                    progressDialog.dismiss();
                    if (e1 == null) {
                        getAppUsersList();
                    } else {
                        showAlert("Error", e1.getMessage());
                    }
                });
            });
            cancelUserButton.setOnClickListener(cancelButtonView -> alertDialog.cancel());
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(allUsersAdapter);
    }

    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(UsersScreen.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.custom_add_user_dialog, null);
        etPopUpPhone = popupInputDialogView.findViewById(R.id.etPopUpNewPhone);
        etPopUpEmail = popupInputDialogView.findViewById(R.id.etPopUpNewEmail);
        etPopUpName = popupInputDialogView.findViewById(R.id.etPopUpNewName);
        etPopUpPassword = popupInputDialogView.findViewById(R.id.etPopUpNewPassword);
        etPopUpRole = popupInputDialogView.findViewById(R.id.etPopUpNewRole);
        etPopUpFaculty = popupInputDialogView.findViewById(R.id.etPopUpNewFaculty);
        etPopUpSem = popupInputDialogView.findViewById(R.id.etPopUpNewSem);
        etPopUpCustomID = popupInputDialogView.findViewById(R.id.etPopUpNewCustomID);
        etPopUpBatch = popupInputDialogView.findViewById(R.id.etPopUpNewBatch);
        saveUserButton = popupInputDialogView.findViewById(R.id.btnPopUpNewSubmit);
        cancelUserButton = popupInputDialogView.findViewById(R.id.btnPopUpNewCancel);
    }

    private void initPopupViewControls(String phone, String email, String name, String password, String role, String faculty, String sem, String customID, String batch) {
        LayoutInflater layoutInflater = LayoutInflater.from(UsersScreen.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.custom_add_user_dialog, null);
        etPopUpPhone = popupInputDialogView.findViewById(R.id.etPopUpNewPhone);
        etPopUpEmail = popupInputDialogView.findViewById(R.id.etPopUpNewEmail);
        etPopUpName = popupInputDialogView.findViewById(R.id.etPopUpNewName);
        etPopUpPassword = popupInputDialogView.findViewById(R.id.etPopUpNewPassword);
        etPopUpRole = popupInputDialogView.findViewById(R.id.etPopUpNewRole);
        etPopUpFaculty = popupInputDialogView.findViewById(R.id.etPopUpNewFaculty);
        etPopUpSem = popupInputDialogView.findViewById(R.id.etPopUpNewSem);
        etPopUpCustomID = popupInputDialogView.findViewById(R.id.etPopUpNewCustomID);
        etPopUpBatch = popupInputDialogView.findViewById(R.id.etPopUpNewBatch);
        saveUserButton = popupInputDialogView.findViewById(R.id.btnPopUpNewSubmit);
        cancelUserButton = popupInputDialogView.findViewById(R.id.btnPopUpNewCancel);

        etPopUpPhone.setText(phone);
        etPopUpEmail.setText(email);
        etPopUpName.setText(name);
        etPopUpPassword.setText(password);
        etPopUpRole.setText(role);
        etPopUpFaculty.setText(faculty);
        etPopUpSem.setText(sem);
        etPopUpCustomID.setText(customID);
        etPopUpBatch.setText(batch);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UsersScreen.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(UsersScreen.this, UsersScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}