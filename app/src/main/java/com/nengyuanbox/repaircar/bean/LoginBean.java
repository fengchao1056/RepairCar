package com.nengyuanbox.repaircar.bean;

public class LoginBean {


    /**
     * code : 2000
     * data : {"access_token":"9e7bd6b90021a3f20be613b961995cdd9e7bd6b90021a3f20be613b961995cdd9e7bd6b90021a3f20be613b961995cdd33a37ab04bac422958908a36345dd5a4","gid":"24","img_url":"http://thirdwx.qlogo.cn/mmopen/vi_32/ADvxH0yj6sJOdsgOTDDTdPyXrkUnYDe4iajV5FtrO6JP2FNibZmkAe1mR6kuLQ8cibSnicMzhOIPZvGIqE7y5ZH0RQ/132","is_master":"0","is_master_car":"0","is_store":"0","iv":"izpWQveL1gsOW8T5","key":"I5HqLHyadFHbU1GF","login_salt":"z4r96k","name":"晨曦吻过彩虹的脸庞","openid":"o50dh5u5PTO-v2Ec_IYbj_82QOXg","phone":"0","sex":"1","uid":"25","wx_access_token":"20_I8-oRQPo7ofdHdsIz-CLmei0z39m8GeQh7cOjru-WHh7kEajrwYCGdEWbluKhAtvHbcSiC3YVEdBwvgoXO2K7QUrh-w34T_bnEnJX56ljU8"}
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
         * access_token : 9e7bd6b90021a3f20be613b961995cdd9e7bd6b90021a3f20be613b961995cdd9e7bd6b90021a3f20be613b961995cdd33a37ab04bac422958908a36345dd5a4
         * gid : 24
         * img_url : http://thirdwx.qlogo.cn/mmopen/vi_32/ADvxH0yj6sJOdsgOTDDTdPyXrkUnYDe4iajV5FtrO6JP2FNibZmkAe1mR6kuLQ8cibSnicMzhOIPZvGIqE7y5ZH0RQ/132
         * is_master : 0
         * is_master_car : 0
         * is_store : 0
         * iv : izpWQveL1gsOW8T5
         * key : I5HqLHyadFHbU1GF
         * login_salt : z4r96k
         * name : 晨曦吻过彩虹的脸庞
         * openid : o50dh5u5PTO-v2Ec_IYbj_82QOXg
         * phone : 0
         * sex : 1
         * uid : 25
         * wx_access_token : 20_I8-oRQPo7ofdHdsIz-CLmei0z39m8GeQh7cOjru-WHh7kEajrwYCGdEWbluKhAtvHbcSiC3YVEdBwvgoXO2K7QUrh-w34T_bnEnJX56ljU8
         */

        private String access_token;
        private String gid;
        private String img_url;
        private String is_master;
        private String is_master_car;
        private String is_store;
        private String iv;
        private String key;
        private String login_salt;
        private String name;
        private String openid;
        private String phone;
        private String sex;
        private String uid;
        private String wx_access_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getIs_master() {
            return is_master;
        }

        public void setIs_master(String is_master) {
            this.is_master = is_master;
        }

        public String getIs_master_car() {
            return is_master_car;
        }

        public void setIs_master_car(String is_master_car) {
            this.is_master_car = is_master_car;
        }

        public String getIs_store() {
            return is_store;
        }

        public void setIs_store(String is_store) {
            this.is_store = is_store;
        }

        public String getIv() {
            return iv;
        }

        public void setIv(String iv) {
            this.iv = iv;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getLogin_salt() {
            return login_salt;
        }

        public void setLogin_salt(String login_salt) {
            this.login_salt = login_salt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWx_access_token() {
            return wx_access_token;
        }

        public void setWx_access_token(String wx_access_token) {
            this.wx_access_token = wx_access_token;
        }
    }
}
