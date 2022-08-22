package com.example.chatbotapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Objects;

public class ForgotActivity extends AppCompatActivity {

    private EditText etForgotEmail, etNewPassword;
    private Button btnConfirmEmail, btnUpdatePassword, btnCanclePassword;
    private View popupInputDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        etForgotEmail = findViewById(R.id.etForgotEmail);
        btnConfirmEmail = findViewById(R.id.btnConfirmEmail);

        btnConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etForgotEmail.getText().toString().isEmpty()) {
                    Toast.makeText(ForgotActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("AppUser");
                    query.whereContains("email", etForgotEmail.getText().toString());
                    query.findInBackground((objects, e) -> {
                        Log.d("Length","Length is: "+objects.size());
                        if (objects.size() == 1) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ForgotActivity.this);
                            alertDialogBuilder.setTitle("Update password");
                            alertDialogBuilder.setCancelable(true);
                            LayoutInflater layoutInflater = LayoutInflater.from(ForgotActivity.this);
                            popupInputDialogView = layoutInflater.inflate(R.layout.custom_forgot_password_dialog, null);
                            etNewPassword = popupInputDialogView.findViewById(R.id.etPopUpForgotPassword);
                            btnUpdatePassword= popupInputDialogView.findViewById(R.id.btnUpdatePassword);
                            btnCanclePassword= popupInputDialogView.findViewById(R.id.btnCancelPassword);
                            alertDialogBuilder.setView(popupInputDialogView);
                            final AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            btnUpdatePassword.setOnClickListener(saveTodoButtonView -> {
                                alertDialog.cancel();
                                objects.get(0).put("password", etNewPassword.getText().toString());
                                objects.get(0).saveInBackground(e1 -> {
                                    if (e1 == null) {
                                        Toast.makeText(ForgotActivity
                                                .this, "Password Updated", Toast.LENGTH_SHORT).show();
                                    } else {
                                        showAlert("Error", e1.getMessage());
                                    }
                                });
                        });
                        btnCanclePassword.setOnClickListener(cancelButtonView -> alertDialog.cancel());
                        } else {
                            Toast.makeText(ForgotActivity.this, "Email not Found", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Intent intent = new Intent(ForgotActivity.this, MainActivity.class);
//                    finishAffinity();
//                    startActivity(intent);
                }
            }
        });
    }
    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(ForgotActivity.this, ForgotActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}