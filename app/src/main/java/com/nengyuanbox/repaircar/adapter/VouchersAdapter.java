package com.nengyuanbox.repaircar.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetVouchersListBean;

import java.util.List;

public class VouchersAdapter extends BaseQuickAdapter<GetVouchersListBean.DataBeanX.DataBean,BaseViewHolder> {



    public VouchersAdapter(int layoutResId, @Nullable List<GetVouchersListBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetVouchersListBean.DataBeanX.DataBean item) {
         StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append( (item.getStart_time()))
                .append("- ")
                .append( (item.getEnd_time()));
          helper.setText(R.id.tv_voucher_name,item.getName())
                  .setText(R.id.tv_voucher_time,stringBuffer.toString())
                  .setText(R.id.tv_voucher_price,item.getPrice());

    }
}
