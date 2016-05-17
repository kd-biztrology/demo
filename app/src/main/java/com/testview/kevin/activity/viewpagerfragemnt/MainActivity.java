package com.testview.kevin.activity.viewpagerfragemnt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.testview.kevin.R;
import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.wights.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ////
    private List<Fragment> mTabContents = new ArrayList<>();

    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("1", "2", "3", "4",
            "5", "6", "7", "8", "9");

    private ViewPagerIndicator mIndicator;
    ///


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         getSupportActionBar().setDisplayShowHomeEnabled(true);


        initView();
        initDatas();
        // 设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        // 设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
        mViewPager.setAdapter(mAdapter);

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.ViewPagerIndicator);
    }

    private void initDatas() {

        for (String ignored : mDatas) {
            SimpleFragment fragment = new SimpleFragment();
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }


}