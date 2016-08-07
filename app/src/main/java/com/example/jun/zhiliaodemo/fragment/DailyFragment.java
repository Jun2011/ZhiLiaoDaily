package com.example.jun.zhiliaodemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.activity.MainActivity;
import com.example.jun.zhiliaodemo.adapter.DailyAdapter;
import com.example.jun.zhiliaodemo.base.BaseFragment;
import com.example.jun.zhiliaodemo.db.DataBaseUtil;
import com.example.jun.zhiliaodemo.model.DailyListBean;
import com.example.jun.zhiliaodemo.model.ImageTitleBean;
import com.example.jun.zhiliaodemo.model.StoriesBean;
import com.example.jun.zhiliaodemo.model.TopStoriesBean;
import com.example.jun.zhiliaodemo.network.RetrofitHelper;
import com.example.jun.zhiliaodemo.util.LogUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 知乎日报新闻列表Fragment
 */
public class DailyFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    // RecyclerView的适配器
    private DailyAdapter mDailyAdapter;
    // 线性排列
    private LinearLayoutManager mLinearLayoutManager;
    // 新闻列表的时间
    private String currentDate;
    // 新闻列表数据
    private List<StoriesBean> storiesBeanList;
    // 新闻列表头部数据
    private List<TopStoriesBean> topStoriesBeenList;
    // 头部图片展示数据
    private List<ImageTitleBean> imageTitleBeanList;
    // 是否正在加载
    private boolean loading = false;
    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("DailyFragment");
    }

    // 获取布局Id
    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily;
    }

    // 初始化控件
    @Override
    public void initViews() {
        // 设置Toolbar
        setToolbar();
        // 初始化下拉刷新
        initSwipeRefresh();
        // 初始化数据
        initData();
        // 初始化Adapter
        initAdapter();
        // 初始化RecyclerView
        initRecyclerView();
        // 获取最新知乎日报新闻数据
        getLateDailyList();
    }

    private void setToolbar() {
        MainActivity activity = (MainActivity) getActivity();
        // 设置Toolbar标题
        mToolbar = (Toolbar) activity.findViewById(R.id.t_title);
        mToolbar.setTitle("首页");
        // 显示OptionMenu
        activity.showMenu();
        // 隐藏“添加”
        activity.hideMenuItem(R.id.action_add);
    }

    private void initAdapter() {
        TopStoriesBean topStoriesBean = new TopStoriesBean();
        topStoriesBean.setTitle("防止控指针");
        topStoriesBeenList.add(topStoriesBean);
        mDailyAdapter = new DailyAdapter(getActivity(),
                storiesBeanList, topStoriesBeenList);
    }

    private void initData() {
        storiesBeanList = new ArrayList<>();
        topStoriesBeenList = new ArrayList<>();
        imageTitleBeanList = new ArrayList<>();
    }

    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setDistanceToTriggerSync(300);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉刷新监听器
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // 获取最新知乎日报新闻数据
                        getLateDailyList();
                    }
                });
    }

    private void initRecyclerView() {
        // 设置Adapter
        mRecyclerView.setAdapter(mDailyAdapter);
        // 设置线性排列
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // 使RecyclerView保持固定的大小
        // 该信息被用于自身的优化？？？
        mRecyclerView.setHasFixedSize(true);
        // 添加滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 总的Item个数
                int totalItemCount = mLinearLayoutManager.getItemCount();
                // 最后一个可见Item的位置
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                // 滑到底部时加载更多数据
                if (!loading && lastVisibleItem >= totalItemCount - 1) {
                    // 正在加载
                    loading = true;
                    // 获取更多数据
                    getMoreDaliyList(currentDate);
                }
            }
        });
    }

    // 获取最新的知乎日报新闻列表数据
    private void getLateDailyList() {
        RetrofitHelper.builder().getLateNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyListBean, DailyListBean>() {
                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        // 重新设置相关数据
                        return resetDailyListBean(dailyListBean);
                    }
                })
                .subscribe(new Action1<DailyListBean>() {
                    @Override
                    public void call(DailyListBean dailyListBean) {
                        if (dailyListBean.getStories() == null) {
                            LogUtil.e("getLateDailyList()失败");
                            // 停止下拉刷新
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            LogUtil.i("getLateDailyList()成功");
                            // 设置获取数据的日期
                            currentDate = dailyListBean.getDate();
                            LogUtil.d(currentDate);
                            // 获取列表数据
                            storiesBeanList = dailyListBean.getStories();
                            // 获取头部数据
                            topStoriesBeenList = dailyListBean.getTop_stories();
                            // 更新新闻列表的数据
                            mDailyAdapter.updateData(storiesBeanList, topStoriesBeenList);
                            // 如果加载的数据太少就再加载
                            if (!loading && dailyListBean.getStories().size() < 8) {
                                // 正在加载
                                loading = true;
                                // 获取更多数据
                                getMoreDaliyList(currentDate);
                            }
                            // 结束加载
                            loading = false;
                            // 停止下拉刷新
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("getLateDailyList()失败");
                    }
                });
    }

    // 根据Date获取前一天的知乎日报新闻列表数据
    private void getMoreDaliyList(String data) {
        RetrofitHelper.builder().getBeforeNews(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyListBean, DailyListBean>() {
                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        // 重新设置相关数据
                        return resetDailyListBean(dailyListBean);
                    }
                })
                .subscribe(new Action1<DailyListBean>() {
                    @Override
                    public void call(DailyListBean dailyListBean) {
                        if (dailyListBean.getStories() == null) {
                            LogUtil.e("getMoreDaliyList()失败");
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            LogUtil.i("getMoreDaliyList(String data)成功");
                            // 设置获取数据的日期
                            currentDate = dailyListBean.getDate();
                            LogUtil.d(currentDate);
                            // 获取列表数据
                            storiesBeanList = dailyListBean.getStories();
                            // 添加列表数据
                            mDailyAdapter.addData(storiesBeanList);
                            // 结束加载
                            loading = false;
                            // 停止下拉刷新
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("getMoreDaliyList()失败");
                    }
                });
    }

    // 重新设置相关数据
    private DailyListBean resetDailyListBean(DailyListBean dailyListBean) {
        // 从数据库中查询所有的id数据
        List<String> list = DataBaseUtil.queryId();
        // 从DailyListBean中取出StoriesBean
        List<StoriesBean> storiesBeanList = dailyListBean.getStories();
        // 循环
        for (StoriesBean storiesBean : storiesBeanList) {
            // 设置Date
            storiesBean.setDate(dailyListBean.getDate());
            for (String id2 : list) {
                String id1 = String.valueOf(storiesBean.getId());
                // 根据数据库的信息设置是否已读
                if (id1.equals(id2)) {
                    // 如果id一样就设置为已读
                    storiesBean.setRead(true);
                }
            }
        }
        return dailyListBean;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
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
