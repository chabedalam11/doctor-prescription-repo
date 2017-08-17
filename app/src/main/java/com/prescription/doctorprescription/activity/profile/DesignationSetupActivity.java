package com.prescription.doctorprescription.activity.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DesignationInfo;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;
import com.prescription.doctorprescription.webService.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DesignationSetupActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "DesignationSetupActivity";
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    PrescriptionMemories memory;
    String doctor_id;

    Context context;
    boolean hardwareBackControll;

    TextView tvDesignationName;
    TextView tvSpecialization;
    TextView tvSaveAndUpdate;
    LinearLayout linLaySaveDesignation;
    String designationName, specialization;

    //intent pass value
    DesignationInfo designationInfo;
    String t_desig_id;

    //check update or save
    String checkUpOrSave ="save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designation_setup);
        setTitle("Designation Setup");
        initialize();
    }


    private void initialize() {
        context = DesignationSetupActivity.this;
        memory = new PrescriptionMemories(getApplicationContext());
        doctor_id = memory.getPref(memory.KEY_DOC_ID);


        tvDesignationName = (TextView) findViewById(R.id.tvDesignationName);
        tvSpecialization = (TextView) findViewById(R.id.tvSpecialization);
        tvSaveAndUpdate = (TextView) findViewById(R.id.tvSaveAndUpdate);
        linLaySaveDesignation = (LinearLayout) findViewById(R.id.linLaySaveDesignation);
        linLaySaveDesignation.setOnClickListener(this);


        //get intent pass value
        designationInfo=(DesignationInfo) getIntent().getSerializableExtra("designationInfo");
        if(designationInfo !=null){
            //Log.d(TAG,docClinicInfo.getT_clinic_other());
            t_desig_id=designationInfo.getT_desig_id();
            tvDesignationName.setText(designationInfo.getT_desig_name());
            tvSpecialization.setText(designationInfo.getOther());
            checkUpOrSave="update";
            tvSaveAndUpdate.setText("Update Designation");
        }
    }

    // Save Designation Infornation By Webservice
    private void setSaveDesignationInformation(){
        designationName=tvDesignationName.getText().toString();
        specialization=tvSpecialization.getText().toString();
        Log.d(TAG,"PRINTED DESIGNATION INFO >>> " + designationName + " " + specialization);

        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.insertDocDesignation(doctor_id, designationName, specialization);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "DESIGNATION INFO >>: " + info);
                    if (info.get(0).getMsgString().equals("1")){
                        Log.d(TAG,"It's Working!!!");
                        Intent intent = new Intent(context, DoctorProfileActivity.class);
                        intent.putExtra("tab","2");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Toast.makeText(context, "Data Not Save!!!", Toast.LENGTH_SHORT).show();
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

    // update Designation Infornation By Webservice
    private void updateDesignationInformation(){
        designationName=tvDesignationName.getText().toString();
        specialization=tvSpecialization.getText().toString();
        Log.d(TAG,"PRINTED DESIGNATION INFO >>> " + t_desig_id + " " + doctor_id);

        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.updateDocDesignation(t_desig_id,doctor_id,designationName,specialization);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "DESIGNATION INFO >>: " + info);
                    if (info.get(0).getMsgString().equals("1")){
                        Toast.makeText(context, "information update", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "update not complete", Toast.LENGTH_SHORT).show();
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


    //<<<<<<<<<<<<<<<<<< set action for Save Designation button >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.linLaySaveDesignation:
                //save or update designation information
                if(PrescriptionUtils.isInternetConnected(context)){
                    if(checkUpOrSave.equals("update")){
                        //go for update designation information
                        updateDesignationInformation();
                    }else {
                        //go for save designation information
                        setSaveDesignationInformation();
                    }
                }else {
                    //Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                    AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
                }
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
        Intent previousActivityIntent = new Intent(context, DoctorProfileActivity.class);
        previousActivityIntent.putExtra("tab","2");
        previousActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(previousActivityIntent);
        ((Activity) context).finish();
    }
}
