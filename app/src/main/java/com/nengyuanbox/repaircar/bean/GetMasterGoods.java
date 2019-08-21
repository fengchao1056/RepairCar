package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetMasterGoods {


    /**
     * code : 2000
     * data : {"count":"1","data":[{"company":"","goods_id":"6349","goods_img":"https://www.xiuche.com/Public/goodsImg/2019-04-25/5cc12b6dc4a49.jpg","goods_master_price":"70.00","goods_name":"回力城轮胎0元抢购","goods_num":"1","goods_price":"150.00"}]}
     * message : 请求成功
     */

    private int code;
    private DataBeanX data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBeanX {
        /**
         * count : 1
         * data : [{"company":"","goods_id":"6349","goods_img":"https://www.xiuche.com/Public/goodsImg/2019-04-25/5cc12b6dc4a49.jpg","goods_master_price":"70.00","goods_name":"回力城轮胎0元抢购","goods_num":"1","goods_price":"150.00"}]
         */

        private String count;
        private List<DataBean> data;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * company :
             * goods_id : 6349
             * goods_img : https://www.xiuche.com/Public/goodsImg/2019-04-25/5cc12b6dc4a49.jpg
             * goods_master_price : 70.00
             * goods_name : 回力城轮胎0元抢购
             * goods_num : 1
             * goods_price : 150.00
             */

            private String company;
            private String goods_id;
            private String goods_img;
            private String goods_master_price;
            private String goods_name;
            private String goods_num;
            private String goods_price;

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
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

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }
        }
    }
}
