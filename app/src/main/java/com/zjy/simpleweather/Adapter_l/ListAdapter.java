package com.zjy.simpleweather.Adapter_l;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjy.simpleweather.R;
import com.zjy.simpleweather.dataBean.WeatherDataBean;
import com.zjy.simpleweather.entiy.MyContast;
import com.zjy.simpleweather.utils.DayForWeek;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 极速蜗牛 on 2016/12/29 0029.
 */

public class ListAdapter extends BaseAdapter implements View.OnClickListener {

    public int[] code_icon = new int[]{
            R.mipmap.h100, R.mipmap.h101, R.mipmap.h102, R.mipmap.h103, R.mipmap.h104,
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
            "100", "101", "102", "103", "104", "200", "201", "202", "203",
            "204", "205", "206", "207", "208", "209", "210", "211"
            , "212", "213", "300", "301", "302", "303", "304", "305",
            "306", "307", "308", "309", "310", "311", "312", "313",
            "400", "401", "402", "403", "404", "405", "406", "407", "500",
            "501", "502", "503", "504", "507", "508", "900", "901", "999"
    };

    public String[] dayWeek = new String[]{
            "未知","星期一","星期二","星期三","星期四","星期五","星期六","星期日"
    };

    private Map<String, Integer> map_icon;


    private WeatherDataBean dataBean;
    Context context;

    public ListAdapter(WeatherDataBean dataBean, Context context) {
        this.dataBean = dataBean;
        this.context = context;
        setmap();
    }


    /**
     * 初始化map
     */
    public void setmap() {
        map_icon = new HashMap<String, Integer>();
        for (int i = 0; i < code_icon.length; i++) {
            map_icon.put(code_key[i], code_icon[i]);
        }

        for (int i = 0; i < code_icon.length; i++) {
            Log.e(MyContast.TAG, map_icon.get(code_key[i]) + " ");
        }
    }


    @Override
    public int getCount() {
        return dataBean.getHeWeather5().get(0).getDaily_forecast().size();
    }

    @Override
    public Object getItem(int position) {
        return dataBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String code_icon_w = dataBean.getHeWeather5().get(0).getDaily_forecast().get(position).getCond().getCode_d();



        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.weilai3weather, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        //取出数据 用方法判断当前星期几
        String date = dataBean.getHeWeather5().get(0).getDaily_forecast().get(position).getDate();
        String week = dayWeek[DayForWeek.GetDayToWeek(date)];

        holder.tvWlDate.setText(date);
        holder.tvWlWeather.setText(week+" "+
                dataBean.getHeWeather5().get(0).getDaily_forecast().get(position).getCond().getTxt_d().toString());
        holder.tvWlWeathercode.setText(dataBean.getHeWeather5().get(0).getDaily_forecast().get(position).getTmp().getMin() + "~" +
                dataBean.getHeWeather5().get(0).getDaily_forecast().get(position).getTmp().getMax() + "度");

        holder.ivWlWeather.setImageResource(map_icon.get(code_icon_w));

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    class ViewHolder {
        @BindView(R.id.iv_wl_weather)
        ImageView ivWlWeather;
        @BindView(R.id.tv_wl_date)
        TextView tvWlDate;
        @BindView(R.id.tv_wl_weather)
        TextView tvWlWeather;
        @BindView(R.id.tv_wl_weathercode)
        TextView tvWlWeathercode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
