package com.nengyuanbox.repaircar.activity.mine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetUserInfoBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.CircleImageView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//我的二维码界面  师傅二维码界面
public class ORCodeMasterActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_character)
    TextView tv_character;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_star)
    TextView tv_star;
    @BindView(R.id.tv_order_name)
    TextView tv_order_name;
    @BindView(R.id.tv_or_txt)
    TextView tv_or_txt;
    @BindView(R.id.tv_my_phone)
    TextView tv_my_phone;
    @BindView(R.id.iv_imagehead)
    CircleImageView iv_imagehead;
    @BindView(R.id.tv_change_or)
    TextView tv_change_or;
    @BindView(R.id.bt_share)
    Button bt_share;
    @BindView(R.id.iv_my_scon)
    ImageView iv_my_scon;
    private IWXAPI api;
    private String img_url;
    private byte[] bytes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orcode_mester;
    }

    @Override
    protected void initView() {
        tv_title_name.setText("我的二维码");
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID_WX);
        getQrCodeImg("1");
        getUserInfo();
    }
    private void getUserInfo() {

        CommonDialog.showProgressDialog(ORCodeMasterActivity.this);
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(ORCodeMasterActivity.this, NetUtils.POST, UrlConstant.GETUSERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("个人中心用户信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetUserInfoBean singleBean = JsonUtil.getSingleBean(response.toString(), GetUserInfoBean.class);
                        GetUserInfoBean.DataBean data = singleBean.getData();
                        if (data!=null){
                            if (!RxDataTool.isEmpty(data.getImg_url())) {
                                ViseLog.d("头像地址：" + data.getImg_url());
                                Glide.with(ORCodeMasterActivity.this)
                                        .load(data.getImg_url())
                                        .placeholder(R.drawable.requestout)
                                        .error(R.drawable.requestout)
                                        .into(iv_imagehead);

                                tv_order_name.setText(data.getOrder_num()+"单");
                                tv_star.setText(data.getStar());
                                tv_name.setText(data.getName());

                            }

                            if (!RxDataTool.isEmpty(data.getPhone())){
                                tv_my_phone.setText(data.getPhone());

                            }
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(ORCodeMasterActivity.this);

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
                CommonDialog.showInfoDialog(ORCodeMasterActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(ORCodeMasterActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
    @Override
    protected void setListener() {


    }

    private void getQrCodeImg(String type) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

//            1、推广码 2、评价码
            jsonObject.put("type", type);


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
                        img_url = response.getString("data");
                        ViseLog.d("生成用户推广码与评价码图片：" + img_url);
                        Glide.with(ORCodeMasterActivity.this)
                                .load(img_url)
                                .error(R.drawable.requestout)
                                .placeholder(R.drawable.requestout)
                                .crossFade().into(new SimpleTarget<GlideDrawable>() {

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                iv_my_scon.setImageDrawable(resource);
                                bytes = StringUtil.Bitmap2Bytes(StringUtil.drawableToBitmap(resource));

                            }
                        });


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(ORCodeMasterActivity.this);

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
                CommonDialog.showInfoDialog(ORCodeMasterActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(ORCodeMasterActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(ORCodeMasterActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    public void getShare() {
        Resources res = getResources();
           Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.my_tou);

        String path = "/custom/pages/entry/masterModule/index?uid=";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(path).append(SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1"));

        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_e28ecc3ec1f6";     // 小程序原始id
        miniProgramObj.path = stringBuffer.toString();            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage((WXMediaMessage.IMediaObject) miniProgramObj);
        msg.title = "我已入驻来修车，接单接到手软，快来一起抢单吧！";                    // 小程序消息title
        msg.description = "我已入驻来修车，接单接到手软，快来一起抢单吧！";               // 小程序消息desc
        msg.thumbData = bytes;                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = ("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        api.sendReq(req);

    }


    @OnClick({R.id.iv_back, R.id.tv_change_or, R.id.bt_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_change_or:
                if (tv_change_or.getText().toString().equals("切换到我的评价码赚取积分")){
                     tv_or_txt.setText("请客户扫描以上评价码获得我的评价积分");
                     tv_change_or.setText("切换到我的推广码赚推广费");
                     bt_share.setVisibility(View.GONE);
                    getQrCodeImg("2");
                }else {
                    tv_or_txt.setText("请师傅扫描以上推广码入驻赚取推广费");
                    tv_change_or.setText("切换到我的评价码赚取积分");
                    bt_share.setVisibility(View.VISIBLE);
                    getQrCodeImg("1");
                }
                break;
            case R.id.bt_share:
                getShare();

                break;
        }
    }
}
