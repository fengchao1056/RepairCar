package com.nengyuanbox.repaircar.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.ProblemListBean;

import java.util.List;

public class ProblemAdapter extends BaseQuickAdapter<ProblemListBean.DataBeanX.DataBean,BaseViewHolder> {



    public ProblemAdapter(int layoutResId, @Nullable List<ProblemListBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProblemListBean.DataBeanX.DataBean item) {
          helper.setText(R.id.tv_problem,item.getTitle());

    }
}
