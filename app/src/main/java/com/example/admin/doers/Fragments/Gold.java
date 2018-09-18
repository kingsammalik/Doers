package com.example.admin.doers.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.admin.doers.Activity.HomeActivity;
import com.example.admin.doers.Adapter.Gold_Adapter;
import com.example.admin.doers.Model.Gold_model;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class Gold extends Fragment {

    RecyclerView recycler;
    public static final String url="http://teamdoers.in/admin/webservices/silver_ranker.php";
    public static final String favurl="http://teamdoers.in/admin/webservices/favourite.php";
    ProgressDialog progressDialog;
    int numberOfColumns = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gold, container, false);
        recycler=view.findViewById(R.id.recyclergold);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        recycler.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));



        GoldData();

        return view;

    }


    void GoldData()
    {

        StringRequest stringRequest=new StringRequest( Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("data",response);
                progressDialog.setMessage("Getting Data....");
              //  showDialog();
                System.out.println("response"+response);
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                Gold_model[]  users=gson.fromJson(response,Gold_model[].class);
                System.out.println("response"+response);
                recycler.setAdapter(new Gold_Adapter(getContext(),users));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        MySingelton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    void  hideDialog(){

        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }


    void Favourites(){

        progressDialog.setMessage("Adding to Favourites.....");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, favurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.d("response", response);
                Log.d("fav",response);
                Intent intent=new Intent(getContext(),Myfavourite_fragment.class);
                startActivity(intent);
                getActivity().finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Register error " + error.getMessage());
                Toast.makeText(getContext(),"something went wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile",ComminPreference.getPreferencesString(getContext(),"mobile"));


                return params;
            }
        };

        MySingelton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}


