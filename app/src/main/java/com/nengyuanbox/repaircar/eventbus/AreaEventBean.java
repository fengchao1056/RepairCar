package com.nengyuanbox.repaircar.eventbus;

public class AreaEventBean {
    private  String area_id;
    private  String getMsg;


    public AreaEventBean(String area_id) {
        this.area_id = area_id;
    }

    public String getGetMsg() {
        return getMsg;
    }

    public void setGetMsg(String getMsg) {
        this.getMsg = getMsg;
    }

    public AreaEventBean(String area_id, String getMsg) {
        this.area_id = area_id;
        this.getMsg = getMsg;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }
}
