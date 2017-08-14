package com.prescription.doctorprescription.activity.profile;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.login.LoginActivity;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;
import com.prescription.doctorprescription.webService.model.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicSetupActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "ClinicSetupActivity";
    Context context;
    PrescriptionMemories memory;
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    String user_id;
    boolean hardwareBackControll;

    //UI component
    EditText edtClinicName;
    EditText edtMobileNo;
    TextView tvAddress;
    TextView tvChamberAddress;
    TextView tvVisitingHour1;
    TextView tvVisitingHour2;
    TextView tvVisitingDay;
    ViewSwitcher timeSwitcher;
    TimePicker from;
    TimePicker to;
    LinearLayout saveClinic;
    TextView tvSaveAndUpdate;

    boolean[] checkItem = {false,false, false, false, false, false, false, false};

    //save value
    String clinicName,mobileNo,address,visitingHour1,visitingHour2,visitingDay;

    //intent pass value
    DocClinicInfo docClinicInfo;

    //alert dialog
    AlertDialog alertDialog;

    //check update or save
    String checkUpOrCheck="save";
    String clinic_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_setup);
        setTitle("Clinic Setup");
        initialize();
    }

    private void initialize() {
        context = ClinicSetupActivity.this;
        memory = new PrescriptionMemories(context);
        user_id=memory.getPref(memory.KEY_DOC_ID);


        //init UI component
        edtClinicName =(EditText) findViewById(R.id.clinicName);
        edtMobileNo = (EditText) findViewById(R.id.mobileNo);
        tvChamberAddress = (TextView) findViewById(R.id.chamberAddress);
        tvChamberAddress.setOnClickListener(this);
        saveClinic=(LinearLayout)findViewById(R.id.saveClinic);
        saveClinic.setOnClickListener(this);
        tvSaveAndUpdate=(TextView) findViewById(R.id.tvSaveAndUpdate);
        //init Alert Dialog
        createAlertDialog();

        //get intent pass value
        docClinicInfo=(DocClinicInfo) getIntent().getSerializableExtra("docClinicInfo");
        if(docClinicInfo !=null){
          //Log.d(TAG,docClinicInfo.getT_clinic_other());
            clinic_id=docClinicInfo.getT_clinic_id();
            edtClinicName.setText(docClinicInfo.getT_clinic_other());
            edtMobileNo.setText(docClinicInfo.getT_clinic_mobile());
            tvAddress.setText(docClinicInfo.getT_clinic_address());
            tvVisitingHour1.setText(docClinicInfo.getT_clinic_visit_time1());
            tvVisitingHour2.setText(docClinicInfo.getT_clinic_visit_time2());
            tvVisitingDay.setText(docClinicInfo.getT_clinic_visit_day());
            tvChamberAddress.setText(docClinicInfo.getT_clinic_address()+" || "+ docClinicInfo.getT_clinic_visit_time1()+" || "+ docClinicInfo.getT_clinic_visit_time2()+" || "+ docClinicInfo.getT_clinic_visit_day());
            checkUpOrCheck="update";
            tvSaveAndUpdate.setText("Update Clinic");
        }
    }

    private void saveClinicInformation(){
        clinicName=edtClinicName.getText().toString();
        mobileNo=edtMobileNo.getText().toString();
        address=tvAddress.getText().toString();
        visitingHour1=tvVisitingHour1.getText().toString();
        visitingHour2=tvVisitingHour2.getText().toString();
        visitingDay=tvVisitingDay.getText().toString();

        Log.d(TAG, clinicName+"\t"+ mobileNo);

        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.insertDocClinic(user_id,address,visitingDay,visitingHour1,visitingHour2,mobileNo,clinicName);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "clinicInfo :: " + info);
                    if (info.get(0).getMsgString().equals("1")){
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();

                        Intent loginIntent = new Intent(context, DoctorProfileActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
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


    private void updateClinicInformation(){
        clinicName=edtClinicName.getText().toString();
        mobileNo=edtMobileNo.getText().toString();
        address=tvAddress.getText().toString();
        visitingHour1=tvVisitingHour1.getText().toString();
        visitingHour2=tvVisitingHour2.getText().toString();
        visitingDay=tvVisitingDay.getText().toString();

        Log.d(TAG, "clinic and doctor id : "+clinic_id+"\t"+ user_id);

        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.upDateDocClinic(clinic_id,user_id,address,visitingDay,visitingHour1,visitingHour2,mobileNo,clinicName);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "clinicInfo :: " + info);
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

    //create a alert dialog
    private void createAlertDialog(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.doctor_chamber_address_layout, null);
        promptsView.findViewById(R.id.addressLayout).setOnClickListener(this);
        promptsView.findViewById(R.id.visitingHour1).setOnClickListener(this);
        promptsView.findViewById(R.id.visitingHour2).setOnClickListener(this);
        promptsView.findViewById(R.id.visitingDay).setOnClickListener(this);
        tvVisitingHour1 = (TextView) promptsView.findViewById(R.id.vh1);
        tvVisitingHour2 = (TextView) promptsView.findViewById(R.id.vh2);
        tvAddress = (TextView) promptsView.findViewById(R.id.address);
        tvVisitingDay = (TextView) promptsView.findViewById(R.id.vd);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);
        builder.setCancelable(false)
                .setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                tvChamberAddress.setText(tvAddress.getText().toString()+" || "+ tvVisitingHour1.getText().toString()+" || "+ tvVisitingHour2.getText().toString()+" || "+ tvVisitingDay.getText().toString());
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
         alertDialog = builder.create();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visitingHour1:
                showVisitingHourPicker1();
                break;
            case R.id.visitingHour2:
                showVisitingHourPicker2();
                break;
            case R.id.next:
                timeSwitcher.showNext();
                break;
            case R.id.previous:
                timeSwitcher.showPrevious();
                break;
            case R.id.addressLayout:
                showAddressPicker();
                break;
            case R.id.visitingDay:
                showVisitDay();
                break;
            case R.id.chamberAddress:
                // show alert dialog
                alertDialog.show();
                break;
            case R.id.saveClinic:
//                Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();
                if(PrescriptionUtils.isInternetConnected(context)){

                    if(checkUpOrCheck.equals("update")){
                        //go for update clinic information
                        updateClinicInformation();
                    }else {
                        //go for save clinic information
                        saveClinicInformation();
                    }
                }else {
                    Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                    AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
                }
                break;
        }
    }

    public void showAddressPicker() {
        final EditText addressBox = new EditText(context);
        addressBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addressBox.setHint("Type tvAddress");
        if (!tvAddress.getText().toString().equalsIgnoreCase("Not set")) {
            addressBox.setText(tvAddress.getText());
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(addressBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = addressBox.getText().toString();
                if (text == null || text.isEmpty()) tvAddress.setText("Not set");
                else tvAddress.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    public void showVisitingHourPicker1() {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.visiting_hour_layout, null);
        timeSwitcher = (ViewSwitcher) view.findViewById(R.id.timeSwitcher);
        from = (TimePicker) view.findViewById(R.id.form);
        to = (TimePicker) view.findViewById(R.id.to);
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.previous).setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar cf = Calendar.getInstance();
                cf.set(Calendar.HOUR_OF_DAY, from.getCurrentHour());
                cf.set(Calendar.MINUTE, from.getCurrentMinute());
                Calendar ct = Calendar.getInstance();
                ct.set(Calendar.HOUR_OF_DAY, to.getCurrentHour());
                ct.set(Calendar.MINUTE, to.getCurrentMinute());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                tvVisitingHour1.setText(format.format(cf.getTime()) + " - " + format.format(ct.getTime()));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    public void showVisitingHourPicker2() {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.visiting_hour_layout, null);
        timeSwitcher = (ViewSwitcher) view.findViewById(R.id.timeSwitcher);
        from = (TimePicker) view.findViewById(R.id.form);
        to = (TimePicker) view.findViewById(R.id.to);
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.previous).setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar cf = Calendar.getInstance();
                cf.set(Calendar.HOUR_OF_DAY, from.getCurrentHour());
                cf.set(Calendar.MINUTE, from.getCurrentMinute());
                Calendar ct = Calendar.getInstance();
                ct.set(Calendar.HOUR_OF_DAY, to.getCurrentHour());
                ct.set(Calendar.MINUTE, to.getCurrentMinute());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                tvVisitingHour2.setText(format.format(cf.getTime()) + " - " + format.format(ct.getTime()));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }


    public void showVisitDay() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setMultiChoiceItems(R.array.weekDay, checkItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    checkItem[which] = true;
                }
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repeatDay = "";
                String[] days = getResources().getStringArray(R.array.weekDay);
                for (int i = 0; i < 7; i++) {
                    if (checkItem[i]) repeatDay += days[i] + ",";
                }
                if (repeatDay.length() == 0) tvVisitingDay.setText("Not set");
                else tvVisitingDay.setText(repeatDay.substring(0, repeatDay.length() - 1));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //<<<<<<<<<<<<<<<<<<  action  define for back press >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onBackPressed() {
        if (hardwareBackControll) {
            super.onBackPressed();
            return;
        }
        this.hardwareBackControll = true;
        PrescriptionUtils.backToPrevious(context, new DoctorProfileActivity());
    }


}
