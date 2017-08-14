package com.prescription.doctorprescription.activity.patient_prescription;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.prescription.doctorprescription.R;

public class PatientPrescriptionInfoActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "PatientPrescriptionInfoActivity";
    Context context;
    boolean hardwareBackControll;

    TextView edtPatientInformation;
    TextView edtChiefComplaints;
    TextView edtGeneralExamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription_info);
        setTitle("Patient Informtion");
        initialize();
    }


    private void initialize() {
        context = PatientPrescriptionInfoActivity.this;

        edtPatientInformation = (TextView) findViewById(R.id.edtPatientInformation);
        edtPatientInformation.setOnClickListener(this);

        edtChiefComplaints = (TextView) findViewById(R.id.edtChiefComplaints);
        edtChiefComplaints.setOnClickListener(this);

        edtGeneralExamin = (TextView) findViewById(R.id.edtGeneralExamin);
        edtGeneralExamin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtPatientInformation:
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.patient_information_holder_layout, null);
                break;
        }
    }
}
