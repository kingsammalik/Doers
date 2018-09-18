package com.example.admin.doers.Firebase_Notification;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by dell on 13-Oct-17.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    public static final String firebasetoken = null;

    @Override
    public void onTokenRefresh() {

        //Getting registration token0
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        //Displaying token on logcat
         Log.e(TAG, "token: " + refreshedToken);


    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
    }

}