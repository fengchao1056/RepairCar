package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetUserMasterStoreStatusBean {







    /**
     * code : 2000
     * data : [{"add_time":"1560928522","business_phone":"18518029970","claim_time":"0","gname":"测试门店","id":"3","master_uid":"300","status":"0","store_uid":"300","type":"2"}]
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
         * add_time : 1560928522
         * business_phone : 18518029970
         * claim_time : 0
         * gname : 测试门店
         * id : 3
         * master_uid : 300
         * status : 0
         * store_uid : 300
         * type : 2
         */

        private String add_time;
        private String business_phone;
        private String claim_time;
        private String gname;
        private String id;
        private String master_uid;
        private String status;
        private String store_uid;
        private String type;
        private String img_url;
        private String area_x;
        private String area_y;

        public String getArea_x() {
            return area_x;
        }

        public void setArea_x(String area_x) {
            this.area_x = area_x;
        }

        public String getArea_y() {
            return area_y;
        }

        public void setArea_y(String area_y) {
            this.area_y = area_y;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getBusiness_phone() {
            return business_phone;
        }

        public void setBusiness_phone(String business_phone) {
            this.business_phone = business_phone;
        }

        public String getClaim_time() {
            return claim_time;
        }

        public void setClaim_time(String claim_time) {
            this.claim_time = claim_time;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaster_uid() {
            return master_uid;
        }

        public void setMaster_uid(String master_uid) {
            this.master_uid = master_uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStore_uid() {
            return store_uid;
        }

        public void setStore_uid(String store_uid) {
            this.store_uid = store_uid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
