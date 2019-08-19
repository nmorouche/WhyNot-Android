package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class FBMPushNotificationDTO {
    @SerializedName("to")
    String to;
    @SerializedName("notification")
    FBMNotificationDTO notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FBMNotificationDTO getNotification() {
        return notification;
    }

    public void setNotification(FBMNotificationDTO notification) {
        this.notification = notification;
    }

    public FBMPushNotificationDTO(String to, FBMNotificationDTO notificationDTO){
        this.to = to;
        this.notification = notificationDTO;
    }

    @Override
    public String toString() {
        return "FBMPushNotificationDTO{" +
                "to='" + to + '\'' +
                ", notification=" + notification +
                '}';
    }
}
