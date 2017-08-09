package com.prescription.doctorprescription.webService.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prescription.doctorprescription.webService.model.DoctorLogin;

import java.util.List;


public class DoctorLoginCollection {
    @SerializedName("data")
    @Expose
    public List<DoctorLogin> data;
}
