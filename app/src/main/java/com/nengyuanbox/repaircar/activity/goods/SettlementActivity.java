package com.nengyuanbox.repaircar.activity.goods;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.registered.MasterCardCertificationActivity;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.AddMasterGoodsOrderBean;
import com.nengyuanbox.repaircar.bean.GetGoodsInfoBean;
import com.nengyuanbox.repaircar.bean.GetMasterAddressBean;
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
import com.nengyuanbox.repaircar.view.ContainsEmojiEditText;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//结算界面
public class SettlementActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_add_address)
    TextView tv_add_address;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.tv_receiver)
    TextView tv_receiver;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_delivery_name)
    TextView tv_delivery_name;
    @BindView(R.id.iv_goods_img)
    ImageView iv_goods_img;
    @BindView(R.id.tv_enent_goods_name)
    TextView tv_enent_goods_name;
    @BindView(R.id.tv_enent_goods_price)
    TextView tv_enent_goods_price;
    @BindView(R.id.tv_enent_goods_oldprice)
    TextView tv_enent_goods_oldprice;
    @BindView(R.id.iv_reduce)
    ImageView iv_reduce;
    @BindView(R.id.et_acount)
    EditText et_acount;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.ll_add_shop)
    LinearLayout ll_add_shop;
    @BindView(R.id.ll_address_modify)
    LinearLayout ll_address_modify;
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.bt_pay_order)
    Button bt_pay_order;
    @BindView(R.id.tv_order_money)
    TextView tv_order_money;
    @BindView(R.id.tv_freight)
    TextView tv_freight;
    @BindView(R.id.et_remark)
    ContainsEmojiEditText et_remark;
    private boolean is_address = false;//是否有收货地址
    private String price;
    private IWXAPI api;
    private PayReq request;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settlement;
    }

    @Override
    protected void initView() {
        //        初始化微信支付
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        api.registerApp(Constant.APP_ID_WX);
        request = new PayReq();
        getOrderList();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void setListener() {
        tv_title_name.setText("结算");

    }

    private void getOrderList() {
        CommonDialog.showProgressDialog(SettlementActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("goods_id", getIntent().getStringExtra("goods_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(SettlementActivity.this, NetUtils.POST, UrlConstant.GETGOODSINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("已上架商品的详情" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetGoodsInfoBean getGoodsInfoBean = JsonUtil.getSingleBean(response.toString(), GetGoodsInfoBean.class);
                        GetGoodsInfoBean.DataBean data = getGoodsInfoBean.getData();
                        if (data != null) {
                            tv_enent_goods_name.setText(data.getGoods_name());
                            tv_enent_goods_oldprice.setText("原价：￥" + data.getGoods_price());
                            tv_enent_goods_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 设置中划线并加清晰
                            tv_enent_goods_price.setText("￥" + data.getGoods_master_price());
                            tv_order_money.setText("￥" + data.getGoods_master_price());
                            tv_total_price.setText("￥" + data.getGoods_master_price());
                            price = data.getGoods_master_price();
//                            tv_goods_num.setText("剩余"+data.getGoods_num()+"件");


                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(SettlementActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("已上架商品的详情baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMasterAddress();
    }

    //    查询师傅的收货信息
    private void getMasterAddress() {
        CommonDialog.showProgressDialog(SettlementActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", "1");
            jsonObject.put("pagesize", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(SettlementActivity.this, NetUtils.POST, UrlConstant.GETMASTERADDRESS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("查询师傅的收货信息" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetMasterAddressBean getMasterAddressBean = JsonUtil.getSingleBean(response.toString(), GetMasterAddressBean.class);
                        GetMasterAddressBean.DataBean data = getMasterAddressBean.getData();

                        if (!RxDataTool.isEmpty(data.getAddress())) {
                            is_address = true;
                            ll_address.setVisibility(View.VISIBLE);
                            tv_add_address.setVisibility(View.GONE);

                            StringBuffer stringBuffer = new StringBuffer();
                            if (!RxDataTool.isEmpty(data.getReturn_phone())) {
                                String phone_asterisk = StringUtil.getPhone_Asterisk(data.getReturn_phone());

                                stringBuffer.append("收货人：")
                                        .append(data.getReturn_name())
                                        .append("   ")
                                        .append(phone_asterisk);
                                StringBuffer stringBuffer1 = new StringBuffer()
                                        .append(data.getProvince())
                                        .append(data.getCity())
                                        .append(data.getCounty())
                                        .append(data.getAddress());
                                tv_address.setText(stringBuffer1.toString());
                                tv_receiver.setText(stringBuffer.toString());
                            }
                        } else {
                            is_address = false;
                            ll_address.setVisibility(View.GONE);
                            tv_add_address.setVisibility(View.VISIBLE);
                            tv_address.setText("");
                            tv_receiver.setText("");
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(SettlementActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("查询师傅的收货信息baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("查询师傅的收货信息baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("查询师傅的收货信息baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    支付商品订单
    private void addMasterGoodsOrder() {
        CommonDialog.showProgressDialog(SettlementActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("goods_id", getIntent().getStringExtra("goods_id"));
            jsonObject.put("goods_num", "1");
            jsonObject.put("money", price);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(SettlementActivity.this, NetUtils.POST, UrlConstant.ADDMASTERGOODSORDER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("支付商品订单" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        AddMasterGoodsOrderBean addMasterGoodsOrderBean = JsonUtil.getSingleBean(response.toString(), AddMasterGoodsOrderBean.class);
                        AddMasterGoodsOrderBean.DataBean data = addMasterGoodsOrderBean.getData();
                        if (data != null) {

                            getWxPay(data.getOrder_sn(), data.getVip_money());
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(SettlementActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("支付商品订单baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("支付商品订单baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("查询师傅的收货信息baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    小程序支付统一下单
    private void getWxPay(String order_sn, String money) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
            jsonObject.put("order_sn", order_sn);
            jsonObject.put("order_money", money);
            jsonObject.put("app_user_id", uid);
            jsonObject.put("tradeType", "APP");


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
                        LoginExpirelUtils.getLoginExpirel(SettlementActivity.this);

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
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
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


                        Toast.makeText(SettlementActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(SettlementActivity.this);

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
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(SettlementActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    @OnClick({R.id.iv_back, R.id.ll_address, R.id.bt_pay_order, R.id.ll_address_modify})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_address:
                intent = new Intent(SettlementActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.ll_address_modify:
                intent = new Intent(SettlementActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.bt_pay_order:
                if (is_address) {
                    addMasterGoodsOrder();

                } else {
                    RxToast.normal("请填写收货地址信息");
                    return;
                }

                break;
        }
    }


    //    微信支付成功回调
    @Subscribe
    public void getWXPayEvent(WXPayEventBean wxPayEventBean) {

        if (!RxDataTool.isEmpty(wxPayEventBean.getIspay())) {
            if (wxPayEventBean.getIspay().equals("1")) {
                showdialog("支付成功！");
            } else if (wxPayEventBean.getIspay().equals("0")) {
                showdialog("支付失败！");
            } else if (wxPayEventBean.getIspay().equals("2")) {
                showdialog("支付已取消！");
            }
        }

    }

    private void showdialog(final String string) {
        final DialogSure rxDialogSure = new DialogSure(SettlementActivity.this);//提示弹窗
        rxDialogSure.setTitle("");
        rxDialogSure.setContent(string);
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();
                if (string.equals("支付成功！")) {
                    Intent intent = new Intent(SettlementActivity.this, MasterCardCertificationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SettlementActivity.this, NoPaymentListActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        rxDialogSure.show();
    }


}
