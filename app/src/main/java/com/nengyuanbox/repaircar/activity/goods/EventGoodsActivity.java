package com.nengyuanbox.repaircar.activity.goods;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
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
import com.nengyuanbox.repaircar.activity.mine.H5AppWeb;
import com.nengyuanbox.repaircar.adapter.GetMasterGoodsAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetMasterGoods;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSureCancel;
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

//活动商品
public class EventGoodsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.rv_processed_list)
    RecyclerView rv_processed_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ArrayList<GetMasterGoods.DataBeanX.DataBean> list = new ArrayList<>();
    @BindView(R.id.tv_go_nengyuanbox)
    TextView tvGoNengyuanbox;
    @BindView(R.id.tv_no_payment)
    TextView tvNoPayment;

    private Handler mHandler = new MyHandler(this);
    private int mPage = 1;
    private GetMasterGoodsAdapter getUserFlowAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_goods;
    }

    @Override

    protected void initView() {
        refresh();
        tv_title_name.setText("活动商品");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getUserFlowAdapter = new GetMasterGoodsAdapter(R.layout.item_event_goods, list);
        rv_processed_list.setLayoutManager(new LinearLayoutManager(EventGoodsActivity.this));
        rv_processed_list.addItemDecoration(new DividerItemDecorations());
        rv_processed_list.setAdapter(getUserFlowAdapter);
        getUserFlowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                 if (list.get(position).getGoods_num().equals("0")){
                     RxToast.normal("可买数量不足哦");
                     return;
                 }else {
                     Intent intent = new Intent(EventGoodsActivity.this, SettlementActivity.class);
                     intent.putExtra("goods_id", list.get(position).getGoods_id());
                     startActivity(intent);
                 }

            }
        });

//        商品详情
        getUserFlowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EventGoodsActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods_id", list.get(position).getGoods_id());
                startActivity(intent);
            }
        });




    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list!=null){
            list.clear();
        }
        getOrderList();
    }

    @OnClick({R.id.tv_go_nengyuanbox, R.id.tv_no_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//             去能源宝网点
            case R.id.tv_go_nengyuanbox:
//                go_nengyuanbox();

                startActivity(new Intent(EventGoodsActivity.this, H5AppWeb.class));

                break;
//                未支付列表
            case R.id.tv_no_payment:
                startActivity(new Intent(EventGoodsActivity.this,NoPaymentListActivity.class));
                break;
        }
    }

    private void go_nengyuanbox() {
        if (StringUtil.checkPackInfo("com.myzyb.appNYB")){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            //知道要跳转应用的包名、类名
            ComponentName componentName = new ComponentName("com.myzyb.appNYB", "com.myzyb.appNYB.ui.activity.prepose.SplashActivity");
            intent.setComponent(componentName);
            startActivity(intent);
        }else {
            getLoginExpirel_Acticity("是否下载能源宝?");

            return;
        }
    }


    public void getLoginExpirel_Acticity(String text) {
        final DialogSureCancel rxDialogSure = new DialogSureCancel(this);//提示弹窗
        rxDialogSure.getTv_title().setText("提示");
        rxDialogSure.setCancelable(true);
        rxDialogSure.getTv_text().setText(text);
        TextView tv_sure = rxDialogSure.getTv_sure();
        tv_sure.setText("确定");

        tv_sure .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                //符合标准
                Uri content_url = Uri.parse("https://a.app.qq.com/o/simple.jsp?pkgname=com.myzyb.appNYB");
                intent.setData(content_url);
                startActivity(intent);

                rxDialogSure.cancel();



            }
        });
        rxDialogSure.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rxDialogSure.cancel();



            }
        });

        rxDialogSure.show();
    }

    private class MyHandler extends Handler {

        private final WeakReference<EventGoodsActivity> mActivity;
        private EventGoodsActivity activity;

        private MyHandler(EventGoodsActivity activity) {

            mActivity = new WeakReference<EventGoodsActivity>(activity);

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


    private void getOrderList() {
        CommonDialog.showProgressDialog(EventGoodsActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(EventGoodsActivity.this, NetUtils.POST, UrlConstant.GETMASTERGOODS, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("师傅可购买商品列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetMasterGoods getOrderListBean = JsonUtil.getSingleBean(response.toString(), GetMasterGoods.class);
                        List<GetMasterGoods.DataBeanX.DataBean> data = getOrderListBean.getData().getData();
                        if (data != null && data.size() > 0) {
                            list.addAll(data);
                            mHandler.sendEmptyMessage(0);
                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(EventGoodsActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("师傅可购买商品列表baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("师傅可购买商品列表baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(EventGoodsActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("师傅可购买商品列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(EventGoodsActivity.this, getResources().getString(R.string.net_error));
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
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


}
