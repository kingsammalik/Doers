package com.example.admin.doers.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.doers.Adapter.Silver_Adapter;
import com.example.admin.doers.Model.Silver_model;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ruby extends Fragment {

    RecyclerView recycler;
    public static final String url="http://teamdoers.in/admin/webservices/ruby_ranker.php";
    ProgressDialog progressDialog;
    int numberOfColumns=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ruby, container, false);
        recycler = view.findViewById(R.id.recyclerruby);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        recycler.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));
        recycler.setAdapter(new Silver_Adapter(true));
        GoldData();

        return view;

    }


    void GoldData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("data", response);
                progressDialog.setMessage("Getting Data....");
                 showDialog();
                System.out.println("response" + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Silver_model[] users = gson.fromJson(response, Silver_model[].class);
                System.out.println("response" + response);
                recycler.setAdapter(new Silver_Adapter(getContext(), users,false));
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        MySingelton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    void hideDialog() {

        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }
}