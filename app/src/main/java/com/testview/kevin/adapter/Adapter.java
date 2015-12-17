package com.testview.kevin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.testview.kevin.R;
import com.testview.kevin.model.Student;


public class Adapter extends AbstractAdapter<Student, Adapter.StudentViewHolder> {

    public Adapter(Context mContext, ItemClickListener listener) {
        super(mContext, listener);
    }


    @Override
    public void sort() {

    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(StudentViewHolder studentViewHolder, int i) {
        studentViewHolder.bindData(datas.get(i));
    }


    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_student, null);

        return new StudentViewHolder(view);
    }

    //获取每一个item 的 postion
    @Override
    public long getItemId(int position) {
        return datas.get(position).getId();
    }


    //自定义的ViewHolder，有每个Item的的所有界面元素
    public final class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView name;
        public final TextView age;
        public final ImageView pic;
        public final TextView delete;

        public Student student;

        public StudentViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            delete = (TextView) itemView.findViewById(R.id.delete);

            //设置监听器
            delete.setOnClickListener(this);
            pic.setOnClickListener(this);
            age.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == delete) {
                listener.onClick(this, v, getLayoutPosition());
            } else if (v == itemView) {
                listener.onItemClick(this, getLayoutPosition());
            } else if (v == pic) {
                listener.onClick(this, v, getLayoutPosition());
            } else if (v == name) {
                listener.onClick(this, v, getLayoutPosition());
            } else if (v == age) {
                listener.onClick(this, v, getLayoutPosition());
            }
        }


        public void bindData(Student s) {
            this.student = s;
            name.setText(s.getName());
            age.setText(s.getAge() + "");

            String url = "http://www.biztrology.com/biztrology/Application/Home/Common/Tableware/Chopstick1.png";
            Picasso.with(mContext).load(url).into(pic);
        }

    }


}
