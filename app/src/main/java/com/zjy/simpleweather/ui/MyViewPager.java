package com.zjy.simpleweather.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

/**
 * Created by 极速蜗牛 on 2016/12/25 0025.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getCurrentItem() {
        return POSITION_NONE;
    }
}
