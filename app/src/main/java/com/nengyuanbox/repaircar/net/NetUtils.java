package com.nengyuanbox.repaircar.net;


import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.config.Config;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.CommonUtil;
import com.nengyuanbox.repaircar.utils.LogUtil;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


/**
 * 网络工具类
 *
 * @author zhuxc
 * @ClassName: NetUtils
 * @Description: TODO
 * @date modify by 2015-7-3 下午2:58:31
 */
public class NetUtils {
    private static NetUtils netUtils;
    public static String GET = "GET";
    public static String POST = "POST";
    public static AsyncHttpClient Client;
    private static String substring;
    private String absoluteUrl;
    private String jsonString;
    private static final int TIMEOUT_TIME = 5 * 1000; //网络超时时间
    private static String vlues;
    private static String vlues1;
    private static String vlues2;

    public static NetUtils newInstance() {
        if (null == netUtils) {
            netUtils = new NetUtils();
            Client = new AsyncHttpClient();
            Client.setTimeout(TIMEOUT_TIME);
            Client.setMaxRetriesAndTimeout(1, TIMEOUT_TIME); //设置连接失败重连次数
            Client.setTimeout(5000);


        }
        return netUtils;
    }


    //   post请求
    public void putReturnJsonNews(Context context, String method, final String relativeUrl,
                                  JSONObject dictParam, JsonHttpResponseHandler jhrhandler) {
        String token = SharedPreferenceUtil.getString(context, Constant.TOKEN, "");
        String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
//        String login_salt = SharedPreferenceUtil.getString(context, Constant.LoginSalt, "");

        if (CommonUtil.hasNetwork(context)) {
            try {
                dictParam.put("access_token", (token));
//                dictParam.put("login_salt", (login_salt));
                dictParam.put("sendType", "json");
                dictParam.put("uid", (uid));
                String sorts = getSorts();
                dictParam.put("sorts", sorts);
                dictParam.put("source", "APP");
                dictParam.put("parameter", "repair");
                dictParam.put("version", "1.1");
                final String sign = getSign(dictParam);
                StringBuffer buffer = new StringBuffer();
                buffer.append(Config.SHOPBASEURL);
                buffer.append(relativeUrl);
                String absoluteUrl = buffer.toString();

                LogUtil.d("Feng", "请求的JSON:" + dictParam.toString());

                if (GET.equals(method)) {

                } else if (POST.equals(method)) {
                    dictParam.put("sign", sign);
                    Client.post(context, absoluteUrl, jsonData(dictParam), "application/json", jhrhandler);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
//            CommonDialog.showInfoDialog(context, context.getResources().getString(R.string.net_error));
        }

    }




    //    登陆    普通传JSONObject的post请求
    public void putReturnJson_Login(Context context, String method, final String relativeUrl,
                                    JSONObject dictParam, JsonHttpResponseHandler jhrhandler) {
        if (CommonUtil.hasNetwork(context)) {
            try {
                dictParam.put("sendType", "json");
                String sorts = getSorts();
                dictParam.put("sorts", sorts);
                dictParam.put("source", "APP");
                dictParam.put("version", "1.1");
                final String sign = getSign(dictParam);
                dictParam.put("sign", sign);
                StringBuffer buffer = new StringBuffer();
                buffer.append(Config.SHOPBASEURL);
                buffer.append(relativeUrl);
                String absoluteUrl = buffer.toString();

                LogUtil.d("Feng", "普通传JSONObject的post请求:" + dictParam.toString());

                if (GET.equals(method)) {

                } else if (POST.equals(method)) {
                    Client.post(context, absoluteUrl, jsonData(dictParam), "application/json", jhrhandler);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            CommonDialog.showInfoDialog(context, context.getResources().getString(R.string.net_error));
        }

    }



    //   get请求   HashMap<String, Object> 类型
    public void getReturnJsons(Context context, final String relativeUrl, RequestParams params,
                               HashMap<String, Object> dictParam, JsonHttpResponseHandler jhrhandler) {
        if (CommonUtil.hasNetwork(context)) {
            String login_salt = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.LoginSalt, "");
            String token = SharedPreferenceUtil.getString(context, Constant.TOKEN, "");
//            String gid = SharedPreferenceUtil.getString(context, Constant.WDID, "");
            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
            String version = SharedPreferenceUtil.getString(context, Constant.versionName, "");
            dictParam.put("access_token", (token));
//            dictParam.put("gid", (gid));
            dictParam.put("sendType", "json");
            dictParam.put("login_salt", login_salt);
            dictParam.put("uid", (uid));
            dictParam.put("source", "APP");
            String sorts = getSorts();
            dictParam.put("sorts", sorts);
            dictParam.put("parameter", "repair");
            dictParam.put("version", "1.1");
            final String sign = getSigns(dictParam);

            params.put("login_salt", login_salt);
            params.add("access_token", (token));
//            params.put("gid", (gid));
            params.add("sendType", "json");
            params.put("uid", (uid));
            params.add("source", "APP");
            params.put("sorts", sorts);
            params.add("parameter", "repair");
          params.put("version", "1.1");
            params.add("sign", sign);

            StringBuffer buffer = new StringBuffer();
            buffer.append(Config.SHOPBASEURL);
            buffer.append(relativeUrl);
            String absoluteUrl = buffer.toString();

            LogUtil.d("Feng", "params请求参数：" + params.toString());
            LogUtil.d("Feng", "dictParam请求参数：" + dictParam.toString());
            Client.get(context, absoluteUrl, params, jhrhandler);
        } else {
            CommonDialog.showInfoDialog(context, context.getResources().getString(R.string.net_error));
        }

    }

    //   get请求   HashMap<String, Object> 类型
    public void postReturnJsons_file(Context context, final String relativeUrl, RequestParams params,
                               HashMap<String, Object> dictParam, JsonHttpResponseHandler jhrhandler) {
        if (CommonUtil.hasNetwork(context)) {
            String login_salt = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.LoginSalt, "");
            String token = SharedPreferenceUtil.getString(context, Constant.TOKEN, "");
            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
            dictParam.put("access_token", (token));
            dictParam.put("sendType", "json");
            dictParam.put("login_salt", login_salt);
            dictParam.put("uid", (uid));
            dictParam.put("source", "APP");
            String sorts = getSorts();
            dictParam.put("sorts", sorts);
            dictParam.put("parameter", "repair");
            dictParam.put("version", "1.1");
            final String sign = getSigns(dictParam);

            params.put("login_salt", login_salt);
            params.add("access_token", (token));
            params.add("sendType", "json");
            params.put("uid", (uid));
            params.add("source", "APP");
            params.put("sorts", sorts);
            params.add("parameter", "repair");
           params.put("version", "1.1");
            params.add("sign", sign);

            StringBuffer buffer = new StringBuffer();
            buffer.append(Config.SHOPBASEURL);
            buffer.append(relativeUrl);
            String absoluteUrl = buffer.toString();

            LogUtil.d("Feng", "params请求参数：" + params.toString());
            LogUtil.d("Feng", "dictParam请求参数：" + dictParam.toString());
            Client.post(context, absoluteUrl, params, jhrhandler);
        } else {
            CommonDialog.showInfoDialog(context, context.getResources().getString(R.string.net_error));
        }

    }




    //登陆调用的get请求
    public void getReturnJson_splash(Context context, final String relativeUrl, RequestParams params,
                                     HashMap<String, Object> dictParam, JsonHttpResponseHandler jhrhandler) {
        if (CommonUtil.hasNetwork(context)) {
            String version = SharedPreferenceUtil.getString(context, Constant.versionName, "");
            dictParam.put("sendType", "json");
            dictParam.put("source", "APP");
            String sorts = getSorts();
            dictParam.put("sorts", sorts);
            dictParam.put("parameter", "repair");
//            dictParam.put("version", "1.1");
            final String sign = getSigns(dictParam);
            params.add("sendType", "json");
            params.add("source", "APP");
            params.put("sorts", sorts);
            params.add("parameter", "repair");
//            params.put("version", "1.1");
            params.add("sign", sign);

            StringBuffer buffer = new StringBuffer();
            buffer.append(Config.SHOPBASEURL);
            buffer.append(relativeUrl);
            String absoluteUrl = buffer.toString();
            Client.get(context, absoluteUrl, params, jhrhandler);
            LogUtil.d("Feng", "请求数据：" + params.toString());
        } else {
            CommonDialog.showInfoDialog(context, context.getResources().getString(R.string.net_error));
        }

    }


    public static RequestParams POST_SIGN(RequestParams params,
                                          HashMap<String, String> dictParam) {
        String sorts = getSorts();
        dictParam.put("sorts", sorts);
        String sign = getSign(dictParam);
        params.add("sorts", sorts);
        params.add("sign", sign);
        return params;

    }

    public static RequestParams POST_SIGN_OBJECT(RequestParams params,
                                                 HashMap<String, Object> dictParam) {
        String sorts = getSorts();
        dictParam.put("sorts", sorts);
        String sign = getSigns(dictParam);
        params.add("sorts", sorts);
        params.add("sign", sign);
        return params;
    }

    public static String getSign(HashMap<String, String> dictParam) {
        StringBuilder sb = new StringBuilder();
        SortedSet<String> keys = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        keys.addAll(dictParam.keySet());
        for (String key : keys) {
            if (key.equals("sign") || key.equals("prd ") || key.equals("norms ") || key.equals("callback") || key.equals("_")) {
                continue;
            }
            if (dictParam != null && null != dictParam.get(key)) {
                if (!TextUtils.isEmpty(dictParam.get(key))) {
                    vlues = dictParam.get(key).trim();
                    vlues = replaceBlank(vlues);
                } else {
                    continue;
                }

            } else {
                return "1";
            }

            if (isArray(vlues) == true) {
                continue;
            }
            if (vlues.equals("") || vlues.equals(null) || vlues.equals("[]")) {
                continue;
            }
            if (vlues.contains("&") || vlues.contains("`") || vlues.contains("/") || vlues.contains("'") || vlues.contains("|")) {
                continue;
            }

            sb.append(key);
            sb.append("=");
            sb.append(getEncode(String.valueOf(dictParam.get(key))));
            sb.append("&");

        }
        String substring = sb.toString().substring(0, sb.toString().length() - 1);
        LogUtil.d("Chao", "getSign----------------------" + sb.toString());
        return getMd5(substring);
    }

    public static boolean isArray(Object array) {
        if (array instanceof Object[]) {
            return true;
        } else if (array instanceof boolean[]) {
            return true;
        } else if (array instanceof byte[]) {
            return true;
        } else if (array instanceof char[]) {
            return true;
        } else if (array instanceof double[]) {
            return true;
        } else if (array instanceof float[]) {
            return true;
        } else if (array instanceof int[]) {
            return true;
        } else if (array instanceof long[]) {
            return true;
        } else if (array instanceof short[]) {
            return true;
        } else if (array instanceof String[]) {
            return true;
        }
        return false;
    }


    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    public static String getSigns(HashMap<String, Object> dictParam) {
        StringBuilder sb = new StringBuilder();
        SortedSet<String> keys = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);

        keys.addAll(dictParam.keySet());
        for (String key : keys) {

            if (key.equals("sign") || key.equals("prd") || key.equals("norms") || key.equals("callback") || key.equals("_")) {
                continue;
            }
            if (dictParam != null && null != dictParam.get(key)) {
                vlues1 = dictParam.get(key).toString().trim();
                vlues1 = replaceBlank(vlues1);
            } else {
                return "1";
            }

            if (isArray(vlues1) == true) {
                continue;
            }
            if (vlues1.equals("") || vlues1.equals(null) || vlues1.equals("[]")) {
                continue;
            }
            if (vlues1.contains("&") || vlues1.contains("`") || vlues1.contains("/") || vlues1.contains("'") || vlues1.contains("|")) {
                continue;
            }
            sb.append(key);
            sb.append("=");
            sb.append(getEncode(vlues1));
            sb.append("&");

        }
        if (sb.toString().length() > 1) {

            substring = sb.toString().substring(0, sb.toString().length() - 1);
            LogUtil.d("Chao", "getSigns----------------------" + substring);
            return getMd5(substring);
        } else {
            return "";
        }


    }




    public static String getSign(JSONObject dictParam) {
        HashMap<String, String> map = jsonToHashMap(dictParam);
        LogUtil.d("Chao", "map-------------" + map.toString());
        StringBuilder sb = new StringBuilder();
        SortedSet<String> keys = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        keys.addAll(map.keySet());
        for (String key : keys) {
            if (key.equals("sign") || key.equals("prd") || key.equals("norms") || key.equals("callback") || key.equals("_") || key.equals("cart")) {
                continue;
            }

            if (dictParam != null && null != map.get(key)) {
                vlues2 = map.get(key).toString().trim();
                vlues2 = replaceBlank(vlues2);
            } else {
                return "1";
            }
            vlues2 = replaceBlank(vlues2);
            if (vlues2.equals("") || vlues2.equals(null) || vlues2.contains("[]") || vlues2.contains("[")) {
                continue;
            }
            if (isArray(vlues2) == true) {
                continue;
            }
            if (vlues2.getClass().isArray()) {
                continue;
            }
            if (vlues2.getClass().equals(String[].class)) {
                continue;
            }

            if (vlues2.contains("&") || vlues2.contains("`") || vlues2.contains("/") || vlues2.contains("'") || vlues2.contains("|")) {
                continue;
            }

            sb.append(key);
            sb.append("=");
            sb.append(getEncode(String.valueOf(map.get((key)))));
            sb.append("&");

        }

        String substring = sb.toString().substring(0, sb.toString().length() - 1);
        LogUtil.d("Chao", "getSigns----------------------" + substring);
        return getMd5(substring);
    }

    private static HashMap<String, String> jsonToHashMap(JSONObject jsonObject) {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            //注意：这里获取value使用的是optString
            // optString 和getString的区别：单来说就是optString会在得不到你想要的值时候返回空字符串”“，而getString会抛出异常。
            String value = (String) jsonObject.optString(key);
            map.put(key, value);
        }
        return map;
    }

    public static String getMd5(String sign) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update((sign).getBytes("UTF-8"));
            byte b[] = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String Md5(String sign) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update((sign).getBytes("UTF-8"));
            byte b[] = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSorts() {
        StringBuilder sb = new StringBuilder();
        int math = (int) ((Math.random() * 9 + 1) * 100000);
        long time = System.currentTimeMillis() / 1000;
        sb.append(math);
        sb.append(time);
        return sb.toString();
    }

    public static String getEncode(String token) {
        token = java.net.URLEncoder.encode(token);
        return token;
    }

    public ByteArrayEntity jsonData(Map<String, String> mapData) {
        this.printLog();
        JSONObject jsonObject = new JSONObject();
        try {
            if (mapData != null) {
                for (Map.Entry<String, String> entry : mapData.entrySet()) {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = null;
        try {
            LogUtil.d("FENGJSON", "请求的json数据：" + jsonObject.toString());
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public ByteArrayEntity jsonData_Object(Map<String, Object> mapData) {
        this.printLog();
        JSONObject jsonObject = new JSONObject();
        try {
            if (mapData != null) {
                for (Map.Entry<String, Object> entry : mapData.entrySet()) {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = null;
        try {
            LogUtil.d("FENGJSON", "请求的json数据：" + jsonObject.toString());
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public ByteArrayEntity jsonData(JSONObject mapData) {
        this.printLog();

        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(mapData.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public void printLog() {

    }
}