package com.example.why_not_android.data.dto.mapper;

import android.util.Log;

import com.example.why_not_android.data.dto.EventDTO;
import com.example.why_not_android.data.Models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static List<Event> map(List<EventDTO> eventDTOList) {
        List<Event> eventList = new ArrayList<>();
        for (EventDTO eventDTO : eventDTOList) {
            Log.d("event",eventDTO.toString());
            eventList.add(map(eventDTO));
        }
        return eventList;
    }

    private static Event map(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setAddress(eventDTO.getAddress());
        event.setDate(eventDTO.getDate());
        event.setImageURL(eventDTO.getImageURL());
        event.setPrice(eventDTO.getPrice());
       event.set_id(eventDTO.get_id());
        return event;
    }
}
