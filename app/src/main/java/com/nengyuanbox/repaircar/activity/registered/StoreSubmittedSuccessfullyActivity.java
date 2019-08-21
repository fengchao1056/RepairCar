package com.nengyuanbox.repaircar.activity.registered;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.mine.MineActivity;
import com.nengyuanbox.repaircar.base.BaseActivity;
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

public class StoreSubmittedSuccessfullyActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_text)
    TextView tv_text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_submitted_successfully;
    }

    @Override
    protected void initView() {
        tv_title_name.setText("门店认证");
    }

    @Override
    protected void setListener() {
        getCodeText();

    }


    private void getCodeText() {

        CommonDialog.showProgressDialog(StoreSubmittedSuccessfullyActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("getType", "0");
        } catch (JSONException e) {

        }

        NetUtils.newInstance().putReturnJsonNews(StoreSubmittedSuccessfullyActivity.this, NetUtils.POST, UrlConstant.GETCODETEXT, jsonObject, new JsonHttpResponseHandler() {


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


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreSubmittedSuccessfullyActivity.this);

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
                CommonDialog.showInfoDialog(StoreSubmittedSuccessfullyActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreSubmittedSuccessfullyActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }



    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        startActivity(new Intent(StoreSubmittedSuccessfullyActivity.this,MineActivity.class));
        finish();
    }
}
