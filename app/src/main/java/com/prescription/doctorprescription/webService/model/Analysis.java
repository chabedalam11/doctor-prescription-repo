package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;

/**
 * Created by medisys on 8/13/2017.
 */

public class Analysis implements Serializable {


    String t_analysis_code;
    String t_analysis_name;
    String t_entry_user;
    String t_entry_date;
    String t_upd_user;
    String t_upd_date;

    public String getT_analysis_code() {
        return t_analysis_code;
    }

    public void setT_analysis_code(String t_analysis_code) {
        this.t_analysis_code = t_analysis_code;
    }

    public String getT_analysis_name() {
        return t_analysis_name;
    }

    public void setT_analysis_name(String t_analysis_name) {
        this.t_analysis_name = t_analysis_name;
    }

    public String getT_entry_user() {
        return t_entry_user;
    }

    public void setT_entry_user(String t_entry_user) {
        this.t_entry_user = t_entry_user;
    }

    public String getT_entry_date() {
        return t_entry_date;
    }

    public void setT_entry_date(String t_entry_date) {
        this.t_entry_date = t_entry_date;
    }

    public String getT_upd_user() {
        return t_upd_user;
    }

    public void setT_upd_user(String t_upd_user) {
        this.t_upd_user = t_upd_user;
    }

    public String getT_upd_date() {
        return t_upd_date;
    }

    public void setT_upd_date(String t_upd_date) {
        this.t_upd_date = t_upd_date;
    }

    @Override
    public String toString() {
        return t_analysis_code;
    }
}
