package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class FirebaseTokenDTO {
    @SerializedName("firebaseToken")
    String firebaseToken;

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    @Override
    public String toString() {
        return "FirebaseTokenDTO{" +
                "firebaseToken='" + firebaseToken + '\'' +
                '}';
    }
}
