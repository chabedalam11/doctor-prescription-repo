package com.prescription.doctorprescription.activity.patient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.WelcomeActivity;
import com.prescription.doctorprescription.adapter.patient.ReviewPatientAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.collection.PatientCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.Message;
import com.prescription.doctorprescription.webService.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewPatientActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "ReviewPatientActivity";
    Context context;
    PrescriptionMemories memory;
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    String doctor_id;
    boolean hardwareBackControll;

    //UI component
    EditText edtSearchPatient;
    ListView lvReviewPatient;
    RadioGroup rbSeachOption;

    String searchOption="search by id";

    ArrayList<Patient> patientArrayListForAdapter = new ArrayList<>();
    Map<String, Patient> mapForNameSearch = new HashMap<>();
    Map<String, Patient> mapForIdSearch = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_patient);
        setTitle("Review Patient");
        initialize();
    }

    private void initialize() {
        context = ReviewPatientActivity.this;
        memory = new PrescriptionMemories(context);
        doctor_id =memory.getPref(memory.KEY_DOC_ID);


        //init UI component
        edtSearchPatient =(EditText) findViewById(R.id.edtSearchPatient);
        // Add Text Change Listener to EditText
        edtSearchPatient.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchOption.equals("search by name")){
                    filterListView( s.toString(),mapForNameSearch);
                }else {
                    filterListView( s.toString(),mapForIdSearch);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        lvReviewPatient =(ListView) findViewById(R.id.lvReviewPatient);
        lvReviewPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient patient = (Patient) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(context,PatientHistoryActivity.class);
                intent.putExtra("patient",patient);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });







        rbSeachOption = (RadioGroup) findViewById(R.id.rbSeachOption);
        rbSeachOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    searchOption=rb.getText().toString();
                }
            }
        });

        //get Patient information and set it into listView
        if(PrescriptionUtils.isInternetConnected(context)){
            getPatientByDocId();
        }else {
            //Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
            AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
        }

    }

    private void getPatientByDocId(){
       //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<PatientCollection> getInfo = prescriptionApi.getPatientByDocId(doctor_id);
        getInfo.enqueue(new Callback<PatientCollection>() {
            @Override
            public void onResponse(Call<PatientCollection> call, Response<PatientCollection> response) {
                try {
                    List<Patient> info = response.body().data;
                    Log.d(TAG, "getPatientByDocId :: " + info);
                    if (info.size()>0){

                        for (Patient patient: info){
                            patientArrayListForAdapter.add(patient);
                            mapForNameSearch.put(patient.getT_pat_name().toLowerCase(),patient);
                            mapForIdSearch.put(patient.getT_pat_id(),patient);
                        }

                    }
                    //set value in listView
                    setAdapterInListView(patientArrayListForAdapter);
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
            public void onFailure(Call<PatientCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });

    }



    private void filterListView(String searchText,Map<String, Patient> map){
        ArrayList<Patient> newList = new ArrayList();


            for (Map.Entry<String, Patient> entry : map.entrySet()) {
                if (entry.getKey().contains(searchText.toLowerCase())) {
                    // do stuff with entry
                    Log.d(TAG,searchText);
                    newList.add(entry.getValue());
                }
            }



        setAdapterInListView(newList);



    }

    private void setAdapterInListView(ArrayList<Patient> patientArrayList){
        ReviewPatientAdapter adapter = new ReviewPatientAdapter(context, R.layout.review_patient_list_items, patientArrayList);
        lvReviewPatient.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLayoutsavePatient:
                //savePatientInformation();
                break;

        }
    }


    //<<<<<<<<<<<<<<<<<<  action  define for back press >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onBackPressed() {
        if (hardwareBackControll) {
            super.onBackPressed();
            return;
        }
        this.hardwareBackControll = true;
        PrescriptionUtils.backToPrevious(context, new WelcomeActivity());
    }


}
