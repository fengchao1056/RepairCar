package com.nengyuanbox.repaircar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.ProvinceBean;
import com.nengyuanbox.repaircar.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 省市区适配器
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProvinceAdapter extends BaseAdapter {
    private List<ProvinceBean.DataBean> dataBean = new ArrayList<>();
    private int defaultCount = 0;
    private Activity mContext;

    public ProvinceAdapter(List<ProvinceBean.DataBean> datas, Activity mContext) {
        if (dataBean != null) {
            this.dataBean = datas;
        } else {
            this.dataBean = new ArrayList<>();
        }
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (dataBean.size() != 0) {
            return dataBean.size();
        }
        return defaultCount;
    }

    @Override
    public Object getItem(int position) {
        return dataBean.get(position);
    }

    public void setData(List<ProvinceBean.DataBean> data) {
        if (dataBean != null) {
            this.dataBean = data;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mGridItemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.province_list_item, null);
            mGridItemHolder = new ViewHolder(convertView);
            convertView.setTag(mGridItemHolder);
        }
        mGridItemHolder = (ViewHolder) convertView.getTag();
        StringUtil.setTextString(dataBean.get(position).getName(), mGridItemHolder.tvName);
        return convertView;
    }



    static

    public class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
