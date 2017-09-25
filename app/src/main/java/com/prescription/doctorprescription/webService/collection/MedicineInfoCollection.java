package com.prescription.doctorprescription.webService.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prescription.doctorprescription.webService.model.MedicineInfo;

import java.util.List;


public class MedicineInfoCollection {
    @SerializedName("data")
    @Expose
    public List<MedicineInfo> data;
}
