package com.nengyuanbox.repaircar.activity.mine;

import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.shizhefei.view.largeimage.LargeImageView;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//收费标准界面
public class ChargesActivity extends BaseActivity {



    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    @BindView(R.id.imageView)
    LargeImageView imageView;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_charges;
    }


    protected void initView() {
        tv_title_name.setText("收费标准");
        chargestandard();
        imageView.setEnabled(true);
        //加载普通大小图片
        imageView.setImage(R.mipmap.iv_rates);
    }

    @Override
    protected void setListener() {

    }

    //  收费标准
    private void chargestandard() {

        CommonDialog.showProgressDialog(ChargesActivity.this);
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(ChargesActivity.this, NetUtils.POST, UrlConstant.CHARGESTANDARD, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("收费标准：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        String url = response.getString("data");


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(ChargesActivity.this);

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
                ViseLog.d("收费标准报错:" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(ChargesActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("收费标准报错2:" + throwable.toString());
                CommonDialog.showInfoDialog(ChargesActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
