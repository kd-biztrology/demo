package com.testview.kevin.activity.cutoutpic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.testview.kevin.R;
import com.testview.kevin.StartActivity;
import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.utils.Base64Utils;
import com.testview.kevin.utils.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by kevin.
 */
public class CutOutPicActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = CutOutPicActivity.class.getSimpleName();
    private ImageView mImageView;
    private TextView mBtnupdate;
    private TextView mBtnCancel;
    private Context mContext = CutOutPicActivity.this;


    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
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
                takepic(v);
                break;
            case R.id.btn_cancel:
                startActivity(new Intent(CutOutPicActivity.this, StartActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    //拍照的方法
    private void takepic(View v) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
        builder.setItems(new String[]{"相册", "拍照"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showPhotoZoom();
                } else if (which == 1) {
                    showPhotoHraph();
                }
                dialog.dismiss();
            }
        }).show();
    }

    //拍照
    private void showPhotoHraph() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, PHOTOHRAPH);
    }

    //相册选取
    private void showPhotoZoom() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, PHOTOZOOM);
    }

    //处理图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 360);
        intent.putExtra("outputY", 360);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Bitmap photo;
        if (resultCode == NONE) {
            return;
        }
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            //设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }
        if (data == null) {
            return;
        }
        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                photo = extras.getParcelable("data");
                //预览
                mImageView.setImageBitmap(photo);
                if (photo != null) {
                    mBtnupdate.setText("update");
                    mBtnupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 上传裁剪以后的图片
                            Toast.makeText(mContext, "update", Toast.LENGTH_LONG).show();
                            uploadBitmap(photo);
                        }
                    });
                }
            }
        }
    }

    //上传图片
    private void uploadBitmap(final Bitmap photo) {
        if (photo != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            photo.recycle();
            byte[] photodata = baos.toByteArray();
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String Base64 = Base64Utils.base64BitmapToString(photo, 100);
            Logger.t(TAG).d(Base64);
            //update photo to data
        }
    }


}
