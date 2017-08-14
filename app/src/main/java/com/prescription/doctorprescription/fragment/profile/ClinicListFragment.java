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
import com.prescription.doctorprescription.activity.WelcomeActivity;
import com.prescription.doctorprescription.activity.profile.ClinicSetupActivity;
import com.prescription.doctorprescription.adapter.profile.ClinicListAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.DocClinicInfoCollection;
import com.prescription.doctorprescription.webService.collection.DoctorLoginCollection;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;
import com.prescription.doctorprescription.webService.model.DoctorLogin;
import com.prescription.doctorprescription.webService.model.Message;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicListFragment extends Fragment implements  View.OnClickListener{

    final String TAG = "ClinicListFragment";
    Context context;
    PrescriptionMemories memory;
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    String user_id;;
    boolean hardwareBackControll;

    ListView clinicListView;
    LinearLayout newClinic;
    private String[] actionList={"View Detail","Remove Clinic"};

    //list for adapter
    ArrayList<DocClinicInfo> clinicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_clinic_setup, container, false);
        context = getActivity();
        memory = new PrescriptionMemories(context);
        user_id=memory.getPref(memory.KEY_DOC_ID);

        clinicListView = (ListView) view.findViewById(R.id.clinicListView);
        newClinic = (LinearLayout) view.findViewById(R.id.newClinic);
        newClinic.setOnClickListener(this);

        //get doc clinic information and set it in to ListView
        getDocClinicByDocId();

        return view;
    }

    private void getDocClinicByDocId(){
        clinicList = new ArrayList<DocClinicInfo>();
        //Hide Dialog
        PrescriptionUtils.hideProgressDialog();
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Log.d(TAG, "docId "+user_id);
        Call<DocClinicInfoCollection> getInfo = prescriptionApi.getDocClinicByDocId(user_id);
        getInfo.enqueue(new Callback<DocClinicInfoCollection>() {
            @Override
            public void onResponse(Call<DocClinicInfoCollection> call, Response<DocClinicInfoCollection> response) {
                try {
                    List<DocClinicInfo> info = response.body().data;
                    Log.d(TAG, "docClinic Info :: " + info);
                    if (info.size() > 0) {
                        for (DocClinicInfo docClinicInfo: info){
                            clinicList.add(docClinicInfo);
                        }

                        ClinicListAdapter adapter = new ClinicListAdapter(context, R.layout.clinic_list_items, clinicList);
                        clinicListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                DocClinicInfo docClinicInfo = (DocClinicInfo) adapterView.getItemAtPosition(i);

                                showActionChooser(docClinicInfo);
                            }
                        });

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
            public void onFailure(Call<DocClinicInfoCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
    }


    private void showActionChooser(final  DocClinicInfo docClinicInfo){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(actionList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent = new Intent(context, ClinicSetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("docClinicInfo",docClinicInfo);
                        startActivity(intent);
                        break;
                    case 1:
                        //before delete clinic first check internet connection
                        if(PrescriptionUtils.isInternetConnected(context)){
                            deleteDocClinic(docClinicInfo.getT_clinic_id());
                        }else {
                            Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
                            AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
                        }
                        break;
                }
            }
        });
        builder.setTitle("Choose an action");
        builder.create().show();
}


    private void deleteDocClinic(String clinic_id){

        Log.d(TAG, "clinic and doctor id : "+clinic_id+"\t"+ user_id);

        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.deleteDocClinic(clinic_id,user_id);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "clinicInfo :: " + info);
                    if (info.get(0).getMsgString().equals("1")){

                        Toast.makeText(context, "clinic delete complete", Toast.LENGTH_SHORT).show();
                        //get doc clinic information and set it in to ListView
                        getDocClinicByDocId();
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
