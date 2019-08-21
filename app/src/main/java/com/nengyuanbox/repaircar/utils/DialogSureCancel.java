package com.nengyuanbox.repaircar.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;


/**
 * 确认 取消 Dialog
 */
public class DialogSureCancel extends RxDialog {



    private TextView tv_title,tv_text,tv_cancel,tv_sure;



    public DialogSureCancel(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogSureCancel(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogSureCancel(Context context) {
        super(context);
        initView();
    }

    public DialogSureCancel(Activity context) {
        super(context);
        initView();
    }

    public DialogSureCancel(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }


    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public TextView getTv_text() {
        return tv_text;
    }

    public void setTv_text(TextView tv_text) {
        this.tv_text = tv_text;
    }

    public TextView getTv_cancel() {
        return tv_cancel;
    }

    public void setTv_cancel(TextView tv_cancel) {
        this.tv_cancel = tv_cancel;
    }

    public TextView getTv_sure() {
        return tv_sure;
    }

    public void setTv_sure(TextView tv_sure) {
        this.tv_sure = tv_sure;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sure_false, null);

        tv_sure = (TextView) dialogView.findViewById(R.id.tv_sure);
        tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        tv_text = (TextView) dialogView.findViewById(R.id.tv_text);
        tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);

        setContentView(dialogView);
    }
}