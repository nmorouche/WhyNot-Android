package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("_id")
    private String _id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("photo")
    private String photo;
    @SerializedName("birthdate")
    private String birthdate;
    @SerializedName("gender")
    private int gender;
    @SerializedName("preference")
    private int preference;
    @SerializedName("bio")
    private String bio;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("isDeleted")
    private String isDeleted;
    @SerializedName("banned")
    private String banned;

    public String get_id() {
        return _id;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender='" + gender + '\'' +
                ", preference='" + preference + '\'' +
                ", bio='" + bio + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", banned='" + banned + '\'' +
                '}';
    }
}
