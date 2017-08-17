package com.prescription.doctorprescription.adapter.profile;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;

import java.util.ArrayList;

/**
 * Created by medisys on 8/10/2017.
 */

public class ClinicListAdapter extends ArrayAdapter<DocClinicInfo> {
    Context context;
    int layoutResourceId;
    ArrayList<DocClinicInfo> clinicList = new ArrayList<DocClinicInfo>();

    public ClinicListAdapter(Context context, int layoutResourceId, ArrayList<DocClinicInfo> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.clinicList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ClinicListWrapper clinicListWrapper = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            clinicListWrapper = new ClinicListWrapper();

//            Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/garmndmi.ttf");

            clinicListWrapper.tvClinicName = (TextView) item.findViewById(R.id.tvClinicName);

            item.setTag(clinicListWrapper);
        } else {
            clinicListWrapper = (ClinicListWrapper) item.getTag();
        }


        DocClinicInfo docClinicInfo = clinicList.get(position);
        if (docClinicInfo != null) {
            clinicListWrapper.tvClinicName.setText(docClinicInfo.getT_clinic_other());

        }

        return item;
    }

    static class ClinicListWrapper {
        TextView tvClinicName;
    }
}