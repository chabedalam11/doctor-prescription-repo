package com.prescription.doctorprescription.fragment.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.profile.ClinicSetupActivity;
import com.prescription.doctorprescription.adapter.profile.ClinicListAdapter;

import java.util.ArrayList;

public class ClinicListFragment extends Fragment implements  View.OnClickListener{

    final String TAG = "ClinicListFragment";
    Context context;
    boolean hardwareBackControll;

    ListView clinicListView;
    LinearLayout newClinic;
    private String[] actionList={"View Detail","Remove Clinic"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_clinic_setup, container, false);
        context = getActivity();

        clinicListView = (ListView) view.findViewById(R.id.clinicListView);
        newClinic = (LinearLayout) view.findViewById(R.id.newClinic);
        newClinic.setOnClickListener(this);

        ArrayList<String> clinicList = new ArrayList<String>();
        clinicList.add("Home clinic");
        clinicList.add("Bkash Clinic");

        ClinicListAdapter adapter = new ClinicListAdapter(context, R.layout.clinic_list_items, clinicList);
        clinicListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listValue = (String) adapterView.getItemAtPosition(i);

                showActionChooser(listValue);
            }
        });

        return view;
    }


    private void showActionChooser(final  String listValue){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(actionList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        /*Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:"+profile.getContactNo()));
                        startActivity(call);*/
                        Intent intent = new Intent(context, ClinicSetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case 1:
                       /* Intent sms = new Intent(Intent.ACTION_VIEW);
                        sms.setData(Uri.parse("smsto:"+profile.getContactNo()));
                        startActivity(sms);*/
                        break;
                    case 2:
                       /* Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{profile.getEmail()});
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "Choose an Email client :"));*/
                        break;
                    case 3:
                        //listener.showProfile(profile);
                        break;
                    case 4:
                        /*if (ApplicationMain.getDatabase().removeDoctorProfile(String.valueOf(profile.getId()))>0) {
                            ((ArrayAdapter<DoctorProfile>) doctorProfileList.getAdapter()).remove(profile);
                            ((ArrayAdapter<DoctorProfile>) doctorProfileList.getAdapter()).notifyDataSetChanged();
                        }*/
                        break;
                }
            }
        });
        builder.setTitle("Choose an action");
        builder.create().show();
}



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newClinic:
                Intent intent = new Intent(context, ClinicSetupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
