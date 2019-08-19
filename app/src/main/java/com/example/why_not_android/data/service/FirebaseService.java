package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.FBMNotificationDTO;
import com.example.why_not_android.data.dto.FBMPushNotificationDTO;
import com.example.why_not_android.data.dto.FirebaseDTO;
import com.example.why_not_android.data.dto.FirebaseTokenDTO;
import com.example.why_not_android.data.dto.RegisterResultDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FirebaseService {

    @POST("firebase/registrate")
    Call<RegisterResultDTO> registration(@Header("x-access-token") String token, @Body FirebaseDTO firebaseDTO);

    @GET("firebase/")
    Call<FirebaseTokenDTO> getFirebaseToken(@Header("x-access-token") String token, @Query("_id") String id);

    @POST("https://fcm.googleapis.com/fcm/send")
    Call<ResponseBody> sendNotification(@Header("Authorization") String header, @Body FBMPushNotificationDTO fbmPushNotificationDTO);
}
