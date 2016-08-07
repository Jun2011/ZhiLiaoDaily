package com.example.jun.zhiliaodemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jun.zhiliaodemo.R;
import com.example.jun.zhiliaodemo.model.ThemeEditorsBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主编Adapter
 */
public class EditorAdapter extends RecyclerView.Adapter<EditorAdapter.EditorViewHolder> {

    private List<ThemeEditorsBean> mThemeEditorsBeanList;
    private Context mContext;

    public EditorAdapter(Context context, List<ThemeEditorsBean> mThemeEditorsBeanList) {
        this.mThemeEditorsBeanList = mThemeEditorsBeanList;
        mContext = context;
    }

    @Override
    public EditorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_theme_editor, parent, false);
        return new EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EditorViewHolder holder, int position) {
        Glide.with(mContext).load(mThemeEditorsBeanList.get(position).getAvatar()).
                into(holder.civEditor);
    }

    @Override
    public int getItemCount() {
        return mThemeEditorsBeanList.size();
    }

    class EditorViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civEditor;

        public EditorViewHolder(View itemView) {
            super(itemView);
            civEditor = (CircleImageView) itemView.findViewById(R.id.civ_editor);
        }
    }
}
