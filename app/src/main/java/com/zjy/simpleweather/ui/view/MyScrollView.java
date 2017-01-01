package com.zjy.simpleweather.ui.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by 极速蜗牛 on 2016/12/29 0029.
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    private void init() {
//        viewDragHelper = ViewDragHelper.create(this, callback);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case
//        }
        return super.onTouchEvent(ev);
    }
}
