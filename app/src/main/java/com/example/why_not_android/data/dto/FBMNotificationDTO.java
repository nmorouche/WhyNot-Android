package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class FBMNotificationDTO {
    @SerializedName("title")
    String title;
    @SerializedName("body")
    String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public FBMNotificationDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return "FBMNotificationDTO{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
