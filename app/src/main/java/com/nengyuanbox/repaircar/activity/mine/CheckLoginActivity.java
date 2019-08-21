package com.nengyuanbox.repaircar.activity.mine;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetUserInfoBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.CircleImageView;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//选择登陆界面
public class CheckLoginActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.iv_imagehead)
    CircleImageView iv_imagehead;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.tv_my_phone)
    TextView tv_my_phone;
    @BindView(R.id.rb_master)
    RadioButton rb_master;
    @BindView(R.id.rb_store)
    RadioButton rb_store;
    @BindView(R.id.rb_people)
    RadioButton rb_people;
    @BindView(R.id.rg_rol)
    RadioGroup rg_rol;
    @BindView(R.id.btn_net_step)
    Button btn_net_step;
    private GetUserInfoBean.DataBean data;
    private String role;
    private String str_role;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_check_login);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_login;
    }

    @Override
    protected void initView() {
        tv_title_name.setText("登陆");
        str_role = SharedPreferenceUtil.getString(CheckLoginActivity.this, Constant.ROLE, "");
        getUserInfo();


         if (str_role.equals("master")){
             rb_master.setChecked(true);
         }else   if (str_role.equals("store")){
             rb_store.setChecked(true);
         }
        rb_master.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                     role = "master";
                     SharedPreferenceUtil.saveString(CheckLoginActivity.this, Constant.ROLE, "master");
                 }else {

                 }
            }
        });
        rb_store.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                     role = "store";
                     SharedPreferenceUtil.saveString(CheckLoginActivity.this, Constant.ROLE, "store");
                 }else {

                 }
            }
        });
    }

    private void getUserInfo() {

        CommonDialog.showProgressDialog(CheckLoginActivity.this);
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(CheckLoginActivity.this, NetUtils.POST, UrlConstant.GETUSERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("个人中心用户信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetUserInfoBean singleBean = JsonUtil.getSingleBean(response.toString(), GetUserInfoBean.class);
                        data = singleBean.getData();
                        if (data != null) {
                            if (!RxDataTool.isEmpty(data.getMaster_examine())) {
//                                注册的未完成流程判断，在点击 注册 时会有说明
                                String str_master_exmine = data.getMaster_examine();
                                String str_store_exmine = data.getStore_examine();


                            }

//                            头像
                            if (!RxDataTool.isEmpty(data.getImg_url())) {
                                Glide.with(CheckLoginActivity.this)
                                        .load(data.getImg_url())
                                        .placeholder(R.drawable.requestout)
                                        .error(R.drawable.requestout)
                                        .into(iv_imagehead);
                            }
//                           是否为师傅
                            if (!RxDataTool.isEmpty(data.getIs_master())) {

                                String str_is_master = data.getIs_master();
                                if (str_is_master.equals("1")){
                                    rb_master.setEnabled(true);
                                }else {
                                    rb_master.setEnabled(false);

                                }
                                //                           是否为门店
                                if (!RxDataTool.isEmpty(data.getIs_store())) {

                                    String str_is_store = data.getIs_store();
                                    if (str_is_store.equals("1")){
                                        rb_store.setEnabled(true);
                                    }else {
                                        rb_store.setEnabled(false);
                                    }
                                }
                            }


//                           用户名字
                            if (!RxDataTool.isEmpty(data.getName())) {
                                tv_my_name.setText(data.getName());
                            }

                            //                           用户名字
                            if (!RxDataTool.isEmpty(data.getPhone())) {
                                tv_my_phone.setText(data.getPhone());
                            }


                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(CheckLoginActivity.this);

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
                CommonDialog.showInfoDialog(CheckLoginActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(CheckLoginActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    protected void setListener() {

    }




    @OnClick({R.id.iv_back, R.id.btn_net_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_net_step:
                finish();

                break;
        }
    }
}
