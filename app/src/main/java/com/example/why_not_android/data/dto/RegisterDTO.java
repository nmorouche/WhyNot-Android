package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterDTO {
    @SerializedName("userId")
    private String email;
    @SerializedName("eventId")
    private String password;

    public RegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
}
