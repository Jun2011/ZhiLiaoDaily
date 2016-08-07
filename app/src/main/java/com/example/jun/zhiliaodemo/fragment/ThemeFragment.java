package com.example.jun.zhiliaodemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.activity.MainActivity;
import com.example.jun.zhiliaodemo.adapter.ThemeAdapter;
import com.example.jun.zhiliaodemo.base.BaseFragment;
import com.example.jun.zhiliaodemo.model.ThemeDailyBean;
import com.example.jun.zhiliaodemo.network.RetrofitHelper;
import com.example.jun.zhiliaodemo.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 主题新闻Fragment
 */
public class ThemeFragment extends BaseFragment {

    @Bind(R.id.rv_content)
    RecyclerView rvContent;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private int themeNewsId;
    private ThemeDailyBean mThemeDailyBean;
    private ThemeAdapter mThemeAdapter;
    private Toolbar mToolbar;
    private String toolbarTitle;

    // 根据传入的id获取相应的主题新闻Fragment实例
    public void setThemeNewsId(int themeNewsId) {
        this.themeNewsId = themeNewsId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_theme;
    }

    @Override
    public void initViews() {
        mThemeDailyBean = new ThemeDailyBean();
        // 获取新闻数据
        getThemeNews(themeNewsId);
        // 设置下拉刷新
        setSwipeRefresh();
        // 设置RecyclerView
        setRecyclerView();
    }

    private void setToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        // 设置Toolbar标题
        mToolbar = (Toolbar) activity.findViewById(R.id.t_title);
        toolbarTitle = mThemeDailyBean.getName();
        mToolbar.setTitle(toolbarTitle);
        // 隐藏OptionMenu
        activity.hideMenu();
        // 显示“添加”
        activity.showMenuItem(R.id.action_add);
    }

    private void setRecyclerView() {
        mThemeAdapter = new ThemeAdapter(getActivity(), mThemeDailyBean);
        rvContent.setAdapter(mThemeAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setSwipeRefresh() {
        // 设置下拉刷新的属性
        srlRefresh.setColorSchemeResources(R.color.colorPrimary);
        srlRefresh.setDistanceToTriggerSync(300);
        srlRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        srlRefresh.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉刷新的监听
        srlRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // 获取最新主题新闻数据
                        getThemeNews(themeNewsId);
                    }
                });
    }

    private void getThemeNews(int themeNewsId) {
        RetrofitHelper.builder().getThemeNews(themeNewsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ThemeDailyBean>() {
                    @Override
                    public void call(ThemeDailyBean themeDailyBean) {
                        if (themeDailyBean == null) {
                            LogUtil.e("getThemeNews()获取失败");
                            // 停止下拉刷新
                            srlRefresh.setRefreshing(false);
                        } else {
                            LogUtil.i("getThemeNews()获取成功");
                            // 更新数据
                            mThemeDailyBean = themeDailyBean;
                            mThemeAdapter.updateData(mThemeDailyBean);
                            // 设置Toolbar
                            setToolbar();
                            // 停止下拉刷新
                            srlRefresh.setRefreshing(false);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("getThemeNews()抛出异常");
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
