package com.prescription.doctorprescription.adapter.patient;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.webService.model.DrugMaster;
import com.prescription.doctorprescription.webService.model.Patient;

import java.util.ArrayList;

/**
 * Created by medisys on 8/10/2017.
 */

public class ReviewPatientAdapter extends ArrayAdapter<Patient>{
    Context context;
    int layoutResourceId;
    ArrayList<Patient> patientArrayList = new ArrayList<Patient>();

    /*public ArrayList<Patient> upDatedList(){
        return patientArrayList;
    }*/

    public ReviewPatientAdapter(Context context, int layoutResourceId, ArrayList<Patient> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.patientArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ClinicListWrapper clinicListWrapper = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            clinicListWrapper = new ClinicListWrapper();


            clinicListWrapper.tvPatName = (TextView) item.findViewById(R.id.tvPatName);
            clinicListWrapper.tvPatId = (TextView) item.findViewById(R.id.tvPatId);
            clinicListWrapper.tvAge = (TextView) item.findViewById(R.id.tvAge);
            clinicListWrapper.tvGender = (TextView) item.findViewById(R.id.tvGender);

            item.setTag(clinicListWrapper);
        } else {
            clinicListWrapper = (ClinicListWrapper) item.getTag();
        }


        Patient drugMaster = patientArrayList.get(position);
        if (drugMaster != null) {
            clinicListWrapper.tvPatName.setText(drugMaster.getT_pat_name());
            clinicListWrapper.tvPatId.setText("ID : "+drugMaster.getT_pat_id());
            clinicListWrapper.tvAge.setText(drugMaster.getT_pat_age());
            clinicListWrapper.tvGender.setText(drugMaster.getT_pat_sex());
        }

        return item;
    }

    static class ClinicListWrapper {
        TextView tvPatName;
        TextView tvPatId;
        TextView tvAge;
        TextView tvGender;
    }
}