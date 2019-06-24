package com.example.why_not_android;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventList extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.event_rcv)
    RecyclerView eventRcv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);
        this.configureBottomView();
        initRcv();
    }


    private void initRcv(){
        //todo faire l'init
    }

    private void loadData(){
        //todo faire le load avec connexion api

    }

    private void configureBottomView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.action_profil:
                Intent intent = new Intent(EventList.this, LoginActivity.class);
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
