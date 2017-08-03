package com.prescription.doctorprescription.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.login.LoginActivity;

public class SplashActivity extends Activity implements View.OnClickListener {

    Context context;
    final String TAG = "SplashActivity";

    ImageView imgIV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();

    }

    private void initialize() {
        context = SplashActivity.this;
        imgIV1 = (ImageView) findViewById(R.id.imgIV1);
        imgIV1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgIV1:
                Intent friendsListActivityIntent = new Intent(context, LoginActivity.class);
                startActivity(friendsListActivityIntent);
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SplashActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
