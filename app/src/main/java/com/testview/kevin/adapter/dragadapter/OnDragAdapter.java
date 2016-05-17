package com.testview.kevin.adapter.dragadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testview.kevin.R;
import com.testview.kevin.wights.LabeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevin.
 */
public class OnDragAdapter extends RecyclerView.Adapter<OnDragAdapter.OnDragViewHolder> implements OnDragsItemTouchHelperCallback.ItemTouchHelperAdapter {
    public List<String> datas = new ArrayList<>();
    private final OnDragsItemTouchHelperCallback.OnStartDragListener mDragStartListener;

    private Context mContext;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public OnDragAdapter(Context mContext, OnDragsItemTouchHelperCallback.OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        datas.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.data_items)));
    }

    @Override
    public OnDragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ondrag_item, parent, false);
        OnDragViewHolder itemViewHolder = new OnDragViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final OnDragViewHolder holder, final int position) {
        holder.text.setText(datas.get(position));
        //整个item view 的动作
        holder.handle.setOnTouchListener((v, event) -> {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });
        if (mOnItemClickLitener != null) {
            holder.mLabeView.setOnClickListener(v -> {
                int postion = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.mLabeView, position);
            });
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    ///
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(datas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }


    //////
    public static class OnDragViewHolder extends RecyclerView.ViewHolder implements OnDragsItemTouchHelperCallback.ItemTouchHelperViewHolder {
        public final TextView text;
        public final ImageView handle;
        public final LabeView mLabeView;

        @SuppressLint("WrongViewCast")
        public OnDragViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            handle = (ImageView) itemView.findViewById(R.id.handle);
            mLabeView = (LabeView) itemView.findViewById(R.id.LabeView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(0);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
