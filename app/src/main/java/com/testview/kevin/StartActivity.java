package com.testview.kevin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.activity.HidingScrollToolbar.HidingScrollToolbarActivity;
import com.testview.kevin.activity.activitylikedilog.Dilog;
import com.testview.kevin.activity.ondragActivity.OnDragActivity;
import com.testview.kevin.activity.viewpagerfragemnt.MainActivity;
import com.testview.kevin.activity.createqrcode.CreateQrcodeActivity;
import com.testview.kevin.activity.cutoutpic.CutOutPicActivity;

/**
 * Created by kevin.
 */
public class StartActivity extends BaseActivity implements View.OnClickListener {
    private Button viewpager;
    private Button ondrag;
    private Button ondrags;
    private Button createqr;
    private Button takepic;
    private Button pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        initview();
        addlistener();
    }

    private void initview() {
        viewpager = (Button) findViewById(R.id.viewpager);
        ondrag = (Button) findViewById(R.id.ondrag);
        ondrags = (Button) findViewById(R.id.ondrags);
        createqr = (Button) findViewById(R.id.createqr);
        takepic = (Button) findViewById(R.id.takepic);
        pic = (Button) findViewById(R.id.pic);

    }

    private void addlistener() {
        viewpager.setOnClickListener(this);
        ondrag.setOnClickListener(this);
        ondrags.setOnClickListener(this);
        createqr.setOnClickListener(this);
        takepic.setOnClickListener(this);
        pic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager:
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                break;
            case R.id.ondrag:
                startActivity(new Intent(StartActivity.this, HidingScrollToolbarActivity.class));
                break;
            case R.id.ondrags:
                startActivity(new Intent(StartActivity.this, OnDragActivity.class));
                break;
            case R.id.createqr:
                startActivity(new Intent(StartActivity.this, CreateQrcodeActivity.class));
                break;
            case R.id.takepic:
                startActivity(new Intent(StartActivity.this, CutOutPicActivity.class));
                break;
            case R.id.pic:
                startActivity(new Intent(StartActivity.this, Dilog.class));
                break;
            default:
                break;
        }
    }
}
