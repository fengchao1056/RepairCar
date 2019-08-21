package com.nengyuanbox.repaircar.eventbus;

public class GoodsDetailEventBean {


    private  String goods_detail;
    private  String goods_parameter;
    private  String goods_shop_detail;

    public GoodsDetailEventBean(String goods_detail, String goods_parameter, String goods_shop_detail) {
        this.goods_detail = goods_detail;
        this.goods_parameter = goods_parameter;
        this.goods_shop_detail = goods_shop_detail;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getGoods_parameter() {
        return goods_parameter;
    }

    public void setGoods_parameter(String goods_parameter) {
        this.goods_parameter = goods_parameter;
    }

    public String getGoods_shop_detail() {
        return goods_shop_detail;
    }

    public void setGoods_shop_detail(String goods_shop_detail) {
        this.goods_shop_detail = goods_shop_detail;
    }
}
