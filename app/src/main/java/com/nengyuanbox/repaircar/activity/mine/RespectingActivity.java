package com.nengyuanbox.repaircar.activity.mine;

import android.widget.ImageButton;
import android.widget.TextView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseActivity;

import butterknife.BindView;

//关于 来修车界面
public class RespectingActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageButton ivBack;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_respecting;

    }

    @Override
    protected void initView() {
        tv_title_name.setText("关于");
    }

    @Override
    protected void setListener() {

    }


}
