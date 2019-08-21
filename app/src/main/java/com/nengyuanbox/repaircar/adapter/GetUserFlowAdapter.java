package com.nengyuanbox.repaircar.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetUserFlowBean;

import java.util.List;

public class GetUserFlowAdapter extends BaseQuickAdapter<GetUserFlowBean.DataBeanX.DataBean, BaseViewHolder> {


    public GetUserFlowAdapter(int layoutResId, @Nullable List<GetUserFlowBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetUserFlowBean.DataBeanX.DataBean item) {
//        order_type": "交易类型（1充值，2 支付订单,3 师傅入驻，4 门店入驻,5 提现，6 订单完结）",
        if (item.getOrder_type().equals("1")) {
            helper.setText(R.id.tv_order_type, "充值");

        } else if (item.getOrder_type().equals("2")) {
            helper.setText(R.id.tv_order_type, "支付订单");

        } else if (item.getOrder_type().equals("3")) {
            helper.setText(R.id.tv_order_type, "师傅入驻");

        } else if (item.getOrder_type().equals("4")) {
            helper.setText(R.id.tv_order_type, "门店入驻");

        } else if (item.getOrder_type().equals("5")) {
            helper.setText(R.id.tv_order_type, "提现");

        } else if (item.getOrder_type().equals("6")) {
            helper.setText(R.id.tv_order_type, "订单完结");

        }

        if (item.getOperation_type().equals("0")) {
            helper.setText(R.id.tv_money, "-" + item.getMoney());
        } else {
            helper.setText(R.id.tv_money, "+" + item.getMoney());
        }
        helper.setText(R.id.tv_time,(item.getTime()))
                .setText(R.id.tv_balance, item.getBalance());

    }
}
