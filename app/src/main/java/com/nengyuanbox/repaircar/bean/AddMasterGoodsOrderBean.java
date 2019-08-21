package com.nengyuanbox.repaircar.bean;

public class AddMasterGoodsOrderBean {


    /**
     * code : 2000
     * data : {"addtime":1557371376,"name":"购买商品","order_sn":"GHX2019050971376646","order_type":2,"pay_type":0,"price":"42.00","uid":"20","vip_money":"42.00"}
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
         * addtime : 1557371376
         * name : 购买商品
         * order_sn : GHX2019050971376646
         * order_type : 2
         * pay_type : 0
         * price : 42.00
         * uid : 20
         * vip_money : 42.00
         */

        private int addtime;
        private String name;
        private String order_sn;
        private int order_type;
        private int pay_type;
        private String price;
        private String uid;
        private String vip_money;

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
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

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
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

        public String getVip_money() {
            return vip_money;
        }

        public void setVip_money(String vip_money) {
            this.vip_money = vip_money;
        }
    }
}
