package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class MapSearchBean {


    /**
     * count : 17
     * data : [{"_distance":347.34,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区上地九街9号数码科技广场","category":"购物:数码家电","id":"2006735876160052562","location":{"lat":40.051304,"lng":116.302994},"tel":" ","title":"火币科技","type":0},{"_distance":665.79,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区开拓路1号院汇苑开拓大厦B1018","category":"购物:数码家电","id":"534567727660080398","location":{"lat":40.044255,"lng":116.303101},"tel":" ","title":"美歌科技","type":0},{"_distance":864.5,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区上地东路9号得实大厦三层南西区","category":"购物:数码家电","id":"14511683452194755501","location":{"lat":40.045503,"lng":116.308201},"tel":" ","title":"罗达麦海电子科技","type":0},{"_distance":1255.15,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区西二旗大街益民市场Q区13","category":"购物:数码家电","id":"5240073314024473221","location":{"lat":40.0527,"lng":116.31374},"tel":"010-62978335; 13426269479","title":"华大恒通电脑维修·组装","type":0},{"_distance":1075.37,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区东北旺西路8号院尚东数字山谷B区2号楼一层105号","category":"购物:数码家电","id":"5435251058066784683","location":{"lat":40.04376,"lng":116.28953},"tel":"17611276803","title":"倍升互联(北京)科技有限公司","type":0},{"_distance":1274.6,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区上地五街16号华胜大厦西楼2层","category":"购物:数码家电","id":"7949471690933958946","location":{"lat":40.040033,"lng":116.307805},"tel":"15210112733","title":"布莱斯数码科技","type":0},{"_distance":1384.22,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区软件园北街与软件园西二路交叉口西北150米","category":"购物:其它购物","id":"8277464565565963432","location":{"lat":40.050748,"lng":116.283407},"tel":" ","title":"慧众科技","type":0},{"_distance":1601.72,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区上地产业基地三街9号嘉华大厦C座1101室","category":"购物:数码家电","id":"16896640712584789019","location":{"lat":40.036944,"lng":116.308542},"tel":" ","title":"鸿合科技市场","type":0},{"_distance":1589.99,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区上地三街金隅嘉华大厦D座1009室","category":"购物:其它购物","id":"8290948992463377690","location":{"lat":40.036537,"lng":116.307144},"tel":" ","title":"弈新科技","type":0},{"_distance":1658.6,"ad_info":{"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区上地金隅嘉华大厦B座1106室","category":"购物:数码家电","id":"7698556371334562850","location":{"lat":40.036566,"lng":116.309029},"tel":" ","title":"尚软科技","type":0}]
     * message : query ok
     * region : {"title":"中国"}
     * request_id : 2221281821319fd7e57739ad823f7a13bde44e422d58
     * status : 0
     */

    private int count;
    private String message;
    private RegionBean region;
    private String request_id;
    private int status;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegionBean getRegion() {
        return region;
    }

    public void setRegion(RegionBean region) {
        this.region = region;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class RegionBean {
        /**
         * title : 中国
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class DataBean {
        /**
         * _distance : 347.34
         * ad_info : {"adcode":110108,"city":"北京市","district":"海淀区","province":"北京市"}
         * address : 北京市海淀区上地九街9号数码科技广场
         * category : 购物:数码家电
         * id : 2006735876160052562
         * location : {"lat":40.051304,"lng":116.302994}
         * tel :
         * title : 火币科技
         * type : 0
         */

        private double _distance;
        private AdInfoBean ad_info;
        private String address;
        private String category;
        private String id;
        private LocationBean location;
        private String tel;
        private String title;
        private int type;

        public double get_distance() {
            return _distance;
        }

        public void set_distance(double _distance) {
            this._distance = _distance;
        }

        public AdInfoBean getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfoBean ad_info) {
            this.ad_info = ad_info;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class AdInfoBean {
            /**
             * adcode : 110108
             * city : 北京市
             * district : 海淀区
             * province : 北京市
             */

            private int adcode;
            private String city;
            private String district;
            private String province;

            public int getAdcode() {
                return adcode;
            }

            public void setAdcode(int adcode) {
                this.adcode = adcode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }
        }

        public static class LocationBean {
            /**
             * lat : 40.051304
             * lng : 116.302994
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
