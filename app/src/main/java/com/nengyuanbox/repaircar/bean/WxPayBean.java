package com.nengyuanbox.repaircar.bean;

public class WxPayBean {


    /**
     * code : 2000
     * data : {"appid":"wx008f0f3b6ac89688","mch_id":"1274052601","nonce_str":"YuBkSYV7OrvjnS3L","prepay_id":"wx1017221261269934021b56f41484051080","result_code":"SUCCESS","return_code":"SUCCESS","return_msg":"OK","sign":"61B2139F94B6D9238C76D3999073079A","time_expire":"20190510173211","time_start":"20190510172211","trade_type":"APP"}
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
         * appid : wx008f0f3b6ac89688
         * mch_id : 1274052601
         * nonce_str : YuBkSYV7OrvjnS3L
         * prepay_id : wx1017221261269934021b56f41484051080
         * result_code : SUCCESS
         * return_code : SUCCESS
         * return_msg : OK
         * sign : 61B2139F94B6D9238C76D3999073079A
         * time_expire : 20190510173211
         * time_start : 20190510172211
         * trade_type : APP
         */

        private String appid;
        private String mch_id;
        private String nonce_str;
        private String prepay_id;
        private String result_code;
        private String return_code;
        private String return_msg;
        private String sign;
        private String time_expire;
        private String time_start;
        private String trade_type;
        private String timeStamp;

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTime_expire() {
            return time_expire;
        }

        public void setTime_expire(String time_expire) {
            this.time_expire = time_expire;
        }

        public String getTime_start() {
            return time_start;
        }

        public void setTime_start(String time_start) {
            this.time_start = time_start;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
