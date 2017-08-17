package com.prescription.doctorprescription.fragment.create_prescription;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.prescription.doctorprescription.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdviceNextVisitFragment extends Fragment implements  View.OnClickListener{

    final String TAG = "PatientPrescriptionInfoActivity";
    Context context;
    boolean hardwareBackControll;

    TextView edtAdvice;
    TextView edtNextVisitDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_advice_next_visit, container, false);
        context = getActivity();

        edtAdvice = (TextView) view.findViewById(R.id.edtAdvice);
        edtAdvice.setOnClickListener(this);

        edtNextVisitDate = (TextView) view.findViewById(R.id.edtNextVisitDate);
        setCalenderInDoBField();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtAdvice:
                LayoutInflater li_2 = LayoutInflater.from(context);
                View promptsView_2 = li_2.inflate(R.layout.advice_nextvisit_information_holder, null);
                promptsView_2.findViewById(R.id.adviceOneLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.adviceTwoLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.adviceThreeLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.adviceFourLayout).setOnClickListener(this);
                promptsView_2.findViewById(R.id.adviceFiveLayout).setOnClickListener(this);
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
        }
    }

    //set date picker in dob EditText
    private void setCalenderInDoBField(){
        //date packer
        final Calendar myCalendar = Calendar.getInstance();
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

                edtNextVisitDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        edtNextVisitDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}
