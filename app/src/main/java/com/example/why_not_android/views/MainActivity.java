package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = SharedPref.getInstance(this);
        if (!sharedPreferences.getString("token", "").equals("")) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
        Button signupbtn = findViewById(R.id.button2);
        signupbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        Button loginbtn = findViewById(R.id.button);
        loginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}

