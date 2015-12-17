package com.testview.kevin.adapter.HidingScrollToolbar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.testview.kevin.R;

/**
 * Created by kevin.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder /*implements ItemTouchHelperViewHolder */{
    public final TextView textView;

    public RecyclerItemViewHolder(final View parent, TextView itemTextView) {
        super(parent);
        textView = itemTextView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.text);
        return new RecyclerItemViewHolder(parent, itemTextView);
    }

    public void setItemText(CharSequence text) {
        textView.setText(text);
    }

 /*   @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }*/
}
