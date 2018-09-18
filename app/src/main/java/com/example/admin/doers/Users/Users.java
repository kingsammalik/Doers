package com.example.admin.doers.Users;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 4/21/2018.
 */

public class Users {
    public String id;
    public String name;
    public String mobile;
    public String email;
    public String  unique_id;
    Context context;
    SharedPreferences sharedPreferences;

   public Users(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("register_details",Context.MODE_PRIVATE);

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
       name=sharedPreferences.getString(name,"");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("name",name).apply();
    }

    public String getMobile() {
        mobile=sharedPreferences.getString(mobile,"");
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        sharedPreferences.edit().putString("mobile",mobile).apply();

    }

    public String getEmail() {
        email=sharedPreferences.getString(email,"");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).apply();
    }

    public String getUnique_id() {
       unique_id=sharedPreferences.getString(unique_id,"");
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
        sharedPreferences.edit().putString("unique_id",unique_id).apply();
    }

    public void removeUser()
    {

        sharedPreferences.edit().clear().commit();

    }
}