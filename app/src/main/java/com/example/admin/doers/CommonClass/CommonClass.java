package com.example.admin.doers.CommonClass;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.admin.doers.Activity.HomeActivity;
import com.example.admin.doers.Users.Users;

public class CommonClass extends Application {
    private static final String PREFERENCE_TAG = "com.vincittech.customer.global.sharedpreference";
    public static final String TAG = CommonClass.class.getSimpleName();
    private static CommonClass globalInstance;
   static   ConnectivityManager connectivityManager;



    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onCreate() {
        super.onCreate();
        globalInstance = this;
    }

}
