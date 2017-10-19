package com.ys.pa200.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by admin on 2017/09/28 0028.
 */

public class SDCUtils
{
    /* 判断SD卡是否存在 返回true表示存在 */
    public boolean avaiableMedia() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /* 获取SD卡可用空间 */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSizeLong();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocksLong();
        // 返回SD卡空闲大小
        return freeBlocks * blockSize; //单位Byte
        //return (freeBlocks * blockSize)/1024; //单位KB
        //return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /* 获取SD卡所有空间 */
    public long getSDAllSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSizeLong();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCountLong();
        // 返回SD卡大小
        // return allBlocks * blockSize; //单位Byte
        // return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

}
