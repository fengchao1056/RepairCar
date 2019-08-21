package com.nengyuanbox.repaircar.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.AlertDialog;
import com.nengyuanbox.repaircar.utils.CommonUtil;
import com.nengyuanbox.repaircar.utils.LogUtil;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

import static com.iflytek.speech.Version.getVersionName;


public class SplashActivity extends AppCompatActivity {

    protected static final String tag = "SplashActivity";

    /**
     * 需要更新apk,状态码
     */
    protected static final int UPDATE_VERSION = 100;
    protected static final int Force_UPDATE_VERSION = 105;

    /**
     * 进入应用程序主界面状态码
     */
    protected static final int ENTER_HOME = 101;
    protected static final int URL_ERROR = 102;
    protected static final int IO_ERROR = 103;
    protected static final int JSON_ERROR = 104;
    private String versionName;
    private String state;
    private String configVersionurl = "";
    private Handler mHandler = new Handler() {//membar	成员变量
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    //提示用户更新APK
                    showUpdateDialog();
                    break;
                case Force_UPDATE_VERSION:
                    //强制更新APK
                    showForceUpdateDialog();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case URL_ERROR:
                    isForceUpdate();
                    break;
                case IO_ERROR:
                    isForceUpdate();
                    break;
                case JSON_ERROR:
                    isForceUpdate();
                    break;
                default:
                    isForceUpdate();
                    break;
            }
        }

        ;
    };
    private String downloadUrl;//服务器下载地址
    private boolean isUrlExists;
    private SplashActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot() && getIntent() != null) {
            String action = getIntent().getAction();
            if (getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        setContentView(R.layout.activity_splash);
//        跳转到主activity
        context = this;
//        skipActivity();
        checkVersions();
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }



//        if (!isTaskRoot()
//                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
//                && getIntent().getAction() != null
//                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
//
//            finish();
//            return;
//        }

    }


    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");

        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小

        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }


    //判断是否为强制
    private void isForceUpdate() {
        if ("0".equals(state)) {
            enterHome();
            return;
        }
        if ("1".equals(state)) {
            CommonUtil.StartToast(context, "连接失败，请检查网络");
        }

    }

    //非强制更新
    private void showUpdateDialog() {
        String verName = getVersionName();
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本:V");
        sb.append(verName);
        sb.append(",发现新版本:V");
        sb.append(versionName);
        sb.append(", 是否更新?");
        if (context != null) {
            new AlertDialog(context).builder()
                    .setTitle("版本更新")
                    .setMsg(sb.toString())
                    .setPositiveButton("更新",// 设置确定按钮
                            new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    enterHome();
                                    downFile(configVersionurl);

                                }


                            })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        SharedPreferenceUtil.saveBoolean(context, Constant.IS_UPDATA, false);
                            enterHome();
                        }
                    }).show();
        } else {
            new AlertDialog(CommApplication.getContext()).builder()
                    .setTitle("版本更新")
                    .setMsg(sb.toString())
                    .setPositiveButton("更新",// 设置确定按钮
                            new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    enterHome();
                                    downFile(configVersionurl);

                                }


                            })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        SharedPreferenceUtil.saveBoolean(context, Constant.IS_UPDATA, false);
                            enterHome();
                        }
                    }).show();
        }


    }

    //强制更新
    private void showForceUpdateDialog() {
        String verName = getVersionName();
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本：V");
        sb.append(verName + "已停用，请更新版本：V");
        sb.append(versionName);
        new AlertDialog(context).builder()
                .setTitle("版本更新")
                .setMsg(sb.toString())
                .setPositiveButton("更新",// 设置确定按钮
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //服务器下载地址
//                              http://www.myzyb.com/app_v versionName(版本名称)/ appNYB_v3.apk

                                downFile(configVersionurl);
                                LogUtil.e("downloadUrl", configVersionurl);

                            }


                        }).show();
    }

    /**
     * 下载服务端apk文件
     *
     * @param url
     */

    void downFile(final String url) {

        final Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");

        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
////                isUrlExists = checkIfUrlExists(configVersionurl);
////                LogUtil.d("Feng", "请求成功：" + isUrlExists);
//
//
////                if (isUrlExists == true) {
////                    //符合标准
////                    Uri content_url = Uri.parse(configVersionurl);
////                    intent.setData(content_url);
////                    startActivity(intent);
////                } else {
////                    //不符合标准
////                    String downUrl = "http://www.myzyb.com/app_nyb/wangdian.apk";
////                    Uri content_url = Uri.parse(downUrl);
////                    intent.setData(content_url);
////                    startActivity(intent);
////                }
//            }
//        }.start();


        //开启服务
//        startService(new Intent(context, WdDownloadService.class).putExtra("url", url));
    }


    /**
     * 进入应用程序主界面
     */
    protected void enterHome() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
//        Intent intent;
//        intent = new Intent(this,HomeActivity.class);
//        startActivity(intent);
//        finish();
    }


    public static boolean checkIfUrlExists(final String URLName) {


        boolean URL_exists = true;
        URL_exists = true;
        try {
//设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
//到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(URLName)
                    .openConnection();
            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
//从 HTTP 响应消息获取状态码
            LogUtil.d("Feng", "head " + con.getResponseCode());
//            if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK||con.getResponseCode() == 302)
                URL_exists = true;
            else
                URL_exists = false;
        } catch (Exception e) {
            LogUtil.d("Feng", "报错：" + e.toString());
            e.printStackTrace();
        }
        return URL_exists;
    }
    /**
     * 通过版本号,检测版本更新
     */

    private void checkVersions() {
        HashMap<String, Object> dictParam = new HashMap<>();
        RequestParams params = new RequestParams();
        dictParam.put("name", "lxc");
        params.put("name", "lxc");
        NetUtils.newInstance().getReturnJson_splash(context, UrlConstant.SYSVERSION, params, dictParam, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    LogUtil.d("Feng", "获取版本号" + response.toString());
                    if (response.getString("code").equals("2000")) {
                        if (response.has("data")) {
                            JSONArray jsonList = response.getJSONArray("data");
                            JSONObject jsonObject = jsonList.getJSONObject(0);
                            state = jsonObject.getString("state");
                            versionName = jsonObject.getString("version");
                            configVersionurl = jsonObject.getString("api_url");
                            String localVersionName = getVersionName();
                            SharedPreferenceUtil.saveString(CommApplication.getContext(), Constant.versionName, localVersionName);
                            // versioncode = jsonObject1.getString("version_admin");
                            // LogUtil.e("versionName",versionName);

                            if (localVersionName != null && localVersionName.equals(versionName)) {
                                //进入应用的主界面
                                mHandler.sendEmptyMessage(ENTER_HOME);
                                return;
                            } else {
                                //提示用户更新
                                if ("0".equals(state)) {
//                        0代表相等，1代表version1大于version2，-1代表version1小于version2
                                    if (compareVersion(localVersionName, versionName) == 0) {

                                        mHandler.sendEmptyMessage(ENTER_HOME);
                                    } else if (compareVersion(localVersionName, versionName) == -1) {
                                        //非强制更新
                                        mHandler.sendEmptyMessage(UPDATE_VERSION);
                                    } else {

                                        mHandler.sendEmptyMessage(ENTER_HOME);
                                    }

                                    return;
                                }
                                if ("1".equals(state)) {
                                    if (compareVersion(localVersionName, versionName) == 0) {

                                        mHandler.sendEmptyMessage(ENTER_HOME);
                                    } else if (compareVersion(localVersionName, versionName) == -1) {
                                        //非强制更新
                                        mHandler.sendEmptyMessage(Force_UPDATE_VERSION);
                                    } else if (compareVersion(localVersionName, versionName) == 1) {
                                        //非强制更新
                                        mHandler.sendEmptyMessage(Force_UPDATE_VERSION);
                                    } else {
                                        mHandler.sendEmptyMessage(ENTER_HOME);
                                    }

                                    return;
                                }

                            }


//                            if (localVersionName != null && Double.parseDouble(localVersionName) < Double.parseDouble(versionName)) {
//                                //提示用户更新
//                                if ("0".equals(state)) {
//                                    //非强制更新
//                                    mHandler.sendEmptyMessage(UPDATE_VERSION);
//
//                                    return;
//                                }
//                                if ("1".equals(state)) {
//                                    //强制更新
//                                    mHandler.sendEmptyMessage(Force_UPDATE_VERSION);
//
//                                    return;
//                                }
//
//                            }

                        }

                    } else {
                        Toast.makeText(SplashActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                            skipActivity();
                    LogUtil.d("Feng", "报错" + e.toString());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                        skipActivity();
                LogUtil.d("Feng", "请求失败2" + responseString);

            }


        });

    }

    private void skipActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 200);
    }
}
