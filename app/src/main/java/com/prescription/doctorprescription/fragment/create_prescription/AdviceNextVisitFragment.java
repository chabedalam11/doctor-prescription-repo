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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.webService.model.MedicineInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdviceNextVisitFragment extends Fragment implements  View.OnClickListener{

    final String TAG = "PatientPrescriptionInfoActivity";
    Context context;
    boolean hardwareBackControll;

    TextView edtAdviceUi;
    TextView edtAdviceValue;
    TextView edtNextVisitDate;
    ImageButton btnHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_advice_next_visit, container, false);
        context = getActivity();

        edtAdviceUi = (TextView) view.findViewById(R.id.edtAdvice);
        edtAdviceUi.setText(PrescriptionInfo.advice);
        edtAdviceUi.setOnClickListener(this);

        btnHelp = (ImageButton) view.findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);

        edtNextVisitDate = (TextView) view.findViewById(R.id.edtNextVisitDate);
        edtNextVisitDate.setText(PrescriptionInfo.nextVisit);
        setCalenderInDoBField();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHelp:
                //show alert dialog in help button
                AlartFactory.showHelpAlartDialog(context, "Remember", "Please add advice in multiple line like \n\n advice one \n advice two \n advice three", false);
                break;
            case R.id.edtAdvice:
                //show  dialog
                openDoaloge();
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
                PrescriptionInfo.nextVisit=sdf.format(myCalendar.getTime());
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


    private void openDoaloge(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.advice_add_layout, null);

        edtAdviceValue = (EditText) promptsView.findViewById(R.id.edtAdviceValue);
        edtAdviceValue.setText(edtAdviceUi.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);
        builder.setCancelable(false)
                .setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                edtAdviceUi.setText(edtAdviceValue.getText().toString());
                                PrescriptionInfo.advice=edtAdviceValue.getText().toString();
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
    }

}
