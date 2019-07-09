package com.example.why_not_android.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionDTO {
    @SerializedName("user")
    private UserDTO userDTO;
    @SerializedName("token")
    private String token;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
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
                "userDTO=" + userDTO +
                ", token='" + token + '\'' +
                '}';
    }
}
