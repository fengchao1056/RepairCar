package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetCoderBean {


    /**
     * message : query ok
     * request_id : 7ef168a4-5ccc-11e9-948a-80b5754ef592
     * result : {"ad_info":{"adcode":"110108","city":"北京市","city_code":"156110000","district":"海淀区","location":{"lat":40.045132,"lng":116.375},"name":"中国,北京市,北京市,海淀区","nation":"中国","nation_code":"156","province":"北京市"},"address":"北京市海淀区成府路","address_component":{"city":"北京市","district":"海淀区","nation":"中国","province":"北京市","street":"成府路","street_number":"成府路"},"address_reference":{"business_area":{"_dir_desc":"内","_distance":0,"id":"6963932222780106183","location":{"lat":39.9935,"lng":116.343002},"title":"五道口"},"crossroad":{"_dir_desc":"东南","_distance":345.2,"id":"559114","location":{"lat":39.992908,"lng":116.340431},"title":"成府路/展春园西路(路口)"},"famous_area":{"_dir_desc":"内","_distance":0,"id":"6963932222780106183","location":{"lat":39.9935,"lng":116.343002},"title":"五道口"},"landmark_l1":{"_dir_desc":"西","_distance":60.6,"id":"10869794334986960539","location":{"lat":39.991039,"lng":116.348236},"title":"中国地质大学"},"landmark_l2":{"_dir_desc":"内","_distance":0,"id":"13484628150036170348","location":{"lat":39.990646,"lng":116.342171},"title":"成府路20号院"},"street":{"_dir_desc":"南","_distance":229,"id":"15717925683449910333","location":{"lat":39.992821,"lng":116.332993},"title":"成府路"},"street_number":{"_dir_desc":"南","_distance":229,"id":"","location":{"lat":39.992821,"lng":116.332993},"title":""},"town":{"_dir_desc":"内","_distance":0,"id":"110108010","location":{"lat":40.008369,"lng":116.341988},"title":"学院路街道"}},"formatted_addresses":{"recommend":"海淀区五道口成府路20号院","rough":"海淀区五道口成府路20号院"},"location":{"lat":39.990906,"lng":116.343605},"poi_count":10,"pois":[{"_dir_desc":"内","_distance":0,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号","category":"房产小区:住宅区:住宅小区","id":"13484628150036170348","location":{"lat":39.990646,"lng":116.342171},"title":"成府路20号院"},{"_dir_desc":"东南","_distance":77.6,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号中国地质大学","category":"美食:火锅","id":"2954540631288621259","location":{"lat":39.991501,"lng":116.343132},"title":"禾地餐厅"},{"_dir_desc":"南","_distance":71.1,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号","category":"教育学校:小学","id":"8685042334658159484","location":{"lat":39.991508,"lng":116.34388},"title":"北京市海淀区第三实验小学"},{"_dir_desc":"西","_distance":60.6,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号","category":"教育学校:大学","id":"10869794334986960539","location":{"lat":39.991039,"lng":116.348236},"title":"中国地质大学"},{"_dir_desc":"南","_distance":98.5,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号麦当劳楼上210室","category":"生活服务:美容美发:美容SPA","id":"8198102742000081491","location":{"lat":39.991749,"lng":116.343254},"title":"慕可郎造型(成府路)"},{"_dir_desc":"","_distance":11.1,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号院眷37号楼","category":"房产小区:住宅区:住宅小区","id":"7759409582520324642","location":{"lat":39.990807,"lng":116.34362},"title":"成府路20号院37号楼"},{"_dir_desc":"东","_distance":34.2,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号院教1号楼","category":"房产小区:住宅区:住宅小区","id":"3389301826234615420","location":{"lat":39.991051,"lng":116.342728},"title":"成府路20号院眷1号楼"},{"_dir_desc":"北","_distance":46.7,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号院眷34号楼","category":"房产小区:住宅区:住宅小区","id":"14146147503468748185","location":{"lat":39.990459,"lng":116.343636},"title":"成府路20号院眷34号楼"},{"_dir_desc":"西南","_distance":109.5,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号","category":"文化场馆:会展中心","id":"1085922115978025454","location":{"lat":39.991562,"lng":116.345222},"title":"地大国际会议中心"},{"_dir_desc":"北","_distance":118.3,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区北四环中路279号","category":"房产小区:住宅区:住宅小区","id":"18428536258378993945","location":{"lat":39.98835,"lng":116.342789},"title":"展春园"}]}
     * status : 0
     */

    private String message;
    private String request_id;
    private ResultBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * ad_info : {"adcode":"110108","city":"北京市","city_code":"156110000","district":"海淀区","location":{"lat":40.045132,"lng":116.375},"name":"中国,北京市,北京市,海淀区","nation":"中国","nation_code":"156","province":"北京市"}
         * address : 北京市海淀区成府路
         * address_component : {"city":"北京市","district":"海淀区","nation":"中国","province":"北京市","street":"成府路","street_number":"成府路"}
         * address_reference : {"business_area":{"_dir_desc":"内","_distance":0,"id":"6963932222780106183","location":{"lat":39.9935,"lng":116.343002},"title":"五道口"},"crossroad":{"_dir_desc":"东南","_distance":345.2,"id":"559114","location":{"lat":39.992908,"lng":116.340431},"title":"成府路/展春园西路(路口)"},"famous_area":{"_dir_desc":"内","_distance":0,"id":"6963932222780106183","location":{"lat":39.9935,"lng":116.343002},"title":"五道口"},"landmark_l1":{"_dir_desc":"西","_distance":60.6,"id":"10869794334986960539","location":{"lat":39.991039,"lng":116.348236},"title":"中国地质大学"},"landmark_l2":{"_dir_desc":"内","_distance":0,"id":"13484628150036170348","location":{"lat":39.990646,"lng":116.342171},"title":"成府路20号院"},"street":{"_dir_desc":"南","_distance":229,"id":"15717925683449910333","location":{"lat":39.992821,"lng":116.332993},"title":"成府路"},"street_number":{"_dir_desc":"南","_distance":229,"id":"","location":{"lat":39.992821,"lng":116.332993},"title":""},"town":{"_dir_desc":"内","_distance":0,"id":"110108010","location":{"lat":40.008369,"lng":116.341988},"title":"学院路街道"}}
         * formatted_addresses : {"recommend":"海淀区五道口成府路20号院","rough":"海淀区五道口成府路20号院"}
         * location : {"lat":39.990906,"lng":116.343605}
         * poi_count : 10
         * pois : [{"_dir_desc":"内","_distance":0,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号","category":"房产小区:住宅区:住宅小区","id":"13484628150036170348","location":{"lat":39.990646,"lng":116.342171},"title":"成府路20号院"},{"_dir_desc":"东南","_distance":77.6,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号中国地质大学","category":"美食:火锅","id":"2954540631288621259","location":{"lat":39.991501,"lng":116.343132},"title":"禾地餐厅"},{"_dir_desc":"南","_distance":71.1,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号","category":"教育学校:小学","id":"8685042334658159484","location":{"lat":39.991508,"lng":116.34388},"title":"北京市海淀区第三实验小学"},{"_dir_desc":"西","_distance":60.6,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号","category":"教育学校:大学","id":"10869794334986960539","location":{"lat":39.991039,"lng":116.348236},"title":"中国地质大学"},{"_dir_desc":"南","_distance":98.5,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号麦当劳楼上210室","category":"生活服务:美容美发:美容SPA","id":"8198102742000081491","location":{"lat":39.991749,"lng":116.343254},"title":"慕可郎造型(成府路)"},{"_dir_desc":"","_distance":11.1,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号院眷37号楼","category":"房产小区:住宅区:住宅小区","id":"7759409582520324642","location":{"lat":39.990807,"lng":116.34362},"title":"成府路20号院37号楼"},{"_dir_desc":"东","_distance":34.2,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号院教1号楼","category":"房产小区:住宅区:住宅小区","id":"3389301826234615420","location":{"lat":39.991051,"lng":116.342728},"title":"成府路20号院眷1号楼"},{"_dir_desc":"北","_distance":46.7,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区成府路20号院眷34号楼","category":"房产小区:住宅区:住宅小区","id":"14146147503468748185","location":{"lat":39.990459,"lng":116.343636},"title":"成府路20号院眷34号楼"},{"_dir_desc":"西南","_distance":109.5,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区学院路29号","category":"文化场馆:会展中心","id":"1085922115978025454","location":{"lat":39.991562,"lng":116.345222},"title":"地大国际会议中心"},{"_dir_desc":"北","_distance":118.3,"ad_info":{"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"},"address":"北京市海淀区北四环中路279号","category":"房产小区:住宅区:住宅小区","id":"18428536258378993945","location":{"lat":39.98835,"lng":116.342789},"title":"展春园"}]
         */

        private AdInfoBean ad_info;
        private String address;
        private AddressComponentBean address_component;
        private AddressReferenceBean address_reference;
        private FormattedAddressesBean formatted_addresses;
        private LocationBeanXXXXXXXXX location;
        private int poi_count;
        private List<PoisBean> pois;

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

        public AddressComponentBean getAddress_component() {
            return address_component;
        }

        public void setAddress_component(AddressComponentBean address_component) {
            this.address_component = address_component;
        }

        public AddressReferenceBean getAddress_reference() {
            return address_reference;
        }

        public void setAddress_reference(AddressReferenceBean address_reference) {
            this.address_reference = address_reference;
        }

        public FormattedAddressesBean getFormatted_addresses() {
            return formatted_addresses;
        }

        public void setFormatted_addresses(FormattedAddressesBean formatted_addresses) {
            this.formatted_addresses = formatted_addresses;
        }

        public LocationBeanXXXXXXXXX getLocation() {
            return location;
        }

        public void setLocation(LocationBeanXXXXXXXXX location) {
            this.location = location;
        }

        public int getPoi_count() {
            return poi_count;
        }

        public void setPoi_count(int poi_count) {
            this.poi_count = poi_count;
        }

        public List<PoisBean> getPois() {
            return pois;
        }

        public void setPois(List<PoisBean> pois) {
            this.pois = pois;
        }

        public static class AdInfoBean {
            /**
             * adcode : 110108
             * city : 北京市
             * city_code : 156110000
             * district : 海淀区
             * location : {"lat":40.045132,"lng":116.375}
             * name : 中国,北京市,北京市,海淀区
             * nation : 中国
             * nation_code : 156
             * province : 北京市
             */

            private String adcode;
            private String city;
            private String city_code;
            private String district;
            private LocationBean location;
            private String name;
            private String nation;
            private String nation_code;
            private String province;

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCity_code() {
                return city_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getNation_code() {
                return nation_code;
            }

            public void setNation_code(String nation_code) {
                this.nation_code = nation_code;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public static class LocationBean {
                /**
                 * lat : 40.045132
                 * lng : 116.375
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

        public static class AddressComponentBean {
            /**
             * city : 北京市
             * district : 海淀区
             * nation : 中国
             * province : 北京市
             * street : 成府路
             * street_number : 成府路
             */

            private String city;
            private String district;
            private String nation;
            private String province;
            private String street;
            private String street_number;

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

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }
        }

        public static class AddressReferenceBean {
            /**
             * business_area : {"_dir_desc":"内","_distance":0,"id":"6963932222780106183","location":{"lat":39.9935,"lng":116.343002},"title":"五道口"}
             * crossroad : {"_dir_desc":"东南","_distance":345.2,"id":"559114","location":{"lat":39.992908,"lng":116.340431},"title":"成府路/展春园西路(路口)"}
             * famous_area : {"_dir_desc":"内","_distance":0,"id":"6963932222780106183","location":{"lat":39.9935,"lng":116.343002},"title":"五道口"}
             * landmark_l1 : {"_dir_desc":"西","_distance":60.6,"id":"10869794334986960539","location":{"lat":39.991039,"lng":116.348236},"title":"中国地质大学"}
             * landmark_l2 : {"_dir_desc":"内","_distance":0,"id":"13484628150036170348","location":{"lat":39.990646,"lng":116.342171},"title":"成府路20号院"}
             * street : {"_dir_desc":"南","_distance":229,"id":"15717925683449910333","location":{"lat":39.992821,"lng":116.332993},"title":"成府路"}
             * street_number : {"_dir_desc":"南","_distance":229,"id":"","location":{"lat":39.992821,"lng":116.332993},"title":""}
             * town : {"_dir_desc":"内","_distance":0,"id":"110108010","location":{"lat":40.008369,"lng":116.341988},"title":"学院路街道"}
             */

            private BusinessAreaBean business_area;
            private CrossroadBean crossroad;
            private FamousAreaBean famous_area;
            private LandmarkL1Bean landmark_l1;
            private LandmarkL2Bean landmark_l2;
            private StreetBean street;
            private StreetNumberBean street_number;
            private TownBean town;

            public BusinessAreaBean getBusiness_area() {
                return business_area;
            }

            public void setBusiness_area(BusinessAreaBean business_area) {
                this.business_area = business_area;
            }

            public CrossroadBean getCrossroad() {
                return crossroad;
            }

            public void setCrossroad(CrossroadBean crossroad) {
                this.crossroad = crossroad;
            }

            public FamousAreaBean getFamous_area() {
                return famous_area;
            }

            public void setFamous_area(FamousAreaBean famous_area) {
                this.famous_area = famous_area;
            }

            public LandmarkL1Bean getLandmark_l1() {
                return landmark_l1;
            }

            public void setLandmark_l1(LandmarkL1Bean landmark_l1) {
                this.landmark_l1 = landmark_l1;
            }

            public LandmarkL2Bean getLandmark_l2() {
                return landmark_l2;
            }

            public void setLandmark_l2(LandmarkL2Bean landmark_l2) {
                this.landmark_l2 = landmark_l2;
            }

            public StreetBean getStreet() {
                return street;
            }

            public void setStreet(StreetBean street) {
                this.street = street;
            }

            public StreetNumberBean getStreet_number() {
                return street_number;
            }

            public void setStreet_number(StreetNumberBean street_number) {
                this.street_number = street_number;
            }

            public TownBean getTown() {
                return town;
            }

            public void setTown(TownBean town) {
                this.town = town;
            }

            public static class BusinessAreaBean {
                /**
                 * _dir_desc : 内
                 * _distance : 0
                 * id : 6963932222780106183
                 * location : {"lat":39.9935,"lng":116.343002}
                 * title : 五道口
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanX {
                    /**
                     * lat : 39.9935
                     * lng : 116.343002
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

            public static class CrossroadBean {
                /**
                 * _dir_desc : 东南
                 * _distance : 345.2
                 * id : 559114
                 * location : {"lat":39.992908,"lng":116.340431}
                 * title : 成府路/展春园西路(路口)
                 */

                private String _dir_desc;
                private double _distance;
                private String id;
                private LocationBeanXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXX {
                    /**
                     * lat : 39.992908
                     * lng : 116.340431
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

            public static class FamousAreaBean {
                /**
                 * _dir_desc : 内
                 * _distance : 0
                 * id : 6963932222780106183
                 * location : {"lat":39.9935,"lng":116.343002}
                 * title : 五道口
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanXXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXXX {
                    /**
                     * lat : 39.9935
                     * lng : 116.343002
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

            public static class LandmarkL1Bean {
                /**
                 * _dir_desc : 西
                 * _distance : 60.6
                 * id : 10869794334986960539
                 * location : {"lat":39.991039,"lng":116.348236}
                 * title : 中国地质大学
                 */

                private String _dir_desc;
                private double _distance;
                private String id;
                private LocationBeanXXXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXXXX {
                    /**
                     * lat : 39.991039
                     * lng : 116.348236
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

            public static class LandmarkL2Bean {
                /**
                 * _dir_desc : 内
                 * _distance : 0
                 * id : 13484628150036170348
                 * location : {"lat":39.990646,"lng":116.342171}
                 * title : 成府路20号院
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanXXXXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXXXXX {
                    /**
                     * lat : 39.990646
                     * lng : 116.342171
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

            public static class StreetBean {
                /**
                 * _dir_desc : 南
                 * _distance : 229
                 * id : 15717925683449910333
                 * location : {"lat":39.992821,"lng":116.332993}
                 * title : 成府路
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanXXXXXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXXXXXX {
                    /**
                     * lat : 39.992821
                     * lng : 116.332993
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

            public static class StreetNumberBean {
                /**
                 * _dir_desc : 南
                 * _distance : 229
                 * id :
                 * location : {"lat":39.992821,"lng":116.332993}
                 * title :
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanXXXXXXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXXXXXXX {
                    /**
                     * lat : 39.992821
                     * lng : 116.332993
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

            public static class TownBean {
                /**
                 * _dir_desc : 内
                 * _distance : 0
                 * id : 110108010
                 * location : {"lat":40.008369,"lng":116.341988}
                 * title : 学院路街道
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanXXXXXXXX location;
                private String title;

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBeanXXXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXXXX location) {
                    this.location = location;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public static class LocationBeanXXXXXXXX {
                    /**
                     * lat : 40.008369
                     * lng : 116.341988
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

        public static class FormattedAddressesBean {
            /**
             * recommend : 海淀区五道口成府路20号院
             * rough : 海淀区五道口成府路20号院
             */

            private String recommend;
            private String rough;

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getRough() {
                return rough;
            }

            public void setRough(String rough) {
                this.rough = rough;
            }
        }

        public static class LocationBeanXXXXXXXXX {
            /**
             * lat : 39.990906
             * lng : 116.343605
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

        public static class PoisBean {
            /**
             * _dir_desc : 内
             * _distance : 0
             * ad_info : {"adcode":"110108","city":"北京市","district":"海淀区","province":"北京市"}
             * address : 北京市海淀区成府路20号
             * category : 房产小区:住宅区:住宅小区
             * id : 13484628150036170348
             * location : {"lat":39.990646,"lng":116.342171}
             * title : 成府路20号院
             */

            private String _dir_desc;
            private int _distance;
            private AdInfoBeanX ad_info;
            private String address;
            private String category;
            private String id;
            private LocationBeanXXXXXXXXXX location;
            private String title;

            public String get_dir_desc() {
                return _dir_desc;
            }

            public void set_dir_desc(String _dir_desc) {
                this._dir_desc = _dir_desc;
            }

            public int get_distance() {
                return _distance;
            }

            public void set_distance(int _distance) {
                this._distance = _distance;
            }

            public AdInfoBeanX getAd_info() {
                return ad_info;
            }

            public void setAd_info(AdInfoBeanX ad_info) {
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

            public LocationBeanXXXXXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXXXXXX location) {
                this.location = location;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public static class AdInfoBeanX {
                /**
                 * adcode : 110108
                 * city : 北京市
                 * district : 海淀区
                 * province : 北京市
                 */

                private String adcode;
                private String city;
                private String district;
                private String province;

                public String getAdcode() {
                    return adcode;
                }

                public void setAdcode(String adcode) {
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

            public static class LocationBeanXXXXXXXXXX {
                /**
                 * lat : 39.990646
                 * lng : 116.342171
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
}
