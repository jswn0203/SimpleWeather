package com.zjy.simpleweather.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 极速蜗牛 on 2016/12/23 0023.
 */

public class ToastShow {
    private static Toast toast;
    public static void ToastShow(Context context,String text){
        if (toast == null){
            toast =Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
