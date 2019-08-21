package com.nengyuanbox.repaircar.activity.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.ProblemAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.ProblemListBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.ClickUtil;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSure;
import com.nengyuanbox.repaircar.utils.DividerItemDecorations;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.ContainsEmojiEditText;
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

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

public class CommonProblemActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.et_hunk)
    EditText et_hunk;
    @BindView(R.id.rv_problem_list)
    RecyclerView rv_problem_list;
    @BindView(R.id.et_reason)
    ContainsEmojiEditText et_reason;
    @BindView(R.id.bt_question)
    Button bt_question;
    @BindView(R.id.ll_question)
    LinearLayout ll_question;
    @BindView(R.id.bt_question_activity)
    Button bt_question_activity;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int mPage = 1;
    private ProblemAdapter problemAdapter;
    private ArrayList<ProblemListBean.DataBeanX.DataBean> mProblemList = new ArrayList<>();
    private Handler mHandler = new MyHandler(this);

    private class MyHandler extends Handler {

        private final WeakReference<CommonProblemActivity> mActivity;
        private CommonProblemActivity activity;

        private MyHandler(CommonProblemActivity activity) {

            mActivity = new WeakReference<CommonProblemActivity>(activity);

            this.activity = mActivity.get();
        }

        @Override
        public void handleMessage(Message msg) {

            if (activity == null) {
                return;
            }
            switch (msg.what) {


                case 0:
                    problemAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    mPage = 1;
                    mProblemList.clear();
                    getCommonProblem();
                    refreshLayout.finishRefresh();

                    break;
                case 2:
                    mPage++;
                    getCommonProblem();
                    refreshLayout.finishLoadMore();
                    break;
            }

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initView() {
        refresh();
        getCommonProblem();

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

    private void showdialog(final String title, final String string) {
        final DialogSure rxDialogSure = new DialogSure(CommonProblemActivity.this);//提示弹窗
        rxDialogSure.setTitle(title);
        rxDialogSure.setContent(string);
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();

            }
        });
        rxDialogSure.show();
    }

    @Override
    protected void setListener() {

        problemAdapter = new ProblemAdapter(R.layout.item_getcommonproblem, mProblemList);
        rv_problem_list.setLayoutManager(new LinearLayoutManager(CommonProblemActivity.this));
        rv_problem_list.addItemDecoration(new DividerItemDecorations());
        rv_problem_list.setAdapter(problemAdapter);


        problemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(CommonProblemActivity.this, ProblemDetailActivity.class);
                intent.putExtra("Problemid", mProblemList.get(position).getId());
                startActivity(intent);
            }
        });
        tv_title_name.setText("常见问题");
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setImageResource(R.drawable.icon_button);
        iv_back.setOnClickListener(this);
        bt_question_activity.setOnClickListener(this);
        bt_question.setOnClickListener(this);
//        EditText 软键盘上回车改为搜索
        et_hunk.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_hunk.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        et_hunk.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                    RxToast.normal("搜索");
                    mHandler.sendEmptyMessage(1);

                    return true;
                }
                return false;
            }
        });

        et_hunk.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = et_hunk.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > et_hunk.getWidth()
                        - et_hunk.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
//                    RxToast.normal("搜索1");
                    mHandler.sendEmptyMessage(1);
                    return false;

                }
                return false;
            }
        });
    }
//    常见问题列表
    private void getCommonProblem() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        try {
            if (!RxDataTool.isNullString(et_hunk.getText().toString().trim())) {
                jsonObject.put("keyword", et_hunk.getText().toString());

            }

            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(CommonProblemActivity.this, NetUtils.POST, UrlConstant.GETCOMMONPROBLEM, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("常见问题列表" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        ProblemListBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), ProblemListBean.class);
                        ProblemListBean.DataBeanX data = getOrderListBean.getData();
                        mProblemList.addAll(data.getData());
                        mHandler.sendEmptyMessage(0);


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(CommonProblemActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("常见问题列表baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("常见问题列表baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(CommonProblemActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("常见问题列表baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(CommonProblemActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
//    常见问题提问
    private void addCommonProblem() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        try {
//            标题
            if (!RxDataTool.isNullString(et_reason.getText().toString().trim())) {
                jsonObject.put("title", et_reason.getText().toString());

            }

            jsonObject.put("page", String.valueOf(mPage));
            jsonObject.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(CommonProblemActivity.this, NetUtils.POST, UrlConstant.ADDCOMMONPROBLEM, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("常见问题提问" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        bt_question_activity.setVisibility(View.VISIBLE);

//                        ProblemListBean getOrderListBean = JsonUtil.getSingleBean(response.toString(), ProblemListBean.class);
//                        showdialog("提交成功", "问问娜娜文娜我能问你你往你往");


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(CommonProblemActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("常见问题提问baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("常见问题提问baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(CommonProblemActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("常见问题提问baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(CommonProblemActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                finish();
                break;
            case R.id.bt_question_activity:
                if (!ClickUtil.isFastClick()) {
                    ll_question.setVisibility(View.VISIBLE);
                    bt_question_activity.setVisibility(View.GONE);

                }else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }

                break;

            case R.id.bt_question:

                if (!RxDataTool.isNullString(et_reason.getText().toString().trim())) {
                    addCommonProblem();
                    bt_question_activity.setVisibility(View.GONE);
                } else {
                    RxToast.normal("请输入问题描述");
                    return;
                }

                break;
        }
    }


}
