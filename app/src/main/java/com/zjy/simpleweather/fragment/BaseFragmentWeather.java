package com.zjy.simpleweather.fragment;

import android.app.ProgressDialog;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.zjy.simpleweather.Adapter_l.ListAdapter;
import com.zjy.simpleweather.Adapter_l.desAdapter;
import com.zjy.simpleweather.R;
import com.zjy.simpleweather.cacheIO.DiskLruCache;
import com.zjy.simpleweather.dataBean.WeatherDataBean;
import com.zjy.simpleweather.entiy.MyContast;
import com.zjy.simpleweather.interface_data.WeatherImpl;
import com.zjy.simpleweather.utils.DayForWeek;
import com.zjy.simpleweather.utils.Obj2File;
import com.zjy.simpleweather.utils.ToastShow;
import com.zjy.simpleweather.utils.Tool_apk;
import com.zjy.simpleweather.utils.tool_Stringtokey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 极速蜗牛 on 2016/12/24 0024.
 */

public class BaseFragmentWeather extends WeatherFragment implements Serializable {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_updatatime)
    TextView tvUpdatatime;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.iv_weather_icon)
    ImageView ivWeatherIcon;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_fx)
    TextView tvFx;
    @BindView(R.id.tv_power)
    TextView tvPower;
    @BindView(R.id.llchart)
    LineChart llchart;
    @BindView(R.id.list_v)
    ListView listV;
    @BindView(R.id.refresh_tool)
    SwipeRefreshLayout refreshTool;
    @BindView(R.id.des_list)
    ListView desList;
    private View view;

    private ListAdapter adapter;
    private desAdapter adapter_des;


    public int[] code_icon = new int[]{
            R.mipmap.h100, R.mipmap.h101,R.mipmap.h102, R.mipmap.h103, R.mipmap.h104,
            R.mipmap.h200, R.mipmap.h201, R.mipmap.h202, R.mipmap.h203,
            R.mipmap.h204, R.mipmap.h205, R.mipmap.h206, R.mipmap.h207,
            R.mipmap.h208, R.mipmap.h209, R.mipmap.h210, R.mipmap.h211,
            R.mipmap.h212, R.mipmap.h213, R.mipmap.h300, R.mipmap.h301,
            R.mipmap.h302, R.mipmap.h303, R.mipmap.h304, R.mipmap.h305,
            R.mipmap.h306, R.mipmap.h307, R.mipmap.h308, R.mipmap.h309,
            R.mipmap.h310, R.mipmap.h311, R.mipmap.h312, R.mipmap.h313,
            R.mipmap.h400, R.mipmap.h401, R.mipmap.h402, R.mipmap.h403,
            R.mipmap.h404, R.mipmap.h405, R.mipmap.h406, R.mipmap.h407,
            R.mipmap.h500, R.mipmap.h501, R.mipmap.h502, R.mipmap.h503,
            R.mipmap.h504, R.mipmap.h507, R.mipmap.h508,
            R.mipmap.h900, R.mipmap.h901, R.mipmap.h999
    };

    public String[] code_key = new String[]{
            "100","101","102","103","104","200","201","202","203",
            "204","205","206","207","208","209","210","211"
            ,"212","213","300","301","302","303","304","305",
            "306","307","308","309","310","311","312","313",
            "400","401","402","403","404","405","406","407","500",
            "501","502","503","504","507","508","900","901","999"
    };

    private Map<String,Integer> map_icon;
    /**
     * 日期
     */
    public String[] dayWeek = new String[]{
            "未知","星期一","星期二","星期三","星期四","星期五","星期六","星期日"
    };

    DiskLruCache mDiskLruCache = null;
    private String city_name = "北京";
    private WeatherImpl impl = null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    int color = 0xff20e291;
    ProgressDialog diwei_dialog;

    private LineDataSet set1;
    private WeatherDataBean dataBean;


    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basefragment, null);
        ButterKnife.bind(this, view);
        setmap();
        Log.e(MyContast.TAG,code_icon.length+" icon");
        Log.e(MyContast.TAG,code_key.length+" key");
        initCache();//缓存初始化

        //初始定位数据
        initView();
        mLocationClient.start();

        refreshTool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String key = tool_Stringtokey.hashKeyForDisk(city_name);
                try {
                    mDiskLruCache.remove(key);
                    requestWeather(city_name.substring(0, city_name.length() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    /**
     * 初始化map
     */
    public void setmap(){
        map_icon = new HashMap<String, Integer>();
        for (int i = 0;i< code_icon.length; i++){
            map_icon.put(code_key[i],code_icon[i]);
        }

        for (int i = 0;i< code_icon.length; i++){
            Log.e(MyContast.TAG,map_icon.get(code_key[i])+" ");
        }
    }


    /**
     * 初始化缓存配置
     */
    private void initCache() {
        //在android/data/data下创建Weather文件夹

        File cachefile = Tool_apk.getDiskCacheDir(getContext(), "HomeWeather");
        if (!cachefile.exists()) {
            cachefile.mkdirs();
        }
        try {
            //设置一个10兆大小的缓存空间
            mDiskLruCache = DiskLruCache.open(cachefile, Tool_apk.getAppversion(getContext()), 1, 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化折线图数据
     */
    public void initChart() {
        //设置后台绘制
        llchart.setDrawGridBackground(false);
        //不需要描述文本
        llchart.getDescription().setEnabled(false);
        //bu支持触摸手势
        llchart.setTouchEnabled(false);
        llchart.setDrawBorders(true);

        //bu设置缩放/滑动
        llchart.setDragEnabled(false);
        llchart.setScaleEnabled(false);

        XAxis xAxis = llchart.getXAxis();
        //设置最大值
        xAxis.setAxisMaximum(8);
        //设置最小值
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1);

        YAxis leftAxis = llchart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawGridLines(false);
        llchart.getAxisRight().setEnabled(false);
        llchart.animateX(1000);
    }

    /**
     * 设置折线图数据
     *
     * @param values
     */
    private void setData(final ArrayList<Entry> values) {
        if (llchart.getData() != null &&
                llchart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) llchart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            llchart.getData().notifyDataChanged();
            llchart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "今天24小时天气");
            set1.setColor(color);
            set1.setCircleColor(color);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(8f);

            set1.setFormLineWidth(2f);
            set1.setHighLightColor(color);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            llchart.setData(data);
        }
        llchart.invalidate();
    }



    //初始化retrofit
    private void initRetrifit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://free-api.heweather.com/")
                .build();
        impl = retrofit.create(WeatherImpl.class);
    }

    //数据请求
    private void requestWeather(final String cityname1) {
        impl.getWeather(cityname1, MyContast.Key_HF).enqueue(new Callback<WeatherDataBean>() {
            @Override
            public void onResponse(Call<WeatherDataBean> call, Response<WeatherDataBean> response) {

                Log.e(MyContast.TAG, cityname1 + "123");
                if (response.body().getHeWeather5().get(0).getStatus().equals("ok")) {
                    refreshTool.setRefreshing(false);
                    WeatherDataBean bean1 = response.body();
                    String key1 = tool_Stringtokey.hashKeyForDisk(cityname1);
                    try {
                        DiskLruCache.Editor editor = mDiskLruCache.edit(key1);
                        if (editor != null) {
                            OutputStream out = editor.newOutputStream(0);
                            byte[] bytes = Obj2File.Obj2Bytes(response.body());
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
                    setdata_UI(bean1);
                } else {
                    ToastShow.ToastShow(getActivity(), "呜呜。。没有钱买服务数据了");
                }
            }


            @Override
            public void onFailure(Call<WeatherDataBean> call, Throwable t) {
                ToastShow.ToastShow(getContext(), "网络出现错误");
                refreshTool.setRefreshing(false);
                System.out.println(t.toString());
            }
        });
    }

    /**
     * 抽取UI数据绑定的方法
     *
     * @param bean
     */
    public void setdata_UI(WeatherDataBean bean) {
        //取出数据 用方法判断当前星期几
        String date = bean.getHeWeather5().get(0).getDaily_forecast().get(0).getDate();
        String week = dayWeek[DayForWeek.GetDayToWeek(date)];



        ArrayList<Entry> values = new ArrayList<Entry>();
        ToastShow.ToastShow(getContext(), Integer.parseInt("-1") + "");
        tvDate.setText(week);
        tvUpdatatime.setText("数据源更新时间：\n"+bean.getHeWeather5().get(0).getBasic().getUpdate().getUtc());
        tvCity.setText(bean.getHeWeather5().get(0).getBasic().getCity());
        tvWeek.setText(bean.getHeWeather5().get(0).getBasic().getUpdate().getLoc().split(" ")[0]);
        tvCode.setText(bean.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp().getMin()
                + "~" +
                bean.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp().getMax() + "度");

        String code_icon_w = bean.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getCode_d();

        ivWeatherIcon.setImageResource(map_icon.get(code_icon_w));

        Log.e(MyContast.TAG,code_icon_w);

        tvWeather.setText(bean.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_d());
        tvPower.setText(bean.getHeWeather5().get(0).getDaily_forecast().get(0).getWind().getSpd());
        tvFx.setText(bean.getHeWeather5().get(0).getDaily_forecast().get(0).getWind().getSc());

        adapter = new ListAdapter(bean, getContext());
        listV.setAdapter(adapter);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        adapter_des = new desAdapter(bean,getContext());
        desList.setAdapter(adapter_des);

        for (int i = 0; i < bean.getHeWeather5().get(0).getHourly_forecast().size(); i++) {
            String code = bean.getHeWeather5().get(0).getHourly_forecast().get(i).getTmp();
            values.add(new Entry(i + 1, Integer.parseInt(code)));
        }
        setData(values);
    }

    private void initView() {
        diwei_dialog = new ProgressDialog(getContext());
        diwei_dialog.setMessage("定位中...");
        //屏幕外点击无效
        diwei_dialog.setCancelable(false);
        diwei_dialog.show();
        initChart();
        //数据请求
        initRetrifit();

        mLocationClient = new LocationClient(getContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public String getCity_name(){
        return city_name;
    }

    public class MyLocationListener implements BDLocationListener, Serializable {

        @Override
        public void onReceiveLocation(BDLocation location) {
            diwei_dialog.dismiss();
            switch (location.getLocType()) {
                case BDLocation.TypeOffLineLocation:
                case BDLocation.TypeGpsLocation:
                case BDLocation.TypeNetWorkLocation:
                    city_name = location.getCity();
                    Log.e("cityname", city_name);
                    break;
                case BDLocation.TypeServerError:
                case BDLocation.TypeNetWorkException:
                case BDLocation.TypeCriteriaException:
                    break;
            }
            Log.i(MyContast.TAG, city_name);
            CacheOrQue();
        }
    }


    /**
     * 有缓存就缓存数据绑定否则请求网络
     */
    public void CacheOrQue() {

        String city_name1 = city_name.substring(0, city_name.length() - 1);
        String key = tool_Stringtokey.hashKeyForDisk(city_name1);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream in = snapshot.getInputStream(0);
                byte[] btyes = new byte[1024 * 1024];
                in.read(btyes);
                dataBean = (WeatherDataBean) Obj2File.Bytes2Obj(btyes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dataBean != null) {
            setdata_UI(dataBean);
            Log.e("requestWeather cache", city_name);
        } else {
            Log.e("requestWeather cityname", city_name);
            requestWeather(city_name1);
        }
    }
}
