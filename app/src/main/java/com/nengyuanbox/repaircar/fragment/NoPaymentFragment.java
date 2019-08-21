package com.nengyuanbox.repaircar.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.NoPayMentGoodsAdapter;
import com.nengyuanbox.repaircar.app.CommApplication;
import com.nengyuanbox.repaircar.base.BaseFragment;
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
import cz.msebera.android.httpclient.Header;

//未支付列表
public class NoPaymentFragment extends BaseFragment {
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String price;
    private IWXAPI api;
    private PayReq request;
    private int mPage = 1;
    private ArrayList<GetGoodsOrdersBean.DataBeanX.DataBean> list=new ArrayList<>();
    private NoPayMentGoodsAdapter noPayMentGoodsAdapter;
    private Handler mHandler = new MyHandler(this);
    private class MyHandler extends Handler {

        private final WeakReference<NoPaymentFragment> mActivity;
        private NoPaymentFragment activity;

        private MyHandler(NoPaymentFragment activity) {

            mActivity = new WeakReference<NoPaymentFragment>(activity);

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
    public void init(Bundle savedInstanceState) {
        refresh();
        //        初始化微信支付
        api = WXAPIFactory.createWXAPI(getActivity(), Constant.APP_ID_WX, true);
        api.registerApp(Constant.APP_ID_WX);
        request = new PayReq();
        noPayMentGoodsAdapter = new NoPayMentGoodsAdapter(R.layout.item_nopayment_goods,list);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_processed_list.addItemDecoration(new DividerItemDecorations());
        rv_processed_list.setAdapter(noPayMentGoodsAdapter);

        noPayMentGoodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                   .addOnClickListener(R.id.bt_goods_revocation)
//                         .addOnClickListener(R.id.bt_enent_goods_buy);
//                 撤销
                if (view.getId()==R.id.bt_goods_revocation){
                    RxToast.normal("撤销");
                    setGoodsOrder(list.get(position).getId());

//                     支付
                }else  if (view.getId()==R.id.bt_enent_goods_buy){
                    RxToast.normal("支付");
                    getWxPay(list.get(position).getOrder_sn(),list.get(position).getPrice());
                }
            }
        });

    }

    private void refresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mHandler.sendEmptyMessageDelayed(1, 1000);

            }
        });
        refreshLayout.setEnableLoadMore(false);
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
////                mHandler.sendEmptyMessageDelayed(2, 1000);
//            }
//        });
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_toprocessed;
    }

    

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
                list.clear();
        if (noPayMentGoodsAdapter!=null){
            noPayMentGoodsAdapter.notifyDataSetChanged();

        }
        getOrderList();
    }



    private void getOrderList() {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pay_type","0");
        } catch (JSONException e) {


        }


        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETGOODSORDERS, jsonObject, new JsonHttpResponseHandler() {


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
                        if (data != null && data.size() > 0) {
                            list.addAll(data);
                            if (list.size() > 0) {
                                refreshLayout.setVisibility(View.VISIBLE);
                            } else {
                                refreshLayout.setVisibility(View.GONE);
                            }
                            noPayMentGoodsAdapter.notifyDataSetChanged();
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("未支付列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
    //    取消订单
    private void setGoodsOrder(String order_id) {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("order_id",order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.SETGOODSORDER, jsonObject, new JsonHttpResponseHandler() {


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
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("未支付列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }



    //    小程序支付统一下单
    private void getWxPay(String order_sn, String money) {
        CommonDialog.showProgressDialog(getActivity());
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

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETWXPAY, jsonObject, new JsonHttpResponseHandler() {


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
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }
        });


    }

    private void getWXSign(String partnerId) {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("prepay_id", partnerId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETWXSIGN, jsonObject, new JsonHttpResponseHandler() {


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


                        Toast.makeText(getActivity(), "微信支付", Toast.LENGTH_SHORT).show();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }
        });
    }
}
