package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;

/**
 * Created by medisys on 8/13/2017.
 */

public class Patient implements Serializable {


    String t_pat_id;
    String t_doc_id;
    String t_pat_name;
    String t_pat_age;
    String t_pat_sex;
    String t_pat_address;
    String t_pat_mobile;
    String t_pat_email;
    String t_pat_marital;
    String t_pat_entry_date;
    String t_pat_f_name;


    public String getT_pat_id() {
        return t_pat_id;
    }

    public void setT_pat_id(String t_pat_id) {
        this.t_pat_id = t_pat_id;
    }

    public String getT_doc_id() {
        return t_doc_id;
    }

    public void setT_doc_id(String t_doc_id) {
        this.t_doc_id = t_doc_id;
    }

    public String getT_pat_name() {
        return t_pat_name;
    }

    public void setT_pat_name(String t_pat_name) {
        this.t_pat_name = t_pat_name;
    }

    public String getT_pat_age() {
        return t_pat_age;
    }

    public void setT_pat_age(String t_pat_age) {
        this.t_pat_age = t_pat_age;
    }

    public String getT_pat_sex() {
        return t_pat_sex;
    }

    public void setT_pat_sex(String t_pat_sex) {
        this.t_pat_sex = t_pat_sex;
    }

    public String getT_pat_address() {
        return t_pat_address;
    }

    public void setT_pat_address(String t_pat_address) {
        this.t_pat_address = t_pat_address;
    }

    public String getT_pat_mobile() {
        return t_pat_mobile;
    }

    public void setT_pat_mobile(String t_pat_mobile) {
        this.t_pat_mobile = t_pat_mobile;
    }

    public String getT_pat_email() {
        return t_pat_email;
    }

    public void setT_pat_email(String t_pat_email) {
        this.t_pat_email = t_pat_email;
    }

    public String getT_pat_marital() {
        return t_pat_marital;
    }

    public void setT_pat_marital(String t_pat_marital) {
        this.t_pat_marital = t_pat_marital;
    }

    public String getT_pat_entry_date() {
        return t_pat_entry_date;
    }

    public void setT_pat_entry_date(String t_pat_entry_date) {
        this.t_pat_entry_date = t_pat_entry_date;
    }

    public String getT_pat_f_name() {
        return t_pat_f_name;
    }

    public void setT_pat_f_name(String t_pat_f_name) {
        this.t_pat_f_name = t_pat_f_name;
    }

    @Override
    public String toString() {
        return t_pat_name;
    }
}
