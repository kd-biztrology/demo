package com.testview.kevin.activity.cutoutpic;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.testview.kevin.R;
import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.utils.PictureUtils;
import com.testview.kevin.utils.logger.Logger;

/**
 * Created by kevin.
 */
public class CutOutPicActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = CutOutPicActivity.class.getSimpleName();
    private ImageView mImageView;
    private TextView mBtnupdate;
    private TextView mBtnCancel;
    private Context mContext = CutOutPicActivity.this;

    private SeletePicPopWindow mPopWindow;
    public static final int REQUEST_TAKE_PHOTO = 1;//拍照
    public static final int REQUEST_TAKE_PHOTO_ZOOM = 2;//相册
    public Uri photoUir;
    //获取到的图片路径
    private String picPath = "";
    public static final String IMAGE_UNSPECIFIED = "image/*";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cut_out_pic_activity);
        initView();
        addListener();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.img);
        mBtnCancel = (TextView) findViewById(R.id.btn_cancel);
        mBtnupdate = (TextView) findViewById(R.id.btn_update);
    }

    private void addListener() {
        mBtnupdate.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                mPopWindow = new SeletePicPopWindow(mContext, mListener);
                mPopWindow.showAtLocation(findViewById(R.id.update), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            default:
                break;
        }
    }

    //弹出框实现的监听
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopWindow.dismiss();
            switch (v.getId()) {
                case R.id.takePhoto://拍照
                    takePhoto();
                    break;
                case R.id.picZoom://相册
                    takePhotZooom();
                    break;
                default:
                    break;
            }
        }

    };

    //相册
    private void takePhotZooom() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO_ZOOM);
    }

    //拍照
    private void takePhoto() {
        //
        String SDCardstart = Environment.getExternalStorageState();
        if (SDCardstart.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //
            ContentValues values = new ContentValues();
            photoUir = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUir);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        } else {
            Toast.makeText(mContext, "sdk is not found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //点击取消
        if (requestCode == RESULT_CANCELED) {
            return;
        }
        //处理不同的选择方式
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO_ZOOM://相册
                doPhoto(requestCode, data);
                break;
            case REQUEST_TAKE_PHOTO://相机
                doPhoto(requestCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //处理图片压缩
    private void doPhoto(int requestCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO_ZOOM) {
            if (data == null) {
                Toast.makeText(this, "select pic is error", Toast.LENGTH_LONG).show();
                return;
            }
            photoUir = data.getData();
            if (photoUir == null) {
                Toast.makeText(this, "select pic is error", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.MediaColumns.DATA};
        Cursor cursor = mContext.getContentResolver().query(photoUir, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
        //上传
        if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
            //设置预览
            mImageView.setImageBitmap(PictureUtils.getSmallBitmap(picPath));
            //转换图片为Base64
            String base64pic = PictureUtils.bitmapToString(picPath);
            Logger.t(TAG).d(base64pic);

            //其他操作


        } else {
            Toast.makeText(this, "file is error", Toast.LENGTH_LONG).show();
        }

    }
}
