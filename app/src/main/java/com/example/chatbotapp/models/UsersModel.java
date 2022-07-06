package com.example.chatbotapp.models;

public class UsersModel {

    int userID;
    long phone;
    String email, password, name, role, faculty, sem, customID, batch;

    public UsersModel(int userID, long phone, String email, String password, String name, String role, String faculty, String sem, String customID, String batch) {
        this.userID = userID;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.faculty = faculty;
        this.sem = sem;
        this.customID = customID;
        this.batch = batch;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getCustomID() {
        return customID;
    }

    public void setCustomID(String customID) {
        this.customID = customID;
    }


    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

}
