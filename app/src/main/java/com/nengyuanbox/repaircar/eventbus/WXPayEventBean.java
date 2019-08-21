package com.nengyuanbox.repaircar.eventbus;

public class WXPayEventBean {

    private String  ispay;

    public WXPayEventBean(String ispay) {
        this.ispay = ispay;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }
}
