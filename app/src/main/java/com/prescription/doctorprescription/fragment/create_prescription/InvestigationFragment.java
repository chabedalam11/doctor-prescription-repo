package com.prescription.doctorprescription.fragment.create_prescription;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.adapter.AutoCompleteTextViewAdapter;
import com.prescription.doctorprescription.adapter.prescription.InvestigationListAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.AnalysisCollection;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigationFragment extends Fragment {
    final String TAG = "InvestigationFragment";
    Context context;
    boolean hardwareBackControll;
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    PrescriptionMemories memory;
    String doctor_id;

    //Ui component
    AutoCompleteTextView autoComInvestigationName;
    ListView listViewInvestigationName;
    ImageButton imgAddInves;
    EditText edtInvestigationName;

    //list for auto complete
    ArrayList<String> listForAutoCompleteAnalysisName = new ArrayList<>();

    //map ans list for listview
    Map map = new HashMap();
    ArrayList<Analysis> investigationList = new ArrayList<Analysis>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_investigation, container, false);
        context = getActivity();
        memory = new PrescriptionMemories(context);
        doctor_id = memory.getPref(memory.KEY_DOC_ID);

        initialize(view);
        return view;
    }

    private void initialize(View view) {
        //ui component init
        autoComInvestigationName=(AutoCompleteTextView)view.findViewById(R.id.autoComInvestigationName);
        listViewInvestigationName=(ListView) view.findViewById(R.id.listViewInvestigationName);
        imgAddInves=(ImageButton) view.findViewById(R.id.imgAddInves);
        imgAddInves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoaloge();
            }
        });

        //get analysis name ans set it to autoCompleteText
        if(PrescriptionUtils.isInternetConnected(context)){
            getAnalysisByDocId();
        }else {
            //Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
            AlartFactory.showNetworkErrorAlertDialog(context, "No Internet Connection", "Please check your internet connection", false);
        }

        //this section for update time
        if(PrescriptionInfo.analysisesList != null){
            investigationList=PrescriptionInfo.analysisesList;
            InvestigationListAdapter adapter = new InvestigationListAdapter(context, R.layout.investigation_list_items, investigationList);
            listViewInvestigationName.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //getValueFromListView();
        }

    }

    private void openDoaloge(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.investigation_add_layout, null);

        edtInvestigationName = (EditText) promptsView.findViewById(R.id.edtInvestigationName);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);
        builder.setCancelable(false)
                .setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                 insertAnalysis(edtInvestigationName.getText().toString());
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }


    //<<<<<<<<<<<<<<<<<< get Analyzer Information  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getAnalysisByDocId() {
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<AnalysisCollection> getInfo = prescriptionApi.getAnalysisByDocId(doctor_id);
        getInfo.enqueue(new Callback<AnalysisCollection>() {
            @Override
            public void onResponse(Call<AnalysisCollection> call, Response<AnalysisCollection> response) {
                try {

                    List<Analysis> info = response.body().data;
                    Log.d(TAG, "getAnalysisByDocId:: " + info);
                    listForAutoCompleteAnalysisName.clear();
                    if (info.size() > 0) {
                        for (int i = 0; i < info.size(); i++) {
                            listForAutoCompleteAnalysisName.add(info.get(i).getT_analysis_name());
                            map.put(info.get(i).getT_analysis_name(), info.get(i));
                        }

                        //create a arrayAdapter and set it in tvReqDropdown
                        createArrayAdapterForAnalysis();

                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();

                    } else {
                        createArrayAdapterForAnalysis();
                        //Hide Dialog
                        PrescriptionUtils.hideProgressDialog();
                    }
                }catch (Exception e){
                    Log.d(TAG,"list is null");
                    e.printStackTrace();
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                    AlartFactory.showAPInotResponseWarn(context);
                }
            }
            @Override
            public void onFailure(Call<AnalysisCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });
    }


    //checking login in information
    private void insertAnalysis(String analysisName) {
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.insertAnalysis(analysisName,doctor_id);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "doctorInfo :: " + info);
                    //Hide Dialog
                    PrescriptionUtils.hideProgressDialog();
                    if (info.get(0).getMsgString().equals("1")){
                        getAnalysisByDocId();
                        Toast.makeText(context, "ADD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }else if (info.get(0).getMsgString().equals("101")){
                        Toast.makeText(context, "NAME ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "PLEASE TRY AGAIN AFTER SOME TIMES", Toast.LENGTH_SHORT).show();
                    }

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



    //<<<<<<<<<<<<<<<<<< create adapter for autoComInvestigationName search option >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void createArrayAdapterForAnalysis() {
        //Creating the instance of ArrayAdapter containing list of language names
        AutoCompleteTextViewAdapter adapter = new AutoCompleteTextViewAdapter(context, listForAutoCompleteAnalysisName);
        //SET AutoCompleteTextView WIDTH FULL SCREEN
        //Point pointSize = new Point();
        //getWindowManager().getDefaultDisplay().getSize(pointSize);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        autoComInvestigationName.setDropDownWidth(dm.widthPixels);

        autoComInvestigationName.setThreshold(1);//will start working from first character
        autoComInvestigationName.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //tvReqDropdown.setTextColor(Color.RED);

        autoComInvestigationName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoComInvestigationName.showDropDown();
                return false;
            }
        });
        autoComInvestigationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String analysisName = adapterView.getItemAtPosition(position).toString();
                if (!investigationList.contains((Analysis)map.get(analysisName))){
                    Analysis analysis =(Analysis)map.get(analysisName);
                    investigationList.add(analysis);
                    InvestigationListAdapter adapter = new InvestigationListAdapter(context, R.layout.investigation_list_items, investigationList);
                    listViewInvestigationName.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getValueFromListView();
                }else {
                    Toast.makeText(context, " Already Exist !!!", Toast.LENGTH_SHORT).show();
                }

                /*listViewInvestigationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });*/
                autoComInvestigationName.setText("");
            }

        });
    }

    public void getValueFromListView() {
            //ArrayList<Analysis> analysisesList = new ArrayList<Analysis>();
            InvestigationListAdapter adapter = new InvestigationListAdapter(context, R.layout.investigation_list_items, investigationList) {
                @Override
                public ArrayList<Analysis> upDatedList() {
                    return super.upDatedList();
                }
            };
            PrescriptionInfo.analysisesList = adapter.upDatedList();
            /*Log.d(TAG,"======== investigation 1=============");
            for(Analysis analysis : PrescriptionInfo.analysisesList){
                Log.d(TAG,analysis.getT_analysis_name());
            }*/
    }
}
