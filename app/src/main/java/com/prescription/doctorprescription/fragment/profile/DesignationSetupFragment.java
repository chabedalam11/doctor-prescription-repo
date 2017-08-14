package com.prescription.doctorprescription.fragment.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.profile.DesignationSetupActivity;
import com.prescription.doctorprescription.adapter.profile.DesignationListAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.DesignationInfoCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DesignationInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DesignationSetupFragment extends Fragment implements View.OnClickListener{
    final String TAG = "DesignationSetupFragment";
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    PrescriptionMemories memory;

    Context context;
    boolean hardwareBackControll;

    ListView designationListView;
    LinearLayout newDesignation;
    private String[] actionList={"View Details","Remove Designation"};
    String doctor_id;
    ArrayList<String> designationList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_designation_setup, container, false);
        context = getActivity();

        memory = new PrescriptionMemories(context);
        doctor_id = memory.getPref(memory.KEY_DOC_ID);

        designationListView = (ListView) view.findViewById(R.id.designationListView);
        newDesignation = (LinearLayout) view.findViewById(R.id.newDesignation);
        newDesignation.setOnClickListener(this);

        getDocDesignationByDocId();

        return view;
    }


    //checking login in information
    private void getDocDesignationByDocId() {
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Log.d(TAG, "DOCTOR ID >> :: " + doctor_id);
        Call<DesignationInfoCollection> getInfo = prescriptionApi.getDocDesignationByDocId(doctor_id);
        getInfo.enqueue(new Callback<DesignationInfoCollection>() {
            @Override
            public void onResponse(Call<DesignationInfoCollection> call, Response<DesignationInfoCollection> response) {
                try {
                    List<DesignationInfo> info = response.body().data;
                    Log.d(TAG, "Designation Info :: " + info);
                    if (info.size() > 0) {
                        for (DesignationInfo designationInfo:info){
                            designationList.add(designationInfo.getT_desig_name());
                        }

                        DesignationListAdapter adapter = new DesignationListAdapter(context, R.layout.designation_list_items, designationList);
                        designationListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        designationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String listValue = (String) adapterView.getItemAtPosition(i);

                                showActionChooser(listValue);
                            }
                        });

                    }else {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                }catch (Exception e){
                    Log.d(TAG,"list is null");
                    e.printStackTrace();
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                    AlartFactory.showAPInotResponseWarn(context);
                }
            }

            @Override
            public void onFailure(Call<DesignationInfoCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
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
                        Intent intent = new Intent(context, DesignationSetupActivity.class);

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
        builder.setTitle("Choose An Action");
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newDesignation:
                Intent intent = new Intent(context, DesignationSetupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
