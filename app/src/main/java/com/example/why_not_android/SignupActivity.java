package com.example.why_not_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.signupMailEdit)
    EditText mailEdit;
    @BindView(R.id.signupUsernameEdit)
    EditText usernameEdit;
    @BindView(R.id.signupPasswordEdit)
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        Button signupnbtn = findViewById(R.id.signupButton);
        signupnbtn.setOnClickListener(v -> {
            String mail = mailEdit.getText().toString();
            String name = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            Intent intent = new Intent(SignupActivity.this, Signup2Activity.class);
            intent.putExtra("mail",mail);
            intent.putExtra("name",name);
            intent.putExtra("password",password);
            startActivity(intent);
        });
    }
}
