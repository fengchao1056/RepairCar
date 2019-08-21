package com.nengyuanbox.repaircar.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.view.CircleImageView;


/**
 * 查询抢单弹窗
 */
public class Dialog_getOrderDetails extends RxDialog {
    TextView tv_master_name;
    TextView tv_store_star;
    TextView tv_order_num;
    TextView tv_repair_experience;
    TextView tv_master_phone;
    CircleImageView iv_imagehead;
    TextView tv_take_order_type;
    Button bt_invite_setlled;


//  private RecyclerView rv_store_order_list;

    public Dialog_getOrderDetails(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public Dialog_getOrderDetails(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public Dialog_getOrderDetails(Context context) {
        super(context);
        initView();
    }

    public Dialog_getOrderDetails(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public TextView getTv_master_name() {
        return tv_master_name;
    }

    public void setTv_master_name(TextView tv_master_name) {
        this.tv_master_name = tv_master_name;
    }

    public TextView getTv_store_star() {
        return tv_store_star;
    }

    public void setTv_store_star(TextView tv_store_star) {
        this.tv_store_star = tv_store_star;
    }

    public TextView getTv_order_num() {
        return tv_order_num;
    }

    public void setTv_order_num(TextView tv_order_num) {
        this.tv_order_num = tv_order_num;
    }

    public TextView getTv_repair_experience() {
        return tv_repair_experience;
    }

    public void setTv_repair_experience(TextView tv_repair_experience) {
        this.tv_repair_experience = tv_repair_experience;
    }

    public TextView getTv_master_phone() {
        return tv_master_phone;
    }

    public void setTv_master_phone(TextView tv_master_phone) {
        this.tv_master_phone = tv_master_phone;
    }

    public CircleImageView getIv_imagehead() {
        return iv_imagehead;
    }

    public void setIv_imagehead(CircleImageView iv_imagehead) {
        this.iv_imagehead = iv_imagehead;
    }

    public TextView getTv_take_order_type() {
        return tv_take_order_type;
    }

    public void setTv_take_order_type(TextView tv_take_order_type) {
        this.tv_take_order_type = tv_take_order_type;
    }

    public Button getBt_invite_setlled() {
        return bt_invite_setlled;
    }

    public void setBt_invite_setlled(Button bt_invite_setlled) {
        this.bt_invite_setlled = bt_invite_setlled;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_store_order_details, null);
//        rv_store_order_list = dialogView.findViewById(R.id.rv_store_order_list);
         tv_master_name = dialogView.findViewById(R.id.tv_master_name);
         tv_store_star = dialogView.findViewById(R.id.tv_store_star);
         tv_order_num = dialogView.findViewById(R.id.tv_order_num);
         tv_repair_experience = dialogView.findViewById(R.id.tv_repair_experience);
         tv_master_phone = dialogView.findViewById(R.id.tv_master_phone);
        iv_imagehead = dialogView.findViewById(R.id.iv_imagehead);
         tv_take_order_type = dialogView.findViewById(R.id.tv_take_order_type);
          bt_invite_setlled = dialogView.findViewById(R.id.bt_invite_setlled);
        setContentView(dialogView);
    }


}
