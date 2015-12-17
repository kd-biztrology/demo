package com.testview.kevin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin.
 */
public abstract class AbstractAdapter<D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    //数据源
    public List<D> datas = new ArrayList<>();
    protected Context mContext;
    //点击事件的接口
    protected ItemClickListener<VH> listener;


    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * @param mContext
     * @param listener
     */
    public AbstractAdapter(Context mContext, ItemClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    /***
     * 数据排序
     */
    public abstract void sort();

    /***
     * 数据绑定
     *
     * @param lists
     * @param isAppend
     */
    public void bind(List<D> lists, boolean isAppend) {
        if (!isAppend) {
            datas.clear();
        }
        datas.addAll(lists);
    }

    /***
     * 关于点击事件
     */
    public interface ItemClickListener<VH> {
        void onClick(VH t, View view, int position);

        void onItemClick(VH t, int position);
    }
}
