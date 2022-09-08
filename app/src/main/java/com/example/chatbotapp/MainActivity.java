package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatbotapp.databasehelpers.ChatAppDatabaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    private Button btnFirebase;
    private TextView tvForgotPassword;

    private TextView mTimeText;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btnChatBot = findViewById(R.id.btnChatBot);
        //btnDemoInsert = findViewById(R.id.btnDemoInsert);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
//        String dateTime = simpleDateFormat.format(calendar.getTime());
//        mTimeText = findViewById(R.id.mTimeText);
//        mTimeText.setText(dateTime);

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("FirstClass");
//        query.orderByDescending("createdAt");
//        query.findInBackground((objects, e) -> {
//            if (e == null) {
//                Log.d("Length of objects: ","Length is "+objects.size());
//                //We are initializing Todo object list to our adapter
//                initTodoList(objects);
//                ParseObject object = objects.get(0);
//                Log.d("This is msg: ", ""+object.getString("message"));
//            } else {
//                Log.d("Error value of E","This is the error: "+e.getMessage().toString());
//            }
//        });

//        ParseObject firstObject = new  ParseObject("FirstClass");
//        firstObject.put("message","Hey ! First message from android. Parse is now connected");
//        firstObject.saveInBackground(e -> {
//            if (e != null){
//                Log.e("MainActivity", e.getLocalizedMessage());
//            }else{
//                Log.d("MainActivity","Object saved.");
//            }
//            });


//        FirebaseApp.initializeApp(MainActivity.this);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        data.put("Name", "Tokyo");
//        data.put("Capital", "Japan");
//
//        db.collection("ThisCities").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Values added Successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        db.collection("This").document("LA")
//                .set(data)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("data", "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("data", "Error writing document", e);
//                    }
//                });

//        Map<String, Object> docData = new HashMap<>();
//        docData.put("stringExample", "Hello world!");
//        docData.put("booleanExample", true);
//        docData.put("numberExample", 3.14159265);
//        docData.put("dateExample", new Timestamp(new Date()));
//        docData.put("listExample", Arrays.asList(1, 2, 3));
//        docData.put("nullExample", null);
//
//        Map<String, Object> nestedData = new HashMap<>();
//        nestedData.put("a", 5);
//        nestedData.put("b", true);
//
//        docData.put("objectExample", nestedData);

//        db.collection("data").document("one")
//                .set(docData)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("TAG", "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error writing document", e);
//                    }
//                });


//        // Add a new document with a generated id.
//        Map<String, Object> data1 = new HashMap<>();
//        data.put("name", "Tokyo");
//        data.put("country", "Japan");
//
//        db.collection("okeys")
//                .add(data1)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });

//        DocumentReference docRef = db.collection("cities").document("SF");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d("TAG", "No such document");
//                    }
//                } else {
//                    Log.d("TAG", "get failed with ", task.getException());
//                }
//            }
//        });

        //btnFirebase = findViewById(R.id.btnFirebase);
//        btnFirebase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Map<String, Object> city = new HashMap<>();
//                city.put("name", "Los Angeles");
//                city.put("state", "CA");
//                city.put("country", "USA");
//
//                db.collection("cities").document("LA")
//                        .set(city)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("TAG", "DocumentSnapshot successfully written!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("TAG", "Error writing document", e);
//                            }
//                        });
//            }
//        });

//        btnFirebase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AtomicReference<Boolean> userFound = new AtomicReference<>(false);
//                AtomicInteger position = new AtomicInteger();
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("AppUser");
//                query.findInBackground((objects, e) -> {
//                    Log.d("Length","Length is: "+objects.size());
//                    if (e == null) {
//                        for (int i = 0; i < objects.size(); i++) {
//                            ParseObject object = objects.get(i);
//                            Log.d("Email", "Email at " + i + " is: " + object.getString("email"));
//                            if(Objects.equals(object.getString("email"), "z3ro647@gmail.com")) {
//                                Toast.makeText(MainActivity.this, "Email Found", Toast.LENGTH_SHORT).show();
//                                userFound.set(true);
//                                break;
//                            }
//                        }
//                        if(!userFound.get()) {
//                            Toast.makeText(MainActivity.this, "Email not Found", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Log.d("Error value of E","This is the error: "+e.getMessage().toString());
//                    }
//                });
//            }
//        });

        btnSignIn = findViewById(R.id.btnSignIn);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        //signUp = findViewById(R.id.signUp);

        //textView38 = findViewById(R.id.textView38);

        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        chatAppDatabaseHelper = new ChatAppDatabaseHelper(this);

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });

        displayWeather();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtomicBoolean userFound = new AtomicBoolean(false);
                AtomicInteger position = new AtomicInteger();
                String name = "";
                if (etPhone.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Phone or Password can not be empty.", Toast.LENGTH_SHORT).show();
                } else if (etPhone.getText().toString().equals("123") && etPassword.getText().toString().equals("admin")) {
                    //startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
                    Intent i = new Intent(MainActivity.this, AdminDashboardScreen.class);
                    finishAffinity();
                    startActivity(i);
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("AppUser");
                    query.findInBackground((objects, e) -> {
                        Log.d("Length", "Length is: " + objects.size());
                        if (e == null) {
                            for (int i = 0; i < objects.size(); i++) {
                                ParseObject object = objects.get(i);
                                Log.d("Phone and Password", "Phone: " + object.getString("phone") + ", Password: " + object.getString("password") + ", at: " + i);
                                if (Objects.equals(object.getString("phone"), etPhone.getText().toString()) && Objects.equals(object.getString("password"), etPassword.getText().toString())) {
                                    //Toast.makeText(MainActivity.this, "User Phone Found", Toast.LENGTH_SHORT).show();
                                    userFound.set(true);
                                    if (Objects.equals(object.getString("role"), "Student")) {
                                        Intent intent = new Intent(MainActivity.this, StudentDashboardScreen.class);
                                        intent.putExtra("customID", object.getString("customID"));
                                        intent.putExtra("batch", object.getString("batch"));
                                        startActivity(intent);
                                    } else if (Objects.equals(object.getString("role"), "Parent")) {
                                        Intent intent = new Intent(MainActivity.this, ParentDashboardScreen.class);
                                        intent.putExtra("customID", object.getString("customID"));
                                        intent.putExtra("batch", object.getString("batch"));
                                        startActivity(intent);
                                    } else if (Objects.equals(object.getString("role"), "Teacher")) {
                                        startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
                                    } else {
                                        startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
                                    }
                                    break;
                                }
                            }
                            if (!userFound.get()) {
                                Toast.makeText(MainActivity.this, "User Phone not Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Error value of E", "This is the error: " + e.getMessage().toString());
                        }
                    });
//                    Cursor cursor = chatAppDatabaseHelper.searchOneUserPhoneAndPassword(etPhone.getText().toString(), etPassword.getText().toString());
//                    if (cursor.getCount() == 0) {
//                        Toast.makeText(MainActivity.this, "Phone and Password does not match.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        cursor.moveToFirst();
//                        if(cursor.getString(5).equals("Student")) {
//                            //Toast.makeText(MainActivity.this, "Student", Toast.LENGTH_SHORT).show();
//                            //startActivity(new Intent(MainActivity.this, StudentDashboardScreen.class));
//                            Intent intent = new Intent(MainActivity.this, StudentDashboardScreen.class);
//                            intent.putExtra("customID", cursor.getString(8));
//                            intent.putExtra("batch", cursor.getString(9));
//                            startActivity(intent);
//                        } else if (cursor.getString(5).equals("Parent")) {
//                            //Toast.makeText(MainActivity.this, "Parent", Toast.LENGTH_SHORT).show();
//                            //startActivity(new Intent(MainActivity.this, ParentDashboardScreen.class));
//                            Intent intent = new Intent(MainActivity.this, ParentDashboardScreen.class);
//                            intent.putExtra("customID", cursor.getString(8));
//                            intent.putExtra("batch", cursor.getString(9));
//                            startActivity(intent);
//                        } else if (cursor.getString(5).equals("Teacher")) {
//                            //Toast.makeText(MainActivity.this, "Teacher", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
//                        } else {
//                            //Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this, AdminDashboardScreen.class));
//                        }
//                    }
                }
            }
        });

//        textView38.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri uri = Uri.parse("https://www.tutorialspoint.com/android/android_tutorial.pdf"); // missing 'http://' will cause crashed
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });

//        btnChatBot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, ChatbotScreen.class);
//                startActivity(i);
//            }
//        });

//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, SignUpScreen.class));
//            }
//        });

//        btnDemoInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnLineDatabaseHelper onlinedatabasehelper = new OnLineDatabaseHelper();
//                onlinedatabasehelper.insertDemoUser("1234567890", "z3ro647@gmail.com", "Vivek", "123", "Student", "IT", "7", "1001", "1");
//                onlinedatabasehelper.insertDemoUser("1234567891", "prijal@gmail.com", "Prabesh", "123", "Teacher", "IT", "7", "2001", "");
//                onlinedatabasehelper.insertDemoUser("1234567892", "admin@gmail.com", "Admin", "123", "Admin", "Admin", "Admin", "3001", "");
//                onlinedatabasehelper.insertDemoUser("1234567893", "suman@gmail.com", "Suman", "123", "Parent", "IT", "7", "1001", "1");
//                onlinedatabasehelper.insertDemoUser("1234567894", "bishnu@gmail.com", "Bishnu", "123", "Student", "Hotel", "7", "1002", "1");
////                chatAppDatabaseHelper.insertUser(1234567890, "z3ro647@gmail.com", "Vivek", "123", "Student", "IT", "7", "1001", "1");
////                chatAppDatabaseHelper.insertUser(1234567891, "prijal@gmail.com", "Prabesh", "123", "Teacher", "IT", "7", "2001", "");
////                chatAppDatabaseHelper.insertUser(1234567892, "admin@gmail.com", "Admin", "123", "Admin", "Admin", "Admin", "3001", "");
////                chatAppDatabaseHelper.insertUser(1234567893, "suman@gmail.com", "Suman", "123", "Parent", "IT", "7", "1001", "1");
////                chatAppDatabaseHelper.insertUser(1234567894, "bishnu@gmail.com", "Bishnu", "123", "Student", "IT", "7", "1002", "1");
//            }
//        });

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void displayWeather() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Weather url to get JSON
        String weather_url1 = "";

        // Api id for url
        String api_id1 = "6c90fc2f0d6746a7819e63a35ada9f8f";
        //https://api.weatherbit.io/v2.0/current?lat=28.156901&lon=84.061078&key=6c90fc2f0d6746a7819e63a35ada9f8f

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> getTemp(location.getLatitude(), location.getLongitude()));
    }

    public void getTemp(double lat, double log) {
        // Api id for url
        String api_id1 = "6c90fc2f0d6746a7819e63a35ada9f8f";
        String weatherUrl1 = "https://api.weatherbit.io/v2.0/current?" + "lat=" + lat + "&lon=" + log + "&key=" + api_id1;
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = weatherUrl1;
        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.d("Response", response.toString());
                        // get the JSON object
                        try {
                            JSONObject object = new JSONObject(String.valueOf(response));
                            // get the Array from obj of name - "data"
                            JSONArray arr = object.getJSONArray("data");
                            Log.d("Data", arr.toString());
                            // get the JSON object from the
                            // array at index position 0
                            JSONObject data = arr.getJSONObject(0);
                            Log.d("City Name", data.getString("city_name"));
                            Log.d("Average Temp", data.getString("app_temp"));
                            JSONObject arr1 = data.getJSONObject("weather");
                            Log.d("Description", arr1.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
        queue.add(jsonObjectRequest);
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