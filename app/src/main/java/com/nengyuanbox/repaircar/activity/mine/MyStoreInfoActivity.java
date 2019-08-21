package com.nengyuanbox.repaircar.activity.mine;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetUserMasterStoreBean;
import com.nengyuanbox.repaircar.bean.TXApiAddressBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import okhttp3.Call;

//师傅   我的门店信息界面
public class MyStoreInfoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_store_star)
    TextView tv_store_star;
    @BindView(R.id.tv_store_phone)
    TextView tv_store_phone;
    @BindView(R.id.tv_store_type)
    TextView tv_store_type;
    @BindView(R.id.tv_store_address)
    TextView tv_store_address;
    @BindView(R.id.bt_unbind)
    Button bt_unbind;
    @BindView(R.id.iv_store_img)
    ImageView iv_store_img;
    private String store_uid;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_store_info;
    }

    @Override
    protected void initView() {
        getUserMasterStore();

    }

    @Override
    protected void setListener() {
        tv_title_name.setText("门店信息");

    }

    //    "门店信息：
    private void getUserMasterStore() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();


        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETUSERMASTERSTORE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("门店信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        GetUserMasterStoreBean getStoreInfoBean = JsonUtil.getSingleBean(response.toString(), GetUserMasterStoreBean.class);
                        GetUserMasterStoreBean.DataBean.StoreInfoBean data = getStoreInfoBean.getData().getStore_info();
                        if (data != null) {

                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(data.getStore_area_x())
                                    .append(",")
                                    .append(data.getStore_area_y());
                            getUserInfo(stringBuffer.toString());
                            store_uid = data.getUid();
//                             手机号
                            if (!RxDataTool.isEmpty(data.getBusiness_phone())) {
                                tv_store_phone.setText(data.getBusiness_phone());
                            }

//                             门店名称
                            if (!RxDataTool.isEmpty(data.getGname())) {
                                tv_store_name.setText(data.getGname());
                            }


                            //                            头像
                            if (!RxDataTool.isEmpty(data.getStore_img())) {
                                Glide.with(MyStoreInfoActivity.this)
                                        .load(data.getStore_img())
                                        .placeholder(R.drawable.requestout)
                                        .error(R.drawable.requestout)
                                        .into(iv_store_img);
                            }

//                             入驻时间
                            if (!RxDataTool.isEmpty(getStoreInfoBean.getData().getClaim_time())) {
                                tv_store_type.setText(getStoreInfoBean.getData().getClaim_time());
                            }


                            //                      门店星数
                            if (!RxDataTool.isEmpty(data.getStar())) {
                                tv_store_star.setText(data.getStar());
                            }

                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MyStoreInfoActivity.this);

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
                ViseLog.d("门店信息：" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(MyStoreInfoActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("门店信息：" + throwable.toString());
                CommonDialog.showInfoDialog(MyStoreInfoActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MyStoreInfoActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    //    "解绑门店
    private void setUserStore() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("store_uid",store_uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.SETUSERSTORE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("解绑门店：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal("解绑成功");
                        finish();

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MyStoreInfoActivity.this);

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
                ViseLog.d("门店信息：" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(MyStoreInfoActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("门店信息：" + throwable.toString());
                CommonDialog.showInfoDialog(MyStoreInfoActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MyStoreInfoActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }


    //提供由坐标到坐标所在位置的文字描述的转换
    private void getUserInfo(String location) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID

        OkHttpUtils.get()
                .url("https://apis.map.qq.com/ws/geocoder/v1")
                .addParams("location", location)
                .addParams("get_poi", "0")//是否返回周边POI列表：1.返回；0不返回(默认)
                .addParams("key", "D5NBZ-RMFKI-JH2GC-5BD7V-QCBGE-ABB6Y")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ViseLog.d("获取错误..");
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        ViseLog.d("腾讯地图返回:" + response);
                        Log.d("Feng", "腾讯地图返回:" + response);

                        TXApiAddressBean singleBean = JsonUtil.getSingleBean(response, TXApiAddressBean.class);


                        if (singleBean.getStatus()==0){
                               if (singleBean!=null&&!RxDataTool.isEmpty(singleBean.getResult().getAddress())){
                                tv_store_address.setText(singleBean.getResult().getAddress());

                            }
                        }




                    }
                });
    }

    @OnClick({ R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_title_name:
                break;
//            case R.id.iv_modify:
//
//                startActivity(new Intent(MyStoreInfoActivity.this, AmendStoreCertificationActivity.class));
//                finish();
//                break;
        }
    }


    @OnClick(R.id.bt_unbind)
    public void onViewClicked() {

          if (!RxDataTool.isEmpty(store_uid)){

              setUserStore();
          }else {
              RxToast.normal("暂无入驻门店");
          }
    }


}
