package com.example.why_not_android.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.why_not_android.R;
//import com.example.why_not_android.data.Models.Signup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup3Activity extends AppCompatActivity {

    @BindView(R.id.signup3Bio)
    EditText bioEdit;
    @BindView(R.id.signup3radioGroup)
    RadioGroup prefRadio;
    String preferences = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        ButterKnife.bind(this);
        //Log.d("toz", Signup.getClient().toString());
        prefRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup3radioButtonHomme) {
                preferences = "homme";
            } else if (checkedId == R.id.signup3radioButtonFemme) {
                preferences = "femme";
            } else {
                preferences = "les 2";
            }
        });
    }

    @OnClick(R.id.signup2Button)
    void submit() {
        if (preferences.length() == 0) {
            Toast.makeText(this, "Selectionner une préférence", Toast.LENGTH_SHORT).show();
        } else if (bioEdit.getText().toString().length() == 0) {
            Toast.makeText(this, "Merci d'entrer une bio", Toast.LENGTH_SHORT).show();
        } else {
           // Signup.getClient().setBio(bioEdit.getText().toString());
           // Signup.getClient().setPreferences(preferences);
           // Log.d("toz", Signup.getClient().getPreferences());
            //Log.d("toz", Signup.getClient().toString());
            Intent intent = new Intent(Signup3Activity.this, Home.class);
            startActivity(intent);
        }
    }
}