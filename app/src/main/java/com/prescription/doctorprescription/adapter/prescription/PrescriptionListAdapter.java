package com.prescription.doctorprescription.adapter.prescription;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.webService.model.DrugMaster;
import com.prescription.doctorprescription.webService.model.Prescription;

import java.util.ArrayList;

/**
 * Created by medisys on 8/10/2017.
 */

public class PrescriptionListAdapter extends ArrayAdapter<Prescription> {
    Context context;
    int layoutResourceId;
    ArrayList<Prescription> prescriptionArrayList = new ArrayList<Prescription>();

    public ArrayList<Prescription> upDatedList(){
        return prescriptionArrayList;
    }

    public PrescriptionListAdapter(Context context, int layoutResourceId, ArrayList<Prescription> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.prescriptionArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        PrescriptionListWrapper prescriptionListWrapper = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            prescriptionListWrapper = new PrescriptionListWrapper();
            prescriptionListWrapper.tvPresDate=(TextView) item.findViewById(R.id.tvPresDate);
            prescriptionListWrapper.tvPresID = (TextView) item.findViewById(R.id.tvPresID);

            item.setTag(prescriptionListWrapper);
        } else {
            prescriptionListWrapper = (PrescriptionListWrapper) item.getTag();
        }


        Prescription prescription = prescriptionArrayList.get(position);
        if (prescription != null) {
            prescriptionListWrapper.tvPresDate.setText(prescription.getT_pres_date());
            prescriptionListWrapper.tvPresID.setText(prescription.getT_pres_id());
        }

        return item;
    }

    static class PrescriptionListWrapper {
        TextView tvPresDate;
        TextView tvPresID;
    }
}