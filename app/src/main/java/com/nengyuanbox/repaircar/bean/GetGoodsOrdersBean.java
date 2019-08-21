package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetGoodsOrdersBean {


    /**
     * code : 2000
     * data : {"count":"2","data":[{"addtime":"2019-05-13 11:51:33","desc":"","goods_img":"https://lxcadmin.nengyuanbox.com/Public/goodsImg/2019-04-30/small_5cc8400b2a0ec.jpg","id":"303","name":"购买商品","order_sn":"GHQ2019051319493242","price":"42.00"}]}
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
         * count : 2
         * data : [{"addtime":"2019-05-13 11:51:33","desc":"","goods_img":"https://lxcadmin.nengyuanbox.com/Public/goodsImg/2019-04-30/small_5cc8400b2a0ec.jpg","id":"303","name":"购买商品","order_sn":"GHQ2019051319493242","price":"42.00"}]
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
             * addtime : 2019-05-13 11:51:33
             * desc :
             * goods_img : https://lxcadmin.nengyuanbox.com/Public/goodsImg/2019-04-30/small_5cc8400b2a0ec.jpg
             * id : 303
             * name : 购买商品
             * order_sn : GHQ2019051319493242
             * price : 42.00
             */

            private String addtime;
            private String desc;
            private String goods_img;
            private String id;
            private String name;
            private String order_sn;
            private String price;
            private String  pay_type;

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

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

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
