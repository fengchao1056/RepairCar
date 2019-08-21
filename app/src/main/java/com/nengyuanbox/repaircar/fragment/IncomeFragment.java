package com.nengyuanbox.repaircar.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.GetUserFlowAdapter;
import com.nengyuanbox.repaircar.base.BaseFragment;
import com.nengyuanbox.repaircar.bean.GetUserFlowBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DividerItemDecorations;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

//收入    账户明细
public class IncomeFragment extends BaseFragment {
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Handler mHandler = new MyHandler(this);
    ArrayList<GetUserFlowBean.DataBeanX.DataBean> list = new ArrayList<>();
    private GetUserFlowAdapter getUserFlowAdapter;
    private int mPage = 1;

    private class MyHandler extends Handler {

        private final WeakReference<IncomeFragment> mActivity;
        private IncomeFragment activity;

        private MyHandler(IncomeFragment activity) {

            mActivity = new WeakReference<IncomeFragment>(activity);

            this.activity = mActivity.get();
        }

        @Override
        public void handleMessage(Message msg) {

            if (activity == null) {
                return;
            }
            switch (msg.what) {

                case 0:
                    getUserFlowAdapter.notifyDataSetChanged();

                    break;
                case 1:
                    list.clear();
                    mPage = 1;
                    getOrderList();
                    refreshLayout.finishRefresh();

                    break;
                case 2:
                    mPage ++;
                    getOrderList();
                    refreshLayout.finishLoadMore();
                    break;
            }

        }
    }

    @Override
    public void init(Bundle savedInstanceState) {

        getUserFlowAdapter = new GetUserFlowAdapter(R.layout.item_money_detail,list);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_processed_list.addItemDecoration(new DividerItemDecorations());
        rv_processed_list.setAdapter(getUserFlowAdapter);
        refresh();

    }

    private void getOrderList() {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        try {
//            查询方式（0：全部 1：支出 2：收入）
            jsonObject.put("getType", "2");
            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETUSERFLOW, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("收入-资金流水记录" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetUserFlowBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetUserFlowBean.class);
                        List<GetUserFlowBean.DataBeanX.DataBean> data = getOrderListBean.getData().getData();
                         if (data!=null&&data.size()>0){
                             list.addAll(data);
                             mHandler.sendEmptyMessage(0);
                         }



                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(getActivity());

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("收入-资金流水记录baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("收入-资金流水记录baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("收入-资金流水记录baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
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
            if (list!=null&&list.size()>0){
                list.clear();
            }
            if (getUserFlowAdapter != null) {

                getUserFlowAdapter.notifyDataSetChanged();
            }
            mPage = 1;
            getOrderList();

        }
    }
}
