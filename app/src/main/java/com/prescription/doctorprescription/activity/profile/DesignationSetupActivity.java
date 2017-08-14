package com.prescription.doctorprescription.activity.profile;

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
    LinearLayout linLaySaveDesignation;
    String designationName, specialization;

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
        linLaySaveDesignation = (LinearLayout) findViewById(R.id.linLaySaveDesignation);
        linLaySaveDesignation.setOnClickListener(this);
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


    //<<<<<<<<<<<<<<<<<< set action for Save Designation button >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.linLaySaveDesignation:
                setSaveDesignationInformation();

               break;
        }

    }
}
