package com.nengyuanbox.repaircar.bean;

public class TXApiAddressBean {


    /**
     * message : query ok
     * request_id : b5903a98-923c-11e9-bcb4-525400699b85
     * result : {"ad_info":{"adcode":"110108","city":"北京市","city_code":"156110000","district":"海淀区","location":{"lat":40.045132,"lng":116.375},"name":"中国,北京市,北京市,海淀区","nation":"中国","nation_code":"156","province":"北京市"},"address":"北京市海淀区信息路甲9号","address_component":{"city":"北京市","district":"海淀区","nation":"中国","province":"北京市","street":"信息路","street_number":"信息路甲9号"},"address_reference":{"business_area":{"_dir_desc":"内","_distance":0,"id":"10444921526510914059","location":{"lat":40.038898,"lng":116.311996},"title":"上地"},"crossroad":{"_dir_desc":"北","_distance":159.5,"id":"564238","location":{"lat":40.04884,"lng":116.299347},"title":"上地西路/上地九街(路口)"},"famous_area":{"_dir_desc":"内","_distance":0,"id":"10444921526510914059","location":{"lat":40.038898,"lng":116.311996},"title":"上地"},"landmark_l1":{"_dir_desc":"内","_distance":0,"id":"17514947664053995056","location":{"lat":40.041729,"lng":116.306747},"title":"上地信息产业基地"},"landmark_l2":{"_dir_desc":"内","_distance":0,"id":"3724601452404202172","location":{"lat":40.05032,"lng":116.299454},"title":"上地科技大厦"},"street":{"_dir_desc":"东","_distance":47.5,"id":"7280645989053185356","location":{"lat":40.041012,"lng":116.304024},"title":"上地西路"},"street_number":{"_dir_desc":"","_distance":15.6,"id":"17814870610005432460","location":{"lat":40.041729,"lng":116.306931},"title":"信息路甲9号"},"town":{"_dir_desc":"内","_distance":0,"id":"110108022","location":{"lat":40.051567,"lng":116.284828},"title":"上地街道"}},"formatted_addresses":{"recommend":"海淀区上地科技大厦(上地西路东)","rough":"海淀区上地科技大厦(上地西路东)"},"location":{"lat":40.05032,"lng":116.299454}}
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
         * address : 北京市海淀区信息路甲9号
         * address_component : {"city":"北京市","district":"海淀区","nation":"中国","province":"北京市","street":"信息路","street_number":"信息路甲9号"}
         * address_reference : {"business_area":{"_dir_desc":"内","_distance":0,"id":"10444921526510914059","location":{"lat":40.038898,"lng":116.311996},"title":"上地"},"crossroad":{"_dir_desc":"北","_distance":159.5,"id":"564238","location":{"lat":40.04884,"lng":116.299347},"title":"上地西路/上地九街(路口)"},"famous_area":{"_dir_desc":"内","_distance":0,"id":"10444921526510914059","location":{"lat":40.038898,"lng":116.311996},"title":"上地"},"landmark_l1":{"_dir_desc":"内","_distance":0,"id":"17514947664053995056","location":{"lat":40.041729,"lng":116.306747},"title":"上地信息产业基地"},"landmark_l2":{"_dir_desc":"内","_distance":0,"id":"3724601452404202172","location":{"lat":40.05032,"lng":116.299454},"title":"上地科技大厦"},"street":{"_dir_desc":"东","_distance":47.5,"id":"7280645989053185356","location":{"lat":40.041012,"lng":116.304024},"title":"上地西路"},"street_number":{"_dir_desc":"","_distance":15.6,"id":"17814870610005432460","location":{"lat":40.041729,"lng":116.306931},"title":"信息路甲9号"},"town":{"_dir_desc":"内","_distance":0,"id":"110108022","location":{"lat":40.051567,"lng":116.284828},"title":"上地街道"}}
         * formatted_addresses : {"recommend":"海淀区上地科技大厦(上地西路东)","rough":"海淀区上地科技大厦(上地西路东)"}
         * location : {"lat":40.05032,"lng":116.299454}
         */

        private AdInfoBean ad_info;
        private String address;
        private AddressComponentBean address_component;
        private AddressReferenceBean address_reference;
        private FormattedAddressesBean formatted_addresses;
        private LocationBeanXXXXXXXXX location;

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
             * street : 信息路
             * street_number : 信息路甲9号
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
             * business_area : {"_dir_desc":"内","_distance":0,"id":"10444921526510914059","location":{"lat":40.038898,"lng":116.311996},"title":"上地"}
             * crossroad : {"_dir_desc":"北","_distance":159.5,"id":"564238","location":{"lat":40.04884,"lng":116.299347},"title":"上地西路/上地九街(路口)"}
             * famous_area : {"_dir_desc":"内","_distance":0,"id":"10444921526510914059","location":{"lat":40.038898,"lng":116.311996},"title":"上地"}
             * landmark_l1 : {"_dir_desc":"内","_distance":0,"id":"17514947664053995056","location":{"lat":40.041729,"lng":116.306747},"title":"上地信息产业基地"}
             * landmark_l2 : {"_dir_desc":"内","_distance":0,"id":"3724601452404202172","location":{"lat":40.05032,"lng":116.299454},"title":"上地科技大厦"}
             * street : {"_dir_desc":"东","_distance":47.5,"id":"7280645989053185356","location":{"lat":40.041012,"lng":116.304024},"title":"上地西路"}
             * street_number : {"_dir_desc":"","_distance":15.6,"id":"17814870610005432460","location":{"lat":40.041729,"lng":116.306931},"title":"信息路甲9号"}
             * town : {"_dir_desc":"内","_distance":0,"id":"110108022","location":{"lat":40.051567,"lng":116.284828},"title":"上地街道"}
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
                 * id : 10444921526510914059
                 * location : {"lat":40.038898,"lng":116.311996}
                 * title : 上地
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
                     * lat : 40.038898
                     * lng : 116.311996
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
                 * _dir_desc : 北
                 * _distance : 159.5
                 * id : 564238
                 * location : {"lat":40.04884,"lng":116.299347}
                 * title : 上地西路/上地九街(路口)
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
                     * lat : 40.04884
                     * lng : 116.299347
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
                 * id : 10444921526510914059
                 * location : {"lat":40.038898,"lng":116.311996}
                 * title : 上地
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
                     * lat : 40.038898
                     * lng : 116.311996
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
                 * _dir_desc : 内
                 * _distance : 0
                 * id : 17514947664053995056
                 * location : {"lat":40.041729,"lng":116.306747}
                 * title : 上地信息产业基地
                 */

                private String _dir_desc;
                private int _distance;
                private String id;
                private LocationBeanXXXX location;
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
                     * lat : 40.041729
                     * lng : 116.306747
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
                 * id : 3724601452404202172
                 * location : {"lat":40.05032,"lng":116.299454}
                 * title : 上地科技大厦
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
                     * lat : 40.05032
                     * lng : 116.299454
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
                 * _dir_desc : 东
                 * _distance : 47.5
                 * id : 7280645989053185356
                 * location : {"lat":40.041012,"lng":116.304024}
                 * title : 上地西路
                 */

                private String _dir_desc;
                private double _distance;
                private String id;
                private LocationBeanXXXXXX location;
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
                     * lat : 40.041012
                     * lng : 116.304024
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
                 * _dir_desc :
                 * _distance : 15.6
                 * id : 17814870610005432460
                 * location : {"lat":40.041729,"lng":116.306931}
                 * title : 信息路甲9号
                 */

                private String _dir_desc;
                private double _distance;
                private String id;
                private LocationBeanXXXXXXX location;
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
                     * lat : 40.041729
                     * lng : 116.306931
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
                 * id : 110108022
                 * location : {"lat":40.051567,"lng":116.284828}
                 * title : 上地街道
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
                     * lat : 40.051567
                     * lng : 116.284828
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
             * recommend : 海淀区上地科技大厦(上地西路东)
             * rough : 海淀区上地科技大厦(上地西路东)
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
             * lat : 40.05032
             * lng : 116.299454
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
