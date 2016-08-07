package com.example.jun.zhiliaodemo.model;

import java.util.List;

/**
 * Created by Jun on 2016/7/4.
 */
public class DailyListBean {
    /**
     * date : 20160704
     * stories : [{"images":["http://pic4.zhimg.com/aef468b22992d5f685ba05069450438f.jpg"],"type":0,"id":8522328,"ga_prefix":"070416","title":"「作为吉卜力唯一的中国人，能跟宫崎骏说话就赚翻了」"},{"title":"有哪些烂到极致的神作？","ga_prefix":"070415","images":["http://pic4.zhimg.com/db399eea22087d7dbc8e3b9008d147e3.jpg"],"multipic":true,"type":0,"id":8509398},{"images":["http://pic4.zhimg.com/6c01f5e71f50df6c58c7792c203b635b.jpg"],"type":0,"id":8522003,"ga_prefix":"070414","title":"首先，股票分析师是没法准确预测股价和营收的"},{"images":["http://pic3.zhimg.com/b4fe240a1c8b723725ff475d54a76332.jpg"],"type":0,"id":8522396,"ga_prefix":"070413","title":"虐待女儿致死只被判了 4 年，先别急着说「判错了」"},{"title":"大误 · 植物成精后，这个过程相当痛苦","ga_prefix":"070412","images":["http://pic2.zhimg.com/8fa304002bbe2e1b0223d2fdb5ececa5.jpg"],"multipic":true,"type":0,"id":8522282},{"images":["http://pic4.zhimg.com/22191ed6af02b1ea00f40fc9a537abe7.jpg"],"type":0,"id":8518665,"ga_prefix":"070411","title":"「圆」字一开始确实是圆的，后来它方了"},{"images":["http://pic1.zhimg.com/d2df76ad7571ab27812ba9964c876228.jpg"],"type":0,"id":8521976,"ga_prefix":"070410","title":"除了好身材，你健身的目的还能是什么？"},{"images":["http://pic1.zhimg.com/41ff29ea06568410ad98f595d348e08c.jpg"],"type":0,"id":8521237,"ga_prefix":"070409","title":"怎样在饭局上谈论贵州那个山一样大的望远镜？"},{"images":["http://pic1.zhimg.com/51ddef4f09b1b988215ad366d68a29e0.jpg"],"type":0,"id":8520597,"ga_prefix":"070408","title":"为什么美国钟情发展公路，而不是铁路？"},{"images":["http://pic2.zhimg.com/b52afc5d9fc641fb225415d5c588a85d.jpg"],"type":0,"id":8518736,"ga_prefix":"070407","title":"严肃地讨论，为何东京地铁常见跳轨自杀"},{"images":["http://pic4.zhimg.com/2329bf9f57f4e555442b6cec35660bbb.jpg"],"type":0,"id":8521037,"ga_prefix":"070407","title":"给你 1 亿美元，你可以解决地球上一个重大问题吗？"},{"images":["http://pic2.zhimg.com/7aa79413d88829ebc62056fefd0fd449.jpg"],"type":0,"id":8520801,"ga_prefix":"070407","title":"上个好大学 · 各行各业入行前，你有必要了解这些"},{"images":["http://pic1.zhimg.com/b64143cb59f908fc1413889c68e04088.jpg"],"type":0,"id":8520902,"ga_prefix":"070407","title":"读读日报 24 小时热门 TOP 5 · 渍水与口水横流的城市"},{"images":["http://pic3.zhimg.com/56145eef9c9d3a8d85875d6d5e54a666.jpg"],"type":0,"id":8513871,"ga_prefix":"070406","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic1.zhimg.com/9f8f76ba0c9bc20c5c8eb8ec4ba6aa5c.jpg","type":0,"id":8509398,"ga_prefix":"070415","title":"有哪些烂到极致的神作？"},{"image":"http://pic1.zhimg.com/02f96747a442c59f6eb866a74a6edd68.jpg","type":0,"id":8521237,"ga_prefix":"070409","title":"怎样在饭局上谈论贵州那个山一样大的望远镜？"},{"image":"http://pic2.zhimg.com/77ac28dbe8287bcffdd915c3baa66e39.jpg","type":0,"id":8520902,"ga_prefix":"070407","title":"读读日报 24 小时热门 TOP 5 · 渍水与口水横流的城市"},{"image":"http://pic4.zhimg.com/5c41f9cc248ef2e05b156224fe16ba5f.jpg","type":0,"id":8520801,"ga_prefix":"070407","title":"上个好大学 · 各行各业入行前，你有必要了解这些"},{"image":"http://pic2.zhimg.com/fa5dce0f604514a447dd3186956a4289.jpg","type":0,"id":8518463,"ga_prefix":"070317","title":"知乎好问题 · 经常加班至深夜，怎样保持身体健康？"}]
     */

    private String date;
    /**
     * images : ["http://pic4.zhimg.com/aef468b22992d5f685ba05069450438f.jpg"]
     * type : 0
     * id : 8522328
     * ga_prefix : 070416
     * title : 「作为吉卜力唯一的中国人，能跟宫崎骏说话就赚翻了」
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic1.zhimg.com/9f8f76ba0c9bc20c5c8eb8ec4ba6aa5c.jpg
     * type : 0
     * id : 8509398
     * ga_prefix : 070415
     * title : 有哪些烂到极致的神作？
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }
}
