package com.prescription.doctorprescription.webService.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prescription.doctorprescription.webService.model.DoctorLogin;
import com.prescription.doctorprescription.webService.model.Message;

import java.util.List;


public class MessegeCollection {
    @SerializedName("data")
    @Expose
    public List<Message> data;
}
