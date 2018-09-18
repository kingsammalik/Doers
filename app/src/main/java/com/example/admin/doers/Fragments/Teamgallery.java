package com.example.admin.doers.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.doers.Adapter.GalleryAdapter;
import com.example.admin.doers.Adapter.Gold_Adapter;
import com.example.admin.doers.ItemClickSupport;
import com.example.admin.doers.Model.Gallery_model;
import com.example.admin.doers.Model.Gold_model;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class Teamgallery extends Fragment implements GalleryAdapter.CallBack {

    TextView privatepolicyy;
    android.support.v7.widget.Toolbar toolbar;
    ProgressDialog progressDialog;
    //RecyclerView recyclerView;
    LinearLayout linearLayout;

    int numberOfColumns = 2;
    public static final String url="http://teamdoers.in/admin/webservices/team_gallery_new.php";
    public static final String favurl="http://teamdoers.in/admin/webservices/favourite.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teamgallery, container, false);
        linearLayout = view.findViewById(R.id.linear);
        //recyclerView=view.findViewById(R.id.recyclergallery);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));

        Gallery();

        return view;
    }




    void Gallery()
    {

        StringRequest stringRequest=new StringRequest( Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("data","resp "+response);
                progressDialog.setMessage("Getting Data....");
                //  showDialog();
                System.out.println("response"+response);
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                Gallery_model[]  users = gson.fromJson(response,Gallery_model[].class);
                initializeViews(users);
                System.out.println("user "+gson.toJson(users));
                //recyclerView.setAdapter(new GalleryAdapter(getContext(),users));
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

    private void initializeViews(Gallery_model[] users) {
        for (int i=0;i<users.length;i+=2){
            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.recyle_lay, null);
            RecyclerView recyclerView = v.findViewById(R.id.recycelview);
            TextView title = v.findViewById(R.id.title);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            title.setText(users[i].getGallery());
            final GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(),users[i+1],this);
            recyclerView.setAdapter(galleryAdapter);
            linearLayout.addView(v);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    galleryAdapter.notifyDataSetChanged();
                }
            });
        }

    }


    void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    void  hideDialog(){

        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }




    void Favourites(final String url){

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
                params.put("mobile", ComminPreference.getPreferencesString(getContext(),"mobile"));
                params.put("image_location", url);
                return params;
            }
        };

        MySingelton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onFavClicked(String url) {
        Favourites(url);
    }
}
