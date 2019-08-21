package com.nengyuanbox.repaircar.bean;

public class GetUserInfoBean {

    /**
     * code : 2000
     * data : {"gname":"丰超","group_money":"680.00","group_vip_money":"11.90","img_url":"http://thirdwx.qlogo.cn/mmopen/vi_32/zwnyvuRu3Ww2QbseEGBCbZsPsFyaHAKEL81AuOY7ia8QkxRP6rVvibjHsicI5AnRzyXUPsXb6fnWxdv10oK7ykBWQ/132","is_master":"1","is_store":"0","master_examine":"0","master_name":"晨曦吻过彩虹的脸庞","money":"0.00","name":"晨曦吻过彩虹的脸庞","order_num":"0","phone":"15903806000","star":"0.00","store_examine":"2","store_order_sn":"MRZ2019050802422109"}
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
         * gname : 丰超
         * group_money : 680.00
         * group_vip_money : 11.90
         * img_url : http://thirdwx.qlogo.cn/mmopen/vi_32/zwnyvuRu3Ww2QbseEGBCbZsPsFyaHAKEL81AuOY7ia8QkxRP6rVvibjHsicI5AnRzyXUPsXb6fnWxdv10oK7ykBWQ/132
         * is_master : 1
         * is_store : 0
         * master_examine : 0 4未填信息 1 .未支付2.未传身份证  未填写信息
         * master_name : 晨曦吻过彩虹的脸庞
         * money : 0.00
         * name : 晨曦吻过彩虹的脸庞
         * order_num : 0
         * phone : 15903806000
         * star : 0.00
         * store_examine : 0  5  填写信息  1.为上传营业执照 2未支付3 未审核
         * store_order_sn : MRZ2019050802422109
         */

        private String gname;
        private String group_money;
        private String group_vip_money;
        private String master_vip_money;
        private String master_money;
        private String img_url;
        private String is_master;
        private String is_store;
        private String master_examine;
        private String master_name;
        private String money;
        private String name;
        private String order_num;
        private String phone;
        private String star;
        private String store_examine;
        private String store_order_sn;
        private String master_order_sn;

        public String getMaster_order_sn() {
            return master_order_sn;
        }

        public void setMaster_order_sn(String master_order_sn) {
            this.master_order_sn = master_order_sn;
        }

        public String getMaster_vip_money() {
            return master_vip_money;
        }

        public void setMaster_vip_money(String master_vip_money) {
            this.master_vip_money = master_vip_money;
        }

        public String getMaster_money() {
            return master_money;
        }

        public void setMaster_money(String master_money) {
            this.master_money = master_money;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getGroup_money() {
            return group_money;
        }

        public void setGroup_money(String group_money) {
            this.group_money = group_money;
        }

        public String getGroup_vip_money() {
            return group_vip_money;
        }

        public void setGroup_vip_money(String group_vip_money) {
            this.group_vip_money = group_vip_money;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getIs_master() {
            return is_master;
        }

        public void setIs_master(String is_master) {
            this.is_master = is_master;
        }

        public String getIs_store() {
            return is_store;
        }

        public void setIs_store(String is_store) {
            this.is_store = is_store;
        }

        public String getMaster_examine() {
            return master_examine;
        }

        public void setMaster_examine(String master_examine) {
            this.master_examine = master_examine;
        }

        public String getMaster_name() {
            return master_name;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getStore_examine() {
            return store_examine;
        }

        public void setStore_examine(String store_examine) {
            this.store_examine = store_examine;
        }

        public String getStore_order_sn() {
            return store_order_sn;
        }

        public void setStore_order_sn(String store_order_sn) {
            this.store_order_sn = store_order_sn;
        }
    }
}
