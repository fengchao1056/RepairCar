package com.nengyuanbox.repaircar.bean;

import java.io.Serializable;

public class StoreResidenceBean implements Serializable {


    /**
     * code : 2000
     * message : 请求成功
     * data : {"order_num":"MRZ2019041327200363","name":"门店入驻","price":620,"pay_type":0,"addtime":1555127200,"order_type":1,"uid":"27"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_num : MRZ2019041327200363
         * name : 门店入驻
         * price : 620
         * pay_type : 0
         * addtime : 1555127200
         * order_type : 1
         * uid : 27
         */

        private String order_sn;
        private String name;
        private double price;
        private int pay_type;
        private int addtime;
        private int order_type;
        private String uid;
        private String vip_money;

        public String getVip_money() {
            return vip_money;
        }

        public void setVip_money(String vip_money) {
            this.vip_money = vip_money;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
