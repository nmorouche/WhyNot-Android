package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsDTO {

    @SerializedName("events")
    private ArrayList<EventDTO> events;

    public ArrayList<EventDTO> getEventDTOArrayList() {
        return events;
    }
}
