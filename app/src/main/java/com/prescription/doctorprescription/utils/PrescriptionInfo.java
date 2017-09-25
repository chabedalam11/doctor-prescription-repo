package com.prescription.doctorprescription.utils;

import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.DrugMaster;
import com.prescription.doctorprescription.webService.model.Patient;

import java.util.ArrayList;

/**
 * Created by medisys on 17-Aug-17.
 */

public class PrescriptionInfo {
    public static Patient patientInfo;

    public static String chiefcomplaint="";
    public static String patientPalse="";
    public static String patientBloodPressure="";
    public static String patientTemprature="";
    public static String patientResp="";
    public static String patientExmineOther="";

    public static ArrayList<Analysis> analysisesList;
    public static ArrayList<DrugMaster> drugMasterList;

    public static String advice="";
    public static String nextVisit="";
    public static String t_pres_id="";

    public void clearField(){
         //patientInfo=null;

        chiefcomplaint="";


        patientPalse="";
        patientBloodPressure="";
        patientTemprature="";
        patientResp="";
        patientExmineOther="";

        analysisesList=null;
        drugMasterList=null;

        advice="";
        nextVisit="";
        t_pres_id="";
    }
}
