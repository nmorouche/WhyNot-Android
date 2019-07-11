package com.example.why_not_android.data.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static SharedPreferences instance;

    public static SharedPreferences getInstance(Activity context) {
        if (instance == null) {
            instance = context.getSharedPreferences("why-not", Context.MODE_PRIVATE);
        }
        return instance;
    }

    public static String getToken() {
        return instance.getString("token", "");
    }
}
