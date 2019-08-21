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
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.mine.MineActivity;
import com.nengyuanbox.repaircar.activity.mine.OrderActivity;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.bean.GetMasterStoreBean;
import com.nengyuanbox.repaircar.bean.GetOrderListBean;
import com.nengyuanbox.repaircar.bean.GetUserMasterStoreStatusBean;
import com.nengyuanbox.repaircar.bean.TXApiAddressBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSure;
import com.nengyuanbox.repaircar.utils.Dialog_MapServiceDetails;
import com.nengyuanbox.repaircar.utils.Dialog_MasterClaimStore;
import com.nengyuanbox.repaircar.utils.Dialog_getOrderList;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LogUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.RoutePlanUtil;
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
import com.tencent.mapsdk.raster.model.CameraPosition;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.reactivex.functions.Consumer;
import okhttp3.Call;


public class MainActivity extends MapActivity implements TencentLocationListener, View.OnClickListener {

    private int GPS_REQUEST_CODE = 1;
    MapView mapview;
    private TencentMap tencentMap;
    private Marker marker;
    private Marker marker1;
    private View view_marker, view_marker1;
    private ImageView iv_marker, iv_marker1;
    private TencentLocationManager mLocationManager;
    private Marker mLocationMarker;
    private ImageButton iv_back;
    private TextView tv_title_name;
    private ImageView iv_mine;
    private boolean isSetUserInfo;
    private String area_x;
    private String area_y;
    private int mPage = 1;
    private List<GetOrderListBean.DataBeanX.DataBean> getorderlist = new ArrayList<>();
    private List<GetMasterStoreBean.DataBean> getorderlist1 = new ArrayList<>();
    private int order_index;
    private RxTextViewVerticalMore mUpview1;
    private String str_is_master;
    private Dialog_getOrderList dialog_getOrderList;
    private int runtime;
    boolean is_master_claim = true;
    private boolean is_Vocice = true;  //是否播放订单播报语音
//    private boolean is_Order_Receiving = true;  //是否可以接单

    private static String TAG = MainActivity.class.getSimpleName();
    private String is_check_role = "master";
    RadioButton rb_master;
    RadioButton rb_consumer;
    RadioGroup rp_role;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    private SpeechSynthesizer mTts;
    private String mEngineType;
    private Dialog_MasterClaimStore dialog_masterClaimStore;
    private boolean is_setUserMasterStore = false;
    private LatLng latLngLocation;
    private Dialog_MapServiceDetails dialog_mapServiceDetails;
    private String str_store_address;
    private String str_latitude;
    private String str_longitude;
    private LatLng mapCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mapview = (MapView) findViewById(R.id.mapview);
        rb_master = (RadioButton) findViewById(R.id.rb_master);
        rb_consumer = (RadioButton) findViewById(R.id.rb_consumer);
        rp_role = (RadioGroup) findViewById(R.id.rp_role);

        rp_role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                  点击消费者
                if (checkedId == rb_master.getId()) {
                    if (tencentMap != null) {
                        tencentMap.clearAllOverlays();
                    }
                    if (latLngLocation != null) {
                        getOrderList(latLngLocation);
                    }
                    is_check_role = "master";
                    return;


//                  点击店铺
                } else {
                    if (tencentMap != null) {
                        tencentMap.clearAllOverlays();
                    }
                    getMasterStoreList();

                    is_check_role = "consumer";
                    return;
                }
            }
        });

        mapview.onCreate(savedInstanceState);

//      申请权限
        requestPermissions();

//      初始化地图
        creatMap();

//      初始化语音
        kdxf();


    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
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

    private void creatMap() {
        //获取TencentMap实例
        tencentMap = mapview.getMap();
//设置卫星底图
        tencentMap.setSatelliteEnabled(false);
//设置实时路况开启
//        tencentMap.setTrafficEnabled(true);
//设置缩放级别
        tencentMap.setZoom(26);
        tencentMap.setOnMapCameraChangeListener(new TencentMap.OnMapCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
//                   Toast.makeText(MainActivity.this, "11", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
//                   LatLng mapCenter = tencentMap.getMapCenter();
                mapCenter = tencentMap.getMapCenter();


//                   LatLng target = cameraPosition.getTarget();
//                   Toast.makeText(MainActivity.this, ""+mapCenter.getLatitude()+"---"+target.getLatitude(), Toast.LENGTH_SHORT).show();

            }
        });



        /*
         *
         * UiSettings类用于设置地图的视图状态，如Logo位置设置、比例尺位置设置、地图手势开关等。下面是UiSettings类的使用示例：
         * */
        //获取UiSettings实例
        UiSettings uiSettings = mapview.getUiSettings();
//设置logo到屏幕底部中心
        uiSettings.setLogoPosition(UiSettings.LOGO_POSITION_CENTER_BOTTOM);

        uiSettings.setAnimationEnabled(true);
//设置比例尺到屏幕右下角
        uiSettings.setScaleViewPosition(UiSettings.SCALEVIEW_POSITION_RIGHT_BOTTOM);
//启用缩放手势(更多的手势控制请参考开发手册)
        uiSettings.setZoomGesturesEnabled(true);
    }

    private void initMap() {
        //Marker点击事件
        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker mMarker) {
                // TODO Auto-generated method stub
//                if (is_Order_Receiving) {
//                showgetOrderList(StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLatitude())),
//                        StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLongitude())));
//                } else {
//                    RxToast.normal("您有订单尚未完成，暂不可接单");
//                }

                str_latitude = StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLatitude()));
                str_longitude = StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLongitude()));
                if (is_check_role.equals("master")) {

                    showgetOrderList(str_latitude,
                            str_longitude);

//                    如果是门店模块模块  点击图标导航
                } else if (is_check_role.equals("consumer")) {
                    if (getorderlist1.size() > 0) {
                        for (int i = 0; i < getorderlist1.size(); i++) {

                            if (str_latitude.equals(StringUtil.format6Decimals(getorderlist1.get(i).getArea_y())) && str_longitude.equals(StringUtil.format6Decimals(getorderlist1.get(i).getArea_x()))) {
                                order_index = i;

                                ViseLog.d(getorderlist1.get(order_index).getAddress() + str_latitude + "++++" + StringUtil.format6Decimals(getorderlist1.get(i).getArea_y()));

                                str_store_address = getorderlist1.get(order_index).getAddress();
//                                第三方导航地图
                                goMap(StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLatitude())),
                                        StringUtil.format6Decimals(String.valueOf(mMarker.getPosition().getLongitude()))
                                        , "地图选定位置");
                                break;
                            } else {
                                continue;
                            }
                        }
                    }


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
        request.setInterval(3000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        mLocationManager.requestLocationUpdates(request, this);


    }


    //    检查是否开启定位服务
    private void openGPSSEtting() {
        if (checkGpsIsOpen()) {
            initMap();
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
    protected void onResume() {
        mapview.onResume();
//        查询是否开启定位权限
        openGPSSEtting();
//        is_Vocice = true;
        super.onResume();
    }


    @Override
    protected void onStop() {
//        mapview.onStop();

        super.onStop();
    }

    //     抢单弹窗
    public void showgetOrderList(String area_x, String area_y) {
        if (getorderlist.size() > 0) {
            for (int i = 0; i < getorderlist.size(); i++) {

                if (area_x.equals(StringUtil.format6Decimals(getorderlist.get(i).getArea_y())) && area_y.equals(StringUtil.format6Decimals(getorderlist.get(i).getArea_x()))) {
                    order_index = i;
                    dialog_getOrderList = new Dialog_getOrderList(MainActivity.this, 1, Gravity.BOTTOM);
                    dialog_getOrderList.setFullScreenWidth();
                    dialog_getOrderList.setCancelable(true);
                    if (!RxDataTool.isEmpty(order_index)) {
                        final GetOrderListBean.DataBeanX.DataBean dataBean = getorderlist.get(order_index);
//                       故障类型
                        dialog_getOrderList.getTv_fault_type().setText(dataBean.getService());
                        if (!RxDataTool.isEmpty(dataBean.getQuestion_desc())) {
                            //                      故障描述
                            dialog_getOrderList.getTv_question_desc().setText(dataBean.getQuestion_desc());
                        } else {
                            //                      故障描述
                            dialog_getOrderList.getTv_question_desc().setText("暂无描述");
                        }


//                      车辆品牌
                        dialog_getOrderList.getTv_car_sign().setText(dataBean.getCar_sign());

//                      联系电话

                        if (!RxDataTool.isEmpty(dataBean.getTel_phone())) {
                            dialog_getOrderList.getTv_tel_phone().setText(StringUtil.getPhone_Asterisk(dataBean.getTel_phone()));

                        }

//                      期望时间
                        dialog_getOrderList.getTv_expected_time().setText(dataBean.getExpected_time());

//                      地址
                        dialog_getOrderList.getTv_service_address().setText(dataBean.getAddress());

                        dialog_getOrderList.getBt_grab_order().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rushOrders(dataBean.getArea_x(), dataBean.getArea_y(), dataBean.getOrder_sn());
                            }
                        });

                        dialog_getOrderList.show();
                    }
                    break;
                } else {
                    continue;
                }
            }


        }


    }

//      点击上面浮层抢单 弹窗

    public void showgetOrderStateList() {
        if (getorderlist.size() > 0) {
//            for (int i = 0; i < getorderlist.size(); i++) {
            if (dialog_getOrderList != null) {
                dialog_getOrderList.dismiss();
            }
//                if (area_x.equals(StringUtil.format6Decimals(getorderlist.get(i).getArea_y())) && area_y.equals(StringUtil.format6Decimals(getorderlist.get(i).getArea_x()))) {
//                    order_index = i;
            dialog_getOrderList = new Dialog_getOrderList(MainActivity.this, 1, Gravity.BOTTOM);
            dialog_getOrderList.setFullScreenWidth();
            dialog_getOrderList.setCancelable(true);
//                    if (!RxDataTool.isEmpty(0)) {
            final GetOrderListBean.DataBeanX.DataBean dataBean = getorderlist.get(0);
//                       故障类型
            dialog_getOrderList.getTv_fault_type().setText(dataBean.getService());
            if (!RxDataTool.isEmpty(dataBean.getQuestion_desc())) {
                //                      故障描述
                dialog_getOrderList.getTv_question_desc().setText(dataBean.getQuestion_desc());
            } else {
                //                      故障描述
                dialog_getOrderList.getTv_question_desc().setText("暂无描述");
            }


//                      车辆品牌
            dialog_getOrderList.getTv_car_sign().setText(dataBean.getCar_sign());

//                      联系电话

            if (!RxDataTool.isEmpty(dataBean.getTel_phone())) {
                dialog_getOrderList.getTv_tel_phone().setText(StringUtil.getPhone_Asterisk(dataBean.getTel_phone()));

            }


//                      期望时间
            dialog_getOrderList.getTv_expected_time().setText(dataBean.getExpected_time());

//                      地址
            dialog_getOrderList.getTv_service_address().setText(dataBean.getAddress());

            dialog_getOrderList.getBt_grab_order().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rushOrders(dataBean.getArea_x(), dataBean.getArea_y(), dataBean.getOrder_sn());
                }
            });

            dialog_getOrderList.show();
//                    }
//                    break;
//                } else {
//                    continue;
//                }
//            }


        }


    }

    //    用户选择允许或拒绝后，会回调onRequestPermissionsResult方法:
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }

    //     选择导航地图
    private void goMap(final String orderAreay, final String orderAreax, final String orderAdress) {

        ViseLog.d("选择导航地图" + area_x + "+++++" + orderAreax + "---" + orderAreay + "---" + orderAdress);

        dialog_mapServiceDetails = new Dialog_MapServiceDetails(MainActivity.this, 1, Gravity.BOTTOM);
        dialog_mapServiceDetails.setFullScreenWidth();
        dialog_mapServiceDetails.getBaidu_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RoutePlanUtil.isInstalledBaidu()) {

                    RoutePlanUtil.newInstance().startRoutePlan(RoutePlanUtil.FLAG_BAIDU, area_y, area_x, "我的位置", orderAreax, orderAreay, orderAdress);

                    dialog_mapServiceDetails.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "未安装百度地图", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "未安装高德地图", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "未安装腾讯地图", Toast.LENGTH_SHORT).show();
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

    // 地图刷新地位
    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (TencentLocation.ERROR_OK == error) {
//            RxToast.normal("定位成功");
            latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());

            if (isSetUserInfo == false) {
                area_x = String.valueOf(location.getLongitude());
                area_y = String.valueOf(location.getLatitude());
            }
            ViseLog.d("area_x" + location.getLatitude() + "----------area_y" + location.getLongitude());


            tencentMap.clearAllOverlays();
//            当前切换显示是消费者时
            if (is_check_role.equals("master")) {


                if (!RxDataTool.isEmpty(mapCenter) && !RxDataTool.isEmpty(mapCenter.getLatitude()) && !RxDataTool.isEmpty(mapCenter.getLongitude())) {
                    getOrderList(mapCenter);
                } else {
                    getOrderList(latLngLocation);
                }


//            当前切换显示是门店时
            } else {
                getMasterStoreList();
            }
            if (is_master_claim) {
                getUserMasterStoreStatus();

            }
            if (mLocationMarker == null) {
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


    //     抢单
    private void rushOrders(String area_x, String area_y, String order_sn) {
        CommonDialog.showProgressDialog(MainActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("area_x", area_x);
            jsonObject.put("area_y", area_y);
            jsonObject.put("order_sn", order_sn);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(MainActivity.this, NetUtils.POST, UrlConstant.RUSHORDERS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("抢单" + response.toString());
                    if ("2000".equals(response.getString("code"))) {
                        JSONObject data = response.getJSONObject("data");
                        String time = data.getString("time");
                        if (!RxDataTool.isEmpty(time)) {
                            runtime = RxDataTool.stringToInt(time + "000") + 2000;
                        } else {
                            runtime = 5000;
                        }
                        if (dialog_getOrderList != null) {
                            dialog_getOrderList.cancel();
                        }

                        ViseLog.d("延时时间" + time + "延时时间2:------" + runtime);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                /**
                                 *要执行的操作
                                 */
                                CommonDialog.closeProgressDialog();
                                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                                finish();
                            }
                        }, runtime);//5秒后执行Runnable中的run方法


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MainActivity.this);

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
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    private void getOrderList(final LatLng latLngLocation) {
        str_is_master = SharedPreferenceUtil.getString(MainActivity.this, Constant.IS_MASTER, "");

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
            jsonObject.put("area_x", String.valueOf(latLngLocation.getLongitude()));
            jsonObject.put("area_y", String.valueOf(latLngLocation.getLatitude()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(MainActivity.this, NetUtils.POST, UrlConstant.GETORDERLIST, jsonObject, new JsonHttpResponseHandler() {


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
                        mLocationMarker =
                                tencentMap.addMarker(new MarkerOptions().
                                        position(latLngLocation).
                                        icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_location)));
//                          不是师傅不查询  地图订单状态
//                        if (str_is_master.equals("0")) {
//                        } else {
//                            getOrderState("");
//                        }

                        if (data.getData() != null && data.getData().size() > 0) {
                            getorderlist.addAll(data.getData());
                            if (str_is_master.equals("0")) {
                            } else {
                                getOrderState("您有新的订单可抢");
                            }
//                           语音提醒
                            if (is_Vocice) {
                                getVoice("您有新的订单可抢");
//                                is_Vocice = false;
                            }
                            for (int i = 0; i < getorderlist.size(); i++) {
//                                ViseLog.d("覆盖地图"+i);
                                final int finalI = i;
                                view_marker = LayoutInflater.from(MainActivity.this).inflate(R.layout.map_marker, null);
                                iv_marker = view_marker.findViewById(R.id.iv_marker);
                                Glide.with(MainActivity.this).load(getorderlist.get(i).getImg_url()).
                                        placeholder(R.drawable.changjianwenti)

                                        .error(R.drawable.changjianwenti)
                                        .into(new SimpleTarget<GlideDrawable>() {
                                            @Override
                                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                                //展示图片
//                                                116.29976969401042","area_y":"40.04985812717014
                                                iv_marker.setImageDrawable(resource);
//                                                ViseLog.d("展示图片2");
                                                Bitmap bitmap = StringUtil.getViewBitmap(view_marker);
                                                marker = tencentMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(RxDataTool.stringToDouble(getorderlist.get(finalI).getArea_y()),
                                                                RxDataTool.stringToDouble(getorderlist.get(finalI).getArea_x())))
                                                        .anchor(0.5f, 0.5f)
                                                        .title(getorderlist.get(finalI).getUname())
                                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                                        .draggable(false));//是否可以拖拽
//                                                marker.showInfoWindow();// 设置默认显示一个infoWindow

                                            }
                                        });

                            }
                        } else {
                            if (str_is_master.equals("0")) {
                            } else {
                                getOrderState("");
                            }
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MainActivity.this);

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
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //      门店位置
    private void getMasterStoreList() {
        str_is_master = SharedPreferenceUtil.getString(MainActivity.this, Constant.IS_MASTER, "");


        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("getType", "store");
//            jsonObject.put("page", String.valueOf(mPage));
//            jsonObject.put("pagesize", "10");
            jsonObject.put("area_x", area_x);
            jsonObject.put("area_y", area_y);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(MainActivity.this, NetUtils.POST, UrlConstant.GETMASTERSTORELIST, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("附近师傅/门店位置" + response.toString());
                    isSetUserInfo = true;

                    if ("2000".equals(response.getString("code"))) {
                        GetMasterStoreBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetMasterStoreBean.class);
                        List<GetMasterStoreBean.DataBean> data = getOrderListBean.getData();


                        getorderlist.clear();
                        getorderlist1.clear();


                        if (data != null && data.size() > 0) {
                            getorderlist1.addAll(data);

                            for (int i = 0; i < getorderlist1.size(); i++) {
                                final int finalI = i;
                                view_marker1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.map_marker, null);
                                iv_marker1 = view_marker1.findViewById(R.id.iv_marker);
                                Glide.with(MainActivity.this).load(getorderlist1.get(i).getImg_url()).
                                        placeholder(R.drawable.changjianwenti)

                                        .error(R.drawable.changjianwenti)
                                        .into(new SimpleTarget<GlideDrawable>() {
                                            @Override
                                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                                //展示图片
//                                                116.29976969401042","area_y":"40.04985812717014
                                                iv_marker1.setImageDrawable(resource);

                                                Bitmap bitmap = StringUtil.getViewBitmap(view_marker1);
                                                marker1 = tencentMap.addMarker(new MarkerOptions()
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
                        LoginExpirelUtils.getLoginExpirel(MainActivity.this);

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
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    private void getOrderState(final String states) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("getType", "1");
        } catch (JSONException e) {

        }

        NetUtils.newInstance().putReturnJsonNews(MainActivity.this, NetUtils.POST, UrlConstant.GETORDERSTATE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("地图页获取订单状态：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        JSONObject data = response.getJSONObject("data");
                        if (!RxDataTool.isEmpty(data.getString("desc"))) {
                            String desc = data.getString("desc");

//                            is_Order_Receiving = true;
                            mUpview1.removeAllViews();
                            List<View> views = new ArrayList<>();
                            setUPMarqueeView(views, 11, desc);
                            mUpview1.setViews(views);

                        } else {
                            if (!RxDataTool.isEmpty(states)) {
                                mUpview1.removeAllViews();
//                                showgetOrderStateList();
                                List<View> views = new ArrayList<>();
                                setUPMarqueeView(views, 11, states);
                                mUpview1.setViews(views);

                            } else {
//                                is_Order_Receiving = true;
                                mUpview1.removeAllViews();

                            }
//
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MainActivity.this);

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
                ViseLog.d("地图页获取订单状态" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("地图页获取订单状态" + throwable.toString());
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    //    认领门店/师傅状状态
    private void getUserMasterStoreStatus() {
        JSONObject jsonObject = new JSONObject();
        try {
            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
            jsonObject.put("master_uid", uid);
            jsonObject.put("UserType", "1");

        } catch (JSONException e) {

        }

        NetUtils.newInstance().putReturnJsonNews(MainActivity.this, NetUtils.POST, UrlConstant.GETUSERMASTERSTORESTATUS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("认领门店/师傅状状态：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {


                        GetUserMasterStoreStatusBean getUserMasterStoreStatusBean = JsonUtil.getSingleBean(response.toString(), GetUserMasterStoreStatusBean.class);

                        if (getUserMasterStoreStatusBean.getData() != null && getUserMasterStoreStatusBean.getData().size() > 0) {
                            is_master_claim = false;
                            Dialog_Master(getUserMasterStoreStatusBean.getData());

                        } else {
                            is_master_claim = true;
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MainActivity.this);

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
                ViseLog.d("地图页获取订单状态" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("地图页获取订单状态" + throwable.toString());
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d("地图页获取订单状态" + throwable.toString());
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
//                        if (singleBean!=null&&!RxDataTool.isEmpty(singleBean.getResult().getAddress())){
//                            dialog_masterClaimStore.getTv_store_address()
//                                    .setText(singleBean.getResult().getAddress());
//                        }

                        if (singleBean.getStatus() == 0) {
                            if (singleBean != null && !RxDataTool.isEmpty(singleBean.getResult().getAddress())) {
                                dialog_masterClaimStore.getTv_store_address().setText(singleBean.getResult().getAddress());

                            }
                        }


                    }
                });
    }

    //    认领门店/师傅弹窗
    private void Dialog_Master(final List<GetUserMasterStoreStatusBean.DataBean> data) {
        dialog_masterClaimStore = new Dialog_MasterClaimStore(MainActivity.this, 1, Gravity.BOTTOM);
        dialog_masterClaimStore.setFullScreenWidth();
        Glide.with(MainActivity.this)
                .load(data.get(0).getImg_url()).asBitmap()
                .error(R.drawable.requestout)
                .placeholder(R.drawable.requestout)
                .into(dialog_masterClaimStore.getIv_imagehead());
        dialog_masterClaimStore.getTv_store_name().setText(data.get(0).getGname());

        if (!RxDataTool.isEmpty(data.get(0).getArea_x())) {
            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append(data.get(0).getArea_x())
                    .append(",")
                    .append(data.get(0).getArea_y());
            getUserInfo(stringBuffer.toString());
        }
        dialog_masterClaimStore.getBt_invite_setlled().setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                is_setUserMasterStore = true;
                setUserMasterStore(data.get(0).getStore_uid(), data.get(0).getMaster_uid(), "1");
                dialog_masterClaimStore.dismiss();
                is_master_claim = true;
            }
        });

        dialog_masterClaimStore.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (is_setUserMasterStore == false) {
                    setUserMasterStore(data.get(0).getStore_uid(), data.get(0).getMaster_uid(), "2");
                    is_master_claim = true;
                }
            }
        });

        dialog_masterClaimStore.show();


    }

    //    认领门店/师傅
    private void setUserMasterStore(String store_uid, String master_uid, String status) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("master_uid", master_uid);
            jsonObject.put("store_uid", store_uid);
            jsonObject.put("UserType", "1");
            jsonObject.put("status", status);

        } catch (JSONException e) {

        }

        NetUtils.newInstance().putReturnJsonNews(MainActivity.this, NetUtils.POST, UrlConstant.SETUSERMASTERSTORE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("认领门店/师傅：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

//                        RxToast.normal("入驻成功");
//                        GetUserMasterStoreStatusBean getUserMasterStoreStatusBean = JsonUtil.getSingleBean(response.toString(), GetUserMasterStoreStatusBean.class);
//
//                        if (getUserMasterStoreStatusBean.getData() != null) {
////                            Dialog_Master(getUserMasterStoreStatusBean.getData());
//
//                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MainActivity.this);

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
                ViseLog.d("认领门店/师傅" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("地图页获取订单状态" + throwable.toString());
                CommonDialog.showInfoDialog(MainActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d("地图页获取订单状态" + throwable.toString());
            }
        });


    }

    private void getVoice(String desc) {
        FlowerCollector.onEvent(MainActivity.this, "tts_play");


        // 设置参数
        setParam();
        int code = mTts.startSpeaking(desc, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
			/*String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
			int code = mTts.synthesizeToUri(text, path, mTtsListener);*/

        if (code != ErrorCode.SUCCESS) {
//            Toast.makeText(MainActivity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
        }
    }

    //     滚动控件
    private void setUPMarqueeView(List<View> views, int size, final String str) {
        for (int i = 0; i < size; i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);

            /**
             * 设置监听
             */


            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!RxDataTool.isEmpty(str) && str.equals("您有新的订单可抢")) {
                        showgetOrderStateList();
                    } else {
                        startActivity(new Intent(MainActivity.this, OrderActivity.class));
                        finish();
                    }
                }
            });
            //进行对控件赋值
            tv1.setText(str);
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
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
        iv_back.setOnClickListener(this);
        iv_mine = (ImageView) findViewById(R.id.iv_mine);
        iv_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                break;
            case R.id.iv_mine:

                startActivity(new Intent(MainActivity.this, MineActivity.class));
                finish();

                break;
        }
    }


    //      跳往系统设置权限界面
    private void showdialog(final String string) {
        final DialogSure rxDialogSure = new DialogSure(MainActivity.this);//提示弹窗
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


    private void kdxf() {
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(MainActivity.this, mTtsInitListener);
        mEngineType = SpeechConstant.TYPE_CLOUD;


    }

    /**
     * 科大讯飞语音  参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //支持实时音频返回，仅在synthesizeToUri条件下支持
            //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");

        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                Toast.makeText(MainActivity.this, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            Toast.makeText(MainActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakPaused() {
//            Toast.makeText(MainActivity.this, "暂停播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakResumed() {
//            Toast.makeText(MainActivity.this, "继续播放", Toast.LENGTH_SHORT).show();

//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
//            Toast.makeText(MainActivity.this, "继续播放", Toast.LENGTH_SHORT).show();
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;

//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));

//            SpannableStringBuilder style=new SpannableStringBuilder(texts);
//            Log.e(TAG,"beginPos = "+beginPos +"  endPos = "+endPos);
//            style.setSpan(new BackgroundColorSpan(Color.RED),beginPos,endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((EditText) findViewById(R.id.tts_text)).setText(style);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
//                showTip("播放完成");
            } else if (error != null) {
                Toast.makeText(MainActivity.this, error.getPlainDescription(true), Toast.LENGTH_SHORT).show();
//                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}

            //当设置SpeechConstant.TTS_DATA_NOTIFY为1时，抛出buf数据
			/*if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
						byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
						Log.e("MscSpeechLog", "buf is =" + buf);
					}*/

        }
    };
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
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);

        }
        ViseLog.d("FC+onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);

        }
        ViseLog.d("FC+onPause");
        initMap();
//        is_Vocice = true;

        super.onPause();
    }

}
