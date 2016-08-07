package com.example.jun.zhiliaodemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.model.LaunchImageBean;
import com.example.jun.zhiliaodemo.network.RetrofitHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jun on 2016/7/3.
 */
public class LaunchActivity extends Activity {

    // 启动界面图片的分辨率
    private static final String LAUNCHIMAGE_RESOLUTION = "1080*1776";

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        // 获取启动界面的图片
        getLaunchImage();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    // 获取启动界面的图片
    private void getLaunchImage() {
        RetrofitHelper.builder().getLaunchImage(LAUNCHIMAGE_RESOLUTION) // 获取数据
                .subscribeOn(Schedulers.io()) // 被观察者在I/O线程中执行
                .observeOn(AndroidSchedulers.mainThread()) // 订阅者在主线程中执行
                .subscribe(new Action1<LaunchImageBean>() { // 成功获取数据
            @Override
            public void call(LaunchImageBean launchImageBean) {
                String imageUrl = launchImageBean.getImg();
                // 加载获取到的启动界面的图片
                Glide.with(LaunchActivity.this)
                        .load(imageUrl) // 加载图片URL地址
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 磁盘缓存所有版本的图片
                        .error(R.drawable.default_splash) // 加载失败后显示的图片
                        .into(ivImage); // 将图片加载到ImageView上
                // 加载获取到的启动界面的图片上的文本
                tvText.setText(launchImageBean.getText());

                // 启动界面的图片动画
                animatorImage();
            }
        }, new Action1<Throwable>() { // 获取数据失败
            @Override
            public void call(Throwable throwable) {
                // 获取数据失败时，加载一张默认的启动界面的图片
                Glide.with(LaunchActivity.this)
                        .load(R.drawable.default_splash)
                        .into(ivImage);
            }
        });
    }

    // 启动界面的图片动画
    private void animatorImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivImage, "scaleX", 1.0f, 1.13f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivImage, "scaleY", 1.0f, 1.13f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(2000);
        animatorSet.start();
        // 添加动画的监听器
        animatorSet.addListener(new AnimatorListenerAdapter() {
            // 动画结束后跳转到主界面
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                LaunchActivity.this.finish();
            }
        });
    }
}
