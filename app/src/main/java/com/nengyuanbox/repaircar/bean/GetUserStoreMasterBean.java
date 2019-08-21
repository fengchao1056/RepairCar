package com.nengyuanbox.repaircar.bean;

import java.util.List;

//我的师傅
public class GetUserStoreMasterBean {


    /**
     * code : 2000
     * data : [{"add_time":"1560825272","claim_time":"0","experience":"半年","id":"2","img_url":"http://thirdwx.qlogo.cn/mmopen/vi_32/kHoDdV15McWtAnO6x1LicTibib5eb2ia4bSEEO0RZJz1lqIbHvOJdIOu0XVLoqWwoTs1IYLGBQv62Ma8fvGIgXCTuA/132","master_uid":"301","name":"丰超","order_num":"0","phone":"15903806000","service":"","star":"2.50","status":"1","store_uid":"300","type":"2"}]
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
         * add_time : 1560825272
         * claim_time : 0
         * experience : 半年
         * id : 2
         * img_url : http://thirdwx.qlogo.cn/mmopen/vi_32/kHoDdV15McWtAnO6x1LicTibib5eb2ia4bSEEO0RZJz1lqIbHvOJdIOu0XVLoqWwoTs1IYLGBQv62Ma8fvGIgXCTuA/132
         * master_uid : 301
         * name : 丰超
         * order_num : 0
         * phone : 15903806000
         * service :
         * star : 2.50
         * status : 1
         * store_uid : 300
         * type : 2
         */

        private String add_time;
        private String claim_time;
        private String experience;
        private String id;
        private String img_url;
        private String master_uid;
        private String name;
        private String order_num;
        private String phone;
        private String service;
        private String star;
        private String status;
        private String store_uid;
        private String type;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getClaim_time() {
            return claim_time;
        }

        public void setClaim_time(String claim_time) {
            this.claim_time = claim_time;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getMaster_uid() {
            return master_uid;
        }

        public void setMaster_uid(String master_uid) {
            this.master_uid = master_uid;
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

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
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
