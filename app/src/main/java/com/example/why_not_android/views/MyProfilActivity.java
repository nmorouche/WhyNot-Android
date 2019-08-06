package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.User;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.LoginDTO;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.UserService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfilActivity extends MenuActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.activity_my_profil_email_edit_text)
    EditText emailET;
    @BindView(R.id.activity_my_profil_username_edit_text)
    EditText usernameET;
    @BindView(R.id.activity_my_profil_password_edit_text)
    EditText passwordET;
    @BindView(R.id.activity_my_profil_male_radio_button)
    RadioButton maleRadioButton;
    @BindView(R.id.activity_my_profil_female_radio_button)
    RadioButton femaleRadioButton;
    @BindView(R.id.activity_my_profil_female_logo)
    ImageView femaleLogo;
    @BindView(R.id.activity_my_profil_male_logo)
    ImageView maleLogo;
    @BindView(R.id.activity_my_profil_image_view)
    ImageView userImage;


    @BindView(R.id.activity_my_profil_validation_image_view)
    ImageView validationIV;
    @BindView(R.id.activity_my_profil_edit_image_view)
    ImageView editIV;
    @BindView(R.id.activity_my_profil_cancel_edit_image_view)
    ImageView cancelIV;

    private SharedPreferences sharedPreferences;
    private UserDTO userDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profil);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
        setupView();
    }

    void setupView() {
        setupToolbar();
        myAccount();
    }

    private void setupInfo() {
        emailET.setEnabled(false);
        usernameET.setEnabled(false);
        passwordET.setEnabled(false);
        maleRadioButton.setEnabled(false);
        femaleRadioButton.setEnabled(false);
        emailET.setText(userDTO.getEmail());
        usernameET.setText(userDTO.getUsername());
        passwordET.setText(userDTO.getPassword());
        switch (userDTO.getGender()) {
            case 0:
                maleLogo.setVisibility(View.VISIBLE);
                femaleLogo.setVisibility(View.INVISIBLE);
                break;
            case 1:
                femaleLogo.setVisibility(View.VISIBLE);
                maleLogo.setVisibility(View.INVISIBLE);
                break;
            default:
                maleLogo.setVisibility(View.VISIBLE);
                femaleLogo.setVisibility(View.INVISIBLE);
                break;
        }
        switch (userDTO.getPreference()) {
            case 0:
                maleRadioButton.setChecked(true);
                break;
            case 1:
                femaleRadioButton.setChecked(true);
                break;
            default:
                break;
        }
        Glide.with(this).load(userDTO.getPhoto().replace("localhost", "10.0.2.2")).into(userImage);
        maleLogo.setEnabled(false);
        femaleLogo.setEnabled(false);
        maleRadioButton.setChecked(true);
        cancelIV.setVisibility(View.INVISIBLE);
        validationIV.setVisibility(View.INVISIBLE);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View header = navigationView.getHeaderView(0);
        TextView drawerUsername = header.findViewById(R.id.drawer_username);
        TextView drawerEmail = header.findViewById(R.id.drawer_email);
        ImageView drawerImageView = header.findViewById(R.id.drawer_image);
        header.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
        });
        String image = sharedPreferences.getString("photo", "");
        Glide.with(this).load(image.replace("localhost", "10.0.2.2")).into(drawerImageView);
        drawerEmail.setText(sharedPreferences.getString("email", ""));
        drawerUsername.setText(sharedPreferences.getString("username", ""));
    }

    private void myAccount() {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");

        Call<UserDTO> userDTOCall = userService.myAccount(token);
        userDTOCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                userDTO = response.body();
                if (response.isSuccessful()) {
                    setupInfo();
                } else if (response.body() == null) {
                    try {
                        JSONObject errorJSON = new JSONObject(response.errorBody().string());
                        Toast.makeText(MyProfilActivity.this, errorJSON.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("toz", t.toString());
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.activity_my_profil_edit_image_view)
    void edit() {
        emailET.setEnabled(true);
        usernameET.setEnabled(true);
        passwordET.setEnabled(true);
        maleRadioButton.setEnabled(true);
        femaleRadioButton.setEnabled(true);
        maleLogo.setEnabled(true);
        femaleLogo.setEnabled(true);
        editIV.setVisibility(View.INVISIBLE);
        validationIV.setVisibility(View.VISIBLE);
        cancelIV.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.activity_my_profil_cancel_edit_image_view)
    void cancel() {
        setupInfo();
        maleLogo.setEnabled(false);
        femaleLogo.setEnabled(false);
        editIV.setVisibility(View.VISIBLE);
        validationIV.setVisibility(View.INVISIBLE);
        cancelIV.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.activity_my_profil_male_logo)
    void changerGenderToFemale() {
        maleLogo.setVisibility(View.INVISIBLE);
        femaleLogo.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.activity_my_profil_female_logo)
    void changerGenderToMale() {
        femaleLogo.setVisibility(View.INVISIBLE);
        maleLogo.setVisibility(View.VISIBLE);
    }
}
