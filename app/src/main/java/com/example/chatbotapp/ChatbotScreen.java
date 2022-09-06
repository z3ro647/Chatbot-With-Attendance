package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.AlanConfig;
import com.alan.alansdk.button.AlanButton;
import com.alan.alansdk.events.EventCommand;
import com.example.chatbotapp.adapters.ChatAdapter;
import com.example.chatbotapp.helpers.SendMessageInBg;
import com.example.chatbotapp.interfaces.BotReply;
import com.example.chatbotapp.models.Message;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatbotScreen extends AppCompatActivity implements BotReply {

    RecyclerView chatView;
    ChatAdapter chatAdapter;
    List<Message> messageList = new ArrayList<>();
    EditText editMessage;
    ImageButton btnSend;

    /// Add AlanButton variable
    private AlanButton alanButton;

    //dialogFlow
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "mainactivity";

    private String customID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_screen);

        customID = getIntent().getStringExtra("customID");

        chatView = findViewById(R.id.chatView);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);

        chatAdapter = new ChatAdapter(messageList, ChatbotScreen.this, customID);
        chatView.setAdapter(chatAdapter);

        /// Define the project key
        AlanConfig config = AlanConfig.builder().setProjectId("c93de0d311280b0dd2e916242791d8a02e956eca572e1d8b807a3e2338fdd0dc/stage").build();
        alanButton = findViewById(R.id.alan_button);
        alanButton.initWithConfig(config);

        AlanCallback alanCallback = new AlanCallback() {
            /// Handle commands from Alan Studio
            @Override
            public void onCommand(final EventCommand eventCommand) {
                String commandName = "";
                try {
                    JSONObject command = eventCommand.getData();
                    commandName = command.getJSONObject("data").getString("command");
                    Log.d("AlanButton", "onCommand: commandName: " + commandName);
//                    if(commandName == "attendance") {
//                        //Toast.makeText(ChatbotScreen.this, "Attendance", Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(ChatbotScreen.this, StudentDashboardScreen.class);
////                        startActivity(i);
//                        openScreen();
//                    }
                } catch (JSONException e) {
                    Log.e("AlanButton", e.getMessage());
                }
                if(commandName.equals("attendance")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(ChatbotScreen.this, AttendanceStudent.class);
                            i.putExtra("customID", customID);
                            startActivity(i);
                        }
                    }, 3000);
                } else if (commandName.equals("marks")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(ChatbotScreen.this, MarksStudent.class);
                            i.putExtra("customID", customID);
                            startActivity(i);
                        }
                    }, 3000);
                } else if (commandName.equals("android")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Uri uri = Uri.parse("https://www.tutorialspoint.com/android/android_tutorial.pdf"); // missing 'http://' will cause crashed
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i);
                        }
                    }, 3000);
                } else if (commandName.equals("flutter")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Uri uri = Uri.parse("https://www.tutorialspoint.com/flutter/flutter_tutorial.pdf"); // missing 'http://' will cause crashed
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i);
                        }
                    }, 3000);
                }
            }
        };

        /// Register callbacks
        alanButton.registerCallback(alanCallback);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String message = editMessage.getText().toString();
                if (!message.isEmpty()) {
                    messageList.add(new Message(message, false));
                    editMessage.setText("");
                    sendMessageToBot(message);
                    Objects.requireNonNull(chatView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(chatView.getLayoutManager())
                            .scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(ChatbotScreen.this, "Please enter text!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setUpBot();
    }


    private void setUpBot() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }

    private void sendMessageToBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
        new SendMessageInBg(this, sessionName, sessionsClient, input).execute();
    }

    @Override
    public void callback(DetectIntentResponse returnResponse) {
        if(returnResponse!=null) {
            String botReply = returnResponse.getQueryResult().getFulfillmentText();
            if(!botReply.isEmpty()){
                messageList.add(new Message(botReply, true));
                chatAdapter.notifyDataSetChanged();
                Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
            }else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show();
        }
    }
}