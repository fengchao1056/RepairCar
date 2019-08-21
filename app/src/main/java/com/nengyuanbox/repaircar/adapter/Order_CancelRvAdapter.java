package com.nengyuanbox.repaircar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.GetOrderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//订单取消
public class Order_CancelRvAdapter extends RecyclerView.Adapter<Order_CancelRvAdapter.ViewHolder> {


    private List<GetOrderListBean.DataBeanX.DataBean> list = new ArrayList<>();
    private Context context;

    public Order_CancelRvAdapter(List<GetOrderListBean.DataBeanX.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_complete, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_repair_time.setText(list.get(position).getAdd_time()+"  报修");
//        "order_status": "订单状态 1 待付款 2 待抢单 3 待处理 4 待处理已出发 5已完成 6已退款",
//                "refund_status": "订单状态 0 无退款 1 退款中 2完成退款",
//        "refund_status": "订单状态 0 无退款 1 退款中 2完成退款",
        if (list.get(position).getRefund_status().equals("1")){
            holder.tv_state.setText("退款中");
        }else if (list.get(position).getOrder_status().equals("2")){
            holder.tv_state.setText("完成退款");
        }else if (list.get(position).getOrder_status().equals("0")){
            holder.tv_state.setText("无退款");
        }else {
            holder.tv_state.setText("已退款");
        }
        holder.ll_repair_star.setVisibility(View.GONE);
        StringBuffer  stringBuffer=new StringBuffer();
        stringBuffer.append(list.get(position).getUname())
                .append("   ")
                .append(list.get(position).getCar_sign())
                .append(list.get(position).getCar_name())
                .append("   ")
                .append(list.get(position).getService());
        holder.tv_client_info.setText(stringBuffer.toString());
        holder.tv_client_address.setText(list.get(position).getAddress());
//           期望時間
        holder.tv_expectation_time.setText(list.get(position).getExpected_time());
        holder.tv_repair_star.setText(list.get(position).getHandle_time());







        holder.ll_item_processed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onLinearClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_state)
        TextView tv_state;
        @BindView(R.id.tv_repair_time)
        TextView tv_repair_time;
        @BindView(R.id.ll_departed)
        LinearLayout ll_departed;
        @BindView(R.id.tv_client_info)
        TextView tv_client_info;
        @BindView(R.id.tv_repair_star)
        TextView tv_repair_star;

        @BindView(R.id.tv_expectation_time)
        TextView tv_expectation_time;
        @BindView(R.id.tv_client_address)
        TextView tv_client_address;
        @BindView(R.id.ll_item_processed)
        LinearLayout ll_item_processed;

        @BindView(R.id.ll_repair_star)
        LinearLayout ll_repair_star;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        //
        void onLinearClick(int itemPosition);
        //        点击出发
        void onSetOffClick(int itemPosition);

        //        点击完成
        void onCompleteClick(int itemPosition);
        //        点击撤销
        void onCancleClick(int itemPosition);

    }
}
