package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;

/**
 * Created by medisys on 8/13/2017.
 */

public class DrugMaster implements Serializable {


    String t_um;
    String t_medicine_name;
    String t_strength;
    String t_dose_time;
    String t_duration;
    String t_advice;

    public String getT_um() {
        return t_um;
    }

    public void setT_um(String t_um) {
        this.t_um = t_um;
    }

    public String getT_medicine_name() {
        return t_medicine_name;
    }

    public void setT_medicine_name(String t_medicine_name) {
        this.t_medicine_name = t_medicine_name;
    }

    public String getT_strength() {
        return t_strength;
    }

    public void setT_strength(String t_strength) {
        this.t_strength = t_strength;
    }

    public String getT_dose_time() {
        return t_dose_time;
    }

    public void setT_dose_time(String t_dose_time) {
        this.t_dose_time = t_dose_time;
    }

    public String getT_duration() {
        return t_duration;
    }

    public void setT_duration(String t_duration) {
        this.t_duration = t_duration;
    }

    public String getT_advice() {
        return t_advice;
    }

    public void setT_advice(String t_advice) {
        this.t_advice = t_advice;
    }

    @Override
    public String toString() {
        return t_medicine_name;
    }
}
