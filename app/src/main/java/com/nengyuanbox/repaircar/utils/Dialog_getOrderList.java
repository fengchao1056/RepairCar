package com.nengyuanbox.repaircar.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;


/**
 *  查询抢单弹窗
 */
public class Dialog_getOrderList extends RxDialog {


    TextView tv_fault_type;
    TextView tv_question_desc;
    TextView tv_car_sign;
    TextView tv_tel_phone;
    TextView tv_expected_time;
    TextView tv_service_address;
    Button bt_grab_order;

    public Dialog_getOrderList(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public Dialog_getOrderList(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public Dialog_getOrderList(Context context) {
        super(context);
        initView();
    }

    public Dialog_getOrderList(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public TextView getTv_fault_type() {
        return tv_fault_type;
    }

    public TextView getTv_question_desc() {
        return tv_question_desc;
    }

    public TextView getTv_car_sign() {
        return tv_car_sign;
    }

    public TextView getTv_tel_phone() {
        return tv_tel_phone;
    }

    public TextView getTv_expected_time() {
        return tv_expected_time;
    }

    public TextView getTv_service_address() {
        return tv_service_address;
    }

    public Button getBt_grab_order() {
        return bt_grab_order;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_grab_the_order, null);
         tv_fault_type = dialogView.findViewById(R.id.tv_fault_type);
         tv_question_desc = dialogView.findViewById(R.id.tv_question_desc);
         tv_car_sign = dialogView.findViewById(R.id.tv_car_sign);
         tv_tel_phone = dialogView.findViewById(R.id.tv_tel_phone);
         tv_expected_time = dialogView.findViewById(R.id.tv_expected_time);
         tv_service_address = dialogView.findViewById(R.id.tv_service_address);
         bt_grab_order = dialogView.findViewById(R.id.bt_grab_order);
        setContentView(dialogView);
    }


}
