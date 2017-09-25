package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by medisys on 8/13/2017.
 */

public class MedicineInfo implements Serializable {


    ArrayList<String> um = new ArrayList<>();
    ArrayList<String> trade = new ArrayList<>();
    ArrayList<String> frequency = new ArrayList<>();


    public ArrayList<String> getUm() {
        return um;
    }

    public void setUm(ArrayList<String> um) {
        this.um = um;
    }

    public ArrayList<String> getTrade() {
        return trade;
    }

    public void setTrade(ArrayList<String> trade) {
        this.trade = trade;
    }

    public ArrayList<String> getFrequency() {
        return frequency;
    }

    public void setFrequency(ArrayList<String> frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return um.get(0);
    }
}
