package com.example.jun.zhiliaodemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.util.ChangeModeUtil;

import butterknife.ButterKnife;

/**
 * 基础Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 在加载布局前调用
        ChangeModeUtil.setDefaultMode();
        super.onCreate(savedInstanceState);
        // 要求窗口无标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置布局
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        // 初始化控件
        inintViews(savedInstanceState);
        // 初始化Toolbar
        initToolbar();
    }

    // 获取布局Id
    public abstract int getLayoutId();
    public abstract void inintViews(Bundle savedInstanceState);
    public abstract void initToolbar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 从左边移动进场从右边移动出场
        overridePendingTransition(R.anim.enter_slide_left, R.anim.exist_slide_right);
    }
}
