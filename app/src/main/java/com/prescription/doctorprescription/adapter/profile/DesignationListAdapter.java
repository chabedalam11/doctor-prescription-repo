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
 * Created by medisys on 8/13/2017.
 */

public class DesignationListAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    ArrayList<String> designationList = new ArrayList<String>();


    public DesignationListAdapter(Context context, int layoutResourceId, ArrayList<String> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.designationList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        DesignationListAdapter.DesignationListWrapper designationListWrapper = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            designationListWrapper = new DesignationListAdapter.DesignationListWrapper();

//            Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/garmndmi.ttf");

            designationListWrapper.tvDesignationName = (TextView) item.findViewById(R.id.tvDesignationName);

            item.setTag(designationListWrapper);
        } else {
            designationListWrapper = (DesignationListAdapter.DesignationListWrapper) item.getTag();
        }

        String designation = designationList.get(position);
        if(designation != null){
            designationListWrapper.tvDesignationName.setText(designation);
        }

        return item;
    }

    static class DesignationListWrapper {
        TextView tvDesignationName;
    }

}
