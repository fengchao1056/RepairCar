package com.nengyuanbox.repaircar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.mine.AccountDetailsActivity;
import com.nengyuanbox.repaircar.activity.mine.WithdrawActivity;
import com.nengyuanbox.repaircar.base.BaseFragment;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//钱
public class WalletFragment extends BaseFragment {
    @BindView(R.id.tv_wallet)
    TextView tv_wallet;
    @BindView(R.id.bt_withdraw)
    Button bt_withdraw;
    @BindView(R.id.account_details)
    LinearLayout account_details;

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
//        getCodeText();

    }

    private void getUserInfo() {

        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETUSERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("个人中心用户信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        JSONObject data = response.getJSONObject("data");
                        if (data.has("money")) {
                            tv_wallet.setText(data.getString("money"));
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
    private void getCodeText() {

        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("getType","1");
        } catch (JSONException e) {

        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETCODETEXT, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("文案描述接口：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        JSONObject data = response.getJSONObject("data");
                        if (data.has("money")) {
                            tv_wallet.setText(data.getString("money"));
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wallet;
    }


    @OnClick({R.id.tv_wallet, R.id.bt_withdraw, R.id.account_details})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()) {
//             錢包
            case R.id.tv_wallet:
                break;
//                提现
            case R.id.bt_withdraw:
                intent=new Intent(getActivity(),WithdrawActivity.class);
                intent.putExtra("money",tv_wallet.getText().toString());
                startActivity(intent);
                break;
//                账户明细
            case R.id.account_details:

                startActivity(new Intent(getActivity(), AccountDetailsActivity.class));
                break;
        }
    }
}
