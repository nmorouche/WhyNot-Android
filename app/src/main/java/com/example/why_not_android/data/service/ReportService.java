package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.RegisterDTO;
import com.example.why_not_android.data.dto.ReportDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ReportService {

    @POST("reports/create")
    Call<ReportDTO> report(@Header("x-access-token") String token, @Body ReportDTO reportDTO);
}
