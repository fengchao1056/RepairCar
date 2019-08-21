package com.nengyuanbox.repaircar.activity.mine;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetCooperationListBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogSure;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

// 商务合作
public class BusinessCooperationActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.et_master_name)
    EditText et_master_name;
    @BindView(R.id.et_master_phone)
    EditText et_master_phone;
    @BindView(R.id.sp_master_experience)
    NiceSpinner sp_master_experience;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private String str_experience;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_cooperation;
    }

    @Override
    protected void initView() {
        getCooperationList();
    }

    @Override
    protected void setListener() {
        sp_master_experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_experience = stringArrayList.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //    GETPHONE  商务合作内容
    private void getCooperationList() {

        JSONObject jsonObject = new JSONObject();


        NetUtils.newInstance().putReturnJsonNews(BusinessCooperationActivity.this, NetUtils.POST, UrlConstant.GETCOOPERATIONLIST, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("商务合作内容：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetCooperationListBean getCooperationListBean = JsonUtil.getSingleBean(response.toString(), GetCooperationListBean.class);
                        if (getCooperationListBean.getData() != null && getCooperationListBean.getData().size() > 0) {
                            stringArrayList.addAll(getCooperationListBean.getData());
                            str_experience=stringArrayList.get(0);
                            sp_master_experience.attachDataSource(stringArrayList);

                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(BusinessCooperationActivity.this);

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
                CommonDialog.showInfoDialog(BusinessCooperationActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(BusinessCooperationActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    GETPHONE  商务合作内容
    private void setBusinessCooperation() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("name", et_master_name.getText().toString());
            jsonObject.put("phone", et_master_phone.getText().toString());
            jsonObject.put("cooperation", str_experience);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetUtils.newInstance().putReturnJsonNews(BusinessCooperationActivity.this, NetUtils.POST, UrlConstant.SETBUSINESSCOOPERATION, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("商务合作：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        showdialog("提交成功");

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(BusinessCooperationActivity.this);

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
                CommonDialog.showInfoDialog(BusinessCooperationActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(BusinessCooperationActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }
    private void showdialog(final String string) {
        final DialogSure rxDialogSure = new DialogSure(BusinessCooperationActivity.this);//提示弹窗
        rxDialogSure.setTitle("");
        rxDialogSure.setContent(string);
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();

//                    Intent intent = new Intent(StorePaymentActivity.this, RechargeAndGetActivity.class);
//                    startActivity(intent);
                    finish();

            }
        });
        rxDialogSure.show();
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if (!RxDataTool.isEmpty(et_master_name.getText().toString())) {
                    if (!RxDataTool.isEmpty(et_master_phone.getText().toString())&&et_master_phone.getText().length()==11) {
                        if (!RxDataTool.isEmpty(str_experience)) {
                            setBusinessCooperation();
                        } else {
                            RxToast.normal("请选择合作内容");
                            return;
                        }
                    } else {
                        RxToast.normal("请输入手机号");
                        return;
                    }
                } else {
                    RxToast.normal("请输入姓名");
                    return;
                }
                break;
        }
    }
}
