package com.nengyuanbox.repaircar.bean;

public class GetStoreInfoBean {


    /**
     * code : 2000
     * data : {"business_phone":"18518029970","gname":"测试门店","group_car_name":"    ","id":"315","main_items":"电动车维修店","star":"0.0","store_area_x":"40.05032","store_area_y":"116.299454","store_img":"http://lxcapimoni.nengyuanbox.com/Public/Upload/2019-06-17/small_5d072f5c33ae2.jpg","uid":"300"}
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
         * business_phone : 18518029970
         * gname : 测试门店
         * group_car_name :
         * id : 315
         * main_items : 电动车维修店
         * star : 0.0
         * store_area_x : 40.05032
         * store_area_y : 116.299454
         * store_img : http://lxcapimoni.nengyuanbox.com/Public/Upload/2019-06-17/small_5d072f5c33ae2.jpg
         * uid : 300
         */

        private String business_phone;
        private String gname;
        private String group_car_name;
        private String id;
        private String main_items;
        private String star;
        private String store_area_x;
        private String store_area_y;
        private String store_img;
        private String uid;
        private String main_items_name;
        private String store_phone;

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getMain_items_name() {
            return main_items_name;
        }

        public void setMain_items_name(String main_items_name) {
            this.main_items_name = main_items_name;
        }

        public String getBusiness_phone() {
            return business_phone;
        }

        public void setBusiness_phone(String business_phone) {
            this.business_phone = business_phone;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getGroup_car_name() {
            return group_car_name;
        }

        public void setGroup_car_name(String group_car_name) {
            this.group_car_name = group_car_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMain_items() {
            return main_items;
        }

        public void setMain_items(String main_items) {
            this.main_items = main_items;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getStore_area_x() {
            return store_area_x;
        }

        public void setStore_area_x(String store_area_x) {
            this.store_area_x = store_area_x;
        }

        public String getStore_area_y() {
            return store_area_y;
        }

        public void setStore_area_y(String store_area_y) {
            this.store_area_y = store_area_y;
        }

        public String getStore_img() {
            return store_img;
        }

        public void setStore_img(String store_img) {
            this.store_img = store_img;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
