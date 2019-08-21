package com.nengyuanbox.repaircar.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.eventbus.WXPayEventBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX);
        api.handleIntent(getIntent(), this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title_name.setText("微信支付");

    }

    @Override
    protected void onStart() {
        super.onStart();
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }


    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("Feng", "onPayFinish, errCode = " + baseResp.errCode);



        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == -1) {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new WXPayEventBean("0"));
                finish();
            }
            if (baseResp.errCode == 0) {

                Toast.makeText(this, "支付完成", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new WXPayEventBean("1"));
                finish();
            }

            if (baseResp.errCode == -2) {
                Toast.makeText(this, "支付已取消", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new WXPayEventBean("2"));
                finish();
            }
        }

    }



}