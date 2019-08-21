package com.nengyuanbox.repaircar.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.config.AES;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * Created by xialv on 2018/2/1.
 */

public class StringUtil {


    private static String str_int;
    private static JSONObject response1;

    //获取get url中的参数
    public static HashMap<String, String> getParamsMap(String url) {

        HashMap<String, String> map = new HashMap<String, String>();
        int start = url.indexOf("?");
        if (start >= 0) {
            String str = url.substring(start + 1);
            System.out.println(str);
            String[] paramsArr = str.split("&");
            for (String param : paramsArr) {
                String[] temp = param.split("=");
                map.put(temp[0], temp[1]);
            }
        }
        return map;
    }

    /**
     * 设置文本
     */
    public static void setTextString(String string, TextView textView) {
        if (!TextUtils.isEmpty(getStringNoEmpty(string))) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(string);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 对字符串判空
     */
    public static String getStringNoEmpty(String string) {
        if (null == string || string.length() == 0 || "null".equals(string)) {
            string = "null";
        }
        return string;
    }

    /**
     * 将字符串格式化为带两位小数的字符串
     *
     * @param str 字符串
     * @return
     */
    public static String format2Decimals(String str) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (df.format(stringToDouble(str)).startsWith(".")) {
            return "0" + df.format(stringToDouble(str));
        } else {
            return df.format(stringToDouble(str));
        }
    }


//     时间比较
    public static boolean TimeCompare(String startTime,String endTime1) {
        boolean  bl_time = false;
        //格式化时间
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            Date beginTime = CurrentTime.parse(startTime);
            Date endTime = CurrentTime.parse(endTime1);
            //判断是否大于两天
            if (((endTime.getTime() - beginTime.getTime()) / (24 * 60 * 60 * 1000)) >= 3) {
                bl_time= false;
            } else {
                bl_time=true;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bl_time;
    }


    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    public static boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = CommApplication.getContext().getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    /**
     * 调此方法输入所要转换的时间输入例如（”2014-06-14-16-09-00”）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                Locale.CHINA);
        Date date;

        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String dataOnes(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        Date date;

        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    public static String formatDouble(double value) {
        String retValue = null;
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        format.setGroupingUsed(false);
        retValue = format.format(value);
        retValue = retValue.replaceAll(",", "");
        return retValue;

    }

    /**
     * 将字符串格式化为带两位小数的字符串
     *
     * @param str 字符串
     * @return
     */
    public static String format6Decimals(String str) {
        if (str.contains("E")) {
            str = StringUtil.formatDouble(Double.parseDouble(str));
        }
        DecimalFormat df = new DecimalFormat("#.000000");
        if (df.format(stringToDouble(str)).startsWith(".")) {
            return "0" + df.format(stringToDouble(str));
        } else {
            return df.format(stringToDouble(str));
        }
    }

    /**
     * 将字符串格式化为带两位小数的字符串
     *
     * @param str 字符串
     * @return
     */
    public static String format2Decimals(Double str) {
        if (String.valueOf(str).contains("E")) {
            str_int = StringUtil.formatDouble((str));
        }
        DecimalFormat df = new DecimalFormat("#.00");
        if (df.format(stringToDouble(str_int)).startsWith(".")) {
            return "0" + df.format(stringToDouble(str_int));
        } else {
            return df.format(stringToDouble(str_int));
        }
    }

    /**
     * 字符串转换成double ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    public static double stringToDouble(String str) {
        if (isNullString(str)) {
            return 0;
        } else {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }


    /**
     * 把View转换成Bitmap类型
     *
     * @return
     * @paramaddViewContent要转换的View
     */
    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());


        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    /**
     * 判断字符串是否为空 为空即true
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNullString(@Nullable String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }


    public static boolean compareVersions(String v1, String v2) {
        //判断是否为空数据
        if (TextUtils.equals(v1, "") || TextUtils.equals(v2, "")) {
            return false;
        }
        String[] str1 = v1.split("\\.");
        String[] str2 = v2.split("\\.");

        if (str1.length == str2.length) {
            for (int i = 0; i < str1.length; i++) {
                if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                    return true;
                } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                    return false;
                } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {

                }
            }
        } else {
            if (str1.length > str2.length) {
                for (int i = 0; i < str2.length; i++) {
                    if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                        return true;
                    } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                        return false;

                    } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {
                        if (str2.length == 1) {
                            continue;
                        }
                        if (i == str2.length - 1) {

                            for (int j = i; j < str1.length; j++) {
                                if (Integer.parseInt(str1[j]) != 0) {
                                    return true;
                                }
                                if (j == str1.length - 1) {
                                    return false;
                                }

                            }
                            return true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < str1.length; i++) {
                    if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                        return true;
                    } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                        return false;

                    } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {
                        if (str1.length == 1) {
                            continue;
                        }
                        if (i == str1.length - 1) {
                            return false;

                        }
                    }

                }
            }
        }
        return false;
    }

    public static JSONObject getAES_decode(JSONObject response) {


        try {
//            LogUtil.d("Chao","解析前数据+++"+response.toString());
            if (response.has("data")) {
                String s = response.getString("data");
                response1 = AES.desEncrypt(s);
            } else {
                response1 = response.getJSONObject("result");
            }
//            LogUtil.d("Chao","解析后数据----"+response1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response1 != null) {
            return response1;
        } else {
            return response;
        }

    }

    //给手机号加*
    public static String getPhone_Asterisk(String phonenum) {
        if (!TextUtils.isEmpty(phonenum) && phonenum.length() > 6) {
            phonenum = phonenum.substring(0, 3) + "****" + phonenum.substring(7, phonenum.length());
        } else {
            return phonenum;
        }
        return phonenum;
    }

    //给银行卡号加*
    public static String BankCard_Asterisk(String aa) {
        if (!TextUtils.isEmpty(aa)) {
            aa = aa.substring(0, 4) + "  ****  ****  " + aa.substring(12, 16);

        } else {
            return aa;
        }
        return aa;
    }

    public static byte[] getImageFromURL(String urlPath) {
        byte[] data = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            is = conn.getInputStream();
            if (conn.getResponseCode() == 200) {
                data = readInputStream(is);
            } else {
                data = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return data;
    }


    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //    **
//            * 生成订单编号  时间戳+3位整数
//     * @return
//             */
    public static String autoOrderId() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date.getTime() / 1000);
        Date orderId = null;
        Random rand = new Random();
        //[900]：900个    100：从100
        int x = rand.nextInt(900000) + 100000;
        try {
            orderId = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(orderId.getTime()) + x;
    }

    //    **
//            * 生成线下订单编号
//    XXD+（年月日，如：20190330）+ （截取5位时间戳）+ （三位随机数）
//
//
//     例如  XXD2019033032764987
//     * @return
//             */
    public static String order_Num() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date orderId = null;
        Random rand = new Random();
        StringBuffer stringBuffer = new StringBuffer();

        //[900]：900个    100：从100
        int x = rand.nextInt(900) + 100;
        try {
            Calendar cd = Calendar.getInstance();
            long l = date.getTime();
            String s = String.valueOf(l);
//            1553938406377
//            1553938406

            stringBuffer.append(cd.get(Calendar.YEAR))
                    .append(cd.get(Calendar.MONTH) + 1)
                    .append(cd.get(Calendar.DATE))
                    .append(s.substring(s.length() - 5, s.length())).append(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


}
