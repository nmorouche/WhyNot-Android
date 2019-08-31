package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class MessageDTO {

    @SerializedName("user1")
    String user1;
    @SerializedName("user2")
    String user2;
    @SerializedName("message")
    String message;

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageDTO(String user2, String message) {
        this.user2 = user2;
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                " user2='" + user2 + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
