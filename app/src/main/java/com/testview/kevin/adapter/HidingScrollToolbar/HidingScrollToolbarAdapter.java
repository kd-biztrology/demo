package com.testview.kevin.adapter.HidingScrollToolbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testview.kevin.R;
import com.testview.kevin.adapter.HidingScrollToolbar.viewholder.RecyclerHeaderViewHolder;
import com.testview.kevin.adapter.HidingScrollToolbar.viewholder.RecyclerItemViewHolder;

import java.util.List;

/**
 * Created by kevin.
 */
public class HidingScrollToolbarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = HidingScrollToolbarAdapter.class.getSimpleName();

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    private List<String> mItemList;

    public HidingScrollToolbarAdapter(List<String> itemList) {
        mItemList=itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_hiding_scroll_, parent, false);
            return RecyclerItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycle_header, parent, false);
            view.setVisibility(View.GONE);
            return new RecyclerHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            String itemText = mItemList.get(position - 1); // header
            holder.setItemText(itemText);
        }
    }

    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1;//header
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }
}
