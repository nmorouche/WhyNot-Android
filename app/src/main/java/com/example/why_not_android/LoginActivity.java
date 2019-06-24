package com.example.why_not_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbtn = findViewById(R.id.loginButton);
        loginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Home.class);
            startActivity(intent);
        });
    }
}
