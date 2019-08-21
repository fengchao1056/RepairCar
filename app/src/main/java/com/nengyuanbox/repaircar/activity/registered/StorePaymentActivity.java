package com.nengyuanbox.repaircar.activity.registered;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetWXSignBean;
import com.nengyuanbox.repaircar.bean.WxPayBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.eventbus.WXPayEventBean;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSure;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

//  门店认证付款界面
public class StorePaymentActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_price_old)
    TextView tv_price_old;
    @BindView(R.id.ck_agreement)
    CheckBox ck_agreement;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.bt_payment)
    Button bt_payment;
    private IWXAPI api;
    private PayReq request;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_payment;
    }

    @Override
    protected void initView() {
//        初始化微信支付
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        api.registerApp(Constant.APP_ID_WX);
        request = new PayReq();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void setListener() {
        tv_price.setText(getIntent().getStringExtra("order_money"));
        if (!RxDataTool.isEmpty(getIntent().getStringExtra("order_money_old"))){
            tv_price_old.setText("原价：￥"+getIntent().getStringExtra("order_money_old"));
            tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 设置中划线并加清晰
        }

        iv_back.setOnClickListener(this);
        iv_back.setImageResource(R.drawable.icon_button);
        tv_title_name.setText("门店认证");
        ck_agreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bt_payment.setEnabled(true);
                } else {
                    bt_payment.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_payment:
//                RxToast.normal("已同意条款");

                getWxPay();

                break;

        }
    }




    //    小程序支付统一下单
    private void getWxPay() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
            jsonObject.put("order_sn",getIntent().getStringExtra("order_sn"));
            jsonObject.put("order_money",getIntent().getStringExtra("order_money"));
            jsonObject.put("app_user_id",uid);
            jsonObject.put("tradeType","APP");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETWXPAY, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("小程序支付统一下单：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        WxPayBean singleBean = JsonUtil.getSingleBean(response.toString(), WxPayBean.class);
                        getWXSign(singleBean.getData().getPrepay_id());
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StorePaymentActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString()+responseString);
                CommonDialog.showInfoDialog(StorePaymentActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StorePaymentActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StorePaymentActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }
    private void getWXSign(String partnerId) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("prepay_id", partnerId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETWXSIGN, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("微信app支付sign：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        GetWXSignBean singleBean = JsonUtil.getSingleBean(response.toString(), GetWXSignBean.class);

                        request.appId = singleBean.getData().getAppid();
                        request.partnerId = singleBean.getData().getPartnerid();
                        request.prepayId = singleBean.getData().getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = singleBean.getData().getNoncestr();
                        request.timeStamp = singleBean.getData().getTimestamp() + "";
                        request.sign = singleBean.getData().getSign();
                        api.sendReq(request);


                        Toast.makeText(StorePaymentActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StorePaymentActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString() + responseString);
                CommonDialog.showInfoDialog(StorePaymentActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StorePaymentActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StorePaymentActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }
    //    微信支付成功回调
    @Subscribe
    public void getWXPayEvent(WXPayEventBean wxPayEventBean){

        if (!RxDataTool.isEmpty(wxPayEventBean.getIspay())){
            if (wxPayEventBean.getIspay().equals("1")){
                showdialog("支付成功！");
            }
        }

    }

    private void showdialog( final String string) {
        final DialogSure rxDialogSure = new DialogSure(StorePaymentActivity.this);//提示弹窗
        rxDialogSure.setTitle("");
        rxDialogSure.setContent(string);
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();
                if (string.equals("支付成功！")) {
//                    Intent intent1 = new Intent(StorePaymentActivity.this, StoreSuccessfulActivity.class);
                    Intent intent = new Intent(StorePaymentActivity.this, StoreSubmittedSuccessfullyActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        rxDialogSure.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
