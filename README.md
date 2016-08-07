# 一个模仿知乎日报的App

### 展示
![](https://github.com/Jun2011/ZhiLiaoDemo/raw/master/images/app_show_1.gif)
![](https://github.com/Jun2011/ZhiLiaoDemo/raw/master/images/app_show_2.gif)
![](https://github.com/Jun2011/ZhiLiaoDemo/raw/master/images/app_show_3.gif)
![](https://github.com/Jun2011/ZhiLiaoDemo/raw/master/images/app_show_4.gif)

### 介绍
　　该App的数据完全来自于[知乎日报API](https://github.com/iKrelve/KuaiHu/blob/master/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5API.md)，在其中应用到了一些现在比较流行的框架。

### 功能
* 启动动画及启动图片的获取
* 文章类型的展示与缓存
* 文章列表的展示与缓存
* 文章内容的展示与缓存
* 今日热点的图片轮播
* 下拉刷新数据
* 滑动到底部自动加载更多数据
* 标记已读文章
* 自动缓存文章
* 夜间模式

### 技术
* 使用CardView制作文章列表卡片，使用RecyclerView展示文章列表；
* Android Design Support Library的使用展示文章详细信息；
* 使用WebView展示解析出来的HTML数据；
* 使用Retrofit进行网络请求获取最新数据、过往数据；
* 使用SwipeRefreshLayout实现下拉刷新数据；
* 自定义UI控件实现图片轮播效果；
* 使用Glide图片加载库加载图片；
* 使用SQLite数据库存储已读文章信息等数据；
* 使用DrawerLayout实现左侧的抽屉导航栏，但没有直接使用NavigationView而是使用RecyclerView来定制抽屉导航栏。

### 依赖的开源项目
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Logger](https://github.com/orhanobut/logger)
* [Okhttp](https://github.com/square/okhttp)
* [Retrofit](https://github.com/square/retrofit)
* [Glide](https://github.com/bumptech/glide)
* [Gson](https://github.com/google/gson)
* [CircleImageView](https://github.com/hdodenhof/CircleImageView)

### 关于我
[李俊的博客](http://www.jianshu.com/users/32702f750012/latest_articles)
