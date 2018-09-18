package com.example.admin.doers.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.doers.CommonClass.CommonClass;
import com.example.admin.doers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTP extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView mTitle;
    EditText editText;
    ImageView imageView;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        toolbar=findViewById(R.id.toolotp);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        editText=findViewById(R.id.editvarify);
        imageView=findViewById(R.id.check);
        mAuth = FirebaseAuth.getInstance();

        imageView.setOnClickListener(this);

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                mVerificationInProgress = false;
                Toast.makeText(OTP.this,"Verification Complete",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(OTP.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(OTP.this,"InValid Phone Number",Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Toast.makeText(OTP.this,"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;
                System.out.println("token"+forceResendingToken);
            }
        };

       }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(OTP.this,HomeActivity.class));
                            Toast.makeText(OTP.this,"Verification Done",Toast.LENGTH_SHORT).show();
                            // ...
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            /*if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTP.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {

        Validateotp();


    }


    void Validateotp()
    {

        if(editText.getText().toString().trim().equalsIgnoreCase(""))
        {
            CommonClass.showToast(getApplicationContext(),"Please Enter One time password");

            editText.requestFocus();

        }
        else if(editText.getText().toString().trim().length()<6){

            CommonClass.showToast(getApplicationContext(), "Otp must be of 6 digits");
            editText.requestFocus();
        }


        else {

            startActivity(new Intent(OTP.this,HomeActivity.class));

        }

    }


}
