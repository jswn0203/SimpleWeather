package com.zjy.simpleweather.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.zjy.simpleweather.entiy.MyContast;

import java.io.File;

/**
 * Created by 极速蜗牛 on 2016/12/26 0026.
 */

public class Tool_apk {

    /**
     * 判断是否有sd卡，缓存的路径
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public  static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            Log.e(MyContast.TAG,Environment.getExternalStorageState()
                    +!Environment.isExternalStorageRemovable()+"");
            cachePath = context.getExternalCacheDir().getPath();
            Log.e(MyContast.TAG,cachePath+"***********");
            cachePath = context.getCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 返回应用的版本信息
     *
     * @param context
     * @return
     */
    public  static int getAppversion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
