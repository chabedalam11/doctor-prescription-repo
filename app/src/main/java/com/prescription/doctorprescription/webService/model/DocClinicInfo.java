package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;

/**
 * Created by medisys on 4/20/2017.
 */

public class DocClinicInfo implements Serializable {


    String t_clinic_id;
    String t_doc_id;
    String t_clinic_address;
    String t_clinic_visit_day;
    String t_clinic_visit_time1;
    String t_clinic_visit_time2;
    String t_clinic_mobile;
    String t_clinic_other;


    public String getT_clinic_id() {
        return t_clinic_id;
    }

    public void setT_clinic_id(String t_clinic_id) {
        this.t_clinic_id = t_clinic_id;
    }

    public String getT_doc_id() {
        return t_doc_id;
    }

    public void setT_doc_id(String t_doc_id) {
        this.t_doc_id = t_doc_id;
    }

    public String getT_clinic_address() {
        return t_clinic_address;
    }

    public void setT_clinic_address(String t_clinic_address) {
        this.t_clinic_address = t_clinic_address;
    }

    public String getT_clinic_visit_day() {
        return t_clinic_visit_day;
    }

    public void setT_clinic_visit_day(String t_clinic_visit_day) {
        this.t_clinic_visit_day = t_clinic_visit_day;
    }

    public String getT_clinic_visit_time1() {
        return t_clinic_visit_time1;
    }

    public void setT_clinic_visit_time1(String t_clinic_visit_time1) {
        this.t_clinic_visit_time1 = t_clinic_visit_time1;
    }

    public String getT_clinic_visit_time2() {
        return t_clinic_visit_time2;
    }

    public void setT_clinic_visit_time2(String t_clinic_visit_time2) {
        this.t_clinic_visit_time2 = t_clinic_visit_time2;
    }

    public String getT_clinic_mobile() {
        return t_clinic_mobile;
    }

    public void setT_clinic_mobile(String t_clinic_mobile) {
        this.t_clinic_mobile = t_clinic_mobile;
    }

    public String getT_clinic_other() {
        return t_clinic_other;
    }

    public void setT_clinic_other(String t_clinic_other) {
        this.t_clinic_other = t_clinic_other;
    }

    @Override
    public String toString() {
        return t_doc_id;
    }

}
