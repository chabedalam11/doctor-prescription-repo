package com.prescription.doctorprescription.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.WelcomeActivity;
import com.prescription.doctorprescription.activity.signup.SignupActivity;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.DoctorLoginCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DoctorLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    PrescriptionMemories memory;
    final String TAG = "LoginActivity";
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    //Ui component
    Button btnLogin;
    EditText edtUserId,edtPass;
    TextView link_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        initialize();
    }

    private void initialize() {
        context = LoginActivity.this;
        memory = new PrescriptionMemories(getApplicationContext());
        //Init Ui component
        edtUserId=(EditText)findViewById(R.id.edtUserId);
        edtPass=(EditText)findViewById(R.id.edtPass);

        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        link_signup = (TextView) findViewById(R.id.link_signup);


        link_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intentSignUp = new Intent(context, SignupActivity.class);
                startActivity(intentSignUp);
            }
        });

        //intent parameter that pass from signUp Activity
        String userName = getIntent().getStringExtra("userName");
        String pass = getIntent().getStringExtra("password");
        if (userName !=null && pass != null){
            if(PrescriptionUtils.isInternetConnected(context)){
                getDoctorLoginInfo(userName,pass);
            }else {
                Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
            }
        }
    }


    //checking login in information
    private void getDoctorLoginInfo(final String email,String password) {
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Log.d(TAG, "doc email and pass :: " + email+" "+password);
        Call<DoctorLoginCollection> getInfo = prescriptionApi.getDoctorLoginInfo(email, password);
        getInfo.enqueue(new Callback<DoctorLoginCollection>() {
            @Override
            public void onResponse(Call<DoctorLoginCollection> call, Response<DoctorLoginCollection> response) {
                try {
                    List<DoctorLogin> info = response.body().data;
                    Log.d(TAG, "doctorInfo :: " + info);
                    if (info.size() > 0) {
                       //save user information in memory
                        memory.putPref(memory.KEY_DOC_ID, info.get(0).getT_doc_id());
                        memory.putPref(memory.KEY_DOC_NAME, info.get(0).getT_doc_first_name());
                        memory.putPref(memory.KEY_DOC_PHONE1, info.get(0).getT_doc_phone1());
                        memory.putPref(memory.KEY_DOC_PHONE2, info.get(0).getT_doc_phone2());
                        memory.putPref(memory.KEY_DOC_EMAIL, info.get(0).getT_doc_email());

                        //now go to welcome activity
                        Intent welcomeIntent = new Intent(context, WelcomeActivity.class);
                        welcomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(welcomeIntent);
                    }else {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                }catch (Exception e){
                    Log.d(TAG,"list is null");
                    e.printStackTrace();
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                    AlartFactory.showAPInotResponseWarn(context);
                }
            }

            @Override
            public void onFailure(Call<DoctorLoginCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
    }



    //<<<<<<<<<<<<<<<<<< set action  for Login button >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLogin:
                String email =edtUserId.getText().toString();
                String password =edtPass.getText().toString();
                if(PrescriptionUtils.isInternetConnected(context)){
                    getDoctorLoginInfo(email,password);
                }else {
                    Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                    AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
                }

                break;
        }
    }

}
