package com.nengyuanbox.repaircar.utils;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 *
 * webview工具类
 */

public class WebViewUtils {

    /**
     * 初始化webview
     */
    public static void initWebView(WebView webView){
        WebSettings webSettings = webView.getSettings();
        //支持缩放，默认为true。
        //调整图片至适合webview的大小
        webSettings .setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings .setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings .setDefaultTextEncodingName("utf-8");
        ////设置自动加载图片
        webSettings .setLoadsImagesAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //设置字体大小
//        webSettings.setTextSize(WebSettings.TextSize.);
    }
}
