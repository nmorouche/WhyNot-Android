package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.adapter.EventAdapter;
import com.example.why_not_android.data.Models.Event;
import com.example.why_not_android.data.service.providers.NetworkProvider;


import com.example.why_not_android.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventList extends MenuActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.event_rcv)
    RecyclerView eventRcv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private EventAdapter eventAdapter;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
        setupToolbar();
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
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            NetworkProvider.getInstance().getMyEvents(new NetworkProvider.Listener<List<Event>>() {
                @Override
                public void onSuccess(List<Event> data) {
                    if (data.size() > 0) {
                        eventAdapter.setEventList(data);
                    } else {
                    }

                }

                @Override
                public void onError(Throwable t) {

                }
            });
        } else {
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
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View header = navigationView.getHeaderView(0);
        TextView drawerUsername = (TextView) header.findViewById(R.id.drawer_username);
        TextView drawerEmail = (TextView) header.findViewById(R.id.drawer_email);
        ImageView drawerImageView = (ImageView) header.findViewById(R.id.drawer_image);
        header.setOnClickListener(v -> {
            Intent myProfil = new Intent(this, MyProfilActivity.class);
            startActivity(myProfil);
        });
        String image = sharedPreferences.getString("photo", "");
        Glide.with(this).load(image.replace("localhost", "10.0.2.2")).into(drawerImageView);
        drawerEmail.setText(sharedPreferences.getString("email", ""));
        drawerUsername.setText(sharedPreferences.getString("username", ""));
    }

}
