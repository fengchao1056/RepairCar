package com.nengyuanbox.repaircar.eventbus;

public class FinishEventBean {

    private String  name;
    private String address;
    private String areax;
    private String areay;

    public FinishEventBean(String name, String address, String areax, String areay) {
        this.name = name;
        this.address = address;
        this.areax = areax;
        this.areay = areay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreax() {
        return areax;
    }

    public void setAreax(String areax) {
        this.areax = areax;
    }

    public String getAreay() {
        return areay;
    }

    public void setAreay(String areay) {
        this.areay = areay;
    }

    public FinishEventBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
