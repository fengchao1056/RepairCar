package com.nengyuanbox.repaircar.activity.mine;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.CircleImageView;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//我的二维码界面
public class ORCodeActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_my_phone)
    TextView tv_my_phone;
    @BindView(R.id.iv_imagehead)
    CircleImageView iv_imagehead;
    @BindView(R.id.iv_my_scon)
    ImageView iv_my_scon;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orcode;
    }

    @Override
    protected void initView() {
        getQrCodeImg();
        String str_name = SharedPreferenceUtil.getString(ORCodeActivity.this, Constant.NAME, "");
        String img_url = SharedPreferenceUtil.getString(ORCodeActivity.this, Constant.Img_url, "");
        tv_name.setText(str_name);

        Glide.with(ORCodeActivity.this)
                .load(img_url).asBitmap()
                .error(R.drawable.requestout)
                .placeholder(R.drawable.requestout)
                .into(iv_imagehead);

    }

    @Override
    protected void setListener() {
        tv_title_name.setText("我的二维码");
    }
    //    生成用户推广码与评价码
    private void getQrCodeImg() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

//            1、推广码 2、评价码
            jsonObject.put("type", "1");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETQRCODEIMG, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("生成用户推广码与评价码：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
//                        RxToast.normal(response.getString("message"));
                        String img_url = response.getString("data");
                        ViseLog.d("生成用户推广码与评价码图片：" + img_url);
                        Glide.with(ORCodeActivity.this)
                                .load(img_url)
                                .error(R.drawable.requestout)
                                .placeholder(R.drawable.requestout)
                                .crossFade().into(iv_my_scon);


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(ORCodeActivity.this);

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
                CommonDialog.showInfoDialog(ORCodeActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(ORCodeActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(ORCodeActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }



    @OnClick(R.id.iv_back)
    public void onViewClicked() {

        finish();
    }
}
