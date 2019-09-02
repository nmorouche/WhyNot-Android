package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.LoginDTO;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginMailEdit)
    EditText email;
    @BindView(R.id.loginPasswordEdit)
    EditText password;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
    }

    @OnClick(R.id.loginButton)
    void login() {
        if (email.getText().length() == 0 || password.getText().length() == 0) {
            Toast.makeText(this, "Merci de remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            connection(email.getText().toString(), password.getText().toString());
        }
    }

    private void connection(String email, String password) {
        SessionService sessionService;
        sessionService = NetworkProvider.getClient().create(SessionService.class);
        LoginDTO loginDTO = new LoginDTO(email, password);

        Call<SessionDTO> sessionDTOCall = sessionService.login(loginDTO);
        sessionDTOCall.enqueue(new Callback<SessionDTO>() {
            @Override
            public void onResponse(Call<SessionDTO> call, Response<SessionDTO> response) {
                SessionDTO sessionDTO = response.body();
                if (response.isSuccessful()) {
                    String hobbies = "";
                    String[] hobbiesDTO = sessionDTO.getHobbies();
                    for (int i = 0; i < hobbiesDTO.length; i++) {
                        hobbies += hobbiesDTO[i];
                        if (i != hobbiesDTO.length - 1) {
                            hobbies += ", ";
                        }
                    }
                    sharedPreferences.edit()
                            .putString("token", sessionDTO.getToken())
                            .putString("email", sessionDTO.getEmail())
                            .putString("username", sessionDTO.getUsername())
                            .putString("photo", sessionDTO.getPhoto())
                            .putString("hobbies", hobbies)
                            .putString("password", password)
                            .apply();
                    Intent intent = new Intent(LoginActivity.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (response.body() == null) {
                    try {
                        JSONObject errorJSON = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, errorJSON.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SessionDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
