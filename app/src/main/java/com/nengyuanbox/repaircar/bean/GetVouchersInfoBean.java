package com.nengyuanbox.repaircar.bean;

public class GetVouchersInfoBean {
//    卡券查询详情

    /**
     * code : 2000
     * data : {"end_time":"1593446400","id":"351","name":"现金抵用券","price":20,"start_time":"1558368000","str":"","text":"%3Cp%3E%0A%09%E4%BD%BF%E7%94%A8%E8%A7%84%E5%88%99%EF%BC%9A%0A%3C%2Fp%3E%0A%3Cp%3E%0A%091%E3%80%81%E5%8F%AF%E7%94%A8%E4%BA%8E%E5%85%A8%E9%A2%9D%E6%8A%B5%E6%89%A3%E6%9C%AC%E5%B9%B3%E5%8F%B0%E6%89%80%E5%94%AE%E9%9B%B6%E9%85%8D%E4%BB%B6%E9%87%87%E8%B4%AD%E4%BB%B7%E6%A0%BC%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%092%E3%80%81%E5%8F%AF%E4%B8%8E%E5%B9%B3%E5%8F%B0%E5%85%B6%E4%BB%96%E4%BC%98%E6%83%A0%E5%8F%A0%E5%8A%A0%E4%BD%BF%E7%94%A8%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%093%E3%80%81%E6%AF%8F%E4%BA%BA%E9%99%90%E9%A2%86%E4%B8%80%E5%BC%A0%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%094%E3%80%81%E6%9C%AC%E5%88%B8%E4%B8%8D%E5%8F%AF%E6%8A%B5%E6%89%A3%E9%85%8D%E4%BB%B6%E4%BB%B7%E6%A0%BC%E4%B9%8B%E5%A4%96%E7%9A%84%E6%9C%8D%E5%8A%A1%E8%B4%B9%E7%94%A8%E3%80%81%E4%B8%8A%E9%97%A8%E8%B4%B9%E3%80%81%E5%AE%89%E8%A3%85%E8%B4%B9%E7%AD%89%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%095%E3%80%81%E8%AF%B7%E5%9C%A8%E6%9C%89%E6%95%88%E6%9C%9F%E5%86%85%E4%BD%BF%E7%94%A8%E3%80%82%E6%9C%89%E6%95%88%E6%9C%9F%E4%B8%BA%EF%BC%9A2019.5.21-2020.6.30.%0A%3C%2Fp%3E%0A%3Cp%3E%0A%096%E3%80%81%E6%8A%B5%E6%89%A3%E6%97%B6%E6%8C%89%E4%B8%8D%E5%90%8C%E5%95%86%E5%93%81%E7%9A%84%E6%8A%B5%E6%89%A3%E4%B8%8A%E9%99%90%E8%BF%9B%E8%A1%8C%E6%8A%B5%E6%89%A3%EF%BC%8C%E6%94%AF%E4%BB%98%E8%AE%A2%E5%8D%95%E6%97%B6%EF%BC%8C%E7%B3%BB%E7%BB%9F%E4%BC%9A%E8%87%AA%E5%8A%A8%E8%AE%A1%E7%AE%97%EF%BC%8C%E8%87%AA%E5%8A%A8%E6%8A%B5%E6%89%A3%E3%80%82%E6%9C%AA%E7%94%A8%E5%AE%8C%E9%83%A8%E5%88%86%EF%BC%8C%E4%B8%8D%E5%86%8D%E8%BF%94%E8%BF%98%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%097%E3%80%81%E4%BD%BF%E7%94%A8%E8%BF%87%E5%90%8E%EF%BC%8C%E5%8F%AF%E5%9C%A8%E5%B7%B2%E5%A4%B1%E6%95%88%E7%9A%84%E5%88%B8%E4%B8%AD%E6%9F%A5%E7%9C%8B%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%09%3Cbr+%2F%3E%0A%3C%2Fp%3E","vtype":"2"}
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
         * end_time : 1593446400
         * id : 351
         * name : 现金抵用券
         * price : 20
         * start_time : 1558368000
         * str :
         * text : %3Cp%3E%0A%09%E4%BD%BF%E7%94%A8%E8%A7%84%E5%88%99%EF%BC%9A%0A%3C%2Fp%3E%0A%3Cp%3E%0A%091%E3%80%81%E5%8F%AF%E7%94%A8%E4%BA%8E%E5%85%A8%E9%A2%9D%E6%8A%B5%E6%89%A3%E6%9C%AC%E5%B9%B3%E5%8F%B0%E6%89%80%E5%94%AE%E9%9B%B6%E9%85%8D%E4%BB%B6%E9%87%87%E8%B4%AD%E4%BB%B7%E6%A0%BC%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%092%E3%80%81%E5%8F%AF%E4%B8%8E%E5%B9%B3%E5%8F%B0%E5%85%B6%E4%BB%96%E4%BC%98%E6%83%A0%E5%8F%A0%E5%8A%A0%E4%BD%BF%E7%94%A8%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%093%E3%80%81%E6%AF%8F%E4%BA%BA%E9%99%90%E9%A2%86%E4%B8%80%E5%BC%A0%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%094%E3%80%81%E6%9C%AC%E5%88%B8%E4%B8%8D%E5%8F%AF%E6%8A%B5%E6%89%A3%E9%85%8D%E4%BB%B6%E4%BB%B7%E6%A0%BC%E4%B9%8B%E5%A4%96%E7%9A%84%E6%9C%8D%E5%8A%A1%E8%B4%B9%E7%94%A8%E3%80%81%E4%B8%8A%E9%97%A8%E8%B4%B9%E3%80%81%E5%AE%89%E8%A3%85%E8%B4%B9%E7%AD%89%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%095%E3%80%81%E8%AF%B7%E5%9C%A8%E6%9C%89%E6%95%88%E6%9C%9F%E5%86%85%E4%BD%BF%E7%94%A8%E3%80%82%E6%9C%89%E6%95%88%E6%9C%9F%E4%B8%BA%EF%BC%9A2019.5.21-2020.6.30.%0A%3C%2Fp%3E%0A%3Cp%3E%0A%096%E3%80%81%E6%8A%B5%E6%89%A3%E6%97%B6%E6%8C%89%E4%B8%8D%E5%90%8C%E5%95%86%E5%93%81%E7%9A%84%E6%8A%B5%E6%89%A3%E4%B8%8A%E9%99%90%E8%BF%9B%E8%A1%8C%E6%8A%B5%E6%89%A3%EF%BC%8C%E6%94%AF%E4%BB%98%E8%AE%A2%E5%8D%95%E6%97%B6%EF%BC%8C%E7%B3%BB%E7%BB%9F%E4%BC%9A%E8%87%AA%E5%8A%A8%E8%AE%A1%E7%AE%97%EF%BC%8C%E8%87%AA%E5%8A%A8%E6%8A%B5%E6%89%A3%E3%80%82%E6%9C%AA%E7%94%A8%E5%AE%8C%E9%83%A8%E5%88%86%EF%BC%8C%E4%B8%8D%E5%86%8D%E8%BF%94%E8%BF%98%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%097%E3%80%81%E4%BD%BF%E7%94%A8%E8%BF%87%E5%90%8E%EF%BC%8C%E5%8F%AF%E5%9C%A8%E5%B7%B2%E5%A4%B1%E6%95%88%E7%9A%84%E5%88%B8%E4%B8%AD%E6%9F%A5%E7%9C%8B%E3%80%82%0A%3C%2Fp%3E%0A%3Cp%3E%0A%09%3Cbr+%2F%3E%0A%3C%2Fp%3E
         * vtype : 2
         */

        private String end_time;
        private String id;
        private String name;
        private int price;
        private String start_time;
        private String str;
        private String text;
        private String vtype;

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getVtype() {
            return vtype;
        }

        public void setVtype(String vtype) {
            this.vtype = vtype;
        }
    }
}
