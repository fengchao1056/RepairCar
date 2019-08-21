package com.nengyuanbox.repaircar.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.vise.log.ViseLog;
import com.vise.log.inner.DefaultTree;
import com.vondear.rxtool.RxTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class CommApplication extends MultiDexApplication {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        MultiDex.install(this);
        // 初始化Log
        initLogs();

        mContext = this;
        initTest();
        // 初始化Bugly
        initCrashReport();

//       科大讯飞   语音识别
        SpeechUtility.createUtility(this, "appid=5cbd320c" );


    }



    private void initLogs() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("Feng")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new DefaultTree());//添加打印日志信息到Logcat的树
    }



    public static Context getContext() {
        return mContext;
    }

    // 初始化Bugly
    private void initCrashReport() {

        Context context = getApplicationContext();
//       获取当前包名
        String packageName = context.getPackageName();
//       获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
//       设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//       初始化Bugly
//        CrashReport.initCrashReport(getContext(), "67e43827d4", true);
        CrashReport.initCrashReport(context, "5186b42d69", true, strategy);
    }

    private void initTest() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }





}


