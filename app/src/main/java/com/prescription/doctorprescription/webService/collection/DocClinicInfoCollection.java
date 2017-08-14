package com.prescription.doctorprescription.webService.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;
import com.prescription.doctorprescription.webService.model.DoctorLogin;

import java.util.List;


public class DocClinicInfoCollection {
    @SerializedName("data")
    @Expose
    public List<DocClinicInfo> data;
}
