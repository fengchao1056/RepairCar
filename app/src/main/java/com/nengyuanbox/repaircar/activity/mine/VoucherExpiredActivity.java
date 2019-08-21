package com.nengyuanbox.repaircar.activity.mine;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.VouchersAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetVouchersListBean;
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
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class VoucherExpiredActivity extends BaseActivity {

    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    private Handler mHandler = new MyHandler(this);
    ArrayList<GetVouchersListBean.DataBeanX.DataBean> list = new ArrayList<>();
    private VouchersAdapter getUserFlowAdapter;
    private int mPage = 1;


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    private class MyHandler extends Handler {

        private final WeakReference<VoucherExpiredActivity> mActivity;
        private VoucherExpiredActivity activity;

        private MyHandler(VoucherExpiredActivity activity) {

            mActivity = new WeakReference<VoucherExpiredActivity>(activity);

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
                    mPage++;
                    getOrderList();
                    refreshLayout.finishLoadMore();
                    break;
            }

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voucher_expired;
    }

    @Override
    protected void initView() {
        tv_title_name.setText("失效的券");
        getUserFlowAdapter = new VouchersAdapter(R.layout.item_voucher, list);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(VoucherExpiredActivity.this));
        rv_processed_list.addItemDecoration(new DividerItemDecorations());
        rv_processed_list.setAdapter(getUserFlowAdapter);
        refresh();

        getOrderList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void getOrderList() {
        CommonDialog.showProgressDialog(VoucherExpiredActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
//            查询方式（0，未使用，1已失效）
            jsonObject.put("getType", "1");
            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(VoucherExpiredActivity.this, NetUtils.POST, UrlConstant.GETVOUCHERSLIST, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("卡券查询列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetVouchersListBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetVouchersListBean.class);
                        List<GetVouchersListBean.DataBeanX.DataBean> data = getOrderListBean.getData().getData();
                        if (data != null && data.size() > 0) {
                            list.addAll(data);
                            mHandler.sendEmptyMessage(0);
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(VoucherExpiredActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("卡券查询列表baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("卡券查询列表baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(VoucherExpiredActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("卡券查询列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(VoucherExpiredActivity.this, getResources().getString(R.string.net_error));
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
    protected void setListener() {

    }
}
