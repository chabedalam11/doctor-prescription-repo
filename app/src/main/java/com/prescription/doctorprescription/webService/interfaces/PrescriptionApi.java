package com.prescription.doctorprescription.webService.interfaces;

import com.prescription.doctorprescription.webService.collection.DocClinicInfoCollection;
import com.prescription.doctorprescription.webService.collection.DoctorLoginCollection;
import com.prescription.doctorprescription.webService.collection.MessegeCollection;

import retrofit2.Call;
import retrofit2.http.GET;
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
    //designation
    @GET("insertSignUpInfo")
    Call<MessegeCollection> insertDocDesignation(
            @Query("t_doc_id") String t_doc_id, @Query("t_desig_name") String t_desig_name,
            @Query("other") String other
    );

    //clinic
    @GET("insertDocClinic")
    Call<MessegeCollection> insertDocClinic(
            @Query("t_doc_id") String t_doc_id, @Query("t_clinic_address") String t_clinic_address,
            @Query("t_clinic_visit_day") String t_clinic_visit_day, @Query("t_clinic_visit_time1") String t_clinic_visit_time1,
            @Query("t_clinic_visit_time2") String t_clinic_visit_time2, @Query("t_clinic_mobile") String t_clinic_mobile,
            @Query("t_clinic_other") String t_clinic_other
    );


    //clinic
    @GET("upDateDocClinic")
    Call<MessegeCollection> upDateDocClinic(
            @Query("t_clinic_id") String t_clinic_id,@Query("t_doc_id") String t_doc_id, @Query("t_clinic_address") String t_clinic_address,
            @Query("t_clinic_visit_day") String t_clinic_visit_day, @Query("t_clinic_visit_time1") String t_clinic_visit_time1,
            @Query("t_clinic_visit_time2") String t_clinic_visit_time2, @Query("t_clinic_mobile") String t_clinic_mobile,
            @Query("t_clinic_other") String t_clinic_other
    );

    @GET("deleteDocClinic")
    Call<MessegeCollection> deleteDocClinic(
            @Query("t_clinic_id") String t_clinic_id,@Query("t_doc_id") String t_doc_id
    );

    //login
    @GET("getDocClinicByDocId")
    Call<DocClinicInfoCollection> getDocClinicByDocId(@Query("t_doc_id") String t_doc_id);

}
