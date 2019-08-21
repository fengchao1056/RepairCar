package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetCooperationListBean {


    /**
     * code : 2000
     * data : ["广告业务","配件供应商入驻","回收公司合作","申请城市运营商"]
     * message : 请求成功
     */

    private int code;
    private String message;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
