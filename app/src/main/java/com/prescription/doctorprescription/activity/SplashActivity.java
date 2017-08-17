package com.prescription.doctorprescription.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.login.LoginActivity;
import com.prescription.doctorprescription.utils.PrescriptionMemories;

public class SplashActivity extends Activity implements View.OnClickListener {

    Context context;
    final String TAG = "SplashActivity";
    PrescriptionMemories memory;

    ImageView imgIV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();

    }

    private void initialize() {
        context = SplashActivity.this;
        memory = new PrescriptionMemories(getApplicationContext());

        imgIV1 = (ImageView) findViewById(R.id.imgIV1);
        //imgIV1.setOnClickListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"doc id : "+memory.getPref(memory.KEY_DOC_ID));
                if (memory.getPref(memory.KEY_DOC_ID) != null){
                    Intent intent = new Intent(context, WelcomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                    finish();
                }else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgIV1:
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
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
