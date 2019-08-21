package com.nengyuanbox.repaircar.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.nengyuanbox.repaircar.bean.WXBaseRespEntity;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.eventbus.LoginEventBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.EventBus;


/**
 * description ：
 * project name：CCloud
 * author : Vincent
 * creation date: 2017/6/9 18:13
 *
 * @version 1.0
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    /**
     * 微信登录相关
     */
    private IWXAPI api;
    private String code;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX,true);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID_WX);
       ViseLog.d("------------------------------------");
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result =  api.handleIntent(getIntent(), this);
            if(!result){
              ViseLog.d("参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
      ViseLog.d("baseReq:"+ JSON.toJSONString(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {




      ViseLog.d("baseResp:--A"+JSON.toJSONString(baseResp));

        ViseLog.d("baseResp--B:"+baseResp.errStr+","+baseResp.openId+","+baseResp.transaction+","+baseResp.errCode);
        WXBaseRespEntity entity = JSON.parseObject(JSON.toJSONString(baseResp),WXBaseRespEntity.class);
        code = entity.getCode();
        String result = "";
        switch(baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
//                result ="发送成功";
//                CommApplication.getShared().putString("code",code);
                EventBus.getDefault().post(new LoginEventBean(code));
                finish();

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
              ViseLog.d("发送取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
              ViseLog.d("发送被拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:

                result = "签名错误";
              ViseLog.d("签名错误");
                break;
                case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:

                    finish();
              ViseLog.d("签名错误");
                break;


            default:
                result = "发送返回";
//                showMsg(0,result);
                finish();
                break;
        }
//        Toast.makeText(WXEntryActivity.this,result,Toast.LENGTH_LONG).show();
    }


}
