package com.nengyuanbox.repaircar.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.view.CircleImageView;


/**
 * 师傅认领门店弹窗
 */
public class Dialog_MasterClaimStore extends RxDialog {

    CircleImageView iv_imagehead;
    Button bt_invite_setlled;

    TextView tv_store_name;
    TextView tv_store_address;


//  private RecyclerView rv_store_order_list;

    public Dialog_MasterClaimStore(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public Dialog_MasterClaimStore(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public Dialog_MasterClaimStore(Context context) {
        super(context);
        initView();
    }

    public Dialog_MasterClaimStore(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public CircleImageView getIv_imagehead() {
        return iv_imagehead;
    }

    public void setIv_imagehead(CircleImageView iv_imagehead) {
        this.iv_imagehead = iv_imagehead;
    }

    public Button getBt_invite_setlled() {
        return bt_invite_setlled;
    }

    public void setBt_invite_setlled(Button bt_invite_setlled) {
        this.bt_invite_setlled = bt_invite_setlled;
    }

    public TextView getTv_store_name() {
        return tv_store_name;
    }

    public void setTv_store_name(TextView tv_store_name) {
        this.tv_store_name = tv_store_name;
    }

    public TextView getTv_store_address() {
        return tv_store_address;
    }

    public void setTv_store_address(TextView tv_store_address) {
        this.tv_store_address = tv_store_address;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_master_claim_store, null);

        iv_imagehead = dialogView.findViewById(R.id.iv_imagehead);
        bt_invite_setlled = dialogView.findViewById(R.id.bt_invite_setlled);
        tv_store_name = dialogView.findViewById(R.id.tv_store_name);
        tv_store_address = dialogView.findViewById(R.id.tv_store_address);

        setContentView(dialogView);
    }


}
