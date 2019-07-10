package com.example.why_not_android.data.service;

import android.content.SharedPreferences;

import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.EventsDTO;
import com.example.why_not_android.data.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface EventService {

    @GET("events") Call<EventsDTO> getEvents();
    @POST("events/register") Call<RegisterDTO> register(@Body RegisterDTO registerDTO);
}
