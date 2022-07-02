package com.example.chatbotapp.databasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Timestamp;

public class DemoDatabaseHelper extends SQLiteOpenHelper {
    public DemoDatabaseHelper(Context context) {
        super(context, "ChatbotApp.db", null, 1);
    }

//1. Users
//    id primary key int
//    userID role id (student id for student, admin id for admin, parent id for parent, teacher id for teacher) int
//    role string
//    email string
//    phone long
//    password string
//    name String
//2. Attendance
//    attendance id primary key int
//    dateTime
//    teacher id
//    course id
//    student id
//3. Notice
//    notice id primary key int
//    dateTime
//    notice string
//4. Marks
//    marks id primary key int
//    course id int
//    student id int
//    marks int
//5. Course
//    course id primary key int
//    course string

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user_table (id INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, role TEXT, email TEXT, phone LONG, password TEXT, name TEXT)");
        //db.execSQL("CREATE TABLE attendace_table (attendanceID PRIMARY KEY AUTOINCREMENT, dateTime DEFAULT (datetime('now', 'localtime')))");
        //db.execSQL("CREATE TABLE demo_table (id INTEGER PRIMARY KEY AUTOINCREMENT, customDate DATE)");
        db.execSQL("CREATE TABLE demo_table (id INTEGER PRIMARY KEY AUTOINCREMENT, customDate DEFAULT (datetime('now', 'localtime')))");
//        db.execSQL("CREATE TABLE demo_table (id INTEGER PRIMARY KEY AUTOINCREMENT, customDate DEFAULT (datetime('now', 'localtime')))");
//        db.execSQL("CREATE TABLE user_table (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
//        db.execSQL("CREATE TABLE user_journal (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "userID LONG," +
//                "title TEXT," +
//                "description LONGTEXT," +
//                "latitude DOUBLE," +
//                "longitude DOUBLE" +
//                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists question_table");
    }

    public Boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("user_table", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertDate(String customDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("customDate", customDate);
        long result = db.insert("demo_table", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertJournal(long userID, String title, String description, Double latitude, Double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        long result = db.insert("user_journal", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void closeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }

    public Cursor searchUserName(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from user_table where username = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username});
        return cursor;
    }

    public Cursor searchUserNameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from user_table where username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username, password});
        return cursor;
    }

    public Cursor viewAllJournal(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from user_journal where userID = ?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(userID)});
        return cursor;
    }

    public Cursor searchJournal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_journal where id = ?", new String[] {String.valueOf(id)});
        return cursor;
    }

    public Boolean updateJournal(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("description", description);
        Cursor cursor = db.rawQuery("Select * from user_journal where id = ?", new String[] {String.valueOf(id)});
        if(cursor.getCount()>0){
            long result = db.update("user_journal", contentValues, "id=?", new String[] {String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor searchUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_table where username = ? AND password = ?", new String[] {username, password});
        return cursor;
    }

    public Boolean deleteJournal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_journal where id = ?", new String[] {String.valueOf(id)});
        int a = cursor.getCount();
        if(cursor.getCount()>0) {
            long result = db.delete("user_journal", "id=?", new String[] {String.valueOf(id)});
            if(result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
