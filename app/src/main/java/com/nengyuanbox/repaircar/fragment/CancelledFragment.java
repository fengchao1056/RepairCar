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
import com.nengyuanbox.repaircar.adapter.Order_CancelRvAdapter;
import com.nengyuanbox.repaircar.base.BaseFragment;
import com.nengyuanbox.repaircar.bean.GetOrderListBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
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

//已取消
public class CancelledFragment extends BaseFragment {
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<GetOrderListBean.DataBeanX.DataBean> getorderlist = new ArrayList<>();
    private Handler mHandler = new MyHandler(this);
    private int mPage = 1;
    private Order_CancelRvAdapter order_toProcessedRvAdapter;
    private String url;

    private class MyHandler extends Handler {

        private final WeakReference<CancelledFragment> mActivity;
        private CancelledFragment activity;

        private MyHandler(CancelledFragment activity) {

            mActivity = new WeakReference<CancelledFragment>(activity);

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

        order_toProcessedRvAdapter = new Order_CancelRvAdapter(getorderlist, getActivity());
        rv_processed_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_processed_list.setAdapter(order_toProcessedRvAdapter);
        order_toProcessedRvAdapter.setItemClickListener(new Order_CancelRvAdapter.OnItemClickListener() {
            @Override
            public void onLinearClick(int itemPosition) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("order_state","已取消");
                intent.putExtra("order_sn", getorderlist.get(itemPosition).getOrder_sn());
                startActivity(intent);
            }


            //              出发
            @Override
            public void onSetOffClick(int itemPosition) {

            }

            @Override
            public void onCompleteClick(int itemPosition) {

            }

            @Override
            public void onCancleClick(int itemPosition) {

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
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        try {


            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");
//            jsonObject.put("area_x", area_x);
//            jsonObject.put("area_y", area_y);
            String str_role = SharedPreferenceUtil.getString(getActivity(), Constant.ROLE, "");
            if (!RxDataTool.isEmpty(str_role)){
                if (str_role.equals("master")){
                    jsonObject.put("getType", "7");
                    url = UrlConstant.GETORDERLIST;
                }else   if (str_role.equals("store")){
                    jsonObject.put("getType", "3");
                    url = UrlConstant.GETMASTERORDERLIST;
                }
            }else {
                jsonObject.put("getType", "7");
                url = UrlConstant.GETORDERLIST;
            }
            ViseLog.d("师傅已取消订单列表---地址"+url);
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
                    ViseLog.d("师傅已取消订单列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetOrderListBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetOrderListBean.class);
                        GetOrderListBean.DataBeanX data = getOrderListBean.getData();
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
                    ViseLog.d("师傅已取消订单列表报错" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("师傅已取消订单列表报错" +throwable.toString() + responseString);
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("师傅已取消订单列表报错" +throwable.toString());
                CommonDialog.showInfoDialog(getActivity(),getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        // 每次切换fragment时调用的方法
        if (isVisibleToUser) {
            if ( getorderlist!= null && getorderlist.size() > 0) {
                getorderlist.clear();
            }
            if (order_toProcessedRvAdapter != null) {

                order_toProcessedRvAdapter.notifyDataSetChanged();
            }
            mPage = 1;
            getOrderList();

        }
    }
}
