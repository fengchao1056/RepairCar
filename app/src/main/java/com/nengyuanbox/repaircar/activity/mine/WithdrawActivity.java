package com.nengyuanbox.repaircar.activity.mine;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CashierInputFilter;
import com.nengyuanbox.repaircar.utils.ClickUtil;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.ClearEditText;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

// 提现界面
public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.et_price)
    ClearEditText et_price;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.bt_withdraw)
    Button bt_withdraw;
    private String money;
    private double sum_money;
    private double lv_money;
    private double max_money;
    private String imput_money;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initView() {
        //        设置输入类型
        InputFilter[] filters = {new CashierInputFilter()};
        et_price.setFilters(filters);
        tv_title_name.setText("订单");
//        iv_back.setImageResource(R.drawable.icon_button);
        if (!RxDataTool.isEmpty(getIntent().getStringExtra("money"))){
            money = getIntent().getStringExtra("money");
            tv_money.setText(money);
        }

        //        账号余额
         sum_money = RxDataTool.stringToDouble(money);
//                提现利率
         lv_money = Double.parseDouble(totalMoney(sum_money * 0.001));


        if (lv_money < 0.1) {
            lv_money = 0.1;
        } else {

        }
//                最大提现金额
         max_money = Double.parseDouble(totalMoney(sum_money - lv_money));
        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                     imput_money = s.toString();
                    double et_dbmoney = Double.parseDouble(imput_money);

                    if (et_dbmoney > (max_money)) {
                       RxToast.normal("已超过最大提现金额");
                        et_price.setText(String.valueOf(max_money));
                        imput_money = String.valueOf(max_money);
                    } else {
                        imput_money = s.toString();

                    }
                    bt_withdraw.setEnabled(true);
                } else {
                    bt_withdraw.setEnabled(false);
                }


            }
        });
    }
    public static String totalMoney(double money) {
        java.math.BigDecimal bigDec = new java.math.BigDecimal(money);
        double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }
    @Override
    protected void setListener() {

    }


    @OnClick({R.id.iv_back, R.id.bt_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //       确认提现
            case R.id.bt_withdraw:

                if (!ClickUtil.isFastClick()) {
                    getCommonProblem();

                }else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }
                break;
        }
    }

    private void getCommonProblem() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        try {



            jsonObject.put("money", et_price.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(WithdrawActivity.this, NetUtils.POST, UrlConstant.TRANSFERS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("提现：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        finish();

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(WithdrawActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("提现：baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("提现：baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(WithdrawActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("提现：baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(WithdrawActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


}
