package com.prescription.doctorprescription.activity.prescription;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.login.LoginActivity;
import com.prescription.doctorprescription.activity.patient.PatientHistoryActivity;
import com.prescription.doctorprescription.adapter.tab.CreatePrescriptionAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.DrugMaster;
import com.prescription.doctorprescription.webService.model.Message;
import com.prescription.doctorprescription.webService.model.Patient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionSetupActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "PrescriptionSetupActivity";
    Context context;
    PrescriptionMemories memory;
    boolean hardwareBackControll;
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    String doctor_id;

    //tab component
    ViewPager createPrescriptionPager;
    PagerSlidingTabStrip tabForCreatePrescriptionAdapter;
    CreatePrescriptionAdapter createPrescriptionAdapter;
    int currentPage;

    //Ui component
    LinearLayout lLayoutSavePrescription;
    LinearLayout lLayoutUpdatePrescription;

    //variable for extra
    String actionType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_setup);
        intialize();
    }


    private  void intialize(){
        context = PrescriptionSetupActivity.this;
        memory = new PrescriptionMemories(getApplicationContext());
        doctor_id =memory.getPref(memory.KEY_DOC_ID);

        //get intent value
        actionType=getIntent().getStringExtra("actionType");

        //tab component init
        tabForCreatePrescriptionAdapter = (PagerSlidingTabStrip) findViewById(R.id.tabForCreatePrescriptionAdapter);
        createPrescriptionPager = (ViewPager) findViewById(R.id.createPrescriptionPager);
        //for stop Fragments get loaded every time i switch tab
        createPrescriptionPager.setOffscreenPageLimit(4);
        createPrescriptionAdapter = new CreatePrescriptionAdapter(getSupportFragmentManager());
        createPrescriptionPager.setAdapter(createPrescriptionAdapter);
        tabForCreatePrescriptionAdapter.setViewPager(createPrescriptionPager);

        //ui component init
        lLayoutSavePrescription =(LinearLayout) findViewById(R.id.lLayoutSavePrescription);
        lLayoutSavePrescription.setOnClickListener(this);
        lLayoutUpdatePrescription =(LinearLayout) findViewById(R.id.lLayoutUpdatePrescription);
        lLayoutUpdatePrescription.setOnClickListener(this);

        if(actionType.equals("create")){
            new PrescriptionInfo().clearField();
            lLayoutUpdatePrescription.setVisibility(View.GONE);
        }else if(actionType.equals("update")){
            lLayoutSavePrescription.setVisibility(View.GONE);
        }



    }

    @Override
    public void onClick(final View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lLayoutSavePrescription:
                 new AlertDialog.Builder(this)
                        .setTitle("Save Prescription")
                        .setMessage("Are you sure all information are correct?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                //Log.d(TAG,PrescriptionInfo.patientInfo.getT_pat_f_name());
                                //Log.d(TAG,PrescriptionInfo.patientInfo.getT_pat_age());
                               // Log.d(TAG,PrescriptionInfo.patientInfo.getT_pat_sex());

                                Log.d(TAG,PrescriptionInfo.chiefcomplaint);

                                Log.d(TAG,PrescriptionInfo.patientPalse);
                                Log.d(TAG,PrescriptionInfo.patientBloodPressure);
                                Log.d(TAG,PrescriptionInfo.patientTemprature);
                                Log.d(TAG,PrescriptionInfo.patientResp);
                                Log.d(TAG,PrescriptionInfo.patientExmineOther);

                                Log.d(TAG,"======== investigation =============");
                                String analysisCode="";
                                ArrayList<Analysis> analysisesList = PrescriptionInfo.analysisesList;
                                if(analysisesList != null){
                                    int count =0;
                                    for (Analysis analysis: analysisesList){
                                        Log.d(TAG,analysis.getT_analysis_name());
                                        if (count == 0){
                                            analysisCode+=analysis.getT_analysis_code();
                                            count++;
                                        }else {
                                            analysisCode+=","+analysis.getT_analysis_code();
                                        }

                                    }

                                }

                                Log.d(TAG,"======== Drgmaster =============");
                                String um="";
                                String medName="";
                                String strength="";
                                String doseTime="";
                                String duration="";
                                String hints="";

                                ArrayList<DrugMaster> drugMasterList = PrescriptionInfo.drugMasterList;
                                if(drugMasterList != null){
                                    for (DrugMaster drugMaster: drugMasterList){
                                        Log.d(TAG,drugMaster.getT_medicine_name());
                                        um+=drugMaster.getT_um()+"|";
                                        medName+=drugMaster.getT_medicine_name()+"|";
                                        strength+=drugMaster.getT_strength()+"|";
                                        doseTime+=drugMaster.getT_dose_time()+"|";
                                        duration+=drugMaster.getT_duration()+"|";
                                        hints+=drugMaster.getT_advice()+"|";
                                    }
                                }

                                Log.d(TAG,"======== advice and next visit =============");
                                Log.d(TAG,PrescriptionInfo.advice);
                                Log.d(TAG,PrescriptionInfo.nextVisit);

                                savePrescriptionInformation(
                                        PrescriptionInfo.patientInfo.getT_pat_id(),
                                        PrescriptionInfo.chiefcomplaint,
                                        PrescriptionInfo.patientPalse,
                                        PrescriptionInfo.patientBloodPressure,
                                        PrescriptionInfo.patientTemprature,
                                        PrescriptionInfo.patientResp,
                                        PrescriptionInfo.patientExmineOther,
                                        analysisCode,
                                        PrescriptionInfo.advice,
                                        PrescriptionInfo.nextVisit,
                                        um,medName,
                                        strength,doseTime,
                                        duration,hints
                                );
                                //Toast.makeText(context, PrescriptionInfo.patientName, Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

                break;
            case R.id.lLayoutUpdatePrescription:
                new AlertDialog.Builder(this)
                        .setTitle("Update Prescription")
                        .setMessage("Are you sure you want to update with new info?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                //update information
                                Log.d(TAG,PrescriptionInfo.t_pres_id);
                                Log.d(TAG,PrescriptionInfo.chiefcomplaint);
                                Log.d(TAG,PrescriptionInfo.patientPalse);
                                Log.d(TAG,PrescriptionInfo.patientBloodPressure);
                                Log.d(TAG,PrescriptionInfo.patientTemprature);
                                Log.d(TAG,PrescriptionInfo.patientResp);

                                Log.d(TAG,"======== investigation =============");
                                String analysisCode="";
                                ArrayList<Analysis> analysisesList = PrescriptionInfo.analysisesList;
                                if(analysisesList != null){
                                    int count =0;
                                    for (Analysis analysis: analysisesList){
                                        Log.d(TAG,analysis.getT_analysis_name());
                                        if (count == 0){
                                            analysisCode+=analysis.getT_analysis_code();
                                            count++;
                                        }else {
                                            analysisCode+=","+analysis.getT_analysis_code();
                                        }
                                    }
                                }
                                Log.d(TAG,analysisCode);

                                Log.d(TAG,"======== Drgmaster =============");
                                String um="";
                                String medName="";
                                String strength="";
                                String doseTime="";
                                String duration="";
                                String hints="";

                                ArrayList<DrugMaster> drugMasterList = PrescriptionInfo.drugMasterList;
                                if(drugMasterList != null){
                                    for (DrugMaster drugMaster: drugMasterList){
                                        //Log.d(TAG,drugMaster.getT_medicine_name());
                                        um+=drugMaster.getT_um()+"|";
                                        medName+=drugMaster.getT_medicine_name()+"|";
                                        strength+=drugMaster.getT_strength()+"|";
                                        doseTime+=drugMaster.getT_dose_time()+"|";
                                        duration+=drugMaster.getT_duration()+"|";
                                        hints+=drugMaster.getT_advice()+"|";
                                    }
                                }
                                Log.d(TAG,um);
                                Log.d(TAG,medName);
                                Log.d(TAG,strength);
                                Log.d(TAG,doseTime);
                                Log.d(TAG,duration);
                                Log.d(TAG,hints);

                                Log.d(TAG,"======== advice and next visit =============");
                                Log.d(TAG,PrescriptionInfo.advice);
                                Log.d(TAG,PrescriptionInfo.nextVisit);

                                updatePrescription(
                                        PrescriptionInfo.t_pres_id,
                                        PrescriptionInfo.chiefcomplaint,
                                        PrescriptionInfo.patientPalse,
                                        PrescriptionInfo.patientBloodPressure,
                                        PrescriptionInfo.patientTemprature,
                                        PrescriptionInfo.patientResp,
                                        analysisCode,
                                        PrescriptionInfo.advice,
                                        PrescriptionInfo.nextVisit,
                                        um,medName,strength,doseTime,duration,hints
                                );

                            }
                        }).create().show();
                break;
        }
    }


    //insert prescription in database
    private void savePrescriptionInformation(
            String patId,String cComplaint,String palse,String bp,
            String temp,String resp,String other,String analysisCode,
            String advice,String nextVisit,
            String um,String medName,
            String strength,String doseTime,
            String duration,String hints
            ){

        Log.d(TAG, "param : "+patId+"\t"+ cComplaint+"\t"+ palse+"\t"+ bp);
        //Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.insertPrescription(doctor_id,patId,PrescriptionUtils.getCurrentDate(),
                                                                            cComplaint,palse,bp,temp,resp,other,analysisCode,
                                                                            advice,nextVisit,
                                                                            um,medName,
                                                                            strength,doseTime,
                                                                            duration,hints);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "clinicInfo :: " + info);
                    if (info.get(0).getMsgString().equals("1")){
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();
                        Intent Intent = new Intent(context, PatientHistoryActivity.class);
                        Intent.putExtra("patient",PrescriptionInfo.patientInfo);
                        Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Intent);

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
    //up-date prescription in database
    private void updatePrescription(String t_pres_id,
                                    String  t_pres_chief_complaints,
                                    String  t_pres_pulse,
                                    String  t_pres_bp,
                                    String  t_pres_temp,
                                    String  t_pres_resp,
                                    String  t_analysis_code,
                                    String  t_pres_advice,
                                    String  t_pres_next_visit,
                                    String  t_pres_um,
                                    String  t_pres_med_name,
                                    String  t_pres_strength,
                                    String  t_pres_dose_time,
                                    String  t_pres_duration,
                                    String  t_pres_hints
                                ){
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.updatePrescription(t_pres_id,t_pres_chief_complaints,
                                        t_pres_pulse,t_pres_bp,t_pres_temp,t_pres_resp,
                                        t_analysis_code,t_pres_advice,t_pres_next_visit,
                                        t_pres_um,t_pres_med_name,
                                        t_pres_strength,t_pres_dose_time,t_pres_duration,t_pres_hints);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "updatePrescription :: " + info);
                    if (info.size()>0){

                        if (info.get(0).getMsgString().equals("1")){
                            //Hide Dialog
                            PrescriptionUtils.hideProgressDialog();
                            Toast.makeText(context, "prescription update", Toast.LENGTH_SHORT).show();
                            Intent Intent = new Intent(context, PatientHistoryActivity.class);
                            Intent.putExtra("patient",PrescriptionInfo.patientInfo);
                            Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(Intent);
                        }else {
                            //Hide Dialog
                            PrescriptionUtils.hideProgressDialog();
                            Toast.makeText(context, "not able to  update", Toast.LENGTH_SHORT).show();
                        }

                    }

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

}
