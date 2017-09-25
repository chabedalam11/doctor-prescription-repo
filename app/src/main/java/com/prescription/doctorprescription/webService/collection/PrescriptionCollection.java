package com.prescription.doctorprescription.webService.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.Prescription;

import java.util.List;


public class PrescriptionCollection {
    @SerializedName("data")
    @Expose
    public List<Prescription> data;
}
