package com.nengyuanbox.repaircar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.goods.EventGoodsActivity;
import com.nengyuanbox.repaircar.activity.mine.InvalidVoucherActivity;
import com.nengyuanbox.repaircar.adapter.VouchersAdapter;
import com.nengyuanbox.repaircar.base.BaseFragment;
import com.nengyuanbox.repaircar.bean.GetVouchersInfoBean;
import com.nengyuanbox.repaircar.bean.GetVouchersListBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.PickerView.DialogVoucherDetails;
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

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

//券
public class VoucherFragment extends BaseFragment {
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.bt_watch)
    Button btWatch;
    Unbinder unbinder;


    private Handler mHandler = new MyHandler(this);
    ArrayList<GetVouchersListBean.DataBeanX.DataBean> list = new ArrayList<>();
    private VouchersAdapter getUserFlowAdapter;
    private int mPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_watch)
    public void onViewClicked() {

        startActivity(new Intent(getActivity(), InvalidVoucherActivity.class));
    }

    private class MyHandler extends Handler {

        private final WeakReference<VoucherFragment> mActivity;
        private VoucherFragment activity;

        private MyHandler(VoucherFragment activity) {

            mActivity = new WeakReference<VoucherFragment>(activity);

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
    public void init(Bundle savedInstanceState) {

        getUserFlowAdapter = new VouchersAdapter(R.layout.item_voucher, list);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_processed_list.setAdapter(getUserFlowAdapter);
        refresh();

        getUserFlowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                getVouchersInfo(list.get(position).getId());
            }
        });

    }
//    卡券查询列表
    private void getOrderList() {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        try {
//            查询方式（0，未使用，1已失效）
            jsonObject.put("getType", "0");
            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETVOUCHERSLIST, jsonObject, new JsonHttpResponseHandler() {


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
                        LoginExpirelUtils.getLoginExpirel(getActivity());

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
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("卡券查询列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

//    卡券查询详情
    private void getVouchersInfo( String vouchers_id) {
        CommonDialog.showProgressDialog(getActivity());
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("vouchers_id", vouchers_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(getActivity(), NetUtils.POST, UrlConstant.GETVOUCHERSINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("卡券查询详情" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetVouchersInfoBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetVouchersInfoBean.class);
                        GetVouchersInfoBean.DataBean data = getOrderListBean.getData();
                         if (!RxDataTool.isEmpty(data.getName())&&!RxDataTool.isEmpty(data.getText())){
                               showDialog(data.getName(),data.getText());

                         }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(getActivity());

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("卡券查询详情baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("卡券查询详情baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("卡券查询详情baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(getActivity(), getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    private void showDialog(String name, String text) {
        final DialogVoucherDetails dialogVoucherDetails=new DialogVoucherDetails(getActivity());
//        关闭弹窗
        dialogVoucherDetails.getIv_cross().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVoucherDetails.cancel();
            }
        });
        dialogVoucherDetails.getTv_voucher_name().setText(name);

        WebView wb_view = dialogVoucherDetails.getWb_view();
//        WebViewUtils.initWebView(wb_view);
        if (list != null) {
            URLDecoder urlDecoder = new URLDecoder();
            String decode = null;
            try {
                decode = urlDecoder.decode(text, "UTF-8");
                wb_view.loadDataWithBaseURL(null, decode, "text/html", "utf-8", null);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
//        去使用优惠券
        dialogVoucherDetails.getBt_go_use().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogVoucherDetails.dismiss();
                startActivity(new Intent(getActivity(),EventGoodsActivity.class));
                getActivity().finish();
            }
        });
        dialogVoucherDetails.show();

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
        return R.layout.fragment_voucher;
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
            if (list != null && list.size() > 0) {
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
