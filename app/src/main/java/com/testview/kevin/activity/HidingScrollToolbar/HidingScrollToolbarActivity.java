package com.testview.kevin.activity.HidingScrollToolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.testview.kevin.R;
import com.testview.kevin.adapter.HidingScrollToolbar.HidingScrollToolbarAdapter;
import com.testview.kevin.adapter.HidingScrollToolbar.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin.
 */
public class HidingScrollToolbarActivity extends AppCompatActivity {
    private static final String TAG = HidingScrollToolbarActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private HidingScrollToolbarAdapter adapter;
    private Toolbar toolbar;
    private Context mContext = HidingScrollToolbarActivity.this;

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            itemList.add("Item " + i);
        }
        return itemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hiding_scroll_activity);
        initView();
        //对recyclerView 的操作
        initrecyclerView();
    }

    private void initrecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HidingScrollToolbarAdapter(createItemList());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
    }

    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
