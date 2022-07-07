package com.example.chatbotapp.models;

public class AttendanceModel {

    int attendanceID;
    long phone;
    String sessionID, customDate, faculty, sem, email, stuName, stuID, remark, batch;

    public AttendanceModel(int attendanceID, long phone, String sessionID, String customDate, String faculty, String sem, String email, String stuName, String stuID, String remark, String batch) {
        this.attendanceID = attendanceID;
        this.phone = phone;
        this.sessionID = sessionID;
        this.customDate = customDate;
        this.faculty = faculty;
        this.sem = sem;
        this.email = email;
        this.stuName = stuName;
        this.stuID = stuID;
        this.remark = remark;
        this.batch = batch;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getCustomDate() {
        return customDate;
    }

    public void setCustomDate(String customDate) {
        this.customDate = customDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
