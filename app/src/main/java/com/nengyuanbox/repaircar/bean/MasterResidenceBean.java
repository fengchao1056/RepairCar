package com.nengyuanbox.repaircar.bean;
//师傅入驻第一步实体bean

public class MasterResidenceBean {


    /**
     * code : 2000
     * data : {"addtime":"1557813507","desc":"","id":"296","name":"师傅入驻","order_sn":"SRZ2019051413507937","order_type":"1","pay_type":"0","price":"120.00","uid":"161","user_type":"1","vip_money":"0.01"}
     * message : 请求成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * addtime : 1557813507
         * desc :
         * id : 296
         * name : 师傅入驻
         * order_sn : SRZ2019051413507937
         * order_type : 1
         * pay_type : 0
         * price : 120.00
         * uid : 161
         * user_type : 1
         * vip_money : 0.01
         */

        private String addtime;
        private String desc;
        private String id;
        private String name;
        private String order_sn;
        private String order_type;
        private String pay_type;
        private String price;
        private String uid;
        private String user_type;
        private String vip_money;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

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

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getVip_money() {
            return vip_money;
        }

        public void setVip_money(String vip_money) {
            this.vip_money = vip_money;
        }
    }
}
