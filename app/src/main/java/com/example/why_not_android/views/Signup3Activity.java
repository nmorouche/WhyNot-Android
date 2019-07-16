package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.MyPermissions;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.UserService;
import com.example.why_not_android.data.service.providers.NetworkProvider;
import com.google.gson.Gson;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup3Activity extends AppCompatActivity {

    @BindView(R.id.signup3Bio)
    EditText bioEdit;
    @BindView(R.id.signup3radioGroup)
    RadioGroup prefRadio;
    int preferences = -1;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
        prefRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup3radioButtonHomme) {
                preferences = 0;
            } else if (checkedId == R.id.signup3radioButtonFemme) {
                preferences = 1;
            } else {
                preferences = 2;
            }
        });
    }

    private void signup(Uri fileUri, File imageFile) {
        SessionService sessionService;
        sessionService = NetworkProvider.getClient().create(SessionService.class);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        imageFile
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        // add another part within the multipart request
        RequestBody email =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getEmail());
        RequestBody username =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getUsername());
        RequestBody password =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getPassword());
        RequestBody gender =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getGender()));
        RequestBody birthdate =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getBirthdate());
        RequestBody bio =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getBio());
        RequestBody preference =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getPreferences()));

        // finally, execute the request
        Call<SessionDTO> call = sessionService.signup(
                email,
                username,
                password,
                gender,
                birthdate,
                bio,
                preference,
                body);
        call.enqueue(new Callback<SessionDTO>() {
            @Override
            public void onResponse(Call<SessionDTO> call,
                                   Response<SessionDTO> response) {
                if (response.isSuccessful()) {
                    SessionDTO sessionDTO = response.body();
                    sharedPreferences.edit()
                            .putString("token", sessionDTO.getToken())
                            .apply();
                    Intent intent = new Intent(Signup3Activity.this, Home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Signup3Activity.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SessionDTO> call, Throwable t) {
                Toast.makeText(Signup3Activity.this, "CA MARCHE PAS", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.signup2Button)
    void submit() {
        if (preferences == -1) {
            Toast.makeText(this, "Selectionner une préférence", Toast.LENGTH_SHORT).show();
        } else if (bioEdit.getText().toString().length() == 0) {
            Toast.makeText(this, "Merci d'entrer une bio", Toast.LENGTH_SHORT).show();
        } else {
            Signup.getClient().setBio(bioEdit.getText().toString());
            Signup.getClient().setPreferences(preferences);
            signup(Signup.getClient().getFileUri(), Signup.getClient().getImageFile());
        }
    }
}