package com.prescription.doctorprescription.fragment.create_prescription;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.adapter.AutoCompleteTextViewAdapter;
import com.prescription.doctorprescription.adapter.prescription.InvestigationListAdapter;
import com.prescription.doctorprescription.adapter.prescription.MedicineInfoAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MedicineInfoCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.DrugMaster;
import com.prescription.doctorprescription.webService.model.MedicineInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionFragment extends Fragment {

    final String TAG = "PrescriptionFragment";
    Context context;
    boolean hardwareBackControll;
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    PrescriptionMemories memory;
    String doctor_id;

    //ui   component
    AutoCompleteTextView auComUM;
    AutoCompleteTextView auComMedicinetName;
    EditText edtStrength;
    AutoCompleteTextView auComDoseTime;
    EditText edtDuration;
    EditText edtAdvice;
    ImageButton btnAddMedicine;
    ListView lvMedicineDetails;

    //list for auto complete
    ArrayList<String> listForAuComUM = new ArrayList<>();
    ArrayList<String> listForAuComMedicinetName = new ArrayList<>();
    ArrayList<String> listForAuComUnit = new ArrayList<>();
    ArrayList<String> listForAuComDoseTime = new ArrayList<>();


    //list for pass in adapter
    ArrayList<DrugMaster> drugMasterArrayList = new ArrayList<DrugMaster>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_prescription, container, false);
        context = getActivity();
        memory = new PrescriptionMemories(context);
        doctor_id = memory.getPref(memory.KEY_DOC_ID);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        //UI component init
        auComUM=(AutoCompleteTextView) view.findViewById(R.id.auComUM);
        auComMedicinetName=(AutoCompleteTextView) view.findViewById(R.id.auComMedicinetName);
        edtStrength =(EditText) view.findViewById(R.id.edtStrength);
        auComDoseTime=(AutoCompleteTextView) view.findViewById(R.id.auComDoseTime);
        edtDuration=(EditText) view.findViewById(R.id.edtDuration);
        edtAdvice=(EditText) view.findViewById(R.id.edtAdvice);
        lvMedicineDetails=(ListView) view.findViewById(R.id.lvMedicineDetails);
        btnAddMedicine=(ImageButton) view.findViewById(R.id.btnAddMedicine);
        btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!auComMedicinetName.getText().toString().equals("")){
                    //button action
                    addMedicineInfoInListView();

                    //clear field
                    auComUM.setText("");
                    auComMedicinetName.setText("");
                    edtStrength.setText("");
                    auComDoseTime.setText("");
                    edtDuration.setText("");
                    edtAdvice.setText("");
                }else {
                    Toast.makeText(context, "select a medicine", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //get medicine info and set search value in AutoCompleteTextView
        getMedicineInfo();

        //this section for update time
        if( PrescriptionInfo.drugMasterList != null){
            drugMasterArrayList=PrescriptionInfo.drugMasterList;
            MedicineInfoAdapter adapter = new MedicineInfoAdapter(context, R.layout.medicine_list_items, drugMasterArrayList);
            lvMedicineDetails.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


    //<<<<<<<<<<<<<<<<<< get Medicine  Information  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getMedicineInfo() {
        /*//show loader
        PrescriptionUtils.showProgressDialog(context);*/
        Call<MedicineInfoCollection> getInfo = prescriptionApi.getMedicineInfo(doctor_id);
        getInfo.enqueue(new Callback<MedicineInfoCollection>() {
            @Override
            public void onResponse(Call<MedicineInfoCollection> call, Response<MedicineInfoCollection> response) {
                try {

                    List<MedicineInfo> info = response.body().data;
                    Log.d(TAG, "getMedicineInfo:: " + info);
                    if (info.size() > 0) {


                        listForAuComUM = info.get(0).getUm();
                        listForAuComMedicinetName = info.get(0).getTrade();
                        listForAuComDoseTime = info.get(0).getFrequency();

                        createArrayAdapterForAutoComplete(auComUM,listForAuComUM);
                        createArrayAdapterForAutoComplete(auComMedicinetName,listForAuComMedicinetName);
                        createArrayAdapterForAutoComplete(auComDoseTime,listForAuComDoseTime);

                    }
                }catch (Exception e){
                    Log.d(TAG,"list is null");
                    e.printStackTrace();
                    /*//Hide Dialog
                    PrescriptionUtils.hideProgressDialog();*/
                    AlartFactory.showAPInotResponseWarn(context);
                }
            }
            @Override
            public void onFailure(Call<MedicineInfoCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
               /* //Hide Dialog
                PrescriptionUtils.hideProgressDialog();*/
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
    }

    //<<<<<<<<<<<<<<<<<< create adapter for autoComInvestigationName search option >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void createArrayAdapterForAutoComplete(final AutoCompleteTextView autoCompleteTextView,ArrayList<String> list) {
        //Creating the instance of ArrayAdapter containing list of language names
        AutoCompleteTextViewAdapter adapter = new AutoCompleteTextViewAdapter(context, list);
        //SET AutoCompleteTextView WIDTH FULL SCREEN
        //Point pointSize = new Point();
        //getWindowManager().getDefaultDisplay().getSize(pointSize);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        autoCompleteTextView.setDropDownWidth(dm.widthPixels);

        autoCompleteTextView.setThreshold(1);//will start working from first character
        autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteTextView.showDropDown();
                return false;
            }
        });

    }


    private void addMedicineInfoInListView(){
        DrugMaster drugMaster =new DrugMaster();
        drugMaster.setT_um(auComUM.getText().toString());
        drugMaster.setT_medicine_name(auComMedicinetName.getText().toString());
        drugMaster.setT_strength(edtStrength.getText().toString());
        drugMaster.setT_dose_time(auComDoseTime.getText().toString());
        drugMaster.setT_duration(edtDuration.getText().toString());
        drugMaster.setT_advice(edtAdvice.getText().toString());

        //if (drugMasterArrayList.contains(drugMaster)){
            drugMasterArrayList.add(drugMaster);
            MedicineInfoAdapter adapter = new MedicineInfoAdapter(context, R.layout.medicine_list_items, drugMasterArrayList);
            lvMedicineDetails.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            getValueFromListView();
        /*}else {
            Toast.makeText(context, " Already Add !!!", Toast.LENGTH_SHORT).show();
        }*/

    }


    public void getValueFromListView() {
        //ArrayList<Analysis> analysisesList = new ArrayList<Analysis>();
        MedicineInfoAdapter adapter = new MedicineInfoAdapter(context, R.layout.medicine_list_items, drugMasterArrayList) {
            @Override
            public ArrayList<DrugMaster> upDatedList() {
                return super.upDatedList();
            }
        };
        PrescriptionInfo.drugMasterList = adapter.upDatedList();
            /*Log.d(TAG,"======== Drugmaster 1=============");
            for(DrugMaster drugMaster : PrescriptionInfo.drugMasterList){
                Log.d(TAG,drugMaster.getT_medicine_name());
            }*/
    }
}
