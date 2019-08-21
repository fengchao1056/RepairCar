package com.nengyuanbox.repaircar.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.mine.MineActivity;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.bean.GetMasterStoreListBean;
import com.nengyuanbox.repaircar.bean.GetOrderListBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSure;
import com.nengyuanbox.repaircar.utils.Dialog_getOrderDetails;
import com.nengyuanbox.repaircar.utils.Dialog_getOrderList;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LogUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.RxTextViewVerticalMore;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.map.UiSettings;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import io.reactivex.functions.Consumer;


public class StoreMainActivity extends MapActivity implements TencentLocationListener, View.OnClickListener {

    @BindView(R.id.rb_master)
    RadioButton rb_master;
    @BindView(R.id.rb_consumer)
    RadioButton rb_consumer;
    @BindView(R.id.rp_role)
    RadioGroup rp_role;
    private int GPS_REQUEST_CODE = 1;
    MapView mapview;
    private TencentMap tencentMap;
    private Marker marker;
    private TencentLocationManager mLocationManager;
    private Marker mLocationMarker;
    private ImageButton iv_back;
    private TextView tv_title_name;
    private ImageView iv_mine;
    private boolean isSetUserInfo;
    private String area_x;
    private String area_y;
    private int mPage = 1;
    private List<GetMasterStoreListBean.DataBean> getorderlist = new ArrayList<>();
    private List<GetOrderListBean.DataBeanX.DataBean> getorderlist1 = new ArrayList<>();
    private int order_index;
    private RxTextViewVerticalMore mUpview1;
    private String str_is_master, str_role;
    private Dialog_getOrderList dialog_getOrderList;
    private int runtime;
    private Dialog_getOrderDetails dialog_getOrderDetails;
    private String is_check_role = "master";
    private View view_marker1;
    private ImageView iv_marker1;
    private LatLng latLngLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);
        ButterKnife.bind(this);
        initView();
        mapview = (MapView) findViewById(R.id.mapview);
        mapview.onCreate(savedInstanceState);
        requestPermissions();
        creatMap();


        rp_role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                  点击师傅
                if (checkedId == rb_master.getId()) {
                    if (tencentMap!=null){
                        tencentMap.clearAllOverlays();

                    }
                    getOrderList("master");


//                  Toast.makeText(StoreMainActivity.this, "师傅", Toast.LENGTH_SHORT).show();
                    is_check_role = "master";
                    return;


//                  点击消费者
                } else {
                    if (tencentMap!=null){
                        tencentMap.clearAllOverlays();

                    }
                    if (latLngLocation!=null){
                        getOrderList_consumer(latLngLocation);
                    }

//                  Toast.makeText(StoreMainActivity.this, "消费者", Toast.LENGTH_SHORT).show();
                    is_check_role = "consumer";
                    return;
                }
            }
        });


    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(StoreMainActivity.this);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CALL_PHONE
        )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtil.d("Feng", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            if (permission.name.contains("android.permission.ACCESS_COARSE_LOCATION")) {
                                showdialog("来修车向您申请定位权限，以提供更加精准的定位服务");
                            }
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtil.d("Feng", permission.name + " is denied. More info should be provided.");
                        } else {
                            if (permission.name.contains("android.permission.ACCESS_COARSE_LOCATION")) {
                                showdialog("来修车向您申请定位权限，以提供更加精准的定位服务");
                            }
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtil.d("Feng", permission.name + " is denied.");
                        }
                    }
                });
    }

    private void creatMap() {
        //获取TencentMap实例
        tencentMap = mapview.getMap();
//设置卫星底图
        tencentMap.setSatelliteEnabled(false);
//设置实时路况开启
//        tencentMap.setTrafficEnabled(true);
//设置缩放级别
        tencentMap.setZoom(26);

        /*
         *
         * UiSettings类用于设置地图的视图状态，如Logo位置设置、比例尺位置设置、地图手势开关等。下面是UiSettings类的使用示例：
         * */
        //获取UiSettings实例
        UiSettings uiSettings = mapview.getUiSettings();
//设置logo到屏幕底部中心
        uiSettings.setLogoPosition(UiSettings.LOGO_POSITION_CENTER_BOTTOM);
//设置比例尺到屏幕右下角
        uiSettings.setScaleViewPosition(UiSettings.SCALEVIEW_POSITION_RIGHT_BOTTOM);
//启用缩放手势(更多的手势控制请参考开发手册)
        uiSettings.setZoomGesturesEnabled(true);
    }

    private void initMap() {

        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker mMarker) {
                // TODO Auto-generated method stub
//                  如果是师傅的话 ，  可以点击查看信息
                if (is_check_role.equals("master")) {
                    showgetOrderList(StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLatitude())),
                            StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLongitude())));
//                    如果是消费者模块  点击图标不做处理
                } else if (is_check_role.equals("consumer")) {

                }

//                Toast.makeText(MainActivity.this, mMarker.getPosition() + "", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
//infoWindow点击事件
        tencentMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub

            }
        });
//Marker拖拽事件
//        tencentMap.setOnMarkerDraggedListener(new TencentMap.OnMarkerDraggedListener() {
//
//            //拖拽开始时调用
//            @Override
//            public void onMarkerDragStart(Marker arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            //拖拽结束后调用
//            @Override
//            public void onMarkerDragEnd(Marker arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            //拖拽时调用
//            @Override
//            public void onMarkerDrag(Marker arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });

        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(5000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        mLocationManager.requestLocationUpdates(request, this);


    }


    //  邀请师傅入驻门店
    public void showgetOrderList(String area_x, String area_y) {
        if (getorderlist.size() > 0) {
            for (int i = 0; i < getorderlist.size(); i++) {

                if (area_x.equals(StringUtil.format6Decimals(getorderlist.get(i).getArea_y())) && area_y.equals(StringUtil.format6Decimals(getorderlist.get(i).getArea_x()))) {
                    order_index = i;
                    dialog_getOrderDetails = new Dialog_getOrderDetails(StoreMainActivity.this, 1, Gravity.BOTTOM);
                    dialog_getOrderDetails.getTv_master_name().setText(getorderlist.get(i).getName());
                    dialog_getOrderDetails.getTv_master_phone().setText(StringUtil.getPhone_Asterisk(getorderlist.get(i).getMaster_phone()));
                    dialog_getOrderDetails.getTv_order_num().setText(getorderlist.get(i).getOrder_num() + "单");


                    if (!RxDataTool.isNullString(getorderlist.get(i).getService())){
//                        dialog_getOrderDetails.getTv_repair_experience().setText(""+getorderlist.get(i).getService());//维修经验

                    }
                    if (!RxDataTool.isEmpty(getorderlist.get(i).getStar())){
                        dialog_getOrderDetails.getTv_store_star().setText(getorderlist.get(i).getStar());

                    }
                    Glide.with(StoreMainActivity.this)
                            .load(getorderlist.get(i).getStore_img()).asBitmap()
                            .error(R.drawable.requestout)
                            .placeholder(R.drawable.requestout)
                            .into(dialog_getOrderDetails.getIv_imagehead());
                    dialog_getOrderDetails.getTv_take_order_type().setText(getorderlist.get(i).getService());

                    dialog_getOrderDetails.setFullScreenWidth();
                    dialog_getOrderDetails.show();

                    dialog_getOrderDetails.getBt_invite_setlled().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");

                            addUserMasterStore(getorderlist.get(order_index).getUid(), uid, "2");
                        }
                    });
                    break;
                } else {
                    continue;
                }
            }


        }


    }

    //    用户选择允许或拒绝后，会回调onRequestPermissionsResult方法:
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }


    // 地图刷新ding位
    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (TencentLocation.ERROR_OK == error) {
//            RxToast.normal("定位成功");
            latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());

            if (isSetUserInfo == false) {
                area_x = String.valueOf(location.getLongitude());
                area_y = String.valueOf(location.getLatitude());
                ViseLog.d("area_x" + area_x + "----------area_y" + area_y);

            }

            if (is_check_role.equals("master")) {
                getOrderList("master");

            } else {

                getOrderList_consumer(latLngLocation);
            }


            if (mLocationMarker == null) {
                mLocationMarker =
                        tencentMap.addMarker(new MarkerOptions().
                                position(latLngLocation).
                                icon(BitmapDescriptorFactory
                                        .defaultMarker()));
//                                icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_location)));

                //设置地图中心点
                tencentMap.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
            } else {
                mLocationMarker.setPosition(latLngLocation);
            }
            // 定位成功
        } else {
//            RxToast.normal("定位失败");
            // 定位失败
        }
    }

    //    附近师傅/门店位置
    private void getOrderList(String str_role) {

//	查询方式（master：师傅 store：门店）
        JSONObject jsonObject = new JSONObject();

        try {

//            if (!RxDataTool.isEmpty(str_role)) {
//                if (str_role.equals("store")) {
//                    jsonObject.put("getType", "store");
//                } else if (str_role.equals("master")) {
//                    jsonObject.put("getType", "master");
//                }
//            }

//            jsonObject.put("page", String.valueOf(mPage));
//            jsonObject.put("pagesize", "10");
            jsonObject.put("area_x", area_x);
            jsonObject.put("area_y", area_y);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(StoreMainActivity.this, NetUtils.POST, UrlConstant.GETMASTERLIST, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("查询订单" + response.toString());
                    isSetUserInfo = true;

                    if ("2000".equals(response.getString("code"))) {


                        GetMasterStoreListBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetMasterStoreListBean.class);
                        List<GetMasterStoreListBean.DataBean> data = getOrderListBean.getData();

                        getorderlist.clear();
                        getorderlist1.clear();
                        tencentMap.clearAllOverlays();

                        if (data != null && data.size() > 0) {
                            getorderlist.addAll(data);
                            for (int i = 0; i < getorderlist.size(); i++) {
                                final View view_marker = LayoutInflater.from(StoreMainActivity.this).inflate(R.layout.map_marker, null);
                                final ImageView iv_marker = view_marker.findViewById(R.id.iv_marker);
//                                ViseLog.d("覆盖地图"+i);
                                final int finalI = i;
                                Glide.with(StoreMainActivity.this).load(getorderlist.get(i).getStore_img()).
                                        placeholder(R.drawable.changjianwenti)
                                        .error(R.drawable.changjianwenti)
                                        .into(new SimpleTarget<GlideDrawable>() {
                                            @Override
                                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                                //展示图片
//                                                116.29976969401042","area_y":"40.04985812717014
                                                iv_marker.setImageDrawable(resource);
                                                Bitmap bitmap = StringUtil.getViewBitmap(view_marker);
                                                marker = tencentMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(RxDataTool.stringToDouble(getorderlist.get(finalI).getArea_y()),
                                                                RxDataTool.stringToDouble(getorderlist.get(finalI).getArea_x())))
                                                        .anchor(0.5f, 0.5f)
                                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                                        .draggable(false));//s是否可以拖拽
//                                                marker.showInfoWindow();// 设置默认显示一个infoWindow

                                            }
                                        });

                            }
                        } else {


                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreMainActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("覆盖地图错误" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                ViseLog.d(throwable.toString() + responseString);
                CommonDialog.showInfoDialog(StoreMainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreMainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    private void getOrderList_consumer(final LatLng latLngLocation) {
        str_is_master = SharedPreferenceUtil.getString(StoreMainActivity.this, Constant.IS_MASTER, "");

//        if (str_is_master.equals("0")) {
//            RxToast.normal("不是师傅");
//        } else {

        // 1：师傅待处理订单列表
        // 2：师傅已完成订单列表
        // 3：地图带抢订单
        // 4：用户全部订单
        // 5：用户已完成订单
        // 6：用户退款订单
        // 7：师傅已取消订单
        JSONObject jsonObject = new JSONObject();

        try {

            if (str_is_master.equals("0")) {
//                RxToast.normal("不是师傅");
                jsonObject.put("getType", "4");
            } else {
                jsonObject.put("getType", "3");
            }

            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");
            jsonObject.put("area_x", area_x);
            jsonObject.put("area_y", area_y);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(StoreMainActivity.this, NetUtils.POST, UrlConstant.GETORDERLIST, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("查询订单" + response.toString());
                    isSetUserInfo = true;

                    if ("2000".equals(response.getString("code"))) {
                        GetOrderListBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetOrderListBean.class);
                        GetOrderListBean.DataBeanX data = getOrderListBean.getData();

                        getorderlist.clear();
                        getorderlist1.clear();
                        tencentMap.clearAllOverlays();
//                        mLocationMarker =
//                                tencentMap.addMarker(new MarkerOptions().
//                                        position(latLngLocation).
//                                        icon(BitmapDescriptorFactory
//                                                .defaultMarker()));


                        if (data.getData() != null && data.getData().size() > 0) {
                            getorderlist1.addAll(data.getData());


                            for (int i = 0; i < getorderlist.size(); i++) {
//                                ViseLog.d("覆盖地图"+i);
                                final int finalI = i;
                                view_marker1 = LayoutInflater.from(StoreMainActivity.this).inflate(R.layout.map_marker, null);
                                iv_marker1 = view_marker1.findViewById(R.id.iv_marker);
                                Glide.with(StoreMainActivity.this).load(getorderlist.get(i).getImg_url()).
                                        placeholder(R.drawable.changjianwenti)

                                        .error(R.drawable.changjianwenti)
                                        .into(new SimpleTarget<GlideDrawable>() {
                                            @Override
                                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                                //展示图片
//                                                116.29976969401042","area_y":"40.04985812717014
                                                iv_marker1.setImageDrawable(resource);
                                                Bitmap bitmap = StringUtil.getViewBitmap(view_marker1);
                                                marker = tencentMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(RxDataTool.stringToDouble(getorderlist1.get(finalI).getArea_y()),
                                                                RxDataTool.stringToDouble(getorderlist1.get(finalI).getArea_x())))
                                                        .anchor(0.5f, 0.5f)
                                                        .title(getorderlist1.get(finalI).getUname())
                                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                                        .draggable(false));//是否可以拖拽
//                                                marker.showInfoWindow();// 设置默认显示一个infoWindow

                                            }
                                        });

                            }
                        } else {
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreMainActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("覆盖地图错误" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                ViseLog.d(throwable.toString() + responseString);
                CommonDialog.showInfoDialog(StoreMainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreMainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    //    提交认领门店/师傅
    private void addUserMasterStore(String master_uid, String store_uid, String userType) {

        JSONObject jsonObject = new JSONObject();

        try {
//            师傅uid
            jsonObject.put("master_uid", master_uid);
//            门店uid
            jsonObject.put("store_uid", store_uid);
//            提交者（1，师傅 2，门店）
            jsonObject.put("UserType", userType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(StoreMainActivity.this, NetUtils.POST, UrlConstant.ADDUSERMASTERSTORE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("提交认领门店/师傅" + response.toString());


                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal("已成功发送邀请，请等待师傅是否同意");
                        dialog_getOrderDetails.dismiss();

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        dialog_getOrderDetails.dismiss();
                        LoginExpirelUtils.getLoginExpirel(StoreMainActivity.this);

                    } else {
                        dialog_getOrderDetails.dismiss();
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("提交认领门店/师傅" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                ViseLog.d("提交认领门店/师傅:" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(StoreMainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d("提交认领门店/师傅:" + throwable.toString());
                CommonDialog.showInfoDialog(StoreMainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    @Override
    public void onStatusUpdate(String name, int status, String desc) {
        if (name.equals("wifi") && status == 5) {
            RxToast.normal("请打开位置开关");
        }

    }

    private void initView() {
        mUpview1 = (RxTextViewVerticalMore) findViewById(R.id.upview1);
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("来修车");
        iv_mine = (ImageView) findViewById(R.id.iv_mine);
        iv_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_mine:

                startActivity(new Intent(StoreMainActivity.this, MineActivity.class));
                finish();

                break;
        }
    }


    //      跳往系统设置权限界面
    private void showdialog(final String string) {
        final DialogSure rxDialogSure = new DialogSure(StoreMainActivity.this);//提示弹窗
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


    /**
     * 跳转设置 应用设置界面
     *
     * @return
     */
    public Intent getAppSetting() {
        Intent localIntent = null;
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(localIntent);
        return localIntent;
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                RxToast.normal("再按一次退出程序");
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                // 杀死当前的进程
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void openGPSSEtting() {
        if (checkGpsIsOpen()) {
            initMap();
//            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        } else {
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
                            startActivityForResult(intent, GPS_REQUEST_CODE);
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
        if (requestCode == GPS_REQUEST_CODE) {
            openGPSSEtting();
        }
    }


    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
//        查询是否开启定位权限
        openGPSSEtting();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapview.onStop();

        super.onStop();
    }
}
