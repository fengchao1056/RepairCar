package com.nengyuanbox.repaircar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.bean.RepairTypeBean;

import java.util.ArrayList;
import java.util.List;

public class MasterCertificationRvAdapter extends RecyclerView.Adapter<MasterCertificationRvAdapter.ViewHolder> {

    private List<RepairTypeBean> list = new ArrayList<>();
    private Context context;

    public MasterCertificationRvAdapter(List<RepairTypeBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_repair_type, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
         holder.ll_type.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


             }
         });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_repair_type;
        LinearLayout ll_type;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_repair_type=itemView.findViewById(R.id.tv_repair_type);
            ll_type=itemView.findViewById(R.id.ll_type);
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick( int itemPosition,boolean  isCheck);
    }
}
