package com.jxl.studybuddy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mVerifiedButton;
    private Button mResendButton;

    @Override
    //This activity displays a blank page with one button that says "I HAVE VERIFIED MY EMAIL"
    //to make a clear indication to the user that they must verify their email.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAuth = FirebaseAuth.getInstance();
        mVerifiedButton = (Button) findViewById(R.id.button_verifiedEmail);
        mResendButton = (Button) findViewById(R.id.button_resend_verify);
        //When user clicks the button, transition back to LoginActivity to complete verification.
        mVerifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Login to complete verification.", Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(VerifyActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = mAuth.getCurrentUser();
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "Failed to send verification email to " + user.getEmail(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }

}
