package com.testview.kevin.activity.ontoucher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.testview.kevin.R;

/**
 * Created by kevin.
 */
public class OnTouchActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private static final String TAG = OnTouchActivity.class.getSimpleName();

    private TextView textview;
    //声明一个文本标签
    private float fontSize = 30;
    //实例化GestureDetector 接口
    private GestureDetector mGestureDetector;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_touch_activity);
        mGestureDetector = new GestureDetector(this);
        textview = (TextView) findViewById(R.id.textView_helloWorld);
        textview.setTextSize(fontSize);
        //实例化这个文本标签并为其设置最初始的大小

    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
