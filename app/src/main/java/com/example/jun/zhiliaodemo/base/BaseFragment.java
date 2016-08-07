package com.example.jun.zhiliaodemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 基础Fragment
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        // 加载布局
        return inflater.inflate(getLayoutId(), container, false);
    }

    // 获取布局Id
    public abstract int getLayoutId();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ButterKnife绑定
        ButterKnife.bind(this,view);
        // 初始化控件
        initViews();
    }

    public abstract void initViews();
}
