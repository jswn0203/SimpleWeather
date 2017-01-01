package com.zjy.simpleweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zjy.simpleweather.fragment.WeatherFragment;

import java.util.ArrayList;

/**
 * Created by 极速蜗牛 on 2016/12/23 0023.
 */

public class Sp {
    private static String NAME = "config";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void putWeather(Context context, String key,String value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String getWeather(Context context, String key,String value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void remove(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
}
