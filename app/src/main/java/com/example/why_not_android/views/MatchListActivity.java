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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Event;
import com.example.why_not_android.data.Models.User;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.adapter.EventAdapter;
import com.example.why_not_android.data.adapter.MatchAdapter;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchListActivity extends MenuActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.match_rcv)
    RecyclerView recyclerView;

    private MatchAdapter matchAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
        setupToolbar();
        initRcv();
        loadData();
    }

    private void initRcv() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchAdapter = new MatchAdapter();
        recyclerView.setAdapter(matchAdapter);
        matchAdapter.setItemClickListener(user -> {
            //Intent intent = new Intent(MatchListActivity.this, DetailUser.class);
            Intent intent = new Intent(MatchListActivity.this, MessageActivity.class);
            intent.putExtra("userName", user.getUsername());
            intent.putExtra("userBio", user.getBio());
            intent.putExtra("userBirth", user.getBirthdate());
            intent.putExtra("userPic", user.getPhoto());
            intent.putExtra("userid", user.get_id());
            startActivity(intent);
        });
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

    private void loadData() {
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        NetworkProvider.getInstance().getMatch(new NetworkProvider.Listener<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                if (data.size() > 0) {
                    matchAdapter.setEventList(data);
                } else {

                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}