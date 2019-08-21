package com.nengyuanbox.repaircar.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.nengyuanbox.repaircar.R;


/**
 * 第三方地图弹窗
 */
public class Dialog_MapServiceDetails extends RxDialog {
    Button baidu_btn;
    Button gaode_btn;
    Button tencent_btn;
    Button cancel_btn2;



    public Dialog_MapServiceDetails(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public Dialog_MapServiceDetails(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public Dialog_MapServiceDetails(Context context) {
        super(context);
        initView();
    }

    public Dialog_MapServiceDetails(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public Button getBaidu_btn() {
        return baidu_btn;
    }

    public void setBaidu_btn(Button baidu_btn) {
        this.baidu_btn = baidu_btn;
    }

    public Button getGaode_btn() {
        return gaode_btn;
    }

    public void setGaode_btn(Button gaode_btn) {
        this.gaode_btn = gaode_btn;
    }

    public Button getTencent_btn() {
        return tencent_btn;
    }

    public void setTencent_btn(Button tencent_btn) {
        this.tencent_btn = tencent_btn;
    }

    public Button getCancel_btn2() {
        return cancel_btn2;
    }

    public void setCancel_btn2(Button cancel_btn2) {
        this.cancel_btn2 = cancel_btn2;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_map_service_details, null);
        baidu_btn = dialogView.findViewById(R.id.baidu_btn);
        gaode_btn = dialogView.findViewById(R.id.gaode_btn);
        tencent_btn = dialogView.findViewById(R.id.tencent_btn);
        cancel_btn2 = dialogView.findViewById(R.id.cancel_btn2);

        setContentView(dialogView);
    }


}
