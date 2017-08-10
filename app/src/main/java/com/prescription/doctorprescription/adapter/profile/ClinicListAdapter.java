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

import java.util.ArrayList;

/**
 * Created by medisys on 8/10/2017.
 */

public class ClinicListAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    ArrayList<String> clinicList = new ArrayList<String>();

    public ClinicListAdapter(Context context, int layoutResourceId, ArrayList<String> objects) {
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

        String appointment = clinicList.get(position);
        if(appointment != null){
            clinicListWrapper.tvClinicName.setText(appointment);
        }

        return item;
    }

    static class ClinicListWrapper {
        TextView tvClinicName;
    }
}
