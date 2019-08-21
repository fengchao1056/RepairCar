package com.nengyuanbox.repaircar.bean;

public class MapNameBean {
    private  String name;
    private  String addr;
    private  String Latitude;
    private  String Longitude;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public MapNameBean(String name, String addr) {
        this.name = name;
        this.addr = addr;
    }

    public MapNameBean(String name, String addr, String latitude, String longitude) {
        this.name = name;
        this.addr = addr;
        Latitude = latitude;
        Longitude = longitude;
    }
}
