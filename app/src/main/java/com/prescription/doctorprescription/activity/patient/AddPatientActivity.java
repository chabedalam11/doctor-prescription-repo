package com.prescription.doctorprescription.activity.patient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.WelcomeActivity;
import com.prescription.doctorprescription.activity.profile.DoctorProfileActivity;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;
import com.prescription.doctorprescription.webService.model.Message;
import com.prescription.doctorprescription.webService.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPatientActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "ClinicSetupActivity";
    Context context;
    PrescriptionMemories memory;
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    String doctor_id;
    boolean hardwareBackControll;

    //UI component
    EditText edtPatientName;
    EditText edtAge;
    RadioGroup rbGsex;
    RadioGroup rbMaritalStatus;
    EditText edtMobile;
    EditText edtEmail;
    EditText edtFatherName;
    EditText edtAddress;
    LinearLayout lLayoutsavePatient;


    String patientName;
    String age;
    String sex;
    String marital;
    String mobile;
    String email;
    String fatherName;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        setTitle("ADD Patient");
        initialize();
    }

    private void initialize() {
        context = AddPatientActivity.this;
        memory = new PrescriptionMemories(context);
        doctor_id =memory.getPref(memory.KEY_DOC_ID);


        //init UI component
        edtPatientName =(EditText) findViewById(R.id.edtPatientName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        rbGsex = (RadioGroup) findViewById(R.id.rbGsex);
        rbGsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    sex=rb.getText().toString();

                }
            }
        });
        rbMaritalStatus = (RadioGroup) findViewById(R.id.rbMaritalStatus);
        rbMaritalStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    marital=rb.getText().toString();

                }
            }
        });
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtFatherName = (EditText) findViewById(R.id.edtFatherName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);

        lLayoutsavePatient = (LinearLayout) findViewById(R.id.lLayoutsavePatient);
        lLayoutsavePatient.setOnClickListener(this);
    }

    private void savePatientInformation(){

        patientName=edtPatientName.getText().toString();
        age=edtAge.getText().toString();
        mobile=edtMobile.getText().toString();
        email=edtEmail.getText().toString();
        fatherName=edtFatherName.getText().toString();
        address=edtAddress.getText().toString();


        Log.d(TAG, patientName+"\t"+ age+"\t"+ sex+"\t"+ marital);

        if (patientName.equals("") || age.equals("") || sex == null || marital == null || mobile.equals("")){
            Toast.makeText(context, "You must fill required field", Toast.LENGTH_SHORT).show();
            return;
        }

        //Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();


       //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.insertPatient(doctor_id,patientName,age,sex,address,mobile,email,marital,PrescriptionUtils.getCurrentDate(),fatherName);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "clinicInfo :: " + info);
                    if (!info.get(0).getMsgString().equals("0")){
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();

                        Intent intent = new Intent(context,PatientHistoryActivity.class);
                        Patient patient = new Patient();
                        patient.setT_doc_id(doctor_id);
                        patient.setT_pat_id(info.get(0).getMsgString());
                        patient.setT_pat_name(patientName);
                        patient.setT_pat_age(age);
                        patient.setT_pat_sex(sex);
                        patient.setT_pat_address(address);
                        patient.setT_pat_mobile(mobile);
                        patient.setT_pat_email(email);
                        patient.setT_pat_marital(marital);
                        patient.setT_pat_entry_date(PrescriptionUtils.getCurrentDate());
                        patient.setT_pat_f_name(fatherName);
                        intent.putExtra("patient",patient);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        //Toast.makeText(context, "save successful", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "information not save please try again after some times", Toast.LENGTH_SHORT).show();
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






    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLayoutsavePatient:
                savePatientInformation();
                break;

        }
    }


    //<<<<<<<<<<<<<<<<<<  action  define for back press >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onBackPressed() {
        if (hardwareBackControll) {
            super.onBackPressed();
            return;
        }
        this.hardwareBackControll = true;
        PrescriptionUtils.backToPrevious(context, new WelcomeActivity());
    }


}
