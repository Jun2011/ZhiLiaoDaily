package com.example.jun.zhiliaodemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.base.BaseActivity;
import com.example.jun.zhiliaodemo.base.BaseApplication;
import com.example.jun.zhiliaodemo.model.DailyDetailBean;
import com.example.jun.zhiliaodemo.network.RetrofitHelper;
import com.example.jun.zhiliaodemo.util.HtmlUtil;
import com.example.jun.zhiliaodemo.util.LogUtil;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 知乎日报新闻详细信息Activity
 */
public class DailyDetailActivity extends BaseActivity {

    @Bind(R.id.detail_image)
    ImageView mDetailImage;
    @Bind(R.id.detail_title)
    TextView mDetailTitle;
    @Bind(R.id.detail_source)
    TextView mDetailSource;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.detail_web)
    WebView mDetailWeb;
    @Bind(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    // 获取布局Id
    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_detail;
    }

    @Override
    public void inintViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            // 获取点击卡片对应Story的Id
            int id = intent.getIntExtra("id", -1);
            // 根据Id获取详细数据
            getNewDetail(id);
        }
    }

    private void getNewDetail(int id) {
        RetrofitHelper.builder().getNewDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyDetailBean>() {
                    @Override
                    public void call(DailyDetailBean dailyDetailBean) {
                        if (dailyDetailBean != null) {
                            // 设置图片
                            String imageUrl = dailyDetailBean.getImage();
                            Glide.with(BaseApplication.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.img_account_avatar)
                                    .into(mDetailImage);
                            // 设置标题
                            String title = dailyDetailBean.getTitle();
                            mDetailTitle.setText(title);
                            // 设置图片的来源
                            String imageSource = dailyDetailBean.getImage_source();
                            mDetailSource.setText(imageSource);
                            // 设置Web内容加载
                            String htmlData = HtmlUtil.createHtmlData(dailyDetailBean);
                            mDetailWeb.loadData(htmlData, "text/html; charset=utf-8", "utf-8");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.e("getNewDetail()失败");
                    }
                });
    }

    @Override
    public void initToolbar() {
        // 设置点击导航图标的监听
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从左边移动进场从右边移动出场
                overridePendingTransition(R.anim.enter_slide_left, R.anim.exist_slide_right);
                // 结束本Activity退回到上一个Activity
                finish();
            }
        });
        // 加载Menu
        mToolbar.inflateMenu(R.menu.other_menu);
        // 设置点击Menu的Item监听
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 编写相应逻辑
                return true;
            }
        });
    }

    // 启动当前Activity
    public static void launch(Context context, int id) {
        Intent intent = new Intent(context, DailyDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
        // 从右边移动进场从左边移动出场
        MainActivity activity = (MainActivity) context;
        activity.overridePendingTransition(R.anim.enter_slide_right, R.anim.exist_slide_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Logger.d("DailyDetailActivity");
    }
}
