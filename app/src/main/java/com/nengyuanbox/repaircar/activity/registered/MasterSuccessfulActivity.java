package com.nengyuanbox.repaircar.activity.registered;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.mine.MineActivity;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//  师傅认证成功界面
public class MasterSuccessfulActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.iv_my_scon)
    ImageView iv_my_scon;
    @BindView(R.id.tv_share)
    TextView tv_share;
    @BindView(R.id.tv_claim)
    TextView tv_claim;
    private String img_url;
    private IWXAPI api;
    private String openid;
    private byte[] bytes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_successful_certification;
    }

    @Override
    protected void initView() {
        //将应用的appid注册到微信
        api = WXAPIFactory.createWXAPI(MasterSuccessfulActivity.this, Constant.APP_ID_WX, true);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID_WX);
        GETQRCODEIMG();
        openid = SharedPreferenceUtil.getString(MasterSuccessfulActivity.this,Constant.OPENID,"");

        SharedPreferenceUtil.saveString(MasterSuccessfulActivity.this, Constant.ROLE, "master");

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


    //    生成用户推广码与评价码
    private void GETQRCODEIMG() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

//            1、推广码 2、评价码
            jsonObject.put("type", "2");


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
                        RxToast.normal(response.getString("message"));
                            img_url = response.getString("data");

                        Glide.with(MasterSuccessfulActivity.this)
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
                        LoginExpirelUtils.getLoginExpirel(MasterSuccessfulActivity.this);

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
                CommonDialog.showInfoDialog(MasterSuccessfulActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MasterSuccessfulActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MasterSuccessfulActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    @Override
    protected void setListener() {
        tv_title_name.setText("维修师傅认证");

    }



    @OnClick({R.id.iv_back, R.id.tv_share, R.id.tv_claim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(MasterSuccessfulActivity.this,MineActivity.class));
                finish();
                break;
            case R.id.tv_share:
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
                getShare();
                break;
            case R.id.tv_claim:
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(new Intent(MasterSuccessfulActivity.this,MineActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
