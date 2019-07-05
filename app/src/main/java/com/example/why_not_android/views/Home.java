package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.UserDTO;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        this.configureBottomView();
        testSingleton();
    }

    void testSingleton() {
        Log.d("toz4", Signup.getClient().toString());
    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer) {
        switch (integer) {
            case R.id.action_profil:
                Intent intent = new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_events:
                Intent intent2 = new Intent(Home.this, EventList.class);
                startActivity(intent2);
                break;

        }
        return true;
    }
}
