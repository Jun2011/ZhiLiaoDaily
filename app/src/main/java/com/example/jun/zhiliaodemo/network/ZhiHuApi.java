package com.example.jun.zhiliaodemo.network;

import com.example.jun.zhiliaodemo.model.DailyListBean;
import com.example.jun.zhiliaodemo.model.DailyDetailBean;
import com.example.jun.zhiliaodemo.model.ThemeDailyBean;
import com.example.jun.zhiliaodemo.model.LaunchImageBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jun on 2016/7/3.
 */
public interface ZhiHuApi {

    // 获取知乎日报启动图片
    @GET("api/4/start-image/{res}")
    Observable<LaunchImageBean> getLaunchImage(@Path("res") String res);

    // 获取最新知乎日报数据列表
    @GET("api/4/stories/latest")
    Observable<DailyListBean> getLatestNews();

    // 根据时间获取前一天的知乎日报数据列表
    @GET("api/4/stories/before/{date}")
    Observable<DailyListBean> getBeforeNews(@Path("date") String date);

    // 根据id获取知乎日报详细数据
    @GET("api/4/story/{id}")
    Observable<DailyDetailBean> getNewDetail(@Path("id") int id);

    // 根据id获取知乎日报的主题新闻数据
    @GET("api/4/theme/{id}")
    Observable<ThemeDailyBean> getThemeNews(@Path("id") int id);
}
