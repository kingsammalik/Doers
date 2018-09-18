package com.example.admin.doers.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.doers.R;

import java.io.IOException;
import java.io.InputStream;


public class Terms_Fragment extends Fragment {
    TextView privatepolicyy;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_terms_, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        }

        privatepolicyy=view.findViewById(R.id.privatepolicy);
        try {
            Resources res = getResources();
            InputStream in_S = res.openRawResource(R.raw.privatepolicy);
            byte[] b = new byte[in_S.available()];
            in_S.read(b);
            privatepolicyy.setText(new String(b));
        } catch (IOException e) {
            privatepolicyy.setText("error cant show terms");
        }

        return view;
    }

}
