package com.example.jun.zhiliaodemo.network;

import com.example.jun.zhiliaodemo.model.DailyListBean;
import com.example.jun.zhiliaodemo.model.DailyDetailBean;
import com.example.jun.zhiliaodemo.model.ThemeDailyBean;
import com.example.jun.zhiliaodemo.model.LaunchImageBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Jun on 2016/7/3.
 * 将RetrifitHelper设计成单例模式
 */
public class RetrofitHelper {

    // 知乎URL
    private static final String ZHIHU_URL = "http://news-at.zhihu.com/";

    // 知乎API
    private ZhiHuApi mZhiHuApi;

    // RetrifitHelper的构造方法
    private RetrofitHelper() {

        // 设置Retrofit
        Retrofit mRetrifit = new Retrofit.Builder()
                .baseUrl(ZHIHU_URL) // 基础URL
                .addConverterFactory(GsonConverterFactory.create()) //添加Gson转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加RxJava支持
                .build();

        // 创建ZhiHuApi的代理对象
        mZhiHuApi = mRetrifit.create(ZhiHuApi.class);
    }

    // 返回RetrifitHelper实例
    public static RetrofitHelper builder() {
        return new RetrofitHelper();
    }

    // 根据分辨率获取知乎日报启动图片
    public Observable<LaunchImageBean> getLaunchImage(String res) {
        return mZhiHuApi.getLaunchImage(res);
    }

    // 获取知乎日报最新新闻列表数据
    public Observable<DailyListBean> getLateNews()
    {
        return mZhiHuApi.getLatestNews();
    }

    // 根据Date获取前一天的知乎日报新闻列表数据
    public Observable<DailyListBean> getBeforeNews(String date)
    {
        return mZhiHuApi.getBeforeNews(date);
    }

    // 根据id获取知乎日报新闻详细数据
    public Observable<DailyDetailBean> getNewDetail(int id) {
        return mZhiHuApi.getNewDetail(id);
    }

    // 根据id获取知乎日报的主题新闻数据
    public Observable<ThemeDailyBean> getThemeNews(int id) {
        return mZhiHuApi.getThemeNews(id);
    }
}
