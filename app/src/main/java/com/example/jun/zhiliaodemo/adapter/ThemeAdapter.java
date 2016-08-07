package com.example.jun.zhiliaodemo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.model.ThemeDailyBean;
import com.example.jun.zhiliaodemo.model.ThemeEditorsBean;
import com.example.jun.zhiliaodemo.model.ThemeStoriesBean;
import com.example.jun.zhiliaodemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jun on 2016/7/30.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {

    // 头部
    private static final int TYPE_HEADER = 0;
    // 中部
    private static final int TYPE_MIDDLE = 1;
    // 内容
    private static final int TYPE_CONTENT = 2;

    private ThemeDailyBean mThemeDailyBean;
    private List<ThemeStoriesBean> mThemeStoriesBeanList;
    private List<ThemeEditorsBean> mThemeEditorsBeanList;
    private Context mContext;

    public ThemeAdapter(Context context, ThemeDailyBean themeDailyBean) {
        mThemeDailyBean = themeDailyBean;
        mThemeStoriesBeanList = new ArrayList<>();
        mThemeEditorsBeanList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_MIDDLE;
        }
        return TYPE_CONTENT;
    }

    @Override
    public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_theme_header, parent, false);
        } else if (viewType == TYPE_MIDDLE) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_theme_middle, parent, false);
        } else if (viewType == TYPE_CONTENT) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_daily_story, parent, false);
        }
        return new ThemeViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ThemeViewHolder holder, int position) {
        if (holder.type == TYPE_HEADER) {
            Glide.with(mContext).load(mThemeDailyBean.getBackground()).into(holder.ivImage);
            holder.tvTitle.setText(mThemeDailyBean.getDescription());
        } else if (holder.type == TYPE_MIDDLE) {
            holder.rvEditor.setAdapter(new EditorAdapter(mContext, mThemeEditorsBeanList));
            holder.rvEditor.setLayoutManager(
                    new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else if (holder.type == TYPE_CONTENT) {
            holder.cvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "Card", Toast.LENGTH_SHORT);
                }
            });
            List<String> imageUrlList = mThemeStoriesBeanList.get(position - 2).getImages();
            if (imageUrlList != null) {
                Glide.with(mContext).load(imageUrlList.get(0)).into(holder.ivContent);
            } else {
                holder.ivContent.setVisibility(View.GONE);
            }
            holder.tvContent.setText(mThemeStoriesBeanList.get(position - 2).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mThemeStoriesBeanList.size() + 2;
    }

    class ThemeViewHolder extends RecyclerView.ViewHolder {

        int type;

        ImageView ivImage;
        TextView tvTitle;

        RecyclerView rvEditor;

        CardView cvContent;
        ImageView ivContent;
        TextView tvContent;

        public ThemeViewHolder(View itemView, int type) {
            super(itemView);
            this.type = type;
            if (type == TYPE_HEADER) {
                ivImage = (ImageView) itemView.findViewById(R.id.iv_theme_image);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_theme_title);
            } else if (type == TYPE_MIDDLE) {
                rvEditor = (RecyclerView) itemView.findViewById(R.id.rv_theme_editor);
            } else {
                cvContent = (CardView) itemView.findViewById(R.id.cv_story_card);
                ivContent = (ImageView) itemView.findViewById(R.id.iv_story_image);
                tvContent = (TextView) itemView.findViewById(R.id.tv_story_title);
            }
        }
    }

    // 更新数据
    public void updateData(ThemeDailyBean themeDailyBean) {
        mThemeDailyBean = themeDailyBean;
        mThemeEditorsBeanList = themeDailyBean.getEditors();
        mThemeStoriesBeanList = themeDailyBean.getStories();
        // 通知数据改变了
        notifyDataSetChanged();
    }
}
