package com.nengyuanbox.repaircar.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.MapNameBean;

import java.util.List;

public class MapNameAdapter extends BaseQuickAdapter<MapNameBean,BaseViewHolder> {



    public MapNameAdapter(int layoutResId, @Nullable List<MapNameBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapNameBean item) {
          helper.setText(R.id.tv_map_name,item.getName())
                  .setText(R.id.tv_map_address,item.getAddr());

    }
}
