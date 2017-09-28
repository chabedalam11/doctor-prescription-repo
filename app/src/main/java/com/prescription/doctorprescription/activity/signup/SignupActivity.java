package com.prescription.doctorprescription.activity.signup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.login.LoginActivity;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "SignupActivity";
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    Context context;
    boolean hardwareBackControll;

    EditText edtFullName, edtDOB, edtPhone1, edtPhone2, edtEmail, edtAddress, edtPassword, edtConfirmpassword;
    RadioGroup radioGrpGender;
    Button btnSignUp;
    TextView link_login;
    TextView tvRadioMsg;

    String fullName, dob,  phone_1, phone_2, email, address, pass, conPass;
    boolean validFullName, validDOB, validGender, validPhone_1, validEmail, validAddress, validPass, validConPass;
    String gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Doctor's Signup");
        initialize();
    }

    private void initialize() {
        context = SignupActivity.this;

        edtFullName = (EditText) findViewById(R.id.edtFullName);
        radioGrpGender = (RadioGroup) findViewById(R.id.radioGrpGender);
        radioGrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    gender = rb.getText().toString();
                    tvRadioMsg.setVisibility(View.GONE);
                }
            }
        });

        edtPhone1 = (EditText) findViewById(R.id.edtPhone1);
        edtPhone2 = (EditText) findViewById(R.id.edtPhone2);
        edtPhone2 = (EditText) findViewById(R.id.edtPhone2);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmpassword = (EditText) findViewById(R.id.edtConfirmpassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        link_login = (TextView) findViewById(R.id.link_login);
        tvRadioMsg = (TextView) findViewById(R.id.tvRadioMsg);
        tvRadioMsg.setVisibility(View.GONE);

        link_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intentLogIn = new Intent(context, LoginActivity.class);
                startActivity(intentLogIn);
            }
        });

        setCalenderInDoBField();
    }

    //set date picker in dob EditText
    private void setCalenderInDoBField(){
        //date packer
        final Calendar myCalendar = Calendar.getInstance();
        edtDOB = (EditText) findViewById(R.id.edtDOB);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                edtDOB.setText(sdf.format(myCalendar.getTime()));
            }
        };

        edtDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //before save first validate information then save
    private void validateSignUpInformation(){
        phone_2=edtPhone2.getText().toString();
        fullName = edtFullName.getText().toString();
        //Log.d(TAG, String.valueOf(isValidFullName(fullName)));
        if (isEmpty(fullName)) {
            edtFullName.setError("Enter Full Name");
        }else{
            validFullName = true;
        }

        dob = edtDOB.getText().toString();
        if (isEmpty(dob)) {
            edtDOB.setError("Enter Birth Date");
        }else{
            validDOB = true;
        }

        //validation gender
        if (isEmpty(gender)) {
            tvRadioMsg.setVisibility(View.VISIBLE);
        }else{
            validGender = true;
            tvRadioMsg.setVisibility(View.GONE);
        }


        phone_1 = edtPhone1.getText().toString();
        if (isEmpty(phone_1)) {
            edtPhone1.setError("Enter Phone Number");
        }else{
            validPhone_1 = true;
        }



        email = edtEmail.getText().toString();
        if (!isValidEmail(email)) {
            edtEmail.setError("Invalid Email");
        }else{
            validEmail = true;
        }

        address = edtAddress.getText().toString();
        if (isEmpty(address)) {
            edtAddress.setError("Enter Address");
        }else{
            validAddress = true;
        }

        pass = edtPassword.getText().toString();
        if (isEmpty(pass)) {
            edtPassword.setError("Enter Password");
        }else{
            validPass = true;
        }

        conPass = edtConfirmpassword.getText().toString();
        if (!isMatchPassword(pass,conPass)) {
            edtConfirmpassword.setError("Password Not Match");
        }else{
            validConPass = true;
        }

        //if(validFullName && validDOB && validPhone_1 && validAddress && validEmail && validPass && validConPass){
        if(validFullName&&validDOB&&validGender&&validPhone_1&&validEmail&&validAddress&&validPass&&validConPass){
            //pass SignUp  Information for save Database
            saveSignUpInformation();
        }

    }


    //checking login in information
    private void saveSignUpInformation() {
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.insertSignUpInfo(fullName,"","",dob,gender,"",phone_1,phone_2,"",conPass,email,address);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "doctorInfo :: " + info);
                    if (info.get(0).getMsgString().equals("1")){
                        Log.d(TAG,"Everything is ok");
                        Intent loginIntent = new Intent(context, LoginActivity.class);
                        loginIntent.putExtra("userName",email);
                        loginIntent.putExtra("password",conPass);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                    }else if (info.get(0).getMsgString().equals("101")){
                        edtEmail.setError("Email already use !!");
                    }else {
                        Toast.makeText(context, "SignUp not complete please try again after some times", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<MessegeCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
    }


    //Empty validation
    private boolean isEmpty(String fullName) {
        if (fullName.equals("")) {
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



    // Validate match two password
    private boolean isMatchPassword(String pass,String conPass) {
        if (pass.equals(conPass)) {
            return true;
        }
        return false;
    }


    //<<<<<<<<<<<<<<<<<< set action on  button >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSignUp:
                if(PrescriptionUtils.isInternetConnected(context)){
                    //go for save signUP information
                    validateSignUpInformation();
                }else {
                    Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                    AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
                }
                break;
        }
    }
}
