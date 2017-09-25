package com.prescription.doctorprescription.webService.interfaces;


import com.prescription.doctorprescription.webService.collection.AnalysisCollection;
import com.prescription.doctorprescription.webService.collection.DocClinicInfoCollection;
import com.prescription.doctorprescription.webService.collection.DesignationInfoCollection;
import com.prescription.doctorprescription.webService.collection.DoctorLoginCollection;
import com.prescription.doctorprescription.webService.collection.MedicineInfoCollection;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;
import com.prescription.doctorprescription.webService.collection.PatientCollection;
import com.prescription.doctorprescription.webService.collection.PrescriptionCollection;
import com.prescription.doctorprescription.webService.model.DrugMaster;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;




public interface PrescriptionApi {

    //signUP
    @GET("insertSignUpInfo")
    Call<MessegeCollection> insertSignUpInfo(
            @Query("t_doc_first_name") String t_doc_first_name, @Query("t_doc_middle_name") String t_doc_middle_name,
            @Query("t_doc_last_name") String t_doc_last_name, @Query("t_doc_birth_day") String t_doc_birth_day,
            @Query("t_doc_gender") String t_doc_gender, @Query("t_doc_nation") String t_doc_nation,
            @Query("t_doc_phone1") String t_doc_phone1, @Query("t_doc_phone2") String t_doc_phone2,
            @Query("t_doc_phone3") String t_doc_phone3, @Query("t_doc_passphrase") String t_doc_passphrase,
            @Query("t_doc_email") String t_doc_email,@Query("t_doc_address") String t_doc_address
            );

    //login
    @GET("doctorLogin")
    Call<DoctorLoginCollection> getDoctorLoginInfo(@Query("t_doc_email") String username, @Query("t_doc_passphrase") String password);

    //=========================Profile================================================


    //=========================clinic================================================
    //insert clinic
    @GET("insertDocClinic")
    Call<MessegeCollection> insertDocClinic(
            @Query("t_doc_id") String t_doc_id, @Query("t_clinic_address") String t_clinic_address,
            @Query("t_clinic_visit_day") String t_clinic_visit_day, @Query("t_clinic_visit_time1") String t_clinic_visit_time1,
            @Query("t_clinic_visit_time2") String t_clinic_visit_time2, @Query("t_clinic_mobile") String t_clinic_mobile,
            @Query("t_clinic_other") String t_clinic_other
    );


    //update clinic
    @GET("upDateDocClinic")
    Call<MessegeCollection> upDateDocClinic(
            @Query("t_clinic_id") String t_clinic_id,@Query("t_doc_id") String t_doc_id, @Query("t_clinic_address") String t_clinic_address,
            @Query("t_clinic_visit_day") String t_clinic_visit_day, @Query("t_clinic_visit_time1") String t_clinic_visit_time1,
            @Query("t_clinic_visit_time2") String t_clinic_visit_time2, @Query("t_clinic_mobile") String t_clinic_mobile,
            @Query("t_clinic_other") String t_clinic_other
    );

    //delete clinic
    @GET("deleteDocClinic")
    Call<MessegeCollection> deleteDocClinic(
            @Query("t_clinic_id") String t_clinic_id,@Query("t_doc_id") String t_doc_id
    );

    //get clinic
    @GET("getDocClinicByDocId")
    Call<DocClinicInfoCollection> getDocClinicByDocId(@Query("t_doc_id") String t_doc_id);

    //=========================Designation================================================

    //designation insert
    @GET("insertDocDesignation")
    Call<MessegeCollection> insertDocDesignation(
            @Query("t_doc_id") String t_doc_id, @Query("t_desig_name") String t_desig_name,
            @Query("other") String other
    );

    //designation update
    @GET("updateDocDesignation")
    Call<MessegeCollection> updateDocDesignation(
            @Query("t_desig_id") String t_desig_id, @Query("t_doc_id") String t_doc_id,
            @Query("t_desig_name") String t_desig_name,@Query("other") String other
    );

 //designation delete
    @GET("deleteDocDesignation")
    Call<MessegeCollection> deleteDocDesignation(
            @Query("t_desig_id") String t_desig_id, @Query("t_doc_id") String t_doc_id
    );

    //designation getDocDesignationByDocId
    @GET("getDocDesignationByDocId")
    Call<DesignationInfoCollection> getDocDesignationByDocId(@Query("t_doc_id") String t_doc_id);

    //=========================Analysis================================================
    //analysis insert
    @GET("insertAnalysis")
    Call<MessegeCollection> insertAnalysis(
            @Query("t_analysis_name") String t_analysis_name,
            @Query("t_entry_user") String t_entry_user
    );
    //getAnalysisByDocId
    @GET("getAnalysisByDocId")
    Call<AnalysisCollection> getAnalysisByDocId(@Query("t_doc_id") String t_doc_id);


    //=========================Prescription================================================
    //getAnalysisByDocId
    @GET("getMedicineInfo")
    Call<MedicineInfoCollection> getMedicineInfo(@Query("t_doc_id") String t_doc_id);

    //insertPrescription
   @FormUrlEncoded
    @POST("insertPrescription")
    Call<MessegeCollection> insertPrescription(
           @Field("t_doc_id") String t_doc_id, @Field("t_pat_id") String t_pat_id,
           @Field("t_pres_date") String t_pres_date, @Field("t_pres_chief_complaints") String t_pres_chief_complaints,
           @Field("t_pres_pulse") String t_pres_pulse, @Field("t_pres_bp") String t_pres_bp,
           @Field("t_pres_temp") String t_pres_temp, @Field("t_pres_resp") String t_pres_resp,
           @Field("t_pres_other") String t_pres_other, @Field("t_analysis_code") String t_analysis_code,
           @Field("t_pres_advice") String t_pres_advice, @Field("t_pres_next_visit") String t_pres_next_visit,
           @Field("t_pres_um") String t_pres_um, @Field("t_pres_med_name") String t_pres_med_name,
           @Field("t_pres_strength") String t_pres_strength, @Field("t_pres_dose_time") String t_pres_dose_time,
           @Field("t_pres_duration") String t_pres_duration, @Field("t_pres_hints") String t_pres_hints
    );

    //updatePrescription
    @FormUrlEncoded
    @POST("updatePrescription")
    Call<MessegeCollection> updatePrescription(
            @Field("t_pres_id") String t_pres_id, @Field("t_pres_chief_complaints") String t_pres_chief_complaints,
            @Field("t_pres_pulse") String t_pres_pulse, @Field("t_pres_bp") String t_pres_bp,
            @Field("t_pres_temp") String t_pres_temp, @Field("t_pres_resp") String t_pres_resp,
            @Field("t_analysis_code") String t_analysis_code,
            @Field("t_pres_advice") String t_pres_advice, @Field("t_pres_next_visit") String t_pres_next_visit,
            @Field("t_pres_um") String t_pres_um, @Field("t_pres_med_name") String t_pres_med_name,
            @Field("t_pres_strength") String t_pres_strength, @Field("t_pres_dose_time") String t_pres_dose_time,
            @Field("t_pres_duration") String t_pres_duration, @Field("t_pres_hints") String t_pres_hints
    );

    //delete prescription
    @GET("deletePrescription")
    Call<MessegeCollection> deletePrescription(@Query("t_pres_id") String t_pres_id,@Query("t_pat_id") String t_pat_id,@Query("t_doc_id") String t_doc_id);

    //getPrescriptionListByDocAndPatId
    @GET("getPrescriptionListByDocAndPatId")
    Call<PrescriptionCollection> getPrescriptionListByDocAndPatId(@Query("t_doc_id") String t_doc_id,@Query("t_pat_id") String t_pat_id);


    //=========================Patient================================================

    //patient insert
    @GET("insertPatient")
    Call<MessegeCollection> insertPatient(
            @Query("t_doc_id") String t_doc_id, @Query("t_pat_name") String t_pat_name,
            @Query("t_pat_age") String t_pat_age,@Query("t_pat_sex") String t_pat_sex,
            @Query("t_pat_address") String t_pat_address,@Query("t_pat_mobile") String t_pat_mobile,
            @Query("t_pat_email") String t_pat_email,@Query("t_pat_marital") String t_pat_marital,
            @Query("t_pat_entry_date") String t_pat_entry_date,@Query("t_pat_f_name") String t_pat_f_name
    );

    //getPatientByDocId
    @GET("getPatientByDocId")
    Call<PatientCollection> getPatientByDocId(@Query("t_doc_id") String t_doc_id);
}
