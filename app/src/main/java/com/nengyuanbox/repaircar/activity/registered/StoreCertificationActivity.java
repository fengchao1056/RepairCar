package com.nengyuanbox.repaircar.activity.registered;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetServiceBean;
import com.nengyuanbox.repaircar.bean.RepairTypeBean;
import com.nengyuanbox.repaircar.bean.StoreResidenceBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.eventbus.FinishEventBean;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.ClickUtil;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.utils.VersionUtils;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

//  门店认证界面
public class StoreCertificationActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.et_master_name)
    EditText et_master_name;
    @BindView(R.id.et_master_card)
    EditText et_master_card;
    @BindView(R.id.sp_master_experience)//维修经验
            NiceSpinner sp_master_experience;

    @BindView(R.id.et_master_phone)
    EditText et_master_phone; //短信验证码
    @BindView(R.id.et_sms_verification)

    EditText et_sms_verification;
    @BindView(R.id.tv_secority_code) //获取验证码
            TextView tv_secority_code;

    @BindView(R.id.bt_verified)//实名认证
            Button bt_verified;
    @BindView(R.id.rl_master_type)
    FlexboxLayout rl_master_type;


    ArrayList<String> stringArrayList = new ArrayList<>();
    @BindView(R.id.tv_master_address)
    TextView tv_master_address;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    private ArrayList<RepairTypeBean> repairList = new ArrayList<>();
    private TimeCount countDownTimer;
    private String str_repair_type;
    private boolean bl_master_name, bl_master_card, bl_master_phone, bl_master_sm;
    private ArrayList<GetServiceBean.DataBean> spinnerList = new ArrayList<>();
    private String str_experience;
    private String area_x;
    private String area_y;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_certification;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        EventBus.getDefault().register(StoreCertificationActivity.this);
        iv_back.setImageResource(R.drawable.icon_button);
        ll_address.setOnClickListener(this);
        tv_master_address.setOnClickListener(this);
        tv_secority_code.setOnClickListener(this);

        et_master_name.addTextChangedListener(new MyTextWatcher(et_master_name));
        et_master_card.addTextChangedListener(new MyTextWatcher(et_master_card));
        et_master_phone.addTextChangedListener(new MyTextWatcher(et_master_phone));
        et_sms_verification.addTextChangedListener(new MyTextWatcher(et_sms_verification));
        tv_title_name.setText("门店认证");
        getCarname();
        getExperience();

        sp_master_experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_experience = spinnerList.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }






    //    查询门店类型
    private void getExperience() {

        JSONObject jsonObject = new JSONObject();

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETGROUPTYPE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("查询维修经验：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        if (spinnerList != null && spinnerList.size() > 0) {
                            spinnerList.clear();
                        }

                        ArrayList<String> arrayList = new ArrayList<>();
                        GetServiceBean getServiceBean = JsonUtil.getSingleBean(response.toString(), GetServiceBean.class);
                        if (getServiceBean.getData() != null && getServiceBean.getData().size() > 0) {
                            List<GetServiceBean.DataBean> data = getServiceBean.getData();
                            for (int i = 0; i < data.size(); i++) {
                                arrayList.add(data.get(i).getName());
                                spinnerList.addAll(data);
                            }
                            str_experience = spinnerList.get(0).getId();
                            sp_master_experience.attachDataSource(arrayList);
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreCertificationActivity.this);

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
                ViseLog.d(throwable.toString()+responseString);
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    //    查询车辆种类（维修类型）
    private void getCarname() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETCARNAME, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("查询服务类型：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        if (repairList != null && repairList.size() > 0) {
                            repairList.clear();
                        }
                        GetServiceBean getServiceBean = JsonUtil.getSingleBean(response.toString(), GetServiceBean.class);

                        if (getServiceBean.getData() != null && getServiceBean.getData().size() > 0) {
                            List<GetServiceBean.DataBean> data = getServiceBean.getData();
                            for (int i = 0; i < data.size(); i++) {
                                repairList.add(new RepairTypeBean(data.get(i).getName(), data.get(i).getId()));
                            }

                            for (int i = 0; i < repairList.size(); i++) {
                                addChildToFlexboxLayout(repairList.get(i));
                            }
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreCertificationActivity.this);

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
                ViseLog.d(throwable.toString()+responseString);
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }


    //    门店入驻
    private void storeResidence() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gname",et_master_name.getText().toString().trim());
            jsonObject.put("business_phone",et_master_card.getText().toString().trim());
            jsonObject.put("phone",et_master_phone.getText().toString().trim());
            jsonObject.put("smscode",et_sms_verification.getText().toString().trim());
            jsonObject.put("main_items",str_experience);
            jsonObject.put("area_x",area_x);
            jsonObject.put("area_y",area_y);
            StringBuffer stringBuffer=new StringBuffer();
            for (int i = 0; i <stringArrayList.size() ; i++) {
                stringBuffer.append(stringArrayList.get(i));
                if (i!=stringArrayList.size()-1){
                    stringBuffer.append(",");
                }
            }

            jsonObject.put("car_name",stringBuffer.toString());
//            营业执照
//            jsonObject.put("license",stringBuffer.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.STORERESIDENCE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("门店入驻：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        RxToast.normal(response.getString("message"));
                        StoreResidenceBean storeResidenceBean = JsonUtil.getSingleBean(response.toString(), StoreResidenceBean.class);
                        Intent intent = new Intent(StoreCertificationActivity.this, StorBusinessLicenseActivity.class);
                        intent.putExtra("order_sn", storeResidenceBean.getData().getOrder_sn());
                        intent.putExtra("order_money", String.valueOf(storeResidenceBean.getData().getVip_money()));
                        intent.putExtra("order_money_old", String.valueOf(storeResidenceBean.getData().getPrice()));
                        intent.putExtra("gname", et_master_name.getText().toString().trim());
                        startActivity(intent);
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreCertificationActivity.this);

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
                ViseLog.d(throwable.toString()+responseString);
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    //   * 添加孩子到布局中 多选
//     */
    private void addChildToFlexboxLayout(final RepairTypeBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_evaluate, null);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(bean.getType());
        view.setTag(bean);
        if (bean.checked) {
            tv.setBackgroundResource(R.drawable.shape_evaluate_lable);
            tv.setTextColor(getResources().getColor(R.color.txt_3b3b3b));
        } else {
            tv.setBackgroundResource(R.drawable.shape_evaluate_item);
            tv.setTextColor(getResources().getColor(R.color.txt_9D9D9D));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.checked = !bean.checked;
                checkLabeel();
            }
        });
        rl_master_type.addView(view);
    }

    //   * 添加孩子到布局中 多选
//     */

    private void checkLabeel() {
        rl_master_type.removeAllViews();
        for (RepairTypeBean labelBean : repairList) {
            addChildToFlexboxLayout(labelBean);
        }
        stringArrayList.clear();
        for (int x = 0; x < repairList.size(); x++) {
            if (repairList.get(x).checked == true) {
                stringArrayList.add(repairList.get(x).getId());
//                Log.d("Feng","点击的多少"+repairList.get(x).getType());
            } else {
                continue;
            }
        }

    }

    @Override
    protected void onResume() {
//        if (!RxDataTool.isNullString( getIntent().getStringExtra("address"))){
//            tv_master_address.setText(getIntent().getStringExtra("address"));
//        }
//        if (!RxDataTool.isNullString( getIntent().getStringExtra("area_x"))
//                &&!RxDataTool.isNullString( getIntent().getStringExtra("area_y"))){
//            area_x = getIntent().getStringExtra("area_x");
//            area_y = getIntent().getStringExtra("area_y");
//        }
        super.onResume();
    }



    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
//                输入定位地址
            case R.id.tv_master_address:
                intent = new Intent(StoreCertificationActivity.this, StoreMapActivity.class);
                startActivityForResult(intent, 200);
                break;

//              输入定位地址
            case R.id.ll_address:
                intent = new Intent(StoreCertificationActivity.this, StoreMapActivity.class);
                startActivityForResult(intent, 200);
                break;

            case R.id.tv_secority_code:
                if (!RxDataTool.isNullString(et_master_phone.getText().toString().trim())) {

                    sendPhoneCodes();

                } else {
                    RxToast.normal("请输入手机号");
                    return;
                }
                break;
            case R.id.bt_verified:
                if (!ClickUtil.isFastClick()) {
                    if (RxDataTool.isNullString(area_x)||RxDataTool.isNullString(area_y)){
                        RxToast.normal("请选择地址");
                        return;
                    }else {
//                      startIntent(intent);
                        storeResidence();

                    }

                }else {
                    RxToast.normal("不可连续点击哦");
                    return;
                }





                break;
        }
    }

    private void startIntent(Intent  intent) {
        intent = new Intent(this, StorBusinessLicenseActivity.class);
        intent.putExtra("gname",et_master_name.getText().toString().trim());
        intent.putExtra("business_phone",et_master_card.getText().toString().trim());
        intent.putExtra("phone",et_master_phone.getText().toString().trim());
        intent.putExtra("smscode",et_sms_verification.getText().toString().trim());
        intent.putExtra("main_items",str_experience);
        intent.putExtra("area_x",area_x);
        intent.putExtra("area_y",area_y);
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i <stringArrayList.size() ; i++) {
            stringBuffer.append(stringArrayList.get(i));
            if (i!=stringArrayList.size()-1){
                stringBuffer.append(",");
            }
        }

        intent.putExtra("car_name",stringBuffer.toString());
        startActivity(intent);

    }

    private void sendCode() {
        countDownTimer = new TimeCount(60000, 1000);
        countDownTimer.start();
    }


    //    发送验证码
    private void sendPhoneCodes() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", et_master_phone.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.SENDPHONECODES, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("发送验证码：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        RxToast.normal("发送验证码成功");
                        sendCode();
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(StoreCertificationActivity.this);

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
                ViseLog.d(throwable.toString()+responseString);
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(StoreCertificationActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }


    /**
     * 倒计时控制器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (tv_secority_code != null) {
                tv_secority_code.setEnabled(false);
                tv_secority_code.setText( millisUntilFinished / 1000 + "s");
            }
        }

        @Override
        public void onFinish() {
            if (tv_secority_code != null) {
                tv_secority_code.setText(getString(R.string.get_verification_code));
                tv_secority_code.setEnabled(true);
            }
        }
    }

    class MyTextWatcher implements TextWatcher {
        View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }


        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            switch (view.getId()) {
//                     姓名
                case R.id.et_master_name:
                    if (!RxDataTool.isNullString(str.trim())) {
                        bl_master_name = true;
                    } else {
                        bl_master_name = false;
                    }
                    break;

//                    身份证号
                case R.id.et_master_card:
                    if (!RxDataTool.isNullString(str.trim())) {
                        bl_master_card = true;
                    } else {
                        bl_master_card = false;
                    }
                    break;

//                    手机号码
                case R.id.et_master_phone:
                    if (!RxDataTool.isNullString(str.trim())) {
                        bl_master_phone = true;
                    } else {
                        bl_master_phone = false;
                    }
                    break;

//                    短信验证
                case R.id.et_sms_verification:
                    if (!RxDataTool.isNullString(str.trim())) {
                        bl_master_sm = true;
                    } else {
                        bl_master_sm = false;
                    }
                    break;
            }
            if (bl_master_sm && bl_master_name && bl_master_card
                    && bl_master_phone
                    && stringArrayList.size() > 0) {
                bt_verified.setEnabled(true);
            } else {
                bt_verified.setEnabled(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
             if (data!=null){
                 if (!RxDataTool.isNullString( data.getStringExtra("address"))){
                     tv_master_address.setText(data.getStringExtra("address"));
                 }
                 if (!RxDataTool.isNullString( data.getStringExtra("area_x"))
                         &&!RxDataTool.isNullString( data.getStringExtra("area_y"))){
                     area_x = data.getStringExtra("area_x");
                     area_y = data.getStringExtra("area_y");
                 }


             }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.onFinish();
        }

        EventBus.getDefault().unregister(StoreCertificationActivity.this);
    }

    @Subscribe
    public void getFinishBean(FinishEventBean eventBean) {
        if (!RxDataTool.isNullString(eventBean.getName())) {
            if (!RxDataTool.isNullString( eventBean.getAddress())){
                tv_master_address.setText(eventBean.getAddress());
            }
            if (!RxDataTool.isNullString( eventBean.getAreax())
                    &&!RxDataTool.isNullString( eventBean.getAreay())){
                area_x = eventBean.getAreax();
                area_y = eventBean.getAreay();
            }
        }

    }



}
