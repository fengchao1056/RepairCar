package com.nengyuanbox.repaircar.activity.mine;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.MainActivity;
import com.nengyuanbox.repaircar.activity.StoreMainActivity;
import com.nengyuanbox.repaircar.activity.goods.EventGoodsActivity;
import com.nengyuanbox.repaircar.activity.registered.MastPaymentActivity;
import com.nengyuanbox.repaircar.activity.registered.MasterCardCertificationActivity;
import com.nengyuanbox.repaircar.activity.registered.MasterCertificationActivity;
import com.nengyuanbox.repaircar.activity.registered.StoreCertificationActivity;
import com.nengyuanbox.repaircar.activity.registered.StoreImgActivity;
import com.nengyuanbox.repaircar.activity.registered.StorePaymentActivity;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.GetUserInfoBean;
import com.nengyuanbox.repaircar.bean.GetUserMasterStoreBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.DialogApply_JoinTheStation;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.SharedPreferenceUtil;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.nengyuanbox.repaircar.view.CircleImageView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MineActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.tv_master_enter)//认领师傅
            TextView tv_master_enter;
    @BindView(R.id.iv_my_scon)
    ImageView iv_my_scon;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_my_phone)
    TextView tv_my_phone;
    @BindView(R.id.tv_is_master)
    TextView tv_is_master;
    @BindView(R.id.iv_modify)
    ImageView iv_modify;
    @BindView(R.id.tv_log_out)
    TextView tv_log_out;
    @BindView(R.id.iv_imagehead)
    CircleImageView iv_imagehead;
    @BindView(R.id.ll_myorder)
    LinearLayout ll_myorder;
    @BindView(R.id.ll_charges)
    LinearLayout ll_charges;
    @BindView(R.id.ll_mywallet)
    LinearLayout ll_mywallet;
    @BindView(R.id.ll_common_problem)
    LinearLayout ll_common_problem;
    @BindView(R.id.ll_suggestions)
    LinearLayout ll_suggestions;
    @BindView(R.id.ll_master_settled)
    LinearLayout ll_master_settled;
    @BindView(R.id.ll_join)
    LinearLayout ll_join;
    @BindView(R.id.ll_goods)
    LinearLayout ll_goods;
    @BindView(R.id.ll_store_enter)
    LinearLayout ll_store_enter;
    @BindView(R.id.ll_business_cooperation)
    LinearLayout ll_business_cooperation;
    @BindView(R.id.ll_my_master)
    LinearLayout ll_my_master;
    @BindView(R.id.ll_my_store)
    LinearLayout ll_my_store;
    @BindView(R.id.ll_store_info)
    LinearLayout ll_store_info;
    @BindView(R.id.tv_store_star)
    TextView tv_store_star;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    private List<String> path = new ArrayList<>();


    private IWXAPI api;
    private String str_is_master;
    private String str_is_store;
    private String str_master_exmine;
    private String str_store_exmine;
    private GetUserInfoBean.DataBean data;
    private String str_role;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        tv_title_name.setText("个人中心");
        iv_back.setImageResource(R.drawable.icon_button);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID_WX);
        Glide.with(context).load(R.mipmap.iv_flash_sale).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_goods);


    }

    //    个人中心用户信息
    private void getUserInfo() {

        CommonDialog.showProgressDialog(MineActivity.this);
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(MineActivity.this, NetUtils.POST, UrlConstant.GETUSERINFO, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);

                    ViseLog.d("个人中心用户信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        GetUserInfoBean singleBean = JsonUtil.getSingleBean(response.toString(), GetUserInfoBean.class);
                        data = singleBean.getData();

//                      角色判定
                        str_role = SharedPreferenceUtil.getString(MineActivity.this, Constant.ROLE, "");


//                        角色师傅时
                        if (str_role.equals("master")) {
                            tv_is_master.setText("认证师傅");
                            tv_master_enter.setText("师傅入驻");
//                            门店信息
                            ll_store_info.setVisibility(View.GONE);
//                            我的师傅
                            ll_my_master.setVisibility(View.GONE);
//                            已入驻门店
//                            ll_my_store.setVisibility(View.VISIBLE);

                            if (data.getIs_store().equals("1")) {
//                                门店入驻
                                ll_store_enter.setVisibility(View.GONE);
                            } else {
                                //                                门店入驻
                                ll_store_enter.setVisibility(View.VISIBLE);
                            }
//

//                            商品列表
                            ll_goods.setVisibility(View.VISIBLE);
//                            收费标准
                            ll_charges.setVisibility(View.VISIBLE);
//                             我的钱包
                            ll_mywallet.setVisibility(View.VISIBLE);
                            getUserMasterStore();

//                           角色是门店时
                        } else if (str_role.equals("store")) {
                            tv_master_enter.setText("认领师傅");
                            tv_is_master.setText("门店用户");
//                             门店信息
                            ll_store_info.setVisibility(View.VISIBLE);
//                             我的师傅
                            ll_my_master.setVisibility(View.VISIBLE);
//                             商品列表
                            ll_goods.setVisibility(View.GONE);
//                             已入驻门店
                            ll_my_store.setVisibility(View.GONE);
//                             我的钱包
                            ll_mywallet.setVisibility(View.GONE);
//                             收费标准
                            ll_charges.setVisibility(View.GONE);
//                             加盟维修站
                            ll_join.setVisibility(View.GONE);
//                           门店入驻
                            ll_store_enter.setVisibility(View.GONE);

                        } else {
//                            tv_is_master.setText("普通用户");
                        }

                        if (data != null) {
                            if (!RxDataTool.isEmpty(data.getMaster_examine())) {
//                                注册的未完成流程判断，在点击 注册 时会有说明
                                str_master_exmine = data.getMaster_examine();
                                str_store_exmine = data.getStore_examine();


                            }

//                            头像
                            if (!RxDataTool.isEmpty(data.getImg_url())) {
                                Glide.with(MineActivity.this)
                                        .load(data.getImg_url())
                                        .placeholder(R.drawable.requestout)
                                        .error(R.drawable.requestout)
                                        .into(iv_imagehead);
                            }


//                           用户名字
                            if (!RxDataTool.isEmpty(data.getName())) {
                                tv_my_name.setText(data.getName());
                            }


//                           用户手机号
                            if (!RxDataTool.isEmpty(data.getPhone())) {
                                tv_my_phone.setText(data.getPhone());
                            }

                            //师傅星级
                            if (!RxDataTool.isEmpty(data.getStar())) {
                                tv_store_star.setText(data.getStar());
                            }

                            //师傅接单数
                            if (!RxDataTool.isEmpty(data.getOrder_num())) {
                                tv_order_num.setText(data.getOrder_num() + "单");
                            }

                            //             是否是师傅
                            if (!RxDataTool.isEmpty(data.getIs_master())) {
                                str_is_master = data.getIs_master();

                            }
                            //             是否是师傅
                            if (!RxDataTool.isEmpty(data.getIs_store())) {
                                str_is_store = data.getIs_store();

                            }


                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MineActivity.this);

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
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //    师傅升级流动维修站
    private void masterCarExamine() {

        CommonDialog.showProgressDialog(MineActivity.this);
        JSONObject jsonObject = new JSONObject();

        NetUtils.newInstance().putReturnJsonNews(MineActivity.this, NetUtils.POST, UrlConstant.MASTERCAREXAMINE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
//                    {"code":2000,"message":"请求成功","data":{"money":"0.00","star":"0","order_num":"0"}}
                    ViseLog.d("师傅升级流动维修站：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        RxToast.normal("提交申请成功");
                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MineActivity.this);

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
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //        个人信息
        getUserInfo();
    }

    //          申请加入流动加盟站
    public void dialog_Apply_JoinTheStation() {
        final DialogApply_JoinTheStation rxDialogSure = new DialogApply_JoinTheStation(this);//提示弹窗
        rxDialogSure.getTv_title().setText("提示");
        rxDialogSure.setCancelable(true);
//        rxDialogSure.getTv_text().setText("");
        TextView tv_sure = rxDialogSure.getTv_sure();
        tv_sure.setText("确定");

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                师傅升级流动维修站
                masterCarExamine();
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


    //    门店信息
    private void getUserMasterStore() {
        CommonDialog.showProgressDialog(this);
        JSONObject jsonObject = new JSONObject();


        netUtils.putReturnJsonNews(context, NetUtils.POST, UrlConstant.GETUSERMASTERSTORE, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("门店信息：" + response.toString());

                    if ("2000".equals(response.getString("code"))) {

                        GetUserMasterStoreBean getStoreInfoBean = JsonUtil.getSingleBean(response.toString(), GetUserMasterStoreBean.class);
                        GetUserMasterStoreBean.DataBean data = getStoreInfoBean.getData();
                        if (data != null) {

                            ll_my_store.setVisibility(View.VISIBLE);

                        } else {
                            ll_my_store.setVisibility(View.GONE);
                        }

                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(MineActivity.this);

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
                ViseLog.d("门店信息：" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("门店信息：" + throwable.toString());
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d(throwable.toString());
                CommonDialog.showInfoDialog(MineActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    @OnClick({R.id.iv_back, R.id.tv_title_name, R.id.tv_my_name, R.id.iv_my_scon, R.id.tv_my_phone, R.id.ll_my_master, R.id.ll_my_store, R.id.ll_store_info, R.id.iv_modify, R.id.tv_log_out, R.id.iv_imagehead, R.id.ll_myorder, R.id.ll_charges, R.id.ll_mywallet, R.id.ll_common_problem, R.id.ll_suggestions, R.id.ll_master_settled, R.id.ll_join, R.id.ll_goods, R.id.iv_goods, R.id.ll_store_enter, R.id.ll_business_cooperation})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
//            返回
            case R.id.iv_back:
                if (str_role.equals("store")) {
                    startActivity(new Intent(MineActivity.this, StoreMainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(MineActivity.this, MainActivity.class));
                    finish();
                }

                break;
//            我的师傅
            case R.id.ll_my_master:
                startActivity(new Intent(MineActivity.this, MyMasterActivity.class));
                break;

            //            已入驻门店
            case R.id.ll_my_store:
                startActivity(new Intent(MineActivity.this, MyStoreInfoActivity.class));
                break;
//             门店信息
            case R.id.ll_store_info:

                startActivity(new Intent(MineActivity.this, StoreInfoActivity.class));
                break;

//              收费标准
            case R.id.ll_charges:
                startActivity(new Intent(MineActivity.this, ChargesActivity.class));
                break;

            case R.id.tv_title_name:
                break;
//                名字
            case R.id.tv_my_name:
                break;
//                二维码
            case R.id.iv_my_scon:
                if (!RxDataTool.isEmpty(str_is_master) && str_is_master.equals("0")) {
                    startActivity(new Intent(MineActivity.this, ORCodeActivity.class));

                } else {
//                    师傅
                    startActivity(new Intent(MineActivity.this, ORCodeMasterActivity.class));

                }
                break;
//                手机号
            case R.id.tv_my_phone:
                break;
//                修改手机号
            case R.id.iv_modify:
                break;
//                退出登陆   切换身份
            case R.id.tv_log_out:

                startActivity(new Intent(MineActivity.this, CheckLoginActivity.class));
                break;
//                头像
            case R.id.iv_imagehead:
//                requestPermissions();

                break;
//                我的订单
            case R.id.ll_myorder:

                ViseLog.d("s是否是师傅-----------" + str_is_master);
                if (!RxDataTool.isEmpty(str_is_master) && str_is_master.equals("1")) {
                    intent = new Intent(MineActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    RxToast.normal("请先认证师傅");
                    return;
                }

                break;
//                我的钱包
            case R.id.ll_mywallet:
                intent = new Intent(MineActivity.this, WalletActivity.class);
                startActivity(intent);

                break;
//                 常见问题
            case R.id.ll_common_problem:
                intent = new Intent(MineActivity.this, CommonProblemActivity.class);
                startActivity(intent);
                break;
//                投诉建议
            case R.id.ll_suggestions:
                break;
//                师傅入驻或 认领师傅
            case R.id.ll_master_settled:
                if (tv_master_enter.getText().toString().equals("师傅入驻")) {
                    if (!RxDataTool.isEmpty(str_is_master) && str_is_master.equals("1")) {
                        RxToast.normal("您已经是师傅，无需注册");
                        return;
                    } else if (str_is_master.equals("0")) {
//                  master_examine : 0 未填信息1 .未支付2.未传身份证  4未填写信息
                        if (str_master_exmine.equals("0") || str_master_exmine.equals("4")) {
                            intent = new Intent(MineActivity.this, MasterCertificationActivity.class);
                        } else if (str_master_exmine.equals("1")) {
                            intent = new Intent(MineActivity.this, MastPaymentActivity.class);
                            if (!RxDataTool.isEmpty(data.getMaster_money())
                                    && !RxDataTool.isEmpty(data.getMaster_vip_money())
                                    && !RxDataTool.isEmpty(data.getMaster_order_sn()))
                                intent.putExtra("order_money", data.getMaster_vip_money());
                            intent.putExtra("order_money_old", data.getMaster_money());
                            intent.putExtra("order_sn", data.getMaster_order_sn());

                        } else if (str_master_exmine.equals("2")) {
                            intent = new Intent(MineActivity.this, MasterCardCertificationActivity.class);
                        }
                        startActivity(intent);
                    }
                } else if (tv_master_enter.getText().toString().equals("认领师傅")) {
                    startActivity(new Intent(MineActivity.this, StoreMainActivity.class));
                    finish();
                }


                break;
//                流动加盟
            case R.id.ll_join:
                dialog_Apply_JoinTheStation();
                break;
//               商品支付商品列表
            case R.id.ll_goods:

                startActivity(new Intent(MineActivity.this, GoodsPayCompleteActivity.class));
//                startActivity(new Intent(MineActivity.this, EventGoodsActivity.class));

                break;
            //               商品列表
            case R.id.iv_goods:
                startActivity(new Intent(MineActivity.this, EventGoodsActivity.class));

                break;

//                门店入驻
            case R.id.ll_store_enter:
//                store_examine : 0  5  填写信息  1.未上传营业执照 2未支付3 未审核
                if (str_is_store.equals("1")) {
                    RxToast.normal("您已经是门店用户，无需注册");
                    return;
                } else if (str_is_store.equals("0")) {
                    ViseLog.d("str_store_exmine:+-----" + str_store_exmine);
//
                    if (str_store_exmine.equals("0") || str_store_exmine.equals("5")) {
                        intent = new Intent(MineActivity.this, StoreCertificationActivity.class);
                    } else if (str_store_exmine.equals("2")) {
                        intent = new Intent(MineActivity.this, StorePaymentActivity.class);
                        if (!RxDataTool.isEmpty(data.getGroup_money())
                                && !RxDataTool.isEmpty(data.getGroup_vip_money())
                                && !RxDataTool.isEmpty(data.getStore_order_sn()))
                            intent.putExtra("order_money", data.getGroup_vip_money());
                        intent.putExtra("order_money_old", data.getGroup_money());
                        intent.putExtra("order_sn", data.getStore_order_sn());

                    } else if (str_store_exmine.equals("1")) {
                        intent = new Intent(MineActivity.this, StoreImgActivity.class);
                        if (!RxDataTool.isEmpty(data.getGroup_money())
                                && !RxDataTool.isEmpty(data.getGroup_vip_money())
                                && !RxDataTool.isEmpty(data.getStore_order_sn()))
                            intent.putExtra("order_money", data.getGroup_vip_money());
                        intent.putExtra("order_money_old", data.getGroup_money());
                        intent.putExtra("order_sn", data.getStore_order_sn());
                    } else if (str_store_exmine.equals("3")) {
                        RxToast.normal("正在审核中，请耐心等候");
                        return;
                    }
                    startActivity(intent);
                }


                break;
//                商务合作
            case R.id.ll_business_cooperation:
                intent = new Intent(MineActivity.this, BusinessCooperationActivity.class);
                startActivity(intent);

                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (str_role.equals("store")) {
                startActivity(new Intent(MineActivity.this, StoreMainActivity.class));
                finish();
            } else {
                startActivity(new Intent(MineActivity.this, MainActivity.class));
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
