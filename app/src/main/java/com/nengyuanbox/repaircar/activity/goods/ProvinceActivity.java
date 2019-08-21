package com.nengyuanbox.repaircar.activity.goods;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.ProvinceAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.ProvinceBean;
import com.nengyuanbox.repaircar.config.UrlConstant;
import com.nengyuanbox.repaircar.eventbus.EventBean;
import com.nengyuanbox.repaircar.net.NetUtils;
import com.nengyuanbox.repaircar.utils.CommonDialog;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.nengyuanbox.repaircar.utils.LoginExpirelUtils;
import com.nengyuanbox.repaircar.utils.StringUtil;
import com.vise.log.ViseLog;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

//选择省份
public class ProvinceActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.province_listView)
    ListView provinceListView;
    private List<ProvinceBean.DataBean> provinceList = new ArrayList<>();
    private ProvinceAdapter myAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_province;
    }

    protected void initView() {
        EventBus.getDefault().register(this);
        tv_title_name.setText("选择省份");
        provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                intent.putExtra("provinceId", provinceList.get(position).getId()+"");
                intent.putExtra("provincename", provinceList.get(position).getName());
                startActivityForResult(intent, 111);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderList();
    }

    private void getOrderList() {
        CommonDialog.showProgressDialog(ProvinceActivity.this);
        JSONObject jsonObject = new JSONObject();



        NetUtils.newInstance().putReturnJsonNews(ProvinceActivity.this, NetUtils.POST, UrlConstant.GETAREA, jsonObject, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    CommonDialog.closeProgressDialog();
                    response = StringUtil.getAES_decode(response);
                    ViseLog.d("已上架商品的详情" + response.toString());

                    if ("2000".equals(response.getString("code"))) {
                        ProvinceBean provinceBean = JsonUtil.getSingleBean(response.toString(), ProvinceBean.class);
                        if (provinceBean != null && provinceBean.getData() != null) {
                            provinceList = provinceBean.getData();
                            flushAddressData();

                        }


                    } else if (Double.parseDouble(response.getString("code")) > 3000) {
                        LoginExpirelUtils.getLoginExpirel(ProvinceActivity.this);

                    } else {
                        RxToast.normal(response.getString("message"));
                    }


                } catch (Exception e) {
                    ViseLog.d("已上架商品的详情baocuo" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo" + throwable.toString() + responseString);
                CommonDialog.showInfoDialog(ProvinceActivity.this, getResources().getString(R.string.net_error));


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonDialog.closeProgressDialog();
                ViseLog.d("已上架商品的详情baocuo2" + throwable.toString());
                CommonDialog.showInfoDialog(ProvinceActivity.this, getResources().getString(R.string.net_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }


    /**
     * 刷新适配器
     */
    private void flushAddressData() {
        if (null == provinceListView) {
            return;
        }
        if (myAdapter == null) {
            myAdapter = new ProvinceAdapter(provinceList, this);
            provinceListView.setAdapter(myAdapter);
        } else {
            myAdapter.setData(provinceList);
        }
    }
    @Subscribe
    public void onEventMainThread(EventBean event) {
        if ("finish".equals(event.getMsg())) {
            finish();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
