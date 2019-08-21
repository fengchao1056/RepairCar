package com.nengyuanbox.repaircar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetOrderInfoBean;

import java.util.List;

public class OrderTrackAdapter extends RecyclerView.Adapter<OrderTrackAdapter.ViewHolder> {


    private List<GetOrderInfoBean.DataBean.OrderDetailBean> list;
    private Context context;

    public OrderTrackAdapter(List<GetOrderInfoBean.DataBean.OrderDetailBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order_details, tv_order_time, tv_long;
        ImageView tv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_details = itemView.findViewById(R.id.tv_order_details);
            tv_img = itemView.findViewById(R.id.tv_img);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);
            tv_long = itemView.findViewById(R.id.tv_long);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_track, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(list.get(position).getDetail())) {
            String detail = list.get(position).getDetail();
            holder.tv_order_time.setText(list.get(position).getTime());
            holder.tv_order_details.setText(detail);

        }


        if (position == 0) {
            holder.tv_img.setImageResource(R.mipmap.top_orange);
        } else {
            holder.tv_img.setImageResource(R.mipmap.top_hui);
        }

        if (position == list.size() - 1) {
            holder.tv_long.setVisibility(View.GONE);
        } else {
            holder.tv_long.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
