package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterDTO {
    @SerializedName("eventId")
    private String eventId;

    public RegisterDTO(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
