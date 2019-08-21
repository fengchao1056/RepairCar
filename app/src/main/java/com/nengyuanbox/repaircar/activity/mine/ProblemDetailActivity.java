package com.nengyuanbox.repaircar.activity.mine;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.ProblemDetailBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

//问题详情界面
public class ProblemDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_text)
    TextView tv_text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_problem_detail;
    }

    @Override
    protected void initView() {
        getCommonProblem();
    }

    private void getCommonProblem() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        try {


            jsonObject.put("Problemid", getIntent().getStringExtra("Problemid"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(ProblemDetailActivity.this, NetUtils.POST, UrlConstant.GETCOMMONPROBLEMINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("常见问题详情" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        ProblemDetailBean  problemDetailBean=JsonUtil.getSingleBean(response.toString(),ProblemDetailBean.class);
                        ProblemDetailBean.DataBean data = problemDetailBean.getData();
                          if (data!=null){
                              tv_text.setText(data.getText());
                              tv_title.setText(data.getTitle());
                          }



                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(ProblemDetailActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("常见问题详情baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("常见问题详情baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(ProblemDetailActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("常见问题详情baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(ProblemDetailActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
    @Override
    protected void setListener() {
        tv_title_name.setText("常见问题");
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setImageResource(R.drawable.icon_button);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
