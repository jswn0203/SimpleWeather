package com.zjy.simpleweather.ui;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.zjy.simpleweather.MainActivity;
import com.zjy.simpleweather.R;

/**
 * Created by 极速蜗牛 on 2016/12/20 0020.
 */

public class SplashActivity extends AppCompatActivity {
    private AnimationSet set;
    private TextView logo_text;
    private int anitorTime = 1000;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        initView();
    }

    private void initView() {
        logo_text = (TextView) findViewById(R.id.logo_text);
        //是否共用一个补间
        set = new AnimationSet(true);
        set.setDuration(anitorTime);
        //动画执行完后保持状态
        set.setFillAfter(true);

        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1);
        scale.setDuration(anitorTime);
        set.addAnimation(scale);


        //平移动画
        TranslateAnimation translate = new TranslateAnimation(0, 0, 0, 200);
        translate.setDuration(anitorTime);
        set.addAnimation(translate);

        logo_text.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.sendEmptyMessageDelayed(1001, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
