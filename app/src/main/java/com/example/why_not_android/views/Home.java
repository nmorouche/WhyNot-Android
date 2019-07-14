package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.MatchDTO;
import com.example.why_not_android.data.dto.RegisterResultDTO;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.service.UserService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.activity_home_iv)
    ImageView imageView;
    @BindView(R.id.activity_home_user_description_tv)
    TextView textView;
    @BindView(R.id.activity_home_like_button)
    Button likeButton;
    @BindView(R.id.activity_home_dislike_button)
    Button dislikeButton;
    @BindView(R.id.activity_home_empty_tv)
    TextView emptyTextView;
    private SharedPreferences sharedPreferences;
    private ArrayList<UserDTO> userDTOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        this.configureBottomView();
        sharedPreferences = SharedPref.getInstance(this);
        hideButtons();
        getUsers();
    }

    @OnClick(R.id.activity_home_like_button)
    void like() {
        setLiked(userDTOList.get(0).get_id());
        cleanUserList();
    }

    @OnClick(R.id.activity_home_dislike_button)
    void dislike() {
        setViewed(userDTOList.get(0).get_id());
        cleanUserList();
    }

    void getUsers() {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");

        Call<ArrayList<UserDTO>> userDTOCall = userService.getUsers(token);
        userDTOCall.enqueue(new Callback<ArrayList<UserDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDTO>> call, Response<ArrayList<UserDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        userDTOList = response.body();
                        UserDTO userDTO = userDTOList.get(0);
                        textView.setText(userDTO.getUsername() + "\n" + userDTO.getPreference());
                        String url = userDTO.getPhoto();
                        url = url.replace("localhost", "10.0.2.2");
                        Glide.with(Home.this).load(url).into(imageView);
                        displayButtons();
                    } else {
                        emptyUserList();
                    }
                } else {
                    try {
                        //Return to MainActivity if the user is no longer connected.
                        Intent intent = new Intent(Home.this, LoginActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserDTO>> call, Throwable t) {
                Log.d("toz", "CA MARCHE PAS CA MARCHE PAS QUAND JE CLIQUE SUR LE LIEN CA MARCHE PAS");
            }
        });

    }

    void setViewed(String id) {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");

        Call<RegisterResultDTO> registerResultDTOCall = userService.setViewed(token, id);
        registerResultDTOCall.enqueue(new Callback<RegisterResultDTO>() {
            @Override
            public void onResponse(Call<RegisterResultDTO> call, Response<RegisterResultDTO> response) {

            }

            @Override
            public void onFailure(Call<RegisterResultDTO> call, Throwable t) {
                Toast.makeText(Home.this, "CA MARCHE PAS :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setLiked(String id) {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");

        Call<MatchDTO> matchDTOCall = userService.like(token, id);
        matchDTOCall.enqueue(new Callback<MatchDTO>() {
            @Override
            public void onResponse(Call<MatchDTO> call, Response<MatchDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("CA MARCHE", response.body().toString());
                } else {
                    Log.d("CA MARCHE", "CA MARCHE PAS :(");
                }
            }

            @Override
            public void onFailure(Call<MatchDTO> call, Throwable t) {
                Log.d("CA MARCHE", "CA FAIL :(");
            }
        });
    }

    private void emptyUserList() {
        imageView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        emptyTextView.setText("YA R MA GUEULE");
        emptyTextView.setVisibility(View.VISIBLE);
    }

    private void cleanUserList() {
        if (userDTOList.size() == 1) {
            getUsers();
        } else {
            userDTOList.remove(0);
            UserDTO userDTO = userDTOList.get(0);
            textView.setText(userDTO.getUsername() + "\n" + userDTO.getPreference());
            Glide.with(Home.this).load(userDTO.getPhoto().replace("localhost", "10.0.2.2")).into(imageView);
        }
    }

    private void hideButtons() {
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.INVISIBLE);
    }

    private void displayButtons() {
        likeButton.setVisibility(View.VISIBLE);
        dislikeButton.setVisibility(View.VISIBLE);
    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer) {
        switch (integer) {
            case R.id.action_profil:
                Intent intent = new Intent(Home.this, Home.class);
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
