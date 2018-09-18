package com.example.admin.doers.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ComminPreference {

    public static boolean getPreferencesBoolean(Context context, String key) {

        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void savePreferencesBoolean(Context context, String key, boolean value) {

        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void savePreferenceString(Context context, String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }


    public static String getPreferencesString(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,"");
    }

    public static void savePreferenceInt(Context context, String key, int value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();

    }

    public static int getPreferencesInt(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);

    }

    public static void removeAllPreference(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("mobile");
        editor.remove("password");
        editor.apply();

    }

}
