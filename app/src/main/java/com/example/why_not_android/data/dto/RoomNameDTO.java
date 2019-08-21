package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class RoomNameDTO {
    @SerializedName("roomName")
    String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "RoomNameDTO{" +
                "roomName='" + roomName + '\'' +
                '}';
    }
}
