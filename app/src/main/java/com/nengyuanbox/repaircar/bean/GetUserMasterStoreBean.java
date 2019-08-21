package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetUserMasterStoreBean {


    /**
     * code : 2000
     * data : {"add_time":"1561541771","claim_time":"2019年06月26","id":"21","master_uid":"306","status":"1","store_info":{"business_phone":"15903806000","gname":"测试门店","group_car_name":"电动自行车 电动三轮车 低速电动车 两轮摩托车 电动自行车 电动三轮车 低速电动车 两轮摩托车 ","group_car_names":[{"car_name_id":"10","gid":"321","id":"10","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","is_del":"0","name":"电动自行车"},{"car_name_id":"11","gid":"321","id":"11","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e4961584.png","is_del":"0","name":"电动三轮车"},{"car_name_id":"12","gid":"321","id":"12","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e52ce821.png","is_del":"0","name":"低速电动车"},{"car_name_id":"13","gid":"321","id":"13","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e5bcf1b8.png","is_del":"0","name":"两轮摩托车"},{"car_name_id":"10","gid":"321","id":"10","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","is_del":"0","name":"电动自行车"},{"car_name_id":"11","gid":"321","id":"11","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e4961584.png","is_del":"0","name":"电动三轮车"},{"car_name_id":"12","gid":"321","id":"12","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e52ce821.png","is_del":"0","name":"低速电动车"},{"car_name_id":"13","gid":"321","id":"13","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e5bcf1b8.png","is_del":"0","name":"两轮摩托车"}],"id":"321","main_items":"5","main_items_name":"电动车维修店","star":"0.0","store_area_x":"40.05032","store_area_y":"116.299454","store_img":"http://lxcapimoni.nengyuanbox.com/Public/Upload/2019-06-25/small_5d1194efd6d7b.jpg","store_phone":"15903806000","uid":"306"},"store_uid":"306","type":"2"}
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
         * add_time : 1561541771
         * claim_time : 2019年06月26
         * id : 21
         * master_uid : 306
         * status : 1
         * store_info : {"business_phone":"15903806000","gname":"测试门店","group_car_name":"电动自行车 电动三轮车 低速电动车 两轮摩托车 电动自行车 电动三轮车 低速电动车 两轮摩托车 ","group_car_names":[{"car_name_id":"10","gid":"321","id":"10","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","is_del":"0","name":"电动自行车"},{"car_name_id":"11","gid":"321","id":"11","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e4961584.png","is_del":"0","name":"电动三轮车"},{"car_name_id":"12","gid":"321","id":"12","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e52ce821.png","is_del":"0","name":"低速电动车"},{"car_name_id":"13","gid":"321","id":"13","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e5bcf1b8.png","is_del":"0","name":"两轮摩托车"},{"car_name_id":"10","gid":"321","id":"10","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","is_del":"0","name":"电动自行车"},{"car_name_id":"11","gid":"321","id":"11","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e4961584.png","is_del":"0","name":"电动三轮车"},{"car_name_id":"12","gid":"321","id":"12","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e52ce821.png","is_del":"0","name":"低速电动车"},{"car_name_id":"13","gid":"321","id":"13","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e5bcf1b8.png","is_del":"0","name":"两轮摩托车"}],"id":"321","main_items":"5","main_items_name":"电动车维修店","star":"0.0","store_area_x":"40.05032","store_area_y":"116.299454","store_img":"http://lxcapimoni.nengyuanbox.com/Public/Upload/2019-06-25/small_5d1194efd6d7b.jpg","store_phone":"15903806000","uid":"306"}
         * store_uid : 306
         * type : 2
         */

        private String add_time;
        private String claim_time;
        private String id;
        private String master_uid;
        private String status;
        private StoreInfoBean store_info;
        private String store_uid;
        private String type;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getClaim_time() {
            return claim_time;
        }

        public void setClaim_time(String claim_time) {
            this.claim_time = claim_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaster_uid() {
            return master_uid;
        }

        public void setMaster_uid(String master_uid) {
            this.master_uid = master_uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public StoreInfoBean getStore_info() {
            return store_info;
        }

        public void setStore_info(StoreInfoBean store_info) {
            this.store_info = store_info;
        }

        public String getStore_uid() {
            return store_uid;
        }

        public void setStore_uid(String store_uid) {
            this.store_uid = store_uid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class StoreInfoBean {
            /**
             * business_phone : 15903806000
             * gname : 测试门店
             * group_car_name : 电动自行车 电动三轮车 低速电动车 两轮摩托车 电动自行车 电动三轮车 低速电动车 两轮摩托车
             * group_car_names : [{"car_name_id":"10","gid":"321","id":"10","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","is_del":"0","name":"电动自行车"},{"car_name_id":"11","gid":"321","id":"11","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e4961584.png","is_del":"0","name":"电动三轮车"},{"car_name_id":"12","gid":"321","id":"12","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e52ce821.png","is_del":"0","name":"低速电动车"},{"car_name_id":"13","gid":"321","id":"13","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e5bcf1b8.png","is_del":"0","name":"两轮摩托车"},{"car_name_id":"10","gid":"321","id":"10","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","is_del":"0","name":"电动自行车"},{"car_name_id":"11","gid":"321","id":"11","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e4961584.png","is_del":"0","name":"电动三轮车"},{"car_name_id":"12","gid":"321","id":"12","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e52ce821.png","is_del":"0","name":"低速电动车"},{"car_name_id":"13","gid":"321","id":"13","img_url":"https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e5bcf1b8.png","is_del":"0","name":"两轮摩托车"}]
             * id : 321
             * main_items : 5
             * main_items_name : 电动车维修店
             * star : 0.0
             * store_area_x : 40.05032
             * store_area_y : 116.299454
             * store_img : http://lxcapimoni.nengyuanbox.com/Public/Upload/2019-06-25/small_5d1194efd6d7b.jpg
             * store_phone : 15903806000
             * uid : 306
             */

            private String business_phone;
            private String gname;
            private String group_car_name;
            private String id;
            private String main_items;
            private String main_items_name;
            private String star;
            private String store_area_x;
            private String store_area_y;
            private String store_img;
            private String store_phone;
            private String uid;
            private List<GroupCarNamesBean> group_car_names;

            public String getBusiness_phone() {
                return business_phone;
            }

            public void setBusiness_phone(String business_phone) {
                this.business_phone = business_phone;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGroup_car_name() {
                return group_car_name;
            }

            public void setGroup_car_name(String group_car_name) {
                this.group_car_name = group_car_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMain_items() {
                return main_items;
            }

            public void setMain_items(String main_items) {
                this.main_items = main_items;
            }

            public String getMain_items_name() {
                return main_items_name;
            }

            public void setMain_items_name(String main_items_name) {
                this.main_items_name = main_items_name;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public List<GroupCarNamesBean> getGroup_car_names() {
                return group_car_names;
            }

            public void setGroup_car_names(List<GroupCarNamesBean> group_car_names) {
                this.group_car_names = group_car_names;
            }

            public static class GroupCarNamesBean {
                /**
                 * car_name_id : 10
                 * gid : 321
                 * id : 10
                 * img_url : https://lxc778ad.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png
                 * is_del : 0
                 * name : 电动自行车
                 */

                private String car_name_id;
                private String gid;
                private String id;
                private String img_url;
                private String is_del;
                private String name;

                public String getCar_name_id() {
                    return car_name_id;
                }

                public void setCar_name_id(String car_name_id) {
                    this.car_name_id = car_name_id;
                }

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }

                public String getIs_del() {
                    return is_del;
                }

                public void setIs_del(String is_del) {
                    this.is_del = is_del;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
