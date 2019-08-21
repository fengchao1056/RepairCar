package com.nengyuanbox.repaircar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.CancelledOrderBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.vondear.rxtool.RxDataTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//订单待处理
public class Order_ToProcessedRvAdapter extends RecyclerView.Adapter<Order_ToProcessedRvAdapter.ViewHolder> {


    private List<CancelledOrderBean.DataBeanX.DataBean> list = new ArrayList<>();
    private Context context;

    public Order_ToProcessedRvAdapter(List<CancelledOrderBean.DataBeanX.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_toprocessed, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_repair_time.setText(list.get(position).getAdd_time() + "  报修");
//        "order_status": "订单状态 1 待付款 2 待抢单 3 待处理 4 待处理已出发 5已完成 6已退款",
//                "refund_status": "订单状态 0 无退款 1 退款中 2完成退款",


        StringBuffer stringBuffer = new StringBuffer();
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

        if (list.get(position).getOrder_status().equals("1")) {
            holder.tv_state.setText("待付款");
        } else if (list.get(position).getOrder_status().equals("2")) {
            holder.tv_state.setText("待抢单");
        } else if (list.get(position).getOrder_status().equals("3")) {
            holder.tv_state.setText("抢单成功");
            holder.bt_cemplete.setText("出发");
        } else if (list.get(position).getOrder_status().equals("4")) {
            holder.tv_state.setText("已出发");
            holder.bt_cemplete.setText("完成");
        } else if (list.get(position).getOrder_status().equals("5")) {
            holder.tv_state.setText("已完成");
            holder.bt_cemplete.setText("完成");
        } else if (list.get(position).getOrder_status().equals("6")) {
            holder.tv_state.setText("已退款");
        }
        holder.bt_cemplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.bt_cemplete.getText().toString().equals("出发")) {
                    mItemClickListener.onSetOffClick(position);
                } else if (holder.bt_cemplete.getText().toString().equals("完成")) {
                    mItemClickListener.onCompleteClick(position);
                }
            }
        });
        holder.bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onCancleClick(position);
            }
        });

        String str_role = SharedPreferenceUtil.getString(context, Constant.ROLE, "");

//     判断角色  是门店  就显示出接单师傅  否则就隐藏接单师傅的选项
        if (!RxDataTool.isEmpty(str_role) && str_role.equals("store")) {
            holder.ll_order_receiving_master.setVisibility(View.VISIBLE);
            holder.tv_order_receiving_master.setText(list.get(position).getHandle_user_name());
            holder.ll_operation.setVisibility(View.GONE);
        } else {
            holder.ll_order_receiving_master.setVisibility(View.GONE);
            holder.tv_order_receiving_master.setText("");
            holder.ll_operation.setVisibility(View.VISIBLE);
        }


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
        @BindView(R.id.tv_expectation_time)
        TextView tv_expectation_time;
        @BindView(R.id.tv_client_address)
        TextView tv_client_address;
        @BindView(R.id.tv_order_receiving_master)//接单师傅姓名
                TextView tv_order_receiving_master;
        @BindView(R.id.bt_cancel)
        Button bt_cancel;
        @BindView(R.id.bt_cemplete)
        Button bt_cemplete;
        @BindView(R.id.ll_item_processed)
        LinearLayout ll_item_processed;
        @BindView(R.id.ll_operation) //如果进来是门店角色 ， 操作功能隐藏
                LinearLayout ll_operation;
        @BindView(R.id.ll_order_receiving_master) //如果进来是门店角色 ， 接单师傅显示栏显示
                LinearLayout ll_order_receiving_master;

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
