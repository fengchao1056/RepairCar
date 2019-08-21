package com.nengyuanbox.repaircar.activity.mine;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.OrderTrackAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetOrderInfoBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.ClickUtil;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DateUtils;
import com.nengyuanbox.repaircar.utils.DialogSureCancel;
import com.nengyuanbox.repaircar.utils.Dialog_MapServiceDetails;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.RoutePlanUtil;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cc.shinichi.library.ImagePreview;
import cz.msebera.android.httpclient.Header;

//订单详情
public class OrderDetailsActivity extends BaseActivity implements TencentLocationListener {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_repair_time_start)
    TextView tv_repair_time_start;
    @BindView(R.id.tv_repair_time_end)
    TextView tv_repair_time_end;
    @BindView(R.id.tv_repair_money)
    TextView tv_repair_money;
    @BindView(R.id.tv_repair_star)
    TextView tv_repair_star;
    @BindView(R.id.bt_set_off)
    Button bt_set_off;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;


    @BindView(R.id.ll_contact_clients)
    LinearLayout ll_contact_clients;
    @BindView(R.id.ll_bt_submit)
    LinearLayout ll_bt_submit;
    @BindView(R.id.tv_client_name)
    TextView tv_client_name;
    @BindView(R.id.tv_repair_state)
    TextView tv_repair_state;
    @BindView(R.id.tv_client_type)
    TextView tv_client_type;
    @BindView(R.id.tv_client_time)
    TextView tv_client_time;
    @BindView(R.id.tv_client_address)
    TextView tv_client_address;
    @BindView(R.id.iv_car_order_detail)
    ImageView iv_car_order_detail;
    @BindView(R.id.tv_order_phone)
    TextView tv_order_phone;
    @BindView(R.id.bt_copy)
    Button bt_copy;
    @BindView(R.id.tv_malfunction_type)
    TextView tv_malfunction_type;
    @BindView(R.id.tv_malfunction_remarks)
    TextView tv_malfunction_remarks;
    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.tv_car_brand)
    TextView tv_car_brand;
    @BindView(R.id.rv_order_track)
    RecyclerView rv_order_track;
    @BindView(R.id.tv_order_receiving_master)
    TextView tv_order_receiving_master;
    @BindView(R.id.ll_order_receiving_master)
    LinearLayout ll_order_receiving_master;

    @BindView(R.id.ll_service_address)//维修地点
            LinearLayout ll_service_address;
    private String order_sn;
    private GetOrderInfoBean getOrderInfoBean;
    private String str_img_car;
    private Dialog_MapServiceDetails dialog_mapServiceDetails;
    private String orderAreax;
    private String orderAreay;
    private String orderAdress;
    private TencentLocationManager mLocationManager;
    private String area_x;
    private String area_y;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        Loation();
//        查询订单详情
        getOrderInfo();
        if (!RxDataTool.isEmpty(getIntent().getStringExtra("order_sn"))) {
            order_sn = getIntent().getStringExtra("order_sn");
        }
        String str_role = SharedPreferenceUtil.getString(context, Constant.ROLE, "");
//           不为空  就是已完成订单详情 不需要显示  撤销 完成 按钮
        if (!RxDataTool.isNullString(getIntent().getStringExtra("order_state")) || str_role.equals("master")) {
            if (getIntent().getStringExtra("order_state").equals("已取消")) {
                ll_bt_submit.setVisibility(View.GONE);
            } else {
                ll_bt_submit.setVisibility(View.GONE);
            }

        } else {
            ll_bt_submit.setVisibility(View.VISIBLE);
        }

        bt_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  ClipboardManager myClipboard;
//                  myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
//                  ClipData myClip;
//                  String text = "hello world";
//                  myClip = ClipData.newPlainText("text", text);
//                  myClipboard.setPrimaryClip(myClip);

                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                cmb.setText(tv_order_phone.getText().toString());
                RxToast.normal("已复制");
            }
        });
    }

    @Override
    protected void setListener() {
        iv_back.setImageResource(R.drawable.icon_button);
        tv_title_name.setText("订单详情");

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

    //    处理订单
    private void setOrder(String setType) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        try {
//         订单号
            jsonObject.put("order_sn", order_sn);
//            处理方式（1：用户取消订单并退款 2：师傅取消订单并退款 3：师傅操作订单完成 4：师傅操作订单出发）
            jsonObject.put("setType", setType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(OrderDetailsActivity.this, NetUtils.POST, UrlConstant.SETORDER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("处理订单" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        getOrderInfo();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(OrderDetailsActivity.this);

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
                ViseLog.d("处理订单报错:" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(OrderDetailsActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("处理订单报错:" + throwable.toString());
                CommonDialog.showInfoDialog(OrderDetailsActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d("处理订单报错:" + throwable.toString());
                CommonDialog.closeProgressDialog();
                CommonDialog.showInfoDialog(OrderDetailsActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    private void getOrderInfo() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("order_sn", getIntent().getStringExtra("order_sn"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(OrderDetailsActivity.this, NetUtils.POST, UrlConstant.GETORDERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("查询订单详情:" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        getOrderInfoBean = JsonUtil.getSingleBean(response.toString(), GetOrderInfoBean.class);
                        GetOrderInfoBean.DataBean data = getOrderInfoBean.getData();
//                         保修时间
                        tv_repair_time_start.setText(data.getAdd_time());
                        OrderTrackAdapter orderTrackAdapter = new OrderTrackAdapter(data.getOrder_detail(), OrderDetailsActivity.this);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this) {
                            @Override
                            public boolean canScrollVertically() {
                                // 直接禁止垂直滑动
                                return false;
                            }
                        };
                        rv_order_track.setLayoutManager(linearLayoutManager);
                        rv_order_track.setAdapter(orderTrackAdapter);
//                        抢单时间
                        tv_repair_time_end.setText(data.getHandle_time());
                        if (data.getOrder_status().equals("1")) {
                            tv_repair_state.setText("待付款");
                        } else if (data.getOrder_status().equals("2")) {
                            tv_repair_state.setText("待抢单");
                        } else if (data.getOrder_status().equals("3")) {
                            tv_repair_state.setText("抢单成功,待出发");
                            bt_set_off.setText("出发");
                        } else if (data.getOrder_status().equals("4")) {
                            tv_repair_state.setText("抢单成功,已出发");
                            bt_set_off.setText("完成");

                        } else if (data.getOrder_status().equals("5")) {
                            tv_repair_state.setText("已完成");

                        } else if (data.getOrder_status().equals("6")) {
                            tv_repair_state.setText("已退款");
                        }
                        String str_role = SharedPreferenceUtil.getString(OrderDetailsActivity.this, Constant.ROLE, "");
                        if (data.getOrder_status().equals("5") || data.getOrder_status().equals("6")) {
                            ll_bt_submit.setVisibility(View.GONE);

                        } else {
                            ll_bt_submit.setVisibility(View.VISIBLE);

                        }


                        if (!RxDataTool.isEmpty(str_role) && str_role.equals("store")) {
                            ll_order_receiving_master.setVisibility(View.VISIBLE);
                            ll_bt_submit.setVisibility(View.GONE);
                            tv_order_receiving_master.setText(data.getHandle_user_name());
                        } else {
                            ll_order_receiving_master.setVisibility(View.GONE);
//                            tv_order_receiving_master.setText(data.getHandle_user_name());
                        }

                        if (!RxDataTool.isEmpty(data.getApply_user_evaluate())) {
                            tv_repair_star.setText(data.getApply_user_evaluate());

                        } else {
                            tv_repair_star.setText(data.getApply_user_star());
                        }
//                        订单服务费
                        tv_repair_money.setText(data.getMoney() + "元");
                        if (!RxDataTool.isEmpty(data.getArea_x()) && !RxDataTool.isEmpty(data.getArea_y())) {
                            orderAreax = data.getArea_x();
                            orderAreay = data.getArea_y();
                            orderAdress = data.getAddress();
                        }


//                        客户信息
                        tv_client_name.setText(data.getUname());
//                        客户类型
                        tv_client_type.setText("普通");
//                        期望时间
                        tv_client_time.setText(data.getExpected_time());
//                        维修地点
                        tv_client_address.setText(data.getAddress());

//                        订单号码
                        tv_order_phone.setText(data.getOrder_sn());
//                        故障类型
                        tv_malfunction_type.setText(data.getService());
//                        故障备注
                        tv_malfunction_remarks.setText(data.getQuestion_desc());
//                        车辆类型
                        tv_car_type.setText(data.getCar_name());
//                        车辆品牌
                        tv_car_brand.setText(data.getCar_sign());

                        if (!RxDataTool.isEmpty(data.getImg())) {

                            str_img_car = data.getImg();
                            Glide.with(OrderDetailsActivity.this)
                                    .load(data.getImg())
                                    .placeholder(R.drawable.requestout)
                                    .error(R.drawable.requestout)
                                    .into(iv_car_order_detail);
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(OrderDetailsActivity.this);

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
                CommonDialog.showInfoDialog(OrderDetailsActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(OrderDetailsActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    @OnClick({R.id.iv_back, R.id.bt_set_off, R.id.bt_cancel, R.id.bt_copy, R.id.ll_service_address, R.id.ll_contact_clients, R.id.iv_car_order_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
//                出发
            case R.id.bt_set_off:
//                处理方式（   1：用户取消订单并退款
                // 2：师傅取消订单并退款
                // 3：师傅操作订单完成
                // 4：师傅操作订单出发）
                if (bt_set_off.getText().toString().equals("出发")) {
                    setOrder("4");

                } else if (bt_set_off.getText().toString().equals("完成")) {
                    setOrder("3");
                }

                break;
            case R.id.bt_cancel:
                if (!ClickUtil.isFastClick()) {
                    setOrder("2");

                } else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }

                break;
            case R.id.bt_copy:
                break;

            case R.id.iv_car_order_detail:

                if (!RxDataTool.isEmpty(str_img_car)) {

                    ImagePreview
                            .getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
                            .setContext(OrderDetailsActivity.this)

                            // 设置从第几张开始看（索引从0开始）
                            .setIndex(0)

                            //=================================================================================================
                            // 有三种设置数据集合的方式，根据自己的需求进行三选一：
                            // 1：第一步生成的imageInfo List
//                            .setImageInfoList(imageInfoList)

                            // 2：直接传url List
                            //.setImageList(List<String> imageList)

                            // 3：只有一张图片的情况，可以直接传入这张图片的url
                            .setImage(str_img_car)
                            //=================================================================================================

                            // 开启预览
                            .start();
                }

                break;
//                拨打电话
            case R.id.ll_contact_clients:
//                       如果是退款订单 无法拨打电话
                if (getOrderInfoBean.getData().getOrder_status().equals("6")) {
                    RxToast.normal("订单已取消，无法拨打电话");
                    return;

//                       如果是待处理订单 超过24小时无法联系电话
                } else if (getOrderInfoBean.getData().getOrder_status().equals("3")) {
                    if (StringUtil.TimeCompare(getOrderInfoBean.getData().getAdd_time(), DateUtils.getCurrentTime()) == true) {

                        getLoginExpirel_Acticity(getOrderInfoBean.getData().getTel_phone(), getOrderInfoBean.getData().getTel_phone());
                    } else {
                        getLoginExpirel_Acticity("订单已超过72小时，请与客服联系。", getOrderInfoBean.getData().getService_telephone());

                    }
//                                 已完成订单 可以拨打电话
                } else {
                    getLoginExpirel_Acticity(getOrderInfoBean.getData().getTel_phone(), getOrderInfoBean.getData().getTel_phone());
                }

                break;
//                 调取第三方地图导航
            case R.id.ll_service_address:


                goMap();

                break;
        }
    }

    //     选择导航地图
    private void goMap() {

      ViseLog.d("我的位置"+orderAreax+"-----------"+ orderAreay);
        dialog_mapServiceDetails = new Dialog_MapServiceDetails(OrderDetailsActivity.this, 1, Gravity.BOTTOM);
        dialog_mapServiceDetails.setFullScreenWidth();
        dialog_mapServiceDetails.getBaidu_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RoutePlanUtil.isInstalledBaidu()) {

                    RoutePlanUtil.newInstance().startRoutePlan(RoutePlanUtil.FLAG_BAIDU, area_x, area_y, "我的位置", orderAreax, orderAreay, orderAdress);

                    dialog_mapServiceDetails.dismiss();
                } else {
                    Toast.makeText(OrderDetailsActivity.this, "未安装百度地图", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                    dialog_mapServiceDetails.dismiss();
//                    }
                }
            }
        });

        dialog_mapServiceDetails.getGaode_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RoutePlanUtil.isInstalledGaode()) {
                    RoutePlanUtil.newInstance().startRoutePlan(RoutePlanUtil.FLAG_GAODE, orderAreax, orderAreay, orderAdress);
                    dialog_mapServiceDetails.dismiss();
                } else {
                    Toast.makeText(OrderDetailsActivity.this, "未安装高德地图", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    dialog_mapServiceDetails.dismiss();
                }
            }
        });

        dialog_mapServiceDetails.getTencent_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RoutePlanUtil.isInstalledTencent()) {
                    RoutePlanUtil.newInstance().startRoutePlan(RoutePlanUtil.FLAG_TENCENT, orderAreax, orderAreay, orderAdress);
                    dialog_mapServiceDetails.dismiss();
                } else {
                    Uri uri = Uri.parse("market://details?id=com.tencent.map");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    Toast.makeText(OrderDetailsActivity.this, "未安装腾讯地图", Toast.LENGTH_SHORT).show();
                    dialog_mapServiceDetails.dismiss();
                }
            }
        });
        dialog_mapServiceDetails.getCancel_btn2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_mapServiceDetails.dismiss();
            }
        });
        dialog_mapServiceDetails.show();


    }


    public void getLoginExpirel_Acticity(String text, final String phone) {
        final DialogSureCancel rxDialogSure = new DialogSureCancel(this);//提示弹窗
        rxDialogSure.getTv_title().setText("提示");
        rxDialogSure.setCancelable(true);
        rxDialogSure.getTv_text().setText(text);
        TextView tv_sure = rxDialogSure.getTv_sure();
        tv_sure.setText("呼叫");

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(OrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    RxToast.normal("请开启打电话权限");

                    return;
                }
                startActivity(intent);
                rxDialogSure.cancel();


            }
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
    public void onLocationChanged(TencentLocation location, int error, String s) {
        ViseLog.d("定位:-----------" + error);
        if (TencentLocation.ERROR_OK == error) {
//            RxToast.normal("定位成功");
//            ViseLog.d("area_x" + area_x + "----------area_y" + area_y);
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

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
