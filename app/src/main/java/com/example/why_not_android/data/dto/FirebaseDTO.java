package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class FirebaseDTO {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FirebaseDTO(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "FirebaseDTO{" +
                "token='" + token + '\'' +
                '}';
    }
}