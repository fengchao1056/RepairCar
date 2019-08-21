package com.nengyuanbox.repaircar.bean;


public class GetWXSignBean {


    /**
     * code : 2000
     * data : {"appid":"wx008f0f3b6ac89688","noncestr":"aerhftatttvgv1p5a9hqdpam6bj7uka2","package":"Sign=WXPay","partnerid":"1274052601","prepayid":"wx141100237815193a86921c923538441942","sign":"F55F349A8A0C70C241ED85F977D1FF45","timestamp":1557802822}
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
         * noncestr : aerhftatttvgv1p5a9hqdpam6bj7uka2
         * package : Sign=WXPay
         * partnerid : 1274052601
         * prepayid : wx141100237815193a86921c923538441942
         * sign : F55F349A8A0C70C241ED85F977D1FF45
         * timestamp : 1557802822
         */

        private String appid;
        private String noncestr;
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private int timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
