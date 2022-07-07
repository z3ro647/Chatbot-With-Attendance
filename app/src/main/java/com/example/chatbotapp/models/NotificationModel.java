package com.example.chatbotapp.models;

public class NotificationModel {
    int notificationID;
    String customDate;
    String title;
    String description;

    public NotificationModel(int notificationID, String customDate, String title, String description) {
        this.notificationID = notificationID;
        this.customDate = customDate;
        this.title = title;
        this.description = description;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getCustomDate() {
        return customDate;
    }

    public void setCustomDate(String customDate) {
        this.customDate = customDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
