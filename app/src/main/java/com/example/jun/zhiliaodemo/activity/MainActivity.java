package com.example.jun.zhiliaodemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.adapter.DrawerAdapter;
import com.example.jun.zhiliaodemo.base.BaseActivity;
import com.example.jun.zhiliaodemo.fragment.DailyFragment;
import com.example.jun.zhiliaodemo.fragment.ThemeFragment;
import com.example.jun.zhiliaodemo.util.ChangeModeUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private DrawerLayout dlLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private Menu mMenu;
    private RecyclerView rvLeftDrawer;
    private DrawerAdapter mDrawerAdapter;
    private List<String> mThemeNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Logger.d("MainActivity");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void inintViews(Bundle savedInstanceState) {
        // 初始化Fragment
        initFragment();
        // 默认刚开始加载“首页”新闻Fragment
        replaceFragment(0);
        // 初始化左边抽屉导航栏
        initLeftDrawer();
    }

    private void initLeftDrawer() {
        dlLayout = (DrawerLayout) findViewById(R.id.dl_layout);
        rvLeftDrawer = (RecyclerView) findViewById(R.id.rv_left_drawer);
        mThemeNameList = new ArrayList<>();
        // 初始化主题新闻名
        initThemeName();
        mDrawerAdapter = new DrawerAdapter(mThemeNameList);
        mDrawerAdapter.setOnItemClickListener(new DrawerAdapter.OnItemClickListener() {

            @Override
            public void onClickItem(View view, int position) {
                // 替换成相应Fragment
                replaceFragment(position);
                // 关闭抽屉导航栏
                dlLayout.closeDrawers();
            }
        });
        rvLeftDrawer.setAdapter(mDrawerAdapter);
        rvLeftDrawer.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initThemeName() {
        mThemeNameList.add("日常心理学");
        mThemeNameList.add("用户推荐日报");
        mThemeNameList.add("电影日报");
        mThemeNameList.add("不许无聊");
        mThemeNameList.add("设计日报");
        mThemeNameList.add("大公司日报");
        mThemeNameList.add("财经日报");
        mThemeNameList.add("互联网安全");
        mThemeNameList.add("开始游戏");
        mThemeNameList.add("音乐日报");
        mThemeNameList.add("动漫日报");
        mThemeNameList.add("体育日报");
    }

    // 初始化Toolbar
    @Override
    public void initToolbar() {
        Toolbar tTitle = (Toolbar) findViewById(R.id.t_title);
        // 将Toolbar加入到ActionBar
        setSupportActionBar(tTitle);
        // 设置点击Toolbar导航图标的监听器
        tTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开抽屉导航栏
                dlLayout.openDrawer(rvLeftDrawer);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.home_menu, menu);
        // 先将“添加”隐藏
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_mode).
                setTitle(ChangeModeUtil.isNightMode ? "白间模式" : "夜间模式");
        return true;
    }

    // 隐藏MenuItem
    public void hideMenuItem(int id) {
        if (mMenu != null) {
            mMenu.findItem(id).setVisible(false);
        }
    }

    // 显示MenuItem
    public void showMenuItem(int id) {
        if (mMenu != null) {
            mMenu.findItem(id).setVisible(true);
        }
    }

    // 隐藏OptionsMenu
    public void hideMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(false);
            }
        }
    }

    // 显示OptionsMenu
    public void showMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 主题模式
            case R.id.action_mode:
                // 模式取反
                ChangeModeUtil.isNightMode = !ChangeModeUtil.isNightMode;
                // 重新创建当前Activity实例
                MainActivity.this.recreate();
                ChangeModeUtil.setLocalMode(MainActivity.this);
                break;
        }
        return true;
    }

    // 初始化Fragment
    private void initFragment() {
        // 添加之前先清空
        fragments.clear();
        // 添加“首页”新闻Fragment
        fragments.add(new DailyFragment());
        // 添加主题新闻Fragment
        // “日常心理”
        ThemeFragment themeFragment1 = new ThemeFragment();
        themeFragment1.setThemeNewsId(13);
        fragments.add(themeFragment1);
        // “用户推荐日报”
        ThemeFragment themeFragment2 = new ThemeFragment();
        themeFragment2.setThemeNewsId(12);
        fragments.add(themeFragment2);
        // “电影日报”
        ThemeFragment themeFragment3 = new ThemeFragment();
        themeFragment3.setThemeNewsId(3);
        fragments.add(themeFragment3);
        // “不许无聊”
        ThemeFragment themeFragment4 = new ThemeFragment();
        themeFragment4.setThemeNewsId(11);
        fragments.add(themeFragment4);
        // “设计日报”
        ThemeFragment themeFragment5 = new ThemeFragment();
        themeFragment5.setThemeNewsId(4);
        fragments.add(themeFragment5);
        // “大公司日报”
        ThemeFragment themeFragment6 = new ThemeFragment();
        themeFragment6.setThemeNewsId(5);
        fragments.add(themeFragment6);
        // “财经日报”
        ThemeFragment themeFragment7 = new ThemeFragment();
        themeFragment7.setThemeNewsId(6);
        fragments.add(themeFragment7);
        // “互联网安全”
        ThemeFragment themeFragment8 = new ThemeFragment();
        themeFragment8.setThemeNewsId(10);
        fragments.add(themeFragment8);
        // “开始游戏”
        ThemeFragment themeFragment9 = new ThemeFragment();
        themeFragment9.setThemeNewsId(2);
        fragments.add(themeFragment9);
        // “音乐日报”
        ThemeFragment themeFragment10 = new ThemeFragment();
        themeFragment10.setThemeNewsId(7);
        fragments.add(themeFragment10);
        // “动漫日报”
        ThemeFragment themeFragment11 = new ThemeFragment();
        themeFragment11.setThemeNewsId(9);
        fragments.add(themeFragment11);
        // “体育日报”
        ThemeFragment themeFragment12 = new ThemeFragment();
        themeFragment12.setThemeNewsId(8);
        fragments.add(themeFragment12);
    }

    // 替换Fragment
    private void replaceFragment(int position) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_layout, fragments.get(position))
                .commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
