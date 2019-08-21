package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetGoodsInfoBean {


    /**
     * code : 2000
     * data : {"bargain_store_id":"0","end_time":"0","goods_detail":"111","goods_id":"1","goods_img":["https://lxcadmin.nengyuanbox.com/Public/goodsImg/2019-04-30/small_5cc8400b2a0ec.jpg"],"goods_master_num":"14","goods_master_price":"42.00","goods_name":"超威电池48v12ah（4只/组）","goods_num":"198","goods_parameter":"1111","goods_price":"476.00","goods_shop_detail":"222","goods_sup_price":"320.00","goods_type":"1","goods_user_price":"288.00","id":"1","pay_num":0,"pay_user_num":0,"start_time":"0","sup_company":"华夏配件"}
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
         * bargain_store_id : 0
         * end_time : 0
         * goods_detail : 111
         * goods_id : 1
         * goods_img : ["https://lxcadmin.nengyuanbox.com/Public/goodsImg/2019-04-30/small_5cc8400b2a0ec.jpg"]
         * goods_master_num : 14
         * goods_master_price : 42.00
         * goods_name : 超威电池48v12ah（4只/组）
         * goods_num : 198
         * goods_parameter : 1111
         * goods_price : 476.00
         * goods_shop_detail : 222
         * goods_sup_price : 320.00
         * goods_type : 1
         * goods_user_price : 288.00
         * id : 1
         * pay_num : 0
         * pay_user_num : 0
         * start_time : 0
         * sup_company : 华夏配件
         */

        private String bargain_store_id;
        private String end_time;
        private String goods_detail;
        private String goods_id;
        private String goods_master_num;
        private String goods_master_price;
        private String goods_name;
        private String goods_num;
        private String goods_parameter;
        private String goods_price;
        private String goods_shop_detail;
        private String goods_sup_price;
        private String goods_type;
        private String goods_user_price;
        private String id;
        private int pay_num;
        private int pay_user_num;
        private String start_time;
        private String sup_company;
        private List<String> goods_img;

        public String getBargain_store_id() {
            return bargain_store_id;
        }

        public void setBargain_store_id(String bargain_store_id) {
            this.bargain_store_id = bargain_store_id;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getGoods_detail() {
            return goods_detail;
        }

        public void setGoods_detail(String goods_detail) {
            this.goods_detail = goods_detail;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_master_num() {
            return goods_master_num;
        }

        public void setGoods_master_num(String goods_master_num) {
            this.goods_master_num = goods_master_num;
        }

        public String getGoods_master_price() {
            return goods_master_price;
        }

        public void setGoods_master_price(String goods_master_price) {
            this.goods_master_price = goods_master_price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_parameter() {
            return goods_parameter;
        }

        public void setGoods_parameter(String goods_parameter) {
            this.goods_parameter = goods_parameter;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_shop_detail() {
            return goods_shop_detail;
        }

        public void setGoods_shop_detail(String goods_shop_detail) {
            this.goods_shop_detail = goods_shop_detail;
        }

        public String getGoods_sup_price() {
            return goods_sup_price;
        }

        public void setGoods_sup_price(String goods_sup_price) {
            this.goods_sup_price = goods_sup_price;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public String getGoods_user_price() {
            return goods_user_price;
        }

        public void setGoods_user_price(String goods_user_price) {
            this.goods_user_price = goods_user_price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPay_num() {
            return pay_num;
        }

        public void setPay_num(int pay_num) {
            this.pay_num = pay_num;
        }

        public int getPay_user_num() {
            return pay_user_num;
        }

        public void setPay_user_num(int pay_user_num) {
            this.pay_user_num = pay_user_num;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getSup_company() {
            return sup_company;
        }

        public void setSup_company(String sup_company) {
            this.sup_company = sup_company;
        }

        public List<String> getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(List<String> goods_img) {
            this.goods_img = goods_img;
        }
    }
}
