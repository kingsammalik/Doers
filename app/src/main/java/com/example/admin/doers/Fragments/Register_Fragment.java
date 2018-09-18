package com.example.admin.doers.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.admin.doers.Activity.OTP;
import com.example.admin.doers.Model.Login_model;
import com.example.admin.doers.Preference.ComminPreference;
import com.example.admin.doers.CommonClass.CommonClass;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register_Fragment extends Fragment{
    ProgressDialog progressDialog;
    private static final String TAG = "Registering Activity";
    EditText name,mobile,emaill,password;
    Button btnsignup;
    Typeface typeface;
    int mInternetStatus=0;
    private static String registerUrl="http://teamdoers.in/admin/webservices/register.php";
    JSONObject jsonObject=null;
    private static final String TAG1 = "PhoneLogin";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register_fragment, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        name=view.findViewById(R.id.name);
        mobile=view.findViewById(R.id.mobile);
        emaill=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        btnsignup=view.findViewById(R.id.btnregister);
        FirebaseApp.initializeApp(getContext());
        auth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                Toast.makeText(getContext(),"Verification Complete",Toast.LENGTH_SHORT).show();
                //signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(getContext(),"Verification Failed",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(getContext(),"InValid Phone Number",Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(getContext(),"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                System.out.println("token"+token);
            }
        };
      //  RegisterValidation();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RegisterValidation();

            }
        });
        typeface= Typeface.createFromAsset(getActivity().getAssets(),"Fonts/SourceSansPro-Italic.ttf");
        name.setTypeface(typeface);
        mobile.setTypeface(typeface);
        emaill.setTypeface(typeface);
        password.setTypeface(typeface);
        btnsignup.setTypeface(typeface);


        return view;

    }


    void RegisterValidation()
    {

            if(name.getText().toString().trim().equalsIgnoreCase(""))
            {
                CommonClass.showToast(getContext(),"Please Enter Name");

                 name.requestFocus();

            }
            else if (name.getText().toString().trim().matches(".*[^a-z^A-Z ].*")) {
                CommonClass.showToast(getContext(), "Invalid Full name only a-z allowed.");
                name.requestFocus();
            }
            else if (name.getText().toString().trim().length() < 3) {
                CommonClass.showToast(getContext(), "Full name should be atLeast 3 characters.");
                name.requestFocus();
            }
            else if (mobile.getText().toString().trim().equalsIgnoreCase("")) {
                CommonClass.showToast(getContext(), "Please enter Mobile Number.");
                mobile.requestFocus();
            } else if (mobile.getText().toString().trim().length() < 10) {
                CommonClass.showToast(getContext(), "Please enter valid Mobile Number.");
                mobile.requestFocus();
            }


           else if(password.getText().toString().trim().equalsIgnoreCase(""))
            {
                CommonClass.showToast(getContext(), "Please enter Password.");
                password.requestFocus();
            }

            else {

                InsertData();
            }

        }

    void InsertData() {
        final String names=name.getText().toString();
        final String emails=emaill.getText().toString();
        final String mobiles=mobile.getText().toString();
        Log.d("newno",mobiles);
        final String passwords=password.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+mobile.getText().toString(), 60, java.util.concurrent.TimeUnit.SECONDS, getActivity(), mCallbacks);

        progressDialog.setMessage("Registering.....");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                ComminPreference.savePreferenceString(getContext(),"user",names);
                ComminPreference.savePreferenceString(getContext(),"mobile",mobiles);
                ComminPreference.savePreferenceString(getContext(),"email",emails);
                ComminPreference.savePreferenceString(getContext(),"password",passwords);
                ComminPreference.savePreferenceString(getContext(),"fcm",FirebaseInstanceId.getInstance().getToken());

                Intent intent=new Intent(getContext(),OTP.class);
                startActivity(intent);

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
                params.put("fcm", FirebaseInstanceId.getInstance().getToken());
                params.put("name",names);
                params.put("email",emails);
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


