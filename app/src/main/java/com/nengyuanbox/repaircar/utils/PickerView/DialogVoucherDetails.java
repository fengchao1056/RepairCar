package com.nengyuanbox.repaircar.utils.PickerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.utils.RxDialog;


/**
 * 优惠券弹窗详情
 */
public class DialogVoucherDetails extends RxDialog {

    TextView tv_voucher_name;
    ImageView iv_cross;
    WebView wb_view;
    Button bt_go_use;


    public DialogVoucherDetails(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogVoucherDetails(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogVoucherDetails(Context context) {
        super(context);
        initView();
    }

    public DialogVoucherDetails(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }


    public TextView getTv_voucher_name() {
        return tv_voucher_name;
    }

    public void setTv_voucher_name(TextView tv_voucher_name) {
        this.tv_voucher_name = tv_voucher_name;
    }

    public ImageView getIv_cross() {
        return iv_cross;
    }

    public void setIv_cross(ImageView iv_cross) {
        this.iv_cross = iv_cross;
    }

    public WebView getWb_view() {
        return wb_view;
    }

    public void setWb_view(WebView wb_view) {
        this.wb_view = wb_view;
    }

    public Button getBt_go_use() {
        return bt_go_use;
    }

    public void setBt_go_use(Button bt_go_use) {
        this.bt_go_use = bt_go_use;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_voucher_details, null);
         tv_voucher_name = (TextView) dialogView.findViewById(R.id.tv_voucher_name);
         iv_cross  = (ImageView) dialogView.findViewById(R.id.iv_cross);
         wb_view = (WebView) dialogView.findViewById(R.id.wb_view);
         bt_go_use = (Button) dialogView.findViewById(R.id.bt_go_use);
        setContentView(dialogView);
    }

}
