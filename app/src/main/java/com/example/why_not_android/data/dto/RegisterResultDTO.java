package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterResultDTO {
    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
