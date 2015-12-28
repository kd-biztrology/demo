package com.testview.kevin.activity.base;

/**
 * Created by kevin.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <IT> 数据类型，JavaBean
 * @param <VH> ViewHolder
 */
public abstract class AbstractAdapter<IT, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final ViewHolderClickListener<VH> mViewHolderClickListener;
    protected final Context mContext;

    public void delete(int position) {
        this.datas.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int postion) {
        this.datas.remove(postion);
        notifyItemRemoved(postion);
    }

    public void update(int position, IT item) {
        this.datas.remove(position);
        this.datas.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder点击监听
     *
     * @param <VH>
     */
    public interface ViewHolderClickListener<VH> {
        void onItemClick(VH holder, int postion);

        void onClick(VH holder, View clickItem, int postion);
    }

    public AbstractAdapter(Context context, ViewHolderClickListener<VH> viewHolderClickListener) {
        this.mViewHolderClickListener = viewHolderClickListener;
        this.mContext = context;
    }

   /* public AbstractAdapter(Fragment fragment, ViewHolderClickListener<VH> viewHolderClickListener) {
        this(fragment.getActivity(), viewHolderClickListener);
    }

    public AbstractAdapter(android.support.v4.app.Fragment fragment, ViewHolderClickListener<VH> viewHolderClickListener) {
        this(fragment.getActivity(), viewHolderClickListener);
    }*/


    protected final ArrayList<IT> datas = new ArrayList<>();

    public void reload(final List<IT> datas, boolean append) {
        if (!append) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        sort(this.datas);
    }

    /**
     * 排序，Call after reload(List<IT>,boolean) method
     *
     * @param datas
     */
    protected void sort(ArrayList<IT> datas) {
        //do same thins
    }

    @Override
    public final int getItemCount() {
        return this.datas.size();
    }

    public final IT getItem(int position) {
        return datas.get(position);
    }

   /* public abstract static class BindViewHolder<IT, BIND extends ViewDataBinding> extends RecyclerView.ViewHolder {
        protected final BIND binding;

        public BindViewHolder(BIND t) {
            super(t.getRoot());
            this.binding = t;
        }*/

    public abstract void bind(IT item);
}
