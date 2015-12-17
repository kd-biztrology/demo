package com.testview.kevin.activity.createqrcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

public class CreateQRCodeUtil {

    private static final int BLACK = 0xff000000;

    public static Bitmap getCreateQrCode(String str, int widthAndHeight, String resname, String tableid) throws WriterException {

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode("http://www.lokobee.com/?table_id=" + str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap qr = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        qr.setPixels(pixels, 0, width, 0, 0, width, height);    //生成的二维码bitmap

        Bitmap newBitmap = Bitmap.createBitmap(640, 760, Bitmap.Config.ARGB_4444);       //生成新的bitmap
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(qr, 52, 100, null);//在 52，100坐标开始插入二维码
        canvas.drawBitmap(newBitmap, 0, 0, null);   //生成最终画布。添加文字和二维码
        TextPaint tabletext = new TextPaint();                  //桌子编号的字体
        Typeface font = Typeface.create(Typeface.SERIF, Typeface.BOLD);     //设置字体
        tabletext.setTypeface(font);
        tabletext.setTextSize(80.0F);
        StaticLayout sl = new StaticLayout(tableid, tabletext, newBitmap.getWidth() - 8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);//写入桌子编号
        canvas.translate(0, 30);
        sl.draw(canvas);
        TextPaint nametest = new TextPaint();  //餐厅名字的字体
        nametest.setAntiAlias(true);
        nametest.setTextSize(50.0F);
        int n = 0;
        if (resname.length() > 26) {
            n = resname.substring(0, 26).indexOf(" ");   //餐厅名字是否有空格
        }
        if (resname.length() <= 26 || n <= 0) {
            StaticLayout ll = new StaticLayout(resname, nametest, newBitmap.getWidth() - 8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);//写入餐厅名字
            canvas.translate(0, 580);
            ll.draw(canvas);
        } else {
            int num = resname.substring(0, 26).lastIndexOf(" ");
            String fname = resname.substring(0, num);        //截取字符串第一部分
            String sname = resname.substring(num + 1);        //截取字符串第二部分
            StaticLayout na = new StaticLayout(fname, nametest, newBitmap.getWidth() - 8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);//写入第一行名字
            canvas.translate(0, 550);
            na.draw(canvas);
            StaticLayout nb = new StaticLayout(sname, nametest, newBitmap.getWidth() - 8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);//写入第二行名字
            canvas.translate(0, 60);
            nb.draw(canvas);
        }
        return newBitmap;
    }


}
