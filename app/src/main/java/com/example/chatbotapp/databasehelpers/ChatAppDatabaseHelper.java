package com.example.chatbotapp.databasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatAppDatabaseHelper extends SQLiteOpenHelper {

    public ChatAppDatabaseHelper(Context context) {
        super(context, "ChatbotApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user_table (userID INTEGER PRIMARY KEY AUTOINCREMENT, phone LONG, email TEXT, name TEXT, password TEXT, role TEXT, faculty TEXT, sem TEXT, customID TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE attendance_table (attendanceID INTEGER PRIMARY KEY AUTOINCREMENT, sessionID TEXT, customDate DEFAULT (datetime('now', 'localtime')), faculty TEXT, sem TEXT, phone LONG, email TEXT, stuName TEXT, stuID TEXT, remark TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE attendance_session_table (attendanceID INTEGER PRIMARY KEY AUTOINCREMENT, customDate DEFAULT (datetime('now', 'localtime')), faculty TEXT, sem TEXT, phone LONG, email TEXT, stuName TEXT, stuID TEXT, remark TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists question_table");
    }

    public void closeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }

    public Boolean insertUser(long phone, String email, String name, String password, String role, String faculty, String sem, String customID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("role", role);
        contentValues.put("faculty", faculty);
        contentValues.put("sem", sem);
        contentValues.put("customID", customID);
        long result = db.insert("user_table", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean createAttendance(String customDate,String faculty, String sem,long phone,String email, String stuName, String stuID, String remark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("customDate", customDate);
        contentValues.put("faculty", faculty);
        contentValues.put("sem", sem);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("stuName", stuName);
        contentValues.put("stuID", stuID);
        contentValues.put("remark", remark);
        long result = db.insert("attendance_table", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor viewAllStudents(String faculty, String sem) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from user_table where faculty = ? AND sem = ?";
        Cursor cursor = db.rawQuery(query, new String[] {faculty, sem});
        return cursor;
    }

    public Cursor viewAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from user_table";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewOneUsers(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from user_table where userID = ?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(userID)});
        return cursor;
    }

    public Cursor searchOneUserPhoneAndPassword(String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from user_table where phone = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] {phone, password});
        return cursor;
    }

    public Boolean updateUser(int id, long phone, String email, String name, String password, String role, String faculty, String sem, String customID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("role", role);
        contentValues.put("faculty", faculty);
        contentValues.put("sem", sem);
        contentValues.put("customID", customID);
        Cursor cursor = db.rawQuery("Select * from user_table where userID = ?", new String[] {String.valueOf(id)});
        if(cursor.getCount()>0){
            long result = db.update("user_table", contentValues, "userID=?", new String[] {String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor viewAllAttendanceBy(String customDate, String faculty, String sem) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from attendance_table where customDate = ? AND faculty = ? AND sem = ?";
        Cursor cursor = db.rawQuery(query, new String[] {customDate, faculty, sem});
        return cursor;
    }

    public Cursor viewAllAttendanceOfStudentBy(String customID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from attendance_table where stuID = ? ";
        Cursor cursor = db.rawQuery(query, new String[] {customID});
        return cursor;
    }

    public Boolean updateAttendance(int id, String remarks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", id);
        contentValues.put("remark", remarks);
        Cursor cursor = db.rawQuery("Select * from attendance_table where attendanceID = ?", new String[] {String.valueOf(id)});
        if(cursor.getCount()>0){
            long result = db.update("attendance_table", contentValues, "attendanceID=?", new String[] {String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

































    public Boolean insertPhone(String customDate) {
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
