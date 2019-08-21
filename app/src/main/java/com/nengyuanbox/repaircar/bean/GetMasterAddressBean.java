package com.nengyuanbox.repaircar.bean;

public class GetMasterAddressBean {


    /**
     * code : 2000
     * data : {"address":"","city":"","county":"","province":"","return_name":"","return_phone":""}
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
         * address :
         * city :
         * county :
         * province :
         * return_name :
         * return_phone :
         */

        private String address;
        private String city;
        private String county;
        private String province;
        private String return_name;
        private String return_phone;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReturn_name() {
            return return_name;
        }

        public void setReturn_name(String return_name) {
            this.return_name = return_name;
        }

        public String getReturn_phone() {
            return return_phone;
        }

        public void setReturn_phone(String return_phone) {
            this.return_phone = return_phone;
        }
    }
}
