package com.nengyuanbox.repaircar.activity.registered;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.MapNameAdapter;
import com.nengyuanbox.repaircar.bean.GetCoderBean;
import com.nengyuanbox.repaircar.bean.MapNameBean;
import com.nengyuanbox.repaircar.eventbus.FinishEventBean;
import com.nengyuanbox.repaircar.utils.DividerItemDecorations;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentPoi;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class StoreMapActivity extends MapActivity implements TencentLocationListener, View.OnClickListener {

    private int GPS_REQUEST_CODE = 1;
    MapView mapview;
    private TencentMap tencentMap;
    private Marker marker;
    private View view_marker;
    private ImageView iv_marker;
    private TencentLocationManager mLocationManager;
    private Marker mLocationMarker;
    private ImageButton iv_back;
    private TextView tv_title_name;
    private ImageView iv_mine;
    private boolean isSetUserInfo;
    private String area_x;
    private String area_y;
    private int mPage = 1;
    private RecyclerView rv_map_list;
    private ArrayList<MapNameBean> mapNameList = new ArrayList<>();
    private MapNameAdapter mapNameAdapter;
    private LinearLayout et_hunk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);
        initView();
        mapview = (MapView) findViewById(R.id.mapview);
        mapview.onCreate(savedInstanceState);
        quxian();
        creatMap();
//        地图地址列表适配器
        mapNameAdapter = new MapNameAdapter(R.layout.item_mapname, mapNameList);
        rv_map_list.addItemDecoration(new DividerItemDecorations());
        rv_map_list.setLayoutManager(new LinearLayoutManager(this));
        rv_map_list.setAdapter(mapNameAdapter);
        mapNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = getIntent();
                intent.putExtra("address", mapNameList.get(position).getAddr());
                intent.putExtra("area_x", mapNameList.get(position).getLatitude());
                intent.putExtra("area_y", mapNameList.get(position).getLongitude());
                setResult(200, intent);//返回页面1
                finish();
            }
        });

    }

    private void initView() {
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("选择位置");
        iv_back.setOnClickListener(this);
        rv_map_list = (RecyclerView) findViewById(R.id.rv_map_list);
        rv_map_list.setOnClickListener(this);
        et_hunk = (LinearLayout) findViewById(R.id.et_hunk);
        et_hunk.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_hunk:

                Intent intent = new Intent(StoreMapActivity.this, StoreMapSearchActivity.class);
                intent.putExtra("area_x", area_x);
                intent.putExtra("area_y", area_y);
                startActivityForResult(intent, 200);
                finish();

                break;

        }
    }

    private void creatMap() {
//          获取TencentMap实例
        tencentMap = mapview.getMap();
//          设置卫星底图
        tencentMap.setSatelliteEnabled(false);
//          设置实时路况开启
//      tencentMap.setTrafficEnabled(true);
//          设置缩放级别
        tencentMap.setZoom(20);



//       获取UiSettings实例
        UiSettings uiSettings = mapview.getUiSettings();
//      设置logo到屏幕底部中心
        uiSettings.setLogoPosition(UiSettings.LOGO_POSITION_CENTER_BOTTOM);
//      设置比例尺到屏幕右下角
        uiSettings.setScaleViewPosition(UiSettings.SCALEVIEW_POSITION_RIGHT_BOTTOM);
//      启用缩放手势(更多的手势控制请参考开发手册)
        uiSettings.setZoomGesturesEnabled(true);


    }

    private void initMap() {

        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(20000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        mLocationManager.requestLocationUpdates(request, this);

        //Marker点击事件
        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker mMarker) {
                // TODO Auto-generated method stub
                Toast.makeText(StoreMapActivity.this, mMarker.getPosition() + "", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
//      infoWindow点击事件
        tencentMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub

            }
        });
//      Marker拖拽事件
        tencentMap.setOnMarkerDraggedListener(new TencentMap.OnMarkerDraggedListener() {

            //拖拽开始时调用
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub

            }

            //拖拽结束后调用
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                area_x = String.valueOf(arg0.getPosition().getLatitude());
                area_y = String.valueOf(arg0.getPosition().getLongitude());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(arg0.getPosition().getLatitude())
                        .append(",")
                        .append(arg0.getPosition().getLongitude());
                getUserInfo(stringBuffer.toString());

            }

            //拖拽时调用
            @Override
            public void onMarkerDrag(Marker arg0) {

                // TODO Auto-generated method stub

            }
        });


    }

    //    检查是否开启定位服务
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
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (TencentLocation.ERROR_OK == error) {
//            RxToast.normal("定位成功");
            LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());

            if (isSetUserInfo == false) {
                area_x = String.valueOf(location.getLatitude());
                area_y = String.valueOf(location.getLongitude());
                //设置地图中心点
                tencentMap.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
                if (!RxDataTool.isNullString(area_x)) {
                    marker = tencentMap.addMarker(new MarkerOptions()
                            .position(new LatLng(RxDataTool.stringToDouble(area_x), RxDataTool.stringToDouble(area_y)))
//                                    .title("测试"+ finalI)
                            .anchor(1.5f, 1.5f)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_biaodian))
                            .draggable(true));
                    marker.showInfoWindow();// 设置默认显示一个infoWindow
                    isSetUserInfo = true;
                    List<TencentPoi> poiList = location.getPoiList();
                    ViseLog.d("地址：---" + poiList.toString());
                    for (int i = 0; i < poiList.size(); i++) {
                        mapNameList.add(new MapNameBean(
                                poiList.get(i).getName(),
                                poiList.get(i).getAddress(),
                                String.valueOf(poiList.get(i).getLatitude()),
                                String.valueOf(poiList.get(i).getLongitude())
                        ));
                    }
                    mapNameAdapter.notifyDataSetChanged();

                }
            }


            if (mLocationMarker == null) {
                mLocationMarker =
                        tencentMap.addMarker(new MarkerOptions().
                                position(latLngLocation).
                                icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_location)));
            } else {
                mLocationMarker.setPosition(latLngLocation);
            }
            // 定位成功
        } else {
//            RxToast.normal("定位失败");
            // 定位失败
        }
    }

    private void getUserInfo(String location) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID

        OkHttpUtils.get()
                .url("https://apis.map.qq.com/ws/geocoder/v1")
                .addParams("location", location)
                .addParams("get_poi", "1")//是否返回周边POI列表：1.返回；0不返回(默认)
                .addParams("key", "D5NBZ-RMFKI-JH2GC-5BD7V-QCBGE-ABB6Y")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ViseLog.d("获取错误..");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ViseLog.d("腾讯地图返回:" + response);
                        GetCoderBean singleBean = JsonUtil.getSingleBean(response, GetCoderBean.class);
                        List<GetCoderBean.ResultBean.PoisBean> pois = singleBean.getResult().getPois();

                        mapNameList.clear();
                        for (int i = 0; i < pois.size(); i++) {
                            mapNameList.add(new MapNameBean(pois.get(i).getTitle(),
                                    pois.get(i).getAddress(),
                                    String.valueOf(pois.get(i).getLocation().getLat()),
                                    String.valueOf(pois.get(i).getLocation().getLat())));
                        }
                        mapNameAdapter.notifyDataSetChanged();


                    }
                });
    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {
        if (name.equals("wifi") && status == 5) {
            RxToast.normal("请打开位置开关");
        }

    }


    private void quxian() {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            }
        }
    }


    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        EventBus.getDefault().unregister(this);
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


    //    用户选择允许或拒绝后，会回调onRequestPermissionsResult方法:
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }


    @Subscribe
    public void getFinishBean(FinishEventBean eventBean) {
        if (!RxDataTool.isNullString(eventBean.getName())) {
            finish();
        }

    }

}
