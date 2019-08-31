package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.MessageDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ChatService {
    @PUT("chat/")
    Call<ResponseBody> insertMessage(@Header("x-access-token") String token, @Body MessageDTO messageDTO);

    @GET("chat/")
    Call<List<MessageDTO>> getMessages(@Header("x-access-token") String token, @Query("_id") String id);
}
