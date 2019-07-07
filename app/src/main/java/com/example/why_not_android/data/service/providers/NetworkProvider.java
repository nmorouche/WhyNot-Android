package com.example.why_not_android.data.service.providers;

import android.util.Log;

import com.example.why_not_android.data.dto.EventDTO;
import com.example.why_not_android.data.dto.mapper.EventMapper;
import com.example.why_not_android.data.model.Event;
import com.example.why_not_android.data.service.EventService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkProvider {
    EventService eventService;
    private static NetworkProvider instance;

    public static NetworkProvider getInstance() {
        if (instance == null) {
            instance = new NetworkProvider();
        }
        return instance;
    }

    private NetworkProvider() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://demo2421622.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        eventService = retrofit.create(EventService.class);
    }

    public void getEvents(Listener<List<Event>> listener) {
        eventService.getEvents().enqueue(new Callback<List<EventDTO>>() {
            @Override public void onResponse(Call<List<EventDTO>> call, Response<List<EventDTO>> response) {
                List<EventDTO> eventDTOList = response.body();
                List<Event> eventList = EventMapper.map(eventDTOList);
                Log.d("suc","success");
                listener.onSuccess(eventList);
            }

            @Override public void onFailure(Call<List<EventDTO>> call, Throwable t) {
                listener.onError(t);
                Log.d("toz","fail");
            }
        });
    }

    public interface Listener<T> {
        void onSuccess(T data);

        void onError(Throwable t);
    }
}
