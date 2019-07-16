package com.example.why_not_android.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionDTO {
    @SerializedName("token")
    private String token;
    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                ", token='" + token + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
