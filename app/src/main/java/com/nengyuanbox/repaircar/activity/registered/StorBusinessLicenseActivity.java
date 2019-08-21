package com.nengyuanbox.repaircar.activity.registered;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.GlideImageLoader1;
import com.nengyuanbox.repaircar.utils.LogUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.reactivex.functions.Consumer;

// 门店上传营业执照
public class StorBusinessLicenseActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_license)
    TextView tv_license;
    @BindView(R.id.iv_license)
    ImageView iv_license;
    @BindView(R.id.rb_no_license)
    CheckBox rb_no_license;
    @BindView(R.id.bt_next_step)
    Button bt_next_step;
    private List<String> path = new ArrayList<>();

    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private String TAG = "---Yancy---";
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private boolean ischecked = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stor_business_license;
    }

    @Override
    protected void initView() {
        initGallery();
        getcamea();
    }

    @Override
    protected void setListener() {
        tv_license.setText(getIntent().getStringExtra("gname"));
        iv_back.setImageResource(R.drawable.icon_button);
        tv_title_name.setText("门店认证");

        rb_no_license.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked = isChecked;
                if (isChecked == false) {
                    if (path != null && path.size() > 0) {
                        bt_next_step.setEnabled(true);
                    } else {
                        bt_next_step.setEnabled(false);
                    }
                } else {
                    bt_next_step.setEnabled(true);
                }

            }
        });
    }

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(StorBusinessLicenseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(StorBusinessLicenseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "拒绝过了");
                Toast.makeText(StorBusinessLicenseActivity.this, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(StorBusinessLicenseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Log.i(TAG, "不需要授权 ");
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(StorBusinessLicenseActivity.this);
        }
    }

    private void getcamea() {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader1())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.nengyuanbox.repaircar.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(1)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)
                // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(StorBusinessLicenseActivity.this);
        rxPermission.requestEach(
                Manifest.permission.CAMERA

        )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtil.d("Feng", permission.name + " is granted.");
                            galleryConfig.getBuilder().isOpenCamera(false).build();
                            initPermissions();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Toast.makeText(context, "您已拒绝打开相机权限1", Toast.LENGTH_SHORT).show();

//                            RxToast.normal("您已拒绝打开相机权限");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtil.d("Feng", permission.name + " is denied. More info should be provided.");
                        } else {
                            Intent intent = new Intent();

                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                            Uri uri = Uri.fromParts("package", getPackageName(), null);

                            intent.setData(uri);

                            startActivityForResult(intent, 10);
                            Toast.makeText(context, "您已拒绝打开相机权限,请去设置中打开此权限", Toast.LENGTH_SHORT).show();
//                            RxToast.normal("您已拒绝打开相机权限");
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtil.d("Feng", permission.name + " is denied.");
                            return;
                        }
                    }
                });
    }


    //    门店入驻
    private void storeResidence() {
        CommonDialog.showProgressDialog(this);
        HashMap<String, Object> jsonObject = new HashMap<String, Object>();
        RequestParams requestParams = new RequestParams();
        try {
            jsonObject.put("setType", "2");
            requestParams.put("setType", "2");


//            营业执照
            if (ischecked == false) {
                if (path.size() > 0) {
                    requestParams.put("store_img", new File(path.get(0)));

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        netUtils.postReturnJsons_file(context, UrlConstant.STORERESIDENCEIMG, requestParams, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("门店入驻di一步：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        Intent intent = new Intent(StorBusinessLicenseActivity.this, StoreImgActivity.class);
                        intent.putExtra("order_sn", getIntent().getStringExtra("order_sn"));
                        intent.putExtra("order_money", getIntent().getStringExtra("order_money"));
                        intent.putExtra("order_money_old", getIntent().getStringExtra("order_money_old"));
                        intent.putExtra("gname", getIntent().getStringExtra("gname"));
                        startActivity(intent);
                        finish();

                        RxToast.normal(response.getString("message"));
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StorBusinessLicenseActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("门店入驻di一步报错：" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString() + responseString);
                CommonDialog.showInfoDialog(StorBusinessLicenseActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StorBusinessLicenseActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StorBusinessLicenseActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "同意授权");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(StorBusinessLicenseActivity.this);
            } else {
                Log.i(TAG, "拒绝授权");
            }
        }
    }


    private void initGallery() {
        //                photoAdapter.notifyDataSetChanged();
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path.clear();
                getcamea();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path.add(s);

                }
                Glide.with(context).load(path.get(0))
                        .into(iv_license);
                bt_next_step.setEnabled(true);
//                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

    }

    @OnClick({R.id.iv_back, R.id.iv_license, R.id.bt_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_license:
                requestPermissions();

                break;
            case R.id.bt_next_step:
//            营业执照
                if (ischecked == false) {
                    if (path.size() > 0) {
                        storeResidence();
                    }else {
                        RxToast.normal("请上传营业执照");
                        return;
                    }
                }else {
                    Intent intent = new Intent(StorBusinessLicenseActivity.this, StoreImgActivity.class);
                    intent.putExtra("order_sn", getIntent().getStringExtra("order_sn"));
                    intent.putExtra("order_money", getIntent().getStringExtra("order_money"));
                    startActivity(intent);
                    finish();
                }



                break;
        }
    }
}
