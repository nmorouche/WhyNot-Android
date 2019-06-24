package com.example.why_not_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Signup3Activity extends AppCompatActivity {

    @BindView(R.id.signup3Bio)
    EditText bioEdit;
    @BindView(R.id.signup3radioGroup)
    RadioGroup prefRadio;

    String pref = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        ButterKnife.bind(this);
        prefRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup3radioButtonHomme) {
                pref = "homme";
            } else if(checkedId == R.id.signup3radioButtonFemme) {
                pref="femme";
            } else {
                pref="les 2";
            }
        });


        Button signupnbtn = findViewById(R.id.signup2Button);
        signupnbtn.setOnClickListener(v -> {
            Bundle extras = getIntent().getExtras();
            String mail = extras.getString("mail");
            String name = extras.getString("name");
            String password = extras.getString("password");
            String gender = extras.getString("gender");
            String bdate = extras.getString("bdate");
            String image = extras.getString("image");
            String bio = bioEdit.getText().toString();
            Intent intent = new Intent(Signup3Activity.this, Home.class);
            intent.putExtra("mail",mail);
            intent.putExtra("name",name);
            intent.putExtra("password",password);
            intent.putExtra("gender",gender);
            intent.putExtra("bdate",bdate);
            intent.putExtra("image",image);
            intent.putExtra("bio",bio);
            intent.putExtra("pref",pref);

            startActivity(intent);
        });
    }
}
