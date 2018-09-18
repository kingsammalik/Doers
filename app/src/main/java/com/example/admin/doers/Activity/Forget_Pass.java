package com.example.admin.doers.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.doers.CommonClass.CommonClass;
import com.example.admin.doers.Fragments.Login_Fragment;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Forget_Pass extends AppCompatActivity {

    EditText email;
    Button btnForget;
    private static String forgetUrl="http://teamdoers.in/admin/webservices/forgot_password.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__pass);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forget_Pass.this,MainPage.class));
                finish();
            }
        });

        email=findViewById(R.id.email);
        btnForget=findViewById(R.id.forget);


        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             validateEmail();

                CommonClass.showToast(getApplicationContext(),"Email Has Been Sent to Regisetred Email ID");
                Intent intent = new Intent(Forget_Pass.this, MainPage.class);
                startActivity(intent);


            }
        });


    }

     void validateEmail(){

          if (email.getText().toString().trim().equalsIgnoreCase("")) {
             CommonClass.showToast(getApplicationContext(), "Please enter Email Address.");
             email.requestFocus();
         } else if (email.getText().toString().trim().matches("^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")) {
             CommonClass.showToast(getApplicationContext(), "Please enter valid Email Address.");
             email.requestFocus();
         }
        else {

              ForgetPassword();

          }

     }


    void ForgetPassword()

    {
        final String emails=email.getText().toString();
        progressDialog.setMessage("Sending Mail.....");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, forgetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.d("response", response);
                Log.d("login",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Vooly" + error.getMessage());
                Toast.makeText(getApplicationContext(),"Email Not Registered in Database ", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("email",emails);
                return params;
            }
        };

        MySingelton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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



