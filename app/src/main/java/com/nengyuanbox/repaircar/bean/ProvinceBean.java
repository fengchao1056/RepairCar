package com.nengyuanbox.repaircar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 省市区
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ProvinceBean implements Serializable {


    /**
     * code : 2000
     * message : 请求成功
     * data : [{"id":"67","name":"路南区"},{"id":"68","name":"路北区"},{"id":"69","name":"古冶区"},{"id":"70","name":"开平区"},{"id":"71","name":"丰南区"},{"id":"72","name":"丰润区"},{"id":"73","name":"滦县"},{"id":"74","name":"滦南县"},{"id":"75","name":"乐亭县"},{"id":"76","name":"迁西县"},{"id":"77","name":"玉田县"},{"id":"78","name":"唐海县"},{"id":"79","name":"遵化市"},{"id":"80","name":"迁安市"}]
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
         * id : 67
         * name : 路南区
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
