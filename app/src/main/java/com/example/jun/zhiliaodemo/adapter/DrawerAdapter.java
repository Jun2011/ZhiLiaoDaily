package com.example.jun.zhiliaodemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jun.zhiliaodemo.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jun on 2016/8/2.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    // 头部
    private static final int TYPE_HEADER = 0;
    // 首页
    private static final int TYPE_HOME = 1;
    // 主题
    private static final int TYPE_THEME = 2;

    private List<String> mThemeNameList;
    private Context mContext;
    // 被选中的位置
    private int selectedPosition = 1;

    public DrawerAdapter(List<String> mThemeNameList) {
        this.mThemeNameList = mThemeNameList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_HOME;
        }
        return TYPE_THEME;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = null;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.drawer_header, parent, false);
        } else if (viewType == TYPE_HOME) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.drawer_home, parent, false);
        } else if (viewType == TYPE_THEME) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.drawer_theme, parent, false);
        }
        return new DrawerViewHolder(view, viewType);
    }

    // 创建监听器接口
    public interface OnItemClickListener {
        void onClickItem(View view, int position);
    }

    // 声明监听器
    private OnItemClickListener mOnItemClickListener;

    // 提供设置监听器的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final DrawerViewHolder holder, final int position) {
        if (holder.type == TYPE_HEADER) {
            holder.civHead.setImageResource(R.drawable.img_account_avatar);
            holder.tvLogin.setText("请登录");
            holder.tvCollect.setText("我的收藏");
            holder.tvDownload.setText("离线下载");
        } else {
            if (holder.type == TYPE_HOME) {
                holder.ivHome.setImageResource(R.mipmap.ic_home);
                holder.tvHome.setText("首页");
            } else if (holder.type == TYPE_THEME) {
                holder.tvTheme.setText(mThemeNameList.get(position - 2));
                holder.ivTheme.setImageResource(R.mipmap.ic_add_2);
            }
            // 判断是否是被选中的位置，如果是就设置为选中状态否则为未选中状态。
            if (position == selectedPosition) {
                holder.item.setSelected(true);
            } else {
                holder.item.setSelected(false);
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 设置选中的位置
                    selectedPosition = position;
                    // 通知数据的设置发生改变
                    notifyDataSetChanged();
                    mOnItemClickListener.onClickItem(v, position - 1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mThemeNameList.size() + 2;
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {

        View item;
        int type;

        CircleImageView civHead;
        TextView tvLogin;
        TextView tvCollect;
        TextView tvDownload;

        ImageView ivHome;
        TextView tvHome;

        TextView tvTheme;
        ImageView ivTheme;

        public DrawerViewHolder(View itemView, int viewType) {
            super(itemView);
            item = itemView;
            type = viewType;
            if (viewType == TYPE_HEADER) {
                civHead = (CircleImageView) itemView.findViewById(R.id.civ_head);
                tvLogin = (TextView) itemView.findViewById(R.id.tv_login);
                tvCollect = (TextView) itemView.findViewById(R.id.tv_collect);
                tvDownload = (TextView) itemView.findViewById(R.id.tv_download);
            } else if (viewType == TYPE_HOME) {
                ivHome = (ImageView) itemView.findViewById(R.id.iv_home);
                tvHome = (TextView) itemView.findViewById(R.id.tv_home);
            } else if (viewType == TYPE_THEME) {
                tvTheme = (TextView) itemView.findViewById(R.id.tv_theme);
                ivTheme = (ImageView) itemView.findViewById(R.id.iv_theme);
            }
        }
    }
}
