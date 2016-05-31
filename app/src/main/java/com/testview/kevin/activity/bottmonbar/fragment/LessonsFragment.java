package com.testview.kevin.activity.bottmonbar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testview.kevin.R;

/**
 * Created by kevin .
 */
public class LessonsFragment extends Fragment {
	static public final String TAG = "LessonsFragment";
	private View fragmentLayout;
	private TextView mTextView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentLayout = inflater.inflate(R.layout.lesson, container, false);
		setView();
		return fragmentLayout;
	}
	private void setView() {
		mTextView = (TextView) fragmentLayout.findViewById(R.id.textView);
		mTextView.setText(TAG);
	}
}
