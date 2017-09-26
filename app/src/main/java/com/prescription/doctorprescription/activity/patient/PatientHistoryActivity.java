package com.prescription.doctorprescription.activity.patient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.prescription.PrescriptionSetupActivity;
import com.prescription.doctorprescription.adapter.CustomExpandableListAdapter;
import com.prescription.doctorprescription.adapter.prescription.PrescriptionListAdapter;
import com.prescription.doctorprescription.utils.AlartFactory;
import com.prescription.doctorprescription.utils.PrescriptionInfo;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionPdf;
import com.prescription.doctorprescription.utils.PrescriptionPdfFooter;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.collection.PrescriptionCollection;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.DrugMaster;
import com.prescription.doctorprescription.webService.model.Message;
import com.prescription.doctorprescription.webService.model.Patient;
import com.prescription.doctorprescription.webService.model.Prescription;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientHistoryActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "PatientHistoryActivity";
    Context context;
    PrescriptionMemories memory;
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    String doctor_id;
    boolean hardwareBackControll;

    //UI component
    Button btnCreatePrescription;
    ListView lvPrescriptionList;

    //ui expendable list
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle = new ArrayList<String>();
    List<Patient> patientList = new ArrayList<>();
    HashMap<String, List<Patient>> expandableListDetail= new HashMap<String, List<Patient>>();

    //Intent value
    Patient patient;

    //array for action chooser
    private String[] actionList={"View Prescription","Edit Prescription","Remove Prescription"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);
        setTitle("Patient History");
        initialize();
    }

    private void initialize() {
        context = PatientHistoryActivity.this;
        memory = new PrescriptionMemories(context);
        doctor_id =memory.getPref(memory.KEY_DOC_ID);

        //Intent value
        patient=(Patient)getIntent().getSerializableExtra("patient");


        //UI component init
        btnCreatePrescription=(Button) findViewById(R.id.btnCreatePrescription);
        btnCreatePrescription.setOnClickListener(this);
        lvPrescriptionList=(ListView) findViewById(R.id.lvPrescriptionList);
        lvPrescriptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prescription prescription = (Prescription) adapterView.getItemAtPosition(i);

                showActionChooser(prescription);
            }
        });

        //method for expendableView
        setExpendableView();

        //get prescription list and set it in listView
        getPrescriptionListByDocAndPatId(doctor_id,patient.getT_pat_id());

    }

    private void setExpendableView(){
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        //expandableListDetail = ExpandableListDataPump.getData();
        //expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListTitle.add(patient.getT_pat_name());
        patientList.add(patient);
        expandableListDetail.put(patient.getT_pat_name(),patientList);
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });
    }

    //get prescription information ans set it in list view
    private void getPrescriptionListByDocAndPatId(String doctor_id,String t_pat_id){
       //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<PrescriptionCollection> getInfo = prescriptionApi.getPrescriptionListByDocAndPatId(doctor_id,t_pat_id);
        getInfo.enqueue(new Callback<PrescriptionCollection>() {
            @Override
            public void onResponse(Call<PrescriptionCollection> call, Response<PrescriptionCollection> response) {
                try {
                    List<Prescription> info = response.body().data;
                    Log.d(TAG, "getPrescriptionListByDocAndPatId :: " + info);
                    if (info.size()>=0){
                        ArrayList<Prescription> prescriptionArrayList = new ArrayList<Prescription>();
                        for (Prescription prescription: info){
                            prescriptionArrayList.add(prescription);
                        }
                        PrescriptionListAdapter adapter = new PrescriptionListAdapter(context, R.layout.prescription_list_items, prescriptionArrayList);
                        lvPrescriptionList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


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
            public void onFailure(Call<PrescriptionCollection> call, Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
                //Hide Dialog
                PrescriptionUtils.hideProgressDialog();
                AlartFactory.showWebServieErrorDialog(context, "Sorry !!!!", "Web Service is not running please contract with development team", false);
            }
        });

    }



    private void showActionChooser(final  Prescription prescription){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(actionList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        /*Intent intent = new Intent(context, ClinicSetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("docClinicInfo",docClinicInfo);
                        startActivity(intent);*/
                        Toast.makeText(context, "open", Toast.LENGTH_SHORT).show();
                        createPdf();
                        break;
                    case 1:
                        Intent intent = new Intent(context, PrescriptionSetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("actionType","update");
                        PrescriptionInfo.patientInfo=patient;
                        PrescriptionInfo.t_pres_id=prescription.getT_pres_id();
                        PrescriptionInfo.chiefcomplaint=prescription.getT_pres_chief_complaints();
                        PrescriptionInfo.patientPalse=prescription.getT_pres_pulse();
                        PrescriptionInfo.patientBloodPressure=prescription.getT_pres_bp();
                        PrescriptionInfo.patientTemprature=prescription.getT_pres_temp();
                        PrescriptionInfo.patientResp=prescription.getT_pres_resp();

                        //get analysisinfo and make a List by it
                        String analysisInfo =prescription.getT_analysis_code();
                        Log.d(TAG,analysisInfo);
                        String [] part = analysisInfo.split("\\|\\|\\|");
                        //Log.d(TAG,"part "+part[0]+" and "+part[1]);
                        String [] analysisCode=part[0].split(",");
                        String [] analysisName=null;
                        Log.d(TAG,part.length+"");
                        if(part.length>1){
                            analysisName=part[1].split("\\$");
                        }
                        ArrayList<Analysis> analysisesList=new ArrayList<Analysis>();
                        if(analysisName!=null){
                            for(int i =0; i<analysisName.length;i++){
                                Analysis analysis = new Analysis();
                                Log.d(TAG,"code : "+analysisCode[i]+"name : "+analysisName[i]);
                                analysis.setT_analysis_code(analysisCode[i]);
                                analysis.setT_analysis_name(analysisName[i]);
                                analysisesList.add(analysis);
                            }
                        }
                        PrescriptionInfo.analysisesList=analysisesList;
                        //get analysis info and make a List by it
                        String [] fullMedNames =prescription.getT_pres_med_name().split("\\|");;
                        String [] ums =prescription.getT_pres_um().split("\\|");;
                        String [] strength =prescription.getT_pres_strength().split("\\|");;
                        String [] doseTimes =prescription.getT_pres_dose_time().split("\\|");;
                        String [] durations =prescription.getT_pres_duration().split("\\|");;
                        String [] hints =prescription.getT_pres_hints().split("\\|");;

                        /*List medNameList = (List) Arrays.asList(fullMedNames);
                        List umList = (List) Arrays.asList(ums);*/

                        //display elements of List
                        Log.d(TAG,"String array converted to List");
                        ArrayList<DrugMaster> drugMasterList= new ArrayList<DrugMaster>();
                        for(int i=0; i < fullMedNames.length; i++){
                            Log.d(TAG,"value : "+fullMedNames[i]);
                            if(!fullMedNames[i].equals("")){
                                DrugMaster drugMaster = new DrugMaster();

                                drugMaster.setT_medicine_name(fullMedNames[i]);
                                drugMaster.setT_um(ums[i]);
                                drugMaster.setT_strength(strength[i]);
                                drugMaster.setT_dose_time(doseTimes[i]);
                                drugMaster.setT_duration(durations[i]);
                                drugMaster.setT_advice(hints[i]);

                                drugMasterList.add(drugMaster);
                            }
                        }


                        /*Log.d(TAG,"medname L "+fullMedNames.length);
                        Log.d(TAG,"um L "+ums.length);
                        Log.d(TAG,"stre L "+strength.length);
                        Log.d(TAG,"dosetime L "+doseTimes.length);
                        Log.d(TAG,"duration L "+durations.length);
                        Log.d(TAG,"hints L "+hints.length);*/

                        /*ArrayList<DrugMaster> drugMasterList= new ArrayList<DrugMaster>();
                        Log.d(TAG,"fullMedNames.length"+fullMedNames.length);
                        //if(fullMedNames.length !=0){
                            for(int i =0; i<fullMedNames.length;i++){
                                DrugMaster drugMaster = new DrugMaster();
                                if(fullMedNames.length !=0){
                                drugMaster.setT_medicine_name(fullMedNames[i]);
                                Log.d(TAG,"med name : "+fullMedNames[i]);
                                }
                                if(ums.length !=i){
                                drugMaster.setT_um(ums[i]);
                                }
                                if(strength.length !=i){
                                drugMaster.setT_strength(strength[i]);
                                }
                                if(doseTimes.length !=i){
                                drugMaster.setT_dose_time(doseTimes[i]);
                                }
                                if(durations.length !=i){
                                drugMaster.setT_duration(durations[i]);
                                }
                                if(hints.length !=i){
                                drugMaster.setT_advice(hints[i]);
                                }
                                if(!fullMedNames[0].equals("")){
                                Log.d(TAG,"add one");
                                drugMasterList.add(drugMaster);
                                }
                            }
                        //}*/

                        PrescriptionInfo.drugMasterList=drugMasterList;
                        PrescriptionInfo.nextVisit=prescription.getT_pres_next_visit();
                        PrescriptionInfo.advice=prescription.getT_pres_advice();

                        startActivity(intent);
                        //Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        new android.support.v7.app.AlertDialog.Builder(context)
                                .setTitle("Warning !!")
                                .setMessage("Are you sure you want to delete?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deletePrescription(prescription.getT_pres_id(),prescription.getT_pat_id()
                                        ,prescription.getT_doc_id());
                                    }
                        }).create().show();
                        //Toast.makeText(context, "HO", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.setTitle("Choose An Action");
        builder.create().show();
    }


    //delete prescription
    private void deletePrescription(String t_pres_id,String t_pat_id,String t_doc_id){
        //show loader
        PrescriptionUtils.showProgressDialog(context);
        Call<MessegeCollection> getInfo = prescriptionApi.deletePrescription(t_pres_id,t_pat_id,t_doc_id);
        getInfo.enqueue(new Callback<MessegeCollection>() {
            @Override
            public void onResponse(Call<MessegeCollection> call, Response<MessegeCollection> response) {
                try {
                    List<Message> info = response.body().data;
                    Log.d(TAG, "getPrescriptionListByDocAndPatId :: " + info);
                    if (info.size()>0){

                        if (info.get(0).getMsgString().equals("1")){
                            //Hide Dialog
                            PrescriptionUtils.hideProgressDialog();
                            Toast.makeText(context, "prescription delete", Toast.LENGTH_SHORT).show();
                            //get prescription list and set it in listView
                            getPrescriptionListByDocAndPatId(doctor_id,patient.getT_pat_id());
                        }else {
                            //Hide Dialog
                            PrescriptionUtils.hideProgressDialog();
                            Toast.makeText(context, "not able to  delete", Toast.LENGTH_SHORT).show();
                        }

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



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreatePrescription:
                Intent intent= new Intent(context, PrescriptionSetupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("actionType","create");
                PrescriptionInfo.patientInfo=patient;
                startActivity(intent);
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
        PrescriptionUtils.backToPrevious(context, new ReviewPatientActivity());
    }



    public void createPdf() {

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DPA/reports";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "12345" + ".pdf");
            FileOutputStream fOut;
            PrescriptionPdf prescriptionPdf = new PrescriptionPdf();   //<<<<<<PrescriptionPdf>>>>>>>>
            fOut = new FileOutputStream(file);
            PdfWriter pw = PdfWriter.getInstance(doc, fOut);
            //contentByte = pw.getDirectContent();
            Rectangle rect = new Rectangle(40, 40, 550, 800);       // <<<< 40 to 30
            pw.setBoxSize("art", rect);
            pw.setPageEvent(new PrescriptionPdfFooter());
            // open the document
            doc.open();
            // add header
            // prescriptionPdf.addHeaderImg(doc, WelcomeActivity.this);
            prescriptionPdf.createPDF(doc, "12345");

            openPdf("DPA" + File.separator + "reports"+ File.separator + file.getName());
        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:"+ de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }
    }

    private void openPdf(String path) {
        Log.d("filePath ", path);
        File file = new File(Environment.getExternalStorageDirectory()+ File.separator + path);
        Uri uri = Uri.fromFile(file);
        Log.d("uri", uri.toString());

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Log.e("ERROR", "No PDF Viewer is found.!!!");
            System.out.println("No PDF Viewer is found.!!!");
            Toast.makeText(getApplicationContext(),"No PDF Viewer is found.",Toast.LENGTH_LONG).show();
        }
    }


}
