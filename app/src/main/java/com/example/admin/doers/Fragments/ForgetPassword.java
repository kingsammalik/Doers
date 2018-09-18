package com.example.admin.doers.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.doers.R;

public class ForgetPassword extends Fragment {

    EditText editText;
    Button btnForget;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forget_password, container, false);
         editText=view.findViewById(R.id.email);
         btnForget=view.findViewById(R.id.forget);


        return view;
    }




}
