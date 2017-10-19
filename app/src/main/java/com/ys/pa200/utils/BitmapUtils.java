package com.ys.pa200.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class BitmapUtils {


    /**
     * 得到bitmap的大小
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){     //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * Convert view to bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     * @param bitmap
     * @param context
     * @return
     */
    public static String saveBitmapInExternalStorage(Bitmap bitmap,Context context) {
        try {
            File extStorage = new File(Environment.getExternalStorageDirectory().getPath() +"/wifiprobe");//wifiprobe为SD卡下一个文件夹
            if (!extStorage.exists()) {
                extStorage.mkdirs();
            }
            File file = new File(extStorage,System.currentTimeMillis()+".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);//压缩图片
            fOut.flush();
            fOut.close();
            Toast.makeText(context,  "保存成功", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(context, new String[] {file.getAbsolutePath()},null, null);
            return file.getAbsolutePath();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            Toast.makeText(context,  "保存失败", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}
