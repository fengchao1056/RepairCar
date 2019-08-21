package com.nengyuanbox.repaircar.activity.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.adapter.OrderPagerAdapter;
import com.nengyuanbox.repaircar.base.BaseActivity;
import com.nengyuanbox.repaircar.fragment.ExpenditureFragment;
import com.nengyuanbox.repaircar.fragment.FullDetailsFragment;
import com.nengyuanbox.repaircar.fragment.IncomeFragment;
import com.nengyuanbox.repaircar.view.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
//账户明细
public class AccountDetailsActivity extends BaseActivity implements View.OnClickListener {

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
        return R.layout.activity_wallet;
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
        FullDetailsFragment fullDetailsFragment=new FullDetailsFragment();
        IncomeFragment incomeFragment=new IncomeFragment();
        ExpenditureFragment expenditureFragment=new ExpenditureFragment();
        fragmentList.add(fullDetailsFragment);
        fragmentList.add(incomeFragment);
        fragmentList.add(expenditureFragment);
        strings.add("全部");
        strings.add("收入");
        strings.add("支出");

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
//                startActivity(new Intent(OrderActivity.this, MineActivity.class));
                finish();
                break;
        }
    }
}
