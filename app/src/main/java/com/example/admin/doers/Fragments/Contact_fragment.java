package com.example.admin.doers.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.doers.Activity.HomeActivity;
import com.example.admin.doers.R;
import com.example.admin.doers.Users.Users;
import com.example.admin.doers.VolleySingelton.MySingelton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Contact_fragment extends Fragment {
    EditText name,email,phone,message;
    Button button;
    Typeface typeface;
    ProgressDialog progressDialog;
    private static String contactUrl="http://argwork-com.stackstaging.com/build2/teamdoers/webservices/contact.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_fragment, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        email=view.findViewById(R.id.email);
        message=view.findViewById(R.id.message);
        button=view.findViewById(R.id.submit);

        typeface=Typeface.createFromAsset(getActivity().getAssets(),"Fonts/OpenSans/OpenSans-SemiBold.ttf");
        name.setTypeface(typeface);
        phone.setTypeface(typeface);
        email.setTypeface(typeface);
        message.setTypeface(typeface);
        button.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Message Sent")
                        .setMessage("Your Message has been sent Successfully")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                InsertData();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        return view;
    }

    void InsertData() {

        final String names=name.getText().toString();
        final String emails=email.getText().toString();
        final String mobiles=phone.getText().toString();
        final String messages=message.getText().toString();
        progressDialog.setMessage("Registering.....");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, contactUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                    //Log.d("chk",users.getName());
                    Intent intent=new Intent(getContext(),HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Register error " + error.getMessage());
                Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("name",names);
                params.put("email",emails);
                params.put("mobile",mobiles);
                params.put("message",messages);

                return params;
            }
        };

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


}
