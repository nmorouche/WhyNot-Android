package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class SessionDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("photo")
    private String photo;
    @SerializedName("hobbies")
    private String[] hobbies;
    @SerializedName("token")
    private String token;
    @SerializedName("error")
    private String error;

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String image) {
        this.photo = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                ", hobbies=" + Arrays.toString(hobbies) +
                ", token='" + token + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
