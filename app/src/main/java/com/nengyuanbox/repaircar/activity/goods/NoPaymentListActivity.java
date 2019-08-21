package com.nengyuanbox.repaircar.activity.goods;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.NoPayMentGoodsAdapter;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetGoodsOrdersBean;
import com.nengyuanbox.repaircar.bean.GetWXSignBean;
import com.nengyuanbox.repaircar.bean.WxPayBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DividerItemDecorations;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//未支付商品列表

public class NoPaymentListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String price;
    private IWXAPI api;
    private PayReq request;
    private Handler mHandler = new MyHandler(this);
    private int mPage = 1;
    private ArrayList<GetGoodsOrdersBean.DataBeanX.DataBean> list = new ArrayList<>();
    private NoPayMentGoodsAdapter noPayMentGoodsAdapter;




    private class MyHandler extends Handler {

        private final WeakReference<NoPaymentListActivity> mActivity;
        private NoPaymentListActivity activity;

        private MyHandler(NoPaymentListActivity activity) {

            mActivity = new WeakReference<NoPaymentListActivity>(activity);

            this.activity = mActivity.get();
        }

        @Override
        public void handleMessage(Message msg) {

            if (activity == null) {
                return;
            }
            switch (msg.what) {


                case 0:
                    noPayMentGoodsAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    mPage = 1;
                    list.clear();
                    getOrderList();
                    refreshLayout.finishRefresh();

                    break;
                case 2:
                    mPage++;
                    getOrderList();
                    refreshLayout.finishLoadMore();
                    break;
            }

        }
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_no_payment_list;
    }

    @Override
    protected void initView() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mHandler.sendEmptyMessageDelayed(1, 1000);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mHandler.sendEmptyMessageDelayed(2, 1000);
            }
        });
        //        初始化微信支付
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        api.registerApp(Constant.APP_ID_WX);
        request = new PayReq();
        noPayMentGoodsAdapter = new NoPayMentGoodsAdapter(R.layout.item_nopayment_goods, list);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(NoPaymentListActivity.this));
        rv_processed_list.addItemDecoration(new DividerItemDecorations());
        rv_processed_list.setAdapter(noPayMentGoodsAdapter);

        noPayMentGoodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                   .addOnClickListener(R.id.bt_goods_revocation)
//                         .addOnClickListener(R.id.bt_enent_goods_buy);
//                 撤销
                if (view.getId() == R.id.bt_goods_revocation) {
                    RxToast.normal("撤销");
                    setGoodsOrder(list.get(position).getId());

//                     支付
                } else if (view.getId() == R.id.bt_enent_goods_buy) {
//                    RxToast.normal("支付");
                    getWxPay(list.get(position).getOrder_sn(), list.get(position).getPrice());
                }
            }
        });

    }

    @Override
    protected void setListener() {
        tv_title_name.setText("未支付列表");

    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        if (noPayMentGoodsAdapter != null) {
            noPayMentGoodsAdapter.notifyDataSetChanged();

        }
        getOrderList();
    }

    private void getOrderList() {
        CommonDialog.showProgressDialog(NoPaymentListActivity.this);
        JSONObject jsonObject = new JSONObject();


        NetUtils.newInstance().putReturnJsonNews(NoPaymentListActivity.this, NetUtils.POST, UrlConstant.GETGOODSORDERS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("未支付列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetGoodsOrdersBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetGoodsOrdersBean.class);
                        List<GetGoodsOrdersBean.DataBeanX.DataBean> data = getOrderListBean.getData().getData();
                        list.addAll(data);
                        if (list.size() > 0) {
                            refreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            refreshLayout.setVisibility(View.GONE);
                        }
                        mHandler.sendEmptyMessage(0);


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(NoPaymentListActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("未支付列表baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("未支付列表baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("未支付列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    取消订单
    private void setGoodsOrder(String order_id) {
        CommonDialog.showProgressDialog(NoPaymentListActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("order_id", order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        NetUtils.newInstance().putReturnJsonNews(NoPaymentListActivity.this, NetUtils.POST, UrlConstant.SETGOODSORDER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("未支付列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        RxToast.normal(response.getString("message"));
                        list.clear();
                        noPayMentGoodsAdapter.notifyDataSetChanged();
                        getOrderList();

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(NoPaymentListActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("未支付列表baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("未支付列表baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("未支付列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));
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
                        LoginExpirelUtils.getLoginExpirel(NoPaymentListActivity.this);

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
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));
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


                        Toast.makeText(NoPaymentListActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(NoPaymentListActivity.this);

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
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(NoPaymentListActivity.this, getResources().getString(R.string.net_error));
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
