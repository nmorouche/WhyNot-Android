package com.example.why_not_android.data.service;


import com.example.why_not_android.data.dto.EventsDTO;
import com.example.why_not_android.data.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface EventService {

    @GET("events")
    Call<EventsDTO> getEvents(@Header("x-access-token") String token);

    @POST("events/register")
    Call<RegisterDTO> register(@Header("x-access-token") String token, @Body RegisterDTO registerDTO);
}
