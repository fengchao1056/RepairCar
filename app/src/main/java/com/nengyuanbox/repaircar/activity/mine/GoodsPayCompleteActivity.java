package com.nengyuanbox.repaircar.activity.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.Order_CompleteRvAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.CompleteOrderBean;
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

public class GoodsPayCompleteActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<CompleteOrderBean.DataBeanX.DataBean> getorderlist = new ArrayList<>();

    private int mPage = 1;
    private Order_CompleteRvAdapter order_toProcessedRvAdapter;
    private Handler mHandler = new MyHandler(this);
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_pay_complete;
    }

    @Override
    protected void initView() {
        refresh();
        getOrderList();

        order_toProcessedRvAdapter = new Order_CompleteRvAdapter(getorderlist, GoodsPayCompleteActivity.this);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(GoodsPayCompleteActivity.this));
        rv_processed_list.setAdapter(order_toProcessedRvAdapter);
        order_toProcessedRvAdapter.setItemClickListener(new Order_CompleteRvAdapter.OnItemClickListener() {
            @Override
            public void onLinearClick(int itemPosition) {
                Intent intent = new Intent(GoodsPayCompleteActivity.this, OrderDetailsActivity.class);
                intent.putExtra("order_state", "已完成");
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title_name.setText("我的采购");
    }

    @Override
    protected void setListener() {

    }


    private class MyHandler extends Handler {

        private final WeakReference<GoodsPayCompleteActivity> mActivity;
        private GoodsPayCompleteActivity activity;

        private MyHandler(GoodsPayCompleteActivity activity) {

            mActivity = new WeakReference<GoodsPayCompleteActivity>(activity);

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


    private void getOrderList() {
        CommonDialog.showProgressDialog(GoodsPayCompleteActivity.this);
        JSONObject jsonObject = new JSONObject();


        try {

            jsonObject.put("getType", "2");
            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");
//            jsonObject.put("area_x", area_x);
//            jsonObject.put("area_y", area_y);
            String str_role = SharedPreferenceUtil.getString(GoodsPayCompleteActivity.this, Constant.ROLE, "");
            if (!RxDataTool.isEmpty(str_role)) {
                if (str_role.equals("master")) {
                    url = UrlConstant.GETORDERLIST;
                } else if (str_role.equals("store")) {
                    url = UrlConstant.GETMASTERORDERLIST;
                }
            } else {
                url = UrlConstant.GETORDERLIST;
            }
            ViseLog.d("师傅已完成订单列表---地址" + url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(GoodsPayCompleteActivity.this, NetUtils.POST, url, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("师傅已完成订单列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        CompleteOrderBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), CompleteOrderBean.class);
                        CompleteOrderBean.DataBeanX data = getOrderListBean.getData();
                        getorderlist.addAll(data.getData());
                        if (getorderlist.size() > 0) {
                            refreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            refreshLayout.setVisibility(View.GONE);
                        }
                        mHandler.sendEmptyMessage(0);


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(GoodsPayCompleteActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("师傅已完成订单列表baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("师傅已完成订单列表baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(GoodsPayCompleteActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("师傅已完成订单列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(GoodsPayCompleteActivity.this, getResources().getString(R.string.net_error));
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

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // TODO Auto-generated method stub
//        super.setUserVisibleHint(isVisibleToUser);
//        // 每次切换fragment时调用的方法
//        if (isVisibleToUser) {
//            if ( getorderlist!= null && getorderlist.size() > 0) {
//                getorderlist.clear();
//            }
//            if (order_toProcessedRvAdapter != null) {
//
//                order_toProcessedRvAdapter.notifyDataSetChanged();
//            }
//            mPage = 1;
//            getOrderList();
//
//        }
//    }
}
