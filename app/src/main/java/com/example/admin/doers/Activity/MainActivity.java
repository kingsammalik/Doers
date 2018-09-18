package com.example.admin.doers.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.R;
import com.example.admin.doers.Users.Users;

public class MainActivity extends AppCompatActivity {

    protected static final int delay=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         final Users users= new Users(MainActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(ComminPreference.getPreferencesString(MainActivity.this,"mobile")!="") {

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }

                else {

                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, delay);

    }
    }

