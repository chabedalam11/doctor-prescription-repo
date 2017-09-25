package com.prescription.doctorprescription.webService.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.Patient;

import java.util.List;


public class PatientCollection {
    @SerializedName("data")
    @Expose
    public List<Patient> data;
}
