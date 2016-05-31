package com.testview.kevin.activity.viewpagerfragemnt;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.testview.kevin.R;
import com.testview.kevin.adapter.AbstractAdapter;
import com.testview.kevin.adapter.Adapter;
import com.testview.kevin.model.Student;
import com.testview.kevin.wights.Decoration.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevin.
 */
public class SimpleFragment extends Fragment {
    private RecyclerView recyclerView;
    private Adapter adapter;
    List<Student> students = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewpager_fragment, container, false);


        adapter = new Adapter(getActivity(), new ItemListener());


        List<String> pics = new ArrayList<>();
        pics.add("http://www.gf-video.com/img/aHR0cDovL2ltZzEudi50bWNkbi5uZXQvaW1nL2gwMDAvaDA2L2ltZzIwMTIwODE3MTQxNDM0MC5qcGc=.jpg");
        pics.add("http://img3.redocn.com/tupian/20141229/sanguozhugeliangrenwuchatu_3710894.jpg");
        pics.add("http://www.hui100.com/cghui/UploadFiles_9737/200907/2009071710334148.jpg");
        pics.add("http://www.mangowed.com/uploads/allimg/140112/1-1401121F220632.jpg");
        pics.add("http://www.hui100.com/cghui/UploadFiles_9737/200907/2009071710343141.jpg");
        pics.add("http://img0.bdstatic.com/img/image/imglogo-r.png");


        for (int i = 0; i < 20; i++) {
            int random = 5;
            Student student = new Student();
            student.setName("三国人物排名" + i);
            student.setAge(23 + (int) (Math.random() * 7));
            student.setPic(pics.get(random));
            students.add(student);
        }

        adapter.bind(students, false);
        adapter.notifyDataSetChanged();
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        //设置item 的分割线
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.setHasFixedSize(true);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        recyclerView.setAdapter(adapter);

        // there is a bug i do not like this
        //onDrag();
        return view;
    }

    // Drag
    private void onDrag() {
        //0则不执行拖动或者滑动
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            /**
             * @param recyclerView
             * @param viewHolder 拖动的ViewHolder
             * @param target 目标位置的ViewHolder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(students, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(students, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                students.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //左右滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
                Log.v("zxy", "onSelectedChanged");
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
                Log.v("zxy", "clearView");
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public final class ItemListener implements AbstractAdapter.ItemClickListener<Adapter.StudentViewHolder> {

        @Override
        public void onClick(Adapter.StudentViewHolder t, View view, int position) {
            if (t.delete == view) {
                adapter.datas.remove(position);
                adapter.notifyItemRemoved(position);
            }
            if (t.pic == view) {
                Toast.makeText(getActivity(), "--" + students.get(position) + "pic", Toast.LENGTH_SHORT).show();
            }
            if (t.age == view) {
                Toast.makeText(getActivity(), "====" + students.get(position).getAge(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onItemClick(Adapter.StudentViewHolder t, int position) {
            Toast.makeText(getActivity(), "[" + t.student.getName() + t.student.getPic() + "]", Toast.LENGTH_SHORT).show();
        }
    }

}
