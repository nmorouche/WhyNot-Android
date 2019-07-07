package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.EventDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventService {
    @GET("events") Call<List<EventDTO>> getEvents();
}
