package com.prescription.doctorprescription.webService.model;

import java.io.Serializable;

/**
 * Created by medisys on 4/20/2017.
 */

public class Message implements Serializable {

    String msgString;

    public String getMsgString() {
        return msgString;
    }

    public void setMsgString(String msgString) {
        this.msgString = msgString;
    }

    @Override
    public String toString() {
        return msgString;
    }

}
