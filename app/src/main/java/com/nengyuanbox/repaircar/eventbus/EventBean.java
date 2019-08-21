package com.nengyuanbox.repaircar.eventbus;

import java.io.Serializable;


public class EventBean implements Serializable {
    private String msg;
    private String adress;
    private String PID;

    private String CID;
    private String AID;
    public EventBean(String msg, String adress, String PID, String CID, String AID) {
        this.msg = msg;
        this.adress = adress;
        this.PID = PID;
        this.CID = CID;
        this.AID = AID;
    }
    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }


    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
