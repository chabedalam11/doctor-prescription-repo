package com.prescription.doctorprescription.fragment.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import com.prescription.doctorprescription.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DesignationSetupFragment extends Fragment implements View.OnClickListener{
    final String TAG = "DesignationSetupFragment";
    Context context;
    boolean hardwareBackControll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_designation_setup, container, false);
        context = getActivity();


        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
