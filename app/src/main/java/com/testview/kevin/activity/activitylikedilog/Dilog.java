package com.testview.kevin.activity.activitylikedilog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.testview.kevin.R;
import com.testview.kevin.activity.BaseActivity;

/**
 * Created by kevin.
 */
public class Dilog extends BaseActivity implements View.OnClickListener {
    private test mTest;
    private Button mButton;
    private Context mContext = Dilog.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        initView();
        addListener();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.showindow);

    }

    private void addListener() {
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showindow:
                mTest = new test(mContext, mListener);
                mTest.showAtLocation(findViewById(R.id.test_view), Gravity.CENTER, 0, 0);
                break;
            default:
                break;
        }
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
