package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class UsersListDTO {
    @SerializedName("users")
    private UserDTO userDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "UsersListDTO{" +
                "userDTO=" + userDTO +
                '}';
    }
}
