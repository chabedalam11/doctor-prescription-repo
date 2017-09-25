package com.prescription.doctorprescription.fragment.create_prescription;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;

public class PatientInfoFragment extends Fragment implements  View.OnClickListener{

    final String TAG = "PatientInfoFragment";
    Context context;
    boolean hardwareBackControll;

    //UI component
    TextView edtChiefComplaints;
    TextView edtChiefComplaintValue;
    TextView edtGeneralExamin;

    TextView tvPatName;
    TextView tvPatAge;
    TextView tvPulse;
    TextView tvBloodPressure;
    TextView tvTemprature;
    TextView tvResp;
    //TextView tvOther2;
    RadioGroup rbGsex;

    ImageButton btnHelp;

    String sex ="Not set";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_patient_info, container, false);
        context = getActivity();

        edtChiefComplaints = (TextView) view.findViewById(R.id.edtChiefComplaints);
        edtChiefComplaints.setText(PrescriptionInfo.chiefcomplaint);
        edtChiefComplaints.setOnClickListener(this);

        btnHelp= (ImageButton) view.findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);

        edtGeneralExamin = (TextView) view.findViewById(R.id.edtGeneralExamin);
        edtGeneralExamin.setOnClickListener(this);
        edtGeneralExamin.setText(PrescriptionInfo.patientPalse+" || "+ PrescriptionInfo.patientBloodPressure+" || "+ PrescriptionInfo.patientTemprature+" || "+ PrescriptionInfo.patientResp/*+" || "+ tvOther2.getText().toString()*/);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtChiefComplaints:
                LayoutInflater li_2 = LayoutInflater.from(context);
                View promptsView_2 = li_2.inflate(R.layout.chief_complaints_holder_layout, null);
                edtChiefComplaintValue = (EditText) promptsView_2.findViewById(R.id.edtChiefComplaintValue);
                edtChiefComplaintValue.setText(edtChiefComplaints.getText().toString());
                AlertDialog.Builder builder_2 = new AlertDialog.Builder(context);
                builder_2.setView(promptsView_2);
                builder_2.setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        edtChiefComplaints.setText(edtChiefComplaintValue.getText().toString());
                                        PrescriptionInfo.chiefcomplaint=edtChiefComplaintValue.getText().toString();
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
                //promptsView_3.findViewById(R.id.otherLayoutGeneral).setOnClickListener(this);
                tvPulse = (TextView) promptsView_3.findViewById(R.id.tvPulse);
                tvBloodPressure = (TextView) promptsView_3.findViewById(R.id.tvBloodPressure);
                tvTemprature = (TextView) promptsView_3.findViewById(R.id.tvTemprature);
                tvResp = (TextView) promptsView_3.findViewById(R.id.tvResp);
                tvPulse.setText(PrescriptionInfo.patientPalse);
                tvBloodPressure.setText(PrescriptionInfo.patientBloodPressure);
                tvTemprature.setText(PrescriptionInfo.patientTemprature);
                tvResp.setText(PrescriptionInfo.patientPalse);
                //tvOther2 = (TextView) promptsView_3.findViewById(R.id.tvOther2);
                AlertDialog.Builder builder_3 = new AlertDialog.Builder(context);
                builder_3.setView(promptsView_3);
                builder_3.setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        PrescriptionInfo.patientPalse=tvPulse.getText().toString();
                                        PrescriptionInfo.patientBloodPressure=tvBloodPressure.getText().toString();
                                        PrescriptionInfo.patientTemprature=tvTemprature.getText().toString();
                                        PrescriptionInfo.patientResp=tvResp.getText().toString();
                                        //PrescriptionInfo.patientExmineOther=tvOther2.getText().toString();
                                        edtGeneralExamin.setText(tvPulse.getText().toString()+" || "+ tvBloodPressure.getText().toString()+" || "+ tvTemprature.getText().toString()+" || "+ tvResp.getText().toString()/*+" || "+ tvOther2.getText().toString()*/);

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


            case R.id.pulseLayout:
                showSingleEditTextDialog(tvPulse);
                break;
            case R.id.bloodPressureLayout:
                showSingleEditTextDialog(tvBloodPressure);
                break;
            case R.id.temperatureLayout:
                showSingleEditTextDialog(tvTemprature);
                break;
            case R.id.respLayout:
                showSingleEditTextDialog(tvResp);
                break;
             /*case R.id.otherLayoutGeneral:
                showSingleEditTextDialog(tvOther2);
                break;*/
            case R.id.btnHelp:
                //show alert dialog in help button
                AlartFactory.showHelpAlartDialog(context, "Remember", "Please add Chief Complaints in multiple line like \n\n Chief Complaints one \n Chief Complaints two \n Chief Complaints three", false);
                break;

        }
    }

    /*public void showPatientNamePicker() {
        final EditText addressBox = new EditText(context);
        addressBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addressBox.setHint("type patient name");
        if (!tvPatName.getText().toString().equalsIgnoreCase("Not set")) {
            addressBox.setText(tvPatName.getText());
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(addressBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = addressBox.getText().toString();
                if (text == null || text.isEmpty()) tvPatName.setText("Not set");
                else tvPatName.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }*/

    /*public void showPatientAgePicker() {
        final EditText addressBox = new EditText(context);
        addressBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addressBox.setHint("type patient age");
        if (!tvPatAge.getText().toString().equalsIgnoreCase("Not set")) {
            addressBox.setText(tvPatAge.getText());
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(addressBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = addressBox.getText().toString();
                if (text == null || text.isEmpty()) tvPatAge.setText("Not set");
                else tvPatAge.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }*/


    public void showSingleEditTextDialog(final TextView textView) {
        final EditText addressBox = new EditText(context);
        addressBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addressBox.setHint("type here");
        if (!textView.getText().toString().equalsIgnoreCase("Not set")) {
            addressBox.setText(textView.getText());
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(addressBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = addressBox.getText().toString();
                if (text == null || text.isEmpty()) textView.setText("Not set");
                else textView.setText(text);
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


}
