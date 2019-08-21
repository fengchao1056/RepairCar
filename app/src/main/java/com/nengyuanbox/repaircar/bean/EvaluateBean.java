package com.nengyuanbox.repaircar.bean;

/**
 * Created by LXL on 2017/12/8.
 */

public class EvaluateBean {
    public String name;
    public boolean checked;

    public EvaluateBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EvaluateBean{" +
                "name='" + name + '\'' +
                ", checked=" + checked +
                '}';
    }
}
