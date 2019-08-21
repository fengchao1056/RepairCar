package com.nengyuanbox.repaircar.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetGoodsOrdersBean;

import java.util.List;

public class NoPayMentGoodsAdapter extends BaseQuickAdapter<GetGoodsOrdersBean.DataBeanX.DataBean, BaseViewHolder> {


    public NoPayMentGoodsAdapter(int layoutResId, @Nullable List<GetGoodsOrdersBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetGoodsOrdersBean.DataBeanX.DataBean item) {


        helper.setText(R.id.tv_enent_goods_order, "订单号：" + item.getOrder_sn() )
                .setText(R.id.tv_enent_goods_oldprice, "下单时间："+(item.getAddtime()))
                .setText(R.id.tv_enent_goods_price, "￥"+item.getPrice())
                .addOnClickListener(R.id.bt_goods_revocation)
                .addOnClickListener(R.id.bt_enent_goods_buy);
//             if (item.getPay_type().equals("0")){
//                 helper.getView(R.id.bt_goods_revocation).setVisibility(View.VISIBLE);
//                 helper.getView(R.id.bt_enent_goods_buy).setVisibility(View.GONE);
//             }

        Glide.with(mContext).load(item.getGoods_img())
                .error(R.drawable.requestout)
                .placeholder(R.drawable.requestout)
                .crossFade().into((ImageView) helper.getView(R.id.iv_enent_goods_img));

}

}
