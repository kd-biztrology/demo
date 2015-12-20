package com.testview.kevin.activity.ondragActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.testview.kevin.R;
import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.adapter.dragadapter.OnDragAdapter;
import com.testview.kevin.adapter.dragadapter.OnDragsItemTouchHelperCallback;
import com.testview.kevin.wights.explosion.ExplosionField;

/**
 * Created by kevin.
 */
public class OnDragActivity extends BaseActivity implements OnDragsItemTouchHelperCallback.OnStartDragListener {
    private static final String TAG = OnDragActivity.class.getSimpleName();
    public OnDragAdapter mDragAdapter;
    public RecyclerView mRecyclerView;
    public ItemTouchHelper mItemTouchHelper;
    private Context mContext = OnDragActivity.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ondrag_activity);
        ////////
        initView();
        //set recyclview
        initRecycleView();

    }

    private void initRecycleView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDragAdapter = new OnDragAdapter(mContext, this);
        mRecyclerView.setAdapter(mDragAdapter);
        mDragAdapter.setOnItemClickLitener(new OnDragAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, position + " delete", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        //// set drag
        initDrag();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initDrag() {
        ItemTouchHelper.Callback callback = new OnDragsItemTouchHelperCallback(mDragAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
