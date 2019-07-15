package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.LoginDTO;
import com.example.why_not_android.data.dto.RegisterDTO;
import com.example.why_not_android.data.dto.ReportDTO;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.service.ReportService;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Report extends AppCompatActivity {
    @BindView(R.id.activity_report_desc)
    TextView descTv;
    @BindView(R.id.activity_report_rg)
    RadioGroup genderGr;
    private String type = "";
    String id;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        sharedPreferences = SharedPref.getInstance(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
         id = extras.getString("userid");
        genderGr.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonFake) {
                type = "Fake";
            } else {
                type = "Vulgaire";
            }
        });


    }

    @OnClick(R.id.activity_report_btn)
    void report(){

        Log.d("wesh",type +" "+ id +"  "+descTv.getText());

        ReportService reportService;
        reportService = NetworkProvider.getClient().create(ReportService.class);
        ReportDTO reportDTO = new ReportDTO(type, descTv.getText().toString(),id);

        Call<ReportDTO> reportDTOCall = reportService.report(SharedPref.getToken(),reportDTO);
        reportDTOCall.enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                ReportDTO reportDTO = response.body();
                if (response.isSuccessful()) {
                    Log.d("tt","tt");
                    Intent intent = new Intent(Report.this, Home.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
