package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class OrderTrackBean {


    /**
     * code : 2000
     * data : [{"detail":"订单创建 操作人：李伟康","time":"2018-12-07 18:20:07"},{"detail":"修改订单价格为0.01元 操作人：华夏配件","time":"2018-12-07 18:22:08"},{"detail":"支付订单 操作人：李伟康","time":"2018-12-07 18:22:31"},{"detail":"确认发货 送货员：周芳方 操作人：华夏配件","time":"2018-12-07 18:37:42"},{"detail":"申请退款（0.01元） 操作人：李伟康","time":"2018-12-07 18:39:47"},{"detail":"拒绝退款（0.01元） 操作人：华夏配件","time":"2018-12-07 18:41:15"},{"detail":"确认收货 订单完结 操作人：李伟康","time":"2018-12-07 18:42:04"},{"detail":"订单出库 操作人：华夏配件","time":"2018-12-10 10:19:50"}]
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
         * detail : 订单创建 操作人：李伟康
         * time : 2018-12-07 18:20:07
         */

        private String detail;
        private String time;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
