package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.ImageDTO;
import com.example.why_not_android.data.dto.LoginDTO;
import com.example.why_not_android.data.dto.SessionDTO;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface SessionService {
    @POST("users/login")
    Call<SessionDTO> login(@Body LoginDTO loginDTO);

    @Multipart
    @PUT("test/single")
    Call<ImageDTO> uploadImage(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );
}