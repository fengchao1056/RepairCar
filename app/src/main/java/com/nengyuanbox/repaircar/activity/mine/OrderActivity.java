package com.nengyuanbox.repaircar.activity.mine;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.MainActivity;
import com.nengyuanbox.repaircar.adapter.OrderPagerAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.fragment.CancelledFragment;
import com.nengyuanbox.repaircar.fragment.CompletedFragment;
import com.nengyuanbox.repaircar.fragment.ToProcessedFragment;
import com.nengyuanbox.repaircar.view.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//订单界面
public class OrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageButton iv_back;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tl_layout)
    TabLayout tl_layout;
    @BindView(R.id.vp_pager)
    NonSwipeableViewPager vp_pager;
    private List<Fragment> fragmentList=new ArrayList<>();
    private ArrayList<String> strings=new ArrayList<>();

    @Override
    protected int getLayoutId() {

        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        getFragment();
        //适配器
        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager(), fragmentList, strings);
        vp_pager.setAdapter(orderPagerAdapter);
        tl_layout.addTab(tl_layout.newTab().setTag(strings.get(0)));
        tl_layout.addTab(tl_layout.newTab().setTag(strings.get(1)));
        tl_layout.addTab(tl_layout.newTab().setTag(strings.get(2)));
        tl_layout.setupWithViewPager(vp_pager);
    }

    private void getFragment() {
        ToProcessedFragment toProcessedFragment=new ToProcessedFragment();
        CompletedFragment completedFragment=new CompletedFragment();
        CancelledFragment cancelledFragment=new CancelledFragment();
        fragmentList.add(toProcessedFragment);
        fragmentList.add(completedFragment);
        fragmentList.add(cancelledFragment);
        strings.add("待处理");
        strings.add("已完成");
        strings.add("已取消");

    }

    @Override
    protected void setListener() {
        tv_title_name.setText("订单");
        iv_back.setImageResource(R.drawable.icon_button);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            返回
            case R.id.iv_back:
                startActivity(new Intent(OrderActivity.this, MainActivity.class));
                finish();
                break;

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

//            startActivity(new Intent(OrderActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
