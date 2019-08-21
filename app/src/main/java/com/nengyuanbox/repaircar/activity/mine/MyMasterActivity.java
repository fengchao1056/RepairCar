package com.nengyuanbox.repaircar.activity.mine;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.GetUserStoreMasterAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetUserStoreMasterBean;
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

//我的师傅界面
public class MyMasterActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int mPage = 1;
    private List<GetUserStoreMasterBean.DataBean> getorderlist = new ArrayList<>();
    private Handler mHandler = new MyHandler(this);
    private GetUserStoreMasterAdapter getUserStoreMasterAdapter;

    private class MyHandler extends Handler {

        private final WeakReference<MyMasterActivity> mActivity;
        private MyMasterActivity activity;

        private MyHandler(MyMasterActivity activity) {

            mActivity = new WeakReference<MyMasterActivity>(activity);

            this.activity = mActivity.get();
        }

        @Override
        public void handleMessage(Message msg) {

            if (activity == null) {
                return;
            }
            switch (msg.what) {


                case 0:
                    getUserStoreMasterAdapter.notifyDataSetChanged();
                    break;
                case 1:
//                    mPage = 1;
                    getorderlist.clear();
                    getUserStoreMaster();
                    refreshLayout.finishRefresh();

                    break;
                case 2:
                    refreshLayout.finishLoadMore();
                    break;
            }

        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_master;
    }

    @Override
    protected void initView() {
        getUserStoreMasterAdapter = new GetUserStoreMasterAdapter(R.layout.item_mymaster,getorderlist);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(MyMasterActivity.this));
        rv_processed_list.setAdapter(getUserStoreMasterAdapter);
        rv_processed_list.addItemDecoration(new DividerItemDecorations());
        getUserStoreMasterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                setUserStoreMaster(getorderlist.get(position).getMaster_uid());
            }
        });
        refresh();
        getUserStoreMaster();
    }

    @Override
    protected void setListener() {

        tv_title_name.setText("我的师傅");

    }




    //    我的师傅
    private void getUserStoreMaster() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();


        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETUSERSTOREMASTER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("我的师傅：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        GetUserStoreMasterBean getUserStoreMasterBean = JsonUtil.getSingleBean(response.toString(), GetUserStoreMasterBean.class);
                        List<GetUserStoreMasterBean.DataBean> data = getUserStoreMasterBean.getData();

                        getorderlist.addAll(data);
                        if (getorderlist.size() > 0) {
                            refreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            refreshLayout.setVisibility(View.GONE);
                        }
                        mHandler.sendEmptyMessage(0);

//                        RxToast.normal(response.getString("message"));



                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MyMasterActivity.this);

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
                CommonDialog.showInfoDialog(MyMasterActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MyMasterActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MyMasterActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }



    //    解绑师傅
    private void setUserStoreMaster(String master_uid) {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("master_uid",master_uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.SETUSERSTOREMASTER, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("解绑师傅：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal("解绑成功");

                        mHandler.sendEmptyMessage(1);


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MyMasterActivity.this);

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
                CommonDialog.showInfoDialog(MyMasterActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MyMasterActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MyMasterActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
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
}
