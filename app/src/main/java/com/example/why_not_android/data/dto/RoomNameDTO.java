package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class RoomNameDTO {
    @SerializedName("roomName")
    String roomName;
    @SerializedName("myID")
    String myID;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMyID() {
        return myID;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }

    @Override
    public String toString() {
        return "RoomNameDTO{" +
                "roomName='" + roomName + '\'' +
                '}';
    }
}
