package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetMasterStoreBean {


    /**
     * code : 2000
     * data : [{"BizToken":"","OcrAddress":"","address":"","apply_star":"0","area_x":"111.82651828342","area_y":"33.051735839844","auto_info":"","business_phone":"13466327712","city":"1652","county":"1659","ctime":"0","ems_id":"0","examine":"0","examine_master_count":"1","experience_id":"11","funds":"0","gname":"电动车维修站","id":"375","identity":"412926196903271206","identity_front":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-31/small_5cf0a07f572a5.jpg","identity_opposite":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-31/small_5cf0a07cf0ccc.jpg","img_url":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIk3GP5NzIUiaic6MCnJl36sqawgonGwOrNunrXCHc5UhP1ibSjjVQ9DVmXD4E1EK9UBbyrL3WwROlibQ/132","integral":"1","is_band":"0","is_master":"1","is_master_car":"0","is_master_delete":"0","is_show":"yes","is_store":"1","is_store_delete":"0","license":"0","main_items":"5","master_car_examine":"1","master_examine":"3","master_phone":"13466327712","money":"0.00","mtime":"1559272705","name":"电动车维修站","province":"1532","return_name":"","return_phone":"","sex":"2","share_id":"0","share_uid":"0","star":"2.50","store_area_x":"116.33397","store_area_y":"40.078526","store_examine":"4","store_img":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-22/5cbd7adddb9bd.png","store_phone":"13466327712","type":"dealer","uid":"362","uname":"时玉青","utime":"0","weight_total":"0.00"},{"BizToken":"","OcrAddress":"","address":"","apply_star":"0","area_x":"116.29979705811","area_y":"40.049499511719","auto_info":"","business_phone":"13426377466","city":"2","county":"10","ctime":"0","ems_id":"0","examine":"0","examine_master_count":"2","experience_id":"8","funds":"0","gname":"来修车西二旗店","id":"400","identity":"150102197704090028","identity_front":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-06-06/small_5cf8ff7042724.jpg","identity_opposite":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-06-06/small_5cf8ff6e97a97.jpg","img_url":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKkfAibOAYF3sqb0F0LZ1ByaBU7HhdU2urzXhfkwav9QsZcxhiaPlatVPo0THU2wFy1HS3DGiaU2011A/132","integral":"16","is_band":"0","is_master":"1","is_master_car":"0","is_master_delete":"0","is_show":"yes","is_store":"1","is_store_delete":"0","license":"0","main_items":"5","master_car_examine":"0","master_examine":"3","master_phone":"13426377466","money":"254.20","mtime":"1559299625","name":"来修车西二旗店","province":"1","return_name":"","return_phone":"","sex":"2","share_id":"0","share_uid":"0","star":"3.10","store_area_x":"116.30432829209","store_area_y":"40.056545726027","store_examine":"4","store_img":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-22/5cbd7ace3e740.png","store_phone":"13426377466","type":"dealer","uid":"387","uname":"周芳","utime":"0","weight_total":"0.00"},{"BizToken":"","OcrAddress":"","address":"","apply_star":"0","area_x":"116.29969024658","area_y":"40.049583435059","auto_info":"","business_phone":"18610474513","city":"2","county":"10","ctime":"0","ems_id":"0","examine":"0","examine_master_count":"0","experience_id":"9","funds":"0","gname":"绣花","id":"593","identity":"230506199709240714","identity_front":"","identity_opposite":"","img_url":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIXz7RZsSWBlMXxFv6pFWv3AWC5DqlEoPmviaU6DCJtsCibt0EIOQy0RFAxM6IFsj  NRGibOzXAHfzSgw/132","integral":"1","is_band":"0","is_master":"1","is_master_car":"0","is_master_delete":"0","is_show":"yes","is_store":"1","is_store_delete":"1","license":"0","main_items":"7","master_car_examine":"0","master_examine":"1","master_phone":"18610474513","money":"5.00","mtime":"1560767382","name":"绣花","province":"1","return_name":"","return_phone":"","sex":"1","share_id":"0","share_uid":"0","star":"2.50","store_area_x":"116.29503631592","store_area_y":"40.049247741699","store_examine":"4","store_img":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-22/5cbd7adddb9bd.png","store_phone":"18610474513","type":"dealer","uid":"594","uname":"孙闯","utime":"0","weight_total":"0.00"}]
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
         * BizToken :
         * OcrAddress :
         * address :
         * apply_star : 0
         * area_x : 111.82651828342
         * area_y : 33.051735839844
         * auto_info :
         * business_phone : 13466327712
         * city : 1652
         * county : 1659
         * ctime : 0
         * ems_id : 0
         * examine : 0
         * examine_master_count : 1
         * experience_id : 11
         * funds : 0
         * gname : 电动车维修站
         * id : 375
         * identity : 412926196903271206
         * identity_front : http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-31/small_5cf0a07f572a5.jpg
         * identity_opposite : http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-31/small_5cf0a07cf0ccc.jpg
         * img_url : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIk3GP5NzIUiaic6MCnJl36sqawgonGwOrNunrXCHc5UhP1ibSjjVQ9DVmXD4E1EK9UBbyrL3WwROlibQ/132
         * integral : 1
         * is_band : 0
         * is_master : 1
         * is_master_car : 0
         * is_master_delete : 0
         * is_show : yes
         * is_store : 1
         * is_store_delete : 0
         * license : 0
         * main_items : 5
         * master_car_examine : 1
         * master_examine : 3
         * master_phone : 13466327712
         * money : 0.00
         * mtime : 1559272705
         * name : 电动车维修站
         * province : 1532
         * return_name :
         * return_phone :
         * sex : 2
         * share_id : 0
         * share_uid : 0
         * star : 2.50
         * store_area_x : 116.33397
         * store_area_y : 40.078526
         * store_examine : 4
         * store_img : https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-22/5cbd7adddb9bd.png
         * store_phone : 13466327712
         * type : dealer
         * uid : 362
         * uname : 时玉青
         * utime : 0
         * weight_total : 0.00
         */

        private String BizToken;
        private String OcrAddress;
        private String address;
        private String apply_star;
        private String area_x;
        private String area_y;
        private String auto_info;
        private String business_phone;
        private String city;
        private String county;
        private String ctime;
        private String ems_id;
        private String examine;
        private String examine_master_count;
        private String experience_id;
        private String funds;
        private String gname;
        private String id;
        private String identity;
        private String identity_front;
        private String identity_opposite;
        private String img_url;
        private String integral;
        private String is_band;
        private String is_master;
        private String is_master_car;
        private String is_master_delete;
        private String is_show;
        private String is_store;
        private String is_store_delete;
        private String license;
        private String main_items;
        private String master_car_examine;
        private String master_examine;
        private String master_phone;
        private String money;
        private String mtime;
        private String name;
        private String province;
        private String return_name;
        private String return_phone;
        private String sex;
        private String share_id;
        private String share_uid;
        private String star;
        private String store_area_x;
        private String store_area_y;
        private String store_examine;
        private String store_img;
        private String store_phone;
        private String type;
        private String uid;
        private String uname;
        private String utime;
        private String weight_total;

        public String getBizToken() {
            return BizToken;
        }

        public void setBizToken(String BizToken) {
            this.BizToken = BizToken;
        }

        public String getOcrAddress() {
            return OcrAddress;
        }

        public void setOcrAddress(String OcrAddress) {
            this.OcrAddress = OcrAddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getApply_star() {
            return apply_star;
        }

        public void setApply_star(String apply_star) {
            this.apply_star = apply_star;
        }

        public String getArea_x() {
            return area_x;
        }

        public void setArea_x(String area_x) {
            this.area_x = area_x;
        }

        public String getArea_y() {
            return area_y;
        }

        public void setArea_y(String area_y) {
            this.area_y = area_y;
        }

        public String getAuto_info() {
            return auto_info;
        }

        public void setAuto_info(String auto_info) {
            this.auto_info = auto_info;
        }

        public String getBusiness_phone() {
            return business_phone;
        }

        public void setBusiness_phone(String business_phone) {
            this.business_phone = business_phone;
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

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getEms_id() {
            return ems_id;
        }

        public void setEms_id(String ems_id) {
            this.ems_id = ems_id;
        }

        public String getExamine() {
            return examine;
        }

        public void setExamine(String examine) {
            this.examine = examine;
        }

        public String getExamine_master_count() {
            return examine_master_count;
        }

        public void setExamine_master_count(String examine_master_count) {
            this.examine_master_count = examine_master_count;
        }

        public String getExperience_id() {
            return experience_id;
        }

        public void setExperience_id(String experience_id) {
            this.experience_id = experience_id;
        }

        public String getFunds() {
            return funds;
        }

        public void setFunds(String funds) {
            this.funds = funds;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getIdentity_front() {
            return identity_front;
        }

        public void setIdentity_front(String identity_front) {
            this.identity_front = identity_front;
        }

        public String getIdentity_opposite() {
            return identity_opposite;
        }

        public void setIdentity_opposite(String identity_opposite) {
            this.identity_opposite = identity_opposite;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getIs_band() {
            return is_band;
        }

        public void setIs_band(String is_band) {
            this.is_band = is_band;
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

        public String getIs_master_delete() {
            return is_master_delete;
        }

        public void setIs_master_delete(String is_master_delete) {
            this.is_master_delete = is_master_delete;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getIs_store() {
            return is_store;
        }

        public void setIs_store(String is_store) {
            this.is_store = is_store;
        }

        public String getIs_store_delete() {
            return is_store_delete;
        }

        public void setIs_store_delete(String is_store_delete) {
            this.is_store_delete = is_store_delete;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getMain_items() {
            return main_items;
        }

        public void setMain_items(String main_items) {
            this.main_items = main_items;
        }

        public String getMaster_car_examine() {
            return master_car_examine;
        }

        public void setMaster_car_examine(String master_car_examine) {
            this.master_car_examine = master_car_examine;
        }

        public String getMaster_examine() {
            return master_examine;
        }

        public void setMaster_examine(String master_examine) {
            this.master_examine = master_examine;
        }

        public String getMaster_phone() {
            return master_phone;
        }

        public void setMaster_phone(String master_phone) {
            this.master_phone = master_phone;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getShare_uid() {
            return share_uid;
        }

        public void setShare_uid(String share_uid) {
            this.share_uid = share_uid;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getStore_area_x() {
            return store_area_x;
        }

        public void setStore_area_x(String store_area_x) {
            this.store_area_x = store_area_x;
        }

        public String getStore_area_y() {
            return store_area_y;
        }

        public void setStore_area_y(String store_area_y) {
            this.store_area_y = store_area_y;
        }

        public String getStore_examine() {
            return store_examine;
        }

        public void setStore_examine(String store_examine) {
            this.store_examine = store_examine;
        }

        public String getStore_img() {
            return store_img;
        }

        public void setStore_img(String store_img) {
            this.store_img = store_img;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }

        public String getWeight_total() {
            return weight_total;
        }

        public void setWeight_total(String weight_total) {
            this.weight_total = weight_total;
        }
    }
}
