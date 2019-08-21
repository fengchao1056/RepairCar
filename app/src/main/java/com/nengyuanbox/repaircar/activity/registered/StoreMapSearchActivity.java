package com.nengyuanbox.repaircar.activity.registered;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.MapNameAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.bean.MapNameBean;
import com.nengyuanbox.repaircar.bean.MapSearchBean;
import com.nengyuanbox.repaircar.config.Constant;
import com.nengyuanbox.repaircar.eventbus.FinishEventBean;
import com.nengyuanbox.repaircar.utils.DividerItemDecorations;
import com.nengyuanbox.repaircar.utils.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

//地图搜索地址界面
public class StoreMapSearchActivity extends BaseActivity {


    private final int RC_SEARCH = 10;
    private final int INTERVAL = 500; //输入时间间隔为300毫秒
    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.et_hunk)
    EditText et_hunk;
    @BindView(R.id.rv_map_list)
    RecyclerView rv_map_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int mPage = 1;
    private ArrayList<MapNameBean> mapNameList = new ArrayList<>();
    private Handler mHandler = new MyHandler(this);
    private MapNameAdapter mapNameAdapter;

    private class MyHandler extends Handler {

        private final WeakReference<StoreMapSearchActivity> mActivity;
        private StoreMapSearchActivity activity;

        private MyHandler(StoreMapSearchActivity activity) {

            mActivity = new WeakReference<StoreMapSearchActivity>(activity);

            this.activity = mActivity.get();
        }

        @Override
        public void handleMessage(Message msg) {

            if (activity == null) {
                return;
            }
            switch (msg.what) {

                case 1:
                    refreshLayout.finishRefresh();

                    break;
                case 2:
                    getUserInfo();
                    refreshLayout.finishLoadMore();
                    break;

                case RC_SEARCH:
                    mPage = 1;
                    mapNameList.clear();
                    getUserInfo();
                    break;

            }

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_map_search;
    }

    @Override
    protected void initView() {

        tv_title_name.setText("搜索地点");
        iv_back.setImageResource(R.drawable.icon_button);
        refresh();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //        地图地址列表适配器
        mapNameAdapter = new MapNameAdapter(R.layout.item_mapname, mapNameList);
        rv_map_list.addItemDecoration(new DividerItemDecorations());
        rv_map_list.setLayoutManager(new LinearLayoutManager(this));
        rv_map_list.setAdapter(mapNameAdapter);
        mapNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(StoreMapSearchActivity.this, StoreCertificationActivity.class);
//                intent.putExtra("address", mapNameList.get(position).getAddr());
//                intent.putExtra("area_x", mapNameList.get(position).getLatitude());
//                intent.putExtra("area_y", mapNameList.get(position).getLongitude());
                startActivity(intent);//返回页面1
                EventBus.getDefault().post(new FinishEventBean("finish", mapNameList.get(position).getAddr(),
                        mapNameList.get(position).getLatitude(),
                        mapNameList.get(position).getLongitude()));
                finish();
            }
        });
        et_hunk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str_hunk = s.toString();

                if (!RxDataTool.isNullString(str_hunk)) {
                    if (mHandler.hasMessages(RC_SEARCH)) {
                        mHandler.removeMessages(RC_SEARCH);
                    }

                    mHandler.sendEmptyMessageDelayed(RC_SEARCH, INTERVAL);

                } else {
                    mapNameList.clear();
                    mapNameAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    //      腾讯地图  地点搜索（search接口）
// 详见 https://lbs.qq.com/webservice_v1/guide-search.html#boundary_detail
    private void getUserInfo() {
        String area_y = getIntent().getStringExtra("area_y");
        String area_x = getIntent().getStringExtra("area_x");
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String encode = URLEncoder.encode(et_hunk.getText().toString(), "UTF-8");

            stringBuffer.append("nearby(")
                    .append(area_x)
                    .append(",")
                    .append(area_y)
                    .append(",")
                    .append("1000")
                    .append(")");

            OkHttpUtils.get()
                    .url("https://apis.map.qq.com/ws/place/v1/search")
                    .addParams("boundary", stringBuffer.toString())//搜索地理范围
                    .addParams("keyword", encode)
                    .addParams("page_index", String.valueOf(mPage))
                    .addParams("key", Constant.TX_KEY)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ViseLog.d("获取错误..");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            ViseLog.d("腾讯地图返回:" + response);
                            MapSearchBean mapSearchBean = JsonUtil.getSingleBean(response, MapSearchBean.class);

                            List<MapSearchBean.DataBean> pois = mapSearchBean.getData();
                            if (pois != null && pois.size() > 0) {
                                for (int i = 0; i < pois.size(); i++) {
                                    mapNameList.add(new MapNameBean(pois.get(i).getTitle(),
                                            pois.get(i).getAddress(),
                                            String.valueOf(pois.get(i).getLocation().getLat()),
                                            String.valueOf(pois.get(i).getLocation().getLat())));
                                }
                                mapNameAdapter.notifyDataSetChanged();
                            }


                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setListener() {

    }

    private void refresh() {
        refreshLayout.setOnRefreshListener(null);
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mHandler.sendEmptyMessageDelayed(1,1000);
//
//            }
//        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mHandler.sendEmptyMessageDelayed(2, 1000);
            }
        });
    }

}
