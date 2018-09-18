package com.example.admin.doers.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.doers.Activity.Forget_Pass;
import com.example.admin.doers.Activity.HomeActivity;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.CommonClass.CommonClass;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Login_Fragment extends Fragment {
TextView forgetpassword;
Typeface typeface;
EditText mobileno, password;

    ProgressDialog progressDialog;
    int mInternetStatus=0;
    private static String loginUrl="http://teamdoers.in/admin/webservices/login_new.php";
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.login_fragment, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        forgetpassword=view.findViewById(R.id.forget);
         mobileno=view.findViewById(R.id.mobile);
         password =view.findViewById(R.id.password);
         button=view.findViewById(R.id.btnlogin);
         typeface=Typeface.createFromAsset(getActivity().getAssets(),"Fonts/SourceSansPro-Italic.ttf");
         mobileno.setTypeface(typeface);
         password.setTypeface(typeface);
         button.setTypeface(typeface);

         forgetpassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent = new Intent(getContext(), Forget_Pass.class);
                 startActivity(intent);

             }
         });
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 LoginValidation();


             }
         });

        return view;

    }



    void LoginValidation()
    {
         if (mobileno.getText().toString().trim().equalsIgnoreCase("")) {
            CommonClass.showToast(getContext(), "Please enter Mobile Number.");
            mobileno.requestFocus();
        } else if (mobileno.getText().toString().trim().length() < 10) {
            CommonClass.showToast(getContext(), "Please enter valid Mobile Number.");
            mobileno.requestFocus();
        }


        else if(password.getText().toString().trim().equalsIgnoreCase(""))
        {
            CommonClass.showToast(getContext(), "Please enter Password.");
            password.requestFocus();
        }


        else {

            get_User();
        }

    }

    void get_User()
    {
        final String mobiles=mobileno.getText().toString();
        final String passwords= password.getText().toString();
        progressDialog.setMessage("Loging in.....");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.d("response", response);
                Log.d("login",response);
                ComminPreference.savePreferenceString(getContext(),"mobile",mobiles);
                Intent intent=new Intent(getContext(),HomeActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Login error " + error.getMessage());
                Toast.makeText(getContext(),"Email/Password Does not Match", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile",mobiles);
                params.put("password",passwords);
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
