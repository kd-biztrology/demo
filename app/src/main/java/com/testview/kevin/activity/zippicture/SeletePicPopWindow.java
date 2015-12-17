package com.testview.kevin.activity.zippicture;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
public class SeletePicPopWindow extends PopupWindow {
    private Button takePhoto, picZoom, btn_cancel;
    private View mMenuView;

    public SeletePicPopWindow(Context context, View.OnClickListener itemsOnCLick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_pic_pop, null);
        takePhoto = (Button) mMenuView.findViewById(R.id.takePhoto);
        picZoom = (Button) mMenuView.findViewById(R.id.picZoom);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        //
        btn_cancel.setOnClickListener(itemsOnCLick);
        picZoom.setOnClickListener(itemsOnCLick);
        takePhoto.setOnClickListener(itemsOnCLick);

        //
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        ColorDrawable provider = new ColorDrawable(0x80000000);
        this.setBackgroundDrawable(provider);

        //
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


}
