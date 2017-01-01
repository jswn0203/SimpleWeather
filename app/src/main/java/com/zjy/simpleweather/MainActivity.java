package com.zjy.simpleweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;

import com.zjy.simpleweather.cacheIO.DiskLruCache;
import com.zjy.simpleweather.dataBean.WeatherDataBean;
import com.zjy.simpleweather.fragment.AddCityFragmentWeather;
import com.zjy.simpleweather.fragment.BaseFragmentWeather;
import com.zjy.simpleweather.fragment.FragmentDialog_AddCity;
import com.zjy.simpleweather.fragment.WeatherFragment;
import com.zjy.simpleweather.ui.SettingActivity;
import com.zjy.simpleweather.utils.Obj2File;
import com.zjy.simpleweather.utils.ToastShow;
import com.zjy.simpleweather.utils.Tool_apk;
import com.zjy.simpleweather.utils.tool_Stringtokey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;

    public ArrayList<WeatherFragment> list_fg;
    private adapter adapter1;

    private int coude = 0;
    private DiskLruCache mDiskLruCache = null;
    private AddCityFragmentWeather addweatherfg;

    private boolean isBack = false;//用于判断用户是否无意触碰到back键,提示是否退出
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isBack = false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        bar.setElevation(0);


        list_str = new ArrayList<String>();

        init();


        list_fg = new ArrayList<WeatherFragment>();

        final BaseFragmentWeather fg = new BaseFragmentWeather();
        fg.setUserVisibleHint(true);
        list_fg.add(fg);

        if (readCache() != null){
            list_str = readCache();
            for (int i = 0; i<list_str.size();i++){
                addweatherfg = new AddCityFragmentWeather();
                addweatherfg.setCityName(list_str.get(i),MainActivity.this);
                list_fg.add(addweatherfg);
                Log.e("read",list_str.get(i));
            }
        }

        adapter1 = new adapter(getSupportFragmentManager());
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(adapter1);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                coude = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private List<String> list_str;

    /**
     * 读添加的城市的列表
     * @return 返回城市list
     */
    public List<String> readCache(){
        List<String> list_read = new ArrayList<String>();
            String key = tool_Stringtokey.hashKeyForDisk("list");
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot != null) {
                    InputStream in = snapshot.getInputStream(0);
                    byte[] btyes = new byte[1024 * 1024];
                    in.read(btyes);
                    list_read = (List<String>) Obj2File.Bytes2Obj(btyes);
                }
                return list_read;
            } catch (IOException e) {
                e.printStackTrace();
            }
        for (int i = 0; i<list_str.size(); i++){
            Log.e("read",list_read.get(i));
        }
        return  null;
    }

    /**
     * 初始化缓存空间
     */
    public void init() {
        File CacheDir = Tool_apk.getDiskCacheDir(this, "weather");
        if (!CacheDir.exists()) {
            CacheDir.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(CacheDir, Tool_apk.getAppversion(this), 1, 1024 * 1024);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class adapter extends FragmentStatePagerAdapter {
        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list_fg.get(position);
        }

        @Override
        public int getCount() {
            return list_fg.size();
        }

        @Override
        public int getItemPosition(Object object) {
            //修改默认值，强制刷新viewpager;
            return POSITION_NONE;
        }
    }

    /**
     * actionbar的菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settingitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.add_city:
                FragmentDialog_AddCity dialog_addcity = new FragmentDialog_AddCity();
                dialog_addcity.setContext(MainActivity.this);
                dialog_addcity.show(getSupportFragmentManager(), "add");
                dialog_addcity.setOnListener(new FragmentDialog_AddCity.onListener() {
                    @Override
                    public void getcity(AddCityFragmentWeather addfg,String str) {
                        list_str.add(str);

                        list_fg.add(addfg);
                        adapter1.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.remove_city:
                if (coude > 0) {
                    list_fg.remove(coude);

                    list_str.remove(coude-1);

                    adapter1.notifyDataSetChanged();
                    pager.postInvalidate();
                } else {
                    ToastShow.ToastShow(MainActivity.this, "必须要保留一个城市哦"+coude);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {

        String key = tool_Stringtokey.hashKeyForDisk("list");
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream out = editor.newOutputStream(0);
                byte[] bytes = Obj2File.Obj2Bytes(list_str);
                if (Obj2File.saveio(bytes, out)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            ToastShow.ToastShow(MainActivity.this,"确定要退出吗?");
            if (!isBack){
                isBack = true;
                handler.sendEmptyMessageDelayed(1,2000);
            }else {
                return super.onKeyDown(keyCode, event);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
