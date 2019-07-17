package com.example.why_not_android.views;

import android.os.Bundle;

import com.example.why_not_android.R;

public class MyEventActivity extends EventList {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
    }
}
