package com.prescription.doctorprescription.adapter.prescription;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.webService.model.DrugMaster;

import java.util.ArrayList;

/**
 * Created by medisys on 8/10/2017.
 */

public class MedicineInfoAdapter extends ArrayAdapter<DrugMaster> {
    Context context;
    int layoutResourceId;
    ArrayList<DrugMaster> drugMasterArrayList = new ArrayList<DrugMaster>();

    public ArrayList<DrugMaster> upDatedList(){
        return drugMasterArrayList;
    }

    public MedicineInfoAdapter(Context context, int layoutResourceId, ArrayList<DrugMaster> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.drugMasterArrayList = objects;
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
            clinicListWrapper.imgRemove=(ImageButton) item.findViewById(R.id.imgRemove);
            clinicListWrapper.imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrugMaster itemsPostion = drugMasterArrayList.get(position);
                    drugMasterArrayList.remove(itemsPostion);

                    // Toast.makeText(context, itemsPostion + " is removed!!!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    PrescriptionInfo.drugMasterList = drugMasterArrayList;
                    /*Log.d("adapter","======== investigation 2 =============");
                    for(Analysis analysis : PrescriptionInfo.analysisesList){
                        Log.d("adapter",analysis.getT_analysis_name());
                    }*/
                }
            });

            clinicListWrapper.um = (TextView) item.findViewById(R.id.um);
            clinicListWrapper.medicineName = (TextView) item.findViewById(R.id.medicineName);
            clinicListWrapper.strength = (TextView) item.findViewById(R.id.strength);
            clinicListWrapper.doseTime = (TextView) item.findViewById(R.id.doseTime);
            clinicListWrapper.duration = (TextView) item.findViewById(R.id.duration);
            clinicListWrapper.advice = (TextView) item.findViewById(R.id.advice);

            item.setTag(clinicListWrapper);
        } else {
            clinicListWrapper = (ClinicListWrapper) item.getTag();
        }


        DrugMaster drugMaster = drugMasterArrayList.get(position);
        if (drugMaster != null) {
            clinicListWrapper.um.setText(drugMaster.getT_um());
            clinicListWrapper.medicineName.setText(drugMaster.getT_medicine_name());
            clinicListWrapper.strength.setText(drugMaster.getT_strength());
            clinicListWrapper.doseTime.setText(drugMaster.getT_dose_time());
            clinicListWrapper.duration.setText(drugMaster.getT_duration());
            clinicListWrapper.advice.setText(drugMaster.getT_advice());

        }

        return item;
    }

    static class ClinicListWrapper {
        TextView um;
        TextView medicineName;
        TextView strength;
        TextView doseTime;
        TextView duration;
        TextView advice;
        ImageButton imgRemove;
    }
}