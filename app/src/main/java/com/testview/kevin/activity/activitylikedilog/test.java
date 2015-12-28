package com.testview.kevin.activity.activitylikedilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.testview.kevin.R;

/**
 * Author by kevin.
 */
public class test extends PopupWindow {
    private Button takePhoto, picZoom, btn_cancel, ok, exit;
    private View mMenuView;

    public test(Context context, View.OnClickListener itemsOnCLick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.test, null);
        takePhoto = (Button) mMenuView.findViewById(R.id.takePhoto);
        picZoom = (Button) mMenuView.findViewById(R.id.picZoom);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        ok = (Button) mMenuView.findViewById(R.id.ok);
        exit = (Button) mMenuView.findViewById(R.id.exit);
        //
        btn_cancel.setOnClickListener(itemsOnCLick);
        picZoom.setOnClickListener(itemsOnCLick);
        takePhoto.setOnClickListener(itemsOnCLick);
        ok.setOnClickListener(itemsOnCLick);
        exit.setOnClickListener(itemsOnCLick);

        //
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        getBackground().setAlpha((int) 1.0f);
        /*ColorDrawable provider = new ColorDrawable(0x80000000);
        this.setBackgroundDrawable(provider);*/

        //
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                      //  dismiss();
                    }
                }
                return true;
            }
        });
    }


}
