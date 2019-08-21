package com.nengyuanbox.repaircar.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    Activity mActivity;
    private Unbinder mUnbinder;

    public void onAttach(Context paramContext) {
        super.onAttach(paramContext);
        mActivity = ((Activity) paramContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;
    }

    public abstract void init(Bundle savedInstanceState);

    public abstract int getLayoutResId();


    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}