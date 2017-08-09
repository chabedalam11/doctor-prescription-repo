package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;

/**
 * Created by medisys on 4/20/2017.
 */

public class DoctorLogin implements Serializable {

    String t_doc_id;
    String t_doc_first_name;
    String t_doc_middle_name;
    String t_doc_last_name;
    String t_doc_birth_day;
    String t_doc_gender;
    String t_doc_nation;
    String t_doc_phone1;
    String t_doc_phone2;
    String t_doc_phone3;
    String t_doc_passphrase;
    String t_doc_email;
    String t_doc_phone_home;
    String t_doc_phone_office;
    String t_doc_marital;
    String t_doc_pic;

    public String getT_doc_id() {
        return t_doc_id;
    }

    public void setT_doc_id(String t_doc_id) {
        this.t_doc_id = t_doc_id;
    }

    public String getT_doc_first_name() {
        return t_doc_first_name;
    }

    public void setT_doc_first_name(String t_doc_first_name) {
        this.t_doc_first_name = t_doc_first_name;
    }

    public String getT_doc_middle_name() {
        return t_doc_middle_name;
    }

    public void setT_doc_middle_name(String t_doc_middle_name) {
        this.t_doc_middle_name = t_doc_middle_name;
    }

    public String getT_doc_last_name() {
        return t_doc_last_name;
    }

    public void setT_doc_last_name(String t_doc_last_name) {
        this.t_doc_last_name = t_doc_last_name;
    }

    public String getT_doc_birth_day() {
        return t_doc_birth_day;
    }

    public void setT_doc_birth_day(String t_doc_birth_day) {
        this.t_doc_birth_day = t_doc_birth_day;
    }

    public String getT_doc_gender() {
        return t_doc_gender;
    }

    public void setT_doc_gender(String t_doc_gender) {
        this.t_doc_gender = t_doc_gender;
    }

    public String getT_doc_nation() {
        return t_doc_nation;
    }

    public void setT_doc_nation(String t_doc_nation) {
        this.t_doc_nation = t_doc_nation;
    }

    public String getT_doc_phone1() {
        return t_doc_phone1;
    }

    public void setT_doc_phone1(String t_doc_phone1) {
        this.t_doc_phone1 = t_doc_phone1;
    }

    public String getT_doc_phone2() {
        return t_doc_phone2;
    }

    public void setT_doc_phone2(String t_doc_phone2) {
        this.t_doc_phone2 = t_doc_phone2;
    }

    public String getT_doc_phone3() {
        return t_doc_phone3;
    }

    public void setT_doc_phone3(String t_doc_phone3) {
        this.t_doc_phone3 = t_doc_phone3;
    }

    public String getT_doc_passphrase() {
        return t_doc_passphrase;
    }

    public void setT_doc_passphrase(String t_doc_passphrase) {
        this.t_doc_passphrase = t_doc_passphrase;
    }

    public String getT_doc_email() {
        return t_doc_email;
    }

    public void setT_doc_email(String t_doc_email) {
        this.t_doc_email = t_doc_email;
    }

    public String getT_doc_phone_home() {
        return t_doc_phone_home;
    }

    public void setT_doc_phone_home(String t_doc_phone_home) {
        this.t_doc_phone_home = t_doc_phone_home;
    }

    public String getT_doc_phone_office() {
        return t_doc_phone_office;
    }

    public void setT_doc_phone_office(String t_doc_phone_office) {
        this.t_doc_phone_office = t_doc_phone_office;
    }

    public String getT_doc_marital() {
        return t_doc_marital;
    }

    public void setT_doc_marital(String t_doc_marital) {
        this.t_doc_marital = t_doc_marital;
    }

    public String getT_doc_pic() {
        return t_doc_pic;
    }

    public void setT_doc_pic(String t_doc_pic) {
        this.t_doc_pic = t_doc_pic;
    }

    @Override
    public String toString() {
        return t_doc_id;
    }

}
