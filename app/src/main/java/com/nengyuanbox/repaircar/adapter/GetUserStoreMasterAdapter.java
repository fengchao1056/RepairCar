package com.nengyuanbox.repaircar.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetUserStoreMasterBean;

import java.util.List;

public class GetUserStoreMasterAdapter extends BaseQuickAdapter<GetUserStoreMasterBean.DataBean,BaseViewHolder> {



    public GetUserStoreMasterAdapter(int layoutResId, @Nullable List<GetUserStoreMasterBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetUserStoreMasterBean.DataBean item) {


        helper.setText(R.id.tv_master_name,item.getName())//师傅姓名
                .addOnClickListener(R.id.bt_unbind)
                  .setText(R.id.tv_repair_experience,item.getExperience())//维修经验
                  .setText(R.id.tv_master_phone,item.getPhone())//师傅手机号
                  .setText(R.id.tv_take_order_type,item.getService())//师傅维修类型
                  .setText(R.id.tv_order_num,item.getOrder_num()+"单");//师傅接单数量

    }
}
