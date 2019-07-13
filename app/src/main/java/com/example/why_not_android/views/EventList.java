package com.example.why_not_android.views;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.why_not_android.DetailEvent;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.adapter.EventAdapter;
import com.example.why_not_android.data.model.Event;
import com.example.why_not_android.data.service.providers.NetworkProvider;


import com.example.why_not_android.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventList extends AppCompatActivity {

    private EventAdapter eventAdapter;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.event_rcv)
    RecyclerView eventRcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);
        this.configureBottomView();
        initRcv();
        loadData();
    }


    private void initRcv() {
        Log.d("init", "init");
        eventRcv.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter();
        eventRcv.setAdapter(eventAdapter);
        eventAdapter.setItemClickListener(new EventAdapter.ItemClickListener() {
            @Override
            public void onclick(Event event) {
                //todo
                Intent intent = new Intent(EventList.this, DetailEvent.class);
                intent.putExtra("eventName", event.getName());
                intent.putExtra("eventPic", event.getImageURL());
                intent.putExtra("eventAddress", event.getAddress());
                intent.putExtra("eventPrice", event.getPrice());
                intent.putExtra("eventDesc", event.getDescription());
                intent.putExtra("eventDate", event.getDate());
                intent.putExtra("eventid", event.get_id());
                startActivity(intent);

            }
        });
    }

    private void loadData() {
        NetworkProvider.getInstance().getEvents(new NetworkProvider.Listener<List<Event>>() {
            @Override
            public void onSuccess(List<Event> data) {
                eventAdapter.setEventList(data);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer) {
        switch (integer) {
            case R.id.action_profil:
                Intent intent = new Intent(EventList.this, Home.class);
                startActivity(intent);
                break;
            case R.id.action_events:
                Intent intent2 = new Intent(EventList.this, EventList.class);
                startActivity(intent2);
                break;

        }
        return true;
    }
}
