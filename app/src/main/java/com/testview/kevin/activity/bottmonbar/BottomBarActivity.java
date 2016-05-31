package com.testview.kevin.activity.bottmonbar;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.testview.kevin.R;
import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.activity.bottmonbar.fragment.DiscoverFragment;
import com.testview.kevin.activity.bottmonbar.fragment.LessonsFragment;
import com.testview.kevin.activity.bottmonbar.fragment.MeFragment;
import com.testview.kevin.activity.bottmonbar.fragment.ScheduledFragment;

/**
 * Created by kevin .
 */
public class BottomBarActivity extends BaseActivity {

	private BottomBar mBottomBar;
	private Fragment mContent;

	private LessonsFragment mLessonsFragment;
	private ScheduledFragment mScheduledFragment;
	private DiscoverFragment mDiscoverFragment;
	private MeFragment mMeFragment;

	static final String tags[] = {LessonsFragment.TAG, ScheduledFragment.TAG, DiscoverFragment.TAG, MeFragment.TAG};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_bar_activity);
		intiFragment();
		stateCheck(savedInstanceState);
		initBottomBar(savedInstanceState);
	}
	private void intiFragment() {
		mLessonsFragment = new LessonsFragment();
		mScheduledFragment = new ScheduledFragment();
		mDiscoverFragment = new DiscoverFragment();
		mMeFragment = new MeFragment();
	}
	/**
	 * 状态检测 用于内存不足的时候保证fragment不会重叠
	 *
	 * @param savedInstanceState
	 */
	private void stateCheck(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
			LessonsFragment af = new LessonsFragment();
			mContent = af;
			fts.add(R.id.content_frame, af);
			fts.commit();
		} else {
			LessonsFragment lf = (LessonsFragment) getSupportFragmentManager()
					.findFragmentByTag(tags[0]);
			ScheduledFragment sf = (ScheduledFragment) getSupportFragmentManager()
					.findFragmentByTag(tags[1]);
			DiscoverFragment df = (DiscoverFragment) getSupportFragmentManager()
					.findFragmentByTag(tags[2]);
			MeFragment mf = (MeFragment) getSupportFragmentManager()
					.findFragmentByTag(tags[3]);
			getSupportFragmentManager().beginTransaction().show(lf).hide(sf).hide(df)
					.hide(mf).commit();
		}
	}
	private void initBottomBar(Bundle savedInstanceState) {
		mBottomBar = BottomBar.attach(this, savedInstanceState);

		mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
			@Override
			public void onMenuTabSelected(@IdRes int menuItemId) {
				switch (menuItemId){
					case R.id.bb_menu_lessons:
						switchContent(mContent, mLessonsFragment, 0);
						break;
					case R.id.bb_menu_schedule:
						switchContent(mContent, mScheduledFragment, 1);
						break;
					case R.id.bb_menu_discover:
						switchContent(mContent, mDiscoverFragment, 2);
						break;
					case R.id.bb_menu_me:
						switchContent(mContent, mMeFragment, 3);
						break;

					default:
						switchContent(mContent, mLessonsFragment, 0);
				}
			}

			@Override
			public void onMenuTabReSelected(@IdRes int menuItemId) {

			}
		});

		// Setting colors for different tabs when there's more than three of them.
		// You can set colors for tabs in three different ways as shown below.
		mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
		mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorPrimary));
		mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.colorPrimary));
		mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.colorPrimary));
	}
	/**
	 * fragment 切换
	 *
	 * @param from
	 * @param to
	 */
	public void switchContent(Fragment from, Fragment to, int position) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(from)
						.add(R.id.content_frame, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Necessary to restore the BottomBar's state, otherwise we would
		// lose the current tab on orientation change.
		//stateCheck(outState);
		if(mBottomBar != null){
			mBottomBar.onSaveInstanceState(outState);
		}
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		mContent = null;
		mLessonsFragment = null;
		mScheduledFragment = null;
		mDiscoverFragment = null;
		mMeFragment = null;
	}

}
