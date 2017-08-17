package com.prescription.doctorprescription.fragment.create_prescription;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prescription.doctorprescription.R;

public class PatientInfoFragment extends Fragment implements  View.OnClickListener{

    final String TAG = "PatientInfoFragment";
    Context context;
    boolean hardwareBackControll;

    TextView edtPatientInformation;
    TextView edtChiefComplaints;
    TextView edtGeneralExamin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_patient_info, container, false);
        context = getActivity();

        edtPatientInformation = (TextView) view.findViewById(R.id.edtPatientInformation);
        edtPatientInformation.setOnClickListener(this);

        edtChiefComplaints = (TextView) view.findViewById(R.id.edtChiefComplaints);
        edtChiefComplaints.setOnClickListener(this);

        edtGeneralExamin = (TextView) view.findViewById(R.id.edtGeneralExamin);
        edtGeneralExamin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtPatientInformation:
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.patient_information_holder_layout, null);
                promptsView.findViewById(R.id.patNameLayout).setOnClickListener(this);
                promptsView.findViewById(R.id.ageLayout).setOnClickListener(this);
                promptsView.findViewById(R.id.genderLayout).setOnClickListener(this);
                /*tvPatName = (TextView) promptsView.findViewById(R.id.tvPatName);
                tvPatAge = (TextView) promptsView.findViewById(R.id.tvPatAge);
                rbGsex = (RadioGroup) promptsView.findViewById(R.id.rbGsex);
                rbGsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(checkedId);
                        if (null != rb && checkedId > -1) {
                            //Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                            sex = rb.getText().toString();
                            if(sex.equals("MALE")){
                                sex= "1";
                            }else if(sex.equals("FEMALE")){
                                sex = "2";
                            }else if(sex.equals("UNKNOWN")){
                                sex = "3";
                            }
                        }
                    }
                });*/

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(promptsView);
                builder.setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = builder.create();

                // show it
                alertDialog.show();
                break;

            case R.id.edtChiefComplaints:
                LayoutInflater li_2 = LayoutInflater.from(context);
                View promptsView_2 = li_2.inflate(R.layout.chief_complaints_holder_layout, null);
                promptsView_2.findViewById(R.id.complaintOneLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.complaintTwoLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.complaintThreeLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.otherLayout).setOnClickListener(this);
                AlertDialog.Builder builder_2 = new AlertDialog.Builder(context);
                builder_2.setView(promptsView_2);
                builder_2.setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog_2 = builder_2.create();

                // show it
                alertDialog_2.show();
                break;

            case R.id.edtGeneralExamin:
                LayoutInflater li_3 = LayoutInflater.from(context);
                View promptsView_3 = li_3.inflate(R.layout.patient_general_examination_holder_layout, null);
                promptsView_3.findViewById(R.id.pulseLayout).setOnClickListener(this);
                promptsView_3.findViewById(R.id.bloodPressureLayout).setOnClickListener(this);
                promptsView_3.findViewById(R.id.temperatureLayout).setOnClickListener(this);
                promptsView_3.findViewById(R.id.respLayout).setOnClickListener(this);
                promptsView_3.findViewById(R.id.otherLayout).setOnClickListener(this);
                AlertDialog.Builder builder_3 = new AlertDialog.Builder(context);
                builder_3.setView(promptsView_3);
                builder_3.setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog_3 = builder_3.create();

                // show it
                alertDialog_3.show();
                break;
        }
    }
}
