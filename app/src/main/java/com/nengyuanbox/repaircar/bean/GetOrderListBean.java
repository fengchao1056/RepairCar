package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetOrderListBean {


    /**
     * code : 2000
     * data : {"data":[{"add_time":"2019-05-13 15:07:47","address":"百度大厦B座","apply_user_id":"161","area_x":"116.301032","area_y":"40.050931","car_id":"10","car_name":"电动自行车","car_sign":"绿源","car_sign_id":"2","city":"2","complete_time":"","county":"10","delete_status":"0","evaluate_status":"0","expected_time":"越快越好","formId":"e1c7d0314be5464ebb345e1ddbcc45f3","formId1":"45a3dfa23a6c455cb120ff89aff48202","formId2":"17dc6ab6d6a94697b90111ca5098fd50","handle_time":"","handle_user_id":"0","id":"LXC2019051331267202","img":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-13/small_5cd917c33a578.jpg","img_url":"https://lxcadmin.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","money":"0.01","order_detail":[{"detail":"订单提交","time":"2019-05-13 15:07:47"},{"detail":"支付成功","time":"2019-05-13 15:07:58"}],"order_sn":"LXC2019051331267202","order_status":"2","pay_time":"2019-05-13 15:07:58","prepay_id":"wx13150750637789584972c04b2589513517","province":"1","question_desc":"测试","refund_status":"0","refund_type":"0","service":"换电瓶","service_id":"6","service_telephone":"010-58858068","tel_phone":"15903806000","transaction_id":"4200000308201905137663100028","uname":"晨曦吻过彩虹的脸庞"}],"total":"1","totalPage":1}
     * message : 请求成功
     */

    private int code;
    private DataBeanX data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBeanX {
        /**
         * data : [{"add_time":"2019-05-13 15:07:47","address":"百度大厦B座","apply_user_id":"161","area_x":"116.301032","area_y":"40.050931","car_id":"10","car_name":"电动自行车","car_sign":"绿源","car_sign_id":"2","city":"2","complete_time":"","county":"10","delete_status":"0","evaluate_status":"0","expected_time":"越快越好","formId":"e1c7d0314be5464ebb345e1ddbcc45f3","formId1":"45a3dfa23a6c455cb120ff89aff48202","formId2":"17dc6ab6d6a94697b90111ca5098fd50","handle_time":"","handle_user_id":"0","id":"LXC2019051331267202","img":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-13/small_5cd917c33a578.jpg","img_url":"https://lxcadmin.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png","money":"0.01","order_detail":[{"detail":"订单提交","time":"2019-05-13 15:07:47"},{"detail":"支付成功","time":"2019-05-13 15:07:58"}],"order_sn":"LXC2019051331267202","order_status":"2","pay_time":"2019-05-13 15:07:58","prepay_id":"wx13150750637789584972c04b2589513517","province":"1","question_desc":"测试","refund_status":"0","refund_type":"0","service":"换电瓶","service_id":"6","service_telephone":"010-58858068","tel_phone":"15903806000","transaction_id":"4200000308201905137663100028","uname":"晨曦吻过彩虹的脸庞"}]
         * total : 1
         * totalPage : 1
         */

        private String total;
        private int totalPage;
        private List<DataBean> data;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * add_time : 2019-05-13 15:07:47
             * address : 百度大厦B座
             * apply_user_id : 161
             * area_x : 116.301032
             * area_y : 40.050931
             * car_id : 10
             * car_name : 电动自行车
             * car_sign : 绿源
             * car_sign_id : 2
             * city : 2
             * complete_time :
             * county : 10
             * delete_status : 0
             * evaluate_status : 0
             * expected_time : 越快越好
             * formId : e1c7d0314be5464ebb345e1ddbcc45f3
             * formId1 : 45a3dfa23a6c455cb120ff89aff48202
             * formId2 : 17dc6ab6d6a94697b90111ca5098fd50
             * handle_time :
             * handle_user_id : 0
             * id : LXC2019051331267202
             * img : http://lxcapi.nengyuanbox.com/Public/Upload/2019-05-13/small_5cd917c33a578.jpg
             * img_url : https://lxcadmin.nengyuanbox.com/Public/configImg/2019-04-20/5cba8e37a3bbe.png
             * money : 0.01
             * order_detail : [{"detail":"订单提交","time":"2019-05-13 15:07:47"},{"detail":"支付成功","time":"2019-05-13 15:07:58"}]
             * order_sn : LXC2019051331267202
             * order_status : 2
             * pay_time : 2019-05-13 15:07:58
             * prepay_id : wx13150750637789584972c04b2589513517
             * province : 1
             * question_desc : 测试
             * refund_status : 0
             * refund_type : 0
             * service : 换电瓶
             * service_id : 6
             * service_telephone : 010-58858068
             * tel_phone : 15903806000
             * transaction_id : 4200000308201905137663100028
             * uname : 晨曦吻过彩虹的脸庞
             */

            private String add_time;
            private String address;
            private String apply_user_id;
            private String area_x;
            private String area_y;
            private String car_id;
            private String car_name;
            private String car_sign;
            private String car_sign_id;
            private String city;
            private String complete_time;
            private String county;
            private String delete_status;
            private String evaluate_status;
            private String expected_time;
            private String formId;
            private String formId1;
            private String formId2;
            private String handle_time;
            private String handle_user_id;
            private String id;
            private String img;
            private String img_url;
            private String money;
            private String order_sn;
            private String order_status;
            private String pay_time;
            private String prepay_id;
            private String province;
            private String question_desc;
            private String refund_status;
            private String refund_type;
            private String service;
            private String service_id;
            private String service_telephone;
            private String tel_phone;
            private String transaction_id;
            private String uname;
            private List<OrderDetailBean> order_detail;

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getApply_user_id() {
                return apply_user_id;
            }

            public void setApply_user_id(String apply_user_id) {
                this.apply_user_id = apply_user_id;
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

            public String getCar_id() {
                return car_id;
            }

            public void setCar_id(String car_id) {
                this.car_id = car_id;
            }

            public String getCar_name() {
                return car_name;
            }

            public void setCar_name(String car_name) {
                this.car_name = car_name;
            }

            public String getCar_sign() {
                return car_sign;
            }

            public void setCar_sign(String car_sign) {
                this.car_sign = car_sign;
            }

            public String getCar_sign_id() {
                return car_sign_id;
            }

            public void setCar_sign_id(String car_sign_id) {
                this.car_sign_id = car_sign_id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getComplete_time() {
                return complete_time;
            }

            public void setComplete_time(String complete_time) {
                this.complete_time = complete_time;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getDelete_status() {
                return delete_status;
            }

            public void setDelete_status(String delete_status) {
                this.delete_status = delete_status;
            }

            public String getEvaluate_status() {
                return evaluate_status;
            }

            public void setEvaluate_status(String evaluate_status) {
                this.evaluate_status = evaluate_status;
            }

            public String getExpected_time() {
                return expected_time;
            }

            public void setExpected_time(String expected_time) {
                this.expected_time = expected_time;
            }

            public String getFormId() {
                return formId;
            }

            public void setFormId(String formId) {
                this.formId = formId;
            }

            public String getFormId1() {
                return formId1;
            }

            public void setFormId1(String formId1) {
                this.formId1 = formId1;
            }

            public String getFormId2() {
                return formId2;
            }

            public void setFormId2(String formId2) {
                this.formId2 = formId2;
            }

            public String getHandle_time() {
                return handle_time;
            }

            public void setHandle_time(String handle_time) {
                this.handle_time = handle_time;
            }

            public String getHandle_user_id() {
                return handle_user_id;
            }

            public void setHandle_user_id(String handle_user_id) {
                this.handle_user_id = handle_user_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getPrepay_id() {
                return prepay_id;
            }

            public void setPrepay_id(String prepay_id) {
                this.prepay_id = prepay_id;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getQuestion_desc() {
                return question_desc;
            }

            public void setQuestion_desc(String question_desc) {
                this.question_desc = question_desc;
            }

            public String getRefund_status() {
                return refund_status;
            }

            public void setRefund_status(String refund_status) {
                this.refund_status = refund_status;
            }

            public String getRefund_type() {
                return refund_type;
            }

            public void setRefund_type(String refund_type) {
                this.refund_type = refund_type;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getService_id() {
                return service_id;
            }

            public void setService_id(String service_id) {
                this.service_id = service_id;
            }

            public String getService_telephone() {
                return service_telephone;
            }

            public void setService_telephone(String service_telephone) {
                this.service_telephone = service_telephone;
            }

            public String getTel_phone() {
                return tel_phone;
            }

            public void setTel_phone(String tel_phone) {
                this.tel_phone = tel_phone;
            }

            public String getTransaction_id() {
                return transaction_id;
            }

            public void setTransaction_id(String transaction_id) {
                this.transaction_id = transaction_id;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public List<OrderDetailBean> getOrder_detail() {
                return order_detail;
            }

            public void setOrder_detail(List<OrderDetailBean> order_detail) {
                this.order_detail = order_detail;
            }

            public static class OrderDetailBean {
                /**
                 * detail : 订单提交
                 * time : 2019-05-13 15:07:47
                 */

                private String detail;
                private String time;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }
}
