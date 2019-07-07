package com.example.why_not_android;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailEvent extends AppCompatActivity {

    @BindView(R.id.activity_detail_event_name)
    TextView nameTv;
    @BindView(R.id.activity_detail_event_imageView)
    ImageView imageIv;
    @BindView(R.id.activity_detail_event_add)
    TextView addressTv;
    @BindView(R.id.activity_detail_event_price)
    TextView priceTv;
    @BindView(R.id.activity_detail_event_desc)
    TextView descTv;
    @BindView(R.id.activity_detail_event_date)
    TextView dateTv;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("eventName");
        String image = extras.getString("eventPic");
        String address = extras.getString("eventAddress");
        Integer price = extras.getInt("eventPrice");
        String desc = extras.getString("eventDesc");
        String date = extras.getString("eventDate");
        nameTv.setText(name);
        Glide.with(DetailEvent.this).load(image).into(imageIv);
        addressTv.setText(address);
        priceTv.setText(price.toString());
        descTv.setText(desc);
        dateTv.setText(date);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_register:
                Log.d("test","test");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
