package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailUser extends AppCompatActivity {
    @BindView(R.id.activity_detail_user_bio)
    TextView biotv;
    @BindView(R.id.activity_detail_user_birthdate)
    TextView birthdatetv;
    @BindView(R.id.activity_detail_user_name)
    TextView nametv;
    @BindView(R.id.activity_detail_user_image)
    ImageView imageView;
    String id;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        sharedPreferences = SharedPref.getInstance(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("userName");
        String bio = extras.getString("userBio");
        String birth = extras.getString("userBirth");
        String image = extras.getString("userPic");
        id = extras.getString("userid");
        biotv.setText(bio);
        birthdatetv.setText(birth);
        nametv.setText(String.format("%s %s", name, getAge(birth)));
        Glide.with(DetailUser.this).load(image.replace("localhost", "10.0.2.2")).into(imageView);

    }

    private String getAge(String s) {
        int age;
        int day;
        int month;
        int birthdateInt;
        int actualMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int actualDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.FRANCE);
        Date date = new Date();
        birthdateInt = Integer.valueOf(formatter.format(date));
        String[] splitted = s.split("/");
        day = Integer.valueOf(splitted[0]);
        month = Integer.valueOf(splitted[1]);
        age = Integer.valueOf(splitted[2]);
        if (month >= actualMonth) {
            if (day >= actualDay) {
                age += 1;
            }
        }
        birthdateInt -= age;
        return String.valueOf(birthdateInt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_report:
                Intent intent = new Intent(DetailUser.this, Report.class);
                intent.putExtra("userid", id);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}