package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class GetUserFlowBean {


    /**
     * code : 2000
     * data : {"data":[{"app_user_id":"51","balance":"3.33","id":"173","money":"3.33","operation_type":"1","order_sn":"LXC2019041505875832","order_type":"6","pay_type":"2","time":"1555329296"},{"app_user_id":"51","balance":"6.66","id":"174","money":"3.33","operation_type":"1","order_sn":"LXC2019041505875832","order_type":"6","pay_type":"2","time":"1555330122"},{"app_user_id":"51","balance":"9.99","id":"175","money":"3.33","operation_type":"1","order_sn":"LXC2019041505875832","order_type":"6","pay_type":"2","time":"1555416532"}],"total":"3","totalPage":1}
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
         * data : [{"app_user_id":"51","balance":"3.33","id":"173","money":"3.33","operation_type":"1","order_sn":"LXC2019041505875832","order_type":"6","pay_type":"2","time":"1555329296"},{"app_user_id":"51","balance":"6.66","id":"174","money":"3.33","operation_type":"1","order_sn":"LXC2019041505875832","order_type":"6","pay_type":"2","time":"1555330122"},{"app_user_id":"51","balance":"9.99","id":"175","money":"3.33","operation_type":"1","order_sn":"LXC2019041505875832","order_type":"6","pay_type":"2","time":"1555416532"}]
         * total : 3
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
             * app_user_id : 51
             * balance : 3.33
             * id : 173
             * money : 3.33
             * operation_type : 1
             * order_sn : LXC2019041505875832
             * order_type : 6
             * pay_type : 2
             * time : 1555329296
             */

            private String app_user_id;
            private String balance;
            private String id;
            private String money;
            private String operation_type;
            private String order_sn;
            private String order_type;
            private String pay_type;
            private String time;

            public String getApp_user_id() {
                return app_user_id;
            }

            public void setApp_user_id(String app_user_id) {
                this.app_user_id = app_user_id;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getOperation_type() {
                return operation_type;
            }

            public void setOperation_type(String operation_type) {
                this.operation_type = operation_type;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
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
