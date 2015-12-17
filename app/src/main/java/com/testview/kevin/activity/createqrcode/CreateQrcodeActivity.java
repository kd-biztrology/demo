package com.testview.kevin.activity.createqrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.testview.kevin.R;
import com.testview.kevin.activity.BaseActivity;
import com.testview.kevin.utils.Base64Utils;
import com.testview.kevin.utils.logger.Logger;

import java.util.UUID;


public class CreateQrcodeActivity extends BaseActivity implements View.OnClickListener {

    private EditText restanname;
    private EditText table_id;
    private ImageView prview_image;
    protected int mScreenWidth;
    private Button CreateQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_qrcode_activity);
        initView();
        addListener();
    }

    private void initView() {
        restanname = (EditText) this.findViewById(R.id.restanname);
        table_id = (EditText) this.findViewById(R.id.table_id);
        CreateQR = (Button) this.findViewById(R.id.CreateQR);
        prview_image = (ImageView) findViewById(R.id.prview_image);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    private void addListener() {
        CreateQR.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 取得返回信息
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            restanname.setText(scanResult);
        }
    }

    //生成二维码
    public void createQRCode() {
        Bitmap bitmap;
        String uri = UUID.randomUUID().toString();        //二维码的字符串    桌子的唯一标识  随机生成
        String resname = restanname.getText().toString();        //餐厅名字非界面传参
        String tableid = table_id.getText().toString();        //餐厅名字非界面传参
        try {
            bitmap = CreateQRCodeUtil.getCreateQrCode(uri, 512, resname, tableid);
            if (bitmap != null) {
                String base64 = Base64Utils.base64BitmapToString(bitmap, 100);
                Logger.d(base64);
                Bitmap img = Base64Utils.base64StringToBitmap(base64);
                prview_image.setImageBitmap(img);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CreateQR:
                createQRCode();
                break;
            default:
                break;
        }
    }
}
