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
import com.example.why_not_android.DetailEvent;
import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.dto.UsersListDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private SharedPreferences sharedPreferences;
    private ArrayList<UserDTO> userDTOList;
    @BindView(R.id.activity_home_iv)
    ImageView imageView;
    @BindView(R.id.activity_home_user_description_tv)
    TextView textView;
    @BindView(R.id.activity_home_like_button)
    Button likeButton;
    @BindView(R.id.activity_home_dislike_button)
    Button dislikeButton;
    @BindView(R.id.testbutton)
    Button test;


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

    void getUsers() {
        SessionService sessionService;
        sessionService = NetworkProvider.getClient().create(SessionService.class);
        String token = sharedPreferences.getString("token", "");

        Call<ArrayList<UserDTO>> userDTOCall = sessionService.getUsers(token);
        userDTOCall.enqueue(new Callback<ArrayList<UserDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDTO>> call, Response<ArrayList<UserDTO>> response) {
                if (response.isSuccessful()) {
                    userDTOList = response.body();
                    UserDTO userDTO = userDTOList.get(0);
                    textView.setText(userDTO.getUsername() + "\n" + userDTO.getPreference());
                    String url = userDTO.getPhoto();
                    url = url.replace("localhost", "10.0.2.2");
                    Glide.with(Home.this).load(url).into(imageView);
                    displayButtons();
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

    @OnClick(R.id.activity_home_like_button)
    void like() {
        cleanUserList();
    }

    @OnClick(R.id.activity_home_dislike_button)
    void dislike() {
        cleanUserList();
    }
    @OnClick(R.id.testbutton)
    void detail(){
        Log.d("wesh","c'est quoi ca ");
        Intent intent = new Intent(Home.this, Report.class);
        //intent.putExtra("userName", userDTOList.get(0).getUsername());
        //intent.putExtra("userBio", userDTOList.get(0).getBio());
        //intent.putExtra("userBirth",userDTOList.get(0).getBirthdate());
        //intent.putExtra("userPic", userDTOList.get(0).getPhoto());
        intent.putExtra("userid", /*userDTOList.get(0).get_id()*/"id");
        startActivity(intent);
    }

    private void cleanUserList() {
        userDTOList.remove(0);
        UserDTO userDTO = userDTOList.get(0);
        textView.setText(userDTO.getUsername() + "\n" + userDTO.getPreference());
        Glide.with(Home.this).load(userDTO.getPhoto().replace("localhost", "10.0.2.2")).into(imageView);
    }

    private void hideButtons() {
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
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
