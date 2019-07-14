package com.example.why_not_android.data.service.providers;


import android.util.Log;

import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.EventDTO;
import com.example.why_not_android.data.dto.EventsDTO;
import com.example.why_not_android.data.dto.mapper.EventMapper;
import com.example.why_not_android.data.Models.Event;
import com.example.why_not_android.data.service.EventService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkProvider {

    private EventService eventService;
    private static NetworkProvider instance;

    public static NetworkProvider getInstance() {
        if (instance == null) {
            instance = new NetworkProvider();
        }
        return instance;
    }

    private NetworkProvider() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventService = retrofit.create(EventService.class);
    }

    public void getEvents(Listener<List<Event>> listener) {
        eventService.getEvents(SharedPref.getToken()).enqueue(new Callback<EventsDTO>() {

            @Override
            public void onResponse(Call<EventsDTO> call, Response<EventsDTO> response) {
                if (response.isSuccessful()) {
                    EventsDTO eventsDTOList = response.body();
                    List<EventDTO> event = eventsDTOList.getEventDTOArrayList();
                    List<Event> eventList = EventMapper.map(event);
                    listener.onSuccess(eventList);
                }
            }

            @Override
            public void onFailure(Call<EventsDTO> call, Throwable t) {
                listener.onError(t);
                Log.d("toz", "fail");
            }
        });
    }

    public interface Listener<T> {
        void onSuccess(T data);

        void onError(Throwable t);
    }


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
