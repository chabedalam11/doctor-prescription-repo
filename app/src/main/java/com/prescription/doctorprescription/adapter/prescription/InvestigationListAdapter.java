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
import com.prescription.doctorprescription.fragment.create_prescription.InvestigationFragment;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.webService.model.Analysis;

import java.util.ArrayList;

/**
 * Created by medisys on 8/10/2017.
 */

public class InvestigationListAdapter extends ArrayAdapter<Analysis> {
    Context context;
    int layoutResourceId;
    ArrayList<Analysis> clinicList = new ArrayList<Analysis>();

    public ArrayList<Analysis> upDatedList(){
        return  clinicList;
    }

    public InvestigationListAdapter(Context context, int layoutResourceId, ArrayList<Analysis> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.clinicList = objects;
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
                    Analysis itemsPostion = clinicList.get(position);
                    clinicList.remove(itemsPostion);

                    // Toast.makeText(context, itemsPostion + " is removed!!!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    PrescriptionInfo.analysisesList = clinicList;
                    /*Log.d("adapter","======== investigation 2 =============");
                    for(Analysis analysis : PrescriptionInfo.analysisesList){
                        Log.d("adapter",analysis.getT_analysis_name());
                    }*/
                }
            });

            clinicListWrapper.tvAnalysis = (TextView) item.findViewById(R.id.tvAnalysis);

            item.setTag(clinicListWrapper);
        } else {
            clinicListWrapper = (ClinicListWrapper) item.getTag();
        }


        Analysis docClinicInfo = clinicList.get(position);
        if (docClinicInfo != null) {
            clinicListWrapper.tvAnalysis.setText(docClinicInfo.getT_analysis_name());

        }

        return item;
    }

    static class ClinicListWrapper {
        TextView tvAnalysis;
        ImageButton imgRemove;
    }
}