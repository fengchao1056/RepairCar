package com.nengyuanbox.repaircar.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.registered.MastPaymentActivity;
import com.nengyuanbox.repaircar.activity.registered.MasterCardCertificationActivity;
import com.nengyuanbox.repaircar.activity.registered.MasterCertificationActivity;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetUserInfoBean;
import com.nengyuanbox.repaircar.bean.LoginBean;
import com.nengyuanbox.repaircar.bean.WXUserInfo;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.eventbus.LoginEventBean;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSure;
import com.nengyuanbox.repaircar.utils.DialogSureCancel;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LogUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.reactivex.functions.Consumer;
import okhttp3.Call;

public class LoginActivity extends BaseActivity implements TencentLocationListener {


    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.rb_master)
    RadioButton rb_master;
    @BindView(R.id.rb_store)
    RadioButton rb_store;
    @BindView(R.id.rb_people)
    RadioButton rb_people;
    @BindView(R.id.rg_rol)
    RadioGroup rg_rol;
    @BindView(R.id.iv_wechat)
    ImageView iv_wechat;

    private String userType = "2";
    /**
     * 微信登录相关
     */
    private IWXAPI api;
    private TencentLocationManager mLocationManager;
    private String area_y = "116.299555";
    private String area_x = "40.049582";
    private String str_store;
    private String str_master;
    private String str_master_exmine;
    private String str_store_exmine;
    private GetUserInfoBean.DataBean data;
    private String str_role;
    private CountDownTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        //        申请权限
        requestPermissions();

//        查询是否开启定位权限
        openGPSSEtting();


        //将应用的appid注册到微信
        api = WXAPIFactory.createWXAPI(LoginActivity.this, Constant.APP_ID_WX, true);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID_WX);
        EventBus.getDefault().register(LoginActivity.this);
        //                      角色判定
        str_role = SharedPreferenceUtil.getString(LoginActivity.this, Constant.ROLE, "");


        rb_master.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userType = "2";
                } else {

                }
            }
        });
        rb_store.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userType = "3";
                } else {

                }
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(LoginActivity.this);
        rxPermission.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE, //读取手机状态的权限
                Manifest.permission.CAMERA,  //读取手机相机的权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE   //拨打电话权限
        )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtil.d("Feng", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {

                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtil.d("Feng", permission.name + " is denied. More info should be provided.");
                            if (permission.name.contains("android.permission.ACCESS_COARSE_LOCATION")) {
                                showdialog("来修车向您申请定位权限，以提供更加精准的定位服务");
                            }
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtil.d("Feng", permission.name + " is denied.");
                            if (permission.name.contains("android.permission.ACCESS_COARSE_LOCATION")) {
                                showdialog("来修车向您申请定位权限，以提供更加精准的定位服务");
                            }
                        }
                    }
                });
    }


    //    第二步   微信登陆回调
    @Subscribe
    public void getLogin(LoginEventBean loginEventBean) {
        if (loginEventBean != null && !RxDataTool.isNullString(loginEventBean.getCode())) {
//            App登陆  获取登录信息
            Login(loginEventBean.getCode());
        }
    }


    private void openGPSSEtting() {
//         是否打开定位功能
        if (checkGpsIsOpen()) {
//             打开  获取定位信息
            Loation();
        } else {
//             没有打开定位功能   弹窗提醒用户开启定位功能
            new AlertDialog.Builder(this).setTitle("定位失败")
                    .setMessage("请检查是否开启定位服务")
                    //  取消选项
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 关闭dialog
                            dialogInterface.dismiss();
                        }
                    })
                    //  确认选项
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //跳转到手机原生设置页面
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 1);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private boolean checkGpsIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            openGPSSEtting();
        }
    }

    private void Loation() {
        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(2000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        mLocationManager.requestLocationUpdates(request, this);
    }


    @Override
    protected void setListener() {
        //        微信登陆
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!RxDataTool.isEmpty(et_mobile.getText().toString().trim())) {
                    if (!RxDataTool.isEmpty(et_code.getText().toString().trim())) {
                        Login_Phone();
                    } else {
                        RxToast.normal("请输入手机验证码");
                        return;
                    }
                } else {
                    RxToast.normal("请输入手机号");
                    return;
                }


            }
        });

    }


    //    发送验证码
    private void sendPhoneCodes() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", et_mobile.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJson_Login(context, NetUtils.POST, UrlConstant.SENDPHONECODES, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("发送验证码：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        initButten();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(LoginActivity.this);

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
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    //    登录接口
    private void Login(String code) {
        CommonDialog.showProgressDialog(this);

        JSONObject dictParam = new JSONObject();
        try {
//            微信登陆获取的code值
            dictParam.put("js_code", code);
//              定位信息
            dictParam.put("area_x", area_x);
            dictParam.put("area_y", area_y);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetUtils.newInstance().putReturnJson_Login(LoginActivity.this, NetUtils.POST, UrlConstant.GETOPENID, dictParam, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("登录：" + response.toString());
                    String status = response.getString("code");
                    String message = response.getString("message");
                    switch (status) {
                        case "2000":
                            LoginBean loginBean = JsonUtil.getSingleBean(response.toString(), LoginBean.class);
                            if (loginBean.getData() != null) {
                                LoginBean.DataBean data = loginBean.getData();

//                                          登录角色判定
//                                        "is_store": "1", //是否是门店 1（是）,0（不是）
//                                        "is_master": "1",  //是否是师傅 1（是）,0（不是）
//                                        "is_master_car": "1",  //是否是流动维修站 1（是）,0（不是）
//                                code=2027 去微信登录


                                //是否是师傅 1（是）,0（不是）
                                if (!RxDataTool.isNullString(data.getIs_master())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IS_MASTER, data.getIs_master());
                                    str_master = data.getIs_master();
                                }

                                //是否是流动维修站 1（是）,0（不是）
                                if (!RxDataTool.isNullString(data.getIs_master_car())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IS_MASTER_CAR, data.getIs_master_car());
                                }
//                                是否是门店 1（是）,0（不是）
                                if (!RxDataTool.isNullString(data.getIs_store())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IS_STORE, data.getIs_store());
                                    str_store = data.getIs_store();
                                }

                                ViseLog.d("是否是师傅-------------" + data.getIs_master());

                                if (!RxDataTool.isNullString(data.getAccess_token())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.TOKEN, data.getAccess_token());
                                }
                                if (!RxDataTool.isNullString(data.getUid())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.YHID, data.getUid());
                                }
                                if (!RxDataTool.isNullString(data.getKey())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.KEY, data.getKey());
                                }
                                if (!RxDataTool.isNullString(data.getKey())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IV, data.getIv());
                                }
                                if (!RxDataTool.isNullString(data.getLogin_salt())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.LoginSalt, data.getLogin_salt());
                                }


                                if (!RxDataTool.isNullString(data.getImg_url())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.Img_url, data.getImg_url());
                                }

                                if (!RxDataTool.isNullString(data.getName())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.NAME, data.getName());
                                }

//                                if (!RxDataTool.isNullString(data.getWx_access_token())) {
//                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.Wx_access_token, data.getWx_access_token());
//                                }
//                                  获取App的openID
                                if (!RxDataTool.isNullString(data.getOpenid())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.OPENID, data.getOpenid());
                                }


//                                如果app登录  没有获取名字的话，需要去调微信接口获取用户名字
                                if (data.getName().equals("0")) {
                                    getUserInfo(data.getWx_access_token(), data.getOpenid());

                                } else {
//                                app登录  获取到了名字的话，去判断角色
                                    if (str_master.equals("0") && str_store.equals("0")) {
                                        getUserInfo();
                                        getLoginExpirel_Acticity("您还没有注册信息，是否前去注册");

                                    } else {

//                                         如果上次登录的门店用户  那么就跳到  门店首页界面
                                        if (!RxDataTool.isEmpty(str_role) && str_role.equals("store")) {
                                            startActivity(new Intent(LoginActivity.this, StoreMainActivity.class));
                                            finish();

                                        } else {

//                                          如果上次登录的师傅用户  那么就跳到   师傅首页界面
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("nikeName", data.getName());
                                            startActivity(intent);
                                            finish();

                                        }

                                    }


                                }


                            }


                            break;

                        default:
                            CommonDialog.closeProgressDialog();
                            RxToast.normal(message);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CommonDialog.closeProgressDialog();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString() + responseString);
                CommonDialog.showInfoDialog(LoginActivity.this, "与服务器连接失败");


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(LoginActivity.this, "与服务器连接失败");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    //    S手机号登录接口
    private void Login_Phone() {
        CommonDialog.showProgressDialog(this);

        JSONObject dictParam = new JSONObject();
        try {
//              定位信息
            dictParam.put("area_x", area_x);
            dictParam.put("area_y", area_y);
            dictParam.put("phone", et_mobile.getText().toString().trim());
            dictParam.put("userType", userType);
            dictParam.put("smscode", et_code.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetUtils.newInstance().putReturnJson_Login(LoginActivity.this, NetUtils.POST, UrlConstant.LOGINPHONE, dictParam, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("S手机号登录接口：" + response.toString());
                    String status = response.getString("code");
                    String message = response.getString("message");
                    switch (status) {
                        case "2000":
                            LoginBean loginBean = JsonUtil.getSingleBean(response.toString(), LoginBean.class);
                            if (loginBean.getData() != null) {
                                LoginBean.DataBean data = loginBean.getData();
                                if (userType.equals("2")) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.ROLE, "master");

                                } else {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.ROLE, "store");

                                }

//                                          登录角色判定
//                                        "is_store": "1", //是否是门店 1（是）,0（不是）
//                                        "is_master": "1",  //是否是师傅 1（是）,0（不是）
//                                        "is_master_car": "1",  //是否是流动维修站 1（是）,0（不是）
//                                code=2027 去微信登录


                                //是否是师傅 1（是）,0（不是）
                                if (!RxDataTool.isNullString(data.getIs_master())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IS_MASTER, data.getIs_master());
                                    str_master = data.getIs_master();
                                }

                                //是否是流动维修站 1（是）,0（不是）
                                if (!RxDataTool.isNullString(data.getIs_master_car())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IS_MASTER_CAR, data.getIs_master_car());
                                }
//                                是否是门店 1（是）,0（不是）
                                if (!RxDataTool.isNullString(data.getIs_store())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IS_STORE, data.getIs_store());
                                    str_store = data.getIs_store();
                                }

                                ViseLog.d("是否是师傅-------------" + data.getIs_master());

                                if (!RxDataTool.isNullString(data.getAccess_token())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.TOKEN, data.getAccess_token());
                                }
                                if (!RxDataTool.isNullString(data.getUid())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.YHID, data.getUid());
                                }
                                if (!RxDataTool.isNullString(data.getKey())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.KEY, data.getKey());
                                }
                                if (!RxDataTool.isNullString(data.getKey())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.IV, data.getIv());
                                }
                                if (!RxDataTool.isNullString(data.getLogin_salt())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.LoginSalt, data.getLogin_salt());
                                }


                                if (!RxDataTool.isNullString(data.getImg_url())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.Img_url, data.getImg_url());
                                }

                                if (!RxDataTool.isNullString(data.getName())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.NAME, data.getName());
                                }

//                                if (!RxDataTool.isNullString(data.getWx_access_token())) {
//                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.Wx_access_token, data.getWx_access_token());
//                                }
//                                  获取App的openID
                                if (!RxDataTool.isNullString(data.getOpenid())) {
                                    SharedPreferenceUtil.saveString(LoginActivity.this, Constant.OPENID, data.getOpenid());
                                }


//                                如果app登录  没有获取名字的话，需要去调微信接口获取用户名字
                                if (data.getName().equals("0")) {
                                    getUserInfo(data.getWx_access_token(), data.getOpenid());

                                } else {
//                                app登录  获取到了名字的话，去判断角色
                                    if (str_master.equals("0") && str_store.equals("0")) {
                                        getUserInfo();
                                        getLoginExpirel_Acticity("您还没有注册信息，是否前去注册");

                                    } else {

//                                         如果上次登录的门店用户  那么就跳到  门店首页界面
                                        if (!RxDataTool.isEmpty(str_role) && str_role.equals("store")) {
                                            startActivity(new Intent(LoginActivity.this, StoreMainActivity.class));
                                            finish();

                                        } else {

//                                          如果上次登录的师傅用户  那么就跳到   师傅首页界面
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("nikeName", data.getName());
                                            startActivity(intent);
                                            finish();

                                        }

                                    }


                                }


                            }


                            break;


                        case "2027":

                            RxToast.normal("您还没有注册消息，请先用微信登录注册");

                            break;

                        default:
                            CommonDialog.closeProgressDialog();
                            RxToast.normal(message);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CommonDialog.closeProgressDialog();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString() + responseString);
                CommonDialog.showInfoDialog(LoginActivity.this, "与服务器连接失败");


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(LoginActivity.this, "与服务器连接失败");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    //   完善信息
    private void setUserInfo(WXUserInfo wxUserInfo) {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("headurl", wxUserInfo.getHeadimgurl());
            jsonObject.put("name", wxUserInfo.getNickname());
            jsonObject.put("sex", String.valueOf(wxUserInfo.getSex()));
            jsonObject.put("area_x", area_x);
            jsonObject.put("area_y", area_y);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(LoginActivity.this, NetUtils.POST, UrlConstant.SETUSERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("完善信息" + response.toString());
                    if ("2000".equals(response.getString("code"))) {

//                        获取个人信息
                        getUserInfo();
                        if (str_master.equals("0") && str_store.equals("0")) {
                            getLoginExpirel_Acticity("您还没有注册信息，是否前去注册");
                        } else {
                            if (!RxDataTool.isEmpty(str_role) && str_role.equals("store")) {
                                startActivity(new Intent(LoginActivity.this, StoreMainActivity.class));
                                finish();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("nikeName", data.getName());
                                startActivity(intent);
                                finish();
                            }

                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(LoginActivity.this);

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
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    调微信接口获取用户名字
    private void getUserInfo(String wx_token, String open_id) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID

        OkHttpUtils.get()
                .url("https://api.weixin.qq.com/sns/userinfo")
                .addParams("access_token", wx_token)
                .addParams("openid", open_id)//openid:授权用户唯一标识
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ViseLog.d("获取错误..");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ViseLog.d("userInfo:" + response);
                        WXUserInfo wxResponse = JSON.parseObject(response, WXUserInfo.class);
//                        从微信获取完 用户信息， 去完善用户信息
                        setUserInfo(wxResponse);

//                        保存的头像地址
                        SharedPreferenceUtil.saveString(LoginActivity.this, Constant.Img_url, wxResponse.getHeadimgurl());
//                        SharedPreferenceUtil.saveWXUserInfo(LoginActivity.this, "WXUserInfo", wxResponse);
                    }
                });
    }

    //        个人信息
    private void getUserInfo() {

        CommonDialog.showProgressDialog(LoginActivity.this);
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(LoginActivity.this, NetUtils.POST, UrlConstant.GETUSERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("个人中心用户信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetUserInfoBean singleBean = JsonUtil.getSingleBean(response.toString(), GetUserInfoBean.class);
                        data = singleBean.getData();
                        if (data != null) {
                            if (!RxDataTool.isEmpty(data.getMaster_examine())) {
//                                注册的未完成流程判断，在点击 注册 时会有说明
//                                为注册过信息 未支付 或未上传身份证做判断
                                str_master_exmine = data.getMaster_examine();
                                str_store_exmine = data.getStore_examine();


                            }
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(LoginActivity.this);

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
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(LoginActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    @Override
    public void onLocationChanged(TencentLocation location, int error, String s) {
        ViseLog.d("定位:-----------" + error);
        if (TencentLocation.ERROR_OK == error) {
//            RxToast.normal("定位成功");
            ViseLog.d("area_x" + area_x + "----------area_y" + area_y);
            area_x = String.valueOf(location.getLongitude());
            area_y = String.valueOf(location.getLatitude());


//            定位完成后，无论成功或失败，都应当尽快删除之前注册的位置监听器 监控器
            if (mLocationManager != null) {
                mLocationManager.removeUpdates(this);
//
            }

        } else if (error == 404) {

        }
    }

    //         前去走注册流程
    public void getLoginExpirel_Acticity(String text) {
        final DialogSureCancel rxDialogSure = new DialogSureCancel(this);//提示弹窗
        rxDialogSure.getTv_title().setText("提示");
        rxDialogSure.setCancelable(true);
        rxDialogSure.getTv_text().setText(text);
        rxDialogSure.getTv_sure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     master_examine : 0 未填信息1 .未支付2.未传身份证  4未填写信息
                Intent intent = null;

//                 未填信息
                if (str_master_exmine.equals("0") || str_master_exmine.equals("4")) {
                    intent = new Intent(LoginActivity.this, MasterCertificationActivity.class);

//                    1 .未支付
                } else if (str_master_exmine.equals("1")) {
                    intent = new Intent(LoginActivity.this, MastPaymentActivity.class);
                    if (!RxDataTool.isEmpty(data.getMaster_money())
                            && !RxDataTool.isEmpty(data.getMaster_vip_money())
                            && !RxDataTool.isEmpty(data.getMaster_order_sn()))
                        intent.putExtra("order_money", data.getMaster_vip_money());
                    intent.putExtra("order_money_old", data.getMaster_money());
                    intent.putExtra("order_sn", data.getMaster_order_sn());

//                    2.未传身份证
                } else if (str_master_exmine.equals("2")) {
                    intent = new Intent(LoginActivity.this, MasterCardCertificationActivity.class);
                }
                startActivity(intent);
                rxDialogSure.cancel();
            }


//                finish();


        });
        rxDialogSure.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rxDialogSure.cancel();


            }
        });

        rxDialogSure.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                RxToast.normal("再按一次退出程序");
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                killAll();
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(LoginActivity.this);
        if (timer!=null){
            timer.cancel();
        }
    }


    //      跳往系统设置权限界面
    private void showdialog(final String string) {
        final DialogSure rxDialogSure = new DialogSure(LoginActivity.this);//提示弹窗
        rxDialogSure.setTitle("提示");
        rxDialogSure.setCancelable(false);
        rxDialogSure.setContent(string);
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();
                getAppSetting();


            }
        });
        rxDialogSure.show();
    }


    @OnClick({R.id.tv_get_code, R.id.iv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            获取验证码
            case R.id.tv_get_code:
                if (et_mobile.getText().toString().length() == 11) {
                    sendPhoneCodes();

                } else {
                    RxToast.normal("请输入正确的手机号");
                    return;
                }
                break;

//                微信登录
            case R.id.iv_wechat:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
                break;
        }
    }

    private void initButten() {

        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_get_code.setEnabled(false);
                tv_get_code.setTextColor(getResources().getColor(R.color._6));
                tv_get_code.setText(("已发送(" + millisUntilFinished / 1000) + "s)");
            }

            @Override
            public void onFinish() {
                tv_get_code.setTextColor(getResources().getColor(R.color.maincolor));
                tv_get_code.setText("重新获取");
                tv_get_code.setEnabled(true);

            }
        }.start();

    }


}
