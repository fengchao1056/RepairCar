package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetServiceBean {

    /**
     * code : 2000
     * data : [{"id":"1","name":"电动车"},{"id":"2","name":"自行车"},{"id":"3","name":"摩托车"},{"id":"4","name":"汽车"},{"id":"5","name":"电动汽车"}]
     * message : 请求成功
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 电动车
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
