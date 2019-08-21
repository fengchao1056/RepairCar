package com.nengyuanbox.repaircar.activity.mine;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetGoodsInfoBean;
import com.nengyuanbox.repaircar.bean.GetMasterAddressBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//支付商品列表详情
public class PayOrderDetailsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_receiver)
    TextView tv_receiver;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.ll_address_modify)
    LinearLayout ll_address_modify;
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
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.bt_pay_order)
    Button bt_pay_order;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_order_details;
    }

    @Override
    protected void initView() {
        getMasterAddress();
    }

    @Override
    protected void setListener() {

    }

    //    查询师傅的收货信息
    private void getMasterAddress() {
        CommonDialog.showProgressDialog(PayOrderDetailsActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", "1");
            jsonObject.put("pagesize", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(PayOrderDetailsActivity.this, NetUtils.POST, UrlConstant.GETMASTERADDRESS, jsonObject, new JsonHttpResponseHandler() {


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
                        getOrderList();
                        if (!RxDataTool.isEmpty(data.getAddress())) {
                            StringBuffer stringBuffer = new StringBuffer();
                            if (!RxDataTool.isEmpty(data.getReturn_phone())) {
                                String phone_asterisk = StringUtil.getPhone_Asterisk(data.getReturn_phone());

                                stringBuffer.append("收货人：")
                                        .append(data.getReturn_name())
                                        .append("   ")
                                        .append(phone_asterisk);
                                tv_address.setText(data.getAddress());
                                tv_receiver.setText(stringBuffer.toString());
                            }
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(PayOrderDetailsActivity.this);

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
                CommonDialog.showInfoDialog(PayOrderDetailsActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("查询师傅的收货信息baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(PayOrderDetailsActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
    private void getOrderList() {
        CommonDialog.showProgressDialog(PayOrderDetailsActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("goods_id", getIntent().getStringExtra("goods_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(PayOrderDetailsActivity.this, NetUtils.POST, UrlConstant.GETGOODSINFO, jsonObject, new JsonHttpResponseHandler() {


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


                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(PayOrderDetailsActivity.this);

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
                CommonDialog.showInfoDialog(PayOrderDetailsActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(PayOrderDetailsActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @OnClick({R.id.iv_back, R.id.bt_pay_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_pay_order:
                break;
        }
    }
}
