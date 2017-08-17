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
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DesignationInfo;
import com.prescription.doctorprescription.webService.model.Message;

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
    ArrayList<DesignationInfo> designationList;

    String button_Flag="";

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
        designationList = new ArrayList<DesignationInfo>();
        if (button_Flag.equals("deleteButton")){
            //show loader
            PrescriptionUtils.showProgressDialog(context);
        }

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
                            designationList.add(designationInfo);
                        }

                        DesignationListAdapter adapter = new DesignationListAdapter(context, R.layout.designation_list_items, designationList);
                        designationListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        designationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                DesignationInfo designationInfo = (DesignationInfo) adapterView.getItemAtPosition(i);

                                showActionChooser(designationInfo);
                            }
                        });

                    }

                    if (button_Flag.equals("deleteButton")){
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();
                    }
                }catch (Exception e){
                    Log.d(TAG,"list is null");
                    e.printStackTrace();
                    if (button_Flag.equals("deleteButton")){
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();
                    }
                    AlartFactory.showAPInotResponseWarn(context);
                }
            }

            @Override
            public void onFailure(Call<DesignationInfoCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                if (button_Flag.equals("deleteButton")){
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                }
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
    }


    private void deleteDocDesignation(String desig_id){

        Log.d(TAG, "clinic and doctor id : "+desig_id+"\t"+ doctor_id);

        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.deleteDocDesignation(desig_id,doctor_id);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "clinicInfo :: " + info);
                    if (info.get(0).getMsgString().equals("1")){
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();
                        Toast.makeText(context, "clinic delete complete", Toast.LENGTH_SHORT).show();
                        //get doc clinic information and set it in to ListView
                        getDocDesignationByDocId();
                    }else {
                        Toast.makeText(context, "clinic is not delete", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<MessegeCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });

    }


    private void showActionChooser(final  DesignationInfo designationInfo){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(actionList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent = new Intent(context, DesignationSetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("designationInfo",designationInfo);
                        startActivity(intent);
                        break;
                    case 1:
                        //before delete clinic first check internet connection
                        if(PrescriptionUtils.isInternetConnected(context)){
                            button_Flag="deleteButton";
                            deleteDocDesignation(designationInfo.getT_desig_id());
                        }else {
                            Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                            AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
                        }
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
