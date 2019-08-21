package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetOrderInfoBean {


    /**
     * code : 2000
     * data : {"add_time":"2019-04-15 13:24:35","address":"海淀区上地科技大厦(上地西路东)","apply_user_id":"41","apply_user_star":"0","area_x":"40.049627278645836","area_y":"116.299609375","car_id":"1","car_name":"电动车","car_sign":"绿能","car_sign_id":"6","city":"2","complete_time":0,"county":"10","delete_status":"1","evaluate_status":"0","expected_time":"30","handle_order_num":"2","handle_time":0,"handle_user_id":"51","handle_user_name":"","handle_user_phone":"","handle_user_star":"0","handle_user_type":"金牌维修站","id":"17","img":"http://lxcapi.nengyuanbox.com/Public/Upload/2019-04-15/small_5cb41593e743d.jpg","money":"3.33","order_detail":[{"detail":"订单提交","time":1555305876},{"detail":"订单取消","time":1555305916}],"order_sn":"LXC2019041505875832","order_status":"3","pay_time":0,"province":"2","question_desc":"1111","refund_status":"0","service":"上门维修","service_id":"1","tel_phone":"15903806002","transaction_id":"","uname":"晨曦吻过彩虹的脸庞"}
     * message : 请求成功
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * add_time : 2019-04-15 13:24:35
         * address : 海淀区上地科技大厦(上地西路东)
         * apply_user_id : 41
         * apply_user_star : 0
         * area_x : 40.049627278645836
         * area_y : 116.299609375
         * car_id : 1
         * car_name : 电动车
         * car_sign : 绿能
         * car_sign_id : 6
         * city : 2
         * complete_time : 0
         * county : 10
         * delete_status : 1
         * evaluate_status : 0
         * expected_time : 30
         * handle_order_num : 2
         * handle_time : 0
         * handle_user_id : 51
         * handle_user_name :
         * handle_user_phone :
         * handle_user_star : 0
         * handle_user_type : 金牌维修站
         * id : 17
         * img : http://lxcapi.nengyuanbox.com/Public/Upload/2019-04-15/small_5cb41593e743d.jpg
         * money : 3.33
         * order_detail : [{"detail":"订单提交","time":1555305876},{"detail":"订单取消","time":1555305916}]
         * order_sn : LXC2019041505875832
         * order_status : 3
         * pay_time : 0
         * province : 2
         * question_desc : 1111
         * refund_status : 0
         * service : 上门维修
         * service_id : 1
         * tel_phone : 15903806002
         * transaction_id :
         * uname : 晨曦吻过彩虹的脸庞
         */

        private String add_time;
        private String address;
        private String apply_user_id;
        private String apply_user_star;
        private String apply_user_evaluate;
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
        private String handle_order_num;
        private String handle_time;
        private String handle_user_id;
        private String handle_user_name;
        private String handle_user_phone;
        private String handle_user_star;
        private String handle_user_type;
        private String id;
        private String img;
        private String money;
        private String order_sn;
        private String order_status;
        private String pay_time;
        private String province;
        private String question_desc;
        private String refund_status;
        private String service;
        private String service_id;
        private String service_telephone;
        private String tel_phone;
        private String transaction_id;
        private String uname;
        private List<OrderDetailBean> order_detail;

        public String getApply_user_evaluate() {
            return apply_user_evaluate;
        }

        public void setApply_user_evaluate(String apply_user_evaluate) {
            this.apply_user_evaluate = apply_user_evaluate;
        }

        public String getService_telephone() {
            return service_telephone;
        }

        public void setService_telephone(String service_telephone) {
            this.service_telephone = service_telephone;
        }

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

        public String getApply_user_star() {
            return apply_user_star;
        }

        public void setApply_user_star(String apply_user_star) {
            this.apply_user_star = apply_user_star;
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

        public String getHandle_order_num() {
            return handle_order_num;
        }

        public void setHandle_order_num(String handle_order_num) {
            this.handle_order_num = handle_order_num;
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

        public String getHandle_user_name() {
            return handle_user_name;
        }

        public void setHandle_user_name(String handle_user_name) {
            this.handle_user_name = handle_user_name;
        }

        public String getHandle_user_phone() {
            return handle_user_phone;
        }

        public void setHandle_user_phone(String handle_user_phone) {
            this.handle_user_phone = handle_user_phone;
        }

        public String getHandle_user_star() {
            return handle_user_star;
        }

        public void setHandle_user_star(String handle_user_star) {
            this.handle_user_star = handle_user_star;
        }

        public String getHandle_user_type() {
            return handle_user_type;
        }

        public void setHandle_user_type(String handle_user_type) {
            this.handle_user_type = handle_user_type;
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
             * time : 1555305876
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
