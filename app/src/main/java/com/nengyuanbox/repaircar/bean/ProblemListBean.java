package com.nengyuanbox.repaircar.bean;

import java.util.List;

public class ProblemListBean {


    /**
     * code : 2000
     * data : {"data":[{"id":"1","title":"如何注册用户"},{"id":"3","title":"如何抢修订单"},{"id":"4","title":"xxxxxx"},{"id":"5","title":"xxxxxx"},{"id":"6","title":"aa"},{"id":"7","title":"aa"},{"id":"8","title":"aa"},{"id":"9","title":"aa"},{"id":"10","title":"cc"},{"id":"11","title":"xx"}],"total":"19","totalPage":2}
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
         * data : [{"id":"1","title":"如何注册用户"},{"id":"3","title":"如何抢修订单"},{"id":"4","title":"xxxxxx"},{"id":"5","title":"xxxxxx"},{"id":"6","title":"aa"},{"id":"7","title":"aa"},{"id":"8","title":"aa"},{"id":"9","title":"aa"},{"id":"10","title":"cc"},{"id":"11","title":"xx"}]
         * total : 19
         * totalPage : 2
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
             * id : 1
             * title : 如何注册用户
             */

            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
