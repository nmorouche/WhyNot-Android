package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class LoginDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}