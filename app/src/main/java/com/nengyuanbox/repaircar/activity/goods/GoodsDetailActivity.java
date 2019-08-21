package com.nengyuanbox.repaircar.activity.goods;

import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.OrderPagerAdapter;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.AddMasterGoodsOrderBean;
import com.nengyuanbox.repaircar.bean.GetGoodsInfoBean;
import com.nengyuanbox.repaircar.bean.GetWXSignBean;
import com.nengyuanbox.repaircar.bean.WxPayBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.eventbus.GoodsDetailEventBean;
import com.nengyuanbox.repaircar.fragment.GoodsParameterFragment;
import com.nengyuanbox.repaircar.fragment.GoodsShopDetailFragment;
import com.nengyuanbox.repaircar.fragment.GraphicDetailsFragment;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.GlideImageLoader;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.MyViewPager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//商品详情
public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.br_img)
    Banner br_img;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_goods_num)
    TextView tv_goods_num;
    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_goods_oldpeice)
    TextView tv_goods_oldpeice;
    @BindView(R.id.tv_goods_number)
    TextView tv_goods_number;


    @BindView(R.id.bt_buy)
    Button bt_buy;
    @BindView(R.id.tl_layout)
    TabLayout tl_layout;
    @BindView(R.id.vp_pager)
    MyViewPager vp_pager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();
    private String price;
    private IWXAPI api;
    private PayReq request;
    private String goods_num;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        getFragment();
        //        初始化微信支付
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        api.registerApp(Constant.APP_ID_WX);
        request = new PayReq();

    }

    private void getFragment() {
//        EventBus.getDefault().register(GoodsDetailActivity.this);
        GraphicDetailsFragment toProcessedFragment = new GraphicDetailsFragment();
        GoodsParameterFragment completedFragment = new GoodsParameterFragment();
        GoodsShopDetailFragment cancelledFragment = new GoodsShopDetailFragment();
        fragmentList.add(toProcessedFragment);
        fragmentList.add(completedFragment);
        fragmentList.add(cancelledFragment);
        strings.add("图文详情");
        strings.add("商品参数");
        strings.add("购买须知");

        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager(), fragmentList, strings);
        vp_pager.setAdapter(orderPagerAdapter);

        vp_pager.setOffscreenPageLimit(3);
        tl_layout.addTab(tl_layout.newTab().setTag(strings.get(0)));
        tl_layout.addTab(tl_layout.newTab().setTag(strings.get(1)));
        tl_layout.addTab(tl_layout.newTab().setTag(strings.get(2)));
        tl_layout.setupWithViewPager(vp_pager);


    }

    @Override
    protected void setListener() {

        tv_title_name.setText("商品详情");
        getOrderList();
    }


    private void getOrderList() {
        CommonDialog.showProgressDialog(GoodsDetailActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("goods_id", getIntent().getStringExtra("goods_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(GoodsDetailActivity.this, NetUtils.POST, UrlConstant.GETGOODSINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("已上架商品的详情" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetGoodsInfoBean getGoodsInfoBean = JsonUtil.getSingleBean(response.toString(), GetGoodsInfoBean.class);
                        GetGoodsInfoBean.DataBean data = getGoodsInfoBean.getData();
                        if (data != null) {
                            tv_goods_name.setText(data.getGoods_name());
                            tv_goods_oldpeice.setText("原价：￥" + data.getGoods_price());
                            tv_goods_oldpeice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 设置中划线并加清晰
                            tv_goods_price.setText("￥" + data.getGoods_master_price());
                            tv_goods_num.setText("已售" + data.getPay_num() + "件");
                            tv_goods_number.setText(data.getPay_num() + "人已买");
                            goods_num = data.getGoods_num();
                            price = data.getGoods_price();
//                            wv_goods_detail_txt.setText(data.getGoods_detail());
                            EventBus.getDefault().post(new GoodsDetailEventBean(data.getGoods_detail(), data.getGoods_parameter(), data.getGoods_shop_detail()));


                            SetBanner(br_img, data.getGoods_img());


                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(GoodsDetailActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("已上架商品的详情baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    //    支付商品订单
    private void addMasterGoodsOrder() {
        CommonDialog.showProgressDialog(GoodsDetailActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("goods_id", getIntent().getStringExtra("goods_id"));
            jsonObject.put("goods_num", "1");
            jsonObject.put("money", price);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(GoodsDetailActivity.this, NetUtils.POST, UrlConstant.ADDMASTERGOODSORDER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("支付商品订单" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        AddMasterGoodsOrderBean addMasterGoodsOrderBean = JsonUtil.getSingleBean(response.toString(), AddMasterGoodsOrderBean.class);
                        AddMasterGoodsOrderBean.DataBean data = addMasterGoodsOrderBean.getData();
                        if (data != null) {

                            getWxPay(data.getOrder_sn(), data.getVip_money());
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(GoodsDetailActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("支付商品订单baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("支付商品订单baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("查询师傅的收货信息baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    小程序支付统一下单
    private void getWxPay(String order_sn, String money) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

            String uid = SharedPreferenceUtil.getString(CommApplication.getContext(), Constant.YHID, "-1");
            jsonObject.put("order_sn", order_sn);
            jsonObject.put("order_money", money);
            jsonObject.put("app_user_id", uid);
            jsonObject.put("tradeType", "APP");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETWXPAY, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("小程序支付统一下单：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        WxPayBean singleBean = JsonUtil.getSingleBean(response.toString(), WxPayBean.class);
                        getWXSign(singleBean.getData().getPrepay_id());
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(GoodsDetailActivity.this);

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
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    private void getWXSign(String partnerId) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("prepay_id", partnerId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETWXSIGN, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("微信app支付sign：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        GetWXSignBean singleBean = JsonUtil.getSingleBean(response.toString(), GetWXSignBean.class);

                        request.appId = singleBean.getData().getAppid();
                        request.partnerId = singleBean.getData().getPartnerid();
                        request.prepayId = singleBean.getData().getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = singleBean.getData().getNoncestr();
                        request.timeStamp = singleBean.getData().getTimestamp() + "";
                        request.sign = singleBean.getData().getSign();
                        api.sendReq(request);


                        Toast.makeText(GoodsDetailActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(GoodsDetailActivity.this);

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
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(GoodsDetailActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }


    private void SetBanner(Banner banner, List<String> url) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(url);

        banner.setImages(arrayList);

        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {


            }
        });
        //设置自动轮播，默认为true
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    @OnClick({R.id.iv_back, R.id.bt_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_buy:
                if (!RxDataTool.isEmpty(goods_num)) {
                    if (goods_num.equals("0")) {
                        RxToast.normal("可买数量不足哦");
                        return;
                    } else {
                        addMasterGoodsOrder();
                    }
                } else {
                    RxToast.normal("可买数量不足哦");
                    return;
                }

                break;
        }
    }


//    @Subscribe
//    public  void  getGoodsDetailsEvent(GoodsDetailEventBean eventBean){
////        if (!RxDataTool.isEmpty(eventBean.getGoods_detail())){
////            wv_view.loadDataWithBaseURL(null,
////                    getHtmlData(eventBean.getGoods_detail()), "text/html", "utf-8", null);
////        }
//
//
//    }
}
