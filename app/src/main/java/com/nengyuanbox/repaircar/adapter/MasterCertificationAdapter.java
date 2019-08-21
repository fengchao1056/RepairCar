package com.nengyuanbox.repaircar.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.RepairTypeBean;

import java.util.List;

public class MasterCertificationAdapter extends BaseQuickAdapter<RepairTypeBean,BaseViewHolder> {
    public MasterCertificationAdapter(int layoutResId, @Nullable List<RepairTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RepairTypeBean item) {
            helper.setText(R.id.tv_repair_type,item.getType());
        LinearLayout ll_type = helper.getView(R.id.ll_type);
        ll_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, ""+item.getType(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
