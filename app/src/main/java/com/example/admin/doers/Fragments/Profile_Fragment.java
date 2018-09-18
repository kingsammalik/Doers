package com.example.admin.doers.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
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
import com.example.admin.doers.Model.Login_model;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Profile_Fragment extends Fragment {

    EditText name,dob,email,address,pincode;
    TextInputLayout inputname,inputdob,inputemail,inputaddress,inputpincode;
    Typeface typeface;
    Button button;
    ProgressDialog progressDialog;
    private  static final String profileUrl="http://teamdoers.in/admin/webservices/update_new.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        button=view.findViewById(R.id.btn_update);

        name=view.findViewById(R.id.name);
        dob=view.findViewById(R.id.dob);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        pincode=view.findViewById(R.id.pincode);

        typeface=Typeface.createFromAsset(getActivity().getAssets(),"Fonts/SourceSansPro-Italic.ttf");
        name.setTypeface(typeface);
        dob.setTypeface(typeface);
        email.setTypeface(typeface);
        address.setTypeface(typeface);
        pincode.setTypeface(typeface);
        button.setTypeface(typeface);

        inputname=view.findViewById(R.id.input_name);
        inputdob=view.findViewById(R.id.input_dob);
        inputemail=view.findViewById(R.id.input_email);
        inputaddress=view.findViewById(R.id.input_address);
        inputpincode=view.findViewById(R.id.input_pincode);



        name.setText(ComminPreference.getPreferencesString(getContext(),"user"));
        email.setText(ComminPreference.getPreferencesString(getContext(),"email"));
        dob.setText(ComminPreference.getPreferencesString(getContext(),"dob"));
        address.setText(ComminPreference.getPreferencesString(getContext(),"address"));
        pincode.setText(ComminPreference.getPreferencesString(getContext(),"pincode"));



        setTypefaceToInputLayout(inputname,"Fonts/SourceSansPro-Italic.ttf");
        setTypefaceToInputLayout(inputdob,"Fonts/SourceSansPro-Italic.ttf");
        setTypefaceToInputLayout(inputemail,"Fonts/SourceSansPro-Italic.ttf");
        setTypefaceToInputLayout(inputaddress,"Fonts/SourceSansPro-Italic.ttf");
        setTypefaceToInputLayout(inputpincode,"Fonts/SourceSansPro-Italic.ttf");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Profilesetting();
                    //DisplayAlert();



            }
        });


        return view;
    }

      private void setTypefaceToInputLayout(TextInputLayout inputLayout, String typeFace){

        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), typeFace);

        inputLayout.getEditText().setTypeface(tf);
        try {
            // Retrieve the CollapsingTextHelper Field
            final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            collapsingTextHelperField.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
            final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
        } catch (Exception ignored) {
            // Nothing to do
        }
    }

    void Profilesetting()
    {
            final String names=name.getText().toString();
            final String dobs=dob.getText().toString();
            final String emails=email.getText().toString();
            final String addresses=address.getText().toString();
            final String pincodes=pincode.getText().toString();

        progressDialog.setMessage("Updating.....");
            showDialog();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, profileUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Log.d("response", response);
                    Log.d("update",response);
                    ComminPreference.savePreferenceString(getContext(),"user",names);
                    ComminPreference.savePreferenceString(getContext(),"email",emails);
                    ComminPreference.savePreferenceString(getContext(),"dob",dobs);
                    ComminPreference.savePreferenceString(getContext(),"address",addresses);
                    ComminPreference.savePreferenceString(getContext(),"pincode",pincodes);
                    Intent intent=new Intent(getContext(),HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"Update error " + error.getMessage());
                    Toast.makeText(getContext(),"something went wrong", Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }){

                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("mobile",ComminPreference.getPreferencesString(getContext(),"mobile"));
                    params.put("name",names);
                    params.put("dob",dobs);
                    params.put("email",emails);
                    params.put("address",addresses);
                    params.put("pincode",pincodes);
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


    private void DisplayAlert()
    {

        new AlertDialog.Builder(getContext())
                .setTitle("Message Sent")
                .setMessage("Your Profile has been updated Successfully")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                      dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
                dialog.dismiss();
            }
        }).show();

    }


        }



