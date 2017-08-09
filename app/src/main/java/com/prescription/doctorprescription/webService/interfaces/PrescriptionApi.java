package com.prescription.doctorprescription.webService.interfaces;

import com.prescription.doctorprescription.webService.collection.DoctorLoginCollection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;




public interface PrescriptionApi {
    @GET("doctorLogin")
    Call<DoctorLoginCollection> getDoctorLoginInfo(@Query("t_doc_email") String username, @Query("t_doc_passphrase") String password);
}
