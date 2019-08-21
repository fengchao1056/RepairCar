package com.nengyuanbox.repaircar.adapter;


import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetMasterGoods;

import java.util.List;

public class GetMasterGoodsAdapter extends BaseQuickAdapter<GetMasterGoods.DataBeanX.DataBean, BaseViewHolder> {


    public GetMasterGoodsAdapter(int layoutResId, @Nullable List<GetMasterGoods.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetMasterGoods.DataBeanX.DataBean item) {
        TextView tv_enent_goods_oldprice = helper.getView(R.id.tv_enent_goods_oldprice);
        tv_enent_goods_oldprice.setText("原价：￥"+item.getGoods_price());
        tv_enent_goods_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 设置中划线并加清晰

        helper.setText(R.id.tv_enent_goods_num, "可购" + item.getGoods_num() + "件")
                .setText(R.id.tv_enent_goods_price, "￥"+item.getGoods_master_price())
                .addOnClickListener(R.id.bt_enent_goods_buy)
                .setText(R.id.tv_enent_goods_name, item.getGoods_name());
        Glide.with(mContext).load(item.getGoods_img())
                .error(R.drawable.requestout)
                .placeholder(R.drawable.requestout)
                .crossFade().into((ImageView) helper.getView(R.id.iv_enent_goods_img));

}

}
