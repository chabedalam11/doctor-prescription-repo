package com.prescription.doctorprescription.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.SplashActivity;
import com.prescription.doctorprescription.signup.SignupActivity;
import com.prescription.doctorprescription.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    Context context;
    boolean hardwareBackControll;

    EditText edtUserId, edtPass;
    Button btnLogin;
    TextView link_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }


    private void initialize() {
        context = LoginActivity.this;
        edtUserId = (EditText) findViewById(R.id.edtUserId);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        link_signup = (TextView) findViewById(R.id.link_signup);


        link_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intentSignUp = new Intent(context, SignupActivity.class);
                startActivityForResult(intentSignUp, REQUEST_SIGNUP);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (hardwareBackControll) {
            super.onBackPressed();
            return;
        }
        this.hardwareBackControll = true;
        Utils.backToPrevious(context, new SplashActivity());
    }

}
