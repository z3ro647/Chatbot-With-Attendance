package com.example.chatbotapp.models;

public class MarksModel {
    int marksID;
    long phone;
    String faculty, sem, email, stuName, stuID, marks, batch;

    public MarksModel(int marksID, long phone, String faculty, String sem, String email, String stuName, String stuID, String marks, String batch) {
        this.marksID = marksID;
        this.phone = phone;
        this.faculty = faculty;
        this.sem = sem;
        this.email = email;
        this.stuName = stuName;
        this.stuID = stuID;
        this.marks = marks;
        this.batch = batch;
    }

    public int getMarksID() {
        return marksID;
    }

    public void setMarksID(int marksID) {
        this.marksID = marksID;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
