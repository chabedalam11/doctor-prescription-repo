package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;


public class Prescription implements Serializable {

    String  t_pres_id;
    String  t_doc_id;
    String  t_pat_id;
    String  t_pres_date;
    String  t_pres_chief_complaints;
    String  t_pres_pulse;
    String  t_pres_bp;
    String  t_pres_temp;
    String  t_pres_resp;
    String  t_pres_other;
    String  t_analysis_code;
    String  t_dragmaster_code;
    String  t_pres_advice;
    String  t_pres_next_visit;
    String  t_pres_time;
    String  t_pres_um;
    String  t_pres_med_name;
    String  t_pres_strength	;
    String  t_pres_dose_time;
    String  t_pres_duration;
    String  t_pres_hints;

    public String getT_pres_id() {
        return t_pres_id;
    }

    public void setT_pres_id(String t_pres_id) {
        this.t_pres_id = t_pres_id;
    }

    public String getT_doc_id() {
        return t_doc_id;
    }

    public void setT_doc_id(String t_doc_id) {
        this.t_doc_id = t_doc_id;
    }

    public String getT_pat_id() {
        return t_pat_id;
    }

    public void setT_pat_id(String t_pat_id) {
        this.t_pat_id = t_pat_id;
    }

    public String getT_pres_date() {
        return t_pres_date;
    }

    public void setT_pres_date(String t_pres_date) {
        this.t_pres_date = t_pres_date;
    }

    public String getT_pres_chief_complaints() {
        return t_pres_chief_complaints;
    }

    public void setT_pres_chief_complaints(String t_pres_chief_complaints) {
        this.t_pres_chief_complaints = t_pres_chief_complaints;
    }

    public String getT_pres_pulse() {
        return t_pres_pulse;
    }

    public void setT_pres_pulse(String t_pres_pulse) {
        this.t_pres_pulse = t_pres_pulse;
    }

    public String getT_pres_bp() {
        return t_pres_bp;
    }

    public void setT_pres_bp(String t_pres_bp) {
        this.t_pres_bp = t_pres_bp;
    }

    public String getT_pres_temp() {
        return t_pres_temp;
    }

    public void setT_pres_temp(String t_pres_temp) {
        this.t_pres_temp = t_pres_temp;
    }

    public String getT_pres_resp() {
        return t_pres_resp;
    }

    public void setT_pres_resp(String t_pres_resp) {
        this.t_pres_resp = t_pres_resp;
    }

    public String getT_pres_other() {
        return t_pres_other;
    }

    public void setT_pres_other(String t_pres_other) {
        this.t_pres_other = t_pres_other;
    }

    public String getT_analysis_code() {
        return t_analysis_code;
    }

    public void setT_analysis_code(String t_analysis_code) {
        this.t_analysis_code = t_analysis_code;
    }

    public String getT_dragmaster_code() {
        return t_dragmaster_code;
    }

    public void setT_dragmaster_code(String t_dragmaster_code) {
        this.t_dragmaster_code = t_dragmaster_code;
    }

    public String getT_pres_advice() {
        return t_pres_advice;
    }

    public void setT_pres_advice(String t_pres_advice) {
        this.t_pres_advice = t_pres_advice;
    }

    public String getT_pres_next_visit() {
        return t_pres_next_visit;
    }

    public void setT_pres_next_visit(String t_pres_next_visit) {
        this.t_pres_next_visit = t_pres_next_visit;
    }

    public String getT_pres_time() {
        return t_pres_time;
    }

    public void setT_pres_time(String t_pres_time) {
        this.t_pres_time = t_pres_time;
    }

    public String getT_pres_um() {
        return t_pres_um;
    }

    public void setT_pres_um(String t_pres_um) {
        this.t_pres_um = t_pres_um;
    }

    public String getT_pres_med_name() {
        return t_pres_med_name;
    }

    public void setT_pres_med_name(String t_pres_med_name) {
        this.t_pres_med_name = t_pres_med_name;
    }

    public String getT_pres_strength() {
        return t_pres_strength;
    }

    public void setT_pres_strength(String t_pres_strength) {
        this.t_pres_strength = t_pres_strength;
    }

    public String getT_pres_dose_time() {
        return t_pres_dose_time;
    }

    public void setT_pres_dose_time(String t_pres_dose_time) {
        this.t_pres_dose_time = t_pres_dose_time;
    }

    public String getT_pres_duration() {
        return t_pres_duration;
    }

    public void setT_pres_duration(String t_pres_duration) {
        this.t_pres_duration = t_pres_duration;
    }

    public String getT_pres_hints() {
        return t_pres_hints;
    }

    public void setT_pres_hints(String t_pres_hints) {
        this.t_pres_hints = t_pres_hints;
    }

    @Override
    public String toString() {
        return t_pres_id;
    }
}
