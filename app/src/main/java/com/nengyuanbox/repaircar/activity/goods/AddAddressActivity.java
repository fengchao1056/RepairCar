package com.nengyuanbox.repaircar.activity.goods;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetMasterAddressBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.eventbus.EventBean;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.ContainsEmojiEditText;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//  新增地址
public class AddAddressActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.et_master_name)
    EditText et_master_name;
    @BindView(R.id.et_master_phone)
    EditText et_master_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.btn_finish)
    Button btn_finish;
    @BindView(R.id.et_address)
    ContainsEmojiEditText et_address;
    private String province;
    private String county;
    private String city;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {
        tv_title_name.setText("新增地址");
        getMasterAddress();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void setListener() {

    }

    //    省市区
    private void getMasterAddress() {
        CommonDialog.showProgressDialog(AddAddressActivity.this);
        JSONObject jsonObject = new JSONObject();

//        try {
//            jsonObject.put("page", "1");
//            jsonObject.put("pagesize", "1");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        NetUtils.newInstance().putReturnJsonNews(AddAddressActivity.this, NetUtils.POST, UrlConstant.GETMASTERADDRESS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("省市区" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetMasterAddressBean getMasterAddressBean = JsonUtil.getSingleBean(response.toString(), GetMasterAddressBean.class);
                        GetMasterAddressBean.DataBean data = getMasterAddressBean.getData();
                        if (data!=null&&!RxDataTool.isEmpty(data.getAddress())){
//                            StringBuffer stringBuffer=new StringBuffer();
//                             stringBuffer.append(data.getProvince())
//                                     .append(data.getCity())
//                                     .append(data.getCounty());
//                            tv_address.setText(stringBuffer.toString());
                            et_master_name.setText(data.getReturn_name());
                            et_master_phone.setText(data.getReturn_phone());
//                            StringBuffer  stringBuffer1=new StringBuffer()
//                                    .append(data.getProvince())
//                                    .append(data.getCity())
//                                    .append(data.getCounty())
//                                    .append(data.getAddress());
//                            et_address.setText(stringBuffer1.toString());
                            et_address.setText(data.getAddress());

                        }
//                        GetGoodsInfoBean getGoodsInfoBean = JsonUtil.getSingleBean(response.toString(), GetGoodsInfoBean.class);


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(AddAddressActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("省市区baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("省市区baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(AddAddressActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("省市区baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(AddAddressActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    x省市区
    private void setMasterAddress() {
        CommonDialog.showProgressDialog(AddAddressActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            if (!RxDataTool.isEmpty(province)){
                jsonObject.put("province", province);
                jsonObject.put("city", city);
                jsonObject.put("county", county);
            }
            jsonObject.put("address", et_address.getText().toString().trim());
            jsonObject.put("return_name", et_master_name.getText().toString().trim());
            jsonObject.put("return_phone", et_master_phone.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(AddAddressActivity.this, NetUtils.POST, UrlConstant.SETMASTERADDRESS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("省市区" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        finish();

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(AddAddressActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("省市区baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("省市区baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(AddAddressActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("省市区baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(AddAddressActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    @OnClick({R.id.iv_back, R.id.btn_finish, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_address:
                Intent intent = new Intent(AddAddressActivity.this, ProvinceActivity.class);

                startActivity(intent);

                break;
//                确定
            case R.id.btn_finish:

                if (!RxDataTool.isEmpty(et_master_name.getText().toString())) {
                    if (!RxDataTool.isEmpty(et_master_phone.getText().toString())) {

                        if (!RxDataTool.isEmpty(tv_address.getText().toString())) {
//                            setMasterAddress();
                            if (!RxDataTool.isEmpty(et_address.getText().toString())) {
                                setMasterAddress();


                            } else {
                                RxToast.normal("请输入详细地址");
                                return;
                            }

                        } else {
                            RxToast.normal("请选择地址");
                            return;
                        }
                    } else {
                        RxToast.normal("请输入手机号码");
                        return;
                    }
                } else {
                    RxToast.normal("请输入收货人姓名");
                    return;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 || resultCode == 200) {

        }
    }

    @Subscribe
    public void getEventBean(EventBean eventBean) {

//          获取地址信息
        if (!RxDataTool.isEmpty(eventBean.getAdress())) {
            tv_address.setText(eventBean.getAdress());

//            获取省市区id
            if (!RxDataTool.isEmpty(eventBean.getPID())
                    && !RxDataTool.isEmpty(eventBean.getAID())
                    && !RxDataTool.isEmpty(eventBean.getCID())) {
                province = eventBean.getPID();
                city = eventBean.getAID();
                county = eventBean.getCID();


            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
