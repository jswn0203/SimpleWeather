package com.zjy.simpleweather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zjy.simpleweather.R;
import com.zjy.simpleweather.fragment.FragmentDialog_about;
import com.zjy.simpleweather.fragment.FragmentDialog_send;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 极速蜗牛 on 2016/12/24 0024.
 */

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tv_yijian)
    TextView tvYijian;
    @BindView(R.id.tv_about)
    TextView tvAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

    }

    @OnClick({R.id.tv_about, R.id.tv_yijian})
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.tv_yijian:
                FragmentDialog_send dialog_send = new FragmentDialog_send();
                dialog_send.show(getSupportFragmentManager(), "yijian");
                break;
            case R.id.tv_about:
                FragmentDialog_about dialog_about = new FragmentDialog_about();
                dialog_about.show(getSupportFragmentManager(), "about");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
