package com.example.jun.zhiliaodemo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.activity.DailyDetailActivity;
import com.example.jun.zhiliaodemo.custom.ImageSlideshow;
import com.example.jun.zhiliaodemo.db.DataBaseUtil;
import com.example.jun.zhiliaodemo.model.ImageTitleBean;
import com.example.jun.zhiliaodemo.model.StoriesBean;
import com.example.jun.zhiliaodemo.model.TopStoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jun on 2016/7/4.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {

    // 头部展示图片轮播
    private static final int TYPE_HEADER = 0;
    // 需要显示日期
    private static final int TYPE_DATE_STORY = 1;
    // 不用显示日期
    private static final int TYPE_STORY = 2;

    private Context context;
    // 新闻列表数据
    private List<StoriesBean> storiesBeanList;
    // 新闻列表Top数据
    private List<TopStoriesBean> topStoriesBeanList;
    private List<ImageTitleBean> imageTitleBeanList;

    public DailyAdapter(Context context,
                        List<StoriesBean> storiesBeanList,
                        List<TopStoriesBean> topStoriesBeanList) {
        this.context = context;
        this.storiesBeanList = storiesBeanList;
        this.topStoriesBeanList = topStoriesBeanList;
    }

    @Override
    public int getItemCount() {
        // Item数目等于新闻条数加头部
        return storiesBeanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_DATE_STORY;
        } else {
            String currentDate = storiesBeanList.get(position - 1).getDate();
            String beforeDate = storiesBeanList.get(position - 2).getDate();
            int type = currentDate.equals(beforeDate) ? TYPE_STORY : TYPE_DATE_STORY;
            return type;
        }
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        DailyViewHolder dailyViewHolder;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(context).
                        inflate(R.layout.item_daily_header, parent, false);
                dailyViewHolder = new DailyViewHolder(view, viewType);
                return dailyViewHolder;
            case TYPE_DATE_STORY:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_daily_date_story, parent, false);
                dailyViewHolder = new DailyViewHolder(view, viewType);
                return dailyViewHolder;
            case TYPE_STORY:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_daily_story, parent, false);
                dailyViewHolder = new DailyViewHolder(view, viewType);
                return dailyViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final DailyViewHolder holder, final int position) {
        if (holder.viewType == TYPE_HEADER) {
            imageTitleBeanList = new ArrayList<>();
            for (TopStoriesBean topStoriesBean : topStoriesBeanList) {
                ImageTitleBean imageTitleBean = new ImageTitleBean();
                imageTitleBean.setImageUrl(topStoriesBean.getImage());
                imageTitleBean.setTitle(topStoriesBean.getTitle());
                imageTitleBeanList.add(imageTitleBean);
            }
            holder.isGallery.setImageTitleBeanList(imageTitleBeanList);
            holder.isGallery.setDotSize(12);
            holder.isGallery.setDotSpace(12);
            holder.isGallery.setDelay(3000);
            holder.isGallery.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 跳转到详细信息界面
                    DailyDetailActivity.launch(context, topStoriesBeanList.get(position).getId());
                }
            });
            holder.isGallery.commit();
        } else {
            // 设置卡片的点击事件的监听
            setListener(holder, position);
            // 设置日期
            if (holder.viewType == TYPE_DATE_STORY) {
                String date = storiesBeanList.get(position - 1).getDate();
                holder.dateTextView.setText(date);
            }
            // 设置图片
            String storyImage = storiesBeanList.get(position - 1).getImages().get(0);
            Glide.with(context).load(storyImage).into(holder.imageView);
            // 设置标题
            String storyTitle = storiesBeanList.get(position - 1).getTitle();
            holder.titleTextView.setText(storyTitle);
            // 根据是否已读来设置标题颜色
            setTitleColor(holder, position);
        }
    }

    // 根据是否已读来设置标题颜色
    private void setTitleColor(DailyViewHolder holder, int position) {
        StoriesBean storiesBean = storiesBeanList.get(position - 1);
        if (storiesBean.isRead()) {
            // 如果是已读的卡片将标题设置成灰色
            holder.titleTextView.setTextColor(
                    ContextCompat.getColor(context, R.color.zl_gray));
        } else {
            // 如果是未读的卡片将标题设置成黑色
            holder.titleTextView.setTextColor(
                    ContextCompat.getColor(context, R.color.cardTextColor));
        }
    }

    // 设置卡片的点击事件的监听
    private void setListener(final DailyViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取相应位置的StoriesBean
                final StoriesBean storiesBean = storiesBeanList.get(position - 1);
                if (!storiesBean.isRead()) {
                    // 设置为已点阅状态
                    storiesBean.setRead(true);
                    // 将标题设置为已点阅的灰色
                    holder.titleTextView.setTextColor(
                            ContextCompat.getColor(context, R.color.zl_gray));
                    // 向数据库中插入被点阅的Id数据
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取Id
                            int id = storiesBean.getId();
                            // 向数据库中插入Id数据
                            DataBaseUtil.insertId(id + "");
                        }
                    }).start();
                }
                // 跳转到详细信息界面
                DailyDetailActivity.launch(context,
                        storiesBeanList.get(position - 1).getId());
            }
        });
    }

    // ViewHolder
    class DailyViewHolder extends RecyclerView.ViewHolder {

        int viewType;
        // 头部
        ImageSlideshow isGallery;
        // 普通
        CardView cardView;
        TextView dateTextView;
        ImageView imageView;
        TextView titleTextView;

        public DailyViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == TYPE_HEADER) {
                isGallery = (ImageSlideshow) itemView.findViewById(R.id.is_gallery);
            } else {
                // 卡片
                cardView = (CardView) itemView.findViewById(R.id.cv_story_card);
                if (viewType == TYPE_DATE_STORY) {
                    // 日期
                    dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
                }
                // 图片
                imageView = (ImageView) itemView.findViewById(R.id.iv_story_image);
                // 标题
                titleTextView = (TextView) itemView.findViewById(R.id.tv_story_title);
            }
        }
    }


    // 更新数据
    public void updateData(List<StoriesBean> storiesBeanList,
                           List<TopStoriesBean> topStoriesBeanList) {
        this.storiesBeanList = storiesBeanList;
        this.topStoriesBeanList = topStoriesBeanList;
        // 通知数据改变了
        notifyDataSetChanged();
    }

    // 添加数据
    public void addData(List<StoriesBean> storiesBeanList) {
        // 将数据添加进来
        this.storiesBeanList.addAll(storiesBeanList);
        // 通知数据改变了
        notifyDataSetChanged();
    }
}
