package com.nengyuanbox.repaircar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.mine.OrderDetailsActivity;
import com.nengyuanbox.repaircar.adapter.Order_ToProcessedRvAdapter;
import com.nengyuanbox.repaircar.base.BaseFragment;
import com.nengyuanbox.repaircar.bean.CancelledOrderBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.ClickUtil;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

//待处理

public class ToProcessedFragment extends BaseFragment {
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<CancelledOrderBean.DataBeanX.DataBean> getorderlist = new ArrayList<>();
    private Handler mHandler = new MyHandler(this);
    private int mPage = 1;
    private Order_ToProcessedRvAdapter order_toProcessedRvAdapter;
    private String url;

    private class MyHandler extends Handler {

        private final WeakReference<ToProcessedFragment> mActivity;
        private ToProcessedFragment activity;

        private MyHandler(ToProcessedFragment activity) {

            mActivity = new WeakReference<ToProcessedFragment>(activity);

            this.activity = mActivity.get();
        }

        @Override
        public void handleMessage(Message msg) {

            if (activity == null) {
                return;
            }
            switch (msg.what) {


                case 0:
                    order_toProcessedRvAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    mPage = 1;
                    getorderlist.clear();
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
//        getOrderList();

        order_toProcessedRvAdapter = new Order_ToProcessedRvAdapter(getorderlist, getActivity());
        rv_processed_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_processed_list.setAdapter(order_toProcessedRvAdapter);
        order_toProcessedRvAdapter.setItemClickListener(new Order_ToProcessedRvAdapter.OnItemClickListener() {
            @Override
            public void onLinearClick(int itemPosition) {
                if (!ClickUtil.isFastClick()) {
                    Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                    intent.putExtra("order_state","待处理");
                    intent.putExtra("order_sn", getorderlist.get(itemPosition).getOrder_sn());
                    startActivity(intent);

                } else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }

            }


            //              出发
            @Override
            public void onSetOffClick(int itemPosition) {
                if (!ClickUtil.isFastClick()) {
                    setOrder("4", getorderlist.get(itemPosition).getOrder_sn());

                } else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }

            }

            //            点击完成
            @Override
            public void onCompleteClick(int itemPosition) {
                if (!ClickUtil.isFastClick()) {
                    setOrder("3", getorderlist.get(itemPosition).getOrder_sn());

                } else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }
            }

            //            点击撤销
            @Override
            public void onCancleClick(int itemPosition) {
                if (!ClickUtil.isFastClick()) {
                    setOrder("2", getorderlist.get(itemPosition).getOrder_sn());

                } else {
                    RxToast.normal("不可连续点击哦");
                    return;
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
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mHandler.sendEmptyMessageDelayed(2, 1000);
            }
        });
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_toprocessed;
    }

    private void getOrderList() {
        //                      角色判定


        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("getType", "1");
            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");
//            jsonObject.put("area_x", area_x);
//            jsonObject.put("area_y", area_y);

            String str_role = SharedPreferenceUtil.getString(getActivity(), Constant.ROLE, "");
            if (!RxDataTool.isEmpty(str_role)){
                if (str_role.equals("master")){
                    url = UrlConstant.GETORDERLIST;
                }else   if (str_role.equals("store")){
                    url = UrlConstant.GETMASTERORDERLIST;
                }
            }else {
                url = UrlConstant.GETORDERLIST;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, url, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("师傅待处理订单列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        CancelledOrderBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), CancelledOrderBean.class);
                        CancelledOrderBean.DataBeanX data = getOrderListBean.getData();
                        getorderlist.addAll(data.getData());
                        if (getorderlist.size() > 0) {
                            refreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            refreshLayout.setVisibility(View.GONE);
                        }
                        mHandler.sendEmptyMessage(0);


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
            }
        });


    }

    //    处理订单
    private void setOrder(String setType, String order_sn) {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("order_sn", order_sn);
//            处理方式（1：用户取消订单并退款 2：师傅取消订单并退款 3：师傅操作订单完成 4：师傅操作订单出发）
            jsonObject.put("setType", setType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.SETORDER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("处理订单" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        mHandler.sendEmptyMessage(1);


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
                ViseLog.d("处理订单报错:" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("处理订单报错:" + throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ViseLog.d("处理订单报错:" + throwable.toString());
                CommonDialog.closeProgressDialog();
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次切换fragment时调用的方法
        if (getorderlist != null && getorderlist.size() > 0) {
            getorderlist.clear();
        }
        if (order_toProcessedRvAdapter != null) {

            order_toProcessedRvAdapter.notifyDataSetChanged();
        }
        mPage = 1;
        getOrderList();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
