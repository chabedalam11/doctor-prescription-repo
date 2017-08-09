package com.prescription.doctorprescription.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.login.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    final String TAG = "SignupActivity";
    private static final int REQUEST_LOGIN = 0;
    Context context;
    boolean hardwareBackControll;

    EditText edtFullName, edtDOB, edtNationality, edtPhone1, edtPhone2, edtEmail, edtAddress, edtPassword, edtConfirmpassword;
    RadioGroup radioGrpGender;
    Button btnSignUp;
    TextView link_login;

    String fullName, gender, dob, nationality, phone_1, phone_2, email, address, pass, conPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize();
    }


    private void initialize() {
        context = SignupActivity.this;

        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtDOB = (EditText) findViewById(R.id.edtDOB);

        radioGrpGender = (RadioGroup) findViewById(R.id.radioGrpGender);
        radioGrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    gender = rb.getText().toString();
                    if(gender.equals("Male")){
                        gender = "1";
                    }else if(gender.equals("Female")){
                        gender = "2";
                    }
                }
            }
        });

        edtNationality = (EditText) findViewById(R.id.edtNationality);
        edtPhone1 = (EditText) findViewById(R.id.edtPhone1);
        edtPhone2 = (EditText) findViewById(R.id.edtPhone2);
        edtPhone2 = (EditText) findViewById(R.id.edtPhone2);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmpassword = (EditText) findViewById(R.id.edtConfirmpassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        link_login = (TextView) findViewById(R.id.link_login);


        link_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intentLogIn = new Intent(context, LoginActivity.class);
                startActivityForResult(intentLogIn, REQUEST_LOGIN);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            boolean validFullName, validDOB, validPhone_1, validEmail, validAddress, validPass, validConPass;
            @Override
            public void onClick(View arg0) {

                fullName = edtFullName.getText().toString();
                //Log.d(TAG, String.valueOf(isValidFullName(fullName)));
                if (!isValidFullName(fullName)) {
                    edtFullName.setError("Enter Full Name");
                }else{
                    validFullName = true;
                }


                dob = edtDOB.getText().toString();
                if (!isValidDOB(dob)) {
                    edtDOB.setError("Enter Birth Date");
                }else{
                    validDOB = true;
                }

                phone_1 = edtPhone1.getText().toString();
                if (!isValidPhone1(phone_1)) {
                    edtPhone1.setError("Enter Phone Number");
                }else{
                    validPhone_1 = true;
                }

                address = edtAddress.getText().toString();
                if (!isValidAddress(address)) {
                    edtAddress.setError("Enter Address");
                }else{
                    validAddress = true;
                }

                email = edtEmail.getText().toString();
                if (!isValidEmail(email)) {
                    edtEmail.setError("Invalid Email");
                }else{
                    validEmail = true;
                }

                pass = edtPassword.getText().toString();
                if (!isValidPassword(pass)) {
                    edtPassword.setError("Invalid Password");
                }else{
                    validPass = true;
                }

                conPass = edtConfirmpassword.getText().toString();
                if (!isValidPassword(conPass)) {
                    edtConfirmpassword.setError("Re-Enter Password");
                }else{
                    validConPass = true;
                }

                if(validFullName && validDOB && validPhone_1 && validAddress && validEmail && validPass && validConPass){
                /*if(validFullName){*/
                Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    // validating Fullname
    private boolean isValidFullName(String fullName) {

        if (fullName.equals("")) {
            return false;
        }
        return true;
    }

    // validating DOB
    private boolean isValidDOB(String dob) {
        if (dob.equals("")) {
            return true;
        }
        return false;
    }

    // validating Phone1
    private boolean isValidPhone1(String phone_1) {
        if (phone_1.equals("")) {
            return true;
        }
        return false;
    }

    // validating Address
    private boolean isValidAddress(String address) {
        if (address.equals("")) {
            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass.equals("")) {
            return true;
        }
        return false;
    }

    // validating Confirm password with retype password
    private boolean isValidConPassword(String conPass) {
        if (conPass.equals("") && conPass.length() > 6 && conPass.equals(pass)) {
            return true;
        }
        return false;
    }
}
